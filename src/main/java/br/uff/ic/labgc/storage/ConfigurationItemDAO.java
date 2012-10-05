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
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jokerfvd
 */
public class ConfigurationItemDAO extends DAO{

    public ConfigurationItem get(int id) {
        return (ConfigurationItem)super.get(id, ConfigurationItem.class);
    }
    
    public ConfigurationItem getByHash(String hash) {
        try {
            Session sessao = HibernateUtil.getSession();

            ConfigurationItem ci = (ConfigurationItem)sessao
                    .createQuery("from ConfigurationItem where hash = :hash")
                    .setString("hash", hash).uniqueResult();

            if (ci == null) {
                throw new ObjectNotFoundException();
            }

            return ci;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

     public List getConfigurationItemsFromRevision(int revisionId)
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from ConfigurationItem ci,RevisionConfigurationItem "
                        + "where ci_id = ci.id AND revision_id = :revisionId")
                        .setInteger("revisionId", revisionId).list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
     
}
