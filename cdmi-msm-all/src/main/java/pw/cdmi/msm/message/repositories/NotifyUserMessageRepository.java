package pw.cdmi.msm.message.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.message.model.entities.NotifyUserMessage;

@NoRepositoryBean
public interface NotifyUserMessageRepository
		extends PagingAndSortingRepository<NotifyUserMessage, String>, QueryByExampleExecutor<NotifyUserMessage> {

	public Iterable<NotifyUserMessage> findByUserId(String userId);

	public Page<NotifyUserMessage> findByUserId(String userId, Pageable pageable);

	public Page<NotifyUserMessage> findByUserIdAndStatusAndMessageIdIn(String userId, String status,
			Collection<String> message_ids, Pageable pageable);

	public Page<NotifyUserMessage> findByUserIdAndStatus(String userId, String status, Pageable pageable);

	public long countByUserId(String userId);

	public Iterable<NotifyUserMessage> findByUserIdAndMessageIdIn(String userId, Collection<String> message_ids);

	public Iterable<NotifyUserMessage> findByUserIdAndStatus(String userId, String status);
}
