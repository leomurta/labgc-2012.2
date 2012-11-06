/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.comm.client.CommunicationFactory;
import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.*;
import br.uff.ic.labgc.server.*;
import br.uff.ic.labgc.workspace.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe R
 */
public class Client implements IClient {

    /**
     * instancia do server
     */
    private IServer server;
    /**
     * instancia do workspace
     */
    private Workspace workspace;
    /**
     * token de login, criado quando o usuario efetua login para o servidor
     */
    private String loginToken;
    /**
     * endereco do servidor, url ou ip
     */
    private String hostname;
    /**
     * repositorio do projeto no servidor
     */
    private String repository;
    /**
     * conjunto de observadores registrados na api client
     */
    private Set<IObserver> observers = new TreeSet<IObserver>();
    /**
     * nome do parametro que ir� guardar o token de autenticacao
     */
    private final String AUTHENTICATION_TOKEN = "token";
    
    /**
     * Construtor para acesso sem area de trabalho(workspace)
     *
     * @param hostname url ou ip do servidor
     * @param repository repositorio do projeto no servidor
     * @param systemDirectory diretorio do sistema que sera criado a workspace
     */
    public Client(String hostname, String repository, String systemDirectory) {

        this.hostname = hostname;
        this.repository = repository;

        workspace = new Workspace(systemDirectory + "//" + repository);
    }

    /**
     * Construtor para acesso com area de trabalho(workspace) ja existente
     *
     * @param systemDirectory diretorio raiz da area de trabalho
     */
    public Client(String systemDirectory) {

        workspace = new Workspace(systemDirectory);
        try {
            this.hostname = workspace.getHost();
            this.repository = workspace.getProject();
        } catch (WorkspaceException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //comandos para o servidor
    public VersionedItem commit(String message) throws ApplicationException{
        VersionedItem files = workspace.commit();
        String revision = server.commit(files,message,loginToken);
        workspace.setRevision(revision);
        return files;
    }

    public VersionedItem update(String revision) throws ApplicationException{
        String clientRevision = workspace.getRevision();
        VersionedItem files = server.update(clientRevision, revision ,loginToken);
        workspace.update(files);
        return files;
    }

    public VersionedItem diff(String file, String version) throws ApplicationException{
        return workspace.diff(file,version);
    }

    public VersionedItem log() throws ApplicationException{
        return server.log(loginToken);
    }
    
    public boolean resolve(String file) throws ApplicationException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //implementados
    public boolean revert() throws ApplicationException {
        return workspace.revert();
    }
    
    public boolean revert(String file) throws ApplicationException {
        return workspace.revert(file);
    }
    

     public  List<VersionedItem> status() throws ApplicationException
    {  
        List<VersionedItem> stat = new ArrayList<VersionedItem>();
        try 
        {
            stat =  workspace.statusVersionedItem();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WorkspaceException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stat;
    }

    public void checkout(String revision) throws ApplicationException {


        if (workspace.isWorkspace()) {
            throw new ClientWorkspaceUnavailableException();
        }

        System.out.println("checkout: nao eh workspace");

        if (!workspace.canCreate()) {
            throw new ClientWorkspaceUnavailableException();
        }

        if (loginToken == null || loginToken.isEmpty()) {
            throw new ClientLoginRequiredException();
        }

        VersionedItem items = server.checkout(revision, loginToken);
        this.createWorkspace(items);

        System.out.println("checkout finalizado");
    }

    public void login(String user, String pwd) throws ClientException {

        this.getServer();

        try {

            loginToken = server.login(user, pwd, this.repository);

            if (workspace.isWorkspace()) {
                workspace.setParam("token", loginToken);
            }

        } catch (ApplicationException ex) {
            throw new ClientServerNotAvailableException();
        }

        System.out.println("login finalizado");
    }

    public boolean isLogged() throws ClientException {

        this.getServer();

        if (loginToken == null && workspace.isWorkspace()) {
            try {
                loginToken = workspace.getParam(AUTHENTICATION_TOKEN);
            } catch (WorkspaceException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                throw new ClientWorkspaceNotInitialized();
            }
        }

        if (loginToken == null || loginToken.isEmpty()) {
            return false;
        }

        return true;
    }

    //observer methods
    public void registerInterest(IObserver obs) {
        observers.add(obs);

        IObserver clientObs = new IObserver() {
            public void sendNotify(String msg) {
                this.sendNotify(msg);
            }
        };

        workspace.registerInterest(clientObs);
    }

    public void sendNotify(String msg) {

        if (observers.size() > 0) {

            Iterator<IObserver> iterator = observers.iterator();

            while (iterator.hasNext()) {
                iterator.next().sendNotify(msg);
            }
        }
    }

    //private 
    /**
     * metodo interno para criar o workspace
     *
     * @throws WorkspaceException
     */
    private void createWorkspace(VersionedItem items) throws ApplicationException {

        workspace.createWorkspace(hostname, repository, items);
        workspace.setParam("token", loginToken);

    }


    /**
     * Recupera uma instancia de IServer;
     *
     * @throws ClientServerNotAvailableException, servidor nao disponivel
     */
    private void getServer() throws ClientException {
        if (server == null) {
            try {
                server = CommunicationFactory.getFactory().getServer(this.hostname);
            } catch (ApplicationException ex) {
                throw new ClientServerNotAvailableException();
            }
        }
    }
}
