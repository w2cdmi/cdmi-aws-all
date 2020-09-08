package pw.cdmi.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wcc.crypt.Crypter;
import org.wcc.crypt.CrypterFactory;
import org.wcc.crypt.EncryptHelper;
import org.wcc.framework.AppRuntimeException;

public final class EDToolsEnhance
{
    
    public final static String ENCRYPT_KEY = "encryptedKey";
    
    public final static String ENCRYPT_CONTENT = "encryptedContent";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EDToolsEnhance.class);
    
    private EDToolsEnhance()
    {
    }
    
    /**
     * 加密并生成加密密钥
     * 
     * @param content
     * @return
     * @throws Exception 
     */
    public static Map<String, String> encode(String content) throws Exception
    {
        try
        {
            Crypter crypter = CrypterFactory.getCrypter(CrypterFactory.AES_CBC);
            String key = generateKey();
            Map<String, String> map = new HashMap<String, String>(2);
            map.put(ENCRYPT_KEY, crypter.encryptByRootKey(key));
            map.put(ENCRYPT_CONTENT, crypter.encrypt(content, key));
            return map;
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER.error("NoSuchAlgorithmException ", e);
            throw new AppRuntimeException("NoSuchAlgorithmException", e);
        }
        catch (AppRuntimeException re)
        {
            LOGGER.error("AppRuntimeException ", re);
            throw re;
        }
        catch (Exception se)
        {
            LOGGER.error("encode exception ", se);
            throw se;
        }
    }
    
    private static String generateKey() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] keyBytes = new byte[16];
        sr.nextBytes(keyBytes);
        return EncryptHelper.parseByte2HexStr(keyBytes);
    }
    
    /**
     * 加密(不需要生成或者更换密钥的情况)
     * 
     * @param content
     * @param encryptedKey
     * @return
     * @throws Exception 
     */
    public static String encode(String content, String encryptedKey) throws Exception
    {
        try
        {
            Crypter crypter = CrypterFactory.getCrypter(CrypterFactory.AES_CBC);
            String key = crypter.decryptByRootKey(encryptedKey);
            return crypter.encrypt(content, key);
        }
        catch (AppRuntimeException re)
        {
            LOGGER.error("AppRuntimeException ", re);
            throw re;
        }
        catch (Exception se)
        {
            LOGGER.error("encode exception ", se);
            throw se;
        }
    }
    
    /**
     * 解密
     * 
     * @param encryptedContent
     * @param encryptedKey
     * @return
     * @throws Exception 
     */
    public static String decode(String encryptedContent, String encryptedKey) throws Exception
    {
        try
        {
            Crypter crypter = CrypterFactory.getCrypter(CrypterFactory.AES_CBC);
            String key = crypter.decryptByRootKey(encryptedKey);
            return crypter.decrypt(encryptedContent, key);
        }
        catch (AppRuntimeException re)
        {
            LOGGER.error("AppRuntimeException ", re);
            throw re;
        }
        catch (Exception se)
        {
            LOGGER.error("decode exception ", se);
            throw se;
        }
    }
}
