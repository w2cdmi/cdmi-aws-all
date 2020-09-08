package pw.cdmi.wishlist.service;

import pw.cdmi.wishlist.model.entities.Participant;

public interface CrowdFundingService {
	
	//邀请好友围观指定用户参与的众筹商品活动
	public void addOnlooker(String friendId, String ownerId, String productId);
	
	//检查指定用户是否参与到众筹商品活动
	public boolean isJoin(String userId, String productId);
	
	//获得众筹参与人的信息
	public Participant getParticipant(String userId, String productId);
	
	//增加众筹参与人的邀请人数，数据库要加锁
	public long updateInviterNumberOfParticipant(Participant participant);
	
	//增加众筹参与人的邀请人数，数据库要加锁
	public long updateInviterNumberOfParticipant(String  participantId);

	//将指定的商品添加到指定用户的心愿单中
	public void addParticipant(String userId, String productId);
}
