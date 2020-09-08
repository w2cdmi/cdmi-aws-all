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
 * 系统的指定用户登录域下的操作权限。
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月23日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_data_privilege")
@Document(collection = "p_data_privilege")
public class SiteDataPrivilege {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                          // 操作权限编号

    private String name;                        // 权限名称

    private Boolean modelEnum;                  // 对应的应用功能模块的标示

    private String authEnum;                    // 对应业务系统的权限的枚举

    private String siteId;                      // 对应的应用的编号

    private String orderNumber;                 // 排序号

    private String description;                 // 对权限的一个描述

}