package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import lombok.experimental.Accessors;
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
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private StarDao starDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        //rows
        List<Album> albums = albumDao.selectByPage(start,rows);
        map.put("rows",albums);
        //page
        map.put("page",page);
        //total
        Integer along = albumDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        //records
        map.put("records",along);
        return map;
    }

    @Override
    public String add(Album album) {
        String id  = UUID.randomUUID().toString();
        album.setId(id).setCreateDate(new Date());
        albumDao.insert(album);
        return id;
    }

    @Override
    public void remove(String id) {
        albumDao.delete(id);
    }

    @Override
    public void change(Album album) {
        if(album.getCover().equals("")){
            album.setCover(null);
        }
        albumDao.update(album);
    }
}
