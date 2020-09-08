package pw.cdmi.msm.sms.resouce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.vertx.core.json.JsonObject;
import lombok.Data;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.sms.model.CheckRequest;
import pw.cdmi.msm.sms.model.MsmRequest;
import pw.cdmi.msm.sms.service.AuthMobileService;


@RestController
@RequestMapping("/messages/v1")
@ConfigurationProperties(prefix="MessageTemplates")
@Api(value = "短信验证服务" ,description = "短信验证服务")
@Data
public class SendMessageResouce {
	private  static Logger log = LoggerFactory.getLogger(SendMessageResouce.class);
	private Map<String,String> templates;
	@Autowired
	private AuthMobileService authMobileService;
	
	@PostMapping("/sms/checkcode")
	@ApiOperation(value = "发送短信" ,notes = "发送短信")
	public @ResponseBody Map<String,Object> SendMessage(@RequestBody MsmRequest message){
		
		if(StringUtils.isBlank(message.getMobile())){
			log.info("sendMessage Param errer "+ JSONObject.fromObject(message).toString());
			throw new SecurityException("参数错误");
		}
		String content = null;
		if(StringUtils.isBlank(message.getTemplatesId())){
			 content = templates.get("default");
		}else{
			
			 content = templates.get(message.getTemplatesId());
			 if(content==null){
				 content = templates.get("default");
			 }
		}
		Map<String, Object> hashMap = new HashMap<String ,Object>();
		hashMap.put("httpCode", authMobileService.sendMessage(message.getMobile(), content ,message.getSignType()));
		log.info("send sms "+message.getMobile()+hashMap);
		return hashMap;
	}
	@PutMapping("/sms/checkcode")
	public @ResponseBody Map<String, Object> authMobile(@RequestBody CheckRequest checkRequest){
		if(checkRequest==null||StringUtils.isBlank(checkRequest.getMobile())||StringUtils.isBlank(checkRequest.getCode())){
			log.info("authMobile param errer "+JSONObject.fromObject(checkRequest).toString());
			throw new SecurityException("参数错误");
		}
		boolean authMobile = authMobileService.AuthMobile(checkRequest.getMobile(), checkRequest.getCode());
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("check", authMobile);

		log.info("auth mobile code Ok , detele authcode:"+checkRequest.getCode());
		return hashMap;
		
	}
}
