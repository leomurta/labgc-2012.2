/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.server;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Cristiano
 */
public interface ICommunicationServer extends Remote {
    public void registerRepository(String repHost, String repName) throws RemoteException;
    public boolean commit(List<File> file, String message) throws RemoteException;
    public List<File> update() throws RemoteException;
    public List<File> checkout() throws RemoteException;
    public String diff(File file, String version) throws RemoteException;
    public String log() throws RemoteException;
    public String getRepPath() throws RemoteException;
    public String getRepHost() throws RemoteException;
}
