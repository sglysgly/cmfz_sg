package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("show")
    @ResponseBody
    public HashMap<String,Object> show(Integer page,Integer rows,String id,HttpServletRequest request){
        request.getSession().setAttribute("id",id);
        HashMap<String, Object> chapters = chapterService.query(page, rows, id);
        return chapters;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper,HttpServletRequest request){
        String uid = null;
        String id= (String) request.getSession().getAttribute("id");
        if(StringUtils.equals(oper,"edit")){
            chapterService.change(chapter);
        }
        if(StringUtils.equals(oper,"add")){
            uid = chapterService.add(chapter, id);

        }
        if(StringUtils.equals(oper,"del")){
            chapterService.remove(chapter.getId(),id);
        }
        return uid;
    }
    @ResponseBody
    @RequestMapping("upload")
    public void upload(MultipartFile url, String id, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name = url.getOriginalFilename();
        url.transferTo(new File(realPath,name));
        //获取文件的大小
        long size = url.getSize();
        DecimalFormat format = new DecimalFormat("0.00");
        String s = String.valueOf(size);
        Double d = Double.valueOf(s)/1024/1024;
        String newsizes = format.format(d)+"MB";
        //获取文件的时长
        AudioFileIO audioFileIO = new AudioFileIO();
        AudioFile audioFile = audioFileIO.readFile(new File(realPath, name));
        AudioHeader audioHeader = audioFile.getAudioHeader();
        int trackLength = audioHeader.getTrackLength();
        String duration = trackLength/60+"分"+ trackLength%60+"秒";
        Chapter chapter = new Chapter();
        String newid = id.replace("\"","");
        chapter.setId(newid).setSizes(newsizes).setDuration(duration).setUrl(name);
        chapterService.change(chapter);
    }
    @RequestMapping("download")
    @ResponseBody
    public void downloadChapter(String fileName,HttpServletRequest request,HttpServletResponse response){
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");
        try {
            FileInputStream is = new FileInputStream(new File(realPath, fileName));
            //设置下载打开方式(inline) 为附件下载(attachment) 默认在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
            String extension = FilenameUtils.getExtension(fileName);
            String mimeType = request.getServletContext().getMimeType("." + extension);
            //设置下载响应类型
            response.setContentType(mimeType);
            //获取响应输出流
            ServletOutputStream os = response.getOutputStream();
            //文件拷贝  file 2049 byte
            IOUtils.copy(is,os);
            //关流
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}














