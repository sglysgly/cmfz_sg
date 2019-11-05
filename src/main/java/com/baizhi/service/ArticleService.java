package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.HashMap;

public interface ArticleService {
    HashMap<String,Object> queryByPage(Integer page,Integer rows);
    void add(Article article);
    void remove(String id);
    void change(Article article);
 }
