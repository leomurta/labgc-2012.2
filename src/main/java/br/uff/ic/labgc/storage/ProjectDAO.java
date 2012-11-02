/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.HibernateUtil;
import br.uff.ic.labgc.storage.util.InfrastructureException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author jokerfvd
 */
public class ProjectDAO extends DAO{
    public static final String NAME = "name";

    public Project getByName(String name) throws ObjectNotFoundException{
        return (Project)getBy(Project.class, NAME, name);
    }
    
    public boolean exist(String name) throws InfrastructureException{
        return exist(Project.class, NAME, name);
    }
    
    public Project get(int id){
        return (Project)get(id, Project.class);
    }

}
