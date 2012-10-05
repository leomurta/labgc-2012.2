/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.io.Serializable;

/**
 *
 * @author jokerfvd
 */
public class RevisionConfigurationItemId implements Serializable{
    private final static long serialVersionUID = 1;	
    private int revisionId;
    private int configItemId;
    
    private RevisionConfigurationItemId(){};

    public RevisionConfigurationItemId(int revisionId, int configItemId) {
        this.revisionId = revisionId;
        this.configItemId = configItemId;
    }

    public int getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(int revisionId) {
        this.revisionId = revisionId;
    }

    public int getConfigItemId() {
        return configItemId;
    }

    public void setConfigItemId(int configItemId) {
        this.configItemId = configItemId;
    }
}
