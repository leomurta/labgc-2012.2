/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.IObservable;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.WorkspaceException;
import java.util.List;

/**
 *
 * @author victor
 */
public interface IWorkspace extends IObservable{
    
    
    // Método de propriedade
    public void setParam(String key, String value) throws ApplicationException;
    
   /**
     * metodo para pegar o valor de um parametro salvo
     *
     * @param key, chave do parametro salvo
     * @return
     */
    public String getParam(String key)throws ApplicationException;
    
    /**
     * retorna valor do hostname guardado na criacao do workspace
     *
     * @return
     */
    public String getHost()throws ApplicationException; 
    
    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor.
     * Valor adicionado na criacao do workspace
     *
     * @return
     */
    public String getProject()throws ApplicationException; 
    
    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor.
     * Valor adicionado na criacao do workspace
     *
     * @return
     */
    public String getRevision()throws ApplicationException;
        
    public void setRevision(String revision)throws ApplicationException;
    
    
    //Métodos de verificação do WS
    /**
     * verifica a possibilidade de criar um workspace, retorna true ou false
     *
     * @return
     */
    public boolean canCreate();
    
    public boolean isWorkspace();
        /**
     *
     * @param hostname
     * @param repository
     * @param items
     * @throws ApplicationException
     */
    public void createWorkspace(String hostname, String repository, VersionedItem items)throws ApplicationException;
    
    
    //Funcionalidades da WS
    public boolean revert(String file)throws ApplicationException;
    
    public boolean revert()throws ApplicationException;
    
    public boolean release();

    public boolean resolve(String file);
    
    public VersionedItem status()throws ApplicationException;

    public void update(VersionedItem files);

    public VersionedItem commit();
    
    //Não implementado
    public VersionedItem diff(String file, String version);

    
    
   
    
} 
