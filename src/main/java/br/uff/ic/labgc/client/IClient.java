/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.exception.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Felipe R
 */
public interface IClient {
    
    //comandos server
    
    boolean commit(List<File> files, String message);
    List<File> update();
    
    String diff(File file, String version);
    String log();
    
    //comandos similares ao OS
    
    boolean remove(File file);
    boolean move(File file, String dest);
    boolean copy(File file, String dest);
    boolean mkdir(String name);
    
    //comandos do diretorio
    
    boolean add(File file);
    
    String status();
    boolean release();
    boolean resolve(File file);
    
    //implementados
    boolean revert(String systemDirectory)throws ClientWorkspaceUnavailableException;
    /**
     * Solicita o checkout de um repositório. Os checkouts são sempre de todo o repositório.
     * @param repository URL do repositório
     * @param systemDirectory Pasta onde será criado o workspace
     * @param revision Número da revisão que se deseja. Caso seja desejada a revisão HEAD, passa a constant EVCSConstants.REVISION_HEAD
     * @throws ClientWorkspaceUnavailableException
     * @throws ClientLoginRequiredException 
     */
    void checkout(String repository, String systemDirectory, int revision) throws ClientWorkspaceUnavailableException, ClientLoginRequiredException;
    void login(String user, String pwd);
    
}
