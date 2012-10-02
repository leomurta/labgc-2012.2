/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.server;

import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor de comunicação que interage com um servidor de repositório.<br/>
 *
 * @author Cristiano
 */
public class CommunicationServer implements ICommunicationServer {

    private Server server;

    public CommunicationServer() {
        server = new Server("localhost");
    }

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

    @Override
    public String commit(VersionedItem item, String token) throws RemoteException {
        try {
            return server.commit(item.inflate(), token);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar commit.", ex);
        }
    }

    @Override
    public VersionedItem update(String revision, String token) throws RemoteException {
        try {
            return server.update(revision, token).deflate();
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar update.", ex);
        }
    }

    @Override
    public VersionedItem checkout(String revision, String token) throws RemoteException {
        try {
            return server.checkout(revision, token).deflate();
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar checkout.", ex);
        }
    }

    @Override
    public String log() throws RemoteException {
        try {
            return server.log();
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar log.", ex);
        }
    }

    /**
     * Método verificar se o objeto remoto está atendendo requisições.
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public String hello(String name) throws RemoteException {
        if (name == null) {
            ApplicationException ae = new ApplicationException("Nome não pode ser nulo.");
            throw new RemoteException("Erro remoto", ae);
        }
        return "Hello, " + name + "!";
    }

    @Override
    public String login(String user, String pwd, String repository) throws RemoteException {
        try {
            return server.login(user, pwd, repository);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar login.", ex);
        }
    }

    @Override
    public String diff(VersionedItem item, String version) throws RemoteException {
        try {
            return server.diff(item.inflate(), version);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar diff.", ex);
        }
    }

    @Override
    public byte[] getItemContent(String hash) throws RemoteException {
        try {
            return server.getItemContent(hash);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar getItemContent.", ex);
        }
    }
}
