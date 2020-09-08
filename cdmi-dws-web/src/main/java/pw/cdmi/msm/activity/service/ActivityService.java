package pw.cdmi.msm.activity.service;

import java.util.List;

import pw.cdmi.msm.activity.model.ActivityArranges;
import pw.cdmi.msm.activity.model.ActivityLimit;
import pw.cdmi.msm.activity.model.ActivityStateSupper;
import pw.cdmi.msm.activity.model.BaseInformation;
import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.Guests;
import pw.cdmi.msm.activity.model.News;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Activity;

public interface ActivityService {

	/**
	 * 添加活动
	 * @param activity
	 * @return
	 */
	public String CreateActivity(Activity activity);
	/**
	 * id查询
	 * @param id
	 * @return
	 */
	public Activity getActivityById(String id);
	/**
	 * 分页查询
	 * @param belong
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Activity> getActivityByBelong(Belong belong,Integer cursor,Integer maxcount);
	/**
	 * 通过user分页查询
	 * @param belong
	 * @param user
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Activity> getActivityByBelongAndUser(Belong belong, User user,Integer cursor,Integer maxcount);
	/**
	 * 更新基础信息
	 * @param id
	 * @param baseInformation
	 */
	public void updateBaseInformation(String id,BaseInformation baseInformation);
	/**
	 * 更新活动安排
	 * @param id
	 * @param activityArranges
	 */
	public void updateArranges(String id, List<ActivityArranges> activityArranges);
	/**
	 * 更新剪影
	 * @param id
	 * @param historySilhouettes
	 */
	public void updateHistorySilhouettes(String id,List<String> historySilhouettes);
	/**
	 * 更新限制条件
	 * @param id
	 * @param activityLimit
	 */
	public void updateActivityLimit(String id,ActivityLimit activityLimit);
	/**
	 * 更新新闻
	 * @param id
	 * @param news
	 */
	public void updateNews(String id,List<News> news);
	/**
	 * 更新状态
	 * @param id
	 * @param state
	 */
	public void updateState(String id,ActivityStateSupper state);
	/**
	 * 更新主办方列表
	 * @param id
	 * @param sponsorNames
	 */
	public void updateSponsor(String id,List<String> sponsorNames);
	/**
	 * 更新嘉宾列表
	 * @param id
	 * @param Guests
	 */
	public void updateGuests(String id,List<Guests> Guests);
	/**
	 * 删除活动
	 */
	public void deleteActivity();
	
}
