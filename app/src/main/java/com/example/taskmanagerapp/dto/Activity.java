package com.example.taskmanagerapp.dto;

import java.sql.Date;

public class Activity {
    private Integer logId;
    private String action;
    private Date timestamp;
    private Integer userId;
    private Integer taskId;
    private String status;

    public Activity(Integer logId, String action, Date timestamp, Integer userId, Integer taskId, String status) {
        this.logId = logId;
        this.action = action;
        this.timestamp = timestamp;
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
