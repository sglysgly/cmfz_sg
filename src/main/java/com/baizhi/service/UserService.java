package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    HashMap<String,Object> query(Integer page,Integer rows,String id);
    HashMap<String,Object> queryAll(Integer page,Integer rows);
    List<User> selectAll();
    void add(User user);
}
