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
 * 系统内置的RPC异常.<br/>
 * 该异常信息包含了应用对异常的分类编号、原因说明.<br/>
 * </pre>
 * @author    伍伟
 * @version   3.0.1
 * @Project   Alpha CDMI Service Platform, cdmi-core Component. 2017年5月29日
 ************************************************************/
public class RpcException extends RuntimeException{
    private static final long serialVersionUID = -5282973330582819378L;
    
    private String reason = null;
    private String code = null;
    
    public RpcException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public RpcException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    public RpcException(String code, String message, String reason) {
        super(message);
        this.code = code;
        this.reason = reason;
    }
    
    public RpcException(String code, String message, String reason, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.reason = reason;
    }
    
    /**
     * reason 异常出现的原因说明
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * code. 系统对该异常的分类编码
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
