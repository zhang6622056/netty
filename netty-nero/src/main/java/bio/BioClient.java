package bio;



import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class BioClient {


    public static void main(String[] args) {
        try {
            //- 启动一个socket连接server
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",3309);
            socket.connect(socketAddress);

            //- 启动一个线程单独阻塞读取交互数据
            new Thread(new BioClientHandler(socket)).start();


            //- 监听键盘输入
            while(true){
                Scanner scanner = new Scanner(System.in);
                while(scanner.hasNextLine()){
                    String a = scanner.nextLine();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(a.getBytes());
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
