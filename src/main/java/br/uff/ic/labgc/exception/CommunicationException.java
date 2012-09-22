/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author Cristiano
 */
public class CommunicationException extends ApplicationException {
    private static final long serialVersionUID = -8484102511254676109L;

    public CommunicationException(String message, Exception ex) {
        super(message, ex);
    }
    public CommunicationException(String message, Throwable t) {
        super(message, t);
    }
    
    public CommunicationException(String message) {
        super(message);
    }

    
}
