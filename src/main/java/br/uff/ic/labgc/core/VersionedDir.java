/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import java.io.Serializable;
import java.util.Collections;
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
    
}
