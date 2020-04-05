package example.firstdemo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * netty server的模版类
 * @author Nero
 * @date 2020-04-01
 * *@param: null
 * @return
 */
public class TestServer {


    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(30000);

        startNetty();
    }




    private static void startNetty() throws InterruptedException {




        //- 初始化NioEventLoopGroup和内部的NioEventLoop线程对象。
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        //- 创建netty启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)  //- 在AbstractBoostrap类中保存ChannelFactory的指向 。服务断所使用的通道
                .childHandler(new TestServerInitializer());    //- 保存在ServerBootstrap 的 childHandler


        try {

            //- 启动线程
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();




            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }








}
