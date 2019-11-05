package com.baizhi.service;

import com.baizhi.dao.CityDao;
import com.baizhi.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao cityDao;
    @Override
    public List<City> queryBySex(String sex) {
        return cityDao.selectBySex(sex);
    }
}
