package com.baizhi;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPoi {
    @Test
    public void testpoi(){
        //创建文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作空间  参数：工作薄名字(sheet1,shet2....)
        HSSFSheet sheet = workbook.createSheet("学生信息表");
        //创建一行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置单元格内容
        cell.setCellValue("这是第一个的单元格");
        //输出
        try {
            workbook.write(new FileOutputStream(new File("G:\\上课专用表\\TestPoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExport(){

        //查询数据库返回集合
        Student stu1 = new Student("1","小可爱",16,new Date());
        Student stu2 = new Student("2","小蛋黄",16,new Date());
        Student stu3 = new Student("3","小狗蛋",12,new Date());
        Student stu4 = new Student("4","小嘿嘿",10,new Date());
        Student stu5 = new Student("5","小小小",23,new Date());

        List<Student> students = Arrays.asList(stu1, stu2, stu3, stu4, stu5);

        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
        Sheet sheet = workbook.createSheet("用户信息表1");

        //创建一个标题行  参数：行下标(下标从0开始)
        Row row0 = sheet.createRow(0);

        //设置内容
        row0.createCell(0).setCellValue("学生信息");

        //合并行   参数：起始行,结束行,起始单元格,结束单元格
        CellRangeAddress addresses = new CellRangeAddress(2,7,5,5);
        sheet.addMergedRegion(addresses);

        //设置列宽  参数：列索引，列宽(注意：单位为1/256)
        sheet.setColumnWidth(3,20*256);

        //设置字体样式
        Font font = workbook.createFont();
        font.setBold(true); //加粗
        font.setColor(Font.COLOR_RED);  //设置字体颜色
        font.setFontHeightInPoints((short) 24);  //设置字体大小
        font.setFontName("宋体");  //设置字体
        font.setItalic(true);  //设置斜体

        //创建样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);

        //创建一行  参数：行下标(下标从0开始)
        Row row = sheet.createRow(1);

        //设置行高   参数：行高(注意：单位为1/20,short类型)
        row.setHeight((short) 900);

        //给目录行设置数据
        String[] title={"ID","名字","年龄","生日"};
        for (int i = 0; i < title.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            //设置单元格内容
            cell.setCellValue(title[i]);
            //给字体设置样式
            cell.setCellStyle(cellStyle1);
        }

        //创建一个日期格式对象
        DataFormat dataFormat = workbook.createDataFormat();
        //创建一个样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期格式放入样式对象中
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));

        //处理数据行数据
        for (int i = 0; i < students.size(); i++) {
            //创建行
            Row row1 = sheet.createRow(i + 2);

            //创建单元格
            Cell cell = row1.createCell(0);
            //设置单元格内容
            cell.setCellValue(students.get(i).getId());
            //创建单元格并设置单元格内容
            row1.createCell(1).setCellValue(students.get(i).getName());
            row1.createCell(2).setCellValue(students.get(i).getAge());
            //处理日期数据
            Cell cell1 = row1.createCell(3);
            cell1.setCellValue(students.get(i).getBir());  //设置单元格内容
            cell1.setCellStyle(cellStyle);  //设置单元格日期样式
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("G:\\上课专用表\\TestPoi.xls")));

            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPoiImport() {

        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("G:\\上课专用表\\TestPoi.xls")));

            //获取工作薄
            HSSFSheet sheet = workbook.getSheet("用户信息表1");

            for (int i = 2; i < sheet.getLastRowNum(); i++) {

                Student student = new Student();

                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取Id
                student.setId(row.getCell(0).getStringCellValue());
                //获取name
                student.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                student.setAge((int) ages);
                //获取生日
                student.setBir(row.getCell(3).getDateCellValue());

                //调用一个插入数据库的方法
                System.out.println(student);
            }

            //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
