package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.HashMap;

public interface BannerService {
    HashMap<String,Object> queryByPage(Integer page,Integer rows);
    String addOne(Banner banner);
    void removeOne(String id);
    void changeOne(Banner banner);
}
