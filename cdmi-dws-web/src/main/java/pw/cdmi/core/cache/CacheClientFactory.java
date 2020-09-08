package pw.cdmi.core.cache;

import net.rubyeye.xmemcached.hw.XMemcachedClient;
import net.rubyeye.xmemcached.transcoders.CachedData;
import pw.cdmi.core.cache.config.CacheConfig;

public final class CacheClientFactory
{
    private CacheClientFactory()
    {
    
    }
    
    public static CacheClient getInstance(CacheConfig config)
    {
        return new CacheClient(config);
    }
    
    /**
     * 创建一个memcache连接池
     * 
     * @param poolName 连接池名称
     * @param serverIps 服务器ip列表，以“;”分割
     * @param serverPort 服务器端口
     * @param maxConns 最大连接数
     * @param socketTimeout socket超时，单位毫秒
     * @param socketConnectTimeout socket连接超时，单位毫秒
     * @param defaultCacheTimeout 默认cache存活期，单位毫秒
     * @param cacheKeyPrefix cache key前缀，用于多子系统使用同一memcache时，防止冲突
     * @param binaryProtocal 是否使用二进制协议
     * @param aliveCheck 是否开启存活检查
     * @param failback 是否开启failback
     * @param failover 是否开启failover
     * @param opTimeout 操作超时
     * @return
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public static CacheClient getInstance(String poolName, String serverIps, int serverPort, int maxConns,
        int socketTimeout, int socketConnectTimeout, long defaultCacheTimeout, String cacheKeyPrefix,
        boolean binaryProtocal, boolean aliveCheck, boolean failback, boolean failover, int opTimeout)
    {
        return getInstance(poolName,
            serverIps,
            serverPort,
            maxConns,
            socketTimeout,
            socketConnectTimeout,
            defaultCacheTimeout,
            cacheKeyPrefix,
            binaryProtocal,
            aliveCheck,
            failback,
            failover,
            opTimeout,
            CachedData.MAX_SIZE,
            XMemcachedClient.DEFAULT_MAX_QUEUED_NOPS,
            false);
    }
    
    /**
     * 
     * @param poolName
     * @param serverIps
     * @param serverPort
     * @param maxConns
     * @param socketTimeout
     * @param socketConnectTimeout
     * @param defaultCacheTimeout
     * @param cacheKeyPrefix
     * @param binaryProtocal
     * @param aliveCheck
     * @param failback
     * @param failover
     * @param opTimeout
     * @param cacheDataMaxSize
     * @param maxQueuedNoReplyOperations 能够存放到cache中的数据的最大值
     * @return
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public static CacheClient getInstance(String poolName, String serverIps, int serverPort, int maxConns,
        int socketTimeout, int socketConnectTimeout, long defaultCacheTimeout, String cacheKeyPrefix,
        boolean binaryProtocal, boolean aliveCheck, boolean failback, boolean failover, int opTimeout,
        int cacheDataMaxSize, int maxQueuedNoReplyOperations, boolean needCheckTransferQueueSize)
    {
        CacheConfig config = new CacheConfig();
        config.setPoolName(poolName);
        String[] servers = serverIps.split(";");
        config.setServers(servers);
        config.setPort(serverPort);
        config.setMaxConns(maxConns);
        config.setSocketTimeout(socketTimeout);
        config.setSocketConnectTimeout(socketConnectTimeout);
        config.setDefaultCacheTimeout(defaultCacheTimeout);
        config.setCacheKeyPrefix(cacheKeyPrefix);
        config.setBinaryProtocal(binaryProtocal);
        config.setAliveCheck(aliveCheck);
        config.setFailback(failback);
        config.setFailover(failover);
        config.setOpTimeout(opTimeout);
        config.setCacheDataMaxSize(cacheDataMaxSize);
        config.setMaxQueuedNoReplyOperations(maxQueuedNoReplyOperations);
        config.setNeedCheckTransferQueueSize(needCheckTransferQueueSize);
        return new CacheClient(config);
    }
}
