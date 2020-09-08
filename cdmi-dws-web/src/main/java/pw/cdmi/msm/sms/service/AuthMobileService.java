package pw.cdmi.msm.sms.service;

import pw.cdmi.msm.sms.model.SignTypeSupper;

public interface AuthMobileService {
    /**
     * 对指定电话号码发送验证短信
     *
     * @param mobile
     */
    public int sendMessage(String mobile, String headMessage, SignTypeSupper type);

    /**
     * 验证短信
     *
     * @param mobile
     * @param AuthNumber
     */
    public boolean AuthMobile(String mobile, String AuthNumber);

}
