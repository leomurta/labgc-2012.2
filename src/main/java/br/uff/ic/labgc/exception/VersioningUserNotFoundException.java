/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningUserNotFoundException extends VersioningException {

    /**
     * Creates a new instance of
     * <code>VersioningUserNotFoundException</code> without detail message.
     */
    public VersioningUserNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningUserNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public VersioningUserNotFoundException(String msg) {
        super(msg);
    }
    
    public VersioningUserNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
    public VersioningUserNotFoundException(Exception ex) {
        super(ex);
    }
}
