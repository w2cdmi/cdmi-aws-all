package pw.cdmi.msm.message.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.message.model.MessageStatus;
import pw.cdmi.msm.message.model.MessageType;
import pw.cdmi.msm.message.model.entities.Message;
import pw.cdmi.msm.message.model.entities.NotifyUserMessage;
import pw.cdmi.msm.message.repositories.MessageRepository;
import pw.cdmi.msm.message.repositories.NotifyUserMessageRepository;
import pw.cdmi.msm.message.service.MessageService;
import pw.cdmi.paas.account.model.entities.UserAccount;

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
	private MessageRepository repository;

	@Autowired
	NotifyUserMessageRepository notifyUserMessageRepository;

	@Transactional
	@Override
	public void createMessage(Message message) {
		try {
			repository.save(message);
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
			notifyUserMessage.setStatus(MessageStatus.UNREAD);
			notifyUserMessageRepository.save(notifyUserMessage);
			notifMessageList.add(notifyUserMessage);
		}

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
				userMessage.setStatus(MessageStatus.UNREAD);
				notifyUserMessageRepository.save(userMessage);
			} else {
				// 创建消息的同时时，创建通知用户信息
				message.setCreateDate(new Date());
				repository.save(message);
				if (message.getId() != null) {
					userMessage.setMessageId(message.getId());
					userMessage.setUserId(userId);
					userMessage.setNotifyDate(new Date());
					// 默认消息状态为未读
					userMessage.setStatus(MessageStatus.UNREAD);
					notifyUserMessageRepository.save(userMessage);
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
			repository.save(message);
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
				userMessage.setStatus(status);
				notifyUserMessageRepository.save(userMessage);
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
				repository.delete(message);
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
				notifyUserMessageRepository.delete(userMessage);
			}
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Message getMessage(String id) {
		try {
			return repository.findOne(id);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public NotifyUserMessage getNotifyUserMessage(String id) {
		try {
			return notifyUserMessageRepository.findOne(id);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Iterable<Message> findAllMessage() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Page<Message> findAllMessage(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo, pageSize);
		return repository.findAll(pageable);
	}

	@Override
	public long findMessageCountByType(MessageType type) {
		return repository.countByType(type.name());
	}

	@Override
	public Page<Message> findMessageByType(MessageType type, int pageNo, int pageSize) {
		try {
			Pageable pageable = new PageRequest(pageNo, pageSize);
			return repository.findByType(type.name(), pageable);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Iterable<Message> findMessageByType(MessageType type) {
		try {
			return repository.findByType(type.name());
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Iterable<NotifyUserMessage> findMessageByUserId(String userId) {
		try {
			return notifyUserMessageRepository.findByUserId(userId);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Page<NotifyUserMessage> findMessageByUserId(String userId, int pageNo, int pageSize) {
		try {
			Pageable pageable = new PageRequest(pageNo, pageSize);
			return notifyUserMessageRepository.findByUserId(userId, pageable);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public long getNotifyUserMessageCountByUserId(String userId) {
		return notifyUserMessageRepository.countByUserId(userId);
	}

	@Override
	public Iterable<Message> findMessageByNotifyMessages(Iterable<NotifyUserMessage> notifyUserMessages) {

		if (notifyUserMessages == null || !notifyUserMessages.iterator().hasNext()) {
			return null;
		}

		Collection<String> list = new ArrayList<String>();

		for (NotifyUserMessage notifyUserMessage : notifyUserMessages) {
			list.add(notifyUserMessage.getMessageId());
		}
		return repository.findByIdIn(list);
	}

	@Override
	public Iterable<NotifyUserMessage> findMessageByUserIdAndType(String userId, MessageType type) {
		try {
			// 根据消息类型获取消息id
			Iterable<Message> messIdList = repository.findByType(type.name());

			// 根据用户id与消息id在messIds内，查找用户消息列表
			Collection<String> list = new ArrayList<String>();
			for (Message message : messIdList) {
				list.add(message.getId());
			}
			return notifyUserMessageRepository.findByUserIdAndMessageIdIn(userId, list);
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Iterable<NotifyUserMessage> findMessageByUserIdAndStatus(String userId, MessageStatus status) {
		try {
			return notifyUserMessageRepository.findByUserIdAndStatus(userId, status.name());
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Page<NotifyUserMessage> findMessageByUserIdAndTypeAndStatus(String userId, MessageType type,
			MessageStatus status, int pageNo, int pageSize) {

		try {
			Pageable pageable = new PageRequest(pageNo, pageSize);

			if (type != null) {
				// 根据消息类型获取消息id
				Iterable<Message> messages = repository.findByType(type.name());
				Collection<String> list = new ArrayList<String>();
				for (Message message : messages) {
					list.add(message.getId());
				}
				// 根据用户id与消息id在messIds内，查找用户消息列表
				return notifyUserMessageRepository.findByUserIdAndStatusAndMessageIdIn(userId, status.name(), list,
						pageable);
			} else {
				if (status != null) {
					return notifyUserMessageRepository.findByUserIdAndStatus(userId, status.name(), pageable);
				} else {
					return notifyUserMessageRepository.findByUserId(userId, pageable);
				}
			}

		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

}
