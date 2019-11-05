package com.baizhi.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baizhi.util.PhoneMessageUtil;

public class Test {
    public static void main(String[] args) {
        String randomCode = PhoneMessageUtil.getRandomCode(6);
        String code = "{'code':"+randomCode+"}";
        try {
            SendSmsResponse msg = PhoneMessageUtil.getMsg("18839139921", "明星管理系统", "SMS_176911156", code);
            System.out.println(msg.getCode());
            System.out.println(randomCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
