package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.BusStationRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import com.zhaokun.busLine.data.entity.BusStation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcBusStationRepository implements BusStationRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcBusStationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public BusStation save(BusStation busStation) {
        return null;
    }

    @Override
    public BusStation findOne(int id) {
        return null;
    }

    @Override
    public List<BusStation> findByBusLineId(String busLineId) {
        return this.jdbcTemplate.query("SELECT * FROM busStation WHERE BUSLINEID = ?", new BusStationMapper(), busLineId);
    }

    @Override
    public List<BusStation> findByBusStation(String busStationName) {
        return this.jdbcTemplate.query("SELECT * FROM busStation WHERE BUSSTATIONNAME LIKE '%"+ busStationName +"%'", new BusStationMapper());
    }

    @Override
    public List<BusStation> findByStartAndEnd(String startStation, String endStation) {
        return this.jdbcTemplate.query("SELECT * FROM busStation WHERE BUSSTATION.STARTSTATION LIKE '%"+ startStation +"%' AND ENDSTATION LIKE '%" + endStation + "%'", new BusStationMapper());
    }

    @Override
    public List<BusStation> findAll() {
        return null;
    }

    @Override
    public Boolean addBusStation(BusStation busStation) {
        this.jdbcTemplate.update("CALL addBusStation(?, ?, ?, ?, ?, ?, ?)", busStation.getBusLineId(), busStation.getBusStationName(), busStation.getArrivalTime(), busStation.getSeqNumber(), busStation.getStartStation(), busStation.getEndStation(), busStation.getRemarks());
        return true;
    }

    private static final class BusStationMapper implements RowMapper<BusStation> {
        public BusStation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            BusStation busStation = new BusStation();
            busStation.setBusStationId(resultSet.getString("busStationId"));
            busStation.setBusLineId(resultSet.getString("busLineId"));
            busStation.setBusStationName(resultSet.getString("busStationName"));
            busStation.setArrivalTime(resultSet.getString("arrivalTime"));
            busStation.setStartStation(resultSet.getString("startStation"));
            busStation.setEndStation(resultSet.getString("endStation"));
            busStation.setSeqNumber(resultSet.getString("seqNumber"));
            busStation.setRemarks(resultSet.getString("remarks"));
            return busStation;
        }
    }
}
