package bio;

import java.util.concurrent.*;

public class ServerThreadPoolHandler implements Executor {


    private ExecutorService executorService;

    public ServerThreadPoolHandler(int corePoolSize,int maxPoolSize,int queueSize) {
        executorService = new ThreadPoolExecutor(corePoolSize,maxPoolSize,3l, TimeUnit.MINUTES,new LinkedBlockingQueue<>(queueSize));
    }


    public void execute(Runnable command) {
        executorService.execute(command);
    }


    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(0,200,3l, TimeUnit.MINUTES,new LinkedBlockingQueue<>());

        while(true){
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println("aaaaaaaa");
                }
            });
        }
    }



}
