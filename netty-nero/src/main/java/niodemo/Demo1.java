package niodemo;

import java.nio.IntBuffer;
import java.util.Random;



/***
 *
 * buffer基本输入，输出
 * @author Nero
 * @date 2019-11-20
 * *@param: null
 * @return 
 */
public class Demo1 {


    public static void main(String[] args) {
        Random random = new Random();
        IntBuffer buffer = IntBuffer.allocate(8);


        for (int i = 0 ; i < buffer.capacity() ; i++){
            buffer.put(random.nextInt(8));
        }

        //- 反转为读模式
        buffer.flip();

        //- 遍历输出buffer
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }


        //- 清空该buffer
        buffer.clear();



    }







}
