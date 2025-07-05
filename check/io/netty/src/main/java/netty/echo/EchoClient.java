package netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import netty.Utils;

final class EchoClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // ~ configure SSL
        final SslContext sslCtx = Utils.buildSslContext();
        // ~ configure the client
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            ChannelPipeline p = channel.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(channel.alloc(), HOST, PORT));
                            }
                            p.addLast(new EchoClientHandler());
                        }
                    });
            // ~ start the client
            ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
            // ~ wait until the connection is closed
            f.channel().closeFuture().sync();
        } finally {
            // ~ shut down the event loop to terminate all threads
            group.shutdownGracefully();
        }
    }
}
