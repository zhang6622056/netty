package jedis;

import redis.clients.jedis.Jedis;

public class JedisTest {


    public static void main(String[] args) {
        Jedis redisClient = new Jedis("127.0.0.1",3309);
        System.out.println(redisClient.set("nero","1234234"));
//        System.out.println(redisClient.get("nero"));
//        System.out.println(redisClient.incr("lock"));
    }



}
