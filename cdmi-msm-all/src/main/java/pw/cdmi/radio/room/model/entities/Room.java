package pw.cdmi.radio.room.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;
import pw.cdmi.radio.room.model.Status;

@Data
@EqualsAndHashCode(callSuper=false)
public class Room extends MultiAppSiteModel{
	private String name;						//房间名称
	private String title;						//房间副标题
	private String notes;						//房间描述
	private String labels;						//房间标签
	private String cover_images;				//封面图集
	private String apply_time;					//申请时间
	private String apply_videos;				//申请视频集
	private Status status;						//主播室状态(申请中，驳回，正常，冻结，待删除）
	private String confirmation_time;			//主播室申请确认的时间
	private String owner_id;					//主播室的拥有人账号ID
}