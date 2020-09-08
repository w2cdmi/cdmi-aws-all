package pw.cdmi.msm.geo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/****************************************************
 * 基础数据，省份信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "c_province")
@Document(collection="c_province")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Province {
    @Id
    @Column(unique = true)
    private Integer openId;// 省份的ID

    private String name;// 省份的名称

    private String pinyin;// 省份的拼音

    private String fullName;// 省份的全称

    private String abbreviation;// 缩写

    private Integer countryId;// 对应的国家ID

    private String latitude;// 打开地图时的地理位置纬度

    private String Longitude;// 打开地图时的地理位置经度

    private String accurate;// 地理位置精度

    public Province() {
    }

    public Province(Integer openId, String name) {
        this.openId = openId;
        this.name = name;
    }
}
