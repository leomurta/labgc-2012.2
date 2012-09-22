/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceEpelhoNaoExisteException extends WorkspaceException{

    public WorkspaceEpelhoNaoExisteException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceEpelhoNaoExisteException(String message) {
    super(message);
    }
}
