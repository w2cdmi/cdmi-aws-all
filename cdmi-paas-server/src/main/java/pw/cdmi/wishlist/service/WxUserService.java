package pw.cdmi.wishlist.service;

import pw.cdmi.wishlist.model.entities.WxUser;

public interface WxUserService {
	
	//指定用户编号的用户信息
	public WxUser getWxUser(String id);
	
    public WxUser createWxUser(WxUser wxUser);

    //根据openId查询微信用户信息
    public WxUser getWxUserByWxOpenId(String openId);
}
