package netty.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;

import java.net.URI;
import java.util.Scanner;

class NettyWebSocketClient {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13,
                    null,
                    false,
                    new DefaultHttpHeaders());
            ClientHandler handler = new ClientHandler(handshaker);
            Channel channel = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(65536),
                                    handler
                            );
                        }
                    })
                    .connect(uri.getHost(), uri.getPort())
                    .sync().channel();
            //
            // ~ wait until the WebSocket handshake is done
            //
            handler.handshakeFuture().sync();
            System.out.println("connected to server " + uri);
            System.out.println("type a message and press 'Enter' or type 'quit' to exit.");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("quit".equalsIgnoreCase(line)) break;
                channel.writeAndFlush(new TextWebSocketFrame(line));
            }
            channel.writeAndFlush(new CloseWebSocketFrame());
            channel.closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }

    private static class ClientHandler extends SimpleChannelInboundHandler<Object> {

        private final WebSocketClientHandshaker handshaker;
        private ChannelPromise handshakeFuture;

        ClientHandler(WebSocketClientHandshaker handshaker) {
            this.handshaker = handshaker;
        }

        ChannelFuture handshakeFuture() {
            return handshakeFuture;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            handshakeFuture = ctx.newPromise();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            handshaker.handshake(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            if (!handshaker.isHandshakeComplete()) {
                handshaker.finishHandshake(ctx.channel(), (FullHttpResponse) msg);
                handshakeFuture.setSuccess();
                return;
            }
            if (msg instanceof TextWebSocketFrame frame) {
                System.out.println("received: " + frame.text());
            } else if (msg instanceof CloseWebSocketFrame) {
                ctx.channel().close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            if (!handshakeFuture.isDone()) handshakeFuture.setFailure(cause);
            System.err.println("error: " + cause.getMessage());
            ctx.close();
        }
    }
}