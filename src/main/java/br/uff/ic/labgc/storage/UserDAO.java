/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.*;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author jokerfvd
 */
public class UserDAO {

    public int add(User user) {
        try {
            Session sessao = HibernateUtil.getSession();
            sessao.save(user);
            return user.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void update(User user) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(user);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(User user) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(user);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public User getUser(int id) {
        try {
            Session sessao = HibernateUtil.getSession();

            User user = (User) sessao.get(User.class, id);

            if (user == null) {
                throw new ObjectNotFoundException();
            }

            return user;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public List getUsers()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from User order by id").list();
		}
		catch(HibernateException e)
		{	throw new InfrastructureException(e);
		}
	}
}
