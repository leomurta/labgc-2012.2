/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.IObservable;
import br.uff.ic.labgc.core.VersionedItem;

/**
 *
 * @author victor
 */
public interface IWorkspace extends IObservable{
    
    
    // Método de propriedade
    public void setParam(String key, String value);
   
    public String getParam(String key);
    
    public String getHost(); 
    
    public String getProject(); 
    
    public String getRevision();
        
    public void setRevision(String revision);
    
    
    //Métodos de verificação do WS
    public boolean canCreate();
    
    public boolean isWorkspace();
    
    public void createWorkspace(String hostname, String repository, VersionedItem items);
    
    
    //Funcionalidades da WS
    public boolean revert(String file);
    
    public boolean revert();
    
    public boolean release();

    public boolean resolve(String file);
    
    public VersionedItem status();

    public void update(VersionedItem files);

    public VersionedItem commit();
    
    //Não implementado
    public VersionedItem diff(String file, String version);

    
    
   
    
} 
