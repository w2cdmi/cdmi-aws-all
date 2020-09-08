package pw.cdmi.msm.price.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import pw.cdmi.msm.price.model.ProductPriceModel;

import org.hibernate.annotations.GenericGenerator;

/**
 * **********************************************************
 * <br/>
 * 实体类，记录产品定价项详细信息
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "soc_priceItemInfo")
@XmlRootElement(name = "PriceItemInfo")
public class PriceItemInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;              // 编号

    @Column(length = 32)
    private String priceItem_Id;     // 产品定价项编号

    @Column(length = 50)
    private String content;            // 计价项内容
    
    @Column(length = 50)
    private String Standard;        //计价项规格
    
    @Column(length = 50)
    private String unit;            //单位
    
    private Double price;           //单价

    @Enumerated(EnumType.STRING)
    private ProductPriceModel priceModel; //定价模式
    
    private Date updateDate;        // 更改时间

    @Column(length = 50)
    private String description;     // 描述
}
