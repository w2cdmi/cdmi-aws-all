/* 
 * 版权声明(Copyright Notice)：
 *     Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 *     Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved. 
 * 
 *     警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业目
 */ 
package pw.cdmi.exception;

/************************************************************
 * @Description:
 * <pre>
 * 对RPC服务端异常的说明.<br/>
 * </pre>
 * @author    伍伟
 * @version   3.0.1
 * @Project   Alpha CDMI Service Platform, cdmi-core Component. 2017年5月29日
 ************************************************************/
public class RpcServiceException extends RpcException{
    private static final long serialVersionUID = -4891626236917914256L;
    
    /**
     * 创建一个 RpcServiceException 对象实例.
     * 
     * @param code
     * @param message
     */
    
    public RpcServiceException(String code, String message) {
        super(code, message);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * 创建一个 RpcServiceException 对象实例.
     * 
     * @param code
     * @param message
     * @param cause
     */
    public RpcServiceException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
    
    /**
     * 
     * 创建一个 RpcServiceException 对象实例.
     * 
     * @param code
     * @param message
     * @param reason
     */
    public RpcServiceException(String code, String message, String reason) {
        super(code, message, reason);
    }
    
    /**
     * 
     * 创建一个 RpcServiceException 对象实例.
     * 
     * @param code
     * @param message
     * @param reason
     * @param cause
     */
    public RpcServiceException(String code, String message, String reason, Throwable cause) {
        super(code, message, reason, cause);
    }
}
