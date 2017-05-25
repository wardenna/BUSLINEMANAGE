package com.zhaokun.busLine.data.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BusLinePage {
    private String total;
    private Collection<BusLine> rows;

    public Collection<BusLine> getRows() {
        return rows;
    }

    public void setRows(Collection<BusLine> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
