/* 
 * 版权声明(Copyright Notice)：
 *     Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 *     Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved. 
 * 
 *     警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package pw.cdmi.auth;

/************************************************************
 * @Description:
 * <pre>
 * 基于Restful请求的验证类型，支持AWS类型.<br/>
 * </pre>
 * @author    伍伟
 * @version   3.0.1
 * @Project   Alpha CDMI Service Platform, box-utils Component. 2017年4月21日
 ************************************************************/
public class Principal {
	
	public static final Principal AllUsers = new Principal("*");
	private final String id;
	
    public Principal(String identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Null account ID specified");
        }
        this.id = identifier.replaceAll("-", "");
    }
    
    public String getProvider() {
        return "AWS";
    }
    
	public String getIdentifier(){
		return this.id;
	}
}
