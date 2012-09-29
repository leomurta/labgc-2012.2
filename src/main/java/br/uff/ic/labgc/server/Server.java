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
public class Server implements IServer{

    private String repPath;
    private String repHost;
    
    public boolean commit(VersionedItem file, String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem update() {
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

   

    public String getRepPath() {
        return repPath;
    }

    public String getRepHost() {
        return repHost;
    }

    public String login(String user, String pwd) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getItemContent(String hash) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
