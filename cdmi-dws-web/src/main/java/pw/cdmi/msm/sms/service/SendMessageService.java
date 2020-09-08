package pw.cdmi.msm.sms.service;

public interface SendMessageService {
	
	public int send(String mobile,String value,String SignId);
}
