package pw.cdmi.msm.activity.service.imp;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pw.cdmi.msm.activity.model.ActivityArranges;
import pw.cdmi.msm.activity.model.ActivityLimit;
import pw.cdmi.msm.activity.model.ActivityStateSupper;
import pw.cdmi.msm.activity.model.BaseInformation;
import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.Guests;
import pw.cdmi.msm.activity.model.News;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Activity;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.repositories.ActivityRepository;
import pw.cdmi.msm.activity.service.ActivityService;
import pw.cdmi.msm.activity.service.ApplicationService;
@Component

public class ActivityServiceImp implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	@Override
	public String CreateActivity(Activity activity) {
		activity.setCreateTime(new Date());
		activity.setUpdateTime(activity.getCreateTime());
		if(activity.getIsLimitNumber() == null){
			activity.setIsLimitNumber(false);
		}
		if(activity.getIsActivityCost() == null){
			activity.setIsActivityCost(false);
		}
		if(activity.getIsApply() == null){
			activity.setIsApply(false);
		}
		if(activity.getIsApply() == null){
			activity.setIsApply(false);
		}
		
		return activityRepository.save(activity).getId();
	}

	@Override
	public Activity getActivityById(String id) {
		
		return activityRepository.findOne(id);
	}

	@Override
	public Iterable<Activity> getActivityByBelong(Belong belong,Integer cursor,Integer maxcount) {
		Activity activity = new Activity();
		activity.setAppId(belong.getAppId());
		activity.setSiteId(belong.getSiteId());
		activity.setTenantId(belong.getTenantId());
		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
        
		return activityRepository.findAll(Example.of(activity),pageRequest);
	}

	@Override
	public Iterable<Activity> getActivityByBelongAndUser(Belong belong,
			User user,Integer cursor,Integer maxcount) {
		Activity activity = new Activity();
		activity.setAppId(belong.getAppId());
		activity.setSiteId(belong.getSiteId());
		activity.setTenantId(belong.getTenantId());
		activity.setActivityAid(user.getActivityAid());
		activity.setActivityTid(user.getActivityTid());
		activity.setActivityUid(user.getActivityUid());

		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
        
		return activityRepository.findAll(Example.of(activity),pageRequest);
	}

	@Override
	public void updateBaseInformation(String id, BaseInformation baseInformation) {
		Activity findOne = activityRepository.findOne(id);
		if(findOne == null){
			throw new SecurityException("没有目标");
		}
		findOne.setActivityName(baseInformation.getName());
		findOne.setActivityType(baseInformation.getType());
		findOne.setActivityDescribe(baseInformation.getDescribe());
		findOne.setStartTime(baseInformation.getStartTime());
		findOne.setEndTime(baseInformation.getEndTime());
		
	}

	@Override
	public void updateArranges(String id,
			List<ActivityArranges> activityArranges) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		JSONObject fromObject = JSONObject.fromObject(activityArranges);
		activity.setActivityArranges(fromObject.toString());
		activityRepository.save(activity);
	}

	@Override
	public void updateHistorySilhouettes(String id,
			List<String> historySilhouettes) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		JSONObject fromObject = JSONObject.fromObject(historySilhouettes);
		activity.setActivityArranges(fromObject.toString());
		activityRepository.save(activity);
		
	}

	@Override
	public void updateActivityLimit(String id, ActivityLimit activityLimit) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		activity.setApplyCost(activityLimit.getApplyCost());
		activity.setIsApplyCost(activityLimit.getIsApplyCost());
		
		activity.setIsActivityCost(activityLimit.getIsActivityCost());
		activity.setActivityCost(activityLimit.getActivityCost());
		
		activity.setIsLimitNumber(activityLimit.getIsLimitNumber());
		activity.setLimitNumber(activityLimit.getLimitNumber());
		
		activityRepository.save(activity);
		
	}

	@Override
	public void updateNews(String id, List<News> news) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		JSONObject fromObject = JSONObject.fromObject(news);
		activity.setActivityArranges(fromObject.toString());
		activityRepository.save(activity);
		
	}

	@Override
	public void updateState(String id, ActivityStateSupper state) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		activity.setActivityState(state);
		activityRepository.save(activity);
	}

	@Override
	public void updateSponsor(String id, List<String> sponsorNames) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		JSONObject fromObject = JSONObject.fromObject(sponsorNames);
		activity.setActivityArranges(fromObject.toString());
		activityRepository.save(activity);
		
	}

	@Override
	public void updateGuests(String id, List<Guests> Guests) {
		Activity activity = activityRepository.findOne(id);
		if(activity == null){
			throw new SecurityException("找不到更新的活动");
		}
		JSONObject fromObject = JSONObject.fromObject(Guests);
		activity.setActivityArranges(fromObject.toString());
		activityRepository.save(activity);
		
	}

	@Override
	@Transactional
	public void deleteActivity() {
		// TODO Auto-generated method stub
		
	}

	
}
