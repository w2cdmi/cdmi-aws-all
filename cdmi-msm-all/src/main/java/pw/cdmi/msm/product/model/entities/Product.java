package pw.cdmi.msm.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import pw.cdmi.msm.product.model.ProductPublishStatus;
import pw.cdmi.msm.product.model.ProductSaleStatus;

/**
 * ********************************************************** <br/>
 * 出租服务表
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "p_product")
public class Product {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String bizId; // 商品的业务编码，往往由商家自定义

	private String name; // 名称

	private String overview; // 产品概述

	private String type; // 商品的类型，保持枚举名

	@Column(nullable = false)
	private Date publishDate; // 发布时间

	@Enumerated(EnumType.STRING)
	private ProductPublishStatus publishStatus; // 商品的发布状态，如果要修改发布状态，必须先下架

	@Enumerated(EnumType.STRING)
	private ProductSaleStatus saleStatus; // 销售状态

	private String presentation; // 产品介绍

	private String providerId; // 供应商

	private String providerName; // 商品的供应商名称

	private String providerType; // 商品的供应商类型

	// @Column(length = 2000)
	// private String function; //产品功能
	//
	// @Column(length = 2000)
	// private String applicationScene; //应用场景

//	@Version
//	private Long version; // 版本

	private String callback; // 订购该商品后，需要调用的后端服务接口

	private float minPrice; // 产品的最低价

	private float maxPrice; // 产品的最高价

	private String prices; // 产品的定价清单

	// 数据所对应的site的ID，在微信第三方开放平台中，该ID对应的开发平添客户的ID
	private String siteId;
	// 数据所对应的应用的ID
	private String appId;
	// 为对象创建的全局唯一ID
}
