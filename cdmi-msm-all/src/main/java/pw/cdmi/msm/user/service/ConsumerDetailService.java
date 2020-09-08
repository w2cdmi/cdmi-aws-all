package pw.cdmi.msm.user.service;


import java.util.Date;
import java.util.List;

import pw.cdmi.msm.user.model.entities.ConsumerDetail;


/**
 * **********************************************************
 * <br/>
 * TODO接口类,提供对消费明细的操作
 * <br/>
 * @author Administrator
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
public interface ConsumerDetailService {
    /**
     * 
     * TODO(创建一条消费明细记录).<br/>
     * @param consumerDetail
     */
    public void createConsumerDetail(ConsumerDetail consumerDetail);
    /**
     * 
     * TODO(根据用户Id查询所有消费明细).<br/>
     * @param siteUserId
     * @return
     */
    public List<ConsumerDetail> findBySiteUserId(String siteUserId);
    /**
     * 
     * TODO(根据指定的日期查询用户消费明细).<br/>
     * @param siteUserId
     * @param date
     * @return
     */
    public List<ConsumerDetail> findByUserIdAndDate(String siteUserId,Date date);
    
    /**
     * 
     * TODO(根据日期范围查询消费明细).<br/>
     * @param siteUserId
     * @param beginDate
     * @param enDate
     * @return
     */
    public List<ConsumerDetail> findByUserIdAndDate(String siteUserId,Date beginDate,Date enDate);
}
