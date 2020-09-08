package pw.cdmi.msm.app.model.entity.ext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * 基础数据，服务的开通城市。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "e_service_city")
@Document(collection="e_service_city")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ServiceCity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                       	// 信息编号
    
    private String appId;                       // 对应的应用ID
    
    private Integer cityId;						// 对应的城市Id
    
    private String name;                        // 对象的城市名称，冗余字段
    
    private String 	coordinates;				// 城市的地理位置坐标
    
    private String photoes;						// 城市的背景图片，多幅{url1,url2}
    
    @Enumerated(EnumType.STRING)
    private ServiceStatus  status;				// 城市服务状态

}
