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
public class ProjectDAO {
    public int add(Project project) {
        try {
            Session sessao = HibernateUtil.getSession();
            sessao.save(project);
            return project.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void update(Project project) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(project);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(Project project) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(project);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public Project getProject(int id) {
        try {
            Session sessao = HibernateUtil.getSession();

            Project project = (Project) sessao.get(Project.class, id);

            if (project == null) {
                throw new ObjectNotFoundException();
            }

            return project;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public List getProjects()
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from T_PROJECT order by id").list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
}
