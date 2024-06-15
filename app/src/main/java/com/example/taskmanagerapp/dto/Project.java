package com.example.taskmanagerapp.dto;

import java.io.Serializable;
import java.sql.Date;

public class Project implements Serializable {
    private Integer projectId;
    private String projectName;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private String createdAt;
    private String updatedAt;
    private Integer manager_id;
    private Integer tienDo;

    public Project(Integer projectId, String description, String status, Integer tienDo) {
        this.projectId = projectId;
        this.description = description;
        this.status = status;
        this.tienDo = tienDo;
    }

    public Project(String projectName, String description, String createdAt, String startDate, String endDate, String updateAt, String status, Integer manager_id) {
        this.projectName = projectName;
        this.description = description;
        this.createdAt = createdAt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedAt = updateAt;
        this.status = status;
        this.manager_id = manager_id;
    }

    public Project(Integer projectId, String projectName, String description, String startDate, String endDate, String status, String createdAt, String updatedAt, Integer manager_id, Integer tienDo) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.manager_id = manager_id;
        this.tienDo = tienDo;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getManager_id() {
        return manager_id;
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }

    public Integer getTienDo() {
        return tienDo;
    }

    public void setTienDo(Integer tienDo) {
        this.tienDo = tienDo;
    }
}
