package br.uff.ic.labgc.exception;

/**
 *
 * @author Cristiano
 */
public class CompressionException extends ApplicationException {
    private static final long serialVersionUID = -8484102511254676109L;

    public CompressionException(String message, Exception ex) {
        super(message, ex);
    }
    public CompressionException(String message, Throwable t) {
        super(message, t);
    }
    
    public CompressionException(String message) {
        super(message);
    }

    
}
