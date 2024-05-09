package com.imchatserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.imchatserver.api.Result;
import com.imchatserver.entity.Contact;
import com.imchatserver.entity.User;
import com.imchatserver.mapper.ContactMapper;
import com.imchatserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ContactMapper contactMapper;

    @GetMapping("/getcontacts")
    public Result<List<User>> GetContacts(String jobno) {
        List<Contact> contacts = contactMapper.selectList(new QueryWrapper<Contact>().eq("uno", jobno));
        List<User> users = new ArrayList<>();
        contacts.forEach(contact -> {
            users.add(userMapper.selectOne(new QueryWrapper<User>().eq("jobno", contact.getCno())));
        });
        contacts = contactMapper.selectList(new QueryWrapper<Contact>().eq("cno", jobno));
        contacts.forEach(contact -> {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("jobno", contact.getUno()));
            if (!users.contains(user)) users.add(user);
        });
        return Result.success(users);
    }

    @PostMapping("/addcontact")
    public Result<Contact> AddContact(String uno, String cno) {
        Contact contact = new Contact(uno, cno);
        contactMapper.insert(contact);
        return Result.success(contact);
    }

    @PutMapping("/agree")
    public Result<Contact> ConsentRequest(String uno, String cno) {
        Contact contact;
        if (contactMapper.selectCount(new QueryWrapper<Contact>().eq("uno", uno).eq("cno", cno)) == 0) {
            String temp = uno;
            uno = cno;
            cno = temp;
        }
        contactMapper.update(null, new UpdateWrapper<Contact>().eq("uno", uno).eq("cno", cno).set("agree", true));
        contact = new Contact(uno, cno, true);
        return Result.success(contact);
    }

    @DeleteMapping("/forget")
    public Result<Contact> ForgetContact(String uno, String cno) {
        contactMapper.delete(new QueryWrapper<Contact>().eq("uno", uno).eq("cno", cno));
        contactMapper.delete(new QueryWrapper<Contact>().eq("uno", cno).eq("cno", uno));
        return Result.success(null);
    }
}
