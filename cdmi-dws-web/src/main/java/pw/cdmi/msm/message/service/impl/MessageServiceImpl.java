package pw.cdmi.msm.message.service.impl;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.cdmi.msm.commons.Belong;
import pw.cdmi.msm.commons.User;
import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.MessageType;
import pw.cdmi.msm.message.model.entity.Message;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.repositories.MessageRepository;
import pw.cdmi.msm.message.repositories.NotifyUserMessageRepository;
import pw.cdmi.msm.message.service.MessageService;

import java.util.Date;
import java.util.Iterator;

/**
 * ********************************************************** <br/>
 * 实现类，提供对消息操作的方法.
 *
 * @author Liujh
 * @version cdm Service Platform, 2016年5月27日
 * **********************************************************
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRepository repository;

    @Autowired
    private NotifyUserMessageRepository notifyUserMessageRepository;

    @Override
    public String createMessage(Message message) {

        message.setCreateDate(new Date());
        message.setMessaageSendState(false);
        return repository.save(message).getId();
    }

    @Override
    public String createNotifyUserMessage(NotifyUserMessage notifyUserMessage) {

        notifyUserMessage.setNotifyDate(new Date());
        notifyUserMessage.setMessageStatus(MessageStatus.UNREAD);

        NotifyUserMessage save = null;
        try {
            save = notifyUserMessageRepository.save(notifyUserMessage);
        } catch (Exception e) {
            log.error("create notify user message errer "+ JSONObject.fromObject(notifyUserMessage).toString());
            e.printStackTrace();
            return null;
        }
        return save.getId();
    }

    @Override
    public void updateUserMessageStatus(String id) {
        NotifyUserMessage findOne = notifyUserMessageRepository.findOne(id);
        if (findOne == null) {
            throw new SecurityException("没有更新对象");
        }

        findOne.setMessageStatus(MessageStatus.READED);

        try {
            notifyUserMessageRepository.save(findOne);
        } catch (Exception e) {
            log.error( "update notify user message status errer "+id);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNotifyUserMessage(String id) {
        try {
            notifyUserMessageRepository.delete(id);
        } catch (Exception e) {
            log.error("delete notify user message errer "+ id);
            e.printStackTrace();
        }

    }

    @Override
    public Message getMessage(String id) {

        return repository.findOne(id);
    }

    @Override
    public NotifyUserMessage getNotifyUserMessage(String id) {

        return notifyUserMessageRepository.findOne(id);
    }

    @Override
    public Iterable<Message> findAllMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Message> findMessageByType(MessageType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<NotifyUserMessage> findMessageByUserId(Belong belong, User notifyId, int cursor, int maxcount) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "notifyDate");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);


        return notifyUserMessageRepository.findAll(Example.of(notifyUserMessage), pageRequest);
    }

    @Override
    public Iterable<NotifyUserMessage> findMessageByUserIdAndBroadcast(
            Belong belong, User notifyId, boolean isBroadcast, int cursor,
            int maxcount) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        notifyUserMessage.setIsBroadcast(isBroadcast);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "notifyDate");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);


        return notifyUserMessageRepository.findAll(Example.of(notifyUserMessage), pageRequest);
    }

    @Override
    public Iterable<NotifyUserMessage> findMessageByUserIdAndStatus(
            Belong belong, User notifyId, MessageStatus status, int cursor,
            int maxcount) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        notifyUserMessage.setMessageStatus(status);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "notifyDate");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);


        return notifyUserMessageRepository.findAll(Example.of(notifyUserMessage), pageRequest);
    }

    @Override
    public Iterable<NotifyUserMessage> findMessageByUserIdAndTypeAndStatus(Belong belong, User notifyId, boolean isBroadcast,
                                                                           MessageStatus status, int cursor, int maxcount) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        notifyUserMessage.setMessageStatus(status);
        notifyUserMessage.setIsBroadcast(isBroadcast);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "notifyDate");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);


        return notifyUserMessageRepository.findAll(Example.of(notifyUserMessage), pageRequest);
    }

    @Override
    public long countMessageByUserIdAndStatus(Belong belong, User notifyId,
                                              MessageStatus status) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        notifyUserMessage.setMessageStatus(status);
        return notifyUserMessageRepository.count(Example.of(notifyUserMessage));
    }

    @Override
    public long getNotifyUserMessageCountByUserId(Belong belong, User notifyId) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage = toBelong(belong, notifyUserMessage);
        notifyUserMessage.setNotifyUid(notifyId.getActivityUid());
        notifyUserMessage.setNotifyAid(notifyId.getActivityAid());
        notifyUserMessage.setNotifyTid(notifyId.getActivityTid());
        return notifyUserMessageRepository.count(Example.of(notifyUserMessage));
    }

    @Override
    public long findMessageCountByType(Belong belong, MessageType type) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Page<Message> findMessageByType(Belong belong, MessageType type,
                                           int cursor, int maxcount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyAllUserMessage(Message message) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterable<Message> findMessageByNotifyMessages(
            Iterable<NotifyUserMessage> notifyUserMessages) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Page<NotifyUserMessage> findMessageByUserId(String userId,
                                                       int cursor, int maxcount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateMessage(Message message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteMessage(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Page<Message> findAllMessage(int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    private NotifyUserMessage toBelong(Belong belong,
                                       NotifyUserMessage notifyUserMessage) {
        notifyUserMessage.setAppId(belong.getAppId());

        notifyUserMessage.setTenantId(belong.getTenantId());

        notifyUserMessage.setSiteId(belong.getSiteId());

        return notifyUserMessage;
    }

    @Override
    public void deleteByTargetId(String id) {
        NotifyUserMessage notifyUserMessage = new NotifyUserMessage();
        notifyUserMessage.setTargerId(id);

        Iterable<NotifyUserMessage> findAll = notifyUserMessageRepository.findAll(Example.of(notifyUserMessage));
        Iterator<NotifyUserMessage> iterator = findAll.iterator();
        while (iterator.hasNext()) {
            NotifyUserMessage next = iterator.next();
            notifyUserMessageRepository.delete(next.getId());
        }

    }


}
