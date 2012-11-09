/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.exception.VersioningCanNotCreateDirException;
import br.uff.ic.labgc.exception.VersioningException;
import br.uff.ic.labgc.exception.VersioningIOException;
import br.uff.ic.labgc.exception.VersioningNeedToUpdateException;
import br.uff.ic.labgc.exception.VersioningProjectAlreadyExistException;
import br.uff.ic.labgc.exception.VersioningUserNotFoundException;
import br.uff.ic.labgc.storage.ConfigurationItem;
import br.uff.ic.labgc.storage.ConfigurationItemDAO;
import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.Revision;
import br.uff.ic.labgc.storage.RevisionDAO;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.bytecode.Descriptor;

/**
 *
 * @author jokerfvd
 */
public class Versioning implements IVersioning{
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static ConfigurationItemDAO configItemDAO = new ConfigurationItemDAO();
    
    public static String dirPath = "repositorio/";

    public Versioning() {
    }
    
    public Versioning(String dirPath) {
        this.dirPath = dirPath;
    }
    
    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public String getRevisionUserName(String number) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private VersionedDir configItemToVersionedDir(ConfigurationItem ci){
        VersionedDir vd = new VersionedDir();
        for (Iterator it = ci.getChildren().iterator(); it.hasNext(); )
        {	
            ConfigurationItem configItem = (ConfigurationItem)it.next();
            Revision ciRev = configItem.getRevision();
            VersionedItem vi;
            if (configItem.getDir() == 1){
                vi = configItemToVersionedDir(configItem);
            }
            else{
                vi = new VersionedFile(configItem.getHash(),configItem.getSize());
            }
            
            vi.setAuthor(ciRev.getUser().getName());
            vi.setCommitMessage(ciRev.getDescription());
            vi.setLastChangedRevision(ciRev.getNumber());
            vi.setLastChangedTime(ciRev.getDate());
            vi.setName(configItem.getName());
            
            //TODO DUVAL
            //vd.setStatus(status);
            
            vd.addItem(vi);
        }
        Revision ciRev = ci.getRevision();
        vd.setAuthor(ciRev.getUser().getName());
        vd.setCommitMessage(ciRev.getDescription());
        vd.setLastChangedRevision(ciRev.getNumber());
        vd.setLastChangedTime(ciRev.getDate());
        vd.setName(ci.getName());
        
        //TODO DUVAL
        //vd.setStatus(status);
        
      return vd;
    }


    @Override
    public VersionedDir getRevision(String revNum, String token) throws VersioningException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        if (revNum.equals(EVCSConstants.REVISION_HEAD)) {
            revNum = revisionDAO.getHeadRevisionNumber(pu.getProject());
        }
        Revision revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), revNum);
        ConfigurationItem ci = revision.getConfigItem();
        VersionedDir vd = configItemToVersionedDir(ci);
        return vd;
    }
    
    //nao encontrado, senha incorreta, sem permissao
    public String login(String projectName, String userName, String pass) throws VersioningException{
        User user = userDAO.getByUserName(userName);
        if (!user.getPassword().equals(pass)){
            throw new IncorrectPasswordException();
        }
        Project project = projectDAO.getByName(projectName);
        ProjectUser pu = projectUserDAO.get(user.getId(), project.getId());
        if (pu.getToken().isEmpty()){
            pu.generateToken();
        }
        return pu.getToken();
    }

    @Override
    public byte[] getVersionedFileContent (String hash, String token) throws VersioningException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        String projName = pu.getProject().getName();
        Path path = Paths.get(dirPath+projName+"/"+hashToPath(hash));
        byte bytes[];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException ex) {
            throw new VersioningIOException(ex.getMessage());
        }
        
        return bytes;
    }
    
    private String hashToPath(String hash){
        String path;
        path = hash.substring(0, 3)+"/"+hash.substring(3);
        return path;
    }
    
    private String incrementRevision(String revision){
        int pos = revision.lastIndexOf(".")+1;
        int num = Integer.parseInt(revision.substring(pos));
        num++;
        String rev = revision.substring(0,pos).concat(Integer.toString(num));
        return rev;
    }
    
    /*
     * no caso de não estar sendo usado o diff, temos os status para saber o que
     * foi modificado
     * synchronized para garantir que 2 processos não peguem a mesma versão para
     * atualizar
     */
    @Override
    public synchronized String addRevision(VersionedDir vd, String token) throws VersioningException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        String projectName = pu.getProject().getName();
        
        try{
            String number = vd.getLastChangedRevision();
            //incrementar o number, se tiver com a mais atual pode, senno throws erro
            String headRevision = revisionDAO.getHeadRevisionNumber(pu.getProject());
            
            if (!headRevision.equals(number)){
                throw new VersioningNeedToUpdateException();
            }
            
            Revision currRevision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(),headRevision);
            String newHeadRevision = incrementRevision(headRevision);
            
            Date date = new Date();
            Revision revision = new Revision(date, vd.getCommitMessage(), newHeadRevision, pu.getUser(), pu.getProject());
            revisionDAO.add(revision);
            //TODO Duval, o status vem como int, passar para char
            ConfigurationItem previous = currRevision.getConfigItem();
            char type;
            switch (vd.getStatus()) {
                case EVCSConstants.ADDED:
                    type = 'A';
                    break;
                case EVCSConstants.DELETED:
                    type = 'D';
                    break;
                case EVCSConstants.MODIFIED:
                    type = 'M';
                    break;  
                case EVCSConstants.UNMODIFIED:
                    type = 'U';
                    break;  
                default:
                   type = 'U';
                    break;
            }
            
            ConfigurationItem ci = new ConfigurationItem(previous.getNumber()+1,
                    projectName,"",type, 1,vd.getSize(),previous,null,revision);
            
            versionedDirToConfigItem(vd,ci,false);
            
            configItemDAO.add(ci);
            
            //verificar se vai ser atualizado no banco automaticamente
            //e se precisa mesmo fazer isso
            revision.setConfigItem(ci);
            return revision.getNumber();
            
        }catch (ObjectNotFoundException e) {
             throw new VersioningUserNotFoundException();   
        }
    }
    

    /**
     * mesmo que um import, checkin
     * a primeira revisão é a 1.0
     * a primeira versão de um item é a 1
     * o nome da raiz sera o nome do projeto
     * @param vd
     * @param user 
     */
    @Override
    public String addFirstRevision(VersionedDir vd, String userName) throws VersioningException{
        String projectName = vd.getName();
        if (projectDAO.exist(projectName)){
            throw new VersioningProjectAlreadyExistException();
        }
        
        Project project = new Project(projectName);
        try{
            User user = userDAO.getByUserName(userName);
            //project.addUser(user);
            projectDAO.add(project);
            ProjectUser pu = new ProjectUser(project.getId(),user.getId());
            pu.setPermission(11111);
            projectUserDAO.add(pu);
            project.addUser(user);
            boolean success = (new File(dirPath+projectName)).mkdirs();
            if (!success) {
                throw new VersioningCanNotCreateDirException();
            }
            
            //criar revisão 0
            Date date = new Date();
            Revision revision = new Revision(date, "revision 1.0", "1.0", user, project);
            revisionDAO.add(revision);
            ConfigurationItem ci = new ConfigurationItem(1,projectName,"",'A',1,vd.getSize(),null,null,revision);
            
            configItemDAO.add(ci);
            versionedDirToConfigItem(vd,ci,true);
            
            //verificar se vai ser atualizado no banco automaticamente
            //e se precisa mesmo fazer isso
            revision.setConfigItem(ci);
            return revision.getNumber();
            
        }catch (ObjectNotFoundException e) {
             throw new VersioningUserNotFoundException();   
        }
       
    }
    
    private void versionedDirToConfigItem(VersionedDir vd, ConfigurationItem father, boolean first){
        for (Iterator<VersionedItem> it = vd.getContainedItens().iterator(); it.hasNext(); ){
            VersionedItem vi = it.next();
            ConfigurationItem ci;
            String hash = "";
            if (!vi.isDir()){
                hash = ((VersionedFile)vi).getHash();
            }
            
            if (first){
                ci = new ConfigurationItem(1, vi.getName(), hash, 'A', vi.isDir()?1:0,
                        vi.getSize(), null, null, father.getRevision());
            }
            else{
                //TODO DUVAL
                //NAO EH PRIMEIRA REVISAO
                ci = new ConfigurationItem();
            }
            
            configItemDAO.add(ci);
            if (vi.isDir()){
                versionedDirToConfigItem((VersionedDir)vi,ci,first);
            }
            father.addChild(ci);            
        }        
    }

    /*
     * Dada uma revisão, madar o diff para que esta seja atualizada para outra
     */
    @Override
    public VersionedDir updateRevision(String revNum, String revTo, String token) throws VersioningException {
        return getRevision(EVCSConstants.REVISION_HEAD, token);
    }
    
    public List<VersionedDir> getLastLogs(String token){
        return getLastLogs(EVCSConstants.DEFAULT_LOG_MSG, token);
    }
    
    /*
     * primeiro da lista é o + recente
     */
    @Override
    public List<VersionedDir> getLastLogs(int num, String token){
        List<VersionedDir> list = new ArrayList();
        ProjectUser pu = projectUserDAO.getByToken(token);
        String revNum = revisionDAO.getHeadRevisionNumber(pu.getProject());
        Revision revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), revNum);
        ConfigurationItem ci = revision.getConfigItem();
        for(int i=0; i < num; i++){
            VersionedDir vd = new VersionedDir();
            vd.setAuthor(ci.getRevision().getUser().getName());
            vd.setCommitMessage(ci.getRevision().getDescription());
            vd.setLastChangedRevision(ci.getRevision().getNumber());
            vd.setLastChangedTime(ci.getRevision().getDate());
            vd.setName(ci.getName());
            //vd.setStatus();
            list.add(vd);
            ci = ci.getPrevious();
            if (ci == null){
                break;
            }
        }
        
        return list;
    }
}
