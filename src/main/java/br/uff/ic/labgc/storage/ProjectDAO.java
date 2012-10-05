/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.HibernateUtil;
import br.uff.ic.labgc.storage.util.InfrastructureException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author jokerfvd
 */
public class ProjectDAO extends DAO{

    public Project getName(String name) {
        try {
            Session sessao = HibernateUtil.getSession();

            Project project = (Project) sessao.createQuery("from Project where name = :name")
                    .setString("name", name).uniqueResult();

            if (project == null) {
                throw new ObjectNotFoundException();
            }

            return project;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public Project get(int id){
        return (Project)get(id, Project.class);
    }

}
