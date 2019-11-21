package niodemo;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/****
 *
 * 将某一个文件读取，写入到另外一个文件之中
 * @author Nero
 * @date 2019-11-20
 * *@param: null
 * @return 
 */
public class Demo4 {

    public static String source = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/filetext.txt";
    public static String des = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/demo4input.txt";




    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(des);

        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel desChannel = fileOutputStream.getChannel();


        //- 默认写模式
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);



        while(true){
            //- 如果缓冲区一次读取不完整，并且需要来回切换读和写模式，需要每一次clear掉，重置
            byteBuffer.clear();
            int readIndex = sourceChannel.read(byteBuffer);
            System.out.println(readIndex);
            if (readIndex == -1){break;}

            //- 反转到读模式
            byteBuffer.flip();
            desChannel.write(byteBuffer);
        }





    }








}
