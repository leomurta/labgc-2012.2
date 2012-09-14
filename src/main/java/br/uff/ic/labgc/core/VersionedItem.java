/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

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
    private int lastChangedRevision;
    
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
     * Hash do item versionado
     */
    private String hash;
    
    public int getLastChangedRevision() {
        return lastChangedRevision;
    }

    public void setLastChangedRevision(int lastChangedRevision) {
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public String generateHash() {
        this.hash = UUID.randomUUID().toString();
        return this.hash;
    }
}
