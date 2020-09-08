package pw.cdmi.core.db;

import java.util.HashMap;
import java.util.Map;

import pw.cdmi.utils.HashTool;

/**
 * 分表对象基础类
 * 
 * 
 */
public abstract class MultiTableEntity<T>
{
    
    private T obj;
    
    protected MultiTableEntity(T obj)
    {
        this.obj = obj;
    }
    
    /**
     * 获取要处理的对象
     * 
     * @return
     */
    public Map<String, ?> buildParameterMap()
    {
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("table", this.getTableName());
        parameters.put("object", this.getObject());
        return parameters;
    }
    
    protected T getObject()
    {
        return this.obj;
    }
    
    /**
     * 获取分表字段
     * 
     * @return
     */
    protected abstract String getSplitKey();
    
    /**
     * 获取表总数
     * 
     * @return
     */
    protected int getTableCount()
    {
        return 100;
    }
    
    /**
     * 获取表名
     * 
     * @return
     */
    protected String getTableName()
    {
        return getTablePrefix() + (HashTool.apply(getSplitKey()) % getTableCount());
    }
    
    /**
     * 获取表名的前缀
     * 
     * @return
     */
    protected abstract String getTablePrefix();
}
