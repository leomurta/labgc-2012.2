/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import java.io.Serializable;
import java.util.List;

/**
 * Representa um arquivo versionado.
 *
 * @author Cristiano
 */
public class VersionedFile extends VersionedItem implements Serializable {
    private static final long serialVersionUID = 3529717766546453848L;
    //TODO CRISTIANO size vai passar para o VersionedItem. no caso de diretórios, terá o tamanho da sub-árvore.
    /**
     * Tamanho do arquivo, em bytes
     */
    private long size = 0L;
    
    /**
     * Conteúdo do arquivo
     */
    private byte[] content;
    
    /**
     * Indica se o conteúdo do item foi carregado. Caso não tenha sido carregado, o primeiro get no conteúdo o buscará no repositório.
     */
    private boolean loaded;
    
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
    
}
