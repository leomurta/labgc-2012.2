/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningNeedToUpdateException extends VersioningException {

    /**
     * Creates a new instance of
     * <code>VersioningNeedToUpdateException</code> without detail message.
     */
    public VersioningNeedToUpdateException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningNeedToUpdateException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public VersioningNeedToUpdateException(String msg) {
        super(msg);
    }
}
