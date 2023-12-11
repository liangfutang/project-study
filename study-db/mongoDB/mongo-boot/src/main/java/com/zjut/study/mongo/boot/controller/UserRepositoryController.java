package com.zjut.study.mongo.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.mongo.boot.entity.User;
import com.zjut.study.mongo.boot.service.UserRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repository")
@AllArgsConstructor
public class UserRepositoryController {

    private final UserRepositoryService userRepositoryService;

    @PostMapping("/save")
    public Result<?> save() {
        User user = new User();
        user.setId("6");
        user.setName("bbb");
        user.setAge(26);
        return Results.success(userRepositoryService.save(user));
    }

    @GetMapping("/user/{id}")
    public Result<?> selectById(@PathVariable String id) {
        return Results.success(userRepositoryService.findById(id));
    }

    @GetMapping("/user/all")
    public Result<?> selectAll() {
        return Results.success(userRepositoryService.findAll());
    }
}
