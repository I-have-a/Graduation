package com.example.graduate.pojo;

import java.util.Date;

public class Node {
    private Long id;            //id
    private String title;       //标题
    private Date startTime;     //开始时间
    private Integer complexity; //复杂度

    public Node(Long id, String title, Date startTime, Integer complexity) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.complexity = complexity;
    }

    public Node() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }
}
