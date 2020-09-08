package pw.cdmi.msm.message.rs.v1;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.cdmi.msm.commons.Belong;
import pw.cdmi.msm.commons.User;
import pw.cdmi.msm.message.model.EventUser;
import pw.cdmi.msm.message.model.MessageContent;
import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.entity.Message;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.rs.MessageRequest;
import pw.cdmi.msm.message.rs.NotifyMessageResponse;
import pw.cdmi.msm.message.service.MessageService;

import java.util.*;

/**
 * ********************************************************** <br/>
 * 处理消息管理的请求操作
 *
 * @author MU
 * @version cdm Service Platform, 2016年6月1日
 * **********************************************************
 */

@RestController
@RequestMapping("messages/v1")
public class MessageResource {

    private static Log log = LogFactory.getLog(MessageResource.class);

    @Autowired
    private MessageService messageService;


    @PostMapping("/message")
    public @ResponseBody
    String createMessageHandler(@RequestBody MessageRequest messageRequest) {
        String newid = null;
        if (messageRequest == null || messageRequest.getMessageType() == null
                || StringUtils.isBlank(messageRequest.getContent())
                || StringUtils.isBlank(messageRequest.getOwnerId())
                || StringUtils.isBlank(messageRequest.getTitle())
                ) {
            throw new SecurityException("参数错误");
        }

        Message message = new Message();
        message.setAppId("test");
        message.setUserAid(messageRequest.getOwnerId());
        message.setTitle(messageRequest.getTitle());
        message.setContent(messageRequest.getContent());
        message.setMessageType(messageRequest.getMessageType());
        newid = messageService.createMessage(message);
        return newid;
    }

    @GetMapping("/messages/unread")
    public @ResponseBody
    List<NotifyMessageResponse> listNotifyMessageResponse(@RequestParam("userId") String userId) {

        if (StringUtils.isBlank(userId)) {
            throw new SecurityException("参数错误");
        }

        Belong belong = new Belong();
        belong.setAppId("test");
        User notifyId = new User();
        notifyId.setActivityAid(userId);

        Iterable<NotifyUserMessage> listMessage = messageService.findMessageByUserIdAndStatus(belong, notifyId, MessageStatus.UNREAD, 0, 999);
        Iterator<NotifyUserMessage> iterator = listMessage.iterator();
        ArrayList<NotifyMessageResponse> listNotifyMessageResponse = new ArrayList<NotifyMessageResponse>();
        while (iterator.hasNext()) {
            NotifyUserMessage next = iterator.next();
            listNotifyMessageResponse.add(toNotifyMessageResponse(next));

            messageService.updateUserMessageStatus(next.getId());
        }
        return listNotifyMessageResponse;
    }

    @GetMapping("/messages/readed")
    public @ResponseBody
    List<NotifyMessageResponse> listNotifyMessageResponse(@RequestParam("userId") String userId, @RequestParam("cursor") Integer cursor,
                                                          @RequestParam("maxcount") Integer maxcount) {

        if (StringUtils.isBlank(userId) || maxcount == null || cursor == null) {
            throw new SecurityException("参数错误");
        }

        Belong belong = new Belong();
        belong.setAppId("test");
        User notifyId = new User();
        notifyId.setActivityAid(userId);

        Iterable<NotifyUserMessage> listMessage = messageService.findMessageByUserIdAndStatus(belong, notifyId, MessageStatus.READED, cursor, maxcount);
        Iterator<NotifyUserMessage> iterator = listMessage.iterator();
        ArrayList<NotifyMessageResponse> listNotifyMessageResponse = new ArrayList<NotifyMessageResponse>();
        while (iterator.hasNext()) {
            NotifyUserMessage next = iterator.next();
            listNotifyMessageResponse.add(toNotifyMessageResponse(next));
        }
        return listNotifyMessageResponse;
    }

    @GetMapping("/{userId}/amount")
    public Map<String, Object> countNotifyMessage(@PathVariable("userId") String userId, @RequestParam("status") String status) {
        Belong belong = new Belong();
        belong.setAppId("test");
        User notifyId = new User();
        notifyId.setActivityAid(userId);
        MessageStatus fromEnumName = MessageStatus.fromEnumName(status);

        long countMessageByUserIdAndStatus = messageService.countMessageByUserIdAndStatus(belong, notifyId, fromEnumName);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("count", countMessageByUserIdAndStatus);

        Iterable<NotifyUserMessage> listMessage = messageService.findMessageByUserIdAndStatus(belong, notifyId, fromEnumName, 0, 5);
        Iterator<NotifyUserMessage> iterator = listMessage.iterator();
        List<EventUser> listEventUser = new ArrayList<EventUser>();
        while (iterator.hasNext()) {
            NotifyUserMessage next = iterator.next();
            JSONObject fromObject = JSONObject.fromObject(next.getContent());
            MessageContent bean = (MessageContent) JSONObject.toBean(fromObject, MessageContent.class);
            listEventUser.add(bean.getEvent().getUser());
        }
        hashMap.put("users", listEventUser);
        return hashMap;
    }

    @PutMapping("/message/{id}/status")
    public void updateStatus(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new SecurityException("id is null");
        }
        messageService.updateUserMessageStatus(id);

    }


    private NotifyMessageResponse toNotifyMessageResponse(NotifyUserMessage notifyUserMessage) {
        JSONObject fromObject = JSONObject.fromObject(notifyUserMessage.getContent());
        MessageContent bean = (MessageContent) JSONObject.toBean(fromObject, MessageContent.class);
        NotifyMessageResponse notifyMessageResponse = new NotifyMessageResponse();

        notifyMessageResponse.setId(notifyUserMessage.getId());
        notifyMessageResponse.setType(bean.getEvent().getContent().getType());
        notifyMessageResponse.setTitle(bean.getTitle());
        notifyMessageResponse.setContent(bean.getContent());

        notifyMessageResponse.setEventUser(bean.getEvent().getUser());
        notifyMessageResponse.setEventObject(bean.getEvent().getTarget());
        notifyMessageResponse.setEventContent(bean.getEvent().getContent());

        //  	java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        notifyMessageResponse.setCreateTime(String.valueOf(notifyUserMessage.getNotifyDate().getTime()));
        return notifyMessageResponse;

    }


    @PutMapping("/{id}/status")
    public void updateUserMessageStatus(@PathVariable(value = "id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new SecurityException("参数错误");
        }
        messageService.updateUserMessageStatus(id);

    }

    @DeleteMapping("/{id}")
    public void deleteNotifyUserMessage(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SecurityException("参数错误");
        }
        messageService.deleteNotifyUserMessage(id);
    }


}
