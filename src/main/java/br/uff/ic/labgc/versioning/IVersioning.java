/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

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
    public VersionedDir getRevision(String revNum, String token) throws VersioningException;
    
    public VersionedDir updateRevision(String revNum, String revTo, String token) throws VersioningException;
    
    public String login(String projectName, String userName, String pass) throws VersioningException;
    
    public byte[] getVersionedFileContent(String hash, String token) throws VersioningException;
    
    public List<VersionedDir> getLastLogs(int num, String token);
    
    public String addRevision(VersionedDir vd, String token) throws ApplicationException;
    
    /**
     * realiza o import. Nao dava pra usar o nome import
     * @param vd
     * @param project
     * @param user 
     */
    public void addFirstRevision(VersionedDir vd, String userName) throws ApplicationException;
}
