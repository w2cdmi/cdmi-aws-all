package pw.cdmi.msm.order.rs.v1;

import net.sf.json.JSONObject;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月8日下午2:59:29
 */

public interface OrderResource {

	/*
	 * 通過rest接口插入一条order数据
	 * 
	 * @Parm ‘orderString’指的是传入的order对象对应的json字符串
	 * 
	 * @Return 返回插入操作结果
	 * 
	 * Public
	 */
	public JSONObject createOrder(String orderString);

	/*
	 * 通過rest接口update一条order数据
	 * 
	 * @Parm ‘orderString’指的是传入的order对象对应的json字符串
	 * 
	 * @Return 返回更新成功操作结果
	 * 
	 * Public
	 */
	public JSONObject updateOrder(String orderString);

	/*
	 * 通過rest接口delete一条order数据
	 * 
	 * @Parm ‘id’指的是传入的order对象对应的id
	 * 
	 * @Return 返回删除操作结果
	 * 
	 * Public
	 */

	public JSONObject deleteOrder(String id);

	/*
	 * 通過rest接口获取一条order数据
	 * 
	 * @Parm ‘id’指的是传入的order对象对应的id
	 * 
	 * @Return 返回数据
	 * 
	 * Public
	 */
	public JSONObject getOrderById(String id);

	/*
	 * 通過rest接口获取order数据
	 * 
	 * @Parm ‘orderId’指的是传入的order对象对应的订单编号
	 * @Parm ‘userId’指的是传入的order对象对应的用户Id
	 * @Parm ‘orderStatus’指的是传入的order对象对应的订单状态
	 * @Parm ‘pageSize’指的是传入的order对象对应的订单单页显示量
	 * @Parm ‘pageNo’指的是分页显示数据的对应当前页
	 * @Return 返回Order数据
	 * Public
	 */
	public JSONObject searchOrders(String orderId, String userId, String orderStatus, int pageNo, int pageSize);
	
	
	/*
	 * 通過指定user获取order数据
	 * 
	 * @Parm ‘userId’指的是传入的order对象对应的userId
	 * 
	 * @Return 返回Order数据
	 * 
	 * Public
	 */
	public JSONObject findOrderByUserId(String userId);
	
	
	public JSONObject findOrderDetailByOrderId(String orderId);
	
	
}
