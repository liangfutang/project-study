package com.zjut.study.mybatis.mj.entity;

import java.io.Serializable;

/**
 * @author jack
 */
public class Comment implements Serializable {

    private String id;
    private Integer blogId;
    private String body;
    private User user;
    private Blog blog;

    public Comment(String id) {
        this.id = id;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
