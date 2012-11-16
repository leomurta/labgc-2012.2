/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ServerException;
import br.uff.ic.labgc.storage.Storage;
import br.uff.ic.labgc.versioning.Versioning;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Felipe R
 */
public class Server extends AbstractServer {
    
    
    Versioning versioning = new Versioning();
    Storage storage = new Storage();
   
    
    public Server(String hostName) {
        super(hostName);
    }

    public String commit(VersionedItem file, String message, String token) throws ApplicationException {
        if(!file.isDir())
            throw new ApplicationException("O versionedItem não diretório");
        return versioning.addRevision((VersionedDir)file, token);
    }

    public VersionedItem update(String clientRevision, String revision,  String token) throws ApplicationException {
        return versioning.updateRevision(clientRevision, revision, token);
    }

    public VersionedItem checkout(String revision, String token) throws ApplicationException {
        return versioning.getRevision(revision, token);
    }

    public  VersionedItem log(String token) throws ApplicationException {
        return versioning.getLastLogs(token);
    }
    
    @Override
    public VersionedItem log(int revision, String token) throws ApplicationException {
        return versioning.getLastLogs(revision,token);
    }
    
    public String login(String user, String pwd, String repository) throws ApplicationException {
        System.out.printf("%s %s %s\n",repository,user,pwd);
        return versioning.login(repository, user, pwd);
    }
    
    public byte[] getItemContent(String hash) throws ApplicationException {
        return versioning.getVersionedFileContent(hash,"nvfdovhfdoivbiofdvf");//TODO:consertar esse método
    }
    
    /**
     * ADMINISTRAÇÃO 
     */
    
    public void checkin(VersionedItem item, String user) throws ApplicationException{
        if(!item.isDir())
            throw new ApplicationException("O versionedItem não diretório");
        versioning.addFirstRevision((VersionedDir)item, user);
    }
    
    public void addProject(String project, String user) throws ApplicationException{
        storage.addProject(project, user);
    }
    
    public void addUser(String name, String username, String password) throws ApplicationException{
        storage.addUser(name, username, password);
    }
    
    public void addUserToProject(String project, String user)throws ApplicationException{
        storage.addUserToProject(project, user);
    }
}