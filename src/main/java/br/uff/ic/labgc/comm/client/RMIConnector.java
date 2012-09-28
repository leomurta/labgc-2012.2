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
import br.uff.ic.labgc.server.IServer;
import br.uff.ic.labgc.utils.URL;
import java.io.File;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe é responsável executar comandos em um servidor remoto, utilizando
 * o protocolo RMI. Em sua inicialização, é pre
 * @author Cristiano
 */
public class RMIConnector implements IServer{

    private String repPath;
    private String repHost;
    private int repPort;
    private ICommunicationServer server;

    public RMIConnector(URL url) throws ApplicationException {
            this.repPath = url.getRepoServer();
            this.repHost = url.getHost();
            this.repPort = url.getPort();
            
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
        try {
            String repositoryServerObject = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            Registry registry = LocateRegistry.getRegistry(repHost, repPort);
            server = (ICommunicationServer) registry.lookup(repositoryServerObject);
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Não foi possível localizar o servidor no host: " + 
                    repHost + ", path: " + repPath, ex);
        } catch (AccessException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Problemas de autorização ao tentar acessar o servidor no host: " + 
                    repHost + ", path: " + repPath, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("A comunicação com o host " + repHost + " falhou.", 
                    ex.getCause());
        }
        
    }

    public boolean commit(List<File> file, String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<File> update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String diff(File file, String version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void registerRepository(String repHost, String repName) {
        //TODO CRISTIANO retirar este método. host será passado no construtor. repositório não precisa
        this.repHost = repHost;
        this.repPath = repName;
    }

    public String getRepPath() {
        return repPath;
    }

    public String getRepHost() {
        return repHost;
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

    public List<VersionedItem> checkout(String revision, String token) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String login(String user, String pwd) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getItemContent(String hash) throws ServerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
