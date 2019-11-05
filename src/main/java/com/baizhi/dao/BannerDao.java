package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    List<Banner> selectByPage(@Param("start")Integer start,@Param("rows")Integer rows);
    void insertOne(Banner banner);
    void updateOne(Banner banner);
    void deleteOne(String id);
    Integer selectRecords();
    Banner selectOne(String id);
}
