package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao extends BaseDao<Chapter> {
    List<Chapter> selectByAlbum(@Param("start")Integer start,@Param("rows")Integer rows,@Param("id")String id);
}
