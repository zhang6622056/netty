package masterslave;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;




/***
 *
 * boss线程初始化工作
 * @author Nero
 * @date 2019-11-23
 * *@param: null
 * @return 
 */
public class Reactor implements Runnable{


    private ServerSocketChannel serverSocketChannel;
    private Selector selector;


    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
    }


    public Reactor(int port) throws IOException {

        //- 初始化server
        serverSocketChannel = ServerSocketChannel.open();
        selector = Selector.open();
        serverSocketChannel.configureBlocking(false);


        //- 添加附件对象，该对象最终处理事件逻辑
        Accepter accepter = new Accepter(serverSocketChannel);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,accepter);


        //- 绑定端口
        InetSocketAddress addr = new InetSocketAddress(port);
        serverSocketChannel.bind(addr);
    }







    /****
     *
     * 委派处理事件。
     * @author Nero
     * @date 2019-11-23
     * *@param:
     * @return void
     */
    @Override
    public void run() {
        while(true){   //- 在线程收到中断信号前保持运行
            try{
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();

                    //- 遍历处理事件
                    while(iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isValid()){
                            if (selectionKey.isAcceptable()){
                                //- 由附加对象处理事件
                                Runnable runnable = (Runnable) selectionKey.attachment();
                                runnable.run();
                            }
                        }
                    }

                    //- 清空已处理的事件
                    iterator.remove();
                }catch(Exception e){
                    e.printStackTrace();
                 }
        }
    }
}
