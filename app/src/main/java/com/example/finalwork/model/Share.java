package com.example.finalwork.model;

import android.graphics.Bitmap;

public class Share {
    private int shareId;
    private String userName;
    private String shareTitle;
    private String shareLabel;
    private String shareContent;
    private Bitmap shareImg;
    private String publishTime;
    public Share(){

    };
    public Share(int shareId,String userName,String shareTitle,String shareLabel,String shareContent,Bitmap shareImg,String publishTime){
        this.shareId = shareId;
        this.userName = userName;
        this.shareTitle = shareTitle;
        this.shareContent =shareContent;
        this.shareLabel = shareLabel;
        this.shareImg = shareImg;
        this.publishTime = publishTime;

    };

    public int getShareId() {
        return shareId;
    }

    public String getUserName() {
        return userName;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareLabel() {
        return shareLabel;
    }

    public String getShareContent() {
        return shareContent;
    }

    public Bitmap getShareImg() {
        return shareImg;
    }


    public String getPublishTime() {
        return publishTime;
    }
}
