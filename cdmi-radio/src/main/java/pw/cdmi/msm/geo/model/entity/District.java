package pw.cdmi.msm.geo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/****************************************************
 * 基础数据，行政县区信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "c_district")
@Document(collection = "c_district")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class District {
    @Id
    @Column(unique = true)
    private Integer openId;// 县区的ID

    private String name;// 县区的名称

    private Integer cityId;// 对应的城市ID

    private Integer provinceId;// 对应的省份ID

    private Integer countryId;// 对应的国家ID

    private Integer postcode;// 对应的邮编

    private String latitude;// 打开地图时的地理位置纬度

    private String Longitude;// 打开地图时的地理位置经度

    private String accurate;// 地理位置精度

    public District() {
    }

    public District(Integer openId, String name) {
        this.openId = openId;
        this.name = name;
    }
}
