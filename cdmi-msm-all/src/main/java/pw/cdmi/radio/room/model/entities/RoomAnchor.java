package pw.cdmi.radio.room.model.entities;

/**
 * 房间主持人
 */
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoomAnchor extends MultiAppSiteModel{
	private String room_id;						//对应的房间Id
	private String anchor_id;					//该房间的主播人的ID
	private String anchor_account_id;			//该房间的主播人的账号ID（冗余字段）
	private Date join_time;						//加入时间
	private float income;						//该主播在该房间中获取的收入总额
	private String head_image;					//主播在该房间中的头像（冗余字段）
}