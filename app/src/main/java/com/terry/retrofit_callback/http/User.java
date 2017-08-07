package com.terry.retrofit_callback.http;

/***
 * *
 * 名称：     User.
 * 作者：     Terry Tan
 * 创建时间：  on 2017/6/23.
 * 说明：     
 * *
 ***/


public class User {

    public String id, name, img, sex, qianmin;
    public int age;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", sex='" + sex + '\'' +
                ", qianmin='" + qianmin + '\'' +
                ", age=" + age +
                '}';
    }
}
