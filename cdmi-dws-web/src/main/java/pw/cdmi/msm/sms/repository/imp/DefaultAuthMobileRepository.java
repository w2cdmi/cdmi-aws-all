package pw.cdmi.msm.sms.repository.imp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.core.cache.CacheClient;
import pw.cdmi.msm.sms.repository.AuthMobileRepository;


@Component
public class DefaultAuthMobileRepository  implements AuthMobileRepository{

	@Autowired
	private CacheClient cachedClient;
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthMobileRepository.class);
	
//	private  final String name = "sms_cache";
	@Override
	public void save(String key, String value) {
		boolean setCache = cachedClient.setCache(key, value);
		if(!setCache){
			LOGGER.error("DefaultAuthMobileRepository save error"+setCache+"  key:"+key);
		}
				
	}

	@Override
	public void deleteObject(String key) {

		try {
			cachedClient.deleteCache(key);
		} catch (Exception e) {
			LOGGER.error("Cache detele key errer "+ key);
			e.printStackTrace();
		}
	}

	@Override
	public Object getValue(String key) {
		Object object = cachedClient.getCache(key);
		if(object==null){
			return null;
		}
		return object;
	}

	
}
