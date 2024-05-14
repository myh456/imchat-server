package com.imchatserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imchatserver.api.AESUtil;
import com.imchatserver.api.Result;
import com.imchatserver.entity.Messages;
import com.imchatserver.mapper.MessagesMapper;
import com.imchatserver.mapper.UserMapper;
import com.imchatserver.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MessagesMapper messagesMapper;
    @Autowired
    KeyService keyService;

    @GetMapping("/getmessages")
    public Result<List<Messages>> GetMessages(String uno, String cno) {
        List<Messages> messages = messagesMapper.selectList(new QueryWrapper<Messages>().eq("uno", uno).eq("cno", cno));
        messages.addAll(messagesMapper.selectList(new QueryWrapper<Messages>().eq("uno", cno).eq("cno", uno)));
        messages.sort(Comparator.comparing(Messages::getPostime));
        messages.forEach(message -> {
            try {
                message.setText(AESUtil.encrypt(AESUtil.decrypt(message.getText(), message.getPostime()), keyService.getkey(uno)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return Result.success(messages);
    }

    @PostMapping("/addmessage")
    public Result<Messages> AddMessage(String uno, String cno, String text) throws Exception {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Messages message = new Messages(uno, cno, AESUtil.encrypt(AESUtil.decrypt(text, keyService.getkey(uno)), timestamp), timestamp);
        messagesMapper.insert(message);
        return Result.success(message);
    }
}
