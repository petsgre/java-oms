package com.site.site.service;

import com.site.site.dao.UserDao;
import com.site.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String show() {
        return "Hello World!???123";
    }

    public List<User> showDao(int age) {
        return userDao.get(age);
    }

    public List<User> showAll() {
        return userDao.getAll();
    }

    public String insert(String name, int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        userDao.insert(user);
        return "Insert ( \""+name+"\", age"+age+") OK!";
    }


}