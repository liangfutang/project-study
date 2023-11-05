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
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/doc/page")
    public Result<?> getDocPage() throws IOException {
        SearchRequest request = new SearchRequest("products");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery())
                        .from(0).size(1)
                        .sort("price", SortOrder.DESC)
                        // 返回指定字段，参数1: 包含字段数组，参数2:排除字段数组
                        .fetchSource(new String[]{"title"}, new String[]{});
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println("总条数:" + response.getHits().getTotalHits().value);
        // 手动指定排序后，默认的按照得分排序就会失效，这里返回NAN
        System.out.println("最大得分:" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("id:" + hit.getId() + ",source:" + hit.getSourceAsString());
        }
        return Results.success();
    }

    @GetMapping("/doc/heightlight")
    public Result<?> getDocHeightLight() throws IOException {
        SearchRequest request = new SearchRequest("products");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false).field("description").field("title").preTags("<span style='color:red;'>").postTags("</span>");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("description", "好"))
                .highlighter(highlightBuilder);
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println("总条数:" + response.getHits().getTotalHits().value);
        System.out.println("最大得分:" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("id:" + hit.getId() + ",source:" + hit.getSourceAsString());
            // 获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")) {
                System.out.println("description高亮结果:" + highlightFields.get("description").fragments()[0]);
            }
            if (highlightFields.containsKey("title")) {
                System.out.println("title高亮结果:" + highlightFields.get("title").fragments()[0]);
            }
        }
        return Results.success();
    }

    /**
     * query:        查询精确查询 查询计算文档得分 并根据文档得分进行返回
     * filter query: 过滤查询 用来在大量数据中筛选出本地查询相关数据 不会计算文档得分 经常使用Lter query 结果进行缓存
     * 注意: 一旦使用 query 和 filterQuery es 优先执行 filter Query 然后再执行 query
     */
    @GetMapping("/doc/filterquery")
    public Result<?> getDocFilterQuery() throws IOException {
        SearchRequest request = new SearchRequest("products");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery())
                .postFilter(QueryBuilders.rangeQuery("description").gt(0).lt(12));
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println("总条数:" + response.getHits().getTotalHits().value);
        // 手动指定排序后，默认的按照得分排序就会失效，这里返回NAN
        System.out.println("最大得分:" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("id:" + hit.getId() + ",source:" + hit.getSourceAsString());
        }
        return Results.success();
    }


    @GetMapping("/aggs")
    public Result<?> getAggs() throws IOException {
        SearchRequest request = new SearchRequest("fruit");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery())
//                        .aggregation(AggregationBuilders.sum("price_sum").field("price"))
                        .aggregation(AggregationBuilders.terms("price_group").field("price"))
                        .size(0);
        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 处理聚合结果
        Aggregations aggregations = response.getAggregations();
        ParsedDoubleTerms parseDoubleTerms = aggregations.get("price_group");
        List<? extends Terms.Bucket> buckets = parseDoubleTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey() + " " + bucket.getDocCount());
        }
        return Results.success();
    }
}
