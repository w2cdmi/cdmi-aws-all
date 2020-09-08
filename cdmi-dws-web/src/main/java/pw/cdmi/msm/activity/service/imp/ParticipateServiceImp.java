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
import pw.cdmi.msm.activity.model.ParticipateTypeSupper;
import pw.cdmi.msm.activity.model.User;
import pw.cdmi.msm.activity.model.entities.Application;
import pw.cdmi.msm.activity.model.entities.Participate;
import pw.cdmi.msm.activity.repositories.ParticipateRepository;
import pw.cdmi.msm.activity.service.ParticipateService;
@Component
public class ParticipateServiceImp implements ParticipateService{
	@Autowired
	private ParticipateRepository participateRepository;
	@Override
	public String createParticipate(Participate participate) {
		
		participate.setCreateTime(new Date());
		
		return participateRepository.save(participate).getId();
	}

	@Override
	public String createParticipateByApply(Application apply) {
		Participate participate = new Participate();
		
		participate.setAppId(apply.getAppId());
		participate.setSiteId(apply.getSiteId());
		participate.setTenantId(apply.getTenantId());
		
		participate.setActivityAid(apply.getActivityAid());
		participate.setActivityTid(apply.getActivityTid());
		participate.setActivityUid(apply.getActivityUid());
		
		participate.setCreateTime(new Date());
		participate.setType(ParticipateTypeSupper.apply);
		
		participate.setActivityId(apply.getActivityId());
		return participateRepository.save(participate).getId();
	}

	@Override
	public Participate getParticipateByid(String id) {
		
		return participateRepository.findOne(id);
	}

	@Override
	public Iterable<Participate> getParticipateByUser(Belong belong, User user,Integer cursor,Integer maxcount) {
		Participate participate = new Participate();	
        Pageable pageRequest = new PageRequest(cursor,maxcount);
		return participateRepository.findAll(Example.of(toParticipate(belong, user,participate)),pageRequest);
	}

	@Override
	public Iterable<Participate> getParticipateByActivityId(Belong belong,
			String activityId,Integer cursor,Integer maxcount) {
		Participate participate = new Participate();
		participate.setActivityId(activityId);	
        Pageable pageRequest = new PageRequest(cursor,maxcount);
		
		return participateRepository.findAll(Example.of(toParticipate(belong,
				participate)),pageRequest);
	}

	@Override
	public Participate getParticipateByActivityIdAndUser(Belong belong,
			String activityId, User user) {
		Participate participate = new Participate();
		participate.setActivityId(activityId);
		return participateRepository.findOne(Example.of(toParticipate(belong, user,participate)));
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		participateRepository.delete(id);
		
	}
	private Participate toParticipate(Belong belong,Participate participate){
		participate.setAppId(belong.getAppId());
		participate.setSiteId(belong.getSiteId());
		participate.setTenantId(belong.getTenantId());		
		return participate;
	}
	private Participate toParticipate(User user,Participate participate){
		participate.setActivityUid(user.getActivityUid());
		participate.setActivityAid(user.getActivityAid());
		participate.setActivityTid(user.getActivityTid());
		return participate;
		
	}
	private Participate toParticipate(Belong belong,User user,Participate participate){
		return toParticipate(user,toParticipate(belong,participate));
		
	}

	
}
