/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningIOException extends VersioningException {

    /**
     * Creates a new instance of
     * <code>VersioningIOException</code> without detail message.
     */
    public VersioningIOException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningIOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public VersioningIOException(String msg) {
        super(msg);
    }
    
    public VersioningIOException(String message, Exception ex) {
        super(message, ex);
    }
    
    public VersioningIOException(Exception ex) {
        super(ex);
    }
}
