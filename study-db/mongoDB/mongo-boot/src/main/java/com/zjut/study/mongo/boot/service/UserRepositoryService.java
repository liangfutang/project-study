package com.zjut.study.mongo.boot.service;

import com.zjut.study.mongo.boot.entity.User;

public interface UserRepositoryService {

    User findById(String id);

    User save(User user);
}
