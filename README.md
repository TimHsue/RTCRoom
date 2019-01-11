# 即时通信系统

#### 一、通信

1. **数据传输**

   ​	在Socket进行传输时flush只会刷新缓冲区，而不会使read结束，read只有在读取到EOF时才会结束，除非调用close()强行结束输出流分两次发送，这又与我们所需要的长连接长轮询相悖。所以连续两次发送的信息在简单的read下会被视为同一条信息，而这将对命令和文本的处理以及实时更新非常不利，所以我们需要一种归一的方式，即不需要close刷新的方式，由此我们对每个数据包单独增加了结尾``$END``，调用read时规定每次只读取一个字符，直到读取到了结尾标识符则结束read方法，将标识符前内容返回，函数实现如下：

   ```JAVA
   private String read(BufferedInputStream in) {
       String tmp;
       int nowlen = 0;
       try {
           while (in.read(buffer, nowlen, 1) != -1) {
               nowlen++;
               tmp = new String(buffer, 0, nowlen);
               if(tmp.indexOf("$END") != -1) {
                   tmp = tmp.substring(0, tmp.length() - 4);
                   break;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
           return null;
       }  
   
       return tmp;
   }
   ```

2. **协议**

   ​	我们采用基于Java Socket的网络数据传输系统，对客户端和服务端的信息进行封包与发送。由于Socket只能进行文本传输，双方无法得知即将传输的具体内容，所以定义了如下规则以保证数据不会产生歧义：

- **命令提示符“$”**: 其代表下文为一段命令，任何一方接收到请求后将对其后文内容进行解析，进而执行相应的函数来完成命令，如
  - ``$SETNAME``表示客户端将要设置用户名，下文为用户名的字符串
  - ``$MESSAGE``表示一方将要传输的内容为文本，下一条消息为所发送的文本内容
  - ``$VEDIO``表示一方将要传输的内容为视频，下一条消息为视频信息，将采用视频解析函数进行解析与播放
  - ``$Success``表示消息接受成功，在需要反馈的命令执行后调用
- **文本提示符“&”**: 其代表下文为一段文本内容或命令的具体参数，任何一方接收到信息后不需对其进行解析，不被当做命令执行，如
  - 客户端用户发送数据需要传输给服务端，则先发送``$SENDMESSAGE``命令，然后发送如下形式的具体内容：``&xxxxx``
  - 服务端向用户返回其id，则可在``$Success``之后发送``&uid``

1. **数据传输实现**
   - 服务端：
     - Server主线程新建SocketServer
     - 实现threadList线程池，存放所有连接到服务器的用户
   - 客户端：
     - Client新建socket与服务器建立连接，发送连接请求
     - 之后转接到RecieveClient与SendClient分别发送本地信息到服务器与监听服务器信息

#### 二、线程

1. **Server**
   - Server主线程负责监听所有新的请求
   - 在Server监听到新的连接请求并建立连接后，新建用户进程，将用户socket转接到用户进程处理，自身继续监听
   - 在子线程中监听用户发送的数据，将用户线程的控制权复制给其所请求房间号的ChatRoom类，每次CharRoom内用户更新将调用其自身更新函数，向每个用户发送信息

1. **Client**

   - Client主线程为JavaFX线程，负责GUI的更新，同时在启动时会建立与服务器连接的ServerLinker子线程
   - ServerLinker将请求与服务器建立TCP长连接，其自身会发送由主线程获得的用户名与房间号
   - 在成功发送用户名与房间号后，ServerLinker将转移控制权到RecieveClient类与SendClient类，并以这两个类为基础建立子线程
   - RecieveClient负责从服务器获取信息然后利用接口更新给主线程
   - SendClient负责从主线程获取信息后发送给服务器

2. **GUI**

   - GUI由JavaFX实现，FX线程规定外部线程（包括子线程）禁止调用其资源，所以需要用如下代码实现外部对GUI的更新

   ```java
   Platform.runLater(new Runnable() {
       @Override
       public void run() {
           Main.myController.updateMessage(name, message);
       }
   });
   ```

#### 三、GUI

1. **聊天气泡**

   ​	实现了根据文本长度而变的聊天气泡框。聊天气泡由两个矩形两个半圆组成。从检查文本框读入的字符串，但是为了统一中英文长度，没有使用length，而是统计了byte。根据byte长度对矩形进行缩放，然后各个组件相应变化（包括长度、高度）。

#### 四、主要组件

1. **ChatRoom**

   ​	分管不同的聊天室，实现聊天室的新加入用户更新与用户信息更新

1. **IdPool**

   ​	为避免资源浪费，对所有的编码与端口调用IdPool类，如若有端口弃用，则下次新启动端口将有使用先前弃用的端口