package com.example.jsp.Model;

import com.example.jsp.Entity.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DataTable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<UserEntity> userEntities;

    public DataTable(int draw, long recordsTotal, long recordsFiltered, List<UserEntity> userEntities) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.userEntities = userEntities;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
