/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Representa um diretório versionado.
 *
 * @author Cristiano
 */
public class VersionedDir extends VersionedItem implements Serializable {
    private static final long serialVersionUID = 6077816301367671740L;
    
    private List<VersionedItem> containedItens = Collections.EMPTY_LIST;

    public List<VersionedItem> getContainedItens() {
        return containedItens;
    }

    public void setContainedItens(List<VersionedItem> containedItens) {
        this.containedItens = containedItens;
    }
    
    public void addItem(VersionedItem item) {
        this.containedItens.add(item);
    }
    
    /**
     * Expande o conteúdo de todos os itens contidos neste diretório.
     * @return item descomprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException 
     */
    @Override
    public VersionedItem inflate() throws ContentNotAvailableException, CompressionException {
        for (Iterator<VersionedItem> it = containedItens.iterator(); it.hasNext();) {
            VersionedItem versionedItem = it.next();
            versionedItem.inflate();
        }
        return this;
    }

    /**
     * Comprime o conteúdo de todos os itens contidos neste diretório.
     * @return item comprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException 
     */
    @Override
    public VersionedItem deflate() throws ContentNotAvailableException, CompressionException {
        for (Iterator<VersionedItem> it = containedItens.iterator(); it.hasNext();) {
            VersionedItem versionedItem = it.next();
            versionedItem.deflate();
        }
        return this;
    }
}
