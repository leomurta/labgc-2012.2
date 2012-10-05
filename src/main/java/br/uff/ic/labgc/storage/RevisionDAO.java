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
public class RevisionDAO extends DAO{

    public Revision get(int id) {
        return (Revision)get(id, Revision.class);
    }
    
    public Revision getByProjectAndNumber(int projId, String number) {
        try {
            Session sessao = HibernateUtil.getSession();

            Revision revision = (Revision) sessao.createQuery("from Revision "
                    + "where project_id = :projId and number = :number")
                    .setInteger("projId", projId)
                    .setString("number", number).uniqueResult();

            if (revision == null) {
                throw new ObjectNotFoundException();
            }

            return revision;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
}
