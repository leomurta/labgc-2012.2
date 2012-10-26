/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.server;

import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.Server;
import br.uff.ic.labgc.util.CompressUtils;
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
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            ICommunicationServer stub = (ICommunicationServer) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "Servidor de repositório pronto para receber requisições.");

        } catch (RemoteException ex) {
            Logger.getLogger(CommunicationServer.class.getName()).log(Level.SEVERE, "Houve um erro ao inicializar o servidor de repositório.", ex);
        }
        server = new Server("localhost");
    }

    @Override
    public String commit(VersionedItem item, String token) throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: commit");
        try {
            item.inflate();
            return server.commit(item, token);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar commit.", ex);
        }
    }

    @Override
    public VersionedItem update(String revision, String token) throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: update");
        try {
            VersionedItem result = server.update(revision, token);
            result.deflate();
            return result;
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar update.", ex);
        }
    }

    @Override
    public VersionedItem checkout(String revision, String token) throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: checkout");
        try {
            VersionedItem result = server.checkout(revision, token);
            result.deflate();
            return result;
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar checkout.", ex);
        }
    }

    @Override
    public String log() throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: log");
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
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: login");
        try {
            return server.login(user, pwd, repository);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar login.", ex);
        }
    }

    @Override
    public String diff(VersionedItem item, String version) throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: diff");
        try {
            item.inflate();
            return server.diff(item, version);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar diff.", ex);
        }
    }

    @Override
    public byte[] getItemContent(String hash) throws RemoteException {
        Logger.getLogger(CommunicationServer.class.getName()).log(Level.INFO, "communication server command received: getItemContent");
        try {
            VersionedFile file = new VersionedFile();
            file.setContent(server.getItemContent(hash));
            file.deflate();
            return file.getContent();
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar getItemContent.", ex);
        }
    }
}
