/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

/**
 *
 * @author jokerfvd
 */
public class ProjectUser {
    private ProjectUserId id;
    private Project project;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public void generateToken() {
        this.token = user.getPassword();
    }
    int 
            permission;

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
