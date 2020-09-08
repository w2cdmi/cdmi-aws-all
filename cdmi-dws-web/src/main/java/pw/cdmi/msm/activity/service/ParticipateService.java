package pw.cdmi.msm.activity.service;

import org.springframework.web.bind.annotation.RequestParam;

import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.model.entities.Participate;

public interface ParticipateService {
	/**
	 * 创建参与人
	 * @param participate
	 * @return
	 */
	public String createParticipate(Participate participate);
	/**
	 * 通过申请表创建参与者
	 * @param id
	 * @return
	 */
	public String createParticipateByApply(Application apply);
	
	/**
	 * id查询
	 * @param id
	 * @return
	 */
	public Participate getParticipateByid(String id);
	/**
	 * 自己的参与
	 * @param belong
	 * @param user
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Participate> getParticipateByUser(Belong belong, User user,Integer cursor,Integer maxcount);
	/**
	 * 活动的参与人
	 * @param belong
	 * @param activityId
	 * @param cursor
	 * @param maxcount
	 * @return
	 */
	public Iterable<Participate> getParticipateByActivityId(Belong belong, String activityId,Integer cursor,Integer maxcount);
	/**
	 * 自己是否参加
	 * @param belong
	 * @param activityId
	 * @param user
	 * @return
	 */
	public Participate getParticipateByActivityIdAndUser(Belong belong, String activityId, User user);
	/**
	 * 取消参与
	 * @param id
	 */
	public void deleteById(String id);
}
