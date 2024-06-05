package netty.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

class InvalidBootstrapClient {

    public static void main(String[] args) {
        new Bootstrap()
                .group(new NioEventLoopGroup()).channel(OioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
                        System.out.println("Received data");
                    }})
                .connect(new InetSocketAddress("www.manning.com", 80))
                .syncUninterruptibly();
    }

}
