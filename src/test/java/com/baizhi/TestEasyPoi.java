package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEasyPoi {
    @Autowired
    private UserService userService;
    @Test
    public void TestEasyPoi() throws IOException {
        Student stu1 = new Student("1","小可爱",16,new Date());
        Student stu2 = new Student("2","小蛋黄",16,new Date());
        Student stu3 = new Student("3","小狗蛋",12,new Date());
        Student stu4 = new Student("4", "小哈哈哈", 10, new Date());
        Student stu5 = new Student("5","小小小",23,new Date());

        List<Student> students = Arrays.asList(stu1, stu2, stu3, stu4, stu5);
        Teacher teacher1 = new Teacher("1", "制定方案", students);
        Teacher teacher2 = new Teacher("1", "制定结果", students);
        //创建老师集合
        List<Teacher> teachers = new ArrayList<Teacher>();
        teachers.add(teacher1);
        teachers.add(teacher2);
        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("计算机学院", "信管", "学生"), Teacher.class, teachers);
        sheets.write(new FileOutputStream(new File("G:\\上课专用表\\TestPoi.xls")));
        sheets.close();
    }
    @Test
    public void  TestEasyPoiInput() throws Exception {
            //创建导入对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2); //表格标题行数,默认0（占用）
            params.setHeadRows(2);  //表头行数,默认1（占用）

            //获取导入数据
            List<Teacher> teachers = ExcelImportUtil.importExcel(new FileInputStream(new File("G:\\上课专用表\\TestPoi.xls")),Teacher.class, params);
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }
    }

    @Test
    public void test1(){
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题行数,默认0（占用）
        params.setHeadRows(1);  //表头行数,默认1（占用）
        try {
            List<User> users = ExcelImportUtil.importExcel(new FileInputStream(new File("G:/上课专用表/users.xlsx")),User.class,params);
            users.forEach(user -> System.out.println(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
