package com.nsh.service;

import com.nsh.pojo.CompanyXZ;
import com.nsh.pojo.MailVo;
import com.nsh.webmagic.SXSPipeline;
import com.nsh.webmagic.SXSProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NSH
 * @create 2021-08-16 11:48
 * 专门处理发送邮件服务
 */
@Service
public class MassageService {

    @Autowired
    CompanyService companyService;

    @Autowired
    MailService mailService;

    @Autowired
    StudentService studentService;

    @Autowired
    SXSProcessor sxsProcessor;

    @Autowired
    SXSPipeline sxsPipeline;

    //开始爬虫 定时每晚12点爬
    @Scheduled(cron = "00 00 00 * * *")
    public void scheduleSpider(){
        Spider.create(sxsProcessor)
                .addPipeline(sxsPipeline)
                .addUrl(SXSProcessor.getURL())
                .run();
    }
    //给用户发邮件
    @Scheduled(cron = "00 10 07 * * *")
    public void notifyByMail(){
        /**
         * 1.先获取所有的新数据
         * 2.封装邮件内容
         * 3.给所有符合要求的用户进行发送
         * 4.变更数据库已发数据状态的变更
         */
        List<CompanyXZ> cpmpanies = companyService.selectNewCpmpanies();
        //判断爬取的内容是否有新增
        if (cpmpanies.size() < 1){
            System.out.println("没有新增招聘信息");
            return;
        }
        //获取符合要求的邮箱
        List<String> emails = studentService.selectAllGrade();

        if (emails.size() < 1){
            System.out.println("没有符合条件的用户，不进行推送");
            return;
        }
        //封装邮件正文
        String content = null;
        try {
            content = buildSXContent(cpmpanies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //分别给用户发送
        for (String email : emails) {
            MailVo mailVo = new MailVo();
            mailVo.setTo(email);
            mailVo.setTitle("有新的招聘信息");
            mailVo.setContent(content);
            mailService.sendHtmlMail(mailVo);
        }
            //变更状态
            //先那个所有需要变更的id
            List<Integer> ids = cpmpanies.stream().map(item -> item.getId()).collect(Collectors.toList());
            //变更数据
            companyService.batchUpdateStatr(ids);
            System.out.println("数据变更完成");


    }
    //封装邮件文本内容
    private String buildSXContent(List<CompanyXZ> companyXZS) throws IOException {

        //加载邮件html模板
        String fileName = "template/XiaoZhao.html";
        //以流的形式将内容从HTML中读取出来
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            System.out.println("读取文件失败，fileName:"+fileName+ e);
        } finally {
            inputStream.close();
            fileReader.close();
        }
        //采用的是字符串拼接的方法进行输出
        String contentText = "以下是新增招聘公司,共"+companyXZS.size()+"家公司信息.敬请查看";
        //邮件表格header
        String header = "<td>公司</td><td>工作岗位</td><td>薪资范围</td><td>工作地点</td><td>公司简介</td><td>立即申请</td>";
        StringBuilder linesBuffer = new StringBuilder();
        //int i = 0;
        //table   <td>XX</td><td>XXX</td>
        for (CompanyXZ company:companyXZS) {
            linesBuffer.append("<tr><td>");
            //公司名
            linesBuffer.append(company.getName());
            linesBuffer.append("</td><td>");
            //网申时间
            linesBuffer.append(company.getJobName());
            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getPrice());

            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getWorkCity());

            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getCompanyInfo());

            linesBuffer.append("</td><td>");
            linesBuffer.append(" <a href=\"");
            //公司网申URL
            linesBuffer.append(company.getJobUrl());
            linesBuffer.append("\"> 我要投 </a>");
            linesBuffer.append(" </td></tr>");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = format.format(new Date());
        //填充html模板中的四个参数 ：完成HTML模板页面的参数替换{1}{2}{3}{4}
        String htmlText = MessageFormat.format(buffer.toString(), "tmp",contentText, date, header,linesBuffer.toString());

        //改变表格样式 将<td>全部替换样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");

        return htmlText;
    }
}
