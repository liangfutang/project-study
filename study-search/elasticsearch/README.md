# 核心概念
**概念**  
```
es中重要概念类比mysql中的概念如下：   
索引(index)    --> 表  
映射(mapping)  --> 表结构  
文档(document) --> 数据  
```

**数据类型**  
只有text类型是分词的，其他都不分词。
```
字符串类型：keyword(不分词) text(分词)
整数类型：integer long
小数类型：float double
布尔类型：boolean
日期类型：date
```

**倒排索引**   
注意: Elasticsearch分别为每个字段都建立了一个倒排索引。因此查询时查询字段的term,就能知道文档ID，就能快速找到文档。    
索引区：存储所有字段和分词后的所有词，每个存储会记录(所在文档id:出现次数:分词前字段总长度)，返回结果会计算次数和总长的比例，从而排序     
元数据区：存储原始文档数据   

**分词器**     
分析器 (analyzer) 都由三种构件组成的:character filters, tokenizers, token filters。   
character filter 字符过滤器       
。在一段文本进行分词之前，先进行预处理，比如说最常见的就是，过滤html标签(<span>mello<span> --> hello) ，& --> and (&you -->land you)                    
tokenizers 分词器      
。英文分词可以根据空格将单词分开,中文分词比较复杂,可以采用机器学习算法来分词。                
Token filters Token过滤器                 
将切分的单进行加工。大小写转换 (例将“Quick”转为小写) ，去掉停用词 (例如停用词像“a”、“and”、“the”等等) ，加入同义词 (例如同义词像“jump”和“leap”)      
注意:      
三者顺序: Character Filters--->Tokenizer--->Token Filter            
三者个数: Character Filters (0个或多个) +Tokenizer + Token Filters(0个或多个)

内置分词器：   
```
·Standard Analyzer- 默认分词器，英文按单词词切分，并小写处理
·Simple Analyzer- 按照单词切分(符号被过滤),小写处理
·Stop Analyzer-小写处理，停用词过滤(the,a,is)
·Whitespace Analyzer - 按照空格切分，不转小写
·Keyword Analyzer- 不分词，直接将输入当作输出
```
内置分词器测试   
。桥准分词器   
特点: 按照单词分词 英文统一转为小写 过滤标点符号 中文单字分词
```
POST /_analyze
{
    "analyzer":standard
    "text":“this is a ，good Man 中华人民共和国
}
```
Simple 分词器   
。特点: 按照单词分词 英文统一转为小写 去掉符号 中文不分词
```
POST /_analyze
{
    "analyzer":"simple"
    "text":"this is a ， good Man 中华人民共和国"
}
```

ik分词器    
地址: https://github.com/medcl/elasticsearch-analysis-ik   
IK有两种颗粒度的拆分:   
+ ik_smart:会做最粗粒度的拆分
+ ik_max_word:会将文本做最细粒度的拆分      
```
POST /_analyze
{
  "analyzer": "ik_smart",
  "text": ["中华人民共和国国歌"]
}
``` 
测试
```
PUT /test
{
  "mappings": {
    "properties": {
      "title": {
        "type": "text",
        "analyzer": "ik_max_word"
      }
    }
  }
}

PUT /test/_doc/1
{
  "title": "我是中国人，this is good Man"
}

GET /test/_search
{
  "query": {
    "term": {
      "title": {
        "value": "中国"
      }
    }
  }
}
```
扩展词、停用词配置   
IK支持自定义 扩展词典和 停用词典     
扩展词典 就是有些词并不是关键词,但是也希望被ES用来作为检索的关键词,可以将这些词加入扩展词典。     
停用词典 就是有些词是关键词,但是出于业务场景不想使用这些关键词被检索到，可以将这些词放入停用词典。     
定义扩展词典和停用词典可以修改IK分词器中 config 目录中 IKAnalyzer.cfg.xml 这个文件。
```
1. 修改vim IKAnalyzer.cfg.xml
<?xml version="1.”encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties .dtd">
<properties>
    <comment>IK Analyzer 扩展配置</comment>
    <!--用户可以在这里配置自己的扩展字典 -->
    <entry key="ext_dict">ext_dict.dic</entry>
    <!--用户可以在这里配置自己的扩展停止词字典-->
    <entry key="ext_stopwords">ext_stopword.dic</entry>
</properties>
2. 在ik分词器目录下config目录中创建ext_dict.dic文件编码一定要为UTF-8才能生效vim ext_dict.dic 加入扩展词即可
3. 在ik分词器目录下config目录中创建ext_stopword.dic文件vim ext_stopword.dic 加入停用词即可
4. 重启es生效(必须)
```

**过滤查询**    
过滤查询<filter query>，其实准确来说，ES中的查询操作分为2种: 查询(query)和过滤(filter)。
查询即是之前提到的 query查询，它(查询)默认会计算每个返回文档的得分，然后根据得分排序。而 过滤(filter) 只会筛选出符合的文档，并不计算 得分，而且它可以缓存文档。
所以，单从性能考虑，过滤比查询更快。 换句话说**、过滤适合在大范围筛选数据，而查询则适合精确匹配数据。一般应用时， 应先使用过滤操作过滤数据，然后使用查询匹配数据。
常见过滤类型有：term 、terms 、ranage、exists、ids等filter。
```
GET /products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match_all": {}
        }
      ],
      "filter": [
        {
          "term": {
            "description": "可乐"
          }
        }
      ]
    }
  }
}
```

**聚合查询:**   
聚合:英文为Aggregation Aggs，是es除搜索功能外提供的针对es数据做统计分析的功能聚合有助于根据搜索查询提供聚合数据。
聚合查询是数据库中重要的功能特性，ES作为搜索引擎兼数据库，同样提供了强大的聚合分析能力。它基于查询条件来对数据进行分桶、计算的方法。
有点类似于 SQL中的 group by 再加一些函数方法的操作。    
注意事项: text类型是不支持聚合的。
```PUT /fruit
# 添加索引
{
  "mappings": {
    "properties": {
      "title":{
        "type": "keyword"
      },
      "price": {
        "type": "double"
      },
      "description":{
        "type": "text",
        "analyzer": "ik_max_word"
      }
    }
  }
}
# 添加模拟数据
PUT /fruit/_bulk
{"index":{}}
  {"title":"面包","price": 19.9,"description":"小面包非常好吃"}
{"index":{}}
  {"title":"旺仔牛奶","price": 29.9,"description":"非常好喝"}
{"index":{}}
  {"title":"日本豆","price": 19.9,"description":"日本豆非常好吃"}
{"index":{}}
  {"title":"小馒头","price": 19.9,"description":"小馒头非常好吃"}
{"index":{}}
  {"title":"大辣片","price": 39.9,"description":"大辣片非常好吃"}
{"index":{}}
  {"title":"透心凉","price": 9.9,"description":"透心凉非常好喝"}
{"index":{}}
  {"title":"小浣熊","price": 19.9,"description":"童年的味道"}
{"index":{}}
{"title":"海苔","price": 19.9,"description":"海的味道"}

# 按照价格聚合
GET /fruit/_search
{
  "query": {
    "match_all": {}
  },
  "size": 0,
  "aggs": {
    "price_group": {
      "terms": {
        "field": "price"
      }
    }
  }
}

# 价格最高
GET /fruit/_search
{
  "query": {
    "match_all": {}
  },
  "size": 0,
  "aggs": {
    "max_group": {
      "max": {
        "field": "price"
      }
    }
  }
}
```
