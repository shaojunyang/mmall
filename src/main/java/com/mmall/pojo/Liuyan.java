package com.mmall.pojo;

public class Liuyan {
    private Integer id;
    private String mobile;
    private String content;
    private String name;

    @Override
    public String toString() {
        return "Liuyan{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Liuyan() {
    }

    public Liuyan(Integer id, String mobile, String content, String name) {
        this.id = id;
        this.mobile = mobile;
        this.content = content;
        this.name = name;
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
}
