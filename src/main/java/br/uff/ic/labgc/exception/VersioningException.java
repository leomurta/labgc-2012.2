/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningException extends ApplicationException {

    /**
     * Creates a new instance of
     * <code>VersioningException</code> without detail message.
     */
    public VersioningException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public VersioningException(String msg) {
        super(msg);
    }
    
    public VersioningException(String message, Exception ex) {
        super(message, ex);
    }
    
    public VersioningException(Exception ex) {
        super(ex);
    }
}
