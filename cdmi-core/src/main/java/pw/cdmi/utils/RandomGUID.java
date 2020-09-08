package pw.cdmi.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomGUID
{
    // 截取字符串开始位(从0开始)
    private static final int BEGIN_INDEX = 7;
    
    // 截取字符串结束位(从0开始)
    private static final int END_INDEX = 23;
    
    private static Logger logger = LoggerFactory.getLogger(RandomGUID.class);
    
    /**
     * 随机数生成工具
     */
    private static Random myRand;
    
    /**
     * 安全的随机数生成工具
     */
    private static SecureRandom mySecureRand;
    
    /**
     * 运算参数
     */
    private static final int PAD_BELOW = 0x10;
    
    // UDS用户前缀
    private static final String PREFIX = "csecloud";
    
    /**
     * ID
     */
    private static String sId;
    
    /**
     * 运算参数
     */
    private static final int TWO_BYTES = 0xFF;
    
    /**
     * 进行MD5运算后的字符串，目标串
     */
    private String valueAfterMD5 = "";
    
    /**
     * 未进行MD5运算的字符串，原始串
     */
    private String valueBeforeMD5 = "";
    
    /**
     * Default constructor. With no specification of security option, this constructor
     * defaults to lower security, high performance.
     * 
     */
    public RandomGUID()
    {
        getRandomGUID(false);
    }
    
    /**
     * Constructor with security option. Setting secure true enables each random number
     * generated to be cryptographically strong. Secure false defaults to the standard
     * Random function seeded with a single cryptographically strong random number.
     * 
     * @param secure
     */
    public RandomGUID(boolean secure)
    {
        getRandomGUID(secure);
    }
    
    /*
     * Static block to take care of one time secureRandom seed. It takes a few seconds to
     * initialize SecureRandom. You might want to consider removing this static block or
     * replacing it with a "time since first loaded" seed to reduce this time. This block
     * will run only once per JVM instance.
     */
    static
    {
        mySecureRand = new SecureRandom();
        long secureInitializer = mySecureRand.nextLong();
        myRand = new Random(secureInitializer);
        try
        {
            sId = InetAddress.getLocalHost().toString();
        }
        catch (UnknownHostException e)
        {
            logger.error(e.getMessage(), e);
        }
        
    }
    
    /**
     * 为UDS生成GUID</br> 结构:</br> 用户标识(8位)+时间戳(8位)+截取GUID串(16位)
     * 
     * @return
     */
    public String getGUID4uds()
    {
        StringBuffer sb = new StringBuffer();
        
        // 时间转成16进制后的形式:13a0c443dc7,我们只要后8位
        String timestamp = Long.toHexString(System.currentTimeMillis()).substring(3);
        
        sb.append(PREFIX).append(timestamp).append(valueAfterMD5.substring(BEGIN_INDEX, END_INDEX));
        
        return sb.toString();
    }
    
    public String getValueAfterMD5()
    {
        return valueAfterMD5;
    }
    
    public String getValueBeforeMD5()
    {
        return valueBeforeMD5;
    }
    
    public void setValueAfterMD5(String valueAfterMD5)
    {
        this.valueAfterMD5 = valueAfterMD5;
    }
    
    public void setValueBeforeMD5(String valueBeforeMD5)
    {
        this.valueBeforeMD5 = valueBeforeMD5;
    }
    
    /*
     * Convert to the standard format for GUID (Useful for SQL Server UniqueIdentifiers,
     * etc.) Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
     */
    @Override
    public String toString()
    {
        String raw = valueAfterMD5.toUpperCase(Locale.getDefault());
        StringBuffer sb = new StringBuffer(64);
        sb.append(raw.substring(0, 8));
        sb.append('-');
        sb.append(raw.substring(8, 12));
        sb.append('-');
        sb.append(raw.substring(12, 16));
        sb.append('-');
        sb.append(raw.substring(16, 20));
        sb.append('-');
        sb.append(raw.substring(20));
        return sb.toString();
    }
    
    private void executeGetRandomGUID(boolean secure, MessageDigest md5)
    {
        long time = System.currentTimeMillis();
        long rand = 0;
        
        if (secure)
        {
            rand = mySecureRand.nextLong();
        }
        else
        {
            rand = myRand.nextLong();
        }

        StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
        sbValueBeforeMD5.append(sId);
        sbValueBeforeMD5.append(':');
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(':');
        sbValueBeforeMD5.append(Long.toString(rand));
        
        valueBeforeMD5 = sbValueBeforeMD5.toString();
        if (md5 != null)
        {
            mD5ToString(md5);
        }
    }
    
    /**
     * Method to generate the random GUID
     * 
     * @param secure
     */
    private void getRandomGUID(boolean secure)
    {
        MessageDigest md5 = null;
        
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(e.getMessage(), e);
        }
        
        try
        {
            executeGetRandomGUID(secure, md5);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    private void mD5ToString(MessageDigest md5)
    {
        md5.update(valueBeforeMD5.getBytes(Charset.defaultCharset()));
        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer(32);
        int b = 0;
        for (int j = 0; j < array.length; ++j)
        {
            b = array[j] & TWO_BYTES;
            if (b < PAD_BELOW)
            {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b));
        }
        valueAfterMD5 = sb.toString();
    }
    
}