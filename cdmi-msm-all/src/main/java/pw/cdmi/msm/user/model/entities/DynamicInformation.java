package pw.cdmi.msm.user.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;


@Data
@EqualsAndHashCode(callSuper=false)
public class DynamicInformation extends MultiAppSiteModel{
	private String owner_id;					//发动动态的拥有人，可以是房间，可以是某个主播
	private String owner_type;					//动态拥有人的类型（主播，房间，用户）
	private String title;						//可为空
	private String content;						//动态信息的文字描述
	private String create_time;					//动态信息创建时间
	private String images;						//发布的多张照片
	private String videos;						//发布的视频
	private boolean islocked;					//内容被锁定，需要使用钻石开启查看
}