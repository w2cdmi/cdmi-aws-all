
package pw.cdmi.msm.order.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;
import pw.cdmi.msm.order.repositories.OrderRepository;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月24日 下午6:33:39
 */
@MongoRepositoryBean
public interface MongoOrderRepository extends OrderRepository {
	@Override
	@Query("{'id':?0}")
	Order findOne(String id);

	@Override
	@Query("{'objectId':?0}")
	Iterable<Order> findByObjectId(String objectId);

	/*
	 * 删除指定Id对应的order数据
	 * 
	 */
	@Override
	void delete(String id);
	
	/* 
	 * 插入一条数据
	 */
	@Override
	<S extends Order> S save(S entity);

	@Override
	@Query(value = "{'userId':?0}")
	Iterable<Order> findByUserId(String userId);

	@Override
	@Query("{'status':?0}")
	Iterable<Order> findByStatus(OrderStatus status);

	@Override
	@Query("{'orderId':?0}")
	Order findByOrderId(String orderId);

	@Override
	@Query("{$or:[{orderId:{ $regex:'?0'}},{userId:{ $regex: '?1'}},{status:{ $regex: '?2'}}]}],Fields:null,Sort:null")
	Iterable<Order> queryOrderOnCondition(String orderId, String userId, OrderStatus status);

	@Override
	@Query(value = "{'orderId':{ $regex:'?0'},'userId':{ $regex:'?1'},'status':?2}")
	Page<Order> findFilteredOrder(String orderId, String userId, OrderStatus status, Pageable page);
}
