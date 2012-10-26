/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.exception.VersioningCanNotCreateDirException;
import br.uff.ic.labgc.exception.VersioningProjectAlreadyExistException;
import br.uff.ic.labgc.exception.VersioningUserNotFoundException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author jokerfvd
 */
public interface IVersioning {
    
    //public RevisionData getRevisionInfo(String number);
    
    //public List<ConfigurationItemData> getRevision(String number);
    
    //por enquanto User só tem nome, mas pode ter outras coisas
    public String getRevisionUserName(String number);
    
    //public List<ConfigurationItemData> getAllConfigurationItemVersions(String name);
    
    //public ConfigurationItemData getConfigurationItem(String name, String version);
    
    //public ConfigurationItemData getConfigurationItemById(String name);
    
    /**
     * Verifica o token de um usuário e pega a revision do projeto associado a
     * esse token. Os VersionedItem vem sem content
     * @param revision
     * @param token
     * @return 
     */
    public VersionedDir getRevision(String revNum, String token) throws ObjectNotFoundException;
    
    public String login(String projectName, String userName, String pass) throws ObjectNotFoundException, IncorrectPasswordException;
    
    public byte[] getVersionedFileContent(String hash, String token) throws IOException;
    
    public void addRevision(VersionedDir vd, String token);
    
    /**
     * realiza o import. Nao dava pra usar o nome import
     * @param vd
     * @param project
     * @param user 
     */
    public void addProject(VersionedDir vd, String userName) throws 
            VersioningProjectAlreadyExistException,VersioningUserNotFoundException,
            VersioningCanNotCreateDirException;
}
