package nerojedis;



/***
 *
 * redis 客户端对象
 * @author Nero
 * @date 2019-11-19
 * *@param: null
 * @return 
 */
public class NeroJedisClient {

    private NeroJedisSocket neroJedisSocket;


    public NeroJedisClient(String address,int port) {
        neroJedisSocket = new NeroJedisSocket(address,port);
    }

    public String set(String key, String value){
        neroJedisSocket.send(NeroJedisSocket.Command.SET,key.getBytes(),value.getBytes());
        return neroJedisSocket.read();
    }

    public String get(String key){
        neroJedisSocket.send(NeroJedisSocket.Command.GET,key.getBytes());
        return neroJedisSocket.read();
    }



    public String incre(String key){
        neroJedisSocket.send(NeroJedisSocket.Command.INCR,key.getBytes());
        return neroJedisSocket.read();
    }





    public static void main(String[] args) {
        NeroJedisClient neroJedisClient = new NeroJedisClient("127.0.0.1",6379);
        //System.out.println(neroJedisClient.set("nero","123456"));
        //System.out.println(neroJedisClient.get("nero"));

        System.out.println(neroJedisClient.incre("lock"));
    }



}
