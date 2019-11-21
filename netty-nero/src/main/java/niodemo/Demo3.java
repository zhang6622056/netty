package niodemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo3 {


    public static String filePath = "/Users/nero/code/opensource/netty/netty-nero/src/main/resources/output.txt";

    public static void main(String[] args) throws IOException {
        //- 申请一个bytebuffer 默认为写模式
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("qweasd".getBytes());



        //- 获取输出目的地的文件
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        FileChannel fileChannel = fileOutputStream.getChannel();

        //- 切换为读模式
        byteBuffer.flip();
        fileChannel.write(byteBuffer);


        fileOutputStream.close();
    }



}
