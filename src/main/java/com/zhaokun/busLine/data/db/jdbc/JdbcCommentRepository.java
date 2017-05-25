package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.db.CommentRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcCommentRepository implements CommentRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BUSLINE_COMMENT", Integer.class);
    }

    @Override
    public List<Comment> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM BUSLINE_COMMENT", new CommentMapper());
    }

    @Override
    public List<Comment> findByBusLineId(String busLineId) {
        return this.jdbcTemplate.query("SELECT * FROM BUSLINE_COMMENT WHERE BUSLINEID = ?", new CommentMapper(), busLineId);
    }

    @Override
    public List<Comment> findByLimit(String limit, String offset) {
        int length = Integer.parseInt(limit) + Integer.parseInt(offset);
        int newOffset = Integer.parseInt(offset) + 1;
        return this.jdbcTemplate.query("SELECT * FROM BUSLINE_COMMENT WHERE ROWNUM <= " + length + " MINUS SELECT * FROM BUSLINE_COMMENT WHERE ROWNUM < " + newOffset, new CommentMapper());
    }

    @Override
    public boolean addComment(Comment comment) {
        comment.setCommentTime(new Date().toString());
        comment.setCommentLike("0");
        this.jdbcTemplate.update("INSERT INTO BUSLINE_COMMENT (COMMENTID, BUSLINEID, COMMENTTEXT, COMMENTLIKE, COMMENTTIME) VALUES (SEQ_CommentID.nextval, ?, ?, ?, ?)",
                comment.getBusLineId(), comment.getCommentText(), comment.getCommentLike(), comment.getCommentTime());
        return true;
    }

    @Override
    public boolean deleteComment(String commentId) {
        this.jdbcTemplate.update("DELETE FROM BUSLINE_COMMENT WHERE COMMENTID = ?", commentId);
        return true;
    }

    private static final class CommentMapper implements RowMapper<Comment> {
        public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setCommentId(resultSet.getString("commentId"));
            comment.setBusLineId(resultSet.getString("busLineId"));
            comment.setCommentLike(resultSet.getString("commentLike"));
            comment.setCommentText(resultSet.getString("commentText"));
            comment.setCommentTime(resultSet.getString("commentTime"));
            return comment;
        }
    }
}
