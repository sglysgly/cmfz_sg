package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> selectByStarId(@Param("start") Integer start, @Param("rows") Integer rows,@Param("id") String id);
}
