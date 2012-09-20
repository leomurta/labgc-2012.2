/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author victor
 */
public class WorkspaceNaoDirException extends WorkspaceException{

    public WorkspaceNaoDirException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceNaoDirException(String message) {
    super(message);
    }
}