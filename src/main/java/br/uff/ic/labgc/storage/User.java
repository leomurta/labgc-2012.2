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
/*    
    private Set revisions = new HashSet();
    private Set projects = new HashSet();
*/    
   //estou passando o id pq esta dando erro no hibernate com o derby 
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setPassword(String password){this.password = password;}
    //public void setRevisions(Set revisions){ this.revisions = revisions;}
    //public void setProjects(Set projects){ this.projects = projects;}
    
    public int getId(){return id;}
    public String getName(){return name;}
    public String getPassword(){return password;}
    //public Set getRevisions(){return revisions;}
    //public Set getProjects(){return projects;}
    
}