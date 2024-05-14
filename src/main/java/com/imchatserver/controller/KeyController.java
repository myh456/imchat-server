package com.imchatserver.controller;

import com.imchatserver.api.Result;
import com.imchatserver.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/key")
public class KeyController {
    @Autowired
    KeyService keyService;

    @PostMapping("/generate")
    public Result<String> generatekey(String jobno, String Ka ) {
        return Result.success(keyService.generate(jobno, Ka));
    }

    @GetMapping("/get")
    public Result<String> getkey(String jobno) {
        return Result.success(keyService.getkey(jobno));
    }
}
