
/*
 * 版权声明(Copyright Notice)：
 * Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 * Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved.
 * 警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package pw.cdmi.core.exception;

/************************************************************
 * @Description:
 * <pre>企业微信登录失败异常</pre>
 * @author Rox
 * @version 3.0.1
 * @Project Alpha CDMI Service Platform, box-utils Component. 2017/12/31
 ************************************************************/
public class WxWorkAuthFailedException extends AuthFailedException {
    public WxWorkAuthFailedException() {
    }

    public WxWorkAuthFailedException(String message) {
        super(message);
    }

    public WxWorkAuthFailedException(Throwable e) {
        super(e);
    }
}
