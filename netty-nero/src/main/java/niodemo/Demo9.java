package niodemo;

import java.nio.ByteBuffer;



/***
 *
 * 当使用wrap的时候，buffer内部的数据随着byte数组一起变化。
 * @author Nero
 * @date 2019-11-21
 * *@param: null
 * @return 
 */
public class Demo9 {


    public static void main(String[] args) {
        byte[] bytes = new byte[]{'a','b','c'};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        bytes[2] = 'a';
        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }





    }





}
