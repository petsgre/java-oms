package com.site.site.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/10/1.
 */
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String sd() {
        return "Hello World";
    }
    @RequestMapping(value = { "/" }, method = { RequestMethod.GET })
    public String ndex() {
        return "forward:/index.html";
    }

}