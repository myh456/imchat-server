package com.imchatserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.imchatserver.component.WebSocketServer;
import com.imchatserver.entity.User;
import com.imchatserver.mapper.UserMapper;
import com.imchatserver.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public Result<User> Login(String jobno, String password) {
        System.out.println(jobno + "发出登陆请求");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("jobno", jobno));
        if (Objects.isNull(user)) {
            System.out.println(jobno + "登陆失败");
            return Result.error(404, "Unknown user");
        } else if (!Objects.equals(user.getPassword(), password)) {
            System.out.println(jobno + "登陆失败");
            return Result.error(401, "Wrong password");
        } else {
            System.out.println(jobno + "登陆成功");
            return Result.success(user);
        }
    }

    @PostMapping("/regist")
    public Result<User> Register(User user) {
        if (!Objects.isNull(userMapper.selectOne(new QueryWrapper<User>().eq("jobno", user.getJobno())))) {
            return Result.error(409, "Duplicate registration");
        } else {
            userMapper.insert(user);
            return Result.success(user);
        }
    }

    @PutMapping("/update/{param}")
    public Result<User> ChangePass(User user, @PathVariable String param) {
        userMapper.update(null, new UpdateWrapper<User>().eq("jobno", user.getJobno()).set(param, Objects.equals(param, "nickname") ? user.getNickname() : user.getPassword()));
        return Result.success(user);
    }

    @DeleteMapping("/unregist")
    public Result<User> Unregister(User user) {
        if (!Objects.equals(userMapper.selectOne(new QueryWrapper<User>().eq("jobno", user.getJobno())).getPassword(), user.getPassword())) {
            return Result.error(401, "Wrong password");
        } else {
            userMapper.delete(new QueryWrapper<User>().eq("jobno", user.getJobno()));
            return Result.success(null);
        }
    }

    @GetMapping("/selectusers")
    public Result<List<User>> SelectUsers (String jobno, String info) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().ne("jobno", jobno));
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getJobno().contains(info) && !users.get(i).getNickname().contains(info)) {
                users.remove(i);
                i--;
            }
        }
        System.out.println("Users==========>");
        System.out.println(users);
        return Result.success(users);
    }

    @GetMapping("/onlineusers")
    public Result<List<String>> GetOnlineUsers() {
        return Result.success(WebSocketServer.getOnlineUsers());
    }
}
