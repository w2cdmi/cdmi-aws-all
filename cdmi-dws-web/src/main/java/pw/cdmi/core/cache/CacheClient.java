package pw.cdmi.core.cache;

import com.google.code.yanf4j.core.impl.StandardSocketOption;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.hw.XMemcachedClient;
import net.rubyeye.xmemcached.hw.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.cdmi.core.cache.config.CacheConfig;
import pw.cdmi.core.cache.exception.CacheException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author q90003805
 *         
 */
public class CacheClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheClient.class);
    private static final int REALTIME_MAXDELTA = 60 * 60 * 24 * 30;
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private XMemcachedClient mcc;
    
    private long defaultCacheTimeout;
    
    private String cacheKeyPrefix;
    
    CacheClient(CacheConfig config)
    {
        config.setTcpNoDelay(true);
        
        List<InetSocketAddress> addressList = new LinkedList<InetSocketAddress>();
        InetSocketAddress inetSocketAddress = null;
        for (String s : config.getServers())
        {
            inetSocketAddress = new InetSocketAddress(s, config.getPort());
            addressList.add(inetSocketAddress);
        }
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(addressList);
        builder.setName(config.getPoolName());
        builder.setConnectionPoolSize(config.getMaxConns());
        builder.setConnectTimeout(config.getSocketConnectTimeout());
        builder.setSocketOption(StandardSocketOption.SO_RCVBUF, config.getSocketReceiveBuffer() * 1024);
        builder.setSocketOption(StandardSocketOption.SO_SNDBUF, config.getSocketSendBuffer() * 1024);
        builder.setSocketOption(StandardSocketOption.TCP_NODELAY, config.isTcpNoDelay());
        builder.getConfiguration().setSessionIdleTimeout(config.getSessionIdleTimeout());
        builder.getConfiguration().setSoTimeout(config.getSocketTimeout());
        builder.setOpTimeout(config.getOpTimeout());
        if (config.isBinaryProtocal())
        {
            builder.setCommandFactory(new BinaryCommandFactory());
        }
        builder.setTranscoder(new SerializingTranscoder(config.getCacheDataMaxSize()));
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        // 最大读缓存队列
        builder.setMaxQueuedNoReplyOperations(config.getMaxQueuedNoReplyOperations());
        builder.setNeedCheckTransferQueueSize(config.isNeedCheckTransferQueueSize());
        try
        {
            mcc = builder.build();
        }
        catch (Exception e)
        {
            throw new CacheException("can not build MemcachedClient", e);
        }
        this.defaultCacheTimeout = config.getDefaultCacheTimeout();
        this.cacheKeyPrefix = config.getCacheKeyPrefix();
    }
    
    public void destroy() throws IOException
    {
        if (mcc != null)
        {
            mcc.shutdown();
            mcc = null;
        }
    }
    
    /**
     * 增加缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean addCache(String key, Object obj)
    {
        return addCache(key, obj, defaultCacheTimeout);
    }
    
    /**
     * 增加缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 过期绝对时间
     * @return
     */
    public boolean addCache(String key, Object obj, Date expireTime)
    {
        return addCache(key, obj, expireTime.getTime());
    }
    
    /**
     * 增加缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 毫秒数,大于30天为绝对时间，否则为相对时间
     * @return
     */
    public boolean addCache(String key, Object obj, long expireTime)
    {
        int expireTimeInSec = getDeltaTime(expireTime);
        try
        {
            return mcc.add(cacheKeyPrefix + key, expireTimeInSec, obj);
        }
        catch (Exception e)
        {
            throw new CacheException("add value to memcache failed, expireTimeInSec is " + expireTimeInSec,
                e);
        }
    }
    
    /**
     * 增加缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean addCacheNoExpire(String key, Object obj)
    {
        
        try
        {
            return mcc.add(cacheKeyPrefix + key, 0, obj);
        }
        catch (Exception e)
        {
            throw new CacheException("add value to memcache failed", e);
        }
    }
    
    /**
     * 获得缓存
     * 
     * @param key
     * @return
     */
    public Object getCache(String key)
    {
        try
        {
            return mcc.get(cacheKeyPrefix + key);
        }
        catch (Exception e)
        {
             throw new CacheException("get value from memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 批量获取缓存
     * 
     * @param keys
     * @return
     */
    public Map<String, Object> getMulti(String[] keys)
    {
		if (LOGGER.isDebugEnabled())
        {
            for (int i = 0; i < keys.length; i++)
            {
                LOGGER.debug("Get [" + cacheKeyPrefix + keys[i] + "] From Cache.");
            }
        }	
        List<String> actualKeys = new ArrayList<String>(keys.length);
        for (int i = 0; i < keys.length; i++)
        {
            actualKeys.add(cacheKeyPrefix + keys[i]);
        }
        try
        {
            return mcc.get(actualKeys);
        }
        catch (Exception e)
        {
 throw new CacheException("get value from memcache failed, keys is " + actualKeys, e);
        }
    }
    
    /**
     * 获得缓存
     * 
     * @param key
     * @return
     */
    public <T> GetsResponse<T> getsCache(String key)
    {
        try
        {
            return mcc.gets(cacheKeyPrefix + key);
        }
        catch (Exception e)
        {
throw new CacheException("gets value from memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 放入缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean setCache(String key, Object obj)
    {
        return setCache(key, obj, defaultCacheTimeout);
    }
    
    /**
     * 放入缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 过期绝对时间
     * @return
     */
    public boolean setCache(String key, Object obj, Date expireTime)
    {
        return setCache(key, obj, expireTime.getTime());
    }
    
    /**
     * 放入缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 毫秒数,大于30天为绝对时间，否则为相对时间
     * @return
     */
    public boolean setCache(String key, Object obj, long expireTime)
    {
	    if (LOGGER.isDebugEnabled())
        {
            SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
            LOGGER.debug("Set [" + cacheKeyPrefix + key + "] To Cache. alive time is "
                + format.format(new Date(expireTime)));
        }	 
        int expireTimeInSec = getDeltaTime(expireTime);
        try
        {
            return mcc.set(cacheKeyPrefix + key, expireTimeInSec, obj);
        }
        catch (Exception e)
        {
            throw new CacheException("set value to memcache failed, key is " + cacheKeyPrefix + key
                + ", expireTimeInSec is " + expireTimeInSec, e);
        }
    }
    
    /**
     * 放入缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean setCacheNoExpire(String key, Object obj)
    {
        try
        {
            return mcc.set(cacheKeyPrefix + key, 0, obj);
        }
        catch (Exception e)
        {
            throw new CacheException("set value to memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 使用CAS操作更新缓存，根据返回值判断是否更新成功，更新失败后可尝试获取新值进行再次更新
     * 
     * @param key
     * @param obj
     * @param casUnique
     * @return
     */
    public boolean casCache(String key, Object obj, long casUnique)
    {
        return casCache(key, obj, defaultCacheTimeout, casUnique);
    }
    
    /**
     * 使用CAS操作更新缓存，根据返回值判断是否更新成功，更新失败后可尝试获取新值进行再次更新
     * 
     * @param key
     * @param obj
     * @param expireTime 过期绝对时间
     * @param casUnique
     * @return
     */
    public boolean casCache(String key, Object obj, Date expireTime, long casUnique)
    {
        return casCache(key, obj, expireTime.getTime(), casUnique);
    }
    
    /**
     * 使用CAS操作更新缓存，根据返回值判断是否更新成功，更新失败后可尝试获取新值进行再次更新
     * 
     * @param key
     * @param obj
     * @param expireTime 毫秒数,大于30天为绝对时间，否则为相对时间
     * @param casUnique
     * @return
     */
    public boolean casCache(String key, Object obj, long expireTime, long casUnique)
    {
        if (LOGGER.isDebugEnabled())
        {
            SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
            LOGGER.debug("Cas [" + cacheKeyPrefix + key + "] To Cache. alive time is "
                + format.format(new Date(expireTime)));
        } 
        int expireTimeInSec = getDeltaTime(expireTime);
        try
        {
            return mcc.cas(cacheKeyPrefix + key, expireTimeInSec, obj, casUnique);
        }
        catch (Exception e)
        {
            throw new CacheException("cas value to memcache failed, key is " + cacheKeyPrefix + key
                + ", expireTimeInSec is " + expireTimeInSec, e);
        }
    }
    
    /**
     * 使用CAS操作更新缓存，根据返回值判断是否更新成功，更新失败后可尝试获取新值进行再次更新
     * 
     * @param key
     * @param obj
     * @param casUnique
     * @return
     */
    public boolean casCacheNoExpire(String key, Object obj, long casUnique)
    {
        
        try
        {
            return mcc.cas(cacheKeyPrefix + key, 0, obj, casUnique);
        }
        catch (Exception e)
        {
            throw new CacheException("cas value to memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 删除缓存
     * 
     * @param key
     * @return
     */
    public boolean deleteCache(String key)
    {
                if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Delete [" + cacheKeyPrefix + key + "] From Cache.");
        }
        try
        {
            boolean deleted = mcc.delete(cacheKeyPrefix + key);
            if (!deleted)
            {
                if (mcc.get(cacheKeyPrefix + key) == null)
                {
                    // 如果KEY已经不存在，则表示删除成功，返回true
                    return true;
                }
                return false;
            }
        }
        catch (Exception e)
        {
			throw new CacheException("delete value from memcache failed, key is " + cacheKeyPrefix + key, e);
        }
        return true;
    }
    
    /**
     * 更新缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean replaceCache(String key, Object obj)
    {
        return replaceCache(key, obj, defaultCacheTimeout);
    }
    
    /**
     * 更新缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 过期绝对时间
     * @return
     */
    public boolean replaceCache(String key, Object obj, Date expireTime)
    {
        return replaceCache(key, obj, expireTime.getTime());
    }
    
    /**
     * 更新缓存
     * 
     * @param key
     * @param obj
     * @param expireTime 毫秒数,大于30天为绝对时间，否则为相对时间
     * @return
     */
    public boolean replaceCache(String key, Object obj, long expireTime)
    {
        if (LOGGER.isDebugEnabled())
        {
            SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
            LOGGER.debug("Replace Cache [" + cacheKeyPrefix + key + "]. alive time is "
                + format.format(new Date(expireTime)));
        } 
        int expireTimeInSec = getDeltaTime(expireTime);
        try
        {
            return mcc.replace(cacheKeyPrefix + key, expireTimeInSec, obj);
        }
        catch (Exception e)
        {
            throw new CacheException("replace value to memcache failed, key is " + cacheKeyPrefix + key
                + ", expireTimeInSec is " + expireTimeInSec, e);
        }
    }
    
    /**
     * 更新缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean replaceCacheNoExpire(String key, Object obj)
    {
             if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Replace Cache [" + cacheKeyPrefix + key + "] no expire.");
        }   
        try
        {
            return mcc.replace(cacheKeyPrefix + key, 0, obj);
        }
        catch (Exception e)
        {
throw new CacheException("replace value to memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 判断对象是否存在
     * 
     * @param key
     * @return
     */
    public boolean checkCacheExists(String key)
    {
        try
        {
            return mcc.get(cacheKeyPrefix + key) != null;
        }
        catch (Exception e)
        {
            throw new CacheException("check value from memcache failed, key is " + cacheKeyPrefix + key, e);
        }
    }
    
    /**
     * 获取cache key前缀
     * 
     * @return
     */
    public String getCacheKeyPrefix()
    {
        return cacheKeyPrefix;
    }
    
    private int getDeltaTime(long expireTime)
    {
        int expireTimeInSec = ((Long) (expireTime / 1000)).intValue();
        long currentTimeMillis = System.currentTimeMillis();
        if (expireTimeInSec > REALTIME_MAXDELTA)
        {
            expireTimeInSec -= ((Long) (currentTimeMillis / 1000)).intValue();
        }
        if (expireTimeInSec < 0)
        {
            throw new IllegalArgumentException("error expire time " + expireTime
                + ", its before system currnet time " + currentTimeMillis);
        }
        return expireTimeInSec;
    }
}
