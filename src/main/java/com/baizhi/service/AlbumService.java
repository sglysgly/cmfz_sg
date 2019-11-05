package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.HashMap;

public interface AlbumService {
    HashMap<String,Object> queryByPage(Integer page,Integer rows);
    String add(Album album);
    void remove(String id);
    void change(Album album);
}
