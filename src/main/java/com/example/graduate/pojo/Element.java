package com.example.graduate.pojo;

import java.util.Date;
import java.util.List;

public class Element {

    Long id;
    private String title;               //事件名
    private String describe;            //描述
    private Date foundTime;             //创建时间
    private Date startTime;             //开始时间
    private Date endTime;               //结束时间
    private Date pStartTime;            //预计开始时间
    private Date pEndTime;              //预计结束时间
    private boolean deleteFlag;         //删除标志位
    private Integer accomplishStatus;   //完成状态
    private Integer complexity;         //难度
    private boolean share;              //多人项目？
    private User uID;                   //创建者
    private List<SubElement> subElements;//子项目集

    public Element() {
    }

    public Element(Long id, String title, String describe, Date foundTime, Date startTime, Date endTime, Date pStartTime, Date pEndTime, boolean deleteFlag, Integer accomplishStatus, Integer complexity, boolean share, User uID, List<SubElement> subElements) {
        this.id = id;
        this.title = title;
        this.describe = describe;
        this.foundTime = foundTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pStartTime = pStartTime;
        this.pEndTime = pEndTime;
        this.deleteFlag = deleteFlag;
        this.accomplishStatus = accomplishStatus;
        this.complexity = complexity;
        this.share = share;
        this.uID = uID;
        this.subElements = subElements;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
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

    public Integer getAccomplishStatus() {
        return accomplishStatus;
    }

    public void setAccomplishStatus(Integer accomplishStatus) {
        this.accomplishStatus = accomplishStatus;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public List<SubElement> getSubElements() {
        return subElements;
    }

    public void setSubElements(List<SubElement> subElements) {
        this.subElements = subElements;
    }

    public User getuID() {
        return uID;
    }

    public void setuID(User uID) {
        this.uID = uID;
    }
}
