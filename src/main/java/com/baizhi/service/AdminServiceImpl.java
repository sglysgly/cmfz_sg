package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void login(Admin admin, HttpServletRequest request, String inputCode) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        Admin admin1 = adminDao.selectOneByUsername(admin.getUsername());
        if(code.equalsIgnoreCase(inputCode)){
            if(admin1 != null){
                if(admin.getPassword().equals(admin1.getPassword())){
                    session.setAttribute("admin",admin1);
                }else {
                    throw new RuntimeException("密码错误！");
                }
            }else{
                throw new RuntimeException("用户名不存在！");
            }
        }else {
            throw new RuntimeException("验证码错误！");
        }
    }
}
