package com.mmall.pojo;

import java.util.Date;

public class Liuyan {
    private Integer id;
    private String mobile;
    private String content;
    private String name;

    private Date createTime;

    @Override
    public String toString() {
        return "Liuyan{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Liuyan() {
    }

    public Liuyan(Integer id, String mobile, String content, String name, Date createTime) {
        this.id = id;
        this.mobile = mobile;
        this.content = content;
        this.name = name;
        this.createTime = createTime;
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
}
