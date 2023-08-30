
# 核心组件
## group()
**EventLoop：**  
一个 EventLoop 相当于一个子反应器（SubReactor），一个 NioEventLoop 子反应器拥有一个事件轮询线程。    
每一个 NioEventLoop 拥有一个 Java NIO Selector以及一个 taskQueue ，Selector 用于实现 I/O 多路复用，taskQueue 是在任务提交的速度大于线程的处理速度的时候起到缓冲作用，或者用于异步处理 Selector 监听到的 IO 事件。如果 Handler 有一些长时间的业务处理，可以交给 taskQueue 异步处理。  
同时 Selector 以及 taskQueue 共用一个线程，用单线程的方式串行执行队列中的 task。   
线程启动时会调用NioEventLoop的run方法，执行I/O任务和非I/O任务：

I/O任务：  
即selectionKey中ready的事件，如accept、connect、read、write等，由processSelectedKeys方法触发。    
非IO任务：    
添加到taskQueue中的任务，如register0、bind0等任务，由runAllTasks方法触发。     
两种任务的执行时间比由变量ioRatio控制，默认为50，则表示允许非IO任务执行的时间与IO任务的执行时间相等。   

**EventLoopGroup（NioEventLoopGroup）**    
多个 EventLoop 线程放在一起，组成一个 EventLoopGroup管理EventLoop生命周期。EventLoopGroup 提供 next 接口，可以从组里面按照一定规则（如轮询）获取其中一个 EventLoop 来处理任务。  
为了及时接收到新连接，在服务器端，一般有两个独立的反应器，一个负责新连接的监听和接收，另一个负责 IO 事件轮询和分发。对应到 Netty 的服务器程序中，则需要设置两个 EventLoopGroup 线程组，一个 EventLoopGroup 负责新连接的监听和接收——Boss 线程组；另一个负责 IO 事件轮询和分发，并执行 Handler 处理器中的业务处理——Worker 线程组。
