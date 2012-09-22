/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceArqNaoExisteException extends WorkspaceException{

    public WorkspaceArqNaoExisteException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceArqNaoExisteException(String message) {
    super(message);
    }
}
