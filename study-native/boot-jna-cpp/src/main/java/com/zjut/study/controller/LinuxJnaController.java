package com.zjut.study.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.dto.StudentDTO;
import com.zjut.study.jna.SolveService;
import com.zjut.study.jna.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供测试Linux下调用动态链接库的测试接口
 */
@RestController
@RequestMapping("linux")
@Slf4j
public class LinuxJnaController {

    @Autowired(required = false)
    private SolveService solveService;

    /**
     * 简单的验证两个相加
     */
    @GetMapping("/add/{a}/{b}")
    public Result<Integer> add(@PathVariable Integer a, @PathVariable Integer b) {
        log.info("a:{},b:{}", a, b);
        int c = solveService.addNum(a, b);
        log.info("c={}", c);
        return Results.success(c);
    }

    /**
     * 通过入参回显的方式获得结果
     */
    @PostMapping("/param/callback")
    public Result<?> setCallName(@RequestBody StudentDTO student){
        log.info("接收到的学生信息:{}", student);
        StudentEntity.ByReference srf = new StudentEntity.ByReference();
        srf.age = student.getAge();
        srf.name = student.getName();
        solveService.setCallName(srf);
        log.info("库函数处理结果，name:{},age:{}", srf.name, srf.age);
        return Results.success();
    }
}
