package org.hds;

import java.text.DateFormat;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
* Title: NettyClientHandler
* Description: 客户端业务逻辑实现
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

	//连接失败次数
	private int connectTime = 0;
	
	//定义最大未连接次数
	private static final int MAX_UN_CON_TIME = 3;

		
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception { 
    	String dt = DateFormat.getDateTimeInstance().format(new Date());
        System.out.println(dt + " 客户端接受的消息: " + msg);
    }
        
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String dt = DateFormat.getDateTimeInstance().format(new Date());
									
			if (event.state() == IdleState.READER_IDLE) {
				//读超时				
				System.out.println(dt +" 服务端读超时=====连接失败次数" + connectTime);
				if (connectTime >= MAX_UN_CON_TIME) {
					System.out.println(dt +" 服务端关闭channel");
					ctx.channel().close();
					connectTime = 0;
				} else {
					connectTime ++;
				}
				
			}else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/  
				
                System.out.println(dt +" 服务端写超时");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                System.out.println(dt +" 服务端全部超时");
                NettyClient.HeartbeatMethod();
            }
			
		}
	}
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
        NettyClient.doConnect();
    }
}