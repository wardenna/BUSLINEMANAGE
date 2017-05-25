package com.zhaokun.busLine.data.db;

import com.zhaokun.busLine.data.entity.Comment;
import com.zhaokun.busLine.data.entity.User;

import java.util.List;

public interface CommentRepository {

    long count();

    List<Comment> findAll();

    List<Comment> findByBusLineId(String busLineId);

    List<Comment> findByLimit(String limit, String offset);

    boolean addComment(Comment comment);

    boolean deleteComment(String commentId);
}
