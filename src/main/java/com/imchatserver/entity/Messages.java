package com.imchatserver.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("messages")
@Data
public class Messages {
    private String from;
    private String to;
    private String text;
    private Timestamp postime;

    public Messages() {}
    public Messages(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }
}
