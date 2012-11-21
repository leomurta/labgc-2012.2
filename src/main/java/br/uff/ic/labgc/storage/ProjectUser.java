/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jokerfvd
 */
public class ProjectUser {
    private ProjectUserId id;
    private Project project;
    private String token;
    private int permission;
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public void generateToken() {
        Date date = new Date();
        int h = 0;
        String aux = date.toString()+project.getName()+user.getPassword()+user.getName();
        int len = aux.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + aux.charAt(i);
        }
        this.token = Integer.toString(h);
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public ProjectUserId getId() {
        return id;
    }

    public void setId(ProjectUserId id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    private User user;

    public ProjectUser(int projectId, int userId) {
        this.id = new ProjectUserId(projectId, userId);
    }

    public ProjectUser() {
    }
    
}
