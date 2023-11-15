package com.example.graduate.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long id;        //id
    private String message; //消息体
    private Long cuID;      //创建者id
    private Long ruID;      //接收者id
    private Integer status; //消息状态
}
