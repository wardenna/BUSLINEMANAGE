package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.CommentRepository;
import com.zhaokun.busLine.data.db.NewsRepository;
import com.zhaokun.busLine.data.entity.BusPage;
import com.zhaokun.busLine.data.entity.Comment;
import com.zhaokun.busLine.data.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String index(Model model) {
        return "news.html";
    }

    @RequestMapping(value = "/addNews", method = RequestMethod.GET)
    public String addNews(Model model) {
        return "addNews.html";
    }

    @RequestMapping(value = "/findNewsByBusLineId", method = RequestMethod.GET)
    public void searchByBusLine(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("busLineId"));
        Gson gson = new Gson();
        Collection<News> newsList = newsRepository.findByBusLineId(request.getParameter("busLineId"));
        System.out.println(gson.toJson(newsList));
        response.getWriter().write(gson.toJson(newsList));
    }

    @RequestMapping(value = "/findNewsByTime", method = RequestMethod.GET)
    public void searchNews(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Collection<News> newsList = newsRepository.findByTime();
        System.out.println(gson.toJson(newsList));
        response.getWriter().write(gson.toJson(newsList));
    }

    @RequestMapping(value = "/findAllNews", method = RequestMethod.GET)
    public void searchAllNews(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("limit"));
        System.out.println(request.getParameter("offset"));
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        Gson gson = new Gson();
        BusPage busPage = new BusPage();
        Collection<News> ints = newsRepository.findByLimit(limit, offset);
        busPage.setRows(ints);
        long count = newsRepository.count();
        busPage.setTotal(count + "");
        System.out.println(gson.toJson(busPage));
        response.getWriter().write(gson.toJson(busPage));
    }

    @RequestMapping(value = "/addNewsInfo", method = RequestMethod.GET)
    public void addNews(HttpServletResponse response, HttpServletRequest request,News news, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(news.toString());
        newsRepository.addNews(news);
    }

    @RequestMapping(value = "/deleteNews", method = RequestMethod.GET)
    public void deleteNews(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        request.setCharacterEncoding("Utf-8");
        String newsId = request.getParameter("newsId");
        newsRepository.deleteNews(newsId);
    }

}
