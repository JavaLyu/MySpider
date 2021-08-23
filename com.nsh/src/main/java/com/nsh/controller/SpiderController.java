package com.nsh.controller;

import com.nsh.pojo.Student;
import com.nsh.service.MailService;
import com.nsh.service.MassageService;
import com.nsh.service.StudentService;
import com.nsh.util.ExcelUtil;
import com.nsh.webmagic.SXSPipeline;
import com.nsh.webmagic.SXSProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class SpiderController {
    
    @Autowired
    private SXSProcessor sxsProcessor;

    @Autowired
    private SXSPipeline sxsPipeline;

    @Autowired
    MailService mailService;

    @Autowired
    MassageService massageService;

    @Autowired
    StudentService studentService;

    /**
     * 通过URL触发爬虫爬取数据
     */

    @RequestMapping("/spider")
    @ResponseBody
    public  String spider(){

        System.out.println("爬虫开始爬取数据...");
        massageService.scheduleSpider();
        return "success";
    }

    @RequestMapping("sendmail")
    @ResponseBody
    public String mail(){
       massageService.notifyByMail();
        return "success";
    }

    //大文件上传问题 上传excel 使用的是post
    //HttpServletRequest前端传给后端的数据会封装为一个对象保存在HttpServletRequest中
    @PostMapping("/uploadExcel")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request){
        //MultipartReques类主要是对文件上传进行的处理，在上传文件时，
        // 编码格式为enctype="multipart/form-data"格式，以二进制形式提交数据，提交方式为post方式。
        MultipartRequest request1 = (MultipartRequest) request;
        //获取前端传入的参数进行映射
        MultipartFile f = request1.getFile("excelFile");//给定前端页面
        if (f.isEmpty()){
            System.out.println("传输数据存在问题");
            return "fail";
        }
        try {
            //从前端流的形式读取前端的文件
            InputStream inputStream = f.getInputStream();
            //excel工具类解析
            //getOriginalFilename()拿到原始的名称
            List<List<Object>> list = ExcelUtil.getCourseListByExcel(inputStream, f.getOriginalFilename());
            inputStream.close();
            //对第一行的下标信息进行删除
            if (list.size() == 1){
                System.out.println("没有数据");
                return "success";
            }
            list.remove(0);


            //获取数据并储存
            Iterator<List<Object>> iterator = list.iterator();
            //保存拿到的对象
            List<Student> students = new ArrayList<>();
            while (iterator.hasNext()){
                List<Object> next = iterator.next();
                Student student = new Student();
                String name = next.get(1).toString();
                student.setName(name);
                //poi中去出的数据默认是double即1.0所以需要转为int
                Double aDouble = Double.valueOf(next.get(2).toString());
                Integer year = null;
                if (aDouble == 1.0){
                    year = 2021;
                }else {
                    year = 2022;
                }
                student.setYear(year);
                String email = next.get(3).toString();
                student.setEmail(email);
                String school = next.get(4).toString();
                student.setSchool(school);
                String xzclass = next.get(5).toString();
                student.setXZclass(xzclass);
                student.setCreateTime(new Date());
                students.add(student);
            }
            //将数据插入到数据库
            studentService.addStudents(students);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    //查找上传页面
    @RequestMapping("/upload")
    public String upload(){
        return "uploadExcel";
    }

}