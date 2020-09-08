package pw.cdmi.core.parser;

import java.util.Properties;

/**
 * 属性解析器
 * 
 * @author s90006125
 *         
 */
public interface PropertyParser
{
    String parse(String propertyName, Properties props);
    
    boolean match(String propertyName);
}
