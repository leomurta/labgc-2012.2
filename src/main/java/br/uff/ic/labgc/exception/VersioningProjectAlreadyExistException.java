/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningProjectAlreadyExistException extends VersioningException {

    /**
     * Creates a new instance of
     * <code>VersioningProjectAlreadyExistException</code> without detail
     * message.
     */
    public VersioningProjectAlreadyExistException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningProjectAlreadyExistException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public VersioningProjectAlreadyExistException(String msg) {
        super(msg);
    }
}
