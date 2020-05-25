package org.hds.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <b>标题: Netty 服务 </b><br />
 * 
 * <pre>
 * </pre>
 *
 * @author 毛宇鹏
 * @date 创建于 上午9:02 2018/4/25
 */
@Component
public class NettyServer {

	private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private Channel channel;

	/**
	 * 启动服务
	 */
	public ChannelFuture run(int port) {

		ChannelFuture f = null;
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ServerChannelInitializer()).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			f = b.bind(port).syncUninterruptibly();
			channel = f.channel();
		} catch (Exception e) {
			log.error("Netty start error:", e);
		} finally {
			if (f != null && f.isSuccess()) {
				log.info("Netty server listening on port " + port + " and ready for connections...");
			} else {
				log.error("Netty server start up Error!");
			}
		}

		return f;
	}

	public void destroy() {
		log.info("Shutdown Netty Server...");
		if (channel != null) {
			channel.close();
		}
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		log.info("Shutdown Netty Server Success!");
	}
}