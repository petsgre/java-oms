package com.site.site.dao;

import com.site.site.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserDao {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    @Select("SELECT * FROM user")
    List<User> getAll();
    @Select("SELECT * FROM user WHERE age = #{age}")
    List<User> get(int age);

    @Select("INSERT INTO user(name, age) VALUES (#{name}, #{age})")
    void insert(User user);
}