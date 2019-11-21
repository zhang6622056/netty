package niodemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;




/***
 *
 * 与demo4类似
 * @author Nero
 * @date 2019-11-21
 * *@param: null
 * @return 
 */
public class Demo8 {


    public static String source = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/demo8source.txt";
    public static String des = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/demo8des.txt";




    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(des);


        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel desChannel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        while(true){
            //- 每一次写入完了之后都要重置,不然永远会死循环。因为position和limit重合了。
            byteBuffer.clear();


            int isEnd = sourceChannel.read(byteBuffer);
            if (isEnd == -1) break;

            byteBuffer.flip();
            desChannel.write(byteBuffer);
        }









    }



}
