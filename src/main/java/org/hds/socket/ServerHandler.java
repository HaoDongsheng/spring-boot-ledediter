package org.hds.socket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <b>标题: </b><br />
 * 
 * <pre>
 * </pre>
 *
 * @author 毛宇鹏
 * @date 创建于 上午9:14 2018/4/25
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		System.out.println("server receive message :" + msg);
//		ctx.channel().writeAndFlush("yes server already accept your message" + msg);

		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateNowString = ft.format(now);

		JSONObject jObject = new JSONObject();
		jObject.put("commandSN", now.getTime());
		jObject.put("commandName", "GetStatusbyDtus");
		jObject.put("commandTime", dateNowString);
		JSONArray jArray = new JSONArray();
		jArray.add("21049468093");
		jArray.add("21049468094");
		jObject.put("commandValue", jArray);

		ctx.channel().writeAndFlush(jObject.toJSONString());

		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("channelActive>>>>>>>>");
	}
}
