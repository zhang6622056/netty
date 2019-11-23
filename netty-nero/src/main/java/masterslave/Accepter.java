package masterslave;


import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/***
 *
 * 处理事件逻辑
 * @author Nero
 * @date 2019-11-23
 * *@param: null
 * @return 
 */
public class Accepter implements Runnable{

    //- 服务端channel，用来accept连接
    private ServerSocketChannel serverSocketChannel;
    //-  cpu核心数
    private static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    //- worker线程的selectors
    private Selector[] selectors = new Selector[CPU_CORES];
    //- 用于分配注册selector，worker channel
    private int selectorIndex = 0;








    /***
     *
     * 主线程的附加对象，主要用来处理连接
     * @author Nero
     * @date 2019-11-23
     * @return
     */
    public Accepter(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
        //- 创建worker线程
        for (int i = 0 ; i < CPU_CORES ; i++){
            try{
                selectors[i] = Selector.open();
                SubReactor subReactor = new SubReactor(selectors[i],selectors);
                new Thread(subReactor).start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }







    /***
     *
     * 加锁保证selectorIndex 并发
     * @author Nero
     * @date 2019-11-23
     * *@param:
     * @return void
     */
    @Override
    public synchronized void run() {
        try{
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            //- 注册读事件到对应的selector上
            //- 不是该selector上面的channel发生的事件，这时候boss线程要注册只能手动wakeup
            selectors[selectorIndex].wakeup();
            socketChannel.register(selectors[selectorIndex], SelectionKey.OP_READ);
            selectors[selectorIndex].wakeup();



            //- 为了分配均匀。如果分配到最后一个selector，则重置该下标
            if (++selectorIndex == CPU_CORES){ selectorIndex = 0; }

            System.out.println(socketChannel.toString()+"online now...");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
