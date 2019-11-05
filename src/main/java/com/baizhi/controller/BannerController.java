package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.sun.jmx.remote.security.FileLoginModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("queyAll")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = bannerService.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Banner banner,String oper,HttpServletRequest request){
        String uid = null;
        if(StringUtils.equals(oper,"edit")){
            bannerService.changeOne(banner);
        }
        if(StringUtils.equals(oper,"add")){
             uid = bannerService.addOne(banner);

        }
        if(StringUtils.equals(oper,"del")){
            bannerService.removeOne(banner.getId());
        }
        return uid;

    }

    @RequestMapping("uploadBanner")
    @ResponseBody
    public void uploadBanner(MultipartFile imgPath, HttpServletRequest request,String id){
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name = imgPath.getOriginalFilename();
        try {
            imgPath.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        String a = id.replace("\"","");
        banner.setId(a);
        banner.setImgPath(name);
        bannerService.changeOne(banner);
    }
}
