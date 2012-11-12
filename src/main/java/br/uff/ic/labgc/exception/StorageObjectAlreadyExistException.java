/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class StorageObjectAlreadyExistException extends StorageException {

    /**
     * Creates a new instance of
     * <code>StorageObjectAlreadyExistException</code> without detail
     * message.
     */
    public StorageObjectAlreadyExistException() {
    }

    /**
     * Constructs an instance of
     * <code>StorageObjectAlreadyExistException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public StorageObjectAlreadyExistException(String msg) {
        super(msg);
    }
}
