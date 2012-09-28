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
public class Project{
    private int id;
    private String name;
    private Set projectUsers = new HashSet();

    public Set getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(Set projectUsers) {
        this.projectUsers = projectUsers;
    }

    public Project(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
