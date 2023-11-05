package com.zjut.study.es.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.es.boot.entity.Product;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/rest")
@AllArgsConstructor
public class RestHighLevelClientController {

    private final RestHighLevelClient restHighLevelClient;

    @PostMapping
    public Result<?> createIndex() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest("products");
        indexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"id\":{\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"title\":{\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"price\":{\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"created_at\":{\n" +
                "        \"type\": \"date\"\n" +
                "      },\n" +
                "      \"description\":{\n" +
                "        \"type\": \"text\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        CreateIndexResponse response = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        // 关闭资源
        restHighLevelClient.close();
        return Results.success(response.isAcknowledged());
    }

    @DeleteMapping
    public Result<?> delete() throws IOException {
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(new DeleteIndexRequest("products"), RequestOptions.DEFAULT);
        return Results.success(delete.isAcknowledged());
    }

    @PostMapping("/doc")
    public Result<?> addDoc(@RequestBody Product product) throws IOException {
        IndexRequest request = new IndexRequest("products");
        request
                .id(product.getId() + "")
                .source(JSONObject.toJSONString(product), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return Results.success(response.status());
    }

    @PutMapping("/doc")
    public Result<?> updateDoc(@RequestBody Product product) throws IOException {
        UpdateRequest request = new UpdateRequest("products", product.getId()+"");
        request.doc("{\"price\": 10.5}", XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return Results.success(response.status());
    }

    @DeleteMapping("/doc")
    public Result<?> deleteDoc() throws IOException {
        DeleteResponse delete = restHighLevelClient.delete(new DeleteRequest("products", "2"), RequestOptions.DEFAULT);
        return Results.success(delete.status());
    }

    @GetMapping("/doc")
    public Result<?> getDoc() throws IOException {
        GetRequest request = new GetRequest("products", "2");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println("_id:" + response.getId());
        String source = response.getSourceAsString();
        System.out.println("source:" + source);
        return Results.success(source);
    }

    @GetMapping("/doc/all")
    public Result<?> getDocAll() throws IOException {
        SearchRequest request = new SearchRequest("products");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println("总条数:" + response.getHits().getTotalHits().value);
        System.out.println("最大得分:" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("id:" + hit.getId() + ",source:" + hit.getSourceAsString());
        }
        return Results.success();
    }

    @GetMapping("/doc/term")
    public Result<?> getDocTerm() throws IOException {
        SearchRequest request = new SearchRequest("products");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("description", "面"));
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println("总条数:" + response.getHits().getTotalHits().value);
        System.out.println("最大得分:" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("id:" + hit.getId() + ",source:" + hit.getSourceAsString());
        }
        return Results.success();
    }
}
