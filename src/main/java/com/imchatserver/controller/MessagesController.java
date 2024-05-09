package com.imchatserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imchatserver.api.Result;
import com.imchatserver.entity.Messages;
import com.imchatserver.mapper.MessagesMapper;
import com.imchatserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessagesController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MessagesMapper messagesMapper;

    @GetMapping("/getmessage")
    public Result<List<Messages>> GetMessages(String uno, String cno) {
        List<Messages> messages = messagesMapper.selectList(new QueryWrapper<Messages>().eq("uno", uno).eq("cno", cno));
        messages.addAll(messagesMapper.selectList(new QueryWrapper<Messages>().eq("uno", cno).eq("cno", uno)));
        messages.sort(Comparator.comparing(Messages::getPostime));
        return Result.success(messages);
    }

    @PostMapping("/addmessage")
    public Result<Messages> AddMessage(String uno, String cno, String text) {
        Messages message = new Messages(uno, cno, text);
        messagesMapper.insert(message);
        return Result.success(message);
    }
}
