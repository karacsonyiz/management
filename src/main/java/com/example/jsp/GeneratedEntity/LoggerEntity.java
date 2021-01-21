package com.example.jsp.GeneratedEntity;

import javax.persistence.*;

@Entity
@Table(name = "log", schema = "demo")
public class LoggerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logid;
    @Column(name = "logtext")
    private String logText;
    private String createdby;
    private String updatedby;
    @Column(name = "createdtimestamp")
    private String createdTimeStamp;
    @Column(name = "updatedtimestamp")
    private String updatedTimeStamp;

    public LoggerEntity(String logText, String createdTimeStamp) {
        this.logText = logText;
        this.createdTimeStamp = createdTimeStamp;
    }

    public LoggerEntity(String logText) {
        this.logText = logText;
    }

    public LoggerEntity() {
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }
}
