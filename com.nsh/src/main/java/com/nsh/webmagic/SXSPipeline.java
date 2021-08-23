package com.nsh.webmagic;

import com.nsh.pojo.CompanyXZ;
import com.nsh.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author NSH
 * @create 2021-08-13 18:53
 * pipeline实现持久化
 */
@Component
public class SXSPipeline implements Pipeline {
    @Autowired
    CompanyService companyService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<Map.Entry<String, Object>> entries = resultItems
                .getAll().entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            if ("companies".equals(entry.getKey())){
                List<CompanyXZ> companyXZS =  (List<CompanyXZ>)entry.getValue();
                //将结果存储到数据库
                for (CompanyXZ c : companyXZS){
                    companyService.addCompany(c);
                }
            }
        }

    }
}
