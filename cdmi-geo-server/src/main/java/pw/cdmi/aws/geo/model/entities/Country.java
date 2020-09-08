package pw.cdmi.aws.geo.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/****************************************************
 * 基础数据，国家信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "c_country")
@Document(collection="c_country")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Country {

    @Id
    @Column(unique = true)
    private Integer openId;// 国家的ID

    private String name;// 国家的名称

    private String englishName;// 国家的英文名

    private String gec;// 国家的GEC代码

    private String latitude;// 打开地图时的地理位置纬度

    private String Longitude;// 打开地图时的地理位置经度

    private String accurate;// 地理位置精度

    private String flagimgurl;// 国旗图片的URL

    /**以下项为非必须项**/
    private String fullName;// 国家的全称

    private String domainName;// 互联网域名

    private String iso31662L;// ISO3166 2位字母代码，-表示不适用

    private String iso31663L;// ISO3166 3位字母代码,-表示不适用

    private String iso31663N;// ISO3166 3位数字代码，0表示不适用

    private Integer phoneCode;// 对应的电话国际区号

    private Integer timeZone;// 时区时差

    private Boolean hasSovereignty;// 是否拥有主权，1为主权国家，2为无主权地区

    private Date createDate;// 国家成立时间

    public Country() {
    }

    public Country(Integer openId, String name) {
        this.openId = openId;
        this.name = name;
    }
}
