/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

/**
 *
 * @author jokerfvd
 */
public class RevisionConfigurationItem {
    private RevisionConfigurationItemId id;
    private Revision revision;
    private ConfigurationItem configItem;
    
    private RevisionConfigurationItem(){};
    
    public RevisionConfigurationItem(int revisionId, int configItemId) {
        this.id = new RevisionConfigurationItemId(revisionId, configItemId);
    }

    public RevisionConfigurationItemId getId() {
        return id;
    }

    public void setId(RevisionConfigurationItemId id) {
        this.id = id;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public ConfigurationItem getConfigItem() {
        return configItem;
    }

    public void setConfigItem(ConfigurationItem configItem) {
        this.configItem = configItem;
    }
}
