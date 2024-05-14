package com.imchatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("messages")
@Data
public class Messages {
    private String uno;
    private String cno;
    private String text;
    private Timestamp postime;

    public Messages() {}
    public Messages(String from, String to, String text, Timestamp postime) {
        this.uno = from;
        this.cno = to;
        this.text = text;
        this.postime = postime;
    }
}
