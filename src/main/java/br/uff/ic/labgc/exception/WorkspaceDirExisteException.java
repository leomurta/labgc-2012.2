/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceDirExisteException extends WorkspaceException{
    
    public WorkspaceDirExisteException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceDirExisteException(String message) {
    super(message);
    }
    
}
