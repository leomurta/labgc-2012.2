package br.uff.ic.labgc.storage.util;

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
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("OIOIOI");
	}

	public static Session getSession() 
	{	Session s = (Session) threadSession.get();
		// Abre uma nova Sess�o, se a thread ainda n�o possui uma.
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