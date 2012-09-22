/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.*;
import br.uff.ic.labgc.server.*;
import br.uff.ic.labgc.workspace.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Felipe R
 */
public class Client implements IClient{
    
    private IServer server;
    private Workspace workspace = new Workspace();
    
    private String repository;
    private String loginToken;
            
            
    void getServer(String source){
        this.server = new Server();
    }   
    
    
    //comandos para o servidor

    public boolean commit(List<File> files, String message) {
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

    public boolean remove(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean move(File file, String dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean copy(File file, String dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean mkdir(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String status() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean release() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean resolve(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //implementados
    
    public boolean revert(String systemDirectory)throws ClientWorkspaceUnavailableException{
        boolean revert = false;
        try {
            revert = workspace.revert(systemDirectory);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WorkspaceException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return revert;
    }

    public void checkout(String repository, String systemDirectory, int revision) throws ClientWorkspaceUnavailableException, ClientLoginRequiredException{
        
        this.repository = repository; 
            
        if(!workspace.canCreate())
            throw new ClientWorkspaceUnavailableException();
        
        if(loginToken == null || loginToken.isEmpty())
            throw new ClientLoginRequiredException();
        
        try{
            createWorkspace(systemDirectory);
            List<VersionedItem> items = server.checkout(-1,loginToken);
            workspace.storeLocalData(items);
        }
        catch(WorkspaceException we){}
        catch(ServerException se){}
        catch(Exception e){}
        
    }

    public void login(String user, String pwd) {
        try {
            getServer();
            loginToken = server.login(user, pwd);
        } catch (ServerException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    //private
    private void createWorkspace(String systemDirectory)throws WorkspaceException{
        try {
            workspace.createWorkspace(systemDirectory,repository);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
         workspace.setParam("token",loginToken);
    }
    
    private void getServer(){
        String url = repository;
        server = new Server();
    }
}
