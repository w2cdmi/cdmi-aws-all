package pw.cdmi.msm.app.model.entity.ext;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * 基础数据，城市景区。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2017
 ***************************************************/

@Data
@Entity
@Table(name = "c_scenicspot")
@Document(collection="c_scenicspot")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ScenicSpot {
	
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                       	// 信息编号
    
	private Integer cityId;						// 对应的城市ID
	
	private Integer provinceId;					// 对应的省份ID
	
	private String name;						// 景区名称
	
	private String ename;						// 景区英文名
	
	private String title;						// 景区标题
	
	private String mapFileUrl;					// 景区地图文件访问地址
	
	private String coordinates;					// 地理坐标位置{type:GPS,Longitude:334,22,latitude:33232.22,accurate:32}
	
	private String coverFileUrl;				// 景区封面文件访问地址
	
	private String photoes;						// 景区相册图片URL{url1,url2,url3}
	
	private Integer commentNum;					// 对该景区的评论数
	
	private String playGuide;					// 景区游玩指引
	
	private String introduction;				// 景区文字介绍
	
	private String voiceFileUrl;				// 景区语音介绍
	
	private Integer grade;						// 景区等级
	
	private Integer rankings;					// 景区城市排名
	
	private String tourMapfile;					// 景区导览地图文件
}
