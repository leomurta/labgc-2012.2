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
public class User {
    
    private int id;
    private String name;
    private String password;
    private Set revisions = new HashSet();
    private Set projects = new HashSet();
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public void setName(String name){this.name = name;}
    public void setPassword(String password){this.password = password;}
    public void setRevisions(Set revisions){ this.revisions = revisions;}
    public void setProjects(Set projects){ this.projects = projects;}
    
    public int getId(){return id;}
    public String getName(){return name;}
    public Set getRevisions(){return revisions;}
    public Set getProjects(){return projects;}
    
}