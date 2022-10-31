package com.example.finalwork.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;
/*
倒数日实体类
 */
public class Matter extends DataSupport implements Serializable {


    private String matterContent; //内容
    private Date targetDate;
    private Date createDate;
    private long typeId;//所属分类id

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public Matter() {

    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public Date getTargetDate() {
        return targetDate;
    }

    public void setMatterContent(String matterContent) {
        this.matterContent = matterContent;
    }


    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getMatterContent() {
        return matterContent;
    }

}

