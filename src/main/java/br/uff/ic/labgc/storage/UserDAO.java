/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.*;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jokerfvd
 */
public class UserDAO extends DAO{

    public User get(int id) {
        return (User)get(id, User.class);
    }
    
    public User getByUserName(String username) {
        try {
            Session sessao = HibernateUtil.getSession();

            String busca = "from User where username = :username";
            Query query = sessao.createQuery(busca);
            query.setString("username", username);
            User user = (User) query.uniqueResult();

            if (user == null) {
                throw new ObjectNotFoundException();
            }

            return user;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public List getUsers()
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from User order by id").list();
                //return sessao.createSQLQuery("select * from t_user order by id").list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
}
