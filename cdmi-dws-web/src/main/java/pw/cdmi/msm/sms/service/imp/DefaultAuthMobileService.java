package pw.cdmi.msm.sms.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.commons.NumberGenerate;
import pw.cdmi.msm.sms.model.SignTypeSupper;
import pw.cdmi.msm.sms.model.SmsSignId;
import pw.cdmi.msm.sms.repository.AuthMobileRepository;
import pw.cdmi.msm.sms.service.AuthMobileService;
import pw.cdmi.msm.sms.service.SendMessageService;


@Component
public class DefaultAuthMobileService implements AuthMobileService {
	@Autowired
	private AuthMobileRepository authMobileRepository;
	@Autowired
	private SendMessageService sendMessageService;
	
	
	@Override
	public int sendMessage(String mobile,String headMessage,SignTypeSupper type) {
		
		String signId = null;
		if(type == null){
			//默认
			signId = SmsSignId.jushu;
		}
		else
		switch (type) {
		case sign_file:
			signId = SmsSignId.file;
			break;
		case sign_jushu:
			signId = SmsSignId.jushu;
			break;
		case sign_huayiyun:
			signId = SmsSignId.huayiyun;
			break;
		default:
			signId = SmsSignId.huayiyun;
		}
		String endMessage = "（有效期十分钟，请完成验证），如非本人操作，请忽略本消息";
		String authNumber = NumberGenerate.authNumber();	
		
		
		authMobileRepository.save(mobile, authNumber);		
		return  sendMessageService.send(mobile, headMessage+authNumber+endMessage,signId);
	}

	@Override
	public boolean AuthMobile(String mobile, String AuthNumber) {
		Object value = authMobileRepository.getValue(mobile);
		if(value==null){
			return false;
		}
		if(!AuthNumber.equals(value)){
			
			return false;
		}
		authMobileRepository.deleteObject(mobile);
		return true;
	}

}
