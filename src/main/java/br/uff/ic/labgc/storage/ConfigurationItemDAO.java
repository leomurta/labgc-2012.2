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
public class ConfigurationItemDAO {
    public int add(ConfigurationItem configItem) {
        try {
            Session sessao = HibernateUtil.getSession();
            sessao.save(configItem);
            return configItem.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void update(ConfigurationItem configItem) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(configItem);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(ConfigurationItem configItem) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(configItem);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public ConfigurationItem getConfigurationItem(int id) {
        try {
            Session sessao = HibernateUtil.getSession();

            ConfigurationItem configItem = (ConfigurationItem) sessao.get(ConfigurationItem.class, id);

            if (configItem == null) {
                throw new ObjectNotFoundException();
            }

            return configItem;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public List getConfigurationItems()
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from T_CONFIGURATION_ITEM order by id").list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
    
     public List getConfigurationItemsFromRevision(int revisionId)
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from T_CONFIGURATION_ITEM where revision_id = :revisionId")
                        .setInteger("revisionId", revisionId).list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
}
