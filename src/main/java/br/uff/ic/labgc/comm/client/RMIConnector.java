package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.comm.server.ICommunicationServer;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CommunicationException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.AbstractServer;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Esta classe é responsável por executar comandos em um servidor remoto, utilizando
 * o protocolo RMI. Em sua inicialização, é pre
 *
 * @author Cristiano
 */
public class RMIConnector extends AbstractServer {

    private int repPort;
    private ICommunicationServer server;

    /**
     * Inicializa um conector RMI. Em todos os métodos invocados, caso a exceção
     * remota seja uma ApplicationException, ela é é extraída e repassada ao
     * chamador.
     *
     * @param hostName Hostname do servidor remoto com o qual este conector se
     * comunicará.
     * @throws CommunicationException
     */
    public RMIConnector(String hostName) throws CommunicationException {
        super(hostName);
        LoggerFactory.getLogger(RMIConnector.class).debug("Inicializando RMIConnector com o servidor {}", hostName);
        this.repPort = Integer.parseInt(ApplicationProperties.getPropertyValue(IPropertiesConstants.COMM_REMOTE_PORT));
        LoggerFactory.getLogger(RMIConnector.class).debug("Porta de comunicação utilizada: {}", repPort);

        if (System.getSecurityManager() == null) {
            LoggerFactory.getLogger(RMIConnector.class).debug("Inicializando um novo SecurityManager.");
            System.setSecurityManager(new SecurityManager());
            LoggerFactory.getLogger(RMIConnector.class).debug("Novo SecurityManager inicializado.");
        }
        try {
            String repositoryServerObject = ApplicationProperties.getPropertyValue(IPropertiesConstants.RMI_REPOSITORY_OBJECT);
            Registry registry = LocateRegistry.getRegistry(getRepHost(), repPort);
            LoggerFactory.getLogger(RMIConnector.class).debug("Buscando pelo objeto {} remotamente no servidor {}.", repositoryServerObject, getRepHost());
            server = (ICommunicationServer) registry.lookup(repositoryServerObject);
            LoggerFactory.getLogger(RMIConnector.class).debug("{} localizado remotamente no servidor {}.", repositoryServerObject, getRepHost());
        } catch (NotBoundException ex) {
            LoggerFactory.getLogger(RMIConnector.class).error("Não foi possível localizar o servidor no host: {}", getRepHost(), ex);
            throw new CommunicationException("Não foi possível localizar o servidor no host: " + getRepHost(), ex);
        } catch (AccessException ex) {
            LoggerFactory.getLogger(RMIConnector.class).error("A comunicação com o host {} falhou.", getRepHost(), ex);
            throw new CommunicationException("Problemas de autorização ao tentar acessar o servidor no host: "
                    + getRepHost(), ex);
        } catch (RemoteException ex) {
            LoggerFactory.getLogger(RMIConnector.class).error("Não foi possível localizar o servidor no host: {}", getRepHost(), ex);
            throw new CommunicationException("A comunicação com o host " + getRepHost() + " falhou.",
                    ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).debug("Inicialização de RMIConnector finalizada com sucesso.");
    }

    /**
     * Invoca remotamente o comando commit
     *
     * @param item
     * @param token
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o commit
     */
    @Override
    public String commit(VersionedItem item, String message, String token) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("commit -> Entry");
        String result = null;
        try {
            LoggerFactory.getLogger(RMIConnector.class).debug("Compactando o conteúdo a ser enviado para o servidor.");
            item.deflate();
            LoggerFactory.getLogger(RMIConnector.class).debug("Conteúdo compactado com sucesso.");
            result = server.commit(item, message, token);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("commit -> Exit");
        return result;
    }

    /**
     * Invoca remotamente o commando update
     *
     * @param revision
     * @param token
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o update
     */
    @Override
    public VersionedItem update(String revision, String token) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("update -> Entry");
        VersionedItem result = null;
        try {
            result = server.update(revision, token);
            LoggerFactory.getLogger(RMIConnector.class).debug("Descompactando conteúdo recebido do servidor.");
            result.inflate();
            LoggerFactory.getLogger(RMIConnector.class).debug("Conteúdo descompactado com sucesso.");
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("update -> Exit");
        return result;
    }

    /**
     * Executa remotamente o comando log
     *
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o log
     */
    @Override
    public List<VersionedItem> log(String token) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("log -> Entry");
        List<VersionedItem> result = null;
        try {
            result = server.log(token);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("log -> Exit");
        return result;
    }

    /**
     * Método para testes do servidor remoto
     *
     * @param name Nome para o qual se deseja dizer Alô
     * @return Mensagem de alô retornada pelo servidor remoto
     * @throws ApplicationException
     */
    public String hello(String name) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("hello -> Entry");
        String result = null;
        try {
            result = server.hello(name);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("hello -> Exit");
        return result;
    }

    /**
     * Executa remotamente o comando checkout
     *
     * @param revision
     * @param token
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o checkout
     */
    @Override
    public VersionedItem checkout(String revision, String token) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("checkout -> Entry");
        VersionedItem result = null;
        try {
            result = server.checkout(revision, token);
            LoggerFactory.getLogger(RMIConnector.class).debug("Descompactando conteúdo recebido do servidor.");
            result.inflate();
            LoggerFactory.getLogger(RMIConnector.class).debug("Conteúdo descompactado com sucesso.");
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("checkout -> Exit");
        return result;
    }

    /**
     * Executa remotamente o comando login
     *
     * @param user
     * @param pwd
     * @param repository
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o login
     */
    @Override
    public String login(String user, String pwd, String repository) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("login -> Entry");
        setRepPath(repository);
        String result = null;
        try {
            result = server.login(user, pwd, repository);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("login -> Exit");
        return result;
    }

    /**
     * Executamente o comando getItemContent
     *
     * @param hash
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o getItemContent
     */
    @Override
    public byte[] getItemContent(String hash, String projectName) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("getItemContent -> Entry");
        byte[] result = null;
        try {
            VersionedFile file = new VersionedFile();
            file.setContent(server.getItemContent(hash, projectName));
            LoggerFactory.getLogger(RMIConnector.class).debug("Descompactando conteúdo recebido do servidor.");
            file.inflate();
            LoggerFactory.getLogger(RMIConnector.class).debug("Conteúdo descompactado com sucesso.");
            result = file.getContent();
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("getItemContent -> Exit");
        return result;
    }

    @Override
    public List<VersionedItem> log(int qtdeRevisions, String token) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("log -> Entry");
        List<VersionedItem> result = null;
        try {
            result = server.log(qtdeRevisions, token);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("log -> Exit");
        return result;
    }

    @Override
    public void addProject(String project, String user) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("addProject -> Entry");
        try {
            server.addProject(project, user);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("addProject -> Exit");
    }

    @Override
    public void init(VersionedItem item, String user) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("init -> Entry");
        try {
            LoggerFactory.getLogger(RMIConnector.class).debug("Compactando o conteúdo a ser enviado para o servidor.");
            item.deflate();
            LoggerFactory.getLogger(RMIConnector.class).debug("Conteúdo compactado com sucesso.");
            server.init(item, user);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("init -> Exit");
    }

    @Override
    public void addUser(String name, String username, String password) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("addUser -> Entry");
        try {
            server.addUser(name, username, password);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("addUser -> Exit");
    }

    @Override
    public void addUserToProject(String project, String user) throws ApplicationException {
        LoggerFactory.getLogger(RMIConnector.class).trace("addUserToProject -> Entry");
        try {
            server.addUserToProject(project, user);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("addUserToProject -> Exit");
    }

    public static void main(String args[]) {
        LoggerFactory.getLogger(RMIConnector.class).trace("main -> Entry");
        try {
            RMIConnector rmi = new RMIConnector("localhost");
            String command = args[0];
            if ("hello".equals(command)) {
                System.out.println(rmi.hello("Cristiano"));
            }
        } catch (CommunicationException ex) {
            LoggerFactory.getLogger(RMIConnector.class).error(null, ex);
        } catch (ApplicationException ex) {
            LoggerFactory.getLogger(RMIConnector.class).error(null, ex);
        }
        LoggerFactory.getLogger(RMIConnector.class).trace("main -> Exit");
    }

    /**
     * Handles a remote exception, throwing the root error cause or creating a
     * new ApplicationException
     *
     * @param ex remote exception to be handled
     * @throws ApplicationException Original exception (or a new one, if not an
     * ApplicationException)
     */
    private void handleRemoteException(RemoteException ex) throws ApplicationException {
        //Recupera a exceção original
        Throwable cause = ex.getCause().getCause();
        if (cause instanceof ApplicationException) {
            throw (ApplicationException) cause;
        } else {
            LoggerFactory.getLogger(RMIConnector.class).error(null, ex);
            throw new ApplicationException("Ocorreu um erro ao tentar executar a operação, verifique a exceção aninhada para mais detalhes.",
                    ex.getCause());
        }
    }
}
