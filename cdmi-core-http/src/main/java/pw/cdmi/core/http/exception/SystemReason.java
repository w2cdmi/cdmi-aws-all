package pw.cdmi.core.http.exception;

import pw.cdmi.core.http.HttpStatus;
import pw.cdmi.error.ErrorReason;

/**
 * **********************************************************
 * <br/>
 * 服务端的错误编码为1000-2000.<br/>
 * 
 * @author 伍伟
 * @version core Service Platform, 2016年6月30日
 ***********************************************************
 */
public enum SystemReason implements ErrorReason {

	ResultUnrecognized(HttpStatus.INTERNAL_SERVER_ERROR,1000,"Received data cannot be resolved"),
    SQLError(HttpStatus.INTERNAL_SERVER_ERROR,1001, "The Server encountered an internal error.Please try again."),
    InconsistentWithDesign(HttpStatus.INTERNAL_SERVER_ERROR,1002,"Do not meet the design requirements"),
    NewsNoDistribute(HttpStatus.INTERNAL_SERVER_ERROR,1003,"信息没有找到分发渠道，这和设计不符，可能数据被篡改或系统存在BUG."),
    DBDataExist(HttpStatus.INTERNAL_SERVER_ERROR,1004,"数据库中记录已经存在."),
    NullData(HttpStatus.INTERNAL_SERVER_ERROR,1005,"数据为空."),
    ReadOnly(HttpStatus.INTERNAL_SERVER_ERROR,1006,"目标对象为只读状态."),
    InvalidAccessKeyAndSecretKey(HttpStatus.INTERNAL_SERVER_ERROR,1007,"无效的AccessKey和SecretKey"),
    InvalidRequest(HttpStatus.INTERNAL_SERVER_ERROR,1008, "无效的请求."),
    MissingAnnotationParameter(HttpStatus.INTERNAL_SERVER_ERROR,1009,"代码中注解的参数缺少"),
    ConfigurationError(HttpStatus.INTERNAL_SERVER_ERROR,1010,"配置错误"),
    UnImplemented(HttpStatus.NOT_IMPLEMENTED,1016, "服務端尚未實現用戶請求所涉及的功能."),
    ClassCastError(HttpStatus.INTERNAL_SERVER_ERROR,1011,"类型转换错误");

    private final int code;
    private final String reason;
    private final HttpStatus status;
    
    private SystemReason(HttpStatus status, int code, String reason){
        this.code = code;
        this.reason = reason;
        this.status = status;
    }

    
    public String getReason() {
        return this.reason;
    }


    @Override
    public int getHttpStatus() {
        return this.status.value();
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
