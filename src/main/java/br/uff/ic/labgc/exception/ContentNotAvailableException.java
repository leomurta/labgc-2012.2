package br.uff.ic.labgc.exception;

/**
 *
 * @author Cristiano
 */
public class ContentNotAvailableException extends ApplicationException {
    private static final long serialVersionUID = -8484102511254676109L;

    public ContentNotAvailableException(String message, Exception ex) {
        super(message, ex);
    }
    public ContentNotAvailableException(String message, Throwable t) {
        super(message, t);
    }
    
    public ContentNotAvailableException(String message) {
        super(message);
    }

    
}
