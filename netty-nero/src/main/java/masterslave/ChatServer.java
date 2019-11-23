package masterslave;

import java.io.IOException;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
    }



}
