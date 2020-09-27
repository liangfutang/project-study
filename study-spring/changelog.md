**记录设计模式相关较大的变动** 

| version |    data    |  current code version  | comments | author |
| :-----: |  :-------: |  :------------------:  | :------: | :----: |
|  1.0.0  | 2020.09.14 |       1.0-SNAPSHOT     | 1.添加Spring模块及简单的spring容器化 | jack |
|  1.0.1  | 2020.09.17 |       1.0-SNAPSHOT     | 1.三种方式实现spring生命周期中的InitializingBean回调  2.BeanNameAware、BeanFactoryAware、ApplicationContextAware回调  3.beanaware添加setter方式的注入 | jack |
|  1.0.2  | 2020.09.21 |       1.0-SNAPSHOT     | 1.添加对FactoryBean的测试 2.添加BeanFactoryPostProcessor后置处理器 | jack |
|  1.0.3  | 2020.09.23 |       1.0-SNAPSHOT     | 1.aop简单的应用 | jack |
|  1.0.4  | 2020.09.27 |       1.0-SNAPSHOT     | 1.简单的java间的循环依赖，内部新建和注入两种方式 | jack |