package com.zjut.study.mongo.boot.service.impl;

import com.zjut.study.mongo.boot.dao.repository.UserRepositoryDao;
import com.zjut.study.mongo.boot.entity.User;
import com.zjut.study.mongo.boot.service.UserRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepositoryDao userRepositoryDao;

    @Override
    public User findById(String id) {
        return userRepositoryDao.findById(id).orElseGet(() -> null);
    }

    @Override
    public List<User> findAll() {
        return userRepositoryDao.findAll();
    }

    @Override
    public User save(User user) {
        return userRepositoryDao.save(user);
    }
}
