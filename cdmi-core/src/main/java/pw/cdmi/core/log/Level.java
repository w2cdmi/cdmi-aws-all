package pw.cdmi.core.log;

import java.util.Locale;

public enum Level
{
    /**
     * 调试
     */
    DEBUG((byte)3),
    /**
     * 普通
     */
    INFO((byte)0),
    /**
     * 警告
     */
    WARN((byte)2),
    /**
     * 错误
     */
    ERROR((byte)1);
    
    Level(byte value)
    {
        this.value = value;
    }
    
    private byte value;
    
    public byte getValue()
    {
        return this.value;
    }
    
    public String toShowString()
    {
        return this.toString().toLowerCase(Locale.ENGLISH);
    }
    
    public static Level build(String name)
    {
        return Level.valueOf(name.toUpperCase(Locale.ENGLISH));
    }
    
    public static Level build(byte value)
    {
        switch(value)
        {
            case 0:
                return INFO;
            case 1:
                return ERROR;
            case 2:
                return WARN;
            case 3:
                return DEBUG;
            default:
                return null;
        }
    }
}

