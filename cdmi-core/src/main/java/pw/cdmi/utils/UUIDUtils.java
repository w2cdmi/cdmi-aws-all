/* 
 * 版权声明(Copyright Notice)：
 *     Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 *     Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved. 
 * 
 *     警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业目
 */ 
package pw.cdmi.utils;

/****************************************************
 * UUID工具类，提供将16进制的UUID转化为64进制的字符串，使之变的更短。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, Aug 9, 2014
 ***************************************************/
import java.util.UUID;

public class UUIDUtils {
	private static final char[] charMap;  
    static {  
        charMap = new char[64];  
        for (int i = 0; i < 10; i++) {  
            charMap[i] = (char) ('0' + i);  
        }  
        for (int i = 10; i < 36; i++) {  
            charMap[i] = (char) ('a' + i - 10);  
        }  
        for (int i = 36; i < 62; i++) {  
            charMap[i] = (char) ('A' + i - 36);  
        }  
        charMap[62] = '*';  
        charMap[63] = '!';  
    }
    
    //用来生成8位UUID
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };
    
    /**
     * 将字符串转化为64进制数值
     * @param hex
     * @return
     */
    private static String hexTo64(String hex) {  
        StringBuffer r = new StringBuffer();  
        int index = 0;  
        int[] buff = new int[3];  
        int l = hex.length();  
        for (int i = 0; i < l; i++) {  
            index = i % 3;  
            buff[index] = Integer.parseInt("" + hex.charAt(i), 16);  
            if (index == 2) {  
                r.append(charMap[buff[0] << 2 | buff[1] >>> 2]);  
                r.append(charMap[(buff[1] & 3) << 4 | buff[2]]);  
            }  
        }  
        return r.toString();  
    }
    
    /**
     * 获得不重复的最短的UUID
     * @param uuid
     * @return
     */
    public static String getUUIDTo64() {  
        StringBuffer sb = new StringBuffer("0"); 
        UUID uuid = UUID.randomUUID();
        String str_uuid =uuid.toString();  
        str_uuid = str_uuid.replaceAll("-", ""); 
        sb.append(str_uuid);  
        str_uuid = hexTo64(sb.toString()); 
        return str_uuid;  
    }
    
    /**
     * 最大限度获得不重复的UUID，但可能存在重复，需要在程序中进行去重判断
     * @return
     */
    public static String getUUIDTo8(){
    	StringBuffer shortBuffer = new StringBuffer();  
        String uuid = UUID.randomUUID().toString().replace("-", "");  
        for (int i = 0; i < 8; i++) {  
            String str = uuid.substring(i * 4, i * 4 + 4);  
            int x = Integer.parseInt(str, 16);  
            shortBuffer.append(chars[x % 0x3E]);  
        }  
        return shortBuffer.toString(); 
    }
    
    public static String getValueAfterMD5()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
