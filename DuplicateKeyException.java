/**
 * Exception for Duplicate Key.

 */
public class DuplicateKeyException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public DuplicateKeyException(){
        super("Duplicate keys not allowed.");
    }
}
