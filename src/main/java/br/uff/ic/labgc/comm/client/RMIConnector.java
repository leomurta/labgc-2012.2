/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.comm.server.ICommunicationServer;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CommunicationException;
import br.uff.ic.labgc.exception.ServerException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.AbstractServer;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe é responsável executar comandos em um servidor remoto, utilizando
 * o protocolo RMI. Em sua inicialização, é pre
 *
 * @author Cristiano
 */
public class RMIConnector extends AbstractServer {

    private int repPort;
    private ICommunicationServer server;

    public RMIConnector(String hostName) throws CommunicationException {
        super(hostName);
        this.repPort = Integer.parseInt(ApplicationProperties.getPropertyValue(IPropertiesConstants.COMM_REMOTE_PORT));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String repositoryServerObject = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            Registry registry = LocateRegistry.getRegistry(getRepHost(), repPort);
            server = (ICommunicationServer) registry.lookup(repositoryServerObject);
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Não foi possível localizar o servidor no host: "
                    + getRepHost(), ex);
        } catch (AccessException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Problemas de autorização ao tentar acessar o servidor no host: "
                    + getRepHost(), ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("A comunicação com o host " + getRepHost() + " falhou.",
                    ex.getCause());
        }

    }

    public String commit(VersionedItem item, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem update(String revision, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(VersionedItem file, String version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String hello(String name) throws ApplicationException {
        try {
            return server.hello(name);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Ocorreu um erro ao tentar executar a operação, verifique a exceção aninhada para mais detalhes.",
                    ex.getCause());
        }
    }

    public VersionedItem checkout(String revision, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String login(String user, String pwd, String repository) throws ServerException {
        setRepPath(repository);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getItemContent(String hash) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void main(String args[]) {
        try {
            new RMIConnector("localhost");
        } catch (CommunicationException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
