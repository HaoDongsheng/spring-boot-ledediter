package org.hds;

import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * 
* Title: NettyClientFilter
* Description: Netty客户端 过滤器
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        /*
         * 解码和编码，应和服务端一致
         * */
        ph.addLast(new IdleStateHandler(0, 0, 300, TimeUnit.SECONDS));
        //ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //ph.addLast("decoder", new StringDecoder());
        //ph.addLast("encoder", new StringEncoder());
        ph.addLast(new StringDecoder(Charset.forName("UTF-8")));					        
        ph.addLast(new StringEncoder(Charset.forName("UTF-8")));
        ph.addLast("handler", new NettyClientHandler()); //客户端的逻辑
    }
}