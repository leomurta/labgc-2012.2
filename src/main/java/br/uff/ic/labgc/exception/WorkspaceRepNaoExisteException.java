/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceRepNaoExisteException extends WorkspaceException{

    public WorkspaceRepNaoExisteException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceRepNaoExisteException(String message) {
    super(message);
    }
}