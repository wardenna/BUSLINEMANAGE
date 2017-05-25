package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.BusStationRepository;
import com.zhaokun.busLine.data.db.UserRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusStation;
import com.zhaokun.busLine.data.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String count() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM BUSLINEMANAGE_USER", String.class);
    }

    @Override
    public List<User> findByLimit(String limit, String offset) {
        int length = Integer.parseInt(limit) + Integer.parseInt(offset);
        int newOffset = Integer.parseInt(offset) + 1;
        return this.jdbcTemplate.query("SELECT * FROM BUSLINEMANAGE_USER WHERE ROWNUM <= " + length + " MINUS SELECT * FROM BUSLINEMANAGE_USER WHERE ROWNUM < " + newOffset, new UserMapper());
    }

    @Override
    public boolean addUser(User user) {
        this.jdbcTemplate.update("INSERT INTO BUSLINEMANAGE_USER(USERID, USERNAME, USERPASSWORD, USERSTATE, EMAIL, PHONE, AUTHORITY) VALUES (SEQ_USERID.nextval, ?, ?, '0', ?, ?, '1')",
                user.getUserName(),
                user.getUserPassword(),
                user.getEmail(),
                user.getPhone());
        return true;
    }

    @Override
    public boolean changeUser(User user) {
        this.jdbcTemplate.update("UPDATE BUSLINEMANAGE_USER SET USERNAME = ?, USERPASSWORD = ?, USERSTATE = ?, EMAIL = ?, PHONE = ?, AUTHORITY = ? WHERE USERID = ?",
                user.getUserName(),
                user.getUserPassword(),
                user.getUserState(),
                user.getEmail(),
                user.getPhone(),
                user.getAuthority(),
                user.getUserId());
        return true;
    }

    @Override
    public User login(User user) {
        System.out.println(user.getEmail() + user.getUserPassword());
        List<User> users = this.jdbcTemplate.query("SELECT * FROM BUSLINEMANAGE_USER WHERE EMAIL = ? AND USERPASSWORD = ?", new UserMapper(), user.getEmail(), user.getUserPassword());
        System.out.println(users.isEmpty());
        if (users.isEmpty()) {
            return user;
        } else {
            return users.get(0);
        }
    }

    private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getString("userId"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setUserState(resultSet.getString("userState"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getString("phone"));
            user.setAuthority(resultSet.getString("authority"));
            return user;
        }
    }
}
