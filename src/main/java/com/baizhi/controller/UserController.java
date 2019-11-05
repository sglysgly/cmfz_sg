package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;

import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.baizhi.entity.City;
import com.baizhi.entity.Echarts;
import com.baizhi.entity.Statistic;
import com.baizhi.entity.User;
import com.baizhi.service.CityService;
import com.baizhi.service.StatisticService;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private CityService cityService;
    @RequestMapping("showByStarId")
    @ResponseBody
    public HashMap<String,Object> showByStarId(Integer page,Integer rows,String id){
        HashMap<String, Object> users = userService.query(page, rows, id);
        return users;
    }

    @RequestMapping("show")
    @ResponseBody
    public HashMap<String,Object> show(Integer page,Integer rows){
        HashMap<String, Object> users = userService.queryAll(page, rows);
        return users;
    }

    @ResponseBody
    @RequestMapping("downOut")
    public void downOut(HttpServletResponse response) {
        List<User> users = userService.selectAll();
        users.forEach(user -> user.setPicImg("E:/idea_sg/cmfz_sg/src/main/webapp/upload/photo/"+user.getPicImg()));
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xlsx";
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),User.class,users);
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("statistic")
    @ResponseBody
    public HashMap<String,Object> querystatistic(){
        HashMap<String, Object> map = new HashMap<>();
        List<Statistic> boys = statisticService.queryBySex("男");
        List<Statistic> girls = statisticService.queryBySex("女");
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i = 0;i<12;i++){
            integers.add(0);
        }
        ArrayList<Integer> integers1 = new ArrayList<>();
        for(int i = 0;i<12;i++){
            integers1.add(0);
        }
        List<String> list = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        for (int i = 0; i < list.size(); i++) {
            for (int i1 = 0; i1 < boys.size(); i1++) {
                if(list.get(i).equals(boys.get(i1).getMonth())){
                    integers.remove(i);
                    integers.add(i,boys.get(i1).getValue());
                }
            }
            for (int i1 = 0; i1 < girls.size(); i1++) {
                if(list.get(i).equals(girls.get(i1).getMonth())){
                    integers1.remove(i);
                    integers1.add(i,girls.get(i1).getValue());
                }
            }

        }
        map.put("month",list);
        map.put("boys",integers);
        map.put("girls",integers1);
        return map;
    }

    @ResponseBody
    @RequestMapping("showcity")
    public ArrayList<Echarts> query(){
        List<City> cities = cityService.queryBySex("男");
        List<City> cities1 = cityService.queryBySex("女");
        Echarts echarts = new Echarts("男", cities);
        Echarts echarts1 = new Echarts("女", cities1);
        ArrayList<Echarts> echarts2 = new ArrayList<>();
        echarts2.add(echarts);
        echarts2.add(echarts1);
        return echarts2;
    }
}
