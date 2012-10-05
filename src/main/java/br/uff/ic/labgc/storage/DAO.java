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
public class DAO implements IDAO{

    @Override
    public <T> int add(DBClass obj) {
        try {
            Session sessao = HibernateUtil.getSession();
            sessao.save(obj);
            return obj.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public <T> void remove(DBClass obj) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(obj);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public <T> void update(DBClass obj) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(obj);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Object get(int id, Class type) {
        try {
            Session sessao = HibernateUtil.getSession();

            Object obj = sessao.get(type, id);

            if (obj == null) {
                throw new ObjectNotFoundException();
            }

            return obj;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
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
