package pw.cdmi.radio.anchor.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;
import pw.cdmi.radio.room.model.Status;

@Data
@EqualsAndHashCode(callSuper=false)
public class Anchor extends MultiAppSiteModel{
	private String account_id;					//对应的账号Id
	private String name;						//主播名称
	private String apply_time;					//申请成为主播的时间
	private Status status;						//主播信息状态(申请中，驳回，正常，冻结，待删除）
	private String confirmation_time;			//主播申请确认的时间
	private String apply_videos;				//申请主播时上传的视频
	private String cover_images;				//封面图集
}
