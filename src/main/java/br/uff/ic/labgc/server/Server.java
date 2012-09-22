/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ServerException;
import java.io.File;
import java.util.List;

/**
 *
 * @author Felipe R
 */
public class Server implements IServer{

    private String repPath;
    private String repHost;
    
    public boolean commit(List<File> file, String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<VersionedItem> checkout(int revision, String token)throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(File file, String version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void registerRepository(String repHost, String repName) {
        this.repHost = repHost;
        this.repPath = repName;
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

    
    
}
