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
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    private String dirPath = "repositorio/";

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
    
    private VersionedDir ConfigItemToVersionedDir(ConfigurationItem ci){
        VersionedDir vd = new VersionedDir();
        for (Iterator it = ci.getChildren().iterator(); it.hasNext(); )
        {	
            ConfigurationItem configItem = (ConfigurationItem)it.next();
            Revision ciRev = configItem.getRevision();
            VersionedItem vi;
            if (configItem.isDir()){
                vi = ConfigItemToVersionedDir(configItem);
            }
            else{
                vi = new VersionedFile();
            }
            
            vi.setAuthor(ciRev.getUser().getName());
            vi.setCommitMessage(ciRev.getDescription());
            vi.setLastChangedRevision(ciRev.getNumber());
            vi.setLastChangedTime(ciRev.getDate());
            vi.setHash(configItem.getHash());
            vi.setName(configItem.getName());
            vi.setSize(configItem.getSize());
            
            vd.addItem(vi);
        }
        vd.setSize(ci.getSize());
      return vd;
    }


    @Override
    public VersionedDir getRevision(String revNum, String token) throws ObjectNotFoundException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        Revision revision = revisionDAO.getByProjectAndNumber(pu.getProject().getId(), revNum);
        ConfigurationItem ci = revision.getConfigItem();
        VersionedDir vd = ConfigItemToVersionedDir(ci);
        return vd;
    }
    
    //nao encontrado, senha incorreta, sem permissao
    public String login(String projectName, String userName, String pass) throws ObjectNotFoundException, IncorrectPasswordException{
        User user = userDAO.getByUserName(userName);
        if (!user.getPassword().equals(pass)){
            throw new IncorrectPasswordException();
        }
        Project project = projectDAO.getName(projectName);
        ProjectUser pu = projectUserDAO.get(user.getId(), project.getId());
        if (pu.getToken().isEmpty()){
            pu.generateToken();
        }
        return pu.getToken();
    }

    @Override
    public byte[] getVersionedFileContent(String hash, String token) throws IOException{
        ProjectUser pu = projectUserDAO.getByToken(token);
        String projName = pu.getProject().getName();
        Path path = Paths.get(dirPath+projName+"/"+hashToPath(hash));
        byte bytes[] = Files.readAllBytes(path);
        
        return bytes;
    }
    
    private String hashToPath(String hash){
        String path;
        path = hash.substring(0, 3)+"/"+hash;
        return path;
    }

    @Override
    public void addRevision(VersionedDir vd, String token){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * gera um hash com 32 chars
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public String generatehash(byte bytes[]) throws NoSuchAlgorithmException{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(bytes);
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
          hashtext = "0"+hashtext;
        }     
        return hashtext;
    }

    public void addProject(VersionedDir vd, String user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
