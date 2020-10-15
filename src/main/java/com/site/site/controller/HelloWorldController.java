package com.site.site.controller;

import com.site.site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/10/1.
 */
@RestController
public class HelloWorldController {
    @Autowired //自动连接到UserService Bean
    private UserService userService;

    @RequestMapping("/hello")
    public String sd() {
        int low = 3;
        int height = 10;
        int mid = (low + height) /2;
        System.out.println(mid);
        System.out.println("111asdasd");
	    System.out.println("asdasd");
	    System.out.println(123);
        return userService.show();
//        return "Hello World";
    }

    @RequestMapping(value = "/showdao")
    public Object showDao(int age) {
        return userService.showDao(age);
    }

    @RequestMapping(value = "/showall")
    public Object showAll() {
        return userService.showAll();
    }

    @RequestMapping(value = "/insertdao")
    public Object insertUser(int age,String name){
        return userService.insert(name,age);
    }

    @RequestMapping(value = { "/" }, method = { RequestMethod.GET })
    public String index() {
        return "forward:/index.html";
    }

}
