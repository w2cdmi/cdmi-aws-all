package pw.cdmi.open.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/************************************************************
 * 实体表 ,记录应用所采用的短信服务配置信息.
 * 
 * @author WUWEI
 * @version iSoc Service Platform, 2015年3月1日
 ************************************************************/
@Data
@Entity
@Table(name = "p_mms_configuration")
public class MMSConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private String url;

    private String password;

    private String signature;

    private String username;

    private String appId;
}
