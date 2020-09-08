package pw.cdmi.msm.order.repositories.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;
import pw.cdmi.msm.order.repositories.OrderRepository;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月24日 下午6:33:28
 */
public interface JpaOrderRepository extends OrderRepository {

	/*@Override
	@Query(value = "select o from Order o where o.objectId = :objectId")
	Iterable<Order> findByObjectId(@Param(value = "objectId") String objectId);

	@Override
	@Query(value = "select o from Order o where o.userId = :userId")
	Iterable<Order> findOrderByUserId(@Param(value = "userId") String userId);

	*/
	@Override
	@Query(value = "select o from Order o where (o.objectId = :orderId or o.userId = :userId) and  o.status =:status")
	Iterable<Order> queryOrderOnCondition(@Param(value = "orderId") String orderId, @Param(value = "userId") String userId, @Param(value = "status") OrderStatus status);

	@Override
	@Query(value = "select o from Order o where (o.objectId = :orderId or o.userId = :userId) and  o.status =:status")
	Page<Order> findFilteredOrder(@Param(value = "orderId") String orderId, @Param(value = "userId") String userId, @Param(value = "status") OrderStatus status, Pageable page);
}
