package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐值")
    private String salt;
    @Excel(name = "头像",type = 2 ,width = 40 , height = 20,imageType = 1)
    private String picImg;
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "签名")
    private String sign;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间",format = "yyyy年MM月dd日")
    private Date createDate;
    @ExcelIgnore
    private String starId;
}
