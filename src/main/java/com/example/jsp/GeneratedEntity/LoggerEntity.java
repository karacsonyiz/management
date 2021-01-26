package com.example.jsp.GeneratedEntity;

import javax.persistence.*;

@Entity
@Table(name = "log", schema = "demo")
public class LoggerEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logid;
    @Column(name = "logtext")
    private String logText;

    public LoggerEntity(String logText) {
        super();
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

}