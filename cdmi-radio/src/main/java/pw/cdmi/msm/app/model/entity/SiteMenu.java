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
 * 应用导航菜单信息。
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月23日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_menu")
@Document(collection = "p_menu")
public class SiteMenu {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String appId;                       // 导航菜单编号

    private String name;                        // 导航菜单名称

    private Boolean isClass;                    // 是否为菜单分类

    private String parentId;                    // 当上级菜单为分类时，这里可以填写上级菜单的编码

    private String Model_Id;                    // 对应的内置导航模块的编号

    private String siteId;                      // 对应的应用的编号

    private String resourceURL;                 // 导航菜单的访问URL,模块为分类时为空

    private String rightLabels;                 // 该菜单所对应的权限标签

    private String orderNumber;                 // 排序号

}