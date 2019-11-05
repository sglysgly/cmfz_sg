package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;
    @ResponseBody
    @RequestMapping("show")
    public HashMap<String,Object> show(Integer page,Integer rows){
        HashMap<String, Object> stars = starService.queyByPage(page, rows);
        return stars;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Star star,String oper){
        String id = null;
        if(oper.equals("add")){
            id = starService.add(star);
        }
        if(oper.equals("del")){
            starService.remove(star.getId());
        }
        if(oper.equals("edit")){
            starService.change(star);
        }
        return id;
    }

    @RequestMapping("upload")
    @ResponseBody
    public void uploadStar(String id, MultipartFile photo, HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name = photo.getOriginalFilename();
        try {
            photo.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Star star = new Star();
        String uid = id.replace("\"","");
        star.setId(uid).setPhoto(name);
        System.out.println(star);
        starService.change(star);
    }
}
