/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.server;

import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ServerException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import java.io.File;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor de comunicação que interage com um servidor de repositório.<br/>
 * @author Cristiano
 */
public class CommunicationServer implements ICommunicationServer {

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());            
        }
        try {
            String name = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            ICommunicationServer server = new CommunicationServer();
            ICommunicationServer stub = (ICommunicationServer) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "Servidor de repositório pronto para receber requisições.");

        } catch (RemoteException ex) {
            Logger.getLogger(CommunicationServer.class.getName()).log(Level.SEVERE, "Houve um erro ao inicializar o servidor de repositório.", ex);
        }
    }

    public String commit(VersionedItem item, String token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     public VersionedItem update(String revision, String token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem checkout(String revision, String token)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método verificar se o objeto remoto está atendendo requisições.
     * @param name
     * @return
     * @throws RemoteException
     */
    public String hello(String name) throws RemoteException {
        if (name == null) {
            ApplicationException ae = new ApplicationException("Nome não pode ser nulo.");
            throw new RemoteException("Erro remoto", ae);
        }
        return "Hello, " + name + "!";
    }

    public String login(String user, String pwd, String repository) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(VersionedItem file, String version) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getItemContent(String hash) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
