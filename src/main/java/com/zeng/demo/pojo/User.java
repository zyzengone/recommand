package com.zeng.demo.pojo;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: yourname
 * @date: 2020年12月08日 下午6:58
 */
public class User {
    private int age;
    private String name;
    private Date brthday;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBrthday() {
        return brthday;
    }

    public void setBrthday(Date brthday) {
        this.brthday = brthday;
    }

}
