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
@Table(name = "p_model")
@Document(collection = "p_model")
public class SiteModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;                          // 模块编号

    private String name;                        // 模块名称

    private Boolean isClass;                    // 是否只是模块分类

    private String parentId;                    // 当上级模块为分类时，这里可以填写上级模块的编码

    private String siteId;                      // 对应的应用的编号

    private String resourceURL;                 // 导航模块的访问URL,模块为分类时为空

    private String rightLabels;                 // 该模块所拥有的全部权限标签列表

    private String orderNumber;                 // 排序号

}