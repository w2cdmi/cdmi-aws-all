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
package pw.cdmi.core.exception;

/**
 * @author s90006125
 */
public class InnerException extends CustomException {
    private static final long serialVersionUID = 7519496716551431166L;

    public InnerException(String message) {
        super(message);
    }

    public InnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InnerException(Throwable cause) {
        super(cause);
    }
}
