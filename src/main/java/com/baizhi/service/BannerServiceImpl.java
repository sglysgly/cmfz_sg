package com.baizhi.service;

import com.baizhi.annotation.AddRedisCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddRedisCache
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        //rows
        List<Banner> banners = bannerDao.selectByPage(start, rows);
        map.put("rows",banners);
        //page
        map.put("page",page);
        //total
        Integer along = bannerDao.selectRecords();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        //records
        map.put("records",along);
        return map;
    }

    @Override
    public String addOne(Banner banner) {
        banner.setId(UUID.randomUUID().toString())
                .setCreateDate(new Date());
        bannerDao.insertOne(banner);
        return banner.getId();
    }

    @Override
    public void removeOne(String id) {
        bannerDao.deleteOne(id);
    }

    @Override
    public void changeOne(Banner banner) {
        if(banner.getImgPath().equals("")){
            banner.setImgPath(null);
        }
        bannerDao.updateOne(banner);
    }

}
