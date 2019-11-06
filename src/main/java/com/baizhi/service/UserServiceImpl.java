package com.baizhi.service;

import com.baizhi.annotation.AddRedisCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddRedisCache
    public HashMap<String, Object> query(Integer page, Integer rows, String id) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        //rows
        List<User> users = userDao.selectByStarId(start,rows,id);
        map.put("rows",users);
        //page
        map.put("page",page);
        //total
        Integer along = userDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        map.put("records",along);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> queryAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        //rows
        List<User> users = userDao.selectByPage(start,rows);
        map.put("rows",users);
        //page
        map.put("page",page);
        //total
        Integer along = userDao.selectTotalCounts();
        Integer total  = along % rows == 0 ? along/rows:(along/rows)+1;
        map.put("total",total);
        map.put("records",along);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public void add(User user) {
        userDao.insert(user);
    }
}
