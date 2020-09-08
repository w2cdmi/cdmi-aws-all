package pw.cdmi.msm.order.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月24日 下午5:35:03
 */

@NoRepositoryBean
public interface OrderRepository extends PagingAndSortingRepository<Order, String>, QueryByExampleExecutor<Order> {
	@Override
	Order findOne(String id);

	Order findByOrderId(String orderId);

	@Override
	void delete(String id);
	
	@Override
	<S extends Order> S save(S entity);

	Iterable<Order> findByObjectId(String objectId);

	Iterable<Order> findByUserId(String userId);

	Iterable<Order> findByStatus(OrderStatus status);

	Iterable<Order> queryOrderOnCondition(String orderId, String userId, OrderStatus status);

	Page<Order> findFilteredOrder(String orderId, String userId, OrderStatus status, Pageable page);
}
