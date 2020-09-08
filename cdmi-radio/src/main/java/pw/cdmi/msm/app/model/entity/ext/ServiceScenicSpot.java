package pw.cdmi.msm.app.model.entity.ext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/****************************************************
 * 基础数据，服务的开通的景区。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2017
 ***************************************************/
@Data
@Entity
@Table(name = "e_service_scenicspot")
@Document(collection="e_service_scenicspot")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ServiceScenicSpot {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                       	// 信息编号
    
    private String appId;                       // 对应的应用ID
    
    private Integer cityId;						// 对应的城市Id
    
    private String scenicspotId;				// 对应的景区ID
    
    private String tag;							// 对应的景区标签
    
	private String coverFileUrl;				// 景区封面文件访问地址
	
	private String photoes;						// 景区相册图片URL{url1,url2,url3}
	
	private Integer commentNum;					// 对该景区的评论数
	
	private Integer visitorNum;					// 到访会员数
	
	private String playGuide;					// 景区游玩指引
	
	private String voiceFileUrl;				// 景区语音介绍
	
	private Integer sortNum;					// 排序号
	
    @Enumerated(EnumType.STRING)
    private ServiceStatus  status;				// 景区服务状态
}
