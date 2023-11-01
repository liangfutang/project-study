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

+ 查询所有
```
GET /products/_doc/_search
GET /products/_search
{
  "query": {
    "match_all": {}
  }
}
```

+ term 基于关键词查询    
keyword integer double date 不分词   
text类型 默认 es 标准分词器 中文单字分词 英文单词分词   
1.在 Es 中除了 text 类型分词其余类型均不分词   
2.在 Es 中默认使用标准分词器 中文单字分词 英文 单词分词   
```
GET /products/_search
{
  "query": {
    "term": {
      "title": {
        "value": "方便面"
      }
    }
  }
}
```

+ 范围查询(range)     
range关键字： 用来查指定范围内的文档
```
GET /products/_search
{
  "query": {
    "range": {
      "price": {
        "gte": 0,
        "lte": 8
      }
    }
  }
}
```

+ 前缀查询(prefix)      
prefix关键字： 检索含有指定前缀的关键词的文档
```
GET /products/_search
{
  "query": {
    "prefix": {
      "title": {
        "value": "方"  # 如果是keyword可前缀，如果是text则因为分词了，得符合分词后的所有词，如标准分词器的某个字
      }
    }
  }
}
```

+ 通配符查询[wildcard]   
wildcard关键字：? 用来匹配一个任意字符  * 用来匹配多个任意字符
```
GET /products/_search
{
  "query": {
    "wildcard": {
      "description": {
        "value": "方*"    # text得是分词后的
      }
    }
  }
}
```

+ 多id查询[ids]     
ids关键字:值为数组类型,用来根据一组id获取多个对应的文档
```
GET /products/_search
{
  "query": {
    "ids": {
      "values": [1,2,3,4,5]
    }
  }
}
```

+ 模糊查询[fuzzy]    
fuzzy 关键字:用来模糊查询含有指定关键字的文档    
注意: fuzzy 模糊查询最大模糊错误 必须在0-2之间     
 搜索关键词长度为2不允许存在模糊   
 搜索关键词长度为3-5 允许一次模糊   
 搜索关键词长度大于5 允许最大2模糊   
```
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "方便包"
    }
  }
}
```

+ 布尔查询[bool]    
bool关键字:用来组合多个条件实现复杂查询    
must: 相当于&& 同时成立    
should: 相当于|| 成立一个就行    
must_not: 相当于!不能满足任何一个
```
GET /products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "ids": {
            "values": [1]
          }
        },{
          "term": {
            "title": {
              "value": "方便面"
            }
          }
        }
      ]
    }
  }
}
```

+ 多字段查询[multi_match]     
注意:如字段类型分词,将对查询条件分词之后进行查询该字段 如该字段不分词就会将查询条件作为整体进行查询     
```
GET /products/_search
{
  "query": {
    "multi_match": {
      "query": "kk可乐",
      "fields": ["title", "description"]
    }
  }
}
```

+ 默认字段分词查询[query_string]    
注意: 查询字段分词就将查询条件分词查询 查询字段不分词将查询条件不分词查询
```
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "喜欢喝可乐"
    }
  }
}
```

+ 高亮查询[highlight]     
highlight 关键字:可以让符合条件的文档中的关键词高亮     
自定义高亮html标签: 可以在highlight中使用 `pre_tags` 和 `post_tags`
```
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "喜欢喝可乐"
    }
  },
  "highlight": {
    "pre_tags": ["<span style='color:red;'>"],
    "post_tags": ["</span>"],
    "require_field_match": "false",
    "fields": {
      "*": {}
    }
  }
}
```

+ 返回指定条数[from][size]     
from 关键字:用来指定起始返回位置，和size关键字连用可实现分页效果   
size 关键字: 指定查询结果中返回指定条数。 默认返回值10条
```
GET /products/_search
{
  "query": {
    "match_all": {}
  },
  "size": 5,
  "from": 0
}
```

+ 指定字段排序[sort]      
```
GET /products/_search
{
  "query": {
    "match_all": {}
  },
  "size": 5,
  "from": 0,
  "sort": [
    {
      "price": {
        "order": "desc"
      }
    }
  ]
}
```

+ 返回指定字段[_source]    
_source 关键字:是一个数组在数组中用来指定展示那些字段  
```
GET /products/_search
{
  "query": {
    "match_all": {}
  },
  "size": 5,
  "from": 0,
  "sort": [
    {
      "price": {
        "order": "desc"
      }
    }
  ],
  "_source": ["id","title","description"]
}
```
