package pw.cdmi.msm.order.service.impl;

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
import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.entity.Order;
import pw.cdmi.msm.order.repositories.OrderRepository;
import pw.cdmi.msm.order.service.OrderService;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月15日 下午6:33:39
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public void createOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        try {
            //Order oOrdEnty = null;// jpaImpl.get(order.getId(), Order.class);
            
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteOrder(String id) {
        try {
            Order order = getOrder(id);
            if (order != null) {
                orderRepository.delete(id);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Order getOrder(String id) {
        try {
            Order order =null;
            if(id!=null){
                order=orderRepository.findOne(id);
            }
            return order;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public Order getOrderByOrderId(String orderId) {
        Order order = null;
            try {
                if(orderId!=null){
                    order=orderRepository.findByOrderId(orderId);   
                }
            } catch (Exception e) {
                log.error(e.getStackTrace().toString());
                throw new AWSServiceException(SystemReason.SQLError);
        }
        return order;
    }

    @Transactional
    @Override
    public List<Order> findAllOrder() {
        List<Order> list = null;
        try {
            list = (List<Order>) orderRepository.findAll();
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Transactional
    @Override
    public List<Order> findOrderByUserId(String userId) {
        List<Order> list = null;
        if (userId != null) {
            try {
                 list= (List<Order>) orderRepository.findByUserId(userId);
            } catch (Exception e) {
                log.error(e.getStackTrace().toString());
                throw new AWSServiceException(SystemReason.SQLError);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public List<Order> findOrderByStatus(OrderStatus status) {
        List<Order> list = null;
        if (status != null) {
            try {
                list=(List<Order>) orderRepository.findByStatus(status);
            } catch (Exception e) {
                log.error(e.getStackTrace().toString());
                throw new AWSServiceException(SystemReason.SQLError);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void handleOrder(String id, OrderStatus status) {
        if (id != null && status != null) {
            try {
                //Order oTemOrder = null;// jpaImpl.get(id, Order.class);

            } catch (Exception e) {
                log.error(e.getStackTrace().toString());
                throw new AWSServiceException(SystemReason.SQLError);
            }
        }
    }

    @Override
    public Page<Order> findFilteredOrder(String orderId, String userId, OrderStatus status, int pageNo,
            int pageSize) {
        try {
            Pageable page = new PageRequest(pageNo, pageSize);
            return  orderRepository.findFilteredOrder(orderId, userId, status, page);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }
}
