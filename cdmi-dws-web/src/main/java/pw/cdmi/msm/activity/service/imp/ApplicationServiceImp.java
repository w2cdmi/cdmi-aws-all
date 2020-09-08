package pw.cdmi.msm.activity.service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pw.cdmi.msm.activity.model.Belong;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.repositories.ApplyRepository;
import pw.cdmi.msm.activity.service.ApplicationService;
@Component

public class ApplicationServiceImp implements ApplicationService {

	@Autowired
	private ApplyRepository applyRepository;
	
	@Override
	public String createApplication(Application apply) {
		
		apply.setCreatTime(new Date());
		if(apply.getIsCost()==null){
			apply.setIsCost(false);
		}
		
		return applyRepository.save(apply).getId();
	}

	@Override
	public Application getApplicationById(String id) {
		
		return applyRepository.findOne(id);
	}

	@Override
	public Iterable<Application> getApplicationByActivityId(Belong belong, String activityId,Integer cursor,Integer maxcount) {
		Application apply = new Application();
		apply = toApply(belong, apply);
		apply.setActivityId(activityId);
		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
		
		
		return applyRepository.findAll(Example.of(apply),pageRequest);
	}

	@Override
	public Iterable<Application> getApplicationByUser(Belong belong, User user,Integer cursor,Integer maxcount) {
		Application apply = new Application();
		apply = toApply(belong,user,apply);
		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
		return applyRepository.findAll(Example.of(apply),pageRequest);
	}

	@Override
	public Application getApplicationByActivityIdAndUser(Belong belong, String activityId,
			User user) {
		Application apply = new Application();
		apply = toApply(belong, user, apply);
		apply.setActivityId(activityId);
		return applyRepository.findOne(Example.of(apply));
	}

	@Override
	@Transactional
	public void deleteId(String id) {
		applyRepository.delete(id);
	}
	
	private Application toApply(Belong belong,Application apply){
		apply.setAppId(belong.getAppId());
		apply.setTenantId(belong.getTenantId());
		apply.setSiteId(belong.getSiteId());
		return apply;
	}
	private Application toApply(User user,Application apply){
		apply.setActivityAid(user.getActivityAid());
		apply.setActivityTid(user.getActivityTid());
		apply.setActivityUid(user.getActivityUid());
		return apply;
	}
	private Application toApply(Belong belong,User user,Application apply){
		
		return toApply(user,toApply(belong,apply));
	}

}
