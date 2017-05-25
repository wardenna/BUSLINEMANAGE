package com.zhaokun.busLine.data.entity;

import org.springframework.stereotype.Component;

@Component
public class News {
    private String newsId;
    private String busLineId;
    private String newsTitle;
    private String newsText;
    private String newsTime;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getBusLineId() {
        return busLineId;
    }

    public void setBusLineId(String busLineId) {
        this.busLineId = busLineId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}
