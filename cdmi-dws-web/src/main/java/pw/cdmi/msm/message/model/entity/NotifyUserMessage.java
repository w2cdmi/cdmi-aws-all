package pw.cdmi.msm.message.model.entity;

import lombok.Data;
import net.sf.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import pw.cdmi.msm.message.model.MessageContent;
import pw.cdmi.msm.message.model.MessageStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * ********************************************************** <br/>
 * 通知用户信息表
 *
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 * **********************************************************
 */
@Data
@Entity
@Table(name = "soc_notify_user_message", indexes = {
        @Index(name = "notify_1", columnList = "notifyAid")
})
public class NotifyUserMessage {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id; // 通知用户信息的ID

    @Column(name = "app_id", nullable = false)
    private String appId; // 数据归属应用ID

    @Column(name = "site_id", nullable = true)
    private String siteId; // 对应的平台内子站点Id，这个子站点可能是租户，可以是频道

    @Column(name = "tenant_id", nullable = true)
    private String tenantId; // 对应的租户ID

    private String notifyUid; // 通知对象id

    private String notifyAid; //

    private String notifyTid;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus; // true为已读，false为未读

    private String type; // 消息类型枚举

    @CreatedDate
    private Date notifyDate; // 通知时间

    private Boolean isBroadcast; // 是否是广播类消息，广播类消息会对应Message表

    private String messageId; // 对应消息编号, 非广播内消息无

    @Column(length = 3000)
    private String content; // 消息内容体，采用JSON格式进行存储，如果是广播类消息为null。

    private String targerId; // 消息的目标对象id

    private String eventOjectType; // 外部自定义的一个消息关联对象类型
    private String eventObjectId; // 外部自定义的消息关联对象的Id
    private String eventObjectOperations; // 可选，用户可以对关联对象所做的操作

    public NotifyUserMessage() {
    }

    ;

    public static NotifyUserMessage messageContent(String messageContent) {
        JSONObject json = JSONObject.fromObject(messageContent);
        MessageContent bean = (MessageContent) JSONObject.toBean(json,
                MessageContent.class);

        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();

        notifyUserMessage.setContent(messageContent);

        notifyUserMessage.setEventObjectId(bean.getEvent().getTarget().getId());
        notifyUserMessage.setEventOjectType(bean.getEvent().getTarget()
                .getType());
        // if(bean.getEvent().getTarget().getOperations()!=null)
        // notifyUserMessage.setEventObjectOperations(JSONObject.fromObject(bean.getEvent().getTarget().getOperations()).toString());

        notifyUserMessage.setTargerId(bean.getEvent().getContent().getId());
        notifyUserMessage.setType(bean.getEvent().getContent().getType());
        notifyUserMessage.setIsBroadcast(false);

        return notifyUserMessage;

    }
}
