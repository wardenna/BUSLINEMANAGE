package com.zhaokun.busLine.data.db;

import com.zhaokun.busLine.data.entity.BusStation;

import java.util.List;

public interface BusStationRepository {

    long count();

    BusStation save(BusStation busStation);

    BusStation findOne(int id);

    List<BusStation> findByBusLineId(String busLineId);

    List<BusStation> findByBusStation(String busStationName);

    List<BusStation> findByStartAndEnd(String startStation, String endStation);

    List<BusStation> findAll();

    Boolean addBusStation(BusStation busStation);
}
