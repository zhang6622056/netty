package masterslave;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 *
 * worker处理类
 * @author Nero
 * @date 2019-11-23
 * *@param: null
 * @return 
 */
public class SubReactor implements Runnable{



    private Selector selector;
    private Selector[] selectors;

    public SubReactor(Selector selector,Selector[] selectors) {
        this.selector = selector;
        this.selectors = selectors;
    }


    @Override
    public void run() {
        try{
            while(true){
                selector.select();

                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();

                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isValid()){
                        if (selectionKey.isReadable()){     //- 读取事件
                            read(selectionKey);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    private void read(SelectionKey selectionKey) throws IOException {
        SelectableChannel selectableChannel = selectionKey.channel();
        if (selectableChannel instanceof SocketChannel){
            SocketChannel socketChannel = (SocketChannel) selectableChannel;


            //-接受发送到的string
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            String str = new String(byteBuffer.array());

            //- 广播该消息
            board(socketChannel,str);
        }
    }





    //- 广播该消息
    private void board(SocketChannel selfSocket,String boardStr) throws IOException {
        for (Selector selector : selectors){

            for (SelectionKey selectionKey : selector.keys()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();


                //if (socketChannel == selfSocket) {continue;}


                ByteBuffer sendBuffer = ByteBuffer.wrap(boardStr.getBytes());
                socketChannel.write(sendBuffer);
            }
        }
    }







}
