package com.zjut.study.es.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.es.boot.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 使用 ElasticsearchOperations 方式操作es
 */
@RestController
@RequestMapping("/operation")
@AllArgsConstructor
public class ElasticsearchOperationsController {
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 新建文档
     * @param product 文档数据
     * @return 新建结果
     */
    @PostMapping
    public Result<?> save(@RequestBody Product product) {
        product.setCreatedAt(new Date());
        Product save = elasticsearchOperations.save(product);
        return Results.success(save);
    }

    @GetMapping
    public Result<?> get() {
        return Results.success(elasticsearchOperations.get("1", Product.class));
    }

    @GetMapping("/all")
    public Result<?> getALl() {
        return Results.success(elasticsearchOperations.search(Query.findAll(), Product.class));
    }

    @DeleteMapping
    public Result<?> delete() {
        Product entity = new Product();
        entity.setId(1);
        return Results.success(elasticsearchOperations.delete(entity));
    }

}
