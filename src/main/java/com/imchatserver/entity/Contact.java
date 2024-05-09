package com.imchatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("contact")
@Data
public class Contact {
    private String uno;
    private String cno;
    private Boolean agree;

    public Contact() {}
    public Contact(String uno, String cno) {
        this.uno = uno;
        this.cno = cno;
    }

    public Contact(String uno, String cno, Boolean agree) {
        this.uno = uno;
        this.cno = cno;
        this.agree = agree;
    }
}
