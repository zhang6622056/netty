package masterslave;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/***
 *
 * 聊天室客户端
 * @author Nero
 * @date 2019-11-23
 * *@param: null
 * @return 
 */
public class ChatClient {






    public ChatClient() throws IOException {

        //-连接服务器
        SocketChannel socketChannel = SocketChannel.open();
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);


        //- 设定事件处理
        socketChannel.register(selector, SelectionKey.OP_READ);


        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",8888);
        if (socketChannel.connect(inetSocketAddress)){
            System.out.println("oneline now...");
        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            //- loop事件
            loop(selector,socketChannel);
        }




        //- 监听客户端输入
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        Scanner scanner = new Scanner(System.in);
                        while(scanner.hasNextLine()){
                            String str = scanner.nextLine();
                            socketChannel.write(ByteBuffer.wrap(str.getBytes()));
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }





    /***
     *
     * 循环事件
     * @author Nero
     * @date 2019-11-23
     * *@param: selector
    *@param: socketChannel
     * @return void
     */
    private void loop(Selector selector,SocketChannel socketChannel){
        //- 监听读事件
        new Thread(()->{
            try{
                while(true){
                    selector.select();
                    Set<SelectionKey> selectionKey = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKey.iterator();
                    while(iterator.hasNext()){
                        SelectionKey curKey = iterator.next();
                        if (curKey.isReadable()){
                            SocketChannel readChannel = (SocketChannel) curKey.channel();

                            //- 处理读事件
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            readChannel.read(byteBuffer);
                            System.out.println(new String(byteBuffer.array(),"utf-8"));
                        }

                        if (curKey.isConnectable()){
                            SocketChannel curChannel = (SocketChannel) curKey.channel();
                            if (curChannel.finishConnect()){
                                System.out.println("online now..");
                                socketChannel.register(selector,SelectionKey.OP_READ);
                            }
                        }
                    }
                    iterator.remove();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }
















    public static void main(String[] args) throws IOException {
        new ChatClient();
    }
}
