package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> {
    void insert(T t);
    void update(T t);
    void delete(String id);
    T selectById(String id);
    List<T> selectAll();
    List<T> selectByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    Integer selectTotalCounts();
}
