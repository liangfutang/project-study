package com.zjut.study.mongo.boot.dao.repository;

import com.zjut.study.mongo.boot.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryDao extends MongoRepository<User, String> {


}
