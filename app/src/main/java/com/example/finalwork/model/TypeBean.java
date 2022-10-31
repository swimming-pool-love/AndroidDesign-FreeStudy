package com.example.finalwork.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 分类实体类
 */
public class TypeBean extends DataSupport implements Serializable {
    private int id;
    private String name; //名字
    private String imageRes;//图片资源

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeBean(String name, String imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }
}
