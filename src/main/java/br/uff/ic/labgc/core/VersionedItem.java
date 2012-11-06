/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import java.io.Serializable;
import java.util.Date;

/**
 * Representa um item versionado do sistema de controle de versões.
 *
 * @author Cristiano
 */
public abstract class VersionedItem implements Serializable {

    private static final long serialVersionUID = 8608395561823655106L;
    /**
     * Número da última revisão em que o item foi alterado.
     */
    private String lastChangedRevision;
    /**
     * Data e hora da última alteração no item.
     */
    private Date lastChangedTime;
    /**
     * Nome do item
     */
    private String name;
    /**
     * Nome do autor da última alteração no item.
     */
    private String author;
    /**
     * Mensagem de commit da última alteração no item.
     */
    private String commitMessage;
    /**
     * Tamanho acumulado dos itens contidos neste VersionedItem
     */
    protected long size = 0L;
    /**
     * Indica se o item possui conteúdo comprimido. Se for true, utiliza o
     * conteúdo comprimido para trafegar o conteúdo na rede.
     */
    private boolean compressed;
    
     
    /**
     * Status do VersionedItem
    */
    private int status;
    
    /**
     * Indica se o conteudo do item é um diff.
     */
    private boolean diff;
    
    public boolean isDiff() {
        return diff;
    }

    public void setDiff(boolean diff) {
        this.diff = diff;
    }

    protected boolean isCompressed() {
        return compressed;
    }

    protected void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getLastChangedRevision() {
        return lastChangedRevision;
    }

    public void setLastChangedRevision(String lastChangedRevision) {
        this.lastChangedRevision = lastChangedRevision;
    }

    public Date getLastChangedTime() {
        return lastChangedTime;
    }

    public void setLastChangedTime(Date lastChangedTime) {
        this.lastChangedTime = lastChangedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }
    
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Retorna o tamanho acumulado de todos os itens contidos neste
     * VersionedItem.
     *
     * @return
     */
    public long getSize() {
        return size;
    }

    protected void setSize(long size) {
        this.size = size;
    }
    

    /**
     * Expande o conteúdo deste item.
     *
     * @return item descomprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    public abstract void inflate() throws ContentNotAvailableException, CompressionException;

    /**
     * Comprime o conteúdo deste item.
     *
     * @return item comprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    public abstract void deflate() throws ContentNotAvailableException, CompressionException;
    
    /**
     * Informa se um VersionedItem é um diretorio
     * @return true para diretorio e false para arquivo
     */
    public abstract boolean isDir();
}
