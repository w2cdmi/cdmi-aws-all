package pw.cdmi.msm.app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.msm.app.model.SiteAccessStatus;
import pw.cdmi.msm.app.model.SiteAttribution;
import pw.cdmi.msm.app.model.SiteStatus;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * **********************************************************
 * <br/>
 * 平台接入应用信息。
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月23日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_application")
@Document(collection = "p_application")
public class SiteApplication {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String appId;                       // 应用的ID，固定字符串长度

    private String appName;                     // 应用的名称表示，比如：智能运维系统

    private String endpoint;                    // 应用的访问路径

    private String labels;                      // 为应用贴标签

    private String description;                 // 应用的简要描述

    @Column(nullable = false, unique = true)
    private String appKey;                      // 应用的访问接入Key，自动生成固定长度字符串

    private String appSecret;                   // 应用的Secret，自动生成固定长度字符串

    private String token;                       // 应用访问时的临时Token

    private Integer expireTime;                     // 失效时间，单位分钟

    private Date createTime;                    // 应用创建的时间

    @Enumerated(EnumType.STRING)
    private SiteAttribution attribution;        // 表示是AWS附属服务组件，还是服务类组件，或者是客户接入应用

    @Enumerated(EnumType.STRING)
    private SiteAccessStatus accessStatus;      // 应用的接入状态

    @Enumerated(EnumType.STRING)
    private SiteStatus status;                  // 表示该应用信息是否生效，新建，还是未缴费，欠费，等待审批

    private Long tenantId;                      // 应用的拥有人，对应租户表Id，如果是SiteAttribution为Self，则该属性为空。

}