package pw.cdmi.core.http.exception;

import pw.cdmi.error.ErrorMessage;

public enum GlobalHttpClientError implements ErrorMessage{
	
	/** 400 Bad Request */
	InvalidRequest("请求服务地址路径错误"),
	
	/** 400 Bad Request */
	MissingMandatoryParameter("请求中丢失了必要的参数, 请检查."),
	
	/** 400 Bad Request */
	InvalidParameter("请求中包含的参数不符合要求"),

	/** 400 Bad Request */
	IncompleteBody("请求体包含的内容不完整，服务端无法识别, 请检查."),

	/** 401 Unauthorized */
	NoSuchAccessKey("请求头中并没有包含对应的访问授权码."),

	/** 401 Unauthorized */
	ErrorSignature("错误的请求签名."),

	/** 401 Unauthorized */
	NoPermissions("当前请求包含的用户凭证信息不具有当前操作的权限."),
	
	/** 401 Incomplete */
	Incomplete("当前用户信息不完整，需进行补充"),
	
	BadCredentials("用户凭证信息错误"),
	
	UnSupportUsage("系统不支持进行该操作，前端逻辑存在错误或为非法请求."),
	
	ResourceNotFound("您要操作的资源没有能找到.."),
	
	/** 500 Internal Server Error */
	InternalError("服务器存在未知错误.");
	
	private String describe;

	private GlobalHttpClientError(String describe) {
		this.describe = describe;
	}


	public String getMessage() {
		return this.describe;
	}
}
