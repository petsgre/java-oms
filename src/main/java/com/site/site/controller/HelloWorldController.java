package com.site.site.controller;

import com.site.site.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/1.
 */
@RestController
@RequestMapping(value = {"/"}, produces = "application/json;charset=UTF-8")
public class HelloWorldController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        HashMap<String, Object> map = new HashMap<>();
        List list = orderService.getList();
        map.put("msg", "success!!!!");
        map.put("list", list);
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
