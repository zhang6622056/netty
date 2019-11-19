package nerojedis;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



/***
 *
 * redis 客户端实际交互socket对象
 * @author Nero
 * @date 2019-11-19
 * *@param: null
 * @return 
 */
public class NeroJedisSocket {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;





    public NeroJedisSocket(String host,int port) {
        try {
            this.socket = new Socket(host,port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 发送
     * @author Nero
     * @date 2019-11-18
     * @param: command
     * @param: bytes
     * @return void
     */
    public void send(Command command,byte[]... bytes){
        StringBuilder stringBuilder = new StringBuilder();
        //- 指令组
        stringBuilder.append(Resp.GROUPLENTH).append(1+bytes.length).append(Resp.LINE);

        //- 指令长度和指令
        stringBuilder.append(Resp.COMMANDLEN).append(command.toString().length()).append(Resp.LINE);
        stringBuilder.append(command.toString()).append(Resp.LINE);

        //- 参数
        for (byte[] byteContent : bytes){
            stringBuilder.append(Resp.COMMANDLEN).append(byteContent.length).append(Resp.LINE);
            stringBuilder.append(new String(byteContent)).append(Resp.LINE);
        }


        try {
            String commandsend = stringBuilder.toString();
            outputStream.write(commandsend.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     *
     * 读取
     * @author Nero
     * @date 2019-11-18
     * *@param:
     * @return java.lang.String
     */
    public String read(){
        byte[] readByte = new byte[1024];
        int count = 0;

        try {
            count = inputStream.read(readByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(readByte,0,count);
    }







    //- 操作指令定义，主要用来发送resp协议相关数据
    public static enum Command{
        SET,GET,INCR;
    }




}
