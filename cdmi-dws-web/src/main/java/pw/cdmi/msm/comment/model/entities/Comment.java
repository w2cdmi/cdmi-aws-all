package pw.cdmi.msm.comment.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;

@Data
@Entity
@Table(name = "t_comment",indexes = {
		@Index(name = "comment_1" , columnList = "target_id"),
		@Index(name = "comment_2 " , columnList = "owner_id")
})
public class Comment implements MultiTenancy, MultiSite, MultiApplication {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "app_id", nullable = false)
	private String appId; // 数据归属应用ID

	@Column(name = "site_id", nullable = true)
	private String siteId; // 对应的平台内子站点Id，这个子站点可能是租户，可以是频道

	@Column(name = "tenant_id", nullable = true)
	private String tenantId; // 对应的租户ID

	@Column(name = "target_id", nullable = false)
	private String targetId; // 评论对象的ID

	@Column(name = "target_type", nullable = false)
	private String targetType; // 评论对象类型，动态信息，主播，房间

	@Column(name = "commentator_uid", nullable = true)
	private String userUid; // 评论人的id

	@Column(name = "commentator_aid", nullable = false)
	private String userAid; // 评论人的应用内账号ID

	@Column(name = "commentator_tid", nullable = true)
	@CreatedBy
	private String userTid; // 对应的租户账号Id
	@Column(name = "owner_id", nullable = false)
	private String ownerId;
	@Column(name = "commentator_name", nullable = true)
	private String userName; // 评论人名字
	
	@Column(name = "commentator_type", nullable = true)
	private String userType;

	@Column(name = "head_image", nullable = true)
	private String headImage; // 评论人头像

	@Column(name = "content", nullable = false)
	private String content; // 评论内容
	
	@Column(name = "praise_number",nullable = true)
	private int praiseNumber;
	
	@CreatedDate
	@Column(name = "create_time", nullable = false)
	private Date createTime; // 评论时间

}