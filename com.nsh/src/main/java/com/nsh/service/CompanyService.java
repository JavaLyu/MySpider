package com.nsh.service;

import com.nsh.dao.CompanyMapper;
import com.nsh.pojo.CompanyXZ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author NSH
 * @create 2021-08-13 18:36
 */
@Service
public class CompanyService {
    @Autowired
    CompanyMapper companyMapper;

    public  int addCompany(CompanyXZ companyXZ){
        return companyMapper.insertCompany(companyXZ);
    }

    public List<String> allMd5(){
        return companyMapper.getallMd5();
    }

    public List<CompanyXZ> selectNewCpmpanies(){
        return companyMapper.selectAllNotSend();
    }

    //变更状态
    public void batchUpdateStatr(List<Integer> ids){
        companyMapper.batchUpdateStatr(ids);
    }
}
