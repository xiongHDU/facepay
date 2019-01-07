package com.face.facepay.dao;

import com.face.facepay.pojo.Order;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface OrderDao {
    /**
     * 新增订单
     * @param order
     * @return
     */
    Boolean insertOrder(Order order);

    /**
     * 根据用户名查询订单列表
     * @param username
     * @return
     */
    List<Order> queryOrderByUsername(String username);
}
