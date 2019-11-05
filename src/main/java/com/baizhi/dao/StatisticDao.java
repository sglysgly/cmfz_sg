package com.baizhi.dao;

import com.baizhi.entity.Statistic;

import java.util.List;

public interface StatisticDao{
    List<Statistic> selectBySex(String sex);
}
