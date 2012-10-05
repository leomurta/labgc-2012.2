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
import org.hibernate.LockMode;
import org.hibernate.Session;

/**
 *
 * @author jokerfvd
 */
public class ProjectUserDAO {

    public ProjectUserDAO() {
    }

    public ProjectUserId add(ProjectUser projectUser) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.save(projectUser);

            return projectUser.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void update(ProjectUser projectUser) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(projectUser);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(ProjectUserId projectUserId) {
        try {
            Session sessao = HibernateUtil.getSession();

            ProjectUser pu = (ProjectUser) sessao.get(ProjectUser.class, projectUserId);
            //  A execução do método get() acima não provoca um acesso a 
            //  banco de dados, uma vez que o objeto já se encontra no
            //  primeiro nível de cache: a sessão.  

            sessao.delete(pu);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(ProjectUser pu) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(pu);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public ProjectUser get(ProjectUserId projectUserId) {
        try {
            Session sessao = HibernateUtil.getSession();

            ProjectUser pu = (ProjectUser) sessao.get(ProjectUser.class, projectUserId);

            if (pu == null) {
                throw new ObjectNotFoundException();
            }

            return pu;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public ProjectUser get(int userId, int projId) {
        ProjectUserId pui = new ProjectUserId(userId, projId);
        ProjectUser pu = get(pui);
        return pu;
    }
    
     public ProjectUser getByToken(String token) {
        try {
            Session sessao = HibernateUtil.getSession();

            ProjectUser pu = (ProjectUser) sessao.createQuery("from ProjectUser where token = :token")
                    .setString("token", token).uniqueResult();

            if (pu == null) {
                throw new ObjectNotFoundException();
            }

            return pu;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public List getProjectUsers() {
        try {
            Session sessao = HibernateUtil.getSession();

            List produtoCategorias = sessao.createQuery(
                    "from ProjectUser as pu order by id asc").list();
            return produtoCategorias;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
}
