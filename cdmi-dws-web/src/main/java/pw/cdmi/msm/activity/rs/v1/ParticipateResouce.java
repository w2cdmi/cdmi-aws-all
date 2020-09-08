package pw.cdmi.msm.activity.rs.v1;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.ParticipateTypeSupper;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.model.entities.Participate;
import pw.cdmi.msm.activity.rs.ParticipateRequest;
import pw.cdmi.msm.activity.rs.ParticipateResponse;
import pw.cdmi.msm.activity.service.ApplicationService;
import pw.cdmi.msm.activity.service.ParticipateService;

@RestController
@RequestMapping("activitys/v1")
public class ParticipateResouce {

	@Autowired
	private ParticipateService participateService;
	
	@Autowired
	private ApplicationService applicationService;
	@PostMapping("/participate")
	public String createParticipate(ParticipateRequest request){
		if(request == null||request.getType() == null||StringUtils.isBlank(request.getActivityId())||StringUtils.isBlank(request.getUserId())){
			throw new SecurityException("参数错误");
		}
		Participate participate = new Participate();
		participate.setAppId("test");
		participate.setActivityAid(request.getUserId());
		participate.setActivityId(request.getActivityId());
		participate.setCreateTime(new Date());	
		return participateService.createParticipate(participate);
	}
	@PostMapping("/participate/user/{userId}/activity/{activityId}")
	public String createParticipate(@PathVariable("userId")String userId,@PathVariable("activityId")String activityId){
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(activityId)){
			throw new SecurityException("参数错误");
		}
		Participate participate = new Participate();
		Belong belong = new Belong();
		User user = new User();
		belong.setAppId("test");
		user.setActivityAid(userId);
		Application application = applicationService.getApplicationByActivityIdAndUser(belong, activityId, user);
		if(application == null){
			throw new SecurityException("applicate is null"); 
		}
		
		participate.setActivityAid(application.getActivityAid());
		participate.setActivityTid(application.getActivityTid());
		participate.setActivityUid(application.getActivityUid());
		
		participate.setAppId(application.getAppId());
		participate.setSiteId(application.getSiteId());
		participate.setTenantId(application.getTenantId());
		
		participate.setCreateTime(new Date());
		
		participate.setActivityId(application.getActivityId());
		participate.setType(ParticipateTypeSupper.apply);
		
		return participateService.createParticipate(participate);
		
	}
	
	@GetMapping("/participate/activity/{activityId}")
	public String getParticipateByactivityId(@PathVariable("activityId") String activityId){
		
		
		return null;
	}
	
//	private ParticipateResponse 
}
