package pw.cdmi.msm.app.model.entity.ext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/****************************************************
 * 基础数据，景区景点。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2017
 ***************************************************/
@Data
@Entity
@Table(name = "c_viewspot")
@Document(collection="c_viewspot")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ViewSpot {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                       	// 信息编号
    
    private String scenicSpotId;				// 所属景区信息编号
    
	private String name;						// 景点名称
	
	private String ename;						// 景点英文名
	
	private String coordinates;					// 地理坐标位置{type:GPS,Longitude:334,22,latitude:33232.22,accurate:32}

	private String coverFileUrl;				// 景点封面文件访问地址
	
	private String photoes;						// 景区相册图片URL{url1,url2,url3}
	
	private String introduction;				// 景点文字介绍
	
	private String voiceFileUrl;				// 景点语音介绍
}
