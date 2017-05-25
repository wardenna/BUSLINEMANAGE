package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusLinePage;
import com.zhaokun.busLine.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BusLineRepository busLineRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<BusLine> busLines = busLineRepository.findAll();
        model.addAttribute("busLines", busLines);
        model.addAttribute("recipient", "World");
        return "index.html";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) {
        return "main.html";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {
        return "error.html";
    }

    @RequestMapping(value = "/starter", method = RequestMethod.GET)
    public String start(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        return "starter.html";
    }

    @RequestMapping(value = "/searchBusLine", method = RequestMethod.GET)
    public String searchBusLine(Model model) {
        return "starter.html";
    }

    @RequestMapping(value = "/busLine", method = RequestMethod.GET)
    public String addBusLine(Model model) {
        return "busLine.html";
    }

    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public String table(Model model) {
        return "table.html";
    }

//    @RequestMapping(value = "/data1", method = RequestMethod.GET)
//    public void data(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        System.out.println(request.getParameter("search"));
//        Gson gson = new Gson();
//        BusLinePage busLinePage = new BusLinePage();
//        Collection<BusLine> ints = busLineRepository.findAll();
//        busLinePage.setRows(ints);
//        busLinePage.setTotal(ints.size() + "");
//        System.out.println(gson.toJson(busLinePage));
//        response.getWriter().write(gson.toJson(busLinePage));
//    }
}
