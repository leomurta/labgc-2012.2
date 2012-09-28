/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

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
    
    public String getHeadRevision();
    
    //retorna nova versão corrente ou gera exceção
    //public String addRevision(List<ConfigurationItemData> configitens);
    
}
