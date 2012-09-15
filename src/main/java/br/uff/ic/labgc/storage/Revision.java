/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.ConfigurationItem.ConfigurationItemData;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author jokerfvd
 */
public class Revision implements IStorage{

    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update() {
    throw new UnsupportedOperationException("Not supported yet.");
    }
    public class RevisionData{
        private int id;
        private double timestamp;
        private String description;
        private String number;
    }
    private RevisionData data;
    private List<ConfigurationItem.ConfigurationItemData> configItens;
    
    
    public void setTimestamp(double timestamp){this.data.timestamp = timestamp;}
    public void setDescription(String description){this.data.description = description;}
    public void setNumber(String number){this.data.number = number;}
    
    public void setConfigurationItens(List<ConfigurationItemData> configItens){
        this.configItens.clear();
        Iterator<ConfigurationItemData> it = configItens.iterator();
        while (it.hasNext()) {
            this.configItens.add(it.next());
	}
    }
    
    public double getId(){return this.data.id;}
    public double getTimestamp(){return this.data.timestamp;}
    public String getDescription(){return this.data.description;}
    public String getNumber(){return this.data.number;}
}
