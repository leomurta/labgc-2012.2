/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceDirNaoExisteException extends WorkspaceException {
    
    public WorkspaceDirNaoExisteException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceDirNaoExisteException(String message) {
    super(message);
    }
}
