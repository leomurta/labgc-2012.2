/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.labgc.storage;

import java.util.List;

/**
 *
 * @author jokerfvd
 */
public interface IDAO {
    
    //TODO DUVAL usar classe generica java
    public <T> int add(DBClass obj);
    public <T> void remove(DBClass obj);
    public <T> void update(DBClass obj);
    public Object get(int id, Class type);
    //public <T> List<T> getAll();
}
