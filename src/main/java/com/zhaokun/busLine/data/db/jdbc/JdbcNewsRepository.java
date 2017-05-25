package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.NewsRepository;
import com.zhaokun.busLine.data.entity.Comment;
import com.zhaokun.busLine.data.entity.News;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class JdbcNewsRepository implements NewsRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcNewsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM NEWS", Integer.class);
    }

    @Override
    public List<News> findAll() {
        return jdbcTemplate.query("SELECT * FROM NEWS", new NewsMapper());
    }

    @Override
    public List<News> findByBusLineId(String busLineId) {
        return jdbcTemplate.query("SELECT * FROM NEWS WHERE BUSLINEID = ?", new NewsMapper(), busLineId);
    }

    @Override
    public List<News> findByLimit(String limit, String offset) {
        int length = Integer.parseInt(limit) + Integer.parseInt(offset);
        int newOffset = Integer.parseInt(offset) + 1;
        return this.jdbcTemplate.query("SELECT * FROM NEWS WHERE ROWNUM <= " + length + " MINUS SELECT * FROM NEWS WHERE ROWNUM < " + newOffset, new NewsMapper());
    }

    @Override
    public List<News> findByTime() {
        return jdbcTemplate.query("SELECT * FROM NEWS ORDER BY NEWSTIME DESC", new NewsMapper());
    }

    @Override
    public boolean addNews(News news) {
        news.setNewsTime(new Date().toString());
        this.jdbcTemplate.update(
                "INSERT INTO NEWS(NEWSID, BUSLINEID, NEWSTITLE, NEWSTEXT, NEWSTIME) VALUES (SEQ_ID.nextval, ?, ?, ?, ?)",
                news.getBusLineId(),
                news.getNewsTitle(),
                news.getNewsText(),
                news.getNewsTime());
        return true;
    }

    @Override
    public boolean deleteNews(String newsId) {
        this.jdbcTemplate.update("DELETE FROM NEWS WHERE NEWSID = ?", newsId);
        return true;
    }

    @Override
    public boolean changeNews(News news) {
        this.jdbcTemplate.update("UPDATE NEWS SET BUSLINEID = ?, NEWSTITLE = ?, NEWSTEXT = ?, NEWSTIME = ? WHERE NEWSID = ?",
                news.getBusLineId(),
                news.getNewsTitle(),
                news.getNewsText(),
                news.getNewsTime(),
                news.getNewsId());
        return true;
    }

    private static final class NewsMapper implements RowMapper<News> {
        public News mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            News news = new News();
            news.setNewsId(resultSet.getString("newsId"));
            news.setBusLineId(resultSet.getString("busLineId"));
            news.setNewsTitle(resultSet.getString("newsTitle"));
            news.setNewsText(resultSet.getString("newsText"));
            news.setNewsTime(resultSet.getString("newsTime"));
            return news;
        }
    }
}
