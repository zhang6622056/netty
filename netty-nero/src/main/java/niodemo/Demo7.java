package niodemo;


import java.nio.ByteBuffer;

/***
 *
 * readonly buffer为只读buffer，它内部的数据随着原buffer变动。也不需要翻转它的读写模式
 * @author Nero
 * @date 2019-11-21
 * *@param: null
 * @return
 */
public class Demo7 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        ByteBuffer byteBufferReadOnly = byteBuffer.asReadOnlyBuffer();

        for (int i = 0 ; i < 20 ; i++){
            byteBuffer.put((byte) i);
        }


        while(byteBufferReadOnly.hasRemaining()){
            System.out.println(byteBufferReadOnly.get());
        }
    }



}
