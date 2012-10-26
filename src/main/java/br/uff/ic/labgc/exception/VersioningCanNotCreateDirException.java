/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class VersioningCanNotCreateDirException extends VersioningException {

    /**
     * Creates a new instance of
     * <code>VersioningCanNotCreateDirException</code> without detail message.
     */
    public VersioningCanNotCreateDirException() {
    }

    /**
     * Constructs an instance of
     * <code>VersioningCanNotCreateDirException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public VersioningCanNotCreateDirException(String msg) {
        super(msg);
    }
}
