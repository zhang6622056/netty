package niodemo;

import java.nio.ByteBuffer;







/***
 *
 * 如果使用slice方法，那么生成的buffer对应将和之前的buffer对象的属性是一致的。
 * @author Nero
 * @date 2019-11-21
 * *@param: null
 * @return 
 */
public class Demo6 {


    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        for (int i = 0 ; i < byteBuffer.capacity(); i++){
            byteBuffer.put((byte) i);
        }




        //- 切换为读模式
        byteBuffer.flip();
        byteBuffer.position(2);
        ByteBuffer resetByteBuffer = byteBuffer.slice();


        while(resetByteBuffer.hasRemaining()){
            System.out.println(resetByteBuffer.get());
        }


















    }







}
