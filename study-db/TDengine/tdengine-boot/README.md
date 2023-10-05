# 运行表数据等操作
1. 创建库  
通过GUI界面，可以可视化的操作
```sql
CREATE DATABASE IF NOT EXISTS 'td' KEEP 3650 UPDATE 2;
```

2. 建超级表  
子表名使用 超级表名 + "_" + 子表专属唯一属性(如城市id)  即: `weather_32`
```sql
CREATE STABLE IF NOT EXISTS td.'weather' (`ts` TIMESTAMP, `temperature` FLOAT, `humidity` FLOAT, `note` NCHAR(32)) TAGS(`location` NCHAR(32));
```

3. 新增测试数据  
如果设置了数据保存的keep时间，则输入数据的时间戳不能超过该时间否则报错`Timestamp data out of range`
```sql
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:32.272', 10.2, 21, '晴');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:34.272', 11.2, 22, '多云');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:36.272', 12.2, 23, '小雨');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:38.272', 13.2, 22, '中雨');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:40.272', 14.2, 20, '大雨');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:45.272', 9.2, 19, '阵雨');
INSERT INTO td.weather_32 USING td.weather TAGS ('江苏-南京') (`ts`, `temperature`, `humidity`, `note`) VALUES ('2021-07-13 14:06:50.272', 8.2, 18, '晴');
```
