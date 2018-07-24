package com.mmall.dao;

import com.mmall.pojo.Cart;
import com.mmall.pojo.Liuyan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LiuyanMapper {

    public Integer insert(Liuyan liuyan);

    public List<Liuyan> findAll();
}