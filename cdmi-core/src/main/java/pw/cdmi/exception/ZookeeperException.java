/*
 * Copyright Notice:
 *      Copyright  1998-2009, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package pw.cdmi.exception;

/**
 * 
 * @author s90006125
 *
 */
public class ZookeeperException extends RuntimeException
{
    private static final long serialVersionUID = -7755658530936615291L;

    public ZookeeperException()
    {
        super();
    }
    
    public ZookeeperException(String message)
    {
        super(message);
    }
    
    public ZookeeperException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public ZookeeperException(Throwable cause)
    {
        super(cause);
    }
    
    protected ZookeeperException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
