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
public class ProjectUserId implements Serializable{
    private final static long serialVersionUID = 1;	
    private int projectId;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ProjectUserId(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public ProjectUserId() {
    }
    
}
