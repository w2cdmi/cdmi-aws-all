package pw.cdmi.core.http.exception;


import pw.cdmi.core.http.HttpStatus;
import pw.cdmi.error.ErrorReason;

public enum ClientReason implements ErrorReason{

    Type_Not_Match(HttpStatus.BAD_REQUEST, 1001, "参数[:?1]的数据类型格式与服务接口规范不符"),
    Over_Range(HttpStatus.BAD_REQUEST,1002, "参数[:?1]的取值范围与服务接口规范不符"),
    MissingMandatoryParameter(HttpStatus.BAD_REQUEST,1003, "参数[:?1]的取值范围与服务接口规范不符"),
    ErrorPermissionType(HttpStatus.BAD_REQUEST,1004, "The Request permission type is wrong."),
    NoFoundData(HttpStatus.BAD_REQUEST,1004, "系统没有找到请求对应的数据."),
    InvalidParameter(HttpStatus.BAD_REQUEST,1013, "请求中所包含的参数是无效的, 请检查."),
    InvalidRequest(HttpStatus.BAD_REQUEST,1014, "The request is invalID,  Please check paramters."),
    UnSupportUsage(HttpStatus.BAD_REQUEST,1016, "系统不支持进行该操作，前端逻辑存在错误或为非法请求."),
    IncompleteBody(HttpStatus.BAD_REQUEST,1017, "请求体包含的内容不完整，服务端无法识别, 请检查."),
    ErrorSignature(HttpStatus.BAD_REQUEST,1019, "错误的请求签名."),
    NoPermissions(HttpStatus.BAD_REQUEST,1020, "请求中包含的访问授权码并不具有访问本资源接口的权限."),
    UserEmailAlreadyExists(HttpStatus.BAD_REQUEST,1026, "UserEmailAlreadyExists"),
    DataAlreadyExists(HttpStatus.BAD_REQUEST,1027, "DataAlreadyExists"),
    UserMobileAlreadyExists(HttpStatus.BAD_REQUEST,1028, "UserMobileAlreadyExists"),
    DataConsistent(HttpStatus.BAD_REQUEST,1030, "客户端的数据状态与服务端对应的数据状态不一致."),
    NotFoundCountryByDomain(HttpStatus.BAD_REQUEST,1030, "不能通过输入的国家域名获取到对应的国家信息.");
    
    private final int code;
    private final String reason;
    private final HttpStatus status;
    
    private ClientReason(HttpStatus status, int code, String reason){
        this.code = code;
        this.reason = reason;
        this.status = status;
    }

    
    public String getReason() {
        return this.reason;
    }


    public int getHttpStatus() {
        return this.status.value();
    }


    public int getCode() {
        return this.code;
    }
}
