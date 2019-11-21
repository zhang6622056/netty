package niodemo;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 *
 * 将文本中的数据读取，输出到buffer中
 * @author Nero
 * @date 2019-11-20
 * *@param: null
 * @return 
 */
public class Demo2 {


    public static String filePath = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/filetext.txt";



    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);

        //- 切换为读模式
        byteBuffer.flip();



        while(byteBuffer.hasRemaining()){
            System.out.println((char)byteBuffer.get());
        }


        fileInputStream.close();
    }





}
