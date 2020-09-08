package pw.cdmi.msm.praise.service;

import java.util.Iterator;
import java.util.List;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.rs.PraiseRequest;

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
	public String createPraise(Praise praise);
	
	//获得目标对象的点赞次数，如果得到Long的最大值，则不再增加
	public long getPrainseNumber(Praise praise);
	
	//获得目标对象的点赞人列表
	public Iterator<Praise> listPraiser(Praise praise,
			int cursor, int maxcount);
	
	//检查是否已经点赞
	public Praise inspectExist(Praise praise);
	
	public void deletePraise(Praise praise);

	public Praise findOne(Praise praise);

	
}
