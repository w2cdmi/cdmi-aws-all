package pw.cdmi.msm.praise.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.repositories.PraiseRepository;
import pw.cdmi.msm.praise.service.PraiseService;

@Component
public class PraiseServiceImpl implements PraiseService {

    @Autowired
    private PraiseRepository praiseRepsitory;
    
	@Override
	public void praiseObject(String account_id, PraiseTarget target) {
		//FIXME 自动创建包含当前用户请求环境信息，并设置到对象中。
		Praise praise = new Praise();
		praise.setUserId(account_id);
		praise.setTargetId(target.getId());
		praise.setTargetType(target.getType());
		praise.setCreatetime(new Date());
		praiseRepsitory.save(praise);	
	}

	@Override
	public long getPrainseNumber(String target_id) {
		return praiseRepsitory.countByTargetId();
	}

	@Override
	public List<String> listPraiser(String target_id) {
		//FIXME 根据认证条件自动组成查询范围条件
//		return praiseRepsitory.findAll();
		return null;
	}

}
