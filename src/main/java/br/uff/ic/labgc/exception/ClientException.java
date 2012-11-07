/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;


public abstract class ClientException extends ApplicationException {
    public ClientException(String message) {
        super(message);
    }
    public ClientException() {
    }
}
