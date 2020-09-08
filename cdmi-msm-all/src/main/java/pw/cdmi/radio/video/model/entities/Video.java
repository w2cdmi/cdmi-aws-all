package pw.cdmi.radio.video.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;


@Data
@EqualsAndHashCode(callSuper=false)
public class Video extends MultiAppSiteModel{
	private String owner_id;			//视频的拥有人，可以是房间，可以是某个主播
	private String owner_type;			//视频拥有人的类型（主播，房间，用户）
	private String title;				//视频的标题，可为空
	private String content;				//视频的文字描述
	private String create_time;			//创建的时间
	private String image;				//视频截图
	private String video;				//发布的视频，只能有一个
	private String islocked;			//内容被锁定，需要使用钻石开启查看
}
