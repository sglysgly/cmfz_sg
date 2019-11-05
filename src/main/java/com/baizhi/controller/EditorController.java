package com.baizhi.controller;


import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("editor")
public class EditorController {
    /*
    * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"
 //成功时
{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
}
//失败时
{
        "error" : 1,
        "message" : "错误信息"
}
    *
    * */
    @RequestMapping("upload")
    public HashMap<String,Object> upload(MultipartFile photo, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = photo.getOriginalFilename();
        //给文件名加上时间
        String name=new Date().getTime()+"-"+filename;
        //获取http:
        String scheme = request.getScheme();
        //获取IP
        String serverName = request.getServerName();
        //获取端口号：
        int serverPort = request.getServerPort();
        //获取项目名：
        String contextPath = request.getContextPath();
        //网络路径的拼接：
        String serverPath=scheme+"://"+serverName+":"+serverPort+"/"+contextPath+"/upload/editor/"+name;
        try {
            photo.transferTo(new File(realPath,name));
            map.put("error",0);
            map.put("url",serverPath);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","上传失败");
        }
        return map;
    }
  /*
    *
{
	"moveup_dir_path": "",
	"current_dir_path": "",
	"current_url": "\/ke4\/php\/..\/attached\/",
	"total_count": 5,
	"file_list": [{
		"is_dir": false,
		"has_file": false,
		"filesize": 245132,
		"dir_path": "",
		"is_photo": true,
		"filetype": "jpg",
		"filename": "2009321101428.jpg",
		"datetime": "2018-06-06 00:36:39"
	}]
}
    *
    * */
  @RequestMapping("queryAllPhoto")
    public HashMap<String,Object> queryAllPhoto(HttpServletRequest request){
      HashMap<String, Object> map = new HashMap<>();
      ArrayList<Object> list = new ArrayList<>();
      //获取图片文件夹的路径
      String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");
      //获取文件夹
      File file = new File(realPath);
      //获取文件夹下所有的文件名称
      String[] names = file.list();
      for (int i = 0; i < names.length; i++) {
          //获取文件的名字
          String name = names[i];
          HashMap<String, Object> map1 = new HashMap<>();
         map1.put("is_dir",false);
         map1.put("has_file",false);
         File file1 = new File(realPath, name);
         map1.put("filesize",file.length());//图片的大小
          map1.put("is_photo",true); //是否是图片
          String extension = FilenameUtils.getExtension(name);
          map1.put("filetype",extension); //图片的格式
          map1.put("filename",name); //文件的名字
          //拆分
          String[] split = name.split("-");
          String times = split[0];
          long time = Long.parseLong(times);
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          String format = simpleDateFormat.format(time);
          map1.put("datetime",format); //上传的时间
          //封装进集合
          list.add(map1);
      }
      //网络路径
      map.put("current_url","http://localhost:8989/cmfz/upload/editor/");
      //文件数量
      map.put("total_count",list.size());
      //文件集合
      map.put("file_list",list);
      return map;
  }
}
















