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
public class UserDAO extends DAO{
    public static final String USERNAME = "username";
    
    public User get(int id) {
        return (User)get(id, User.class);
    }
    
    public User getByUserName(String username) throws ObjectNotFoundException{
        return (User)getBy(User.class, USERNAME, username);
    }
    
    public boolean exist(String userName) throws InfrastructureException{
        return exist(User.class, USERNAME, userName);
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
