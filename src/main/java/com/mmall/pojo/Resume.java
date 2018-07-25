package com.mmall.pojo;

import java.util.Date;

public class Resume {
    private Integer id;
    private String name;
    private String resumeName;
    private String resumeUrl;
    private Date createTime;

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

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Resume(Integer id, String name, String resumeName, String resumeUrl, Date createTime) {

        this.id = id;
        this.name = name;
        this.resumeName = resumeName;
        this.resumeUrl = resumeUrl;
        this.createTime = createTime;
    }

    public Resume() {
    }
}
