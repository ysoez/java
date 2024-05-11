package netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import netty.Utils;

import java.net.InetSocketAddress;

final class EchoServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // ~ configure SSL
        final SslContext sslCtx = Utils.buildSslContext();
        // ~ configure the server
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        final var serverHandler = new EchoServerHandler();
        try {
            var bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8000))
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            ChannelPipeline p = channel.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(channel.alloc()));
                            }
                            p.addLast(serverHandler);
                        }
                    });
            // ~ wait for bind completion (start the server)
            ChannelFuture f = bootstrap.bind(PORT).sync();
            // ~ wait until the server socket is closed
            f.channel().closeFuture().sync();
        } finally {
            // ~ shut down all event loops to terminate all threads
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
