/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import br.uff.ic.labgc.comm.client.CommunicationFactory;
import br.uff.ic.labgc.exception.ApplicationException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Representa um arquivo versionado.
 *
 * @author Cristiano
 */
public class VersionedFile extends VersionedItem implements Serializable {

    private static final long serialVersionUID = 3529717766546453848L;
    private String originHost;

    /**
     * Cria um item versionável, marcando seu atributo loaded como falso e 
     * atribuindo o endereço de origem como sendo o hostname no qual o item está
     * sendo instanciado.
     */
    public VersionedFile() {
        super();
        this.loaded = false;
        try {
            this.originHost = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
    }
    
    /**
     * Conteúdo do arquivo
     */
    private byte[] content;
    /**
     * Indica se o conteúdo do item foi carregado. Caso não tenha sido
     * carregado, o primeiro get no conteúdo o buscará no repositório.
     */
    private boolean loaded;

    /**
     * Retorna o conteúdo do arquivo representado por este arquivo versionado.
     * Caso o conteúdo ainda não tenha sido carregado, invoca o servidor para
     * buscá-lo.
     * @return
     * @throws ApplicationException 
     */
    public byte[] getContent() throws ApplicationException {
        if (loaded) {
            return content;
        } else {
            return CommunicationFactory.getFactory().getServer(originHost).getItemContent(this.getHash());
        }
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.loaded = true;
    }
}
