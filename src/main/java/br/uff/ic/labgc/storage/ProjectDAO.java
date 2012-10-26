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

    public Project getByName(String name) throws ObjectNotFoundException{
        try {
            Session sessao = HibernateUtil.getSession();
            
            Criteria criteria = sessao.createCriteria(Project.class)
                .add(Restrictions.eq("name", name));
            Project project = (Project) criteria.uniqueResult();

            if (project == null) {
                throw new ObjectNotFoundException();
            }

            return project;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public boolean exist(String name) throws InfrastructureException{
        return exist(Project.class,"name",name);
    }
    
    public Project get(int id){
        return (Project)get(id, Project.class);
    }

}
