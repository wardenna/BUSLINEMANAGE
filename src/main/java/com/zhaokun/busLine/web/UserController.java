package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.db.BusStationRepository;
import com.zhaokun.busLine.data.db.UserRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusLinePage;
import com.zhaokun.busLine.data.entity.BusPage;
import com.zhaokun.busLine.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userIndex(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        return "login.html";
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String map(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        return "map.html";
    }


    @RequestMapping(value = "/loginData", method = RequestMethod.POST)
    public void userLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model, User user) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        User user1 = userRepository.login(user);
        session.setAttribute("user", user1);
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(user1));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String userRegister(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        return "register.html";
    }

    @RequestMapping(value = "/registerData", method = RequestMethod.POST)
    public void registerData(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        request.setCharacterEncoding("Utf-8");
        System.out.println(user.getEmail());
        userRepository.addUser(user);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        return "profile.html";
    }

    @RequestMapping(value = "/manageUser", method = RequestMethod.GET)
    public String manageUser(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        return "user.html";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        return "addUser.html";
    }

    @RequestMapping(value = "/addUserInfo", method = RequestMethod.GET)
    public void addUserInfo(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        request.setCharacterEncoding("Utf-8");
        userRepository.addUser(user);
    }

    @RequestMapping(value = "/userData", method = RequestMethod.GET)
    public void getData(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("limit"));
        System.out.println(request.getParameter("offset"));
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        Gson gson = new Gson();
        BusPage busPage = new BusPage();
        Collection<User> ints = userRepository.findByLimit(limit, offset);
        busPage.setRows(ints);
        String count = userRepository.count();
        busPage.setTotal(count);
        System.out.println(gson.toJson(busPage));
        response.getWriter().write(gson.toJson(busPage));
    }

    @RequestMapping(value = "/changeUser", method = RequestMethod.GET)
    public void changeUser(HttpServletResponse response, HttpServletRequest request, Model model, User user) throws IOException {
        request.setCharacterEncoding("Utf-8");
        userRepository.changeUser(user);
    }
}
