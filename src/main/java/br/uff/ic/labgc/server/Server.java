/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ServerException;


/**
 *
 * @author Felipe R
 */
public class Server extends AbstractServer{
    
    String serverTempToken = "zyx";
    
    public Server(String hostName) {
        super(hostName);
    }

    public String commit(VersionedItem file, String token) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem update(String revision, String token) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem checkout(String revision, String token)throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(VersionedItem file, String version) throws ApplicationException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String login(String user, String pwd, String repository)throws ApplicationException {
        setRepPath(repository);
        return serverTempToken;
    }

    public byte[] getItemContent(String hash) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
