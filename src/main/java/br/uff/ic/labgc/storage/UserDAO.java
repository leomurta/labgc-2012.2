/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.*;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author jokerfvd
 */
public class UserDAO extends DAO{

    public User get(int id) {
        return (User)get(id, User.class);
    }
    
    public User getByUserName(String username) throws ObjectNotFoundException{
        try {
            Session sessao = HibernateUtil.getSession();

            Criteria criteria = sessao.createCriteria(User.class)
                .add(Restrictions.eq("username", username));
            User user = (User) criteria.uniqueResult();


            if (user == null) {
                throw new ObjectNotFoundException();
            }

            return user;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public boolean exist(String userName) throws InfrastructureException{
        return exist(User.class,"username",userName);
    }
    
    public List getUsers()
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from LABGC.T_USER order by id").list();
                //return sessao.createSQLQuery("select * from t_user order by id").list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
}
