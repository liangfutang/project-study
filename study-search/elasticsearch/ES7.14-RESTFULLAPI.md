# 一. 基本使用
## 1.1. 索引<index>
+ 创建索引
```
PUT /products
```
注意:
  + 1.ES中索引健康转态 red(索引不可用) 、yellow(索引可用,存在风险)、green(健康)
  + 2.默认ES在创建索引时回为索引创建1个备份索引和一个primary索引，所以在电接点下创建的索引健康状态是yellow

+ 创建索引 进行索引分片配置
```
PUT /products
{
  "settings": {
    "number_of_shards": 1
    "number_of_replicas": 0,
  }
}
```

+ 查看当前所有的索引
```
GET /_cat/indices?v
v: 返回显示结果的标题
```

## 1.2. 映射<mapping>
决定索引中所存储的文档字段及类型,映射不能删除或修改，只能删除索引后再创建   

+ 创建索引&映射
```
# 创建商品索引 products 指定 mapping {id,title,price,created_at,description}
PUT /products
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "properties": {
      "id":{
        "type": "integer"
      },
      "title":{
        "type": "keyword"
      },
      "price":{
        "type": "double"
      },
      "created_at":{
        "type": "date"
      },
      "description":{
        "type": "text"
      }
    }
  }
}
```
+ 查看某个索引的映射样  
GET /索引名/_mapping=====> GET /products/_mapping
```
GET /products/_mapping
```

## 1.3. 文档<document>
+ 创建文档
```
# 创建文档，路径中为_id，建议和body中id保持一致
POST /products/_doc/1   # 手动生成的id
{
  "id": 1,
  "title": "方便面",
  "price": 5.5,
  "created_at": "2023-11-01",
  "description": "方便面真好吃"
}

POST /products/_doc/   # 自动生成的id
{
  "id": 1,
  "title": "方便面",
  "price": 5.5,
  "created_at": "2023-11-01",
  "description": "方便面真好吃"
}
```
+ 基于id查询文档
```
GET /products/_doc/id
```
+ 基于id删除文档
```
DELETE /products/_doc/S9s1hosBD0SAGkVBE4hC
```
+ 基于id更新文档
```
# 删除原始文档再重新添加，如要保留不要修改的字段，需要带上不需要修改的字段一起
PUT /products/_doc/1
{
  "title":"小饼干"
}

# 基于指定字段更新
POST /products/_doc/1/_update
{
  "doc": {
    "price": 5.0
  }
}
```
+ 批量操作_bulk   
说明:批量时不会因为一个失败而全部失败,而是继续执行后续操作,在返回时按照执行的状态返回!
```
# 批量添加文档
POST /products/_doc/_bulk
{"index":{"_id":2}}   # 如使用自动生成的id，则拿掉这里的_id，填一个空大括号就行
  {"id":2,"title":"小面包","price":6.0,"created_at":"2023-11-02","description":"小面包真好吃"}
{"index":{"_id":3}}
  {"id":3,"title":"可乐","price":6.0,"created_at":"2023-11-03","description":"可乐真好喝"}

# 批量操作： 添加 更新 删除
POST /products/_doc/_bulk
{"index":{"_id":4}}
  {"id":3,"title":"雪碧","price":6.0,"created_at":"2023-11-04","description":"雪碧真好喝"}
{"update":{"_id":3}}
  {"doc": {"price":6.5}}
{"delete":{"_id":2}}
```

# 二. query dsl
语法：    
GET /索引名/_doc/_search {json格式请求体数据)
GET /索引名/_search {ison格式请求体数据}     #好处：再kibana上会有提示
```
GET /products/_doc/_search
GET /products/_search
{
  "query": {
    "match_all": {}
  }
}
```
