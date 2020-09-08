package pw.cdmi.msm.message.rs.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pw.cdmi.core.http.utils.RequestParameterHandleUtils;
import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.MessageType;
import pw.cdmi.msm.message.model.entities.Message;
import pw.cdmi.msm.message.model.entities.MessageForUserEntry;
import pw.cdmi.msm.message.model.entities.NotifyUserMessage;
import pw.cdmi.msm.message.service.MessageService;
import pw.cdmi.paas.account.model.entities.UserAccount;
import pw.cdmi.paas.account.service.UserService;

/**
 * ********************************************************** <br/>
 * 处理消息管理的请求操作
 * 
 * @author MU
 * @version cdm Service Platform, 2016年6月1日
 ***********************************************************
 */

@Path("/v1")
@Service("messageResource")
public class MessageResource {

    private static Log log = LogFactory.getLog(MessageResource.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userservice;

    @Resource
    private RequestParameterHandleUtils requestParameterHandleUtils;

    /**
     * 向系统添加一条消息
     */
    @POST
    @Path("/message/")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject createMessageHandler(String postRequest) {
        log.info("message Request:" + postRequest);
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        Message message = new Message();
        message = requestParameterHandleUtils.convertRequestParams2Entity(message, postRequest);
        message.setMessaageSendState((byte) 0);
        message.setCreateDate(new Date());
        messageService.createMessage(message);
        json1.put("code", "0");
        json1.put("message", "success");
        json.put("status", json1);

        return json;
    }

    /**
     * 
     * 为用户推送一条消息
     * 
     * @param message
     *            待添加的消息
     * @param userId
     *            用户id
     */
    @POST
    @Path("/sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject createNotifyUserMessage(String shareRequest) {
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        Message message = new Message();
        message = requestParameterHandleUtils.convertRequestParams2Entity(message, shareRequest);
        Iterable<UserAccount> userList = userservice.findAllUserAccount();
        messageService.notifyAllUserMessage(message, userList);
        ;
        message = messageService.getMessage(message.getId());
        message.setMessaageSendState((byte) 1);
        messageService.updateMessage(message);
        json1.put("code", "0");
        json1.put("message", "success");
        json.put("status", json1);
        return json;
    }

    /**
     * 更新消息.
     * 
     * @param message
     *            待更新的消息
     */
    @Path("/message")
    @PUT
    public boolean updateMessage(Message message) {
        return false;
    }

    /**
     * 
     * 更改用户消息的状态
     *
     * @param id
     *            用户消息id
     * @param status
     *            需要更新的状态
     */
    @Path("/updateUserMessageStatus/{id}/{status}")
    public boolean updateUserMessageStatus(@PathParam(value = "id") String id, @PathParam(value = "status") int answer) {
        return false;
    }

    /**
     * 
     * 删除消息.
     *
     * @param id
     *            待删除的消息id
     */
    @Path("/delMessage/id/{id}")
    @POST
    public boolean deleteMessage(@PathVariable(value = "id") String id) {
        return false;
    }

    /**
     * 
     * 删除消息.
     *
     * @param id
     *            用户消息id
     */
    @POST
    @Path("/delNotifyUserMessage/")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteNotifyUserMessage(String delNotifyUserMessageRequest) {
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = requestParameterHandleUtils.convertRequestParams2Entity(notifyUserMessage, delNotifyUserMessageRequest);
        
        messageService.deleteNotifyUserMessage(notifyUserMessage.getId());
        
        json1.put("code", "0");
        json1.put("message", "success");
        json.put("status", json1);
        
        return json;
    }

    /**
     * 
     * 获得指定的消息.
     *
     * @param id
     *            指定的消息编号
     * @return 消息
     */
    @Path("/message/{id}")
    @GET
    @Produces("application/json;charset=utf-8")
    public Message getMessage(@PathParam(value = "id") String id) {
        return null;
    }

    /**
     * 
     * 获得指定的消息.
     *
     * @param id
     *            用户消息id return 通知用户信息对象
     */
    @GET
    @Path("/getMessageForUser/")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMessageForUser(@QueryParam("id") String id) {
        JSONObject json = new JSONObject();
        NotifyUserMessage notifyUserMessage = messageService.getNotifyUserMessage(id);
        if(notifyUserMessage == null){
            return json;
        }
        if(notifyUserMessage.getStatus()==MessageStatus.UNREAD){
            messageService.updateUserMessageStatus(id, MessageStatus.READED);
        }
        Message message =  messageService.getMessage(notifyUserMessage.getMessageId());
        MessageForUserEntry messageForUserEntry = new MessageForUserEntry();
        messageForUserEntry.putInfor(notifyUserMessage, message);
        
        json.put("data", messageForUserEntry);
        
        return json;
    }

    /**
     * 
     * 获取所有消息.
     *
     * @return 消息列表
     */
    @GET
    @Path("/findAllMessage")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findAllMessage(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {

        JSONObject json = new JSONObject();
        Page<Message> list = messageService.findAllMessage(pageNo, pageSize);
        json.put("datas", JSONArray.fromObject(list));
        int count = list.getSize();
        int totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        json.put("count", count);
        json.put("totalPages", totalPages);
        return json;

    }

    @GET
    @Path("/findMessage/product")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageByProduct(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        return findMessageByType(MessageType.PRODUCT, pageNo, pageSize);
    }

    @GET
    @Path("/findMessage/security")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageBySecurity(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        return findMessageByType(MessageType.SECURITY, pageNo, pageSize);
    }

    @GET
    @Path("/findMessage/service")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageByService(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        return findMessageByType(MessageType.SERVICE, pageNo, pageSize);
    }

    @GET
    @Path("/findMessage/fault")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageByFault(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        return findMessageByType(MessageType.FAULT, pageNo, pageSize);
    }

    @GET
    @Path("/findMessage/activity")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageByActivity(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        return findMessageByType(MessageType.ACTIVITY, pageNo, pageSize);
    }

    private JSONObject findMessageByType(MessageType type, int pageNo, int pageSize) {
        JSONObject json = new JSONObject();
        Iterable<Message> productMessages = messageService.findMessageByType(type, pageNo, pageSize);
        json.put("datas", JSONArray.fromObject(productMessages));
        long count = messageService.findMessageCountByType(type);
        long totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        json.put("count", count);
        json.put("totalPages", totalPages);
        return json;
    }
 

    /**
     * 
     * 根据用户id查询消息.
     *
     * @param userId
     *            用户id
     * @return 消息列表
     */
    @GET
    @Path("/findMessByUserId/")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findMessageByUserId(@QueryParam("userId") String userId, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize) {
        
        JSONObject json = new JSONObject();
        
        Iterable<NotifyUserMessage> notifyUserMessages = messageService.findMessageByUserId(userId, pageNo, pageSize);
        Iterable<Message> messages = messageService.findMessageByNotifyMessages(notifyUserMessages);
        
        json.put("datas", JSONArray.fromObject(getMessageForUserList(notifyUserMessages,messages)));
        
        long count = messageService.getNotifyUserMessageCountByUserId(userId);
        
        long totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        json.put("count", count);
        json.put("totalPages", totalPages);
        return json;
    }

    private List<MessageForUserEntry> getMessageForUserList(Iterable<NotifyUserMessage> notifyUserMessages,
    		Iterable<Message> messages) {
    	List<MessageForUserEntry> messageForUserEntries = new ArrayList<MessageForUserEntry>();

        if (notifyUserMessages == null || messages == null) {
            return messageForUserEntries;
        }
        
        for(NotifyUserMessage notifyUserMessage:notifyUserMessages){
            
            for(Message message:messages){
                if(notifyUserMessage.getMessageId().equals(message.getId())){
                    MessageForUserEntry messageForUserEntry = new MessageForUserEntry();
                    messageForUserEntry.putInfor(notifyUserMessage, message);
                    messageForUserEntries.add(messageForUserEntry);
                    break;
                }
            }
            
        }
        
        return messageForUserEntries;

    }

    /**
     * 
     * 根据用户id与消息类型查询消息.
     *
     * @param userId
     *            用户id
     * @param type
     *            消息类型
     * @return 消息列表
     */
    @GET
    @Path("/findMessByUserIdAndType/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NotifyUserMessage> findMessageByUserIdAndType(@QueryParam("userId") String userId,
        @QueryParam("messageType") String type, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize) {
        
        return null;
        
    }

    /**
     * 
     * 根据用户id与消息状态查询消息.
     *
     * @param userId
     *            用户id
     * @param status
     *            消息状态
     * @return 消息列表
     */
    @Path("/findMesByUseridAndStatus/userId/{userId}/status/{status}")
    @GET
    public List<NotifyUserMessage> findMessageByUserIdAndStatus(@PathVariable(value = "userId") String userId,
        @PathVariable(value = "status") String status) {
        return null;
    }

    /**
     * 
     * 根据用户id、消息类型与消息状态查询消息.
     *
     * @param userId
     *            用户id
     * @param type
     *            消息类型
     * @param status
     *            消息状态
     * @return 消息列表
     */
    @Path("/findMessByUserIdAndTypeAndStatus/userId/{userId}/type/{type}/status/{status}")
    @GET
    public List<NotifyUserMessage> findMessageByUserIdAndTypeAndStatus(@PathVariable(value = "userId") String userId,
        @PathVariable(value = "type") String type, @PathVariable(value = "status") String status) {
        return null;
    }

}
