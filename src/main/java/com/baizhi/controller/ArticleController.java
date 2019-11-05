package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("show")
    @ResponseBody
    public HashMap<String,Object> show(Integer page,Integer rows){

        return articleService.queryByPage(page,rows);
    }

    @RequestMapping("add")
    @ResponseBody
    public void add(Article article){

        articleService.add(article);

        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-c63fdb9a3d284514a6a520eef2604475");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("myChannel", "有新文章发布:（"+ article.getTitle()+"）请注意查看！");
    }

    @ResponseBody
    @RequestMapping("change")
    public void change(Article article,String id){
        article.setId(id);
        articleService.change(article);
    }
    @RequestMapping("edit")
    @ResponseBody
    public void edit(Article article,String oper){
        if(oper.equals("del")){
            articleService.remove(article.getId());
        }
    }
}
