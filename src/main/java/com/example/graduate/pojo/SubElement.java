package com.example.graduate.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubElement {
    private Integer id;                 //id
    private Long eID;                   //父级事件
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
    private Long auID;                  //完成者id
    private Long cuID;                  //创建者id
    private List<Node> nodes;           //节点集
}
