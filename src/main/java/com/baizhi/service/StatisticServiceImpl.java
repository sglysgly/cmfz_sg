package com.baizhi.service;

import com.baizhi.dao.StatisticDao;
import com.baizhi.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticDao statisticDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<com.baizhi.entity.Statistic> queryBySex(String sex) {
        List<Statistic> statistics = statisticDao.selectBySex(sex);
        return statistics;
    }
}
