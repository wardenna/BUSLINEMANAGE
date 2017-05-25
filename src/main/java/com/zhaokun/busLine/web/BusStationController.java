package com.zhaokun.busLine.web;

import com.google.gson.Gson;
import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.db.BusStationRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Controller
public class BusStationController {

    @Autowired
    private BusLineRepository busLineRepository;

    @Autowired
    private BusStationRepository busStationRepository;

    @RequestMapping(value = "/busStationData", method = RequestMethod.GET)
    public void getData(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("search"));
        System.out.println(request.getParameter("busLineId"));
        Gson gson = new Gson();
        Collection<BusStation> ints = busStationRepository.findByBusLineId(request.getParameter("busLineId"));
        System.out.println(gson.toJson(ints));
        response.getWriter().write(gson.toJson(ints));
    }

    @RequestMapping(value = "/addBusStation", method = RequestMethod.GET)
    public void addBusStation(HttpServletResponse response, HttpServletRequest request, Model model, BusStation busStation) throws IOException {
        request.setCharacterEncoding("Utf-8");
        System.out.println(busStation.getArrivalTime());
        System.out.println(busStation.getBusLineId());
        busStationRepository.addBusStation(busStation);
    }
}
