package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
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
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (page-1)*rows;
        List<Article> articles = articleDao.selectByPage(start, rows);
        map.put("rows",articles);
        map.put("page",page);
        Integer along = articleDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        //records
        map.put("records",along);
        return map;
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString()).setCreateDate(new Date());
        articleDao.insert(article);
    }

    @Override
    public void remove(String id) {
        articleDao.delete(id);
    }

    @Override
    public void change(Article article) {
        articleDao.update(article);
    }
}
