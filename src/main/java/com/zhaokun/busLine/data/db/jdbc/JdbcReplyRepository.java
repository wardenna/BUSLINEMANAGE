package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.ReplyRepository;
import com.zhaokun.busLine.data.db.UserRepository;
import com.zhaokun.busLine.data.entity.Reply;
import com.zhaokun.busLine.data.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class JdbcReplyRepository implements ReplyRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcReplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Boolean addReply(Reply reply) {
        reply.setReplyTime(new Date().toString());
        this.jdbcTemplate.update(
                "INSERT INTO busLine_Reply(REPLYID, COMMENTID, REPLYTITLE, REPLYTEXT, REPLYTIME) VALUES (SEQ_REPLYID.nextval, ?, ?, ?, ?)",
                reply.getCommentId(),
                reply.getReplyTitle(),
                reply.getReplyText(),
                reply.getReplyTime());
        return true;
    }

    private static final class ReplyMapper implements RowMapper<Reply> {
        public Reply mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Reply reply = new Reply();
            reply.setReplyId(resultSet.getString("replyId"));
            reply.setCommentId(resultSet.getString("commentId"));
            reply.setReplyTitle(resultSet.getString("replyTitle"));
            reply.setReplyText(resultSet.getString("replyText"));
            reply.setReplyTime(resultSet.getString("replyTime"));
            return reply;
        }
    }
}
