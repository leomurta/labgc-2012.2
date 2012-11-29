/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.exception.StorageCanNotCreateDirException;
import br.uff.ic.labgc.exception.StorageException;
import br.uff.ic.labgc.exception.VersioningException;
import br.uff.ic.labgc.exception.VersioningIOException;
import br.uff.ic.labgc.exception.VersioningNeedToUpdateException;
import br.uff.ic.labgc.exception.StorageObjectAlreadyExistException;
import br.uff.ic.labgc.exception.StorageUserNotFoundException;
import br.uff.ic.labgc.storage.ConfigurationItem;
import br.uff.ic.labgc.storage.ConfigurationItemDAO;
import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.Revision;
import br.uff.ic.labgc.storage.RevisionDAO;
import br.uff.ic.labgc.storage.Storage;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;
import br.uff.ic.labgc.storage.util.HibernateUtil;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
    private static MyStorage storage = new MyStorage();
    
    private static class MyStorage extends Storage { 
        @Override
        protected void addProject(String projName, String userName, ConfigurationItem ci) throws StorageException{
            super.addProject(projName, userName, ci);
        }
        protected String hashToPath(String hash){
            return super.hashToPath(hash);
        }
        protected void storeFiles(VersionedDir father, String projName) throws ApplicationException {
            super.storeFiles(father, projName);
        }
    }
    
    //public static String dirPath = "../../repositorio/";

    public Versioning() {
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
            String projectName = ciRev.getProject().getName();
            VersionedItem vi;
            if (configItem.getDir() == 1){
                vi = configItemToVersionedDir(configItem);
            }
            else{
                vi = new VersionedFile(configItem.getHash(),configItem.getSize(),projectName);
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
    public VersionedDir getRevision(String revNum, String token) throws ApplicationException{
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
        ProjectUser pu = projectUserDAO.get(project.getId(), user.getId());
 
       HibernateUtil.beginTransaction();
       if (pu.getToken()==null || pu.getToken().isEmpty()){
            pu.generateToken();
        }
       HibernateUtil.commitTransaction();
        return pu.getToken();
    }

    @Override
    public byte[] getVersionedFileContent (String hash, String projectName) throws ApplicationException{
        
        System.out.println(Storage.getDirPath()+projectName+"/"+storage.hashToPath(hash));
        File path = new File(Storage.getDirPath()+projectName+"/"+storage.hashToPath(hash));
        try {
            return getBytesFromFile(path);
        } catch (IOException ex) {
            Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, null, ex);
            throw new VersioningIOException("nao foi possivel ler o conteudo do arquivo",ex);
        }
    }
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();    
        return bytes;
    }
    
    private String incrementRevision(String revision){
        int pos = revision.lastIndexOf(".")+1;
        int num = Integer.parseInt(revision.substring(pos));
        num++;
        String rev = revision.substring(0,pos).concat(Integer.toString(num));
        return rev;
    }
    
    private char toMyStatus(int status){
        switch (status) {
                case EVCSConstants.ADDED:
                    return 'A';
                case EVCSConstants.DELETED:
                    return 'D';
                case EVCSConstants.MODIFIED:
                    return 'M';
                case EVCSConstants.UNMODIFIED:
                    return 'U';
                default:
                   return 'U';
            }
    }
    
    private int toTheirsStatus(char status){
        switch (status){
            case 'A':
                return EVCSConstants.ADDED;
            case 'D':
                return EVCSConstants.DELETED;
            case 'M':
                return EVCSConstants.MODIFIED;    
            case 'U':
                return EVCSConstants.UNMODIFIED; 
            default:
                return EVCSConstants.UNMODIFIED;
        }
    }
    
    /*
     * no caso de não estar sendo usado o diff, temos os status para saber o que
     * foi modificado
     * synchronized para garantir que 2 processos não peguem a mesma versão para
     * atualizar
     */
    @Override
    public synchronized String addRevision(VersionedDir vd, String token) throws ApplicationException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        String projectName = pu.getProject().getName();
        
        try{
            String number = vd.getLastChangedRevision();
            //incrementar o number, se tiver com a mais atual pode, senno throws erro
            String headRevision = revisionDAO.getHeadRevisionNumber(pu.getProject());
            
            if (!headRevision.equals(number)){
                VersioningNeedToUpdateException ex = new VersioningNeedToUpdateException();
                Logger.getLogger(Versioning.class.getName()).log(Level.WARNING, null, ex);
                throw ex;
            }
            
            HibernateUtil.beginTransaction();
            Revision currRevision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(),headRevision);
            String newHeadRevision = incrementRevision(headRevision);
            
            Date date = new Date();
            Revision revision = new Revision(date, vd.getCommitMessage(), newHeadRevision, pu.getUser(), pu.getProject());
            revisionDAO.add(revision);
            ConfigurationItem previous = currRevision.getConfigItem();
            char type = toMyStatus(vd.getStatus());
            ConfigurationItem ci = new ConfigurationItem(previous.getNumber()+1,
                    projectName,"",type, 1,vd.getSize(),null,previous,revision);
            versionedDirToConfigItem(vd,ci,false);
            previous.setNext(ci);
            configItemDAO.add(ci);

            storage.storeFiles(vd, projectName);

            revision.setConfigItem(ci);
            HibernateUtil.commitTransaction();
            return revision.getNumber();
            
        }catch (ObjectNotFoundException e) {
             Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, null, e);
             throw new VersioningException("Objeto não encontrado", e);   
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
    public void addFirstRevision(VersionedDir vd, String userName) throws ApplicationException{
        String projectName = vd.getName();
        ConfigurationItem ci=null;
        storage.addProject(projectName, userName,ci);
        
        versionedDirToConfigItem(vd,ci,true);
        configItemDAO.add(ci);
        storage.storeFiles(vd, projectName);
    }
    
    private void versionedDirToConfigItem(VersionedDir vd, ConfigurationItem father, boolean first){
        for (Iterator<VersionedItem> it = vd.getContainedItens().iterator(); it.hasNext(); ){
            VersionedItem vi = it.next();
            ConfigurationItem ci = null;
            String hash = vi.isDir()?null:((VersionedFile)vi).getHash();
     
            if (first){
                ci = new ConfigurationItem(1, vi.getName(), hash, 'A', vi.isDir()?1:0,
                        vi.getSize(), null, null, father.getRevision());
            }
            else{
                ConfigurationItem previous = null;
                switch (vi.getStatus()){
                    case EVCSConstants.ADDED:
                        ci = new ConfigurationItem(1, vi.getName(), hash, 'A', vi.isDir()?1:0,
                        vi.getSize(), null, null, father.getRevision());
                        configItemDAO.add(ci);
                        break;
                    case EVCSConstants.DELETED:
                        previous = configItemDAO.getByValuesAnParent(vi.getName(),hash,father.getPrevious().getId(),vi.isDir()?1:0);
                        ci = new ConfigurationItem(previous.getNumber()+1, vi.getName(), 
                                hash, 'D', vi.isDir()?1:0,0, null, previous, father.getRevision());
                        previous.setNext(ci);
                        configItemDAO.add(ci);
                        break;
                    case EVCSConstants.MODIFIED:
                        //hash changed
                        previous = configItemDAO.getByValuesAnParent(vi.getName(),null,father.getPrevious().getId(),vi.isDir()?1:0);
                        ci = new ConfigurationItem(previous.getNumber()+1, vi.getName(), 
                                hash, 'M', vi.isDir()?1:0,vi.getSize(), null, previous, father.getRevision());
                        previous.setNext(ci);
                        configItemDAO.add(ci);
                        break;
                    case EVCSConstants.UNMODIFIED:
                        ci = configItemDAO.getByValuesAnParent(vi.getName(),hash,father.getPrevious().getId(),vi.isDir()?1:0);
                        break;
                }
            }
            
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
    public VersionedDir updateRevision(String revNum, String revTo, String token) throws ApplicationException {
        return getRevision(revTo, token);
    }
    
    public List<VersionedItem> getLastLogs(String token) throws ApplicationException{
        return getLastLogs(EVCSConstants.DEFAULT_LOG_MSG, token);
    }
    
    public List<VersionedItem> getLastLogs(int num, String token) throws ApplicationException{
        return getLastLogs(num,token,EVCSConstants.REVISION_HEAD);
    }
    
    /*
     * primeiro da lista é o + recente
     */
    @Override
    public List<VersionedItem> getLastLogs(int num, String token, String revisionNumber) throws ApplicationException{
        List<VersionedItem> list = new ArrayList();
        ProjectUser pu = projectUserDAO.getByToken(token);
        Revision revision;
        if (revisionNumber.equals(EVCSConstants.REVISION_HEAD)){
            String headNum = revisionDAO.getHeadRevisionNumber(pu.getProject());
            revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), headNum);
        }
        else{
            revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), revisionNumber);
        }
        ConfigurationItem ci = revision.getConfigItem();
        for(int i=0; i < num; i++){
            VersionedDir vd = new VersionedDir();
            vd.setAuthor(ci.getRevision().getUser().getName());
            vd.setCommitMessage(ci.getRevision().getDescription());
            vd.setLastChangedRevision(ci.getRevision().getNumber());
            vd.setLastChangedTime(ci.getRevision().getDate());
            vd.setName(ci.getName());
            vd.setStatus(toTheirsStatus(ci.getType()));
            list.add(vd);
            ci = ci.getPrevious();
            if (ci == null){
                break;
            }
        }
        
        return list;
    }
}
