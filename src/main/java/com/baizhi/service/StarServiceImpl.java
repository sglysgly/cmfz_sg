package com.baizhi.service;

import com.baizhi.annotation.AddRedisCache;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddRedisCache
    public HashMap<String, Object> queryByPage(Integer pager, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (pager - 1) * rows;
        //rows
        List<Star> stars = starDao.selectByPage(start, rows);
        map.put("rows",stars);
        //page
        map.put("page",pager);
        //total
        Integer along = starDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        //records
        map.put("records",along);
        return map;
    }

    @Override
    public String add(Star star) {
        String id = UUID.randomUUID().toString();
        star.setId(id);
        starDao.insert(star);
        return id;
    }

    @Override
    public void change(Star star) {
        if(star.getPhoto().equals("")){
            star.setPhoto(null);
        }
        if(star.getBir()==null){
            Star star1 = starDao.selectById(star.getId());
            star.setBir(star1.getBir());
        }
        starDao.update(star);
    }

    @Override
    public void remove(String id) {
        starDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Star> queryAll() {
        return starDao.selectAll();
    }


}
