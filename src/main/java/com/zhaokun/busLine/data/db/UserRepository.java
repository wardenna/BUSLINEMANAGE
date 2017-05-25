package com.zhaokun.busLine.data.db;

import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.User;

import javax.jws.Oneway;
import java.util.List;

public interface UserRepository {

    String count();

    List<User> findByLimit(String limit, String offset);

    boolean addUser(User user);

    boolean changeUser(User user);

    User login(User user);

}
