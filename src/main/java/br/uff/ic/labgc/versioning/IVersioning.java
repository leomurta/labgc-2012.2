/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
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
    public List<VersionedItem> getRevision(String revNum, String token) throws ObjectNotFoundException;
    
    public VersionedFile getVersionedFile(String hash, String token) throws IOException;
}
