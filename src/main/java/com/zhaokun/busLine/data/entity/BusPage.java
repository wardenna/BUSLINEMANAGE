package com.zhaokun.busLine.data.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
public class BusPage<T> {
    private String total;
    private Collection<T> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Collection<T> getRows() {
        return rows;
    }

    public void setRows(Collection<T> rows) {
        this.rows = rows;
    }
}
