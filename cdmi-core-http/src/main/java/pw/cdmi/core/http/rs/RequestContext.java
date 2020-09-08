package pw.cdmi.core.http.rs;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private static ThreadLocal<Map<String, String>> extparams = new ThreadLocal<Map<String, String>>();
    
	public static void addExtParameter(String name, String value){
		Map<String, String> ext_param = extparams.get();
		if(ext_param == null || ext_param.size() == 0){
			ext_param = new HashMap<String, String>();
		}
		ext_param.put(name, value);
		extparams.set(ext_param);
    }

	public static String getExtParameter(String name){
		Map<String, String> ext_param = extparams.get();
		if(ext_param == null || ext_param.size() == 0){
			return null;
		}else{
			return ext_param.get(name);
		}
    }
	
	public static void destroy(){
		extparams.remove();
	}
}
