/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    private int dir;
    private long size;
    private ConfigurationItem next;
    private ConfigurationItem previous;
    private Revision revision;
    private Set children = new HashSet();

    public ConfigurationItem() {
    }
    
    public ConfigurationItem(int number, String name, String hash, char type, int dir, long size, ConfigurationItem next, ConfigurationItem previous, Revision revision) {
        this.number = number;
        this.name = name;
        this.hash = hash;
        this.type = type;
        this.dir = dir;
        this.size = size;
        this.next = next;
        this.previous = previous;
        this.revision = revision;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public Set getChildren() {
        return children;
    }

    public void setChildren(Set children) {
        this.children = children;
    }
    
    public void addChild(ConfigurationItem child) {
        this.children.add(child);
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
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
    
    public void removeFKs(){
        ConfigurationItem previous = this.getPrevious();
        ConfigurationItem next = this.getNext();
        if (previous != null){
            previous.setNext(null);
        }
        if (next != null){
            next.setPrevious(null);
        }
        if (type != 'U'){// diferente de unmodified
            for (Iterator it = children.iterator(); it.hasNext(); ){
                ConfigurationItem ci = (ConfigurationItem)it.next();
                    ci.removeFKs();
            }
        }
        else{
            //fazedo isso para o revemo não remover esses filhos
            setChildren(null);
        }
    }
    
}
