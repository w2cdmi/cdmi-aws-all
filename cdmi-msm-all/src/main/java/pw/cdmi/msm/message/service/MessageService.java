package pw.cdmi.msm.message.service;


import java.util.List;

import org.springframework.data.domain.Page;

import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.MessageType;
import pw.cdmi.msm.message.model.entities.Message;
import pw.cdmi.msm.message.model.entities.NotifyUserMessage;
import pw.cdmi.paas.account.model.entities.UserAccount;


/**
 * **********************************************************
 * <br/>
 * 接口类，提供对消息操作的方法
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
public interface MessageService {

    /**
     * 
     * 向系统中添加一条消息
     * 
     * @param message 待添加的消息
     */
    public void createMessage(Message message);
    
    /**
     * 
     * 为用户推送一条消息
     * 
     * @param message 待添加的消息
     * @param userId 用户id
     */
    public void createNotifyUserMessage(Message message, String userId);

    /**
     * 
     * 更新消息.
     *
     * @param message 待更新的消息
     */
    public void updateMessage(Message message);
    
    /**
     * 
     * 更改用户消息的状态
     *
     * @param id 用户消息id
     * @param status 需要更新的状态
     */
    public void updateUserMessageStatus(String id, MessageStatus status);

    /**
     * 
     * 删除消息.
     *
     * @param id 待删除的消息id
     */
    public void deleteMessage(String id);
    
    /**
     * 
     * 删除消息.
     *
     * @param id 用户消息id
     */
    public void deleteNotifyUserMessage(String id);
    
    /**
     * 
     * 获得指定的消息.
     *
     * @param id 指定的消息编号
     * @return 消息
     */
    public Message getMessage(String id);
    
    /**
     * 
     * 获得指定的消息.
     *
     * @param id 用户消息id
     * return 通知用户信息对象
     */
    public NotifyUserMessage getNotifyUserMessage(String id);
    
    
    /**
     * 
     * 获取所有消息.
     *
     * @return 消息列表
     */
    public Iterable<Message> findAllMessage();

    /**
     * 
     * 根据消息类型查询消息.
     *
     * @param status 消息类型
     * @return 消息列表
     */
    public Iterable<Message> findMessageByType(MessageType type);

    /**
     * 
     * 根据用户id查询消息.
     *
     * @param userId 用户id
     * @return 消息列表
     */
    public Iterable<NotifyUserMessage> findMessageByUserId(String userId);
    
    /**
     * 
     * 根据用户id与消息类型查询消息.
     *
     * @param userId 用户id
     * @param type 消息类型
     * @return 消息列表
     */
    public Iterable<NotifyUserMessage> findMessageByUserIdAndType(String userId, MessageType type);
    
    /**
     * 
     * 根据用户id与消息状态查询消息.
     *
     * @param userId 用户id
     * @param status 消息状态
     * @return 消息列表
     */
    public Iterable<NotifyUserMessage> findMessageByUserIdAndStatus(String userId, MessageStatus status);
    

    /**
     * 
     * 根据用户id、消息类型与消息状态查询消息.
     *
     * @param userId 用户id
     * @param type 消息类型
     * @param status 消息状态
     * @param pageNo 当前页
     * @param pageSize 每页大小
     * @return 消息列表
     */
    public Page<NotifyUserMessage> findMessageByUserIdAndTypeAndStatus(String userId, MessageType type,
        MessageStatus status, int pageNo, int pageSize);

    /**
     * 
     * 查询消息.
     *
     * @param pageNo 当前页
     * @param pageSize 每页大小
     * @return 消息列表
     */
    public Page<Message> findAllMessage(int pageNo, int pageSize);

	/**
	 * 
	 * 查找该消息类型的消息条数.<br/>
	 * 
	 * @param type
	 * @return 消息条数
	 */
    public long findMessageCountByType(MessageType type);

    /**
     * 
     * 根据消息类型进行分页查找.<br/>
     * 
     * @param type
     * @param pageNo
     * @param pageSize
     * @return 消息列表
     */
    public Page<Message> findMessageByType(MessageType type, int pageNo, int pageSize);

    /**
     * 
     * 该条消息推送给所有用户.<br/>
     * 
     * @param message
     * @param userList
     */
    public void notifyAllUserMessage(Message message, Iterable<UserAccount> userList);

    /**
     * 
     * 根据notifyUserMessage查询消息.<br/>
     * 
     * @param notifyUserMessages
     * @return
     */
    public Iterable<Message> findMessageByNotifyMessages(Iterable<NotifyUserMessage> notifyUserMessages);

    /**
     * 
     * 根据userId 查询该用户消息的条数.<br/>
     * 
     * @param userId
     * @return
     */
    public long getNotifyUserMessageCountByUserId(String userId);

    /**
     * 
     * 根据用户Id查询消息与用户的关联表.<br/>
     * 
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<NotifyUserMessage> findMessageByUserId(String userId, int pageNo, int pageSize);

}
