package pw.cdmi.msm.price.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

/**
 * **********************************************************
 * <br/>
 * 实体类，纪录产品的定价项
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "soc_productPriceItem")
@XmlRootElement(name = "ProductPriceItem")
public class ProductPriceItem {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;              // 编号

    @Column(length = 32)
    private String product_id;     // 产品编号

    @Column(length = 50)
    private String name;            // 名称

    private Date createDate;        // 创建时间

    @Column(length = 50)
    private String description;     // 描述

}
