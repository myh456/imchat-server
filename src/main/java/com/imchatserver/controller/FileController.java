package com.imchatserver.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.imchatserver.api.Result;
import com.imchatserver.entity.User;
import com.imchatserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
public class FileController {
    @Autowired
    UserMapper userMapper;

    @PostMapping("/user/changePic")
    public Result<String> changePic(@RequestPart(value = "jobno") String jobno,@RequestPart(value = "file") MultipartFile file){
        try {
            System.out.println(jobno);
            System.out.println(file);
            String staticPath = "/home/myh/Documents/MyObject/imchat-server/src/main/resources/static/";
            String classStaticPath = "/home/myh/Documents/MyObject/imchat-server/target/classes/static/";
            String filetype = ".png";
            File f = new File(classStaticPath + jobno + filetype);
            file.transferTo(f);
            File dest = new File(staticPath + jobno + filetype);
            if (dest.exists()) dest.delete();
            Files.copy(f.toPath(), dest.toPath());
            userMapper.update(null, new UpdateWrapper<User>().eq("jobno", jobno).set("pic", "/images/" + jobno + filetype));
            return Result.success("http://ip-location:8086/images/" + jobno + filetype);
        } catch (IOException e) {
            return Result.error(500, "Internal Server Error");
        }
    }
}
