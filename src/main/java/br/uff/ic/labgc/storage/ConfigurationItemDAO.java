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

/*    
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
  */
  
    //pior caso, 2 arquivos com mesmo nome, mesmo hash e do mesmo projeto, mas em 
    //locais diferentes.
    //seriam diferenciados pelo pai
    public ConfigurationItem getByValuesAnParent(String name, String hash, int parentId, int isDir) {
        try {
            Session sessao = HibernateUtil.getSession();
            String hashQuery = (hash == null)?"":"hash = '"+hash+"' and ";
            

            ConfigurationItem ci = (ConfigurationItem)sessao
                    .createSQLQuery("select labgc.t_configuration_item.* from labgc.t_configuration_item , labgc.t_children where "
                    + "parent_id = :parentId and id = child_id and name = :name and "
                    + hashQuery+"is_dir = :isDir")
                    .addEntity(ConfigurationItem.class)
                    .setInteger("parentId", parentId)
                    .setString("name", name)
                    .setInteger("isDir", isDir).uniqueResult();

            if (ci == null) {
                throw new ObjectNotFoundException();
            }

            return ci;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
        
    }
     
}
