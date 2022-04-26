package com.site.site.service;

import com.site.site.domain.Order;

import java.util.List;

/**
 * @author Admin
 * @description 针对表【t_order】的数据库操作Service
 * @createDate 2022-04-27 01:03:49
 */
public interface OrderService {
    public List<Order> getList();
}
