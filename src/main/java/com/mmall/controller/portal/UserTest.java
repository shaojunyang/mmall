package com.mmall.controller.portal;

/**
 * ajax
 *
 * @author yangshaojun
 * @create 2018-02-19 上午11:51
 **/

public class UserTest {
    private String name;
    private Integer age;

    public UserTest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
