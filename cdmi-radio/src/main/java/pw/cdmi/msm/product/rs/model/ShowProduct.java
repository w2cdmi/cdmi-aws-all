package pw.cdmi.msm.product.rs.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pw.cdmi.open.utils.JaxbDateAdapter;
import pw.cdmi.open.utils.JsonDateSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement(name = "Product")
@JsonInclude(Include.NON_NULL)
public class ShowProduct {

    private String id;              // 编号

    private String name; // 名称

    private String type; // 类型

    private String typeText; // 类型

    private String companyId; // 供应商

    private Date publishDate;       // 发布时间

    private String saleStatus; // 销售状态

    private String saleStatusText; // 销售状态

    private String introduce;     // 产品简介

    private String overview;     // 产品概述

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getSaleStatusText() {
        return saleStatusText;
    }

    public void setSaleStatusText(String saleStatusText) {
        this.saleStatusText = saleStatusText;
    }


    private Double price;    		// 产品价格

    private String version;         // 版本

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

}
