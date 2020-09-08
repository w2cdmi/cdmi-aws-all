package pw.cdmi.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class HashTool
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HashTool.class);
    private HashTool()
    {
        
    }
    /**
     * 使用MD5后的前4个字节计算hash
     * 
     * @param id
     * @return
     */
    public static long apply(long id)
    {
        byte[] digest = computeMd5(String.valueOf(id));
        long hashCode = hashPrefix(digest);
        return hashCode;
    }
    
    /**
     * 使用MD5后的前4个字节计算hash
     * 
     * @param id
     * @return
     */
    public static long apply(String id)
    {
        byte[] digest = computeMd5(id);
        long hashCode = hashPrefix(digest);
        return hashCode;
    }
    
    /**
     * 使用MD5后的后4个字节计算hash
     * 
     * @param id
     * @return
     */
    public static long applySuffux(String id)
    {
        byte[] digest = computeMd5(id);
        long hashCode = hashSuffix(digest);
        return hashCode;
    }
    
    private static byte[] computeMd5(String k)
    {
        if (StringUtils.isBlank(k))
        {
            throw new IllegalArgumentException("param k is not null");
        }
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER.error("MD5 not supported");
            throw new RuntimeException("MD5 not supported", e);
        }
        byte[] keyBytes = null;
        try
        {
            keyBytes = k.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Unknown string :" + k, e);
        }
        md5.update(keyBytes);
        return md5.digest();
    }
    
    private static long hashPrefix(byte[] digest)
    {
        if (digest == null || digest.length < 4)
        {
            return 0;
        }
        long rv = ((long) (digest[3] & 0xFF) << 24) | ((long) (digest[2] & 0xFF) << 16)
            | ((long) (digest[1] & 0xFF) << 8) | (digest[0] & 0xFF);
        
        return rv & 0xffffffffL;
    }
    
    private static long hashSuffix(byte[] digest)
    {
        if (digest == null || digest.length < 4)
        {
            return 0;
        }
        int length = digest.length;
        long rv = ((long) (digest[length - 4] & 0xFF) << 24) | ((long) (digest[length - 3] & 0xFF) << 16)
            | ((long) (digest[length - 2] & 0xFF) << 8) | (digest[length - 1] & 0xFF);
        
        return rv & 0xffffffffL;
    }
}
