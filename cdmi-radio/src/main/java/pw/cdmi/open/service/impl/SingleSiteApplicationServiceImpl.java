package pw.cdmi.open.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.open.Configuration;
import pw.cdmi.open.model.SiteAccessStatus;
import pw.cdmi.open.model.SiteStatus;
import pw.cdmi.open.model.entity.SiteApplication;
import pw.cdmi.open.service.SingleSiteApplicationService;
import pw.cdmi.utils.UUIDUtils;

/****************************************************
 * 实现类，获取当前应用的License和配置信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
@Service
public class SingleSiteApplicationServiceImpl implements SingleSiteApplicationService {

    @Autowired
    private GenericDao daoImpl;

    @Autowired
    Configuration configuration;

    private String currentSiteId;

    @Override
    @Transactional
    public SiteApplication registerAplication() {
        // 本地部署应用数据库中只有一条记录，如果出现多条，则会抛出异常
        String hql = "from SiteApplication where appId='" + configuration.getAppId() + "'";
        SiteApplication app = daoImpl.findOne(hql);

        // 1.为接入应用生成appkey和appSecret
        String appKey = UUIDUtils.getUUIDTo8();
        String appSecret = UUIDUtils.getUUIDTo64();

        // 2.TODO 通过读取license文件和硬件信息，加密重新生成appSecret，

        if (app != null) {//
            app.setAppKey(appKey);
            app.setAppSecret(appSecret);
            daoImpl.update(app);
        } else {
            // 3. 将appkey和appSecret赋值给app对象，并保存到数据库中
            app = new SiteApplication();
            app.setAppId(configuration.getAppId());
            app.setAppName(configuration.getAppName());
            app.setAccessStatus(SiteAccessStatus.Normal);
            app.setStatus(SiteStatus.ToBeConfigured);
            app.setAppKey(appKey);
            app.setAppSecret(appSecret);
            app.setCreateTime(new Date());

            daoImpl.save(app);
        }

        return app;
    }

    // @Override
    // public String resetSecretKey() {
    // // 本地部署应用数据库中只有一条记录，如果出现多条，则会抛出异常
    // String hql = "from SiteApplication";
    // SiteApplication app = jpaImpl.findOne(hql);
    //
    // // 随机生成一个新的SecretKey
    // String new_secretkey = UUIDUtils.getUUIDTo64();
    // app.setAppSecret(new_secretkey);
    // jpaImpl.update(app);
    // return new_secretkey;
    // }

    @Override
    public SiteApplication getAccessAplication() {
        String hql = "from  SiteApplication  where appId='" + configuration.getAppId() + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public SiteApplication getAccessAplication(String appKey, String appSecretKey) {
        String hql = "from SiteApplication  where appKey='" + appKey + "' and appSecret='" + appSecretKey + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public String getCurrentAppId() {
        if (StringUtils.isEmpty(currentSiteId)) {
            SiteApplication app = getAccessAplication();
            if (app == null) {
                throw new AWSServiceException(SystemReason.NullData);
            }
            this.currentSiteId = app.getAppId();
            return app.getAppId();
        } else {
            return currentSiteId;
        }

    }

    @Override
    @Transactional
    public void activateApplication(String appId) {
        SiteApplication app = daoImpl.get(appId, SiteApplication.class);
        if (app == null) {
            throw new AWSServiceException(SystemReason.NullData);
        }
        app.setStatus(SiteStatus.Normal);
        daoImpl.update(app);
    }
}
