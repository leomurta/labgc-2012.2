/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.server.IServer;
import java.io.File;
import java.util.List;

/**
 *
 * @author Cristiano
 */
public class RMIConnector implements IServer{

    private String repPath;
    private String repHost;

    public boolean commit(List<File> file, String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> checkout() {
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
    
}