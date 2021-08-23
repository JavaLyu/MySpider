package com.nsh.dao;

import com.nsh.pojo.CompanyXZ;

import java.util.List;

/**
 * @author NSH
 * @create 2021-08-13 18:09
 */
public interface CompanyMapper {
    int insertCompany(CompanyXZ companyXZ);
    //获取存储的MD5值
    List<String> getallMd5();
    //获取所有的未发送的数据
    List<CompanyXZ> selectAllNotSend();
    //更改状态值
    void batchUpdateStatr(List<Integer> ids);
}
