package netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

class NettyWebSocketServer {

    static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        var boss = new NioEventLoopGroup(1);
        var worker = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new HttpServerCodec(),
                                    new HttpObjectAggregator(65536),
                                    new WebSocketServerProtocolHandler("/ws"),
                                    new ServerHandler()
                            );
                        }
                    })
                    .bind(PORT)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            clients.add(ctx.channel());
            System.out.println("client connected: " + ctx.channel().remoteAddress());
            System.out.println("total clients: " + clients.size());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            System.out.println("client disconnected: " + ctx.channel().remoteAddress());
            System.out.println("total clients: " + clients.size());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
            String message = frame.text();
            System.out.println("received: " + message);
            //
            // ~ broadcast to every connected client
            //
            clients.writeAndFlush(new TextWebSocketFrame("broadcast: " + message));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("error: " + cause.getMessage());
            ctx.close();
        }
    }
}