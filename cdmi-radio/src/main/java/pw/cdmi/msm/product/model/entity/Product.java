package pw.cdmi.msm.product.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.msm.product.model.ProductPublishStatus;
import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.radio.model.MultiAppSiteModel;

/**
 * **********************************************************
 * <br/>
 * 出租服务表
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
@NamedQueries({
        @NamedQuery(name = "Product.nameIsRepeatByName", query = "select id from Product where name=:name"),
        @NamedQuery(name = "Product.nameIsRepeatByNameAndId", query = "select id from Product where id<>:id and name=:name") })
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "p_product")
public class Product extends MultiAppSiteModel{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;									//编号

    private String bizId;								//商品的业务编码，往往由商家自定义
    
    @Column(length = 50)
    private String name;								//名称
    
    @Column(length = 2000)
    private String overview;     						//产品概述
    
    @Enumerated(EnumType.STRING)
    private String type;       							//商品的类型，保持枚举名

    @Column(nullable = false)
    private Date publishDate;							//发布时间

    private ProductPublishStatus publishStatus;			//商品的发布状态，如果要修改发布状态，必须先下架
    
    @Enumerated(EnumType.STRING)
    private ProductSaleStatus saleStatus;				//销售状态
    
    @Column(length = 4000)
    private String desc;     							//产品介绍
    
    private String provider_id;							//供应商
    
    private String provider_name;						//商品的供应商名称
    
    private String provider_type;						//商品的供应商类型
    
//    @Column(length = 2000)
//    private String function;     						//产品功能
//    
//    @Column(length = 2000)
//    private String applicationScene;    				//应用场景

    @Column(length = 10)
    private String version;								//版本
    
    private String callback;							//订购该商品后，需要调用的后端服务接口

    private String min_price;							//产品的最低价
    
    private String max_price;							//产品的最高价
    
    private String prices;								//产品的定价清单
}
