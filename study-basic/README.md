
# 一. 浅拷贝&深拷贝
## 1.1 [使用`clone()`方法](src/main/java/com/zjut/study/basic/copy/CloneClient.java)
使用clone:  
① 实现Cloneable接口，这是一个标记接口，自身没有方法。  
② 覆盖clone()方法，可见性提升为public。

如果一个被复制的属性都是基本类型，那么只需要实现当前类的cloneable机制就可以了，此为浅拷贝。如果被复制对象的属性包含其他实体类对象引用，那么这些实体类对象都需要实现cloneable接口并覆盖clone()方法。

如果被复制对象不是直接继承Object，中间还有其它继承层次，每一层super类都需要实现Cloneable接口并覆盖clone()方法。与对象成员不同，继承关系中的clone不需要被复制类的clone()做多余的工作。如果实现完整的深拷贝，需要被复制对象的继承链、引用链上的每一个对象都实现克隆机制。前面的实例还可以接受，如果有N个对象成员，有M层继承关系，就会很麻烦。

## 1.2 [序列化反序列化](src/main/java/com/zjut/study/basic/copy/SerializableClient.java)
+ 被复制对象的继承链、引用链上的每一个对象都实现java.io.Serializable接口。这个比较简单，不需要实现任何方法，serialVersionID的要求不强制，对深拷贝来说没毛病。
+ 实现自己的deepClone方法，将this写入流，再读出来。

**注意:** 序列化对象中所有自定义类都要实现`Serializable`，否则会抛出异常`NotSerializableException`

## 1.3 
