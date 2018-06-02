package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangshaojun
 * @create 2018-06-01 下午7:56
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Lombok {
    private String name;


    private Integer age;
}
