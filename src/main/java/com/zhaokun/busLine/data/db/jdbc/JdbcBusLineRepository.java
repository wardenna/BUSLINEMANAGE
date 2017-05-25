package com.zhaokun.busLine.data.db.jdbc;

import com.zhaokun.busLine.data.db.BusLineRepository;
import com.zhaokun.busLine.data.entity.BusLine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JdbcBusLineRepository implements BusLineRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcBusLineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM busLine", Integer.class);
    }

    @Override
    public BusLine save(BusLine busLine) {
        String id = busLine.getBusLineId();
        //如果ID为空则插入线路返回ID，否则更新线路
        if (id == null || id.equals("")) {
            String busLineId = insertBusLineAndReturnId(busLine);
            return new BusLine(busLineId, busLine.getBusLineName(), busLine.getStartStation(), busLine.getEndStation(), busLine.getStartTime());
        } else {
            jdbcTemplate.update("UPDATE BUSLINE SET BUSLINENAME = ?, STARTSTATION = ?, ENDSTATION = ?, STARTTIME = ? WHERE BUSLINEID = ?",
                    busLine.getBusLineName(),
                    busLine.getStartStation(),
                    busLine.getEndStation(),
                    busLine.getStartTime(),
                    id);
        }
        return busLine;
    }

    private String insertBusLineAndReturnId(BusLine busLine) {
        String uuid = UUID.randomUUID().toString();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("busLine");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("busLineId", uuid);
        args.put("busLineName", busLine.getBusLineName());
        args.put("startStation", busLine.getStartStation());
        args.put("endStation", busLine.getEndStation());
        args.put("startTime", busLine.getStartTime());
        jdbcInsert.execute(args);
        return uuid;
    }

    @Override
    public BusLine findOne(int id) {
        return null;
    }

    @Override
    public List<BusLine> findByBusLineName(String busLineName) {
        return this.jdbcTemplate.query("SELECT * FROM BUSLINE WHERE BUSLINENAME = ?", new BusLineMapper(), busLineName);
    }

    @Override
    public List<BusLine> findByBusStation(String busStationName) {
        return this.jdbcTemplate.query("SELECT * FROM busLine WHERE busLineId IN (SELECT busStation.busLineId FROM busStation WHERE busStationName LIKE '%" + busStationName + "%')", new BusLineMapper());
    }

    @Override
    public List<BusLine> findByStartAndEnd(String startStation, String endStation) {
        return this.jdbcTemplate.query("SELECT * FROM busLine WHERE busLineId IN (SELECT busLineId FROM busStation WHERE busStation.startStation LIKE '%" + startStation + "%' AND busStation.endStation LIKE '%" + endStation + "%')", new BusLineMapper());
    }

    @Override
    public List<BusLine> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM BUSLINE", new BusLineMapper());
    }

    @Override
    public List<BusLine> findByLimit(String limit, String offset) {
        int length = Integer.parseInt(limit) + Integer.parseInt(offset);
        int newOffset = Integer.parseInt(offset) + 1;
        return this.jdbcTemplate.query("SELECT * FROM busLine WHERE ROWNUM <= " + length + " MINUS SELECT * FROM busLine WHERE ROWNUM < " + newOffset, new BusLineMapper());
    }

    @Override
    public Boolean addBusLine(BusLine busLine) {
        this.jdbcTemplate.update(
                "INSERT INTO BUSLINE (BUSLINEID, BUSLINENAME, STARTSTATION, ENDSTATION, STARTTIME, REMARKS, DESCRIPTION, COMPANY, PHONE, BUSNUMBER, DISTANCE, PRICE) VALUES (SEQ_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                busLine.getBusLineName(),
                busLine.getStartStation(),
                busLine.getEndStation(),
                busLine.getStartTime(),
                busLine.getRemarks(),
                busLine.getDescription(),
                busLine.getCompany(),
                busLine.getPhone(),
                busLine.getBusNumber(),
                busLine.getDistance(),
                busLine.getPrice());
        return true;
    }

    @Override
    public Boolean deleteBusLine(String busLineId) {
        this.jdbcTemplate.update("DELETE FROM BUSLINE WHERE BUSLINEID = ?", busLineId);
        return true;
    }

    @Override
    public Boolean changeBusLine(BusLine busLine) {
        this.jdbcTemplate.update("UPDATE BUSLINE SET BUSLINENAME = ?, STARTSTATION = ?, ENDSTATION = ?, STARTTIME = ?, REMARKS = ?, DESCRIPTION = ?, COMPANY = ?, PHONE = ?, BUSNUMBER = ?, DISTANCE = ?, PRICE = ? WHERE BUSLINEID = ?",
                busLine.getBusLineName(),
                busLine.getStartStation(),
                busLine.getEndStation(),
                busLine.getStartTime(),
                busLine.getRemarks(),
                busLine.getDescription(),
                busLine.getCompany(),
                busLine.getPhone(),
                busLine.getBusNumber(),
                busLine.getDistance(),
                busLine.getPrice(),
                busLine.getBusLineId());
        return true;
    }

    private static final class BusLineMapper implements RowMapper<BusLine> {
        public BusLine mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            BusLine busLine = new BusLine();
            busLine.setBusLineId(resultSet.getString("busLineId"));
            busLine.setBusLineName(resultSet.getString("busLineName"));
            busLine.setStartStation(resultSet.getString("startStation"));
            busLine.setEndStation(resultSet.getString("endStation"));
            busLine.setStartTime(resultSet.getString("startTime"));
            busLine.setRemarks(resultSet.getString("remarks"));
            busLine.setDescription(resultSet.getString("description"));
            busLine.setCompany(resultSet.getString("company"));
            busLine.setPhone(resultSet.getString("phone"));
            busLine.setBusNumber(resultSet.getString("busNumber"));
            busLine.setDistance(resultSet.getString("distance"));
            busLine.setPrice(resultSet.getString("price"));
            return busLine;
        }
    }
}
