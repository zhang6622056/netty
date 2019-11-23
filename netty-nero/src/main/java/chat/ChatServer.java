package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {


    public static void main(String[] args) throws IOException {
        new ChatServer().start();
    }




    //- 事件轮训器
    private Selector selector;

    //- 服务端socketchannel
    private ServerSocketChannel serverSocketChannel;


    public ChatServer() throws IOException {
        //- 服务端channel
        serverSocketChannel = ServerSocketChannel.open();
        //- 轮询器对象
        selector = Selector.open();
        //- 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9090));
        //- 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //- 注册socket以及事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("server ready...");
    }



    public void start() throws IOException {
        while(true){
            //- 阻塞询问
            selector.select();

            //- 获取本次所有事件
            Set<SelectionKey> events = selector.selectedKeys();
            Iterator<SelectionKey> keys = events.iterator();
            while(keys.hasNext()){
                SelectionKey currentKey = keys.next();
                if (currentKey.isAcceptable()){ //- 连接事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println(socketChannel.toString()+"online now...");
                }

                if (currentKey.isReadable()){   //- 读取事件
                    SocketChannel socketChannel = (SocketChannel) currentKey.channel();
                    //- 申请buffer，默认写模式
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int readcount = socketChannel.read(byteBuffer);

                    //- bytebuffer转换byte数组
                    byteBuffer.flip();
                    byte[] bytes = new byte[readcount];
                    byteBuffer.get(bytes,0,readcount);
                    String str = new String(bytes,"utf-8");



                    //- 广播
                    Set<SelectionKey> selectionKeySet = selector.keys();
                    for (SelectionKey oneKey : selectionKeySet){
                        if (oneKey.isValid()){
                            SelectableChannel boardChannel = oneKey.channel();
                            if (boardChannel instanceof SocketChannel){
                                SocketChannel trueBoardChannel = (SocketChannel) boardChannel;

                                //- 如果是传输过来的socket，则不转发请求
                                if (trueBoardChannel == socketChannel){
                                    continue;
                                }
                                ByteBuffer sendBuffer = ByteBuffer.wrap(str.getBytes());
                                trueBoardChannel.write(sendBuffer);
                            }
                        }
                    }
                }
            }
            //- 处理完本次事件之后，要删除事件，否则会一直轮训
            keys.remove();

        }
    }




}
