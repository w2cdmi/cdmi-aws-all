package pw.cdmi.msm.order.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;

/**
 * ********************************************************** <br/>
 * 接口类，提供对订单信息操作的方法
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
public interface OrderService {

	/**
	 * 
	 * 向系统中添加一条订单信息
	 * 
	 * @param order
	 *            待添加的订单信息
	 */
	public void createOrder(Order order);

	/**
	 * 
	 * 更新订单信息.
	 *
	 * @param order
	 *            待更新的订单信息
	 */
	public void updateOrder(Order order);

	/**
	 * 
	 * 删除订单信息.
	 *
	 * @param orderId
	 *            待删除的订单信息编号
	 */
	public void deleteOrder(String id);

	/**
	 * 
	 * 获得指定的订单信息.
	 *
	 * @param id
	 *            指定的订单信息编号
	 * @return 订单信息
	 */
	public Order getOrder(String id);

	/**
	 * 
	 * 根据订单编码获得指定的订单信息.
	 *
	 * @param orderId
	 *            指定的订单编码号·
	 * @return 订单信息
	 */
	public Order getOrderByOrderId(String orderId);

	/**
	 * 
	 * 获取所有订单信息.
	 *
	 * @return 订单信息列表
	 */
	public List<Order> findAllOrder();

	/**
	 * 
	 * 根据用户id查询订单信息.
	 *
	 * @param userId
	 *            用户id
	 * @return 订单信息列表
	 */
	public List<Order> findOrderByUserId(String userId);

	/**
	 * 
	 * 根据订单状态查询订单信息.
	 *
	 * @param status
	 *            订单状态
	 * @return 订单信息列表
	 */
	public List<Order> findOrderByStatus(OrderStatus status);

	/**
	 * 
	 * 处理订单信息
	 *
	 * @param id
	 *            订单信息Id
	 * @param status
	 *            需要更新的状态
	 */
	public void handleOrder(String id, OrderStatus status);
	public  Page<Order> findFilteredOrder(String orderId,String userId,OrderStatus status,int pageNo, int pageSize);
}
