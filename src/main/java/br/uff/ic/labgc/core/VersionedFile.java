/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.comm.client.CommunicationFactory;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import br.uff.ic.labgc.util.CompressUtils;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
     * Cria um item versionável, marcando seu atributo loaded como falso e
     * atribuindo o endereço de origem como sendo o hostname no qual o item está
     * sendo instanciado.
     */
    public VersionedFile() {
        super();
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
        if (this.getHash() == null || this.getHash().equals("")) {
            throw new ApplicationException("Não é possível recuperar o conteúdo de um item sem informar seu hash.");
        }
        if (!loaded) {
            setContent(CommunicationFactory.getFactory().getServer(originHost).getItemContent(this.getHash()));
        } else if (isCompressed()) {
            inflate();
        }
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.loaded = true;
        setCompressed(false);
    }

    /**
     * Expande o conteúdo deste item.
     *
     * @return item descomprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    @Override
    public VersionedItem inflate() throws ContentNotAvailableException, CompressionException {
        if (loaded) {
            setContent(CompressUtils.inflateBytes(this.content));
            setCompressed(false);
        }
        return this;
    }

    /**
     * Comprime o conteúdo deste item.
     *
     * @return item comprimido.
     *
     * @throws ContentNotAvailableException
     * @throws CompressionException
     */
    @Override
    public VersionedItem deflate() throws ContentNotAvailableException, CompressionException {
        if (loaded) {
            setContent(CompressUtils.deflateBytes(this.content));
            setCompressed(true);
        }
        return this;
    }
}
