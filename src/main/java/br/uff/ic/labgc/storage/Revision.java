/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jokerfvd
 */
public class Revision {

    private int id;
    private Date date;
    private String description;
    private String number;
    private User user;
    private Project project;
    //TODO mudar de Set para VersionedItem
    private Set configItens = new HashSet();

    public Revision(Date date, String description, String number, User user, Project project) {
        this.date = date;
        this.description = description;
        this.number = number;
        this.user = user;
        this.project = project;
    }

    public Set getConfigItens() {
        return configItens;
    }

    public void setConfigItens(Set configItens) {
        this.configItens = configItens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
