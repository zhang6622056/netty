package niodemo;


import java.nio.ByteBuffer;

/***
 *
 * buffer
 * 读取的数据顺序与类型 需要 放入与的顺序一致。否则不能识别读
 * @author Nero
 * @date 2019-11-20
 * *@param: null
 * @return 
 */
public class Demo5 {


    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);


        char lk = '1';
        byteBuffer.putInt(123);
        byteBuffer.putChar(lk);
        byteBuffer.putDouble(Double.valueOf(789));
        byteBuffer.putFloat(100f);
        byteBuffer.putLong(200l);
        byteBuffer.putShort(Short.valueOf("399"));
        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getFloat());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getShort());
    }



}
