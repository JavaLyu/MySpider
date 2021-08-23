package com.nsh.pojo;

import java.util.Date;

/**
 * @author NSH
 * @create 2021-08-16 15:29
 */
public class Student {
    private Integer id;
    private String name;
    private String email;
    private Integer year;
    private String school;
    private String XZclass;
    private Date  createTime;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getXZclass() {
        return XZclass;
    }

    public void setXZclass(String XZclass) {
        this.XZclass = XZclass;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", year=" + year +
                ", school='" + school + '\'' +
                ", XZclass='" + XZclass + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
