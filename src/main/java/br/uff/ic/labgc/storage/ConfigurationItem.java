/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jokerfvd
 */
public class ConfigurationItem extends DBClass{

    private int number;
    private String name;
    //TODO DUVAL o hash deve ser do caminho do repo mais do conteudo do arquivo 
    private String hash;
    private char type; //diz se foi add, delete, update - "A", "D", "U"
    private ConfigurationItem next;
    private ConfigurationItem previous;
    private Set revisions = new HashSet();

    public ConfigurationItem() {
    }
    
    public ConfigurationItem(int number, String name, String hash, char type, ConfigurationItem next, ConfigurationItem previous) {
        this.number = number;
        this.name = name;
        this.hash = hash;
        this.type = type;
        this.next = next;
        this.previous = previous;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public ConfigurationItem getNext() {
        return next;
    }

    public void setNext(ConfigurationItem next) {
        this.next = next;
    }

    public ConfigurationItem getPrevious() {
        return previous;
    }

    public void setPrevious(ConfigurationItem previous) {
        this.previous = previous;
    }

    public Set getRevisions() {
        return revisions;
    }

    public void setRevisions(Set revisions) {
        this.revisions = revisions;
    }
    
}
