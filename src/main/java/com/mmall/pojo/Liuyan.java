package com.mmall.pojo;

import java.util.Date;

public class Liuyan {
    private Integer id;
    private String mobile;
    private String content;
    private String name;

    private Date createTime;

    private String email;
    private String company;

    @Override
    public String toString() {
        return "Liuyan{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Liuyan() {
    }

    public Liuyan(Integer id, String mobile, String content, String name, Date createTime, String email, String company) {
        this.id = id;
        this.mobile = mobile;
        this.content = content;
        this.name = name;
        this.createTime = createTime;
        this.email = email;
        this.company = company;
    }
}
