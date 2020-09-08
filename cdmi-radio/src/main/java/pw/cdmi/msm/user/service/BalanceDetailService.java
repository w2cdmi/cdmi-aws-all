package pw.cdmi.msm.user.service;


import java.util.Date;
import java.util.List;

import pw.cdmi.msm.user.model.entity.BalanceDetail;



/**
 * **********************************************************
 * <br/>
 * TODO接口类，提供对账户收支明细的操作方法
 * 
 * @author Administrator
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
public interface BalanceDetailService {

   /**
    * 
    * TODO创建一条收支明细记录<br/>
    * 
    * @param balanceDetail
    */
    public void createBalanceDetail(BalanceDetail balanceDetail);

    /**
     * 
     * TODO根据用户id查询他所有的收支明细.<br/>
     * 
     * @param siteUserId
     * @return
     */
    public List<BalanceDetail> findBySiteUserId(String siteUserId);
    
    /**
     * 
     * TODO根据用户id和指定日期查询收支明细单.<br/>
     *
     * @param siteUserId
     * @param Date
     * @return
     */
    public List<BalanceDetail> findByUserIdAndDate(String siteUserId,Date date);
    
    /**
     * 
     * TODO根据日期范围查询.<br/>
     * @param siteUserId
     * @param beginDate
     * @param enDate
     * @return
     */
    public List<BalanceDetail> findByUserIdAndDate(String siteUserId,Date beginDate,Date enDate);
    
}
