package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("getInputCode")
    public void getInputCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
      HttpSession session = request.getSession();
      response.setContentType("image/png");
      String code = SecurityCodeUtil.generateVerifyCode(4);
      session.setAttribute("code", code);
      BufferedImage image = SecurityCodeUtil.getImage(80, 30, code);
      ImageIO.write(image, "png", response.getOutputStream());
    }

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(HttpServletRequest request, Admin admin, String inputCode){
        HashMap<String,Object> result = new HashMap();
        try{
            adminService.login(admin,request,inputCode);
            result.put("success",true);
        }catch(Exception e){
            String message  =e.getMessage();
            result.put("success",false);
            result.put("message",message);
        }
        return result;
    }

    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}









