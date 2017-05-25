package com.zhaokun.busLine.data.db;

import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusStation;

import java.util.List;

public interface BusLineRepository {

    long count();

    BusLine save(BusLine busLine);

    BusLine findOne(int id);

    List<BusLine> findByBusLineName(String busLineName);

    List<BusLine> findByBusStation(String busStationName);

    List<BusLine> findByStartAndEnd(String startStation, String endStation);

    List<BusLine> findAll();

    List<BusLine> findByLimit(String limit, String offset);

    Boolean addBusLine(BusLine busLine);

    Boolean deleteBusLine(String busLineId);

    Boolean changeBusLine(BusLine busLine);
}
