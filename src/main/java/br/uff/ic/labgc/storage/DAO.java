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
public class DAO implements IDAO {

    @Override
    public <T> int add(DBClass obj) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();
            sessao.save(obj);
            HibernateUtil.commitTransaction();
            return obj.getId();
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

    @Override
    public <T> void remove(DBClass obj) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();
            sessao.delete(obj);
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

    @Override
    public <T> void update(DBClass obj) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();
            sessao.update(obj);
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

    @Override
    public Object get(int id, Class type) {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            Object obj = sessao.get(type, id);
            HibernateUtil.commitTransaction();

            if (obj == null) {
                throw new ObjectNotFoundException();
            }

            return obj;
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

    public Object getBy(Class objClass, String searchBy, String searchValue)
            throws ObjectNotFoundException {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            Criteria criteria = sessao.createCriteria(objClass)
                    .add(Restrictions.eq(searchBy, searchValue));
            Object obj = criteria.uniqueResult();
            HibernateUtil.commitTransaction();

            if (obj == null) {
                throw new ObjectNotFoundException();
            }

            return obj;
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

    public boolean exist(Class objClass, String searchBy, String searchValue)
            throws ObjectNotFoundException {
        try {
            HibernateUtil.beginTransaction();
            Session sessao = HibernateUtil.getSession();

            Criteria criteria = sessao.createCriteria(objClass)
                    .add(Restrictions.eq(searchBy, searchValue));
            Object obj = criteria.uniqueResult();
            HibernateUtil.commitTransaction();

            return (obj != null);
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

    /*    
     @Override
     public <T> List<T> getAll() {
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
     */
}
