package chat;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChatClient {

    private SocketChannel socketChannel;
    private Selector selector;



    public ChatClient() throws IOException {
        //- 打开一个打开一个socketchannel
        socketChannel = SocketChannel.open();
        //- 打开一个selector
        selector = Selector.open();
        //- 设置非阻塞
        socketChannel.configureBlocking(false);
    }

    //- 连接
    public void doConn() throws IOException {
        InetSocketAddress inetSocketAddress=new InetSocketAddress("127.0.0.1",9090);

        //- 监听channel的read事件
        if (socketChannel.connect(inetSocketAddress)){  //- 连接上了。
            System.out.println("connected...");
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else{      //-如果连接不上
            socketChannel.register(selector,SelectionKey.OP_CONNECT);   //-监听连接事件
        }
    }



    public void loopEvent() throws IOException {
        while(true){
            selector.select();
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator iterator = selectionKeySet.iterator();

            while(iterator.hasNext()){
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                if (selectionKey.isReadable()){
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int readcount = socketChannel.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array(),"utf-8"));
                }

                if (selectionKey.isConnectable()){  //- 没有连接上
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    if (socketChannel.finishConnect()){
                        socketChannel.register(selector,SelectionKey.OP_READ);
                        System.out.println("bbbbbbbbbbbbb");
                        //写数据
                        send(socketChannel);
                    }else{
                        System.exit(1);
                    }
                }
                //- 如果不可用，则关闭连接
                if (!selectionKey.isValid()){
                    System.exit(1);
                }



            }
           iterator.remove();
        }
    }




    public void send(SocketChannel socketChannel) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Scanner scanner=new Scanner(System.in);
                        String str = scanner.nextLine();
                        ByteBuffer byteBuffer=ByteBuffer.wrap((socketChannel.getLocalAddress().toString()+"说："+str).getBytes());
                        socketChannel.write(byteBuffer);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }






    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();   // 创建一个客户端
        // 连接服务器
        chatClient.doConn();

        //- 循环访问事件要单独的线程，因为用了while true
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    chatClient.loopEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }









}
