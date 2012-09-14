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
    
    /**
     * Tamanho do arquivo, em bytes
     */
    private long size = 0L;
    
    /**
     * Conteúdo do arquivo
     */
    private byte[] content;
    
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
