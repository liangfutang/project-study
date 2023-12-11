package com.zjut.study.mongo.boot.service.impl;

import com.zjut.study.mongo.boot.entity.User;
import com.zjut.study.mongo.boot.service.UserTemplateService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserTemplateServiceImpl implements UserTemplateService {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public User insert() {
        User user = new User();
        user.setId("8");
        user.setName("hhh");
        user.setAge(12);
        return mongoTemplate.insert(user);
    }

    @Override
    public User selectById(String id) {
        return mongoTemplate.findById(id, User.class);
    }
}
