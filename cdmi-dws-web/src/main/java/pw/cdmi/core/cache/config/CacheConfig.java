package pw.cdmi.core.cache.config;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.transcoders.CachedData;
import org.apache.commons.lang3.ArrayUtils;

public class CacheConfig
{
    private String poolName;
    
    private String[] servers;
    
    private int port;
    
    private int maxConns = 500;
    
    private int socketTimeout = 3000;
    
    private int socketConnectTimeout = 100;
    
    private long defaultCacheTimeout = 10 * 60 * 1000;
    
    private String cacheKeyPrefix;
    
    private boolean binaryProtocal = false;
    
    private boolean aliveCheck = false;
    
    private boolean failback = true;
    
    private boolean failover = true;
    
    private int socketReceiveBuffer = 64;
    
    private int socketSendBuffer = 32;
    
    private boolean tcpNoDelay = false;
    
    private long sessionIdleTimeout = 5000;
    						 
    // 能够存放到cache中的数据的最大值
    // 默认值为1MB
    private int cacheDataMaxSize = CachedData.MAX_SIZE;
    
    private int maxQueuedNoReplyOperations = MemcachedClient.DEFAULT_MAX_QUEUED_NOPS;
    
    private boolean needCheckTransferQueueSize = false;
    private int opTimeout = 5000;
    
    public String getPoolName()
    {
        return poolName;
    }
    
    public void setPoolName(String poolName)
    {
        this.poolName = poolName;
    }
    
    public String[] getServers()
    {
        if (servers == null)
        {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return servers.clone();
    }
    
    public void setServers(String[] servers)
    {
        if (servers == null)
        {
            this.servers = ArrayUtils.EMPTY_STRING_ARRAY;
        }
        else
        {
            this.servers = servers.clone();
        }
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public int getMaxConns()
    {
        return maxConns;
    }
    
    public void setMaxConns(int maxConns)
    {
        this.maxConns = maxConns;
    }
    
    public int getSocketTimeout()
    {
        return socketTimeout;
    }
    
    public void setSocketTimeout(int socketTimeout)
    {
        this.socketTimeout = socketTimeout;
    }
    
    public int getSocketConnectTimeout()
    {
        return socketConnectTimeout;
    }
    
    public void setSocketConnectTimeout(int socketConnectTimeout)
    {
        this.socketConnectTimeout = socketConnectTimeout;
    }
    
    public long getDefaultCacheTimeout()
    {
        return defaultCacheTimeout;
    }
    
    public void setDefaultCacheTimeout(long defaultCacheTimeout)
    {
        this.defaultCacheTimeout = defaultCacheTimeout;
    }
    
    public String getCacheKeyPrefix()
    {
        return cacheKeyPrefix;
    }
    
    public void setCacheKeyPrefix(String cacheKeyPrefix)
    {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }
    
    public boolean isBinaryProtocal()
    {
        return binaryProtocal;
    }
    
    public void setBinaryProtocal(boolean binaryProtocal)
    {
        this.binaryProtocal = binaryProtocal;
    }
    
    public boolean isAliveCheck()
    {
        return aliveCheck;
    }
    
    public void setAliveCheck(boolean aliveCheck)
    {
        this.aliveCheck = aliveCheck;
    }
    
    public boolean isFailback()
    {
        return failback;
    }
    
    public void setFailback(boolean failback)
    {
        this.failback = failback;
    }
    
    public boolean isFailover()
    {
        return failover;
    }
    
    public void setFailover(boolean failover)
    {
        this.failover = failover;
    }
    
    public int getSocketReceiveBuffer()
    {
        return socketReceiveBuffer;
    }
    
    public void setSocketReceiveBuffer(int socketReceiveBuffer)
    {
        this.socketReceiveBuffer = socketReceiveBuffer;
    }
    
    public int getSocketSendBuffer()
    {
        return socketSendBuffer;
    }
    
    public void setSocketSendBuffer(int socketSendBuffer)
    {
        this.socketSendBuffer = socketSendBuffer;
    }
    
    public boolean isTcpNoDelay()
    {
        return tcpNoDelay;
    }
    
    public void setTcpNoDelay(boolean tcpNoDelay)
    {
        this.tcpNoDelay = tcpNoDelay;
    }
    
    public long getSessionIdleTimeout()
    {
        return sessionIdleTimeout;
    }
    
    public void setSessionIdleTimeout(long sessionIdleTimeout)
    {
        this.sessionIdleTimeout = sessionIdleTimeout;
    }
    						 
    public int getCacheDataMaxSize()
    {
        return cacheDataMaxSize;
    }
    
    public void setCacheDataMaxSize(int cacheDataMaxSize)
    {
        this.cacheDataMaxSize = cacheDataMaxSize;
    }
    
    public int getMaxQueuedNoReplyOperations()
    {
        return maxQueuedNoReplyOperations;
    }
    
    public void setMaxQueuedNoReplyOperations(int maxQueuedNoReplyOperations)
    {
        this.maxQueuedNoReplyOperations = maxQueuedNoReplyOperations;
    }

    public int getOpTimeout()
    {
        return opTimeout;
    }

    public void setOpTimeout(int opTimeout)
    {
        this.opTimeout = opTimeout;
    }

    public boolean isNeedCheckTransferQueueSize()
    {
        return needCheckTransferQueueSize;
    }

    public void setNeedCheckTransferQueueSize(boolean needCheckTransferQueueSize)
    {
        this.needCheckTransferQueueSize = needCheckTransferQueueSize;
    }
    
}
