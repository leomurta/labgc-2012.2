/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.comm.client.CommunicationFactory;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.util.CompressUtils;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa um arquivo versionado.
 *
 * @author Cristiano
 */
public class VersionedFile extends VersionedItem implements Serializable {

    private static final long serialVersionUID = 3529717766546453848L;
    /**
     * Hostname de onde este item se originou.
     */
    private String originHost;
    /**
     * Conteúdo do arquivo. Caso o atributo compressed seja true, o conteúdo
     * está comprimido.
     */
    private byte[] content;
    /**
     * Indica se o conteúdo do item foi carregado. Caso não tenha sido
     * carregado, o primeiro get no conteúdo o buscará no repositório.
     */
    private boolean loaded;
    /**
     * Hash do item versionado
     */
    private String hash;
    
    
    /**
     * Indicaca se o item possui conflito  a ser resolvido
     */
    private boolean conflict;

    /**
     * Cria um item versionável, marcando seu atributo loaded como falso e
     * atribuindo o endereço de origem como sendo o hostname no qual o item está
     * sendo instanciado.
     */
    public VersionedFile() {
        this.loaded = false;
        setCompressed(false);
        this.originHost = ApplicationProperties.getPropertyValue(IPropertiesConstants.HOSTNAME);
    }

    /**
     * Faz o mesmo que o construtor padrao so que seta o hash e size tambem
     *
     * @param hash
     */
    public VersionedFile(String hash, long size) {
        this();
        this.hash = hash;
        this.size = size;
    }

    public VersionedFile(String lastChangedRevision, Date lastChangedTime, String name, String author, String commitMessage) {
        super(lastChangedRevision, lastChangedTime, name, author, commitMessage);
        this.loaded = false;
        setCompressed(false);
        try {
            this.originHost = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
    }

    /**
     * Retorna o conteúdo do arquivo representado por este arquivo versionado.
     * Caso o conteúdo ainda não tenha sido carregado, invoca o servidor para
     * buscá-lo.
     *
     * @return
     * @throws ApplicationException
     */
    public byte[] getContent() throws ApplicationException {
        if (!loaded) {
            if (this.getHash() == null || this.getHash().equals("")) {
                throw new ApplicationException("Não é possível recuperar o conteúdo de um item sem informar seu hash.");
            }
            setContent(CommunicationFactory.getFactory().getServer(originHost).getItemContent(this.getHash()));
        } else if (isCompressed()) {
            inflate();
        }
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.loaded = true;
        setDiff(false);
        setCompressed(false);
        setSize(content.length);
        try {
            generateHash();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(VersionedFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean hasContent(){
        return (this.content != null);
    }
    
    public void releaseContent(){
        this.content = null;
    }
    
    //TODO passar a versão na qual o diff deve ser aplicado
    public void setDiffContent(byte[] content) {
        this.content = content;
        this.loaded = true;
        setDiff(true);
        setCompressed(false);
        setSize(content.length);
        try {
            generateHash();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(VersionedFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getHash() {
        return this.hash;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * gera um hash com 32 chars
     *
     * @param bytes
     * @throws NoSuchAlgorithmException
     */
    private void generateHash() throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(this.content);
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        this.hash = hashtext;
    }

    /**
     * Expande o conteúdo deste item.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    @Override
    public void inflate() throws ContentNotAvailableException, CompressionException {
        if (loaded) {
            setContent(CompressUtils.inflateBytes(this.content));
            setCompressed(false);
        }
    }

    /**
     * Comprime o conteúdo deste item.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    @Override
    public void deflate() throws ContentNotAvailableException, CompressionException {
        if (loaded) {
            content = CompressUtils.deflateBytes(this.content);
            setCompressed(true);
        }
    }

    @Override
    public boolean isDir() {
        return false;
    }

    /**
     * @return the conflict
     */
    public boolean hasConflict() {
        return conflict;
    }

    /**
     * @param conflict the conflict to set
     */
    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }
}
