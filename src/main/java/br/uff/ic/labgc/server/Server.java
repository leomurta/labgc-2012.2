/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ServerException;


/**
 *
 * @author Felipe R
 */
public class Server extends AbstractServer{
    public Server(String hostName) {
        super(hostName);
    }

    public String commit(VersionedItem file, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem update(String revision, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem checkout(String revision, String token)throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(VersionedItem file, String version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String login(String user, String pwd, String repository)throws ServerException {
        setRepPath(repository);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getItemContent(String hash) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
