/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class StorageUserNotFoundException extends StorageException {

    /**
     * Creates a new instance of
     * <code>StorageUserNotFoundException</code> without detail message.
     */
    public StorageUserNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>StorageUserNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public StorageUserNotFoundException(String msg) {
        super(msg);
    }
    
    public StorageUserNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
    public StorageUserNotFoundException(Exception ex) {
        super(ex);
    }
}
