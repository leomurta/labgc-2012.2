/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.Iterator;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;


/**
 *
 * @author jokerfvd
 */
public class User implements IStorage{
    
    private int id;
    private String name;
    
    private List<ConfigurationItem> revisions;
    
    public User(String name) {
        this.name = name;
    }
    
    public void setName(String name){this.name = name;}
    public void setRevisions(List<ConfigurationItem> revisions){
        this.revisions.clear();
        Iterator<ConfigurationItem> it = revisions.iterator();
        while (it.hasNext()) {
            this.revisions.add(it.next());
	}
    }
    
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public List<ConfigurationItem> getRevisions(){return this.revisions;}

    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.save(this);
        session.close();
    }

    public void remove() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.save(this);
        session.close();
    }

    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
