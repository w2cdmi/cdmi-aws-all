/**
 * 
 */
package pw.cdmi.msm.order.rs.v1;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.utils.RequestParameterHandleUtils;
import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;
import pw.cdmi.msm.order.service.OrderService;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月22日 上午10:59:00
 */
@Path("/v1")
@Service("orderResource")

public class OrderResourceImpl implements OrderResource {

	@Autowired
	private OrderService orderService;
	@Resource
	private RequestParameterHandleUtils requestParmHandler;

	/*
	 * 通過rest接口插入一条order数据
	 * 
	 * @Parm ‘orderString’指的是传入的order对象对应的json字符串
	 * 
	 * @Return 返回插入成功或者失败提示
	 * 
	 * Public
	 */

	@Override
	@POST
	@Path("/order/create")
	@Produces("application/json;charset=utf-8")
	public JSONObject createOrder(String orderString) {
		Order order = new Order();
		order = requestParmHandler.convertRequestParams2Entity(order, orderString);
		if (order == null || "".equals(order.getObjectId())) {
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.NoFoundData);
		}
		JSONObject result = new JSONObject();
		try {
			orderService.createOrder(order);
			result.put("status", setResultStus("0", "Success"));
		} catch (Exception e) {
			result.put("status", setResultStus("-1", "Failure"));
		}
		return result;
	}

	/*
	 * 通過rest接口update一条order数据
	 * 
	 * @Parm ‘orderString’指的是传入的order对象对应的json字符串
	 * 
	 * @Return 返回更新成功或者失败提示
	 * 
	 * Public
	 */
	@Override
	@PUT
	@Path("/order/update")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public JSONObject updateOrder(String orderString) {
		Order order = requestParmHandler.convertRequestParams2Entity(new Order(), orderString);
		if (order == null || "".equals(order.getObjectId())) {
		    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.NoFoundData);
		}
		JSONObject result = new JSONObject();
		try {
			System.out.println(order);
			orderService.updateOrder(order);
			result.put("status", setResultStus("0", "Success"));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", setResultStus("-1", "Failure"));
		}
		return result;
	}

	/*
	 * 通過rest接口delete一条order数据
	 * 
	 * @Parm ‘id’指的是传入的order对象对应的id
	 * 
	 * @Return 返回删除成功或者失败提示
	 * 
	 * Public
	 */

	@Override
	@DELETE
	@Path("/order/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deleteOrder(@PathParam("id") String id) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isBlank(id)) {
		    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		JSONObject result = new JSONObject();
		try {
			orderService.deleteOrder(id);
			result.put("status", setResultStus("0", "Success"));
		} catch (Exception e) {
			result.put("status", setResultStus("-1", "Failure"));
		}
		return jsonObject;
	}

	/*
	 * 通過rest接口获取一条order数据
	 * 
	 * @Parm ‘id’指的是传入的order对象对应的id
	 * 
	 * @Return 返回数据
	 * 
	 * Public
	 */
	@GET
	@Path("/order/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getOrderById(@PathParam("id") String id) {
		JSONObject orderObj = new JSONObject(), jsonObject = new JSONObject();
		if (StringUtils.isBlank(id)) {
		    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		JSONObject result = new JSONObject();
		try {
			Order order = orderService.getOrder(id);
			orderObj.put("description", order.getDescription());
			orderObj.put("id", order.getId());
			orderObj.put("status", order.getStatus());
			orderObj.put("objectId", order.getObjectId());
			orderObj.put("orderId", order.getOrderId());
			orderObj.put("payDate", order.getPayDate());
			orderObj.put("submitDate", order.getSubmitDate());
			orderObj.put("totalPrice", order.getTotalPrice());
			orderObj.put("userId", order.getUserId());
			result.put("status", setResultStus("0", "Success"));
		} catch (Exception e) {
			result.put("status", setResultStus("-1", "Failure"));
		}
		return jsonObject;
	}

	/*
	 * 通過rest接口获取order数据
	 * 
	 * @Parm ‘orderId’指的是传入的order对象对应的订单号
	 * 
	 * @Parm ‘userId’指的是传入的order对象对应的用户Id
	 * 
	 * @Parm ‘orderStatus’指的是传入的order对象对应的订单状态
	 * 
	 * @Parm ‘pageNo’指的是传入的页面跳转的页码
	 * 
	 * @Parm ‘pageSize’指的是传入的页面的单页显示量
	 * 
	 * @Return 返回Order数据或者失败提示
	 * 
	 * Public
	 */
	@Override
	@GET
	@Path("/orders/search")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject searchOrders(@QueryParam("orderId") String orderId, @QueryParam("userId") String userId,
			@QueryParam("status") String orderStatus, @QueryParam("pageNo") int pageNo,
			@QueryParam("pageSize") int pageSize) {
		JSONObject resData = new JSONObject(), page = new JSONObject();
		JSONObject result = new JSONObject();
		try {
			JSONArray jsonAry = new JSONArray();
			List<Order> list = null;
			OrderStatus status = null;
			if (orderStatus != null && !"".equals(orderStatus)) {
				status = OrderStatus.fromEnumName(orderStatus);
			}
			// Get data for filter conditions
			Page<Order> pageObj = orderService.findFilteredOrder(orderId, userId, status, pageNo, pageSize);
			list = pageObj.getContent();
			if (list != null) {
				for (Order ord : list) {
					jsonAry.add(convertFromOrder(ord));
				}
				Long count = pageObj.getTotalElements();
				int totalPages = pageObj.getTotalPages();
				page.put("count", count);
				page.put("totalPages", totalPages);
				resData.put("orders", jsonAry);
				resData.put("oPage", page);
				result.put("result", resData);
				result.put("status", setResultStus("0", "Success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", setResultStus("-1", "failure"));
		}
		return result;
	}

	@Override
	@GET
	@Path("/orders/byUser")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject findOrderByUserId(@QueryParam("userId") String userId) {
		if (StringUtils.isBlank(userId)) {
		    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		JSONObject resData = new JSONObject();
		JSONObject result = new JSONObject();
		JSONArray jsonAry = new JSONArray();

		try {
			List<Order> list = orderService.findOrderByUserId(userId);
			if (list != null) {
				for (Order ord : list) {
					jsonAry.add(convertFromOrder(ord));
				}
				resData.put("orders", jsonAry);
				result.put("result", resData);
				result.put("status", setResultStus("0", "Success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", setResultStus("-1", "failure"));
		}
		return result;
	}
	
	@Path("/order/{orderId}/detail")
	@Override
	public JSONObject findOrderDetailByOrderId(String orderId) {
		if (StringUtils.isBlank(orderId)) {
		    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		
		JSONObject resData = new JSONObject();
		JSONObject result = new JSONObject();
		JSONArray jsonAry = new JSONArray();
		try {
			Order order = null;//orderService;
			/*if (list != null) {
				for (Order ord : list) {
					jsonAry.add(convertFromOrder(ord));
				}
				resData.put("orders", jsonAry);
				result.put("result", resData);
				result.put("status", setResultStus("0", "Success"));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", setResultStus("-1", "failure"));
		}
		return null;
	}

	/*
	 *Help to convert order into JSONObject 
	 */	
	private JSONObject convertFromOrder(Order order) {
		JSONObject jsonObject = null;
		if (order != null) {
			jsonObject = new JSONObject();
			String stusVal = order.getStatus() != null ? order.getStatus().getText() : "";
			jsonObject.put("id", order.getId());
			jsonObject.put("objectId", order.getObjectId());
			jsonObject.put("orderId", order.getOrderId());
			jsonObject.put("status", stusVal);
			jsonObject.put("payDate", order.getPayDate());
			jsonObject.put("totalPrice", order.getTotalPrice());
			jsonObject.put("submitDate", order.getSubmitDate());
			jsonObject.put("userId", order.getUserId());
			jsonObject.put("description", order.getDescription());
		}
		return jsonObject;
	}

	private JSONObject setResultStus(String code, String msg) {
		JSONObject status = new JSONObject();
		status.put("code", code);
		status.put("message", msg);
		return status;
	}

	
}
