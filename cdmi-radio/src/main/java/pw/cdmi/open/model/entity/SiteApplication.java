package pw.cdmi.open.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.open.model.SiteAccessStatus;
import pw.cdmi.open.model.SiteAttribution;
import pw.cdmi.open.model.SiteStatus;

/****************************************************
 * 平台接入应用信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "p_application")
public class SiteApplication {
    @Id
    private String appId;						// 应用的ID，固定字符串长度,比如：simo

    private String appName;						// 应用的名称表示，比如：智能运维系统

    private String endpoint;					// 应用的访问域名
    
    private String labels;                      // 为应用贴标签

    private String description;					// 应用的简要描述

    @Column(nullable = false, unique = true)
    private String appKey;						// 应用的访问接入Key，自动生成固定长度字符串

    private String appSecret;					// 应用的Secret，自动生成固定长度字符串

    private String token;						// 应用访问时的临时Token

    private Integer expireTime;					// 失效时间，单位分钟

    private Date createTime;					// 应用创建的时间

    private SiteAttribution attribution;		// 应用的类型，是AWS附属服务组件，还是服务类组件，或者是客户接入应用

    private SiteStatus status;                  // 应用站点的状态
    
    private SiteAccessStatus accessStatus;		// 应用的接入状态

    private String tenantId;					// 应用的拥有人，对应租户表Id，如果是SiteAttribution为Self，则该属性为空。

}
