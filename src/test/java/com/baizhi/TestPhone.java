package com.baizhi;

import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlOutFileExpr;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baizhi.util.PhoneMessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPhone {
    @Test
    public void test(){
        String randomCode = PhoneMessageUtil.getRandomCode(6);
        String code = "您的验证码为:"+randomCode+"  如非本人，请勿操作";
        try {
            SendSmsResponse msg = PhoneMessageUtil.getMsg("18552958981", "明星管理系统", "SMS_176911156", code);
            System.out.println(msg);
            System.out.println(randomCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
