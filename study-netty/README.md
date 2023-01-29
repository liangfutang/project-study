# 一. NIO
non-blocking io 非阻塞 IO
## 1. 三大组件
### 1.1 Channel & Buffer
channel 类似 stream，是读写数据的**双向通道**，可以从 channel 将数据读入 buffer，也可以将 buffer 的数据写入 channel，而之前的 stream 要么是输入，要么是输出，channel 比 stream 更为底层
