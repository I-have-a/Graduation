package com.example.graduate.pojo;

import java.util.Date;
import java.util.List;

public class SubElement {
    private Integer id;                 //id
    private String title;               //标题
    private String description;         //描述
    private Date foundDate;             //创建时间
    private Date startTime;             //开始时间
    private Date endTime;               //结束时间
    private Date pStartTime;            //预计开始时间
    private Date pEndTime;              //预计结束时间
    private boolean deleteFlag;         //删除标志位
    private Integer accomplishStatus;   //完成状态
    private Integer complexity;         //复杂度
    private User auID;                  //完成者id
    private User cuID;                  //创建者id
    private List<Node> nodes;           //节点集

    public SubElement() {
    }

    public SubElement(Integer id, String title, String description, Date foundDate, Date startTime, Date endTime, Date pStartTime, Date pEndTime, boolean deleteFlag, Integer accomplishStatus, Integer complexity, List<Node> nodes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.foundDate = foundDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pStartTime = pStartTime;
        this.pEndTime = pEndTime;
        this.deleteFlag = deleteFlag;
        this.accomplishStatus = accomplishStatus;
        this.complexity = complexity;
        this.nodes = nodes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getpStartTime() {
        return pStartTime;
    }

    public void setpStartTime(Date pStartTime) {
        this.pStartTime = pStartTime;
    }

    public Date getpEndTime() {
        return pEndTime;
    }

    public void setpEndTime(Date pEndTime) {
        this.pEndTime = pEndTime;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAccomplishStatus() {
        return accomplishStatus;
    }

    public void setAccomplishStatus(Integer accomplishStatus) {
        this.accomplishStatus = accomplishStatus;
    }

    public User getAuID() {
        return auID;
    }

    public void setAuID(User auID) {
        this.auID = auID;
    }

    public User getCuID() {
        return cuID;
    }

    public void setCuID(User cuID) {
        this.cuID = cuID;
    }
}
