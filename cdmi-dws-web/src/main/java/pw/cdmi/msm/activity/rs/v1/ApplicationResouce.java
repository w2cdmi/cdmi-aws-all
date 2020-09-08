package pw.cdmi.msm.activity.rs.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.rs.ApplicationRequest;
import pw.cdmi.msm.activity.rs.ApplicationResponse;
import pw.cdmi.msm.activity.service.ApplicationService;

@RestController
@RequestMapping("activitys/v1")
public class ApplicationResouce {

	@Autowired
	private ApplicationService applicationService;

	@PostMapping("/application")
	public @ResponseBody Map<String, Object> createApplication(
			@RequestBody ApplicationRequest applicationRequest) {

		Application application = new Application();
		application.setActivityAid(applicationRequest.getUserId());
		application.setAppId("test");

		application.setActivityId(applicationRequest.getActivityId());
		application.setIsCost(applicationRequest.getIsCost());
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("newid", applicationService.createApplication(application));
		return hashMap;
	}

	@GetMapping("/applications/activity/{id}")
	public List<ApplicationResponse> getApplicationByActivityId(
			@PathVariable("id") String activityId,
			@RequestParam("cursor") Integer cursor,
			@RequestParam("maxcount") Integer maxcount) {
		List<ApplicationResponse> listResponse = new ArrayList<ApplicationResponse>();
		
		if(StringUtils.isBlank(activityId)||cursor == null||maxcount == null){
			throw new SecurityException("参数错误");
		}
		Belong belong = new Belong();
		belong.setAppId("test");
		Iterable<Application> listApplication = applicationService
				.getApplicationByActivityId(belong, activityId, cursor,
						maxcount);
		Iterator<Application> iterator = listApplication.iterator();
		while (iterator.hasNext()) {
			Application next = iterator.next();
			listResponse.add(toApplicationResponse(next));
		}
		return listResponse;

	}
	@GetMapping("/applications/user/{id}")
	public List<ApplicationResponse> getApplilcationByUserId(@PathVariable("id")String userId,@RequestParam("cursor") Integer cursor,
			@RequestParam("maxcount") Integer maxcount){
		List<ApplicationResponse> listResponse = new ArrayList<ApplicationResponse>();
		if(StringUtils.isBlank(userId)||cursor == null||maxcount == null){
			throw new SecurityException("参数错误");
		}
		Belong belong = new Belong();
		belong.setAppId("test");
		User user = new User();
		user.setActivityAid(userId);
		Iterable<Application> listApplication = applicationService.getApplicationByUser(belong, user, cursor, maxcount);
		Iterator<Application> iterator = listApplication.iterator();
		while (iterator.hasNext()) {
			Application next = iterator.next();
			listResponse.add(toApplicationResponse(next));
		}
		return listResponse;
	}
	@GetMapping("/application/user/{userId}/activity/{activityId}")
	public ApplicationResponse getApplilcationByUserIdAndActivityId(@PathVariable("userId")String userId,@PathVariable("activityId")String activityId){
		
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(activityId)){
			throw new SecurityException("参数错误");
		}
		Belong belong = new Belong();
		belong.setAppId("test");
		User user = new User();
		user.setActivityAid(userId);
		Application applicationByActivityIdAndUser = applicationService.getApplicationByActivityIdAndUser(belong, activityId, user);
		
		
		return toApplicationResponse(applicationByActivityIdAndUser);
	}
	
	@DeleteMapping("/application")
	public void deleteApplication(@RequestParam("userId")String userId,@RequestParam("applicationId")String applicationId){
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(applicationId)){
			throw new SecurityException("参数错误");
		}
		applicationService.deleteId(applicationId);
	}
	
	private ApplicationResponse toApplicationResponse(Application application) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setCreatTime(String.valueOf(application
				.getCreatTime().getTime()));
		applicationResponse.setActivityId(application.getActivityId());
		applicationResponse.setUserId(application.getActivityAid());
		applicationResponse.setIsCost(application.getIsCost());
		
		applicationResponse.setName(application.getName());
		applicationResponse.setHeadImageUrl(application.getHeadImageUrl());
		applicationResponse.setUserType(application.getUserType());
		applicationResponse.setDegree(application.getDegree());
		return applicationResponse;
	}

}
