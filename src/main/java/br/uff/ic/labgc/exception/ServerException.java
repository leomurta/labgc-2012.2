/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.exception;


public class ServerException extends ApplicationException {
    private static final long serialVersionUID = 7589237863648182334L;
    
    public ServerException(String message, Exception ex) {
    super(message, ex);
    }
    
    public ServerException(String message) {
    super(message);
    }
    
}
