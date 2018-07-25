package com.mmall.dao;

import com.mmall.pojo.Resume;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeMapper {

    public Integer insert(Resume resume);

    public List<Resume> findAll();
}