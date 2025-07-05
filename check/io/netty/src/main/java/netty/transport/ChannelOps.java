package netty.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
class ChannelOps {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    static void channelWriteCallback() {
        ByteBuf buf = Unpooled.copiedBuffer("data", CharsetUtil.UTF_8);
        ChannelFuture cf = CHANNEL_FROM_SOMEWHERE.writeAndFlush(buf);
        cf.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.debug("Write successful");
            } else {
                log.error("Write error", future.cause());
            }
        });
    }

    static void writeToChannelConcurrently() {
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        Runnable writer = () -> CHANNEL_FROM_SOMEWHERE.write(buf.duplicate());
        Executor executor = Executors.newCachedThreadPool();
        // possible write in one thread
        executor.execute(writer);
        // possible write in another thread
        executor.execute(writer);
    }

}
