package br.uff.ic.labgc.storage.util;

import java.util.List;

public class ApplicationException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	private int codigo;
	private List mensagens;
	
	public ApplicationException()
	{
	}

	public ApplicationException(String msg)
	{	super(msg);
	}

	public ApplicationException(List mensagens)
	{	this.mensagens = mensagens;
	}

	public ApplicationException(int codigo, String msg)
	{	super(msg);
		this.codigo = codigo;
	}
	
	public ApplicationException(Exception e)
	{	super(e);
	}
	
	public int getCodigoDeErro()
	{	return codigo;
	}

	public List getMensagens()
	{	return mensagens;
	}
}	