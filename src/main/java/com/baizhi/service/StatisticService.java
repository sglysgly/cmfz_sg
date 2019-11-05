package com.baizhi.service;

import com.baizhi.entity.Statistic;

import java.util.List;

public interface StatisticService {
    List<Statistic> queryBySex(String sex);
}
