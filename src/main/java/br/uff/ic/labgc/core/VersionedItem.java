/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * Hash do item versionado
     */
    private String hash;
    /**
     * Tamanho acumulado dos itens contidos neste VersionedItem
     */
    protected int size = 0;
    /**
     * Indica se o item possui conteúdo comprimido. Se for true, utiliza o
     * conteúdo comprimido para trafegar o conteúdo na rede.
     */
    private boolean compressed;

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * gera um hash com 32 chars
     * @param bytes
     * @throws NoSuchAlgorithmException 
     */
    public void generateHash(byte bytes[]) throws NoSuchAlgorithmException{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(bytes);
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
          hashtext = "0"+hashtext;
        }     
        setHash(hashtext);
    }

    /**
     * Retorna o tamanho acumulado de todos os itens contidos neste
     * VersionedItem.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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
    public abstract VersionedItem inflate() throws ContentNotAvailableException, CompressionException;

    /**
     * Comprime o conteúdo deste item.
     *
     * @return item comprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    public abstract VersionedItem deflate() throws ContentNotAvailableException, CompressionException;
}
