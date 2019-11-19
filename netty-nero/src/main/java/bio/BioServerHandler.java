package bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class BioServerHandler implements Runnable{


    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
            while(true){
                byte[] bytes = new byte[1024];
                int readsize = socket.getInputStream().read(bytes);     //- read操作会阻塞,切勿用avalible....
                if (readsize > 0){
                    String str = new String(bytes,"UTF-8");
                    System.out.println(str);
                }

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(new String("200").getBytes());
                outputStream.write(new String("send ok!").getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
