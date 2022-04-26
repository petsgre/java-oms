package com.site.site.mapper;

import com.site.site.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Admin
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2022-04-27 01:03:49
* @Entity com.site.site.Order
*/
@Mapper
public interface OrderMapper {
    public List<Order> getList();

}
