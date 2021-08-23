package com.nsh.webmagic;

import com.nsh.pojo.CompanyXZ;
import com.nsh.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author NSH
 * @create 2021-08-13 13:00
 */
@Component("sxsprocessor")
public class SXSProcessor implements PageProcessor {
    @Autowired
    CompanyService companyService;
    //页面url
    static String URL = "https://resume.shixiseng.com/interns?page=1&type=school&keyword=Java&area=&months=&days=&degree=&official=&enterprise=&salary=-0&publishTime=&sortType=&city=%E5%85%A8%E5%9B%BD&internExtend=";

    //获取url
    public static String getURL(){
        return URL;
    }

    @Override
    public void process(Page page) {
        //解析页面
        //nodes获取各个节点
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='intern-wrap intern-item']").nodes();
        //停止爬虫操作
        if (nodes.size()<1){
            //System.out.println("当前最后一页");
            return;
        }
        Iterator<Selectable> iterator = nodes.iterator();
        //封装数据列表
        ArrayList<CompanyXZ> companyXZSlist = new ArrayList<>();
        Date date = new Date();
        //获取数据库中的所有md5值
        List<String> md5s = companyService.allMd5();
        while (iterator.hasNext()) {
            Selectable selectable = iterator.next();
            //StringBuffer stringBuffer = new StringBuffer();
            //岗位名称
            String jobName = selectable.xpath("//div[@class='f-l intern-detail__job']/p/a/text()").get();
           // stringBuffer.append("工作岗位："+jobName+",");
            //网申URL
            String jobURL = selectable.xpath("//div[@class='f-l intern-detail__job']/p/a/@href").get();
           // stringBuffer.append("网申URL："+jobURL+",");
            //工资信息
            String price = selectable.xpath("//span[@class='day font']/text()").get();
           // stringBuffer.append("工资信息："+price+",");
            //城市信息
            String city = selectable.xpath("//span[@class='city ellipsis']/text()").get();
            //stringBuffer.append("工作城市："+city+",");
            //公司信息
            String company = selectable.xpath("//div[@class='f-r intern-detail__company']/p/a/text()").get();
            //stringBuffer.append("公司："+company+",");
            //公司优势
            String companyInfo = selectable.xpath("//span[@class='company-label']/text()").get();
            //stringBuffer.append("公司信息："+companyInfo+",");
           // System.out.println(stringBuffer.toString());
            //获取md5值
            String key = company+jobName+city;
            //如果key重复表示已经存在，不进行后续的处理
            if (md5s.contains(key)){
                ///数据重复直接return 但是会存在重复之前的数据也需要保存
                System.out.println("数据重复：" + key);
                //所以需要将前面的数据交给papeline
                page.putField("companies",companyXZSlist);
                return;
            }
            //填充对象信息
            CompanyXZ companyXZ = new CompanyXZ();
            companyXZ.setName(company);
            companyXZ.setCompanyInfo(companyInfo);
            companyXZ.setJobName(jobName);
            companyXZ.setJobUrl(jobURL);
            companyXZ.setPrice(price);
            companyXZ.setWorkCity(city);
            companyXZ.setCreateTime(date);
            companyXZ.setState(0);//状态0表示未推送，1表示推送
            companyXZ.setMd5Key(company+jobName+city);//公司名称+岗位+城市作为判断
            companyXZSlist.add(companyXZ);
            System.out.println(companyXZ);
        }
        //将数据提交给pigeline
        page.putField("companies",companyXZSlist);
        //如果数据不小于1 小于20 说明是最后一页，也应该结束
        if (nodes.size() < 20){
           // System.out.println("当前最后一页");
            return;
        }
        String url = page.getUrl().get();
        Integer pagenum = Integer.valueOf(url.substring(url.indexOf("page=") + 5, url.indexOf("&")));
        System.out.println("当前页面："+pagenum+"----------------------------");
        pagenum += 1;
        //替换page=num
        url = url.replace(url.substring(url.indexOf("page="), url.indexOf("&")), "page=" + pagenum);
        //跳转到新的页面
            page.addTargetRequest(url);
    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    public static void main(String[] args) {
        Spider.create(new SXSProcessor())
                .addUrl(URL)
                .run();
    }
}
