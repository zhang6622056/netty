package bio;






import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



//- 服务器
public class BioServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3309);
            ServerThreadPoolHandler serverThreadPoolHandler = new ServerThreadPoolHandler(2,2,2);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println(socket.toString()+"connected....");

                //- 新启动一个线程服务单个socket
                //new Thread(new BioServerHandler(socket)).start();


                serverThreadPoolHandler.execute(new BioServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
