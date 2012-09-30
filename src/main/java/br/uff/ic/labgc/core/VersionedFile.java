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
     * Conteúdo do arquivo
     */
    private byte[] content;
    
    /**
     * Indica se o conteúdo do item foi carregado. Caso não tenha sido carregado, o primeiro get no conteúdo o buscará no repositório.
     */
    private boolean loaded;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
    
}
