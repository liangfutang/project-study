# 一. NIO
non-blocking io 非阻塞 IO
## 1. 三大组件
### 1.1 Channel & Buffer
channel 类似 stream，是读写数据的**双向通道**，可以从 channel 将数据读入 buffer，也可以将 buffer 的数据写入 channel，而之前的 stream 要么是输入，要么是输出，channel 比 stream 更为底层
```mermaid
graph LR
channel --> buffer
buffer --> channel
```

常见的 Channel 有：
* FileChannel  (做文件的数据传输通道)
* DatagramChannel   (做UDP网络编程时的数据传输通道)
* SocketChannel   (做TCP网络编程数据传输通道，客户端和服务器端都能用)
* ServerSocketChannel   (做TCP网络编程数据传输通道，专用于服务器端)

buffer 则用来缓冲读写数据，常见的 buffer 有：
* ByteBuffer   (最常用)
    * MappedByteBuffer
    * DirectByteBuffer
    * HeapByteBuffer
* ShortBuffer
* IntBuffer
* LongBuffer
* FloatBuffer
* DoubleBuffer
* CharBuffer

### 1.2 Selector
selector 单从字面意思不好理解，需要结合服务器的设计演化来理解它的用途
#### 多线程版设计
```mermaid
graph TD
subgraph 多线程版
t1(thread) --> s1(socket1)
t2(thread) --> s2(socket2)
t3(thread) --> s3(socket3)
end
```
#### ⚠️ 多线程版缺点
* 内存占用高
* 线程上下文切换成本高
* 只适合连接数少的场景

#### 线程池版设计
```mermaid
graph TD
subgraph 线程池版
t4(thread) --> s4(socket1)
t5(thread) --> s5(socket2)
t4(thread) -.-> s6(socket3)
t5(thread) -.-> s7(socket4)
end
```
#### ⚠️ 线程池版缺点
* 阻塞模式下，线程仅能处理一个 socket 连接
* 仅适合短连接场景

#### selector 版设计
selector 的作用就是配合一个线程来管理多个 channel，获取这些 channel 上发生的事件，这些 channel 工作在非阻塞模式下，不会让线程吊死在一个 channel 上。适合连接数特别多，但流量低的场景（low traffic）
```mermaid
graph TD
subgraph selector 版
thread --> selector
selector --> c1(channel)
selector --> c2(channel)
selector --> c3(channel)
end
```
调用 selector 的 select() 会阻塞直到 channel 发生了读写就绪事件，这些事件发生，select 方法就会返回这些事件交给 thread 来处理

## 2. ByteBuffer
