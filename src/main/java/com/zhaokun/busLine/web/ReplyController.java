package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.db.ReplyRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusLinePage;
import com.zhaokun.busLine.data.entity.Reply;
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
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @RequestMapping(value = "/reply", method = RequestMethod.GET)
    public void reply(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("commentId"));
        System.out.println(request.getParameter("replyTitle"));
        System.out.println(request.getParameter("replyText"));
        String commentId = request.getParameter("commentId");
        String replyTitle = request.getParameter("replyTitle");
        String replyText = request.getParameter("replyText");
        Reply reply = new Reply();
        reply.setCommentId(commentId);
        reply.setReplyTitle(replyTitle);
        reply.setReplyText(replyText);
        replyRepository.addReply(reply);
    }

}
