package pw.cdmi.msm.sms.service.imp;

import com.smn.client.AkskSmnClient;
import com.smn.client.SmnClient;
import com.smn.request.sms.SmsPublishRequest;
import com.smn.response.sms.SmsPublishResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pw.cdmi.msm.sms.service.SendMessageService;




@Component
public class SmnSendMessagerService implements SendMessageService {

    private static Logger logger = LoggerFactory.getLogger(SmnSendMessagerService.class);
	@Override
	public int send(String mobile, String value,String SignId) {
		SmnClient smnClient = new AkskSmnClient(
		        "526GZIMDCZ2JIZQJQX1P",
		        "diKMQshRF63CpMeDVatp0zGf0GU9ls4SqW4nt2cd",
		        "cn-north-1");
		
		
		// 构造请求对象
        SmsPublishRequest smnRequest = new SmsPublishRequest();
        // 设置参数,接收手机号，短信内容，短信签名ID
        smnRequest.setEndpoint(mobile)
                .setMessage(value)
                .setSignId(SignId);
        //        .setSignId("36778e025ec747b385c3673410587246");
        // 发送短信
        SmsPublishResponse res = null;
        try {
            res = smnClient.sendRequest(smnRequest);
            
           
//            System.out.println("httpCode:" + res.getHttpCode()
//                    + ",message_id:" + res.getMessageId()
//                    + ", request_id:" + res.getRequestId()
//                    + ", errormessage:" + res.getMessage()
//                    + " code:"+res.getCode());

            if(res.getHttpCode()!= 200){
                logger.info(mobile+"短信发送失败"+"httpCode:" + res.getHttpCode()
                    + ",message_id:" + res.getMessageId()
                    + ", request_id:" + res.getRequestId()
                    + ", errormessage:" + res.getMessage()
                    + " code:"+res.getCode());
            }
        } catch (Exception e) {
            // 处理异常
            logger.error(mobile+"短信发送失败");
            throw new SecurityException("短信发送失败");
        }
        return res.getHttpCode();

	}

}
