/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.server.IServer;
import java.io.*;
import java.util.*;


/**
 *
 * @author Felipe R
 */
public class Client implements IClient{
    
    private IServer server;
    
    
    
    public boolean commit(File file, String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> checkout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean release() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(File file, String version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean move(File file, String dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean copy(File file, String dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean mkdir(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean revert(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String status() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean resolve(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
