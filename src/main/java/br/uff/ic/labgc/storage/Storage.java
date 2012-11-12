/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.StorageException;
import br.uff.ic.labgc.exception.StorageCanNotCreateDirException;
import br.uff.ic.labgc.exception.StorageObjectAlreadyExistException;
import br.uff.ic.labgc.exception.StorageUserNotFoundException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import br.uff.ic.labgc.versioning.Versioning;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jokerfvd
 */
public class Storage {

    private static String dirPath = "../../repositorio/";
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    private static RevisionDAO revisionDAO = new RevisionDAO();

    public static void setDirPath(String dirPath) {
        Storage.dirPath = dirPath;
    }

    public static String getDirPath() {
        return Storage.dirPath;
    }

    public Storage(String dirPath) {
        this.dirPath = dirPath;
    }

    public Storage() {
    }
    
    public void addUser(String name, String username, String password) throws StorageObjectAlreadyExistException{
        if (userDAO.exist(username)){
            throw new StorageObjectAlreadyExistException("Usuário "+username+" já existe");
        }
        User user = new User(name, username, password);
        userDAO.add(user);
    }

    public void addProject(String projName, String userName) throws StorageException {
        addProject(projName, userName, null);
    }
    
    public void addUserToProject(String projectName, String userName) throws StorageException{
        Project project = projectDAO.getByName(projectName);
        User user = userDAO.getByUserName(userName);
        ProjectUser pu = new ProjectUser(project.getId(), user.getId());
        if (projectUserDAO.exist(pu.getId())){
            throw new StorageObjectAlreadyExistException("Usuário "+userName+" já esta no projeto "+projectName);
        }
        pu.setPermission(11111);
        projectUserDAO.add(pu);
    }

    protected void addProject(String projName, String userName, ConfigurationItem ci) throws StorageException {
        if (projectDAO.exist(projName)) {
            throw new StorageObjectAlreadyExistException("Projeto "+projName+" já existe");
        }

        Project project = new Project(projName);
        try {
            User user = userDAO.getByUserName(userName);
            //project.addUser(user);
            projectDAO.add(project);
            ProjectUser pu = new ProjectUser(project.getId(), user.getId());
            //TODO definir quais são as prioridade
            pu.setPermission(11111);
            projectUserDAO.add(pu);
            boolean success = (new File(dirPath + projName)).mkdirs();
            if (!success) {
                throw new StorageCanNotCreateDirException();
            }

            //criar revisão 0
            Date date = new Date();
            Revision revision = new Revision(date, "revision 1.0", "1.0", user, project);
            revisionDAO.add(revision);
            ci = new ConfigurationItem(1, projName, "", 'A', 1, 0, null, null, revision);
            revision.setConfigItem(ci);

        } catch (ObjectNotFoundException e) {
            Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, null, e);
            throw new StorageUserNotFoundException(e);
        }
    }

    protected String hashToPath(String hash) {
        String path;
        path = hash.substring(0, 3) + "/" + hash.substring(3);
        return path;
    }

    protected void storeFiles(VersionedDir father, String projName) throws ApplicationException {
        for (Iterator<VersionedItem> it = father.getContainedItens().iterator(); it.hasNext();) {
            VersionedItem vi = it.next();
            if (vi.getStatus() == EVCSConstants.DELETED || vi.getStatus() == EVCSConstants.UNMODIFIED){
                continue;
            }
            if (vi.isDir()) {
                storeFiles((VersionedDir) vi, projName);
                continue;
            }
            VersionedFile vf = (VersionedFile) vi;
            String projPath = Storage.getDirPath() + projName + "/";
            String dirPath = projPath + vf.getHash().substring(0, 3);
            String filePath = projPath + hashToPath(vf.getHash());
            File dir = new File(dirPath);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(filePath);
            if (file.exists()) {
                continue;
            }
            //criar arquivo
            FileOutputStream fileOuputStream;
            try {
                file.createNewFile();
                fileOuputStream = new FileOutputStream(filePath);
                fileOuputStream.write(vf.getContent());
                fileOuputStream.close();
            } catch (FileNotFoundException ex) {
                //nunca deve entrar aqui já que eu verifiquei antes se o arquivo existia
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
                throw new StorageException("Arquivo "+filePath+" não econtrado",ex);
            }catch (IOException ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
                throw new StorageException(ex);
            }

        }
    }
}
