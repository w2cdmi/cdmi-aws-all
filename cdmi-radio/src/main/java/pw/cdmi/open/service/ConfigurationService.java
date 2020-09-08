package pw.cdmi.open.service;

import org.springframework.stereotype.Service;

import pw.cdmi.open.model.entity.MMSConfiguration;
import pw.cdmi.open.model.entity.MailConfiguration;

/****************************************************
 * 接口类，提供对系统所使用的短信，邮件服务器的配置信息的操作方法。
 * 
 * @author 伍伟
 * @version O2O Service Platform, July 28, 2014
 ***************************************************/
@Service
public interface ConfigurationService {

    /**
     * 向数据库中创建一条邮件服务器配置信息
     * @param configuration 邮件服务器配置信息
     * @return 新创建的邮件服务器配置信息
     */
    public MailConfiguration createMailConfiguration(MailConfiguration configuration);

    /**
     * 向数据库中创建一条短信服务器配置信息
     * @param configuration 短信服务器配置信息
     * @return 新创建的短信服务器配置信息
     */
    public MMSConfiguration createMMSConfiguration(MMSConfiguration configuration);

    /**
     * 向数据库中更新一条邮件服务器配置信息
     * @param configuration 邮件服务器配置信息
     * @return 更新后的邮件服务器配置信息
     */
    public MailConfiguration updateMailConfiguration(MailConfiguration configuration);

    /**
     * 向数据库中更新一条短信服务器配置信息
     * @param configuration 短信服务器配置信息
     * @return 更新后的短信服务器配置信息
     */
    public MMSConfiguration updateMMSConfiguration(MMSConfiguration configuration);

    /**
     * 获取应用的邮件服务器配置信息
     * @return 邮件服务器配置信息
     */
    public MailConfiguration getMailConfiguration();

    /**
     * 获取应用的短信服务器配置信息
     * @return 短信服务器配置信息
     */
    public MMSConfiguration getMMSConfiguration();

}
