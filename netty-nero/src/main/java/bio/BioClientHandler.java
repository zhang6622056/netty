package bio;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class BioClientHandler implements Runnable {


    private Socket socket;


    public BioClientHandler(Socket socket) {
        this.socket = socket;
    }



    public void run() {
        while(true){
            byte[] bytes = new byte[1024];
            try {
                socket.getInputStream().read(bytes);
                if (bytes.length > 0){
                    System.out.println(new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
