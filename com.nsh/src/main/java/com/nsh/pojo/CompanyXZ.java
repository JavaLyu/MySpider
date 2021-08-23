package com.nsh.pojo;

import com.fasterxml.jackson.core.SerializableString;

import java.util.Date;

/**
 * @author NSH
 * @create 2021-08-13 18:05
 */
public class CompanyXZ {
    private Integer id;
    private String name;
    private String jobUrl;
    private String workCity;
    private String price;
    private String jobName;
    private String companyInfo;
    private Date createTime;
    private Integer state;
    private String md5Key;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    @Override
    public String toString() {
        return "CompanyXZ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobUrl='" + jobUrl + '\'' +
                ", workCity='" + workCity + '\'' +
                ", price='" + price + '\'' +
                ", jobName='" + jobName + '\'' +
                ", companyInfo='" + companyInfo + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                ", md5Key='" + md5Key + '\'' +
                '}';
    }
}
