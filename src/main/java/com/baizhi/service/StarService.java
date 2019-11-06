package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.HashMap;
import java.util.List;

public interface StarService {
    HashMap<String, Object> queryByPage(Integer pager, Integer rows);
    String add(Star star);
    void change(Star star);
    void remove(String id);
    List<Star> queryAll();
}
