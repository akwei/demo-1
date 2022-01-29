package com.github.akwei.demo1.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long userId;
    private String name;
    private Date createTime;
}
