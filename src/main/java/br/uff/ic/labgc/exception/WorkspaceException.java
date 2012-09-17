/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;


public class WorkspaceException extends ApplicationException {
    private static final long serialVersionUID = 7280993250981760795L;
    
    public WorkspaceException(String message, Exception ex) {
    super(message, ex);
    }
    
    public WorkspaceException(String message) {
    super(message);
    }

}
