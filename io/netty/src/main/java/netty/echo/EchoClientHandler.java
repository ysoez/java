package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // ~ invoked when a connection has been established
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        // ~ invoked whenever data is received.
        log.debug("Client received: {}", in.toString(CharsetUtil.UTF_8));
    }

    // ~ stop the server to trigger execution
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // ~ close the connection when an exception is raised
        log.error("Error happened: ", cause);
        ctx.close();
    }
}
