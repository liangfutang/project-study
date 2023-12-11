package com.zjut.study.mongo.boot.service;

import com.zjut.study.mongo.boot.entity.User;

public interface UserTemplateService {
    User insert();

    User selectById(String id);
}
