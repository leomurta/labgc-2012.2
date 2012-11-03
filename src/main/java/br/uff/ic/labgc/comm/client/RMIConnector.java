package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.comm.server.CommunicationServer;
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
            throw new CommunicationException("Não foi possível localizar o servidor no host: " + getRepHost(), ex);
        } catch (AccessException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Problemas de autorização ao tentar acessar o servidor no host: "
                    + getRepHost(), ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("A comunicação com o host " + getRepHost() + " falhou.",
                    ex);
        }
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
        String result = null;
        try {
            item.deflate();
            result = server.commit(item, message, token);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
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
        VersionedItem result = null;
        try {
            result = server.update(revision, token);
            result.inflate();
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        return result;
    }

    /**
     * Executa remotamente o comando diff
     *
     * @param item
     * @param version
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o diff
     */
    

    /**
     * Executa remotamente o comando log
     *
     * @return
     * @throws ApplicationException Exceção ocorrida no servidor ao tentar
     * efetuar o log
     */
    @Override
    public VersionedItem log(String token) throws ApplicationException {
        VersionedItem result = null;
        try {
            result = server.log(token);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
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
        String result = null;
        try {
            result = server.hello(name);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
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
        VersionedItem result = null;
        try {
            result = server.checkout(revision, token);
            result.inflate();
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
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
        setRepPath(repository);
        String result = null;
        try {
            result = server.login(user, pwd, repository);
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
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
    public byte[] getItemContent(String hash) throws ApplicationException {
        byte[] result = null;
        try {
            VersionedFile file = new VersionedFile();
            file.setContent(server.getItemContent(hash));
            //file.inflate();
            result = file.getContent();
        } catch (RemoteException ex) {
            handleRemoteException(ex);
        }
        return result;
    }

    public static void main(String args[]) {
        try {
            RMIConnector rmi = new RMIConnector("localhost");
            String command = args[0];
            if ("hello".equals(command)) {
                System.out.println(rmi.hello("Cristiano"));
            }
        } catch (CommunicationException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ApplicationException ex) {
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(RMIConnector.class.getName()).log(Level.SEVERE, null, ex);
            throw new ApplicationException("Ocorreu um erro ao tentar executar a operação, verifique a exceção aninhada para mais detalhes.",
                    ex.getCause());
        }
    }
}
