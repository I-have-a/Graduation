package com.bi.element.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Long id;            //id
    private String title;       //标题
    private Date startTime;     //开始时间
    private Integer complexity; //复杂度
}
