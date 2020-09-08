package pw.cdmi.msm.activity.service;

import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;

public interface ApplicationService {
	/**
	 * 创建申请表
	 * @param apply
	 * @return
	 */
	public String createApplication(Application apply);
	/**
	 * id查询申请表
	 * @param id
	 * @return
	 */
	public Application getApplicationById(String id);
	/**
	 * user分页查询申请表
	 * @param belong
	 * @param user
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Application> getApplicationByUser(Belong belong , User user,Integer cursor,Integer maxcount);
	/**
	 * 活动id页查询申请表
	 * @param belong
	 * @param activityId
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Application> getApplicationByActivityId(Belong belong,String activityId,Integer cursor,Integer maxcount);
	/**
	 * user与活动id查询
	 * @param belong
	 * @param activityId
	 * @param user
	 * @return
	 */
	public Application getApplicationByActivityIdAndUser(Belong belong,String activityId, User user);
	
	/**
	 * 取消申请
	 * @param id
	 */
	public void deleteId(String id);
	
	
}
