package br.uff.ic.labgc.storage.util;

import br.uff.ic.labgc.core.EVCSConstants;
import java.io.File;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil 
{	private static final SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	static 
	{	
            switch (EVCSConstants.BDTYPE){
                case EVCSConstants.DERBY:
                    sessionFactory = new Configuration().configure("derby.hibernate.cfg.xml").buildSessionFactory();
                    break;
                case EVCSConstants.HSQLDB:
                    sessionFactory = new Configuration().configure("hsqldb.hibernate.cfg.xml").buildSessionFactory();
                    break;
                case EVCSConstants.POSTGRES:    
                default:
                    sessionFactory = new Configuration().configure("postgres.hibernate.cfg.xml").buildSessionFactory();
            }
	}

	public static Session getSession() 
	{	Session s = (Session) threadSession.get();
		// Abre uma nova Sessao, se a thread ainda nao possui uma.
		try 
		{	if (s == null) 
			{	s = sessionFactory.openSession();
				threadSession.set(s);
				//System.out.println("criou sessao");
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfrastructureException(ex);
		}
		return s;
	}

	public static void closeSession() 
	{	//System.out.println("fechou sessao");
		try 
		{	Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen())
				s.close();
		} 	
		catch (HibernateException ex) 
		{	throw new InfrastructureException(ex);
		}
	}

	public static void beginTransaction() 
	{	//System.out.println("vai criar transacao");
		Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	if (tx == null) 
			{	tx = getSession().beginTransaction();
				threadTransaction.set(tx);
				//System.out.println("criou transacao");
			}
			else
			{	//System.out.println("nao criou transacao");
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfrastructureException(ex);
		}
	}

	public static void commitTransaction() 
	{	Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() )
			{	tx.commit();
				//System.out.println("comitou transacao");
			}
			threadTransaction.set(null);
		} 
		catch (HibernateException ex) 
		{	rollbackTransaction();
			throw new InfrastructureException(ex);
		}
	}

	public static void rollbackTransaction() 
	{	//System.out.println("rollback de transacao");
	
		Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	threadTransaction.set(null);
			if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) 
			{	tx.rollback();
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfrastructureException(ex);
		} 
		finally 
		{	closeSession();
		}
	}
}