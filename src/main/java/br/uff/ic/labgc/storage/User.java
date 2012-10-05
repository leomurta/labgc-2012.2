/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jokerfvd
 */
public class User extends DBClass{

    public Set getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(Set userProjects) {
        this.userProjects = userProjects;
    }
    private String name;
    private String username;
    private String password;
    private Set revisions = new HashSet();
    private Set userProjects = new HashSet();

    public Set getRevisions() {
        return revisions;
    }

    public void setRevisions(Set revisions) {
        this.revisions = revisions;
    }

    public User() {
    }
    
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //public void setRevisions(Set revisions){ this.revisions = revisions;}
    //public void setProjects(Set projects){ this.projects = projects;}

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    //public Set getRevisions(){return revisions;}
    //public Set getProjects(){return projects;}
}