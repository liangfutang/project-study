package com.zjut.study.mongo.boot.service;

import com.zjut.study.mongo.boot.entity.User;

import java.util.List;

public interface UserRepositoryService {

    User findById(String id);

    List<User> findAll();

    User save(User user);
}
