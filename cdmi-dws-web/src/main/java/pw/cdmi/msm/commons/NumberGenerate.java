package pw.cdmi.msm.commons;


import java.util.UUID;

/**
 * 生成11位openid 测试一亿为发现重复
 * @author ljj
 *
 */
public class NumberGenerate {

	public static String toOpenId(){
		String uuid = String.valueOf(Math.abs(UUID.randomUUID().toString().replace("-", "").hashCode()));
		String time = String.valueOf(System.currentTimeMillis());
		int spare = 11-uuid.length();
		
		return uuid + time.substring(13-spare, 13);
		
	}
	public static String authNumber(){
		String uuid = String.valueOf(Math.abs(UUID.randomUUID().toString().replace("-", "").hashCode()));
		String time = String.valueOf(System.currentTimeMillis());		
		return uuid.subSequence(1, 5) + time.substring(11, 13);
	}
	
}
