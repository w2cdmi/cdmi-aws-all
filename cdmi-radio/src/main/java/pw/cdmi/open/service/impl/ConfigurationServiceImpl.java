package pw.cdmi.open.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.open.model.entity.MMSConfiguration;
import pw.cdmi.open.model.entity.MailConfiguration;
import pw.cdmi.open.model.entity.SiteApplication;
import pw.cdmi.open.service.ConfigurationService;
import pw.cdmi.open.service.SingleSiteApplicationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Autowired
    private GenericDao daoImpl;

    @Autowired
    private SingleSiteApplicationService appService;

    @Override
    @Transactional
    public MailConfiguration createMailConfiguration(MailConfiguration configuration) {
        configuration.setAppId(appService.getCurrentAppId());
        daoImpl.save(configuration);
        return configuration;
    }

    @Override
    @Transactional
    public MMSConfiguration createMMSConfiguration(MMSConfiguration configuration) {
        configuration.setAppId(appService.getCurrentAppId());
        daoImpl.save(configuration);
        return configuration;
    }

    @Override
    @Transactional
    public MailConfiguration updateMailConfiguration(MailConfiguration configuration) {
        configuration.setAppId(appService.getCurrentAppId());
        MailConfiguration mail = daoImpl.get(configuration.getId(), MailConfiguration.class);
        if (mail == null) {
            throw new AWSServiceException(SystemReason.NullData);
        }
        daoImpl.update(configuration);
        return configuration;
    }

    @Override
    @Transactional
    public MMSConfiguration updateMMSConfiguration(MMSConfiguration configuration) {
        configuration.setAppId(appService.getCurrentAppId());
        MMSConfiguration mms = daoImpl.get(configuration.getId(), MMSConfiguration.class);
        if (mms == null) {
            throw new AWSServiceException(SystemReason.NullData);
        }
        daoImpl.update(configuration);
        return configuration;
    }

    @Override
    public MailConfiguration getMailConfiguration() {
        SiteApplication app = appService.getAccessAplication();
        if (app == null) {
            return null;
        }
        String hql = "from MailConfiguration where appId ='" + app.getAppId() + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public MMSConfiguration getMMSConfiguration() {
        SiteApplication app = appService.getAccessAplication();
        if (app == null) {
            return null;
        }
        String hql = "from MMSConfiguration where appId ='" + app.getAppId() + "'";
        return daoImpl.findOne(hql);
    }

}
