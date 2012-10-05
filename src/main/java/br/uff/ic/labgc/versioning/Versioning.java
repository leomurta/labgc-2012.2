/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    
    private String dirPath = "C:\\repositorio\\";

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

    @Override
    public List<VersionedItem> getRevision(String revNum, String token) throws ObjectNotFoundException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        Revision revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), revNum);
        ArrayList<VersionedItem> list = new ArrayList<VersionedItem>();
        for (Iterator it = revision.getConfigItens().iterator(); it.hasNext(); )
        {	
            ConfigurationItem ci = (ConfigurationItem)it.next();
            
            //pegando a primeira revisao na qual o item aparece
            Revision ciRev = (Revision)ci.getRevisions().iterator().next();
            VersionedItem vi;
            if (ci.getName().endsWith("\\")){//diretorio
                vi = new VersionedDir();
            }
            else{
                vi = new VersionedFile();
            }
            
            vi.setAuthor(ciRev.getUser().getName());
            vi.setCommitMessage(ciRev.getDescription());
            vi.setLastChangedRevision(ciRev.getNumber());
            vi.setLastChangedTime(ciRev.getDate());
            vi.setHash(ci.getHash());
            vi.setName(ci.getName());
            list.add(vi);
        }
        return list;
    }
    
    public String login(String projectName, String userName, String pass) throws ObjectNotFoundException, IncorrectPasswordException{
        User user = userDAO.getByUserName(userName);
        if (!user.getPassword().equals(pass)){
            throw new IncorrectPasswordException();
        }
        Project project = projectDAO.getName(projectName);
        ProjectUser pu = projectUserDAO.get(user.getId(), project.getId());
        if (pu.getToken().equals("")){
            pu.generateToken();
        }
        return pu.getToken();
    }

    @Override
    public VersionedFile getVersionedFile(String hash, String token) throws IOException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        //TODO DUVAL tem que ser por hash e projeto
        ConfigurationItem ci = configItemDAO.getByHash(hash); 
        Revision revision = (Revision)ci.getRevisions().iterator().next();
        
        VersionedFile vf = new VersionedFile();
        vf.setAuthor(revision.getUser().getName());
        vf.setCommitMessage(revision.getDescription());
        vf.setLastChangedRevision(revision.getNumber());
        vf.setLastChangedTime(revision.getDate());
        vf.setHash(ci.getHash());
        vf.setName(ci.getName());
        
        String projName = pu.getProject().getName();
        Path path = Paths.get(dirPath+projName+"\\"+ci.getHash().subSequence(0, 3)+"\\"+ci.getHash());
        byte bytes[] = Files.readAllBytes(path);
        vf.setContent(bytes);
        vf.setSize(bytes.length);
        
        return vf;
    }
    
}
