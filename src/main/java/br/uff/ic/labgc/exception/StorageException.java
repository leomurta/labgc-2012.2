/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class StorageException extends ApplicationException {

    /**
     * Creates a new instance of
     * <code>StorageException</code> without detail message.
     */
    public StorageException() {
    }

    /**
     * Constructs an instance of
     * <code>StorageException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public StorageException(String msg) {
        super(msg);
    }
}
