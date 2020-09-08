package pw.cdmi.msm.order.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
