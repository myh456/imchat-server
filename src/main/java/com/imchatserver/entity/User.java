package com.imchatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("user")
@Data
public class User {
    private String jobno;
    private String nickname;
    private String password;
    private String pic;
    private Timestamp creatime;
}
