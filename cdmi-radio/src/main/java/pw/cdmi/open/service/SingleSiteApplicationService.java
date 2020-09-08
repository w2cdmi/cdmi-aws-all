package pw.cdmi.open.service;

import pw.cdmi.open.model.entity.SiteApplication;

/****************************************************
 * 接口类，获取当前应用的License和配置信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
public interface SingleSiteApplicationService {

    /**
     * 注册或重新注册一个应用，只有在单独部署时候才能调用该方法
     * @param app 待添加的接入应用信息
     * @return 新接入的应用信息
     */
    public SiteApplication registerAplication();

    // /**
    // * 重置接入应用的SecretKey，如果需要远程链接数据平台，则需要修改实现，本地应用不能重置SecretKey
    // * @return 新的SecretKey
    // */
    // public String resetSecretKey();

    /**
     * 获得当前应用的信息
     * @return 返回当前应用信息
     */
    public SiteApplication getAccessAplication();

    /**
     * 校验接入应用的合法性
     * @param appKey 接入应用的appKey
     * @param appSecretKey 接入应用的appSecretKey
     * @return 接入应用信息
     */
    public SiteApplication getAccessAplication(String appKey, String appSecretKey);

    /**
     * 通过配置文件中的信息获得当前应用的AppId
     * @return
     */
    public String getCurrentAppId();

    public void activateApplication(String appId);

}
