package pw.cdmi.open.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/****************************************************
 * 工具类，提供短信的发送功能。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
public class MMSUtils {
	protected static Log log = LogFactory.getLog(MMSUtils.class);
	/** MMS短信功能的配置信息 **/
	private static Properties p = new Properties();
	static {
		initProperties("MMS.properties");
	}

	private MMSUtils() {
	}

	public static boolean sendMMS(String mobile, String content) {
		return sendMMS(mobile, null, content);
	}
	
	public static boolean sendMMS(String mobile, String cell, String content) {
		String sendTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Map<String,String> params = new HashMap<String,String>();
		params.put("CorpID",p.getProperty("mms.server.user"));
		params.put("Pwd",p.getProperty("mms.server.password"));
		params.put("Mobile",mobile);
		params.put("Content", content + p.getProperty("mms.user.signature"));
		params.put("Cell", cell);
		params.put("SendTime",sendTime);
		
		return post(p.getProperty("mms.server.url"),params);
	}

	private static boolean post(String wurl, Map<String,String> params)  {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(wurl);
        
		ArrayList<NameValuePair> nvps = new ArrayList <NameValuePair>();
		Iterator<Entry<String,String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
				Map.Entry<String,String> entry = (Map.Entry<String,String>) it.next();
	            String key = entry.getKey();
	            String value = entry.getValue();
	            nvps.add(new BasicNameValuePair(key, value));
		 }  
		try{
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	        HttpResponse response = httpclient.execute(httpost);
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8"); 
	        if(result != null){
	        	int result_value = Integer.valueOf(result);
	        	if(result_value>=0){
	        		return true;
	        	}
	        }
		} catch (ClientProtocolException ce){
			return false;
		} catch (IOException ie){
			return false;
		} catch (NumberFormatException ne){
			return false;
		}
		return false;
	}

	/**
	 * 类初始化加载JMail配置文件
	 * 
	 * @param propertyFileName
	 */
	private static void initProperties(String propertyFileName) {
		InputStream in = null;
		try {
			in = MMSUtils.class.getClassLoader().getResourceAsStream(
					propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			log.error(
					"未能加载到短信服务功能的配置文件，该文件的名称为：'MMS.properties',应放到classes的根目录..",
					e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("'MMS.properties'文件关闭失败..", e);
				}
			}
		}
	}
}
