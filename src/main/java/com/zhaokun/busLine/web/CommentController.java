package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.CommentRepository;
import com.zhaokun.busLine.data.db.UserRepository;
import com.zhaokun.busLine.data.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public String index(Model model) {
        return "comment.html";
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public void registerData(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        request.setCharacterEncoding("Utf-8");
        String commentText = request.getParameter("commentText");
        String busLineId = request.getParameter("busLineId");
        System.out.println(commentText);
        Comment comment = new Comment();
        comment.setBusLineId(busLineId);
        comment.setCommentText(commentText);
        commentRepository.addComment(comment);
    }

    @RequestMapping(value = "/findCommentByBusLineId", method = RequestMethod.GET)
    public void searchByBusLine(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("busLineId"));
        Gson gson = new Gson();
        Collection<Comment> comments = commentRepository.findByBusLineId(request.getParameter("busLineId"));
        System.out.println(gson.toJson(comments));
        response.getWriter().write(gson.toJson(comments));
    }

    @RequestMapping(value = "/findAllComment", method = RequestMethod.GET)
    public void searchAllComment(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("limit"));
        System.out.println(request.getParameter("offset"));
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        Gson gson = new Gson();
        BusPage busPage = new BusPage();
        Collection<Comment> ints = commentRepository.findByLimit(limit, offset);
        busPage.setRows(ints);
        long count = commentRepository.count();
        busPage.setTotal(count + "");
        System.out.println(gson.toJson(busPage));
        response.getWriter().write(gson.toJson(busPage));
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
    public void deleteComment(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        request.setCharacterEncoding("Utf-8");
        String commentId = request.getParameter("commentId");
        commentRepository.deleteComment(commentId);
    }
}
