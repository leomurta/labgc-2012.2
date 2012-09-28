package br.uff.ic.labgc.storage.util;

public class InfrastructureException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public InfrastructureException(Exception e)
	{	super(e);
	}
}	