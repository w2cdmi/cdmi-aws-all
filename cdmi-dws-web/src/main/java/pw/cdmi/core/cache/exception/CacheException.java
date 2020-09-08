/**
 * 
 */
package pw.cdmi.core.cache.exception;

/**
 * @author q90003805
 * 
 */
public class CacheException extends RuntimeException
{
    
    private static final long serialVersionUID = -3590259449200994477L;
    
    public CacheException(String msg)
    {
        super(msg);
    }
    
    public CacheException(String msg, Throwable tx)
    {
        super(msg, tx);
    }
}
