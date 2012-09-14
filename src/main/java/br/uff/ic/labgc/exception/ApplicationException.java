/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author Cristiano
 */
public class ApplicationException extends Exception {
    private static final long serialVersionUID = -7924231328845556963L;
    
    public ApplicationException() {
        super();
    }
    
    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(Throwable t) {
        super(t);
    }
    
    public ApplicationException(String message, Throwable t) {
        super(message, t);
    }
}
