/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

/**
 *
 * @author jokerfvd
 */
public class ConfigurationItem implements IStorage{
    
    public class ConfigurationItemData{
        private int id;
        private String name ;
        private String hash;
        private char type; //diz se foi add, delete, update - "A", "D", "U"
        private int next;
        private int previous;
    }
    
    private ConfigurationItemData data;
    
    public void setName(String name){this.data.name = name;}
    public void setHash(String hash){this.data.hash = hash;}
    public void setType(char type){this.data.type = type;}
    
    public int getId(){return this.data.id;}
    public String getName(){return this.data.name;}
    public String getHash(){return this.data.hash;}
    public char getType(){return this.data.type;}
    

    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
