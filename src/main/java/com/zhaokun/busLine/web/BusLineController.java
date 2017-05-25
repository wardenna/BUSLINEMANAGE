package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.db.BusStationRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusLinePage;
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
public class BusLineController {

    @Autowired
    private BusLineRepository busLineRepository;

    @RequestMapping(value = "/busLineData", method = RequestMethod.GET)
    public void getData(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("limit"));
        System.out.println(request.getParameter("offset"));
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        Gson gson = new Gson();
        BusLinePage busLinePage = new BusLinePage();
        Collection<BusLine> ints = busLineRepository.findByLimit(limit, offset);
        busLinePage.setRows(ints);
        Collection<BusLine> length = busLineRepository.findAll();
        busLinePage.setTotal(length.size() + "");
        System.out.println(gson.toJson(busLinePage));
        response.getWriter().write(gson.toJson(busLinePage));
    }

    @RequestMapping(value = "/busLineName", method = RequestMethod.GET)
    public void data(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Collection<BusLine> ints = busLineRepository.findAll();
        System.out.println(gson.toJson(ints));
        response.getWriter().write(gson.toJson(ints));
    }

    @RequestMapping(value = "/searchByBusLine", method = RequestMethod.GET)
    public void searchByBusLine(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("busLineName"));
        Gson gson = new Gson();
        BusLinePage busLinePage = new BusLinePage();
        Collection<BusLine> busLines = busLineRepository.findByBusLineName(request.getParameter("busLineName"));
        busLinePage.setRows(busLines);
        busLinePage.setTotal(busLines.size() + "");
        System.out.println(gson.toJson(busLinePage));
        response.getWriter().write(gson.toJson(busLinePage));
    }

    @RequestMapping(value = "/searchByBusStation", method = RequestMethod.GET)
    public void searchByBusStation(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("busStation"));
        Gson gson = new Gson();
        BusLinePage busLinePage = new BusLinePage();
        Collection<BusLine> busLines = busLineRepository.findByBusStation(request.getParameter("busStation"));
        busLinePage.setRows(busLines);
        busLinePage.setTotal(busLines.size() + "");
        System.out.println(gson.toJson(busLinePage));
        response.getWriter().write(gson.toJson(busLinePage));
    }

    @RequestMapping(value = "/searchByStartAndEnd", method = RequestMethod.GET)
    public void searchByStartAndEnd(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        String startStation = request.getParameter("startStation");
        String endStation = request.getParameter("endStation");
        System.out.println(request.getParameter("startStation"));
        System.out.println(request.getParameter("endStation"));
        Gson gson = new Gson();
        BusLinePage busLinePage = new BusLinePage();
        Collection<BusLine> ints = busLineRepository.findByStartAndEnd(startStation, endStation);
        busLinePage.setRows(ints);
        busLinePage.setTotal(ints.size() + "");
        System.out.println(gson.toJson(busLinePage));
        response.getWriter().write(gson.toJson(busLinePage));
    }

    @RequestMapping(value = "/addBusLine", method = RequestMethod.GET)
    public void addBusLine(HttpServletResponse response, HttpServletRequest request, Model model, BusLine busLine) throws IOException {
        request.setCharacterEncoding("Utf-8");
        System.out.println(busLine.getBusLineName());
        busLineRepository.addBusLine(busLine);
    }

    @RequestMapping(value = "/deleteBusLine", method = RequestMethod.GET)
    public void deleteBusLine(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        request.setCharacterEncoding("Utf-8");
        System.out.println(request.getParameter("busLineId"));
        String busLineId = request.getParameter("busLineId");
        busLineRepository.deleteBusLine(busLineId);
    }

    @RequestMapping(value = "/changeBusLine", method = RequestMethod.GET)
    public void changeBusLine(HttpServletResponse response, HttpServletRequest request, Model model, BusLine busLine) throws IOException {
        request.setCharacterEncoding("Utf-8");
        System.out.println(busLine.getStartStation());
        busLineRepository.changeBusLine(busLine);
    }
}
