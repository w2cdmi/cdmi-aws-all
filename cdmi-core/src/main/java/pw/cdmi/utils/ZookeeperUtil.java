package pw.cdmi.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pw.cdmi.exception.ZookeeperException;

public final class ZookeeperUtil
{
    private ZookeeperUtil()
    {
    
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperUtil.class);
    
    /**
     * 创建zk节点。如果父节点不存在，自动创建父节点：父节点数据为byte[0]
     * 
     * @param client
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public static boolean safeCreateNode(CuratorFramework client, String path, byte[] data) throws ZookeeperException
    {
        String[] paths = path.split("/");
        StringBuilder curPath = new StringBuilder("");
        String s = null;
        for (int i = 1; i < paths.length - 1; i++)
        {
            s = paths[i];
            if (StringUtils.isBlank(s))
            {
                continue;
            }
            curPath.append('/').append(s);
            createNode(client, curPath);
        }
        try
        {
            client.create().forPath(path, data);
            return true;
        }
        catch (KeeperException.NodeExistsException e)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("node '" + path + "' is already existed.");
            }
            return false;
        } catch (Exception e) {
            throw new ZookeeperException(e);
        }
    }
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    private static void createNode(CuratorFramework client, StringBuilder curPath) throws ZookeeperException
    {
        try
        {
            client.create().forPath(curPath.toString(), new byte[0]);
        }
        catch (KeeperException.NodeExistsException e)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("node '" + curPath + "' is already existed.");
            }
        } catch (Exception e) {
            throw new ZookeeperException(e);
        }
    }
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public static void createNode(CuratorFramework client, String curPath) throws Exception
    {
        try
        {
            client.create().forPath(curPath, new byte[0]);
        }
        catch (KeeperException.NodeExistsException e)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("node '" + curPath + "' is already existed.");
            }
        }
    }
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public static void deleteNode(CuratorFramework client, String curPath) throws Exception
    {
        try
        {
            client.delete().forPath(curPath);
        }
        catch (KeeperException.NoNodeException e)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("node '" + curPath + "' is not existed.");
            }
        }
    }
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public static String createEphemeralSequentialNode(CuratorFramework client, String path, byte[] data)
        throws Exception
    {
        String createdPath = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data);
        return createdPath;
    }
    
}
