package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> query(Integer page, Integer rows, String id) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start  = (page-1)*rows;
        List<Chapter> chapters = chapterDao.selectByAlbum(start,rows,id);
        map.put("rows",chapters);
        map.put("page",page);
        Integer along = chapterDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("records",along);
        map.put("total",total);
        return map;
    }

    @Override
    public String add(Chapter chapter,String id) {
        String string = UUID.randomUUID().toString();
        chapter.setId(string).setCreateDate(new Date()).setAlbumId(id);
        chapterDao.insert(chapter);
        Album album = albumDao.selectById(id);
        album.setCounts(album.getCounts()+1);
        albumDao.update(album);
        return string;
    }

    @Override
    public void remove(String id,String aid) {
        chapterDao.delete(id);
        Album album = albumDao.selectById(aid);
        album.setCounts(album.getCounts()-1);
        albumDao.update(album);
    }

    @Override
    public void change(Chapter chapter) {
        if(chapter.getUrl().equals("")){
            chapter.setUrl(null);
        }
        chapterDao.update(chapter);
    }
}
