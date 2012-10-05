/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;

/**
 *
 * @author jokerfvd
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectPasswordException</code> without detail message.
     */
    public IncorrectPasswordException() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectPasswordException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
