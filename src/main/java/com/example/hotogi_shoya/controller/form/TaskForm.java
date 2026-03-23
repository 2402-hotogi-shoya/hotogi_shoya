package com.example.hotogi_shoya.controller.form;

import java.time.LocalDateTime;

public class TaskForm {

    private int id;
    private String content;
    private String status;
    private LocalDateTime limit_date;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(LocalDateTime limit_date) {
        this.limit_date = limit_date;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(LocalDateTime updated_date) {
        this.updated_date = updated_date;
    }
}

