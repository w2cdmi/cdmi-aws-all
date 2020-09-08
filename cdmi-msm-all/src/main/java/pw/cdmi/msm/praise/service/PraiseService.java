package pw.cdmi.msm.praise.service;

import java.util.List;

import pw.cdmi.msm.praise.model.PraiseTarget;

/**
 * **********************************************************
 * <br/>
 * 接口类，提供点赞模块的操作接口
 * 
 * @author 伍伟
 * @version CDMI Service Platform, 2017年1月29日
 ***********************************************************
 */
public interface PraiseService {
	//对目标对象进行点赞
	public void praiseObject(String account_id, PraiseTarget target);
	
	//获得目标对象的点赞次数，如果得到Long的最大值，则不再增加
	public long getPrainseNumber(String target_id);
	
	//获得目标对象的点赞人列表
	public List<String> listPraiser(String target_id);
	
}
