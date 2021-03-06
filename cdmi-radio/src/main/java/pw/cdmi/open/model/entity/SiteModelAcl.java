package pw.cdmi.open.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "p_model_acl")
public class SiteModelAcl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                     // 记录的编号
    
    private Integer roleId;                 // 访问者角色的ID，与业务相关
    
    private Long modelId;                   // 模块的编号，与RoleId共同作为主键

    private String siteId;                  // 对应的应用的编号

}
