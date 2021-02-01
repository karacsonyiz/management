package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DataTable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<GeneratedUserEntity> userEntities;
    private List<GeneratedOrganizationEntity> orgEntities;
    private int start;

    public DataTable(int draw, long recordsTotal, long recordsFiltered, List<GeneratedUserEntity> userEntities, List<GeneratedOrganizationEntity> orgEntities) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.userEntities = userEntities;
        this.orgEntities = orgEntities;
    }

    public DataTable(int draw, long recordsTotal, long recordsFiltered, List<GeneratedOrganizationEntity> orgEntities, List<GeneratedUserEntity> userEntities,int start) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.orgEntities = orgEntities;
        this.userEntities = userEntities;
        this.start = start;
    }

    public DataTable() {
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

    public List<GeneratedUserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<GeneratedUserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public List<GeneratedOrganizationEntity> getOrgEntities() {
        return orgEntities;
    }

    public void setOrgEntities(List<GeneratedOrganizationEntity> orgEntities) {
        this.orgEntities = orgEntities;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "DataTable{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", userEntities=" + userEntities +
                ", orgEntities=" + orgEntities +
                '}';
    }
}
