package pw.cdmi.msm.comment.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;


@Data
@EqualsAndHashCode(callSuper=false)
public class Comment extends MultiAppSiteModel{
	private String target_id;				//评论对象的ID
	private String target_sid;				//评论对象的应用内账号id
	private String target_type;				//评论对象类型，动态信息，主播，房间
	private String content;					//评论内容
	private String commentator_id;			//评论人的id
	private String commentator_sid;			//评论人的应用内账号ID
	private Date create_time;				//评论时间
}