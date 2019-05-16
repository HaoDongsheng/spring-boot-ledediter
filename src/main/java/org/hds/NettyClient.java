package org.hds;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hds.model.advertisement;
/**
 * 
* Title: NettyClient
* Description: Netty客户端 
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyClient {

    public static String host = "127.0.0.1";  //ip地址
    public static int port = 6666;          //端口
    static int MemorySeq = 0;
    private static ChannelFutureListener channelFutureListener = null;
    /// 通过nio方式来接收连接和处理连接   
    private static EventLoopGroup group = new NioEventLoopGroup(); 
    private static  Bootstrap b = new Bootstrap();
    private static Channel ch;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     
    public static void main(String[] args) throws InterruptedException, IOException { 
            System.out.println("客户端成功启动...");
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new NettyClientFilter());
            // 连接服务端
            ch = b.connect(host, port).sync().channel();
            //star();
    }
     **/
    public static void sendMsg(String msg) throws InterruptedException, IOException{  
    	if(ch==null)
    	{    		
		String dt = DateFormat.getDateTimeInstance().format(new Date());    					
    	System.out.println(dt +" 客户端成功启动...");
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.handler(new NettyClientFilter());
        // 连接服务端
        ch = b.connect(host, port).sync().channel();
        }
        ch.writeAndFlush(msg+ "\r\n");
        String dt = DateFormat.getDateTimeInstance().format(new Date());
        System.out.println(dt + " 客户端发送数据:"+msg);            
	}
    
   public static void star()  throws InterruptedException, IOException {
	   String dt = DateFormat.getDateTimeInstance().format(new Date());
	   System.out.println(dt + " 客户端成功启动...");
       b.group(group);
       b.channel(NioSocketChannel.class);
       b.handler(new NettyClientFilter());
       // 连接服务端
       //ch = b.connect(host, port).sync().channel();
       doConnect();
   }
   
   /**
    * 重连机制,每隔60s重新连接一次服务器
    */
   protected static void doConnect() {
       if (ch != null && ch.isActive()) {
           return;
       }

       ChannelFuture future = b.connect(host, port);

       future.addListener(new ChannelFutureListener() {
           public void operationComplete(ChannelFuture futureListener) throws Exception {
               if (futureListener.isSuccess()) {
                   ch = futureListener.channel();
                   String dt = DateFormat.getDateTimeInstance().format(new Date());
                   System.out.println(dt + " Connect to server successfully!");
                   SendLoginData("admin","admin");
               } else {
            	   String dt = DateFormat.getDateTimeInstance().format(new Date());
                   System.out.println(dt + " Failed to connect to server, try connect after 300s");

                   futureListener.channel().eventLoop().schedule(new Runnable() {
                       @Override
                       public void run() {
                           doConnect();
                       }
                   }, 300, TimeUnit.SECONDS);
               }
           }
       });
   }
   
   //获取流水号        
   private static String GetXmlSN()
   {
       if (MemorySeq >= 65535) { MemorySeq = 0; }
       MemorySeq++;
       return Integer.toString(MemorySeq);
   }
    
   public static void SendLoginData(String adminName,String adminPwd)  throws Exception{       			
		Document doc = DocumentHelper.createDocument();
	    //增加根节点
	    Element NewXmlElement = doc.addElement("Command");
	    NewXmlElement.addAttribute("name", "LOGIN_REQ");
	    NewXmlElement.addAttribute("sn", GetXmlSN());
	    NewXmlElement.addAttribute("version", "1.0.0");
	    //增加子元素
	    Element NodeUserName = NewXmlElement.addElement("UserName");
	    NodeUserName.setText(adminName);
	    
	    String strUserPwdBase64 = new String(Base64Encoder.GetEncode(adminPwd.getBytes("UTF-8")));
	    
	    Element NodeUserPwd = NewXmlElement.addElement("UserPwd");
	    NodeUserPwd.setText(strUserPwdBase64);      
	    
		String sendSring = doc.asXML().replaceAll("\r|\n", "");
		ch.writeAndFlush(sendSring + "\r\n");
	}
   
   public static void HeartbeatMethod()
   {       
       try
       {
           Document doc = DocumentHelper.createDocument();
	   	   //增加根节点
	   	   Element NewXmlElement = doc.addElement("Command");
	   	   NewXmlElement.addAttribute("name", "LINK");
	   	   NewXmlElement.addAttribute("sn", GetXmlSN());
		   NewXmlElement.addAttribute("version", "1.0.0");
		   //增加子元素
		   Element NodeUserName = NewXmlElement.addElement("Time");		   
		   Date now = new Date(); 				       
		   DateFormat d1 = DateFormat.getDateTimeInstance();						
		   NodeUserName.setText(d1.format(now));
		   
		   String sendSring = doc.asXML().replaceAll("\r|\n", "");
		   ch.writeAndFlush(sendSring + "\r\n");
       }
       catch (Exception ex)
       {
           //ex.Message.ToString()
       }
   }
   
   public static  void TcpSocketSendPublish(List<advertisement> publishList,List<advertisement> deleteList)
   {
       try
       {        
    	   Document doc = DocumentHelper.createDocument();
	   	   //增加根节点
	   	   Element NewXmlElement = doc.addElement("Command");
           NewXmlElement.addAttribute("name", "PUBLISH");
           NewXmlElement.addAttribute("sn", GetXmlSN());
           NewXmlElement.addAttribute("version", "1.0.0");
           if (publishList != null && publishList.size() > 0)
           {
        	   Element NodePublish = NewXmlElement.addElement("PublishList");	               
               for (int i = 0; i < publishList.size(); i++)
               {
            	   advertisement advertisement=publishList.get(i);
            	   Element AdXmlElement = NodePublish.addElement("Ad");                   
                   AdXmlElement.addAttribute("ID", advertisement.getPubid().toString());
                   AdXmlElement.addAttribute("Name", advertisement.getAdvname());                   
                   AdXmlElement.addAttribute("Type", "广告");
                   AdXmlElement.addAttribute("LifeAct",advertisement.getlifeAct());
        		   AdXmlElement.addAttribute("LifeDie",advertisement.getlifeDie());                                      
               }               
           }
           if (deleteList != null && deleteList.size() > 0)
           {
        	   Element NodeDelete = NewXmlElement.addElement("DeleteList");               
               for (int i = 0; i < deleteList.size(); i++)
               {
            	   advertisement advertisement=deleteList.get(i);
            	   Element AdXmlElement = NodeDelete.addElement("Ad");                   
                   AdXmlElement.addAttribute("ID", advertisement.getPubid().toString());
                   AdXmlElement.addAttribute("Name", advertisement.getAdvname());                   
                   AdXmlElement.addAttribute("Type", "广告");
                   AdXmlElement.addAttribute("LifeAct",advertisement.getlifeAct());
        		   AdXmlElement.addAttribute("LifeDie",advertisement.getlifeDie());                  
               }               
           }           
           
           String sendSring = doc.asXML().replaceAll("\r|\n", "");
		   ch.writeAndFlush(sendSring + "\r\n");                     
       }
       catch (Exception e) {e.printStackTrace(); }
   }
   

}