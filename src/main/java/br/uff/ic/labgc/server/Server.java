/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import java.io.File;
import java.util.List;

/**
 *
 * @author Felipe R
 */
public class Server implements IServer{

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

    
    
}
