package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.HashMap;

public interface ChapterService {
    HashMap<String,Object> query(Integer page,Integer rows,String id);
    String add(Chapter chapter,String id);
    void remove(String id,String aid);
    void change(Chapter chapter);
}
