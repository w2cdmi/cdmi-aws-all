package pw.cdmi.msm.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * **********************************************************
 * <br/>
 * 应用用户的登录域信息。
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月23日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_application_logindomain")
@Document(collection = "p_application_logindomain")
public class SiteUserDomain {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                          // 用户域编号

    private String siteId;                      // 对应应用的编号

    private String name;                        // 用户域名称

    private String loginURL;                    // 登录访问路径

    private String returnURL;                   // 验证后原网站验证结果接收路径

    private String loginRole;                   // 对应的访问角色枚举,和具体的业务相关

    private String description;                 // 用户域的简要描述

}
