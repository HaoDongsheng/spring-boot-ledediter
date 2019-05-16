package org.hds;

import java.util.HashMap;
import java.util.Map;

public class NettyClientList {
	static Map<String, NettyClient> NettyClientList=new HashMap<String, NettyClient>();

	public static Map<String, NettyClient> getNettyClientList() {
		return NettyClientList;
	}

	public static void setNettyClientList(Map<String, NettyClient> nettyClientList) {
		NettyClientList = nettyClientList;
	}
	
	public static int getNettyClientListCount() {
		return NettyClientList.size();
	}
	
	public static NettyClient getNettyClient(String NettyClientKey) {
		if(NettyClientList.containsKey(NettyClientKey))
		{
			return NettyClientList.get(NettyClientKey);
		}
		else {
			NettyClient client=new NettyClient();
			addNettyClient(NettyClientKey,client);
			return NettyClientList.get(NettyClientKey);
		}		
	}
	
	public static void addNettyClient(String NettyClientKey, NettyClient nettyClient) {
		if(NettyClientList.containsKey(NettyClientKey))
		{
			NettyClientList.remove(NettyClientKey);
		}		
		NettyClientList.put(NettyClientKey, nettyClient);		
	}
	
	public static void removeNettyClient(String NettyClientKey) {
		if(NettyClientList.containsKey(NettyClientKey))
		{
			NettyClientList.remove(NettyClientKey);
		}				
	}
}
