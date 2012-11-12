/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class StorageCanNotCreateDirException extends StorageException {

    /**
     * Creates a new instance of
     * <code>StorageCanNotCreateDirException</code> without detail message.
     */
    public StorageCanNotCreateDirException() {
    }

    /**
     * Constructs an instance of
     * <code>StorageCanNotCreateDirException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public StorageCanNotCreateDirException(String msg) {
        super(msg);
    }
}
