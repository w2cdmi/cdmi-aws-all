/**
 * 
 */
package pw.cdmi.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;

/**
 * @author s00108907
 *         
 */
public class ZookeeperServer
{
    private CuratorFramework zkClient;
    
	public static final String AUTH_TYPE = "digest";
    
    public static final String AUTH = "zkUser:sharedrive";																							  
    private int baseSleepTimeMs = 1000;
    
    private int maxSleepTimeMs = 30000;
    
    private int maxRetries = 10;
    
    private int connectionTimeoutMs = 15000;
    
    private int sessionTimeoutMs = 60000;
    
    private String connectString = "localhost:2181";
    
    /**
     * 获取Zookeeper连接client对象
     * 
     * @return
     */
    public CuratorFramework getClient()
    {
        return zkClient;
    }
    
    /**
     * 初始化加载Zookeeper连接client对象
     */
    public void init()
    {
        BoundedExponentialBackoffRetry retryPolicy = new BoundedExponentialBackoffRetry(baseSleepTimeMs,
            maxSleepTimeMs, maxRetries);
        CuratorFramework newClient = CuratorFrameworkFactory.builder()
            .connectString(connectString)
            .retryPolicy(retryPolicy)
            .connectionTimeoutMs(connectionTimeoutMs)
            .sessionTimeoutMs(sessionTimeoutMs)
            .build();
        newClient.start();
        zkClient = newClient;
    }
    
    public void destroy()
    {
        if (zkClient != null)
        {
            zkClient.close();
        }
    }
    
    public void setBaseSleepTimeMs(int baseSleepTimeMs)
    {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }
    
    public void setMaxSleepTimeMs(int maxSleepTimeMs)
    {
        this.maxSleepTimeMs = maxSleepTimeMs;
    }
    
    public void setMaxRetries(int maxRetries)
    {
        this.maxRetries = maxRetries;
    }
    
    public void setConnectionTimeoutMs(int connectionTimeoutMs)
    {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }
    
    public void setSessionTimeoutMs(int sessionTimeoutMs)
    {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
    
    public void setConnectString(String connectString)
    {
        this.connectString = connectString;
    }
}
