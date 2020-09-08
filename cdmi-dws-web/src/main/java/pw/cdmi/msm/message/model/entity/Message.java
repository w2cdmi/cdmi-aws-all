package pw.cdmi.msm.message.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import pw.cdmi.msm.message.model.MessageType;

import javax.persistence.*;
import java.util.Date;

/**
 * ********************************************************** <br/>
 * 消息信息表
 *
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 * **********************************************************
 */
@Entity
@Table(name = "soc_message")
@Data
public class Message {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;                    // 消息的ID

    @Column(name = "app_id", nullable = false)
    private String appId; // 数据归属应用ID

    @Column(name = "site_id", nullable = true)
    private String siteId; // 对应的平台内子站点Id，这个子站点可能是租户，可以是频道

    @Column(name = "tenant_id", nullable = true)
    private String tenantId; // 对应的租户ID

    private String userUid;        //用户平台id

    private String userAid;        //用户应用id

    private String userTid;        //用户租户id


    @Column(length = 100)
    private String title;                // 消息标题

    @Column(length = 2000, nullable = false)
    private String content;            // 消息内容

    @Enumerated(EnumType.STRING)
    private MessageType messageType;    // 消息类型枚举

    private Boolean messaageSendState;    // true表示已推送 false表示未推送

    private Date createDate;            // 创建时间
}
