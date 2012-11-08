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
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author jokerfvd
 */
public class ProjectUserDAO {

    public ProjectUserDAO() {
    }

    public ProjectUserId add(ProjectUser projectUser) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            sessao.save(projectUser);
            HibernateUtil.commitTransaction();

            return projectUser.getId();
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }

    public void update(ProjectUser projectUser) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            sessao.update(projectUser);
            HibernateUtil.commitTransaction();
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }

    public void remove(ProjectUserId projectUserId) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            ProjectUser pu = (ProjectUser) sessao.get(ProjectUser.class, projectUserId);
            //  A execução do método get() acima não provoca um acesso a 
            //  banco de dados, uma vez que o objeto já se encontra no
            //  primeiro nível de cache: a sessão.  

            sessao.delete(pu);
            HibernateUtil.commitTransaction();
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }

    public void remove(ProjectUser pu) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            sessao.delete(pu);
            HibernateUtil.commitTransaction();
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }

    public ProjectUser get(ProjectUserId projectUserId) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            ProjectUser pu = (ProjectUser) sessao.get(ProjectUser.class, projectUserId);
            HibernateUtil.commitTransaction();

            if (pu == null) {
                throw new ObjectNotFoundException();
            }

            return pu;
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }
    
    public ProjectUser get(int userId, int projId) {
        ProjectUserId pui = new ProjectUserId(userId, projId);
        ProjectUser pu = get(pui);
        return pu;
    }
    
     public ProjectUser getByToken(String token) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();
            
            Criteria criteria = sessao.createCriteria(ProjectUser.class)
                .add(Restrictions.eq("token", token));
            ProjectUser pu = (ProjectUser) criteria.uniqueResult();
            HibernateUtil.commitTransaction();

            if (pu == null) {
                throw new ObjectNotFoundException();
            }

            return pu;
        } catch (HibernateException e) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (InfrastructureException ie) {
            }

            throw e;
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (InfrastructureException he) {
                throw he;
            }
        }
    }
}
