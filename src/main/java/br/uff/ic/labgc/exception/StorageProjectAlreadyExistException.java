/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class StorageProjectAlreadyExistException extends StorageException {

    /**
     * Creates a new instance of
     * <code>StorageProjectAlreadyExistException</code> without detail
     * message.
     */
    public StorageProjectAlreadyExistException() {
    }

    /**
     * Constructs an instance of
     * <code>StorageProjectAlreadyExistException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public StorageProjectAlreadyExistException(String msg) {
        super(msg);
    }
}
