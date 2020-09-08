package pw.cdmi.msm.message.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.message.model.entities.Message;

@NoRepositoryBean
public interface MessageRepository extends PagingAndSortingRepository<Message, String>,QueryByExampleExecutor<Message>{

	public Page<Message> findByType(String type, Pageable pageable);
	
	public Iterable<Message> findByType(String type);
	
	/**
	 * 获取指定类型的消息的总数
	 * @return
	 */
	public long countByType(String type);
	
	public Iterable<Message> findByIdIn(Collection<String> ids);
}
