/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Representa um diretório versionado.
 *
 * @author Cristiano
 */
public class VersionedDir extends VersionedItem implements Serializable {
    private static final long serialVersionUID = 6077816301367671740L;
    
    private List<VersionedItem> containedItens = new ArrayList<VersionedItem>();

    public List<VersionedItem> getContainedItens() {
        return containedItens;
    }
    
    public VersionedDir() {
        super();
    }

    public VersionedDir(long size) {
        this();
        this.size = size;
    }
    
    public VersionedDir(String lastChangedRevision, Date lastChangedTime, String name, String author, String commitMessage) {
        super(lastChangedRevision, lastChangedTime, name, author, commitMessage);   
    }

    public void setContainedItens(List<VersionedItem> containedItens) {
        this.containedItens = containedItens;
    }
    
    public void addItem(VersionedItem item) {
        this.containedItens.add(item);
        setSize(this.size+item.getSize());
        
    }
    public void addItem(List<VersionedItem> items) {
        for(VersionedItem item:items)
            this.addItem(item);        
    }
    
    /**
     * Expande o conteúdo de todos os itens contidos neste diretório.
     * @return item descomprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException 
     */
    @Override
    public void inflate() throws ContentNotAvailableException, CompressionException {
        for (Iterator<VersionedItem> it = containedItens.iterator(); it.hasNext();) {
            VersionedItem versionedItem = it.next();
            versionedItem.inflate();
        }
    }

    /**
     * Comprime o conteúdo de todos os itens contidos neste diretório.
     * @return item comprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException 
     */
    @Override
    public void deflate() throws ContentNotAvailableException, CompressionException {
        for (Iterator<VersionedItem> it = containedItens.iterator(); it.hasNext();) {
            VersionedItem versionedItem = it.next();
            versionedItem.deflate();
        }
    }

    @Override
    public boolean isDir() {
        return true;
    }
}
