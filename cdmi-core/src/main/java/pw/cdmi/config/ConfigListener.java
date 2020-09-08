/**
 * 
 */
package pw.cdmi.config;

/**
 * @author q90003805
 * 
 */
public interface ConfigListener
{
    void configChanged(String key, Object value);
}
