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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Servidor de comunicação que interage com um servidor de repositório.<br/>
 *
 * @author Cristiano
 */
public class CommunicationServer implements ICommunicationServer {

    private Server server;

    public CommunicationServer() {
        LoggerFactory.getLogger(CommunicationServer.class).debug("Inicializando CommunicationServer.");
        if (System.getSecurityManager() == null) {
            LoggerFactory.getLogger(CommunicationServer.class).debug("Inicializando um novo SecurityManager.");
            System.setSecurityManager(new SecurityManager());
            LoggerFactory.getLogger(CommunicationServer.class).debug("Novo SecurityManager inicializado.");
        }
        try {
            String name = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            int port = Integer.parseInt(ApplicationProperties.getPropertyValue(IPropertiesConstants.COMM_REMOTE_PORT));
            ICommunicationServer stub = (ICommunicationServer) UnicastRemoteObject.exportObject(this, 0);
            LoggerFactory.getLogger(CommunicationServer.class).debug("Stub para {} gerado.", name);
            Registry registry = LocateRegistry.createRegistry(port);
            LoggerFactory.getLogger(CommunicationServer.class).debug("RMI registry inicializado na porta {}", port);
            registry.rebind(name, stub);
            LoggerFactory.getLogger(CommunicationServer.class).debug("Efetuado o bind de {} no RMI registry.", name);
            LoggerFactory.getLogger(CommunicationServer.class).info("Servidor de repositório pronto para receber requisições.");

        } catch (RemoteException ex) {
            LoggerFactory.getLogger(CommunicationServer.class).error("Houve um erro ao inicializar o servidor de repositório.", ex);
        }
        server = new Server("localhost");
        LoggerFactory.getLogger(CommunicationServer.class).debug("Inicialização de CommunicationServer finalizada com sucesso.");
    }

    @Override
    public String commit(VersionedItem item, String message, String token) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("commit -> Entry");
        String result = null;
        try {
            LoggerFactory.getLogger(CommunicationServer.class).debug("Descompactando conteúdo recebido do cliente.");
            item.inflate();
            LoggerFactory.getLogger(CommunicationServer.class).debug("Conteúdo descompactado com sucesso.");
            result = server.commit(item, message, token);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar commit.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("commit -> Exit");
        return result;
    }

    @Override
    public VersionedItem update(String revision, String token) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("update -> Entry");
        VersionedItem result = null;
        try {
            result = server.update(revision, token);
            LoggerFactory.getLogger(CommunicationServer.class).debug("Compactando conteúdo a ser enviado para o cliente.");
            result.deflate();
            LoggerFactory.getLogger(CommunicationServer.class).debug("Conteúdo compactado com sucesso.");
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar update.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("update -> Exit");
        return result;
    }

    @Override
    public VersionedItem checkout(String revision, String token) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("checkout -> Entry");
        VersionedItem result = null;
        try 
        {
            result = server.checkout(revision, token);
            LoggerFactory.getLogger(CommunicationServer.class).debug("Compactando conteúdo a ser enviado para o cliente.");
            result.deflate();
            LoggerFactory.getLogger(CommunicationServer.class).debug("Conteúdo compactado com sucesso.");
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar checkout.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("checkout -> Exit");
        return result;
    }

    @Override
    public  List<VersionedItem> log(String token) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("log -> Entry");
        List<VersionedItem> result = null;
        try {
            result = server.log(token);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar log.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("log -> Exit");
        return result;
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
        LoggerFactory.getLogger(CommunicationServer.class).trace("hello -> Entry");
        if (name == null) {
            ApplicationException ae = new ApplicationException("Nome não pode ser nulo.");
            throw new RemoteException("Erro remoto", ae);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("login -> Exit");
        return "Hello, " + name + "!";
    }

    @Override
    public String login(String user, String pwd, String repository) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("login -> Entry");
        String result = null;
        try {
            result = server.login(user, pwd, repository);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar login.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("login -> Exit");
        return result;
    }

    

    @Override
    public byte[] getItemContent(String hash, String projectName) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("getItemContent -> Entry");
        byte[] result = null;
        try {
            VersionedFile file = new VersionedFile();
            file.setContent(server.getItemContent(hash, projectName));
            LoggerFactory.getLogger(CommunicationServer.class).debug("Compactando conteúdo a ser enviado para o cliente.");
            file.deflate();
            LoggerFactory.getLogger(CommunicationServer.class).debug("Conteúdo compactado com sucesso.");
            result =  file.getContent();
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar getItemContent.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("getItemContent -> Exit");
        return result;
    }

    @Override
    public List<VersionedItem> log(int qtdeRevisions, String token) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("log -> Entry");
        List<VersionedItem> result = null;
        try {
            result = server.log(qtdeRevisions, token);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar o comando log.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("log -> Exit");
        return result;
    }

    @Override
    public void addProject(String project, String user) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("addProject -> Entry");
        try {
            server.addProject(project, user);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar o comando addProject.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("addProject -> Exit");
    }

    @Override
    public void init(VersionedItem item, String user) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("init -> Entry");
        try {
            LoggerFactory.getLogger(CommunicationServer.class).debug("Descompactando conteúdo recebido do cliente.");
            item.inflate();
            LoggerFactory.getLogger(CommunicationServer.class).debug("Conteúdo descompactado com sucesso.");
            server.init(item, user);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar o comando init.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("init -> Exit");
    }

    @Override
    public void addUser(String name, String username, String password) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("addUser -> Entry");
        try {
            server.addUser(name, username, password);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar o comando addUser.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("addUser -> Exit");
    }

    @Override
    public void addUserToProject(String project, String user) throws RemoteException {
        LoggerFactory.getLogger(CommunicationServer.class).trace("addUserToProject -> Entry");
        try {
            server.addUserToProject(project, user);
        } catch (ApplicationException ex) {
            throw new RemoteException("Erro ao executar o comando addUserToProject.", ex);
        }
        LoggerFactory.getLogger(CommunicationServer.class).trace("addUserToProject -> Exit");
    }

}
