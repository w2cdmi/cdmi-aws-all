package pw.cdmi.aws.geo.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * **********************************************************
 * <br/>
 * 基础数据，街道办、乡镇一级行政单位信息。.<br/>
 * 
 * @author 伍伟
 * @version cdm Service Platform, 2016年6月2日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "c_town")
@Document(collection="c_town")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Town {
    @Id
    @Column(unique = true)
    private Long openId;// 街道，乡镇的ID

    private String name;// 街道，乡镇的名称

    private Integer districtId;// 对应的区县行政单位ID
    
    private Integer cityId;// 对应的城市ID

    private Integer provinceId;// 对应的省份ID

    private Integer countryId;// 对应的国家ID

    private String latitude;// 打开地图时的地理位置纬度

    private String Longitude;// 打开地图时的地理位置经度

    private String accurate;// 地理位置精度

    public Town() {
    }

    public Town(Long openId, String name) {
        this.openId = openId;
        this.name = name;
    }
}
