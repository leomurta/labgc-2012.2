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
public class RevisionDAO {
    public int add(Revision revision) {
        try {
            Session sessao = HibernateUtil.getSession();
            sessao.save(revision);
            return revision.getId();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void update(Revision revision) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.update(revision);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public void remove(Revision revision) {
        try {
            Session sessao = HibernateUtil.getSession();

            sessao.delete(revision);
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public Revision getRevision(int id) {
        try {
            Session sessao = HibernateUtil.getSession();

            Revision revision = (Revision) sessao.get(Revision.class, id);

            if (revision == null) {
                throw new ObjectNotFoundException();
            }

            return revision;
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
    
    public List getRevisions()
	{	
            try
            {
                Session sessao = HibernateUtil.getSession();

                return sessao.createQuery("from T_REVISION order by id").list();
            } catch (HibernateException e) {
                throw new InfrastructureException(e);
            }
	}
}
