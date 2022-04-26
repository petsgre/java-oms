package com.site.site.service.impl;

import com.site.site.domain.Order;
import com.site.site.service.OrderService;
import com.site.site.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Admin
 * @description 针对表【t_order】的数据库操作Service实现
 * @createDate 2022-04-27 01:03:49
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    public OrderMapper orderMapper;

    @Override
    public List<Order> getList() {
        List list = orderMapper.getList();
        return list;
    }
}
