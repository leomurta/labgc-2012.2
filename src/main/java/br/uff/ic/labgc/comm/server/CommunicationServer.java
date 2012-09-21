/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.server;

import br.uff.ic.labgc.exception.ApplicationException;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano
 */
public class CommunicationServer implements ICommunicationServer {


    public static void main(String args[]) {
        
    }

    public void registerRepository(String repHost, String repName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean commit(List<File> file, String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> update() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> checkout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(File file, String version) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRepPath() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRepHost() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
