package com.zhaokun.busLine.data.db;

import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.Comment;
import com.zhaokun.busLine.data.entity.News;

import java.util.List;

public interface NewsRepository {

    long count();

    List<News> findAll();

    List<News> findByBusLineId(String busLineId);

    List<News> findByLimit(String limit, String offset);

    List<News> findByTime();

    boolean addNews(News news);

    boolean deleteNews(String newsId);

    boolean changeNews(News news);
}
