package com.baizhi.controller;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowProcessListStatement;
import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import com.baizhi.service.StarService;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private StarService starService;
    @RequestMapping("show")
    @ResponseBody
    public HashMap<String,Object> show(Integer page,Integer rows){
        HashMap<String, Object> albums = albumService.queryByPage(page, rows);
        return albums;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Album album, String oper){
        String uid = null;
        System.out.println(oper);
        if(StringUtils.equals(oper,"edit")){
            albumService.change(album);
        }
        if(StringUtils.equals(oper,"add")){
            uid = albumService.add(album);

        }
        if(StringUtils.equals(oper,"del")){
            albumService.remove(album.getId());
        }
       return uid;
    }

    @RequestMapping("upload")
    @ResponseBody
    public void uploadStar(String id, MultipartFile cover, HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name = cover.getOriginalFilename();
        try {
            cover.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        String uid = id.replace("\"","");
        album.setId(uid);
        album.setCover(name);
        albumService.change(album);
    }
    @ResponseBody
    @RequestMapping("showList")
    public void showList(HttpServletResponse response) throws IOException {
        final List<Star> stars = starService.queryAll();
        StringBuffer sb = new StringBuffer();
        sb.append("<select>");
        stars.forEach(star -> {
            sb.append("<option value=").append(star.getNickname()).append(">").append(star.getNickname()).append("</option>");
        });
        sb.append("</select>");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(sb.toString());
    }

}
