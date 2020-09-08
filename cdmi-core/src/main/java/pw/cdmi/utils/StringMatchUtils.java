package pw.cdmi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****************************************************
 * 字符串检验工具类，判断字符串的格式。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, Aug 18, 2014
 ***************************************************/
public class StringMatchUtils {

	/**
	 * 简单判断字符串是否为手机号码
	 * @param mobile 待判断的手机号码字符串
	 * @return true为手机号码，false为非法手机号码
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	public static boolean isNotMobile(String mobile) { 
		return !isMobile(mobile);
	}
	/**
	 * 简单判断字符串是否为邮件地址
	 * @param mobile 待判断的邮件地址字符串
	 * @return true为邮件地址，false为非法邮件地址
	 */
	public static boolean isEmail(String email) {
//		1. 必须包含一个并且只有一个符号“@” 
//		2. 第一个字符不得是“@”或者“.” 
//		3. 不允许出现“@.”或者.@ 
//		4. 结尾不得是字符“@”或者“.” 
//		5. 允许“@”前的字符中出现“＋” 
//		6. 不允许“＋”在最前面，或者“＋@” 
		Pattern p = Pattern.compile("^(\\w+((-\\w+)|(\\.\\w+))*)\\+\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"); 
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
