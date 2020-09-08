package pw.cdmi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class EnvironmentUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentUtils.class);
    
    private static String hostName;
    
    private static String dmidecode;
    
    private static final String SERVER_TAG = "server.tag";
    
    private EnvironmentUtils()
    {
        
    }
    
    public static String getServerTag()
    {
        return StringUtils.trimToEmpty(System.getProperty(SERVER_TAG));
    }
    
    public static String getDeviceUUID()
    {
        if (StringUtils.isNotBlank(dmidecode))
        {
            return dmidecode;
        }
        
        BufferedReader in = null;
        try
        {
            StringBuilder uuid = new StringBuilder();
            Process child = Runtime.getRuntime().exec("dmidecode");
            in = new BufferedReader(new InputStreamReader(child.getInputStream(), "UTF-8"));
            LineIterator iterator = new LineIterator(in);
            String c = null;
            boolean hasNext = iterator.hasNext();
            while (hasNext)
            {
                c = iterator.next();
                if (c.contains("UUID"))
                {
                    uuid.append(c);
                    break;
                }
                hasNext = iterator.hasNext();
            }
            String[] ids = uuid.toString().split(":");
            
            if (ids.length >= 1)
            {
                dmidecode = StringUtils.trimToEmpty(ids[1]);
            }
            else
            {
                LOGGER.error("Get device UUID failure. " + uuid);
            }
        }
        catch (Exception e1)
        {
            LOGGER.warn("Get device UUID failure.", e1);
            return null;
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    LOGGER.warn("Close InputStream Failed.");
                }
            }
        }
        
        return dmidecode;
    }
    
    public static String getHostName()
    {
        try
        {
            if (StringUtils.isBlank(hostName))
            {
                hostName = InetAddress.getLocalHost().getHostName();
            }
            return hostName;
        }
        catch (UnknownHostException e)
        {
            LOGGER.warn("unknown host!");
        }
        return null;
    }
}
