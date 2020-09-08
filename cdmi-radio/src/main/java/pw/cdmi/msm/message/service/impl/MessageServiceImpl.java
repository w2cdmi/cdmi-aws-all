package pw.cdmi.msm.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pw.cdmi.core.db.GenericJPAHibernateImpl;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.MessageType;
import pw.cdmi.msm.message.model.entity.Message;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.service.MessageService;
import pw.cdmi.open.model.entity.UserAccount;

/**
 * ********************************************************** <br/>
 * 实现类，提供对消息操作的方法.
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private GenericJPAHibernateImpl jpaImpl;

    @Transactional
    @Override
    public void createMessage(Message message) {
        try {
            jpaImpl.save(message);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void notifyAllUserMessage(Message message, Iterable<UserAccount> userList) {
        List<NotifyUserMessage> notifMessageList = new ArrayList<NotifyUserMessage>();
        for (UserAccount userAccount : userList) {
            NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
            notifyUserMessage.setMessageId(message.getId());
            notifyUserMessage.setUserId(userAccount.getId());
            notifyUserMessage.setNotifyDate(new Date());
            // 默认消息状态为未读
            notifyUserMessage.setMessageStatus(MessageStatus.UNREAD);
            notifMessageList.add(notifyUserMessage);
        }
        jpaImpl.save(notifMessageList);
    }

    @Transactional
    @Override
    public void createNotifyUserMessage(Message message, String userId) {
        NotifyUserMessage userMessage = new NotifyUserMessage();
        try {
            Message mess = getMessage(message.getId());
            // 判断数据库中是否有相同的消息
            if (mess != null) {
                // 创建关联，将消息id关联到通知用户消息表中
                userMessage.setMessageId(mess.getId());
                userMessage.setUserId(userId);
                userMessage.setNotifyDate(new Date());
                // 默认消息状态为未读
                userMessage.setMessageStatus(MessageStatus.UNREAD);
                jpaImpl.save(userMessage);
            } else {
                // 创建消息的同时时，创建通知用户信息
                message.setCreateDate(new Date());
                jpaImpl.save(message);
                if (message.getId() != null) {
                    userMessage.setMessageId(message.getId());
                    userMessage.setUserId(userId);
                    userMessage.setNotifyDate(new Date());
                    // 默认消息状态为未读
                    userMessage.setMessageStatus(MessageStatus.UNREAD);
                    jpaImpl.save(userMessage);
                }
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateMessage(Message message) {
        try {
            jpaImpl.update(message);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateUserMessageStatus(String id, MessageStatus status) {
        try {
            NotifyUserMessage userMessage = getNotifyUserMessage(id);
            if (userMessage != null) {
                userMessage.setMessageStatus(status);
                jpaImpl.update(userMessage);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteMessage(String id) {
        try {
            Message message = getMessage(id);
            if (message != null) {
                jpaImpl.delete(message);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteNotifyUserMessage(String id) {
        try {
            NotifyUserMessage userMessage = getNotifyUserMessage(id);
            if (userMessage != null) {
                jpaImpl.delete(userMessage);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Message getMessage(String id) {
        try {
            return jpaImpl.get(id, Message.class);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public NotifyUserMessage getNotifyUserMessage(String id) {
        try {
            return jpaImpl.get(id, NotifyUserMessage.class);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<Message> findAllMessage() {
        try {
            return jpaImpl.findAll(Message.class);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<Message> findAllMessage(int pageNo, int pageSize) {
        JPQuery jpQuery = JPQuery.createQuery("from Message m");

        return jpaImpl.find(jpQuery, pageNo, pageSize);
    }

    @Override
    public int findMessageCount() {
        return findAllMessage().size();
    }

    @Override
    public int findMessageCountByType(MessageType type) {
        int size = 0;
        List<Message> messageList = findMessageByType(type);
        if (messageList != null) {
            size = messageList.size();
        }
        return size;
    }

    @Override
    public List<Message> findMessageByType(MessageType type, int pageNo, int pageSize) {
        String jpql = "from Message o where o.messageType =:messageType";
        try {
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("messageType", type);
            return jpaImpl.find(query, pageNo, pageSize);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<Message> findMessageByType(MessageType type) {
        String jpql = "from Message o where o.messageType =:messageType";
        try {
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("messageType", type);
            return jpaImpl.find(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<NotifyUserMessage> findMessageByUserId(String userId) {
        String jpql = "from NotifyUserMessage o where o.userId =:userId";
        try {
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("userId", userId);
            return jpaImpl.find(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<NotifyUserMessage> findMessageByUserId(String userId, int pageNo, int pageSize) {
        String jpql = "from NotifyUserMessage o where o.userId =:userId";
        try {
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("userId", userId);
            return jpaImpl.find(query, pageNo, pageSize);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public int getNotifyUserMessageCountByUserId(String userId) {
        List<NotifyUserMessage> userMessages = findMessageByUserId(userId);
        if (userMessages == null) {
            return 0;
        }
        return userMessages.size();
    }

    @Override
    public List<Message> findMessageByNotifyMessages(List<NotifyUserMessage> notifyUserMessages) {
        
        if (notifyUserMessages == null || notifyUserMessages.size() == 0) {
            return null;
        }
        
        StringBuilder jpqlBuilder = new StringBuilder("from Message m where m.id IS NOT EMPTY and m.id IN ('"+notifyUserMessages.get(0).getMessageId()+"'");
        
        for(int notifyUserMessageIndex=1;notifyUserMessageIndex< notifyUserMessages.size();notifyUserMessageIndex++){
            jpqlBuilder.append(",'"+notifyUserMessages.get(notifyUserMessageIndex).getMessageId()+"'");
        }
        jpqlBuilder.append(")");
        
        return jpaImpl.find(jpqlBuilder.toString());
    }

    
    @Override
    public List<NotifyUserMessage> findMessageByUserIdAndType(String userId, MessageType type) {
        String mjpql = "select o.id from Message o where o.messageType =:messageType";
        try {
            // 根据消息类型获取消息id
            JPQuery mquery = JPQuery.createQuery(mjpql);
            mquery.setParamater("messageType", type);
            List<Message> messIdList = jpaImpl.find(mquery);
            String messIds = String.valueOf(messIdList).replace("[", "('").replace("]", "')").replaceAll(", ", "', '");
            // 根据用户id与消息id在messIds内，查找用户消息列表
            String jpql = "from NotifyUserMessage o where o.userId =:userId and o.messageId IN " + messIds;
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("userId", userId);
            return jpaImpl.find(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<NotifyUserMessage> findMessageByUserIdAndStatus(String userId, MessageStatus status) {
        String jpql = "from NotifyUserMessage o where o.userId =:userId and o.messageStatus =:messageStatus";
        try {
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("userId", userId);
            query.setParamater("messageStatus", status);
            return jpaImpl.find(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<NotifyUserMessage> findMessageByUserIdAndTypeAndStatus(String userId, MessageType type,
        MessageStatus status, int pageNo, int pageSize) {
        StringBuilder sb = new StringBuilder("from NotifyUserMessage o where o.userId = :userId");
        try {
            if (status != null) {
                sb.append(" and o.messageStatus = :messageStatus");
            }
            if (type != null) {
                // 根据消息类型获取消息id
                String mjpql = "select o.id from Message o where o.messageType = :messageType";
                JPQuery mquery = JPQuery.createQuery(mjpql);
                mquery.setParamater("messageType", type);
                List<String> messIdList = jpaImpl.find(mquery);
                String messIds = String.valueOf(messIdList).replace("[", "('").replace("]", "')")
                    .replaceAll(", ", "', '");
                // 根据用户id与消息id在messIds内，查找用户消息列表
                sb.append(" and o.messageId IN " + messIds);
            }
            String jpql = sb.toString();
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("userId", userId);
            query.setParamater("messageStatus", status);
            return jpaImpl.find(query, pageNo, pageSize);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

}
