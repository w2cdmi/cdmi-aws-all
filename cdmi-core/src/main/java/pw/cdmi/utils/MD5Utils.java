/* 
 * 版权声明(Copyright Notice)：
 *     Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 *     Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved. 
 * 
 *     警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业目
 */ 
package pw.cdmi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/****************************************************
 * MD5工具类，提供从字符串，字节数组，文件，数据流得到其MD5值。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 28, 2014
 ***************************************************/
public class MD5Utils {
	protected static Log log = LogFactory.getLog(MD5Utils.class);
    /**
     * 得到指定字符串的MD5信息
     */
	public final static String getMD5(String s) throws NoSuchAlgorithmException{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] strTemp = s.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
    /**
     * 得到指定字节的MD5信息
     */
	public final static String getMD5(byte[] buf) throws NoSuchAlgorithmException{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(buf);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
    /**
     * 得到指定文件MD5信息
     */
    public static String getMD5(File file){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
        FileInputStream fis = null;
        MessageDigest md = null;
        byte[] buf = new byte[2048];
        
        try {
            md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            int num = fis.read(buf);
            while (num != (-1)){
                md.update(buf,0,num);
                num = fis.read(buf);
            }
        } catch (FileNotFoundException ex){
            log.error("输入的文件未能找到..", ex);
            ex.printStackTrace();
        } catch (IOException e){
        	log.error("输入的文件存在IO错误..", e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ex){
        	log.error("获取文件的MD5值时出现错误..", ex);
            ex.printStackTrace();
        }finally {
        	try{
        	fis.close();
        	}catch(IOException e){
        		log.error("输入的文件关闭失败..", e);
        	}
        }
		byte[] tmd = md.digest();
		int j = tmd.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmd[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
    }
    
    /**
     * 得到指定数据输入流MD5信息
     */
    public static String getMD5(InputStream stream){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
        MessageDigest md = null;
        byte[] buf = new byte[2048];
        
        try {
            md = MessageDigest.getInstance("MD5");
            int num = stream.read(buf);
            while (num != (-1)){
                md.update(buf,0,num);
                num = stream.read(buf);
            }
        } catch (FileNotFoundException ex){
        	log.error("输入的文件流未能找到..", ex);
            ex.printStackTrace();
        } catch (IOException e){
        	log.error("输入的文件流存在IO错误..", e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ex){
        	log.error("获取文件流的MD5值时出现错误..", ex);
            ex.printStackTrace();
        }
		byte[] tmd = md.digest();
		int j = tmd.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmd[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
    }
}
