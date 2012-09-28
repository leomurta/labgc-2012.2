/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

/**
 *
 * @author jokerfvd
 */
public class ConfigurationItem {

    private int id;
    private int number;
    private String name;
    private String hash;
    private char type; //diz se foi add, delete, update - "A", "D", "U"
    private ConfigurationItem next;
    private ConfigurationItem previous;
    private Revision revision;

    public ConfigurationItem(int number, String name, String hash, char type, ConfigurationItem next, ConfigurationItem previous) {
        this.number = number;
        this.name = name;
        this.hash = hash;
        this.type = type;
        this.next = next;
        this.previous = previous;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
