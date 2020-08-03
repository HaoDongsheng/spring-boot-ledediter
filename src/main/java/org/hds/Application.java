package org.hds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
//@EnableAsync
//@MapperScan("org.hds.mapper")
//@ComponentScan(basePackages = { "org.hds.service", "org.hds.service.impl", "org.hds.web", "org.hds.config",
//		"org.hds.socket" })
public class Application implements CommandLineRunner {

	@Value("${n.port}")
	private int port;

	@Autowired
	org.hds.socket.NettyServer NettyServer;
//	@Autowired
//	IInfoListService InfoListSer;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		try {
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void run(String... args) throws Exception {
		// InfoListSer.updateAllLists();

		NettyClient.star();
	}

//	@Override
//	public void run(String... strings) {
//		try {
////			ChannelFuture future = NettyServer.run(port);
////			Runtime.getRuntime().addShutdownHook(new Thread() {
////				@Override
////				public void run() {
////					NettyServer.destroy();
////				}
////			});
////			future.channel().closeFuture().syncUninterruptibly();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}