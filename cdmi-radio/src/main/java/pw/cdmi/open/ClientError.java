package pw.cdmi.open;

import pw.cdmi.exception.ErrorMessage;

/************************************************************
 * TODO(对类的简要描述说明 – 必须).
 * TODO(对类的作用含义说明 – 可选).
 * TODO(对类的使用方法说明 – 可选).
 * 
 * @author WUWEI
 * @version iSoc Service Platform, 2015年3月1日
 ************************************************************/
public enum ClientError implements ErrorMessage {
	
	SiteDomainExisted(1000, "接入应用的域名已经在系统中存在，请重新选择应用的域名"),
	/** 400 Bad Request */
	InvalidCompany(1000, "用户未激活，请联系管理员进行激活"),
	
	/** 400 Bad Request */
	AccountDisabled(1000, "用户未激活，请联系管理员进行激活"),
	
	BadCredentials(1000, "用户名或密码错误，请重试"),
	
	UsernameNotFound(1000, "User has no GrantedAuthority"),
	
	/** 400 Bad Request */
	NeedSchoolID(1001, "请求中未包含学校的OpenId.."),

	/** 400 Bad Request */
	ErrorPermissionType(1002, "The Request permission type is wrong."),

	/** 400 Bad Request */
	NoSuchUserSort(1003, "用户类别不可识别或系统尚不支持."),

	/** 400 Bad Request */
	NoFoundData(1004, "系统没有找到请求对应的数据."),

	/** 400 Bad Request */
	NoSuchNewsSort(1005, "信息类别不可识别或系统尚不支持."),

	/** 400 Bad Request */
	NoSuchPaymentItem(1006, "要购买的元宝包不可识别或系统尚不支持."),

	/** 400 Bad Request */
	NoSuchPayOrder(1007, "请求中包含的支付订单编号并不存在, 或订单已被删除."),

	/** 400 Bad Request */
	CompletedPayOrder(1008, "支付订单已经完成，不可进行操作."),

	/** 400 Bad Request */
	InvalidUserId(1009, "请求中所包含的用户编号不符合要求, 请检查."),

	/** 400 Bad Request */
	InvalidPayOrderId(1010, "请求中所包含的支付订单编号不符合要求, 请检查."),

	/** 400 Bad Request */
	InvalidNewsId(1011, "请求中所包含的信息编号不符合要求, 请检查."),

	/** 400 Bad Request */
	InvalidUserShopId(1012, "请求中所包含的用户企业编号不符合要求, 请检查."),

	/** 400 Bad Request */
	InvalidParameter(1013, "请求中所包含的参数是无效的, 请检查."),

	/** 400 Bad Request */
	InvalidRequest(1014, "The request is invalID,  Please check paramters."),

	/** 400 Bad Request */
	MissingMandatoryParameter(1015, "请求中丢失了必要的参数, 请检查."),

	UnSupportUsage(1016, "系统不支持进行该操作，前端逻辑存在错误或为非法请求."),

	/** 400 Bad Request */
	IncompleteBody(1017, "请求体包含的内容不完整，服务端无法识别, 请检查."),

	/** 401 Unauthorized */
	NoSuchAccessKey(1018, "请求头中并没有包含对应的访问授权码."),

	/** 401 Unauthorized */
	ErrorSignature(1019, "错误的请求签名."),

	/** 401 Unauthorized */
	NoPermissions(1020, "请求中包含的访问授权码并不具有访问本资源接口的权限."),

	/** 403 Forbidden */
	NoRelatedNews(1021, "该请求所依赖的信息没有能找到，该信息可能已经被删除. 请求将不被受理."),

	/** 403 Forbidden */
	NoRelatedUser(1022, "该请求所依赖的用户在系统中并不存在. 请求将不被受理."),

	/** 403 Forbidden */
	NoUserPipelines(1023, "用户尚未绑定任何微博站点, 请求将不被受理. 请返回先绑定用户的微博站点后, 重试."),

	/** 404 Not Found */
	NoSuchUser(1024, "所请求的用户并不存在."),

	/** 404 Not Found */
	NoSuchTeacher(1025, "所请求的老师并不存在."),

	/** 409 Conflict */
	UserEmailAlreadyExists(1026, "UserEmailAlreadyExists"),

	/** 409 Conflict */
	DataAlreadyExists(1027, "DataAlreadyExists"),

	/** 409 Conflict */
	UserMobileAlreadyExists(1028, "UserMobileAlreadyExists"),

	/** 409 Conflict */
	ErrorUserPwd(1029, "The login name and passord is not consistent."),

	/** 409 Conflict */
	DataConsistent(1030, "客户端的数据状态与服务端对应的数据状态不一致."),

	/** 500 Internal Server Error */
	InternalError(-1,
			"The Server encountered an internal error.Please try again.");

	private String describe;
	private int code;

	private ClientError(int code, String describe) {
		this.code = code;
		this.describe = describe;
	}


	@Override
	public String getMessage() {
		return this.describe;
	}
}

