/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceFileNotFoundException extends WorkspaceException{

    public WorkspaceFileNotFoundException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceFileNotFoundException(String message) {
    super(message);
    }
}

