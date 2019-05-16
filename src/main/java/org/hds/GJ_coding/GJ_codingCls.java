package org.hds.GJ_coding;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sun.font.FontDesignMetrics;

public class GJ_codingCls {
	 private final int Fixedbyte = 54;//协议体中，除数据内容以外的字节。
     private byte[] byte10 = new byte[10];

     private int SendMaxPackege;
   
     public GJ_codingCls()
     {
         SendMaxPackege = 0;//发送数据不分包
     }

     public GJ_codingCls(int SendMaxPackegeLen)
     {         
         if (SendMaxPackegeLen < 512)
         { SendMaxPackege = 256; }
         else if (SendMaxPackegeLen < 768)
         { SendMaxPackege = 512; }
         else if (SendMaxPackegeLen < 1024)
         { SendMaxPackege = 768; }
         else if (SendMaxPackegeLen < 2048)
         { SendMaxPackege = 1024; }
         else if (SendMaxPackegeLen < 3072)
         { SendMaxPackege = 2048; }
         else if (SendMaxPackegeLen < 4096)
         { SendMaxPackege = 3072; }
         else
         { SendMaxPackege = 4096; } 
     }
     
     private byte[] ListTobyte(List<Byte> list) { 
    	 if (list == null || list.size() < 0)
             return null;
         byte[] bytes = new byte[list.size()];
         int i = 0;
         Iterator<Byte> iterator = list.iterator();
         while (iterator.hasNext()) {
             bytes[i] = iterator.next();
             i++;
         }
         return bytes;   	 
	 }
     
     private void byteArrayInsList(List tByte,byte[] srcbytes) {    	     	 
    	 if(srcbytes!=null && srcbytes.length>0)
    	 {    		 
    		 for(int i=0;i<srcbytes.length;i++)
    		 {    			     			 
    			 tByte.add(srcbytes[i]);    			 
    		 }    		 
    	 }        	 
	 }
     
     private byte intTobyte(int a) {    	 
    	 return (byte) (a & 0xFF);
	 }
     
     public int byteArrayToInt(byte[] b) {   
    	 return   b[3] & 0xFF |   
    	             (b[2] & 0xFF) << 8 |   
    	             (b[1] & 0xFF) << 16 |   
    	             (b[0] & 0xFF) << 24;   
    	 } 
     
	 public byte[] intToByteArray(int a) {   
	 return new byte[] {   
	         (byte) ((a >> 24) & 0xFF),   
	         (byte) ((a >> 16) & 0xFF),      
	         (byte) ((a >> 8) & 0xFF),      
	         (byte) (a & 0xFF)   
	     };   
	 } 

     /// <summary>
     /// 生成单包发送数据
     /// </summary>
     /// <param name="type"></param>
     /// <param name="version"></param>
     /// <param name="key"></param>
     /// <param name="bytedata"></param>
     /// <returns></returns>
     public byte[] GJ_SendDataencoding(int type, int version,int mpageall,int mpage,int sendid, String key, byte[] bytedata)
     {
         try
         {           	 
             List<Byte> tByte = new ArrayList<Byte>();
             
             tByte.add(intTobyte(0x7E));//头 
             tByte.add(intTobyte(0x46));
             tByte.add(intTobyte(0x47));
             tByte.add(intTobyte(0x4A));
             tByte.add(intTobyte(0));//长度2个字节4
             tByte.add(intTobyte(0));//长度2个字节5  
             tByte.add(intTobyte(type));//协议类型2                
             tByte.add(intTobyte(version));//协议版本2
             tByte.add(intTobyte(mpageall / 256));//总包数
             tByte.add(intTobyte(mpageall % 256));//
             tByte.add(intTobyte(mpage / 256));//本包号
             tByte.add(intTobyte(mpage % 256));//
             tByte.add(intTobyte(0x41));////数据流向 A下行，B上行                
             tByte.add(intTobyte(0x61));////数据来源 a led平台下发
             byteArrayInsList(tByte, byte10);//保留10个字节          
             
             byte[] tt = key.getBytes();//指令代号                     
             byteArrayInsList(tByte,tt);
             tByte.add(intTobyte(sendid / 256));//多包的id
             tByte.add(intTobyte(sendid % 256));//  

             if (bytedata != null)
             {
                 tByte.add(intTobyte(bytedata.length / 256));//数据区总字节数
                 tByte.add(intTobyte(bytedata.length % 256));//                    
                 byteArrayInsList(tByte,bytedata);
             } //添加数据区内容
             else
             {
                 tByte.add(intTobyte(0));//数据区总字节数
                 tByte.add(intTobyte(0));//
             }

             //更改长度值  24=流水号+屏号+保留+头尾-2             
             tByte.set(4, intTobyte((tByte.size() + 22) / 256));
             tByte.set(5, intTobyte((tByte.size() + 22) % 256));             
             GJ_CRCcls crc = new GJ_CRCcls();//crc校验
             int crcvalue = crc.CRC_16(ListTobyte(tByte), 4, tByte.size()-1);
             
             tByte.add(intTobyte(crcvalue / 256));//
             tByte.add(intTobyte(crcvalue % 256));//
             byteArrayInsList(tByte, byte10);//流水号+屏号
             byteArrayInsList(tByte, byte10);//保留10个字节
             tByte.add(intTobyte(0x45));//结尾EN
             tByte.add(intTobyte(0x4E));//
             tByte.add(intTobyte(0x7E));//
             
             byte[] mybytedata = GJ_ZhuanYi(ListTobyte(tByte));//改动2-15

             return mybytedata;//改动2-15

             //return ListTobyte(tByte);
         }
         catch (Exception e)
         {
             return null;                             
         }
     }
     
     /// <summary>
     /// 转义 改动2-15
     /// </summary>
     /// <param name="bytedata"></param>
     /// <returns></returns>
     private byte[] GJ_ZhuanYi(byte[] bytedata)
     {
         List<Byte> tByte = new ArrayList<Byte>();
         tByte.add(intTobyte(0x7E));
         for (int i = 1; i < bytedata.length-1; i++)
         {
             if (bytedata[i] == intTobyte(0x7D))
             {
                 tByte.add(intTobyte(0x7D));
                 tByte.add(intTobyte(0x01));
             }
             else if (bytedata[i] == intTobyte(0x7E))
             {
                 tByte.add(intTobyte(0x7D));
                 tByte.add(intTobyte(0x02));
             }
             else
             {
                 tByte.add(bytedata[i]);
             }

         }
         tByte.add(intTobyte(0x7E));
         return ListTobyte(tByte);

     }
     /// <summary>
     /// 反转义 改动2-15
     /// </summary>
     /// <param name="bytedata"></param>
     /// <returns></returns>
     private byte[] GJ_FanZhuanYi(byte[] bytedata)
     {
         List<Byte> tByte = new ArrayList<Byte>();
         tByte.add(intTobyte(0x7E));
         for (int i = 1; i < bytedata.length - 1; i++)
         {
             if (bytedata[i] == intTobyte(0x7D))
             {
                 i++;
                 if (bytedata[i] == intTobyte(0x01))
                 {
                     tByte.add(intTobyte(0x7D));
                 }
                 else if (bytedata[i] == intTobyte(0x02))
                 {
                     tByte.add(intTobyte(0x7E));
                 }
                 else
                 {
                     tByte.add(intTobyte(0x7D));
                     i--;
                 }
             }
             else
             {
                 tByte.add(bytedata[i]);
             }
         }
         tByte.add(intTobyte(0x7E));
         return ListTobyte(tByte);
     }

     /// <summary>
     /// 生成多包发送数据
     /// </summary>
     /// <param name="type"></param>
     /// <param name="version"></param>
     /// <param name="key"></param>
     /// <param name="bytedata"></param>
     /// <returns></returns>
     public List<byte[]> GJ_SplitData(int type, int version, String key,int sendid, byte[] bytedata)
     {

         try
         {
             List<byte[]> mylistbyte = new ArrayList<byte[]>();
             byte[] myonebyte = new byte[] { };
             //int JSZ = SendMaxPackege - Fixedbyte;//一包数据区数据的大小
             int JSZ = SendMaxPackege;//一包数据区数据的大小 //改动2-15
             int byteinfoLen = bytedata.length;

             if ((byteinfoLen <= JSZ) || (SendMaxPackege==0)) //不分包
             {
                 myonebyte = GJ_SendDataencoding(type, version, 1, 0, sendid, key, bytedata);
                 mylistbyte.add(myonebyte);           
             }
             else//分多包
             {
                 double pAll = (double)byteinfoLen / JSZ;//
                 int pageAll = (int)Math.ceil(pAll);//向上取整 共几包
                 
                 for (int page = 0; page < pageAll; page++)
                 {
                     if ((page + 1) == pageAll)//最后一包
                     {
                         //byte[] myonebyte_1 = new byte[byteinfoLen - JSZ * page];
                         //System.arraycopy(bytedata, JSZ * page, myonebyte_1, 0,(byteinfoLen - JSZ * page));
                         myonebyte = new byte[JSZ];
                         System.arraycopy(bytedata, JSZ * page,myonebyte,0,(byteinfoLen - JSZ * page));
                         
                     }
                     else
                     {
                         myonebyte = new byte[JSZ];
                         System.arraycopy(bytedata,JSZ * page, myonebyte, 0, JSZ);
                     }
                     
                     mylistbyte.add(GJ_SendDataencoding(type, version, pageAll, page, sendid, key, myonebyte));
                 }
             }              
                                             
             return mylistbyte;

         }
         catch (Exception e)
         {
             return null;                
         }
     }
     /// <summary>
     /// 添加播放列表alst
     /// </summary>
     public List<byte[]> GJ_AddPlayList(int type, int version, GJ_Playlistcls playlist)
     {
         List<Byte> mylist = new ArrayList<Byte>();         
         try
         {
             byte[] tt = new byte[] { };

             mylist.add(intTobyte(playlist.getM_id() / 256));//列表id
             mylist.add(intTobyte(playlist.getM_id() % 256));

             mylist.add(intTobyte(0));//总长度4byte
             mylist.add(intTobyte(0));//总长度4byte

             mylist.add(playlist.getM_level());//优先级
             byteArrayInsList(mylist, byte10);//保留10个字节
             byteArrayInsList(mylist, byteLife(playlist.getM_lifestart()));//生命周期开始
             byteArrayInsList(mylist, byteLife(playlist.getM_lifeend()));//生命周期结束
             mylist.add(intTobyte(playlist.getM_Timequantum().size() % 256));//时间段总数 以分为单位 4字节
             for (int i = 0; i < playlist.getM_Timequantum().size(); i++)
             {
                 int tvalue = timevalueMM(playlist.getM_Timequantum().get(i).getM_playstart());//开始
                 mylist.add(intTobyte(tvalue / 256));
                 mylist.add(intTobyte(tvalue % 256));
                 tvalue = timevalueMM(playlist.getM_Timequantum().get(i).getM_playend());//结束
                 mylist.add(intTobyte(tvalue / 256));
                 mylist.add(intTobyte(tvalue % 256));
             }
             mylist.add(playlist.getM_Scheduling().getM_mode());//优先级
             
             switch (playlist.getM_Scheduling().getM_mode())
             {
                 case 0://0x00按指定id顺序排挡。排挡内容：广告总数+广告id集合。
                	 byteArrayInsList(mylist, byte10);//保留10个字节
                     mylist.add(intTobyte(playlist.getM_Scheduling().getM_Loop_adidlist().size() / 256));//广告总数
                     mylist.add(intTobyte(playlist.getM_Scheduling().getM_Loop_adidlist().size() % 256));//广告总数
                     for (int i = 0; i < playlist.getM_Scheduling().getM_Loop_adidlist().size(); i++)
                     {
                    	 mylist.add(intTobyte(playlist.getM_Scheduling().getM_Loop_adidlist().get(i).getM_ADid() / 256));//广告id
                         mylist.add(intTobyte(playlist.getM_Scheduling().getM_Loop_adidlist().get(i).getM_ADid() % 256));
                         byteArrayInsList(mylist, byteLife(playlist.getM_Scheduling().getM_Loop_adidlist().get(i).getM_ADidlifestart()));//生命周期开始
                         byteArrayInsList(mylist, byteLife(playlist.getM_Scheduling().getM_Loop_adidlist().get(i).getM_ADidlifeend()));//生命周期结束
                         //tt = intToByteArray(playlist.getM_Scheduling().getM_Loop_adidlist().get(i));                         
                         //byteArrayInsList(mylist, tt);
                     }
                     break;
                 case 1://0x01模板排挡：
                	 byteArrayInsList(mylist, byte10);//保留10个字节
                     tt = intToByteArray(playlist.getM_Scheduling().getM_Template_cycle());//周期                     
                     byteArrayInsList(mylist, tt);
                     mylist.add(intTobyte(playlist.getM_Scheduling().getM_Template_adlist().size() / 256));//广告总数
                     mylist.add(intTobyte(playlist.getM_Scheduling().getM_Template_adlist().size() % 256));//广告总数
                     for (int i = 0; i < playlist.getM_Scheduling().getM_Template_adlist().size(); i++)
                     {
                         mylist.add(intTobyte(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_adid() / 256));//广告id
                         mylist.add(intTobyte(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_adid() % 256));//

                         mylist.add(intTobyte(0));
                         mylist.add(intTobyte(0));
                         mylist.add(intTobyte(0));
                         mylist.add(intTobyte(0));
                         
                         byteArrayInsList(mylist, byteLife(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_ADlifestart()));//生命周期开始
                         byteArrayInsList(mylist, byteLife(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_ADlifeend()));//生命周期结束
                         
                         tt = intToByteArray(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_adplaytime());//广告播放时长                         
                         byteArrayInsList(mylist, tt);
                         mylist.add(intTobyte(0));//播放时间偏移量总数
                         mylist.add(intTobyte(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_timeoffset().size() % 256));//播放时间偏移量总数
                         for (int j = 0; j < playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_timeoffset().size(); j++)
                         {
                             tt = intToByteArray(Integer.parseInt(playlist.getM_Scheduling().getM_Template_adlist().get(i).getM_timeoffset().get(j)));//偏移量                             
                             byteArrayInsList(mylist, tt);                                
                         }
                     }

                     break;
                 default:
                     return null;                        
             }

             //更改长度值
             mylist.set(2, intTobyte((mylist.size() + 2) / 256));
             mylist.set(3, intTobyte((mylist.size() + 2) % 256));             

             GJ_CRCcls crc = new GJ_CRCcls();//crc校验
             playlist.setM_crc(crc.CRC_16(ListTobyte(mylist)));             
             mylist.add(intTobyte(playlist.getM_crc() / 256));//
             mylist.add(intTobyte(playlist.getM_crc() % 256));//

             return GJ_SplitData(type, version,"alst",playlist.getM_id(), ListTobyte(mylist));             
         }
         catch (Exception e)
         {             
             return null;           
         }

     }

     /// <summary>
     /// 查询播放列表
     /// </summary>
     /// <param name="type"></param>
     /// <param name="version"></param>
     /// <param name="sendByte"></param>
     /// <returns></returns>
     public byte[] GJ_QueryPlayList(int type, int version)
     {
         
    	 return GJ_SendDataencoding(type, version, 1, 0, 0,"qver", null);         
     }

     /// <summary>
     /// 删除播放列表
     /// </summary>
     public byte[] GJ_DeletePlayList(int type, int version,int[] listidarray)
     {   
         List<Byte> mylist = new ArrayList<Byte>();
         mylist.add(intTobyte(listidarray.length  / 256));//
         mylist.add(intTobyte(listidarray.length  % 256));//
         for (int i = 0; i < listidarray.length; i++)
         {
             mylist.add(intTobyte(listidarray[i] / 256));//
             mylist.add(intTobyte(listidarray[i] % 256));//
         }
         return GJ_SendDataencoding(type, version, 1, 0, 0,"dlst", ListTobyte(mylist));         
     }
     
     /// <summary>
     /// 添加广告
     /// </summary>
     public List<byte[]>  GJ_AddAdScript(int type, int version, GJ_ADscriptcls adscript)
     {
    	 List<Byte> mylist = new ArrayList<Byte>();         
         try
         {
             byte[] tt = new byte[] { };

             mylist.add(intTobyte(adscript.getM_adid() / 256));//广告id
             mylist.add(intTobyte(adscript.getM_adid() % 256));//

             mylist.add(intTobyte(0));//总长度4byte
             mylist.add(intTobyte(0));//总长度4byte
             mylist.add(intTobyte(0));//总长度4byte
             mylist.add(intTobyte(0));//总长度4byte

             mylist.add(adscript.getM_adtype());//广告类型
             byteArrayInsList(mylist, byte10);//保留10个字节
             
                 mylist.add(intTobyte(adscript.getM_basemapwidth() / 256));//底图宽
                 mylist.add(intTobyte(adscript.getM_basemapwidth() % 256));//
                 mylist.add(intTobyte(adscript.getM_basemapheight() / 256));//底图高
                 mylist.add(intTobyte(adscript.getM_basemapheight() % 256));//
                 mylist.add(adscript.getM_framemode());//边框样式
                 mylist.add(adscript.getM_framespeed());//边框速度
                 mylist.add(adscript.getM_transparency());//透明度
                 byteArrayInsList(mylist, byte10);//保留10个字节
                                  
                 int basemaplen = 0;

                 switch (adscript.getM_basemalistmode())                 
                 {
                     case 1://全彩3字节
                         basemaplen =(adscript.getM_basemapwidth() * adscript.getM_basemapheight() +3 )* 3 * adscript.getM_basemalist().size();
                         break;
                     case 2://纯色
                         basemaplen = ((adscript.getM_basemapwidth() * adscript.getM_basemapheight()/8) + 3) * adscript.getM_basemalist().size();                            
                         break;
                     case 3://全彩2字节
                         basemaplen = (adscript.getM_basemapwidth() * adscript.getM_basemapheight() + 3) * 2 * adscript.getM_basemalist().size();
                         break;
                     default:
                         break;
                 }
                 
                 tt = intToByteArray(basemaplen);                 
                 byteArrayInsList(mylist, tt);
                 mylist.add(intTobyte(adscript.getM_basemalist().size()  % 256));//底图总数

                 for (int i = 0; i < adscript.getM_basemalist().size(); i++)
                 {
                     switch ((int)(adscript.getM_basemalistmode()))
                     {
                         case 1://全彩3字节
                             mylist.add(intTobyte(0));
                             mylist.add(intTobyte(0));
                             mylist.add(intTobyte(0));
                             byteArrayInsList(mylist, picturetobyte(adscript.getM_basemalist().get(i)));//读图片                              
                             break;
                         case 2://纯色
                        	 byteArrayInsList(mylist,picturetobyteCUNSE(adscript.getM_basemalist().get(i)));
                             break;
                         case 3://全彩2字节
                             mylist.add(intTobyte(0));
                             mylist.add(intTobyte(0));
                             mylist.add(intTobyte(0));
                             byteArrayInsList(mylist,picturetobyte2byte(adscript.getM_basemalist().get(i)));
                             break;
                         default:
                             break;
                     }                	                 
                 }   

             mylist.add(intTobyte(adscript.getM_HDRmode()));//播放模式
             mylist.add(intTobyte(adscript.getM_playtime() / 256));//播放总时长
             mylist.add(intTobyte(adscript.getM_playtime() % 256));//
             mylist.add(intTobyte(adscript.getM_aditem().size() / 256));//显示项总数
             mylist.add(intTobyte(adscript.getM_aditem().size() % 256));//
             for (int i = 0; i < adscript.getM_aditem().size(); i++)
             {
            	 byteArrayInsList(mylist, itemtobyte(adscript.getM_aditem().get(i)));//显示项
             }

             
             tt = intToByteArray(mylist.size() + 2);//更改长度值
             mylist.set(2, tt[0]);
             mylist.set(3, tt[1]);
             mylist.set(4, tt[2]);
             mylist.set(5, tt[3]);             

             GJ_CRCcls crc = new GJ_CRCcls();//crc校验
             adscript.setM_crc(crc.CRC_16(ListTobyte(mylist)));             
             mylist.add(intTobyte(adscript.getM_crc() / 256));//
             mylist.add(intTobyte(adscript.getM_crc() % 256));//

             return GJ_SplitData(type, version,  "aadv", adscript.getM_adid(), ListTobyte(mylist));             
         }
         catch (Exception e)
         {
             return null;          
         }
     }

     /// <summary>
     /// 删除广告列表
     /// </summary>
     public byte[] GJ_DeleteADscriptList(int type, int version, int[] adidarray)
     {
    	 try
    	 {
         List<Byte> mylist = new ArrayList<Byte>();
         mylist.add(intTobyte(adidarray.length / 256));//
         mylist.add(intTobyte(adidarray.length % 256));//
         for (int i = 0; i < adidarray.length; i++)
         {
             mylist.add(intTobyte(adidarray[i] / 256));//
             mylist.add(intTobyte(adidarray[i] % 256));//
         }
         return GJ_SendDataencoding(type, version, 1, 0, 0, "dadv", ListTobyte(mylist));
    	 }
    	 catch(Exception ex){return null;}
     }

     //读图片
     private byte[] picturetobyte(BufferedImage  map)
     {   
         int p = 0;
         int group = (int)(map.getWidth() / 8);
         byte[] returnimg = new byte[map.getHeight() * map.getWidth() * 3];            
         for(int g = 0;g<group;g++)
         {
             for (int h = 0; h < map.getHeight(); h++)//行 
             {                       
                 for (int l = 0; l < 8; l++)//列
                 {
                     int pixelColor = map.getRGB(l + g * 8, h);
                     returnimg[p++] = (byte)((pixelColor & 0xff0000) >> 16);
                     returnimg[p++] = (byte)((pixelColor & 0xff00) >> 8);
                     returnimg[p++] = (byte)(pixelColor & 0xff);                        
                 }
             }                 
         }
         return returnimg;
     }

     //读图片---纯色      
     private byte[] picturetobyteCUNSE(BufferedImage imageIn)//
     {
         byte tmp = 0;
         short k = 0;
         byte[] returnimg = new byte[(imageIn.getHeight() * (int)(imageIn.getWidth() / 8) * 8) / 8+3];
         returnimg[0] = 0;
         returnimg[1] = 0;
         returnimg[2] = 0;

         for (int x = 0; x < (imageIn.getWidth() / 8); x++)
         {
             {
                 for (int y = 0; y < imageIn.getHeight(); y++)
                 {
                     tmp = 0;
                     for (int p = 0; p < 8; p++)
                     {                         
                         int luma = imageIn.getRGB(x * 8 + p, y);
                         if ((luma & 255) > 64)
                         {
                             tmp = (byte)(tmp / 2 + 128);
                             returnimg[0] = (byte)((luma & 0xff0000) >> 16);;
                             returnimg[1] = (byte)((luma & 0xff00) >> 8);
                             returnimg[2] = (byte)(luma & 0xff);
                         }
                         else
                         {
                             tmp = (byte)(tmp / 2);
                         }
                     }
                     returnimg[k] = tmp; k++;
                 }
             }
         }

         return returnimg;
     }
     
     
     private byte[] Gamma_Init(double g)
     {
    	 byte[] Gamma_a = new byte[256];
       	 for(int i=0;i<256;i++)
	          {
       			Gamma_a[i] = (byte)(Math.pow((float)i / 255, g) * 255);
	          }
       	 return Gamma_a;
     }
     
     //读图片---全彩2byte
     private byte[] picturetobyte2byte(BufferedImage map)
     {    	   
    	 //byte[] Gamma_a=Gamma_Init(2.1);
         int p = 0;
         int group = (int)(map.getWidth() / 8);
         byte[] returnimg = new byte[map.getHeight() * map.getWidth() * 2];
         for (int g = 0; g < group; g++)
         {
             for (int h = 0; h < map.getHeight(); h++)//行 
             {
                 for (int l = 0; l < 8; l++)//列
                 {
                	 int luma = map.getRGB(l + g * 8, h);                     
                     //565 16位颜色
                	 int R = ((luma & 0xff0000) >> 16);;
                     int G = ((luma & 0xff00) >> 8);
                     int B = (luma & 0xff);
                     
                     //int val = B >> 3 << 11;
                     //val |= G >> 2 << 5;
                     //val |= R >> 3;
                     int val = (B * 32 / 256) << 11;
                     val |= (G * 32 / 256) << 5;
                     val |= (R * 32 / 256);

                     returnimg[p++] = intTobyte(val / 256);
                     returnimg[p++] = intTobyte(val % 256);
                 }
             }
         }
         return returnimg;
     }
     
     //显示项
     private byte[] itemtobyte(GJ_ADitemcls aditem)
     {
         List<Byte> mylist = new ArrayList<Byte>();
         mylist.add(intTobyte(0));//显示项长度4字节
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));//
         mylist.add(aditem.getM_type());
         if(aditem.getM_fontforecolor()!=null)
         {
	         mylist.add(intTobyte(aditem.getM_fontforecolor().getRed()));
	         mylist.add(intTobyte(aditem.getM_fontforecolor().getGreen()));
	         mylist.add(intTobyte(aditem.getM_fontforecolor().getBlue()));
	         mylist.add(intTobyte(aditem.getM_fontforetransparency()));
         }
         else {        	 
        	 mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
		 }
         if(aditem.getM_fontbackcolor()!=null)
         {
	         mylist.add(intTobyte(aditem.getM_fontbackcolor().getRed()));
	         mylist.add(intTobyte(aditem.getM_fontbackcolor().getGreen()));
	         mylist.add(intTobyte(aditem.getM_fontbackcolor().getBlue()));
	         mylist.add(intTobyte(aditem.getM_fontbacktransparency()));
         }
         else {        	 
        	 mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
	         mylist.add(intTobyte(0));
		}
         mylist.add(aditem.getM_fontno());
         mylist.add(intTobyte(aditem.getM_X1() / 256));//
         mylist.add(intTobyte(aditem.getM_X1() % 256));//
         mylist.add(intTobyte(aditem.getM_Y1() / 256));//
         mylist.add(intTobyte(aditem.getM_Y1() % 256));//
         mylist.add(intTobyte(aditem.getM_X2() / 256));//
         mylist.add(intTobyte(aditem.getM_X2() % 256));//
         mylist.add(intTobyte(aditem.getM_Y2() / 256));//
         mylist.add(intTobyte(aditem.getM_Y2() % 256));//
         mylist.add(aditem.getM_linkmove());
         mylist.add(aditem.getM_playtype());
         mylist.add(aditem.getM_playspeed());
         mylist.add(aditem.getM_rollstop());
         int dd = (int)Math.floor(aditem.getM_stoptime() *1000) ;
         byte[] tt = intToByteArray(dd);//周期         
         byteArrayInsList(mylist, tt);
         mylist.add(aditem.getM_looptime());
         
         mylist.add(aditem.getM_Gamma());//对比度
         //原9位保留位，改为总长度2byte+播放时长2byte（单位秒）+5byte保留
         mylist.add(intTobyte(aditem.getM_rollwidth() / 256));//
         mylist.add(intTobyte(aditem.getM_rollwidth() % 256));//
         mylist.add(intTobyte(aditem.getM_rolltimelenght() / 256));//
         mylist.add(intTobyte(aditem.getM_rolltimelenght() % 256));//
         
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));
         mylist.add(intTobyte(0));
         //byteArrayInsList(mylist, byte10);//保留10个字节
         switch (aditem.getM_type())
         {
             case 0://图文
                 {
                     int pos = mylist.size();//Convert.ToByte(aditem.texts.Count / 256)
                     mylist.add(intTobyte(0));// 图文总数
                     mylist.add(intTobyte(0));//
                     int ptcount=0;//重计图文总数

                     List<Byte> textlist = new ArrayList<Byte>();

                     for (int i = 0; i < aditem.getM_texts().size(); i++)
                     {
                         switch (aditem.getM_texts().get(i).getmTextType())
                         {
                             case 0://文字
                            	 try {
                            		 tt = aditem.getM_texts().get(i).getmTexts().getBytes("GBK");//内码文字内容										                                 

                                 for (int t = 0; t < tt.length; t++)
                                 {

                                     if (tt[t] > 127)//汉字                            
                                     {
                                         if ((tt[t] >= 0xA1 && tt[t] < 0xF7) && (tt[t + 1] >= 0xA1 && tt[t + 1] <= 0xFE)) //GB2312
                                         {
                                             textlist.add(tt[t++]);
                                             textlist.add(tt[t]);
                                         }
                                         else if ((tt[t] == 0xF7) && (tt[t + 1] >= 0xA1 && tt[t + 1] <= 0xEA))
                                         {
                                             mylist.add(tt[t++]);
                                             textlist.add(tt[t]);
                                         }
                                         else//非GB2312
                                         {
                                             if (textlist.size() != 0)
                                             {
                                                 mylist.add(intTobyte(0));//文字类型
                                                 mylist.add(intTobyte(textlist.size() / 256));//文字总数
                                                 mylist.add(intTobyte(textlist.size() % 256));//
                                                 mylist.add(intTobyte(0));//图片宽高
                                                 mylist.add(intTobyte(0));
                                                 mylist.add(intTobyte(0));
                                                 mylist.add(intTobyte(0));
                                                 mylist.addAll(textlist);//文字内容
                                                 ptcount++;//图文加一
                                                 textlist = new ArrayList<Byte>();//清空
                                             }

                                             byte[] printbyte = new byte[2];
                                             printbyte[t] = tt[t++];
                                             printbyte[t] = tt[t];

                                             textlist = new ArrayList<Byte>();
                                             textlist.add(intTobyte(2));//图片类型
                                             textlist.add(intTobyte(0));
                                             textlist.add(intTobyte(0));

                                             int fontsize = 0;
                                             switch (aditem.getM_fontno())
                                             {
                                                 case 0://16
                                                     fontsize = 16;
                                                     break;
                                                 case 1://24
                                                     fontsize = 24;
                                                     break;
                                                 case 2://32
                                                     fontsize = 32;
                                                     break;
                                                 default:
                                                     break;
                                             }

                                             BufferedImage bmp=new BufferedImage(fontsize, fontsize, BufferedImage.TYPE_INT_BGR);                                             
                                             textlist.add(intTobyte(fontsize / 256));// 图片宽高
                                             textlist.add(intTobyte(fontsize % 256));//
                                             textlist.add(intTobyte(fontsize / 256));//
                                             textlist.add(intTobyte(fontsize % 256));//

                                             Graphics graphics = bmp.getGraphics();//画图
                                             String prints = new String(printbyte,"GBK");

                                             graphics.setColor(aditem.getM_fontforecolor());// 在换成黑色 
                                             graphics.setFont(new Font("宋体",Font.PLAIN,fontsize));// 设置画笔字体 

                                             FontDesignMetrics metrics = FontDesignMetrics.getMetrics(new Font("宋体",Font.PLAIN,fontsize));
                                             
                                             graphics.drawString(prints, 0, metrics.getAscent());
                                             byteArrayInsList(textlist, picturetobyteCUNSE(bmp));                                             
                                             //bmp.Save("11.bmp");
                                             ptcount++;//图文加一
                                             mylist.addAll(textlist);
                                             textlist = new ArrayList<Byte>();//清空
                                         }
                                     }
                                     else
                                     {
                                         textlist.add(tt[t]);
                                     }
                                 }
                                 if (textlist.size() != 0)
                                 {
                                     mylist.add(intTobyte(0));//文字类型
                                     mylist.add(intTobyte(textlist.size() / 256));//文字总数
                                     mylist.add(intTobyte(textlist.size() % 256));//
                                     mylist.add(intTobyte(0));//图片宽高
                                     mylist.add(intTobyte(0));
                                     mylist.add(intTobyte(0));
                                     mylist.add(intTobyte(0));
                                     mylist.addAll(textlist);//文字内容
                                     ptcount++;//图文加一
                                     textlist = new ArrayList<Byte>();//清空
                                 }
                            	 } catch (Exception e) {
										// TODO: handle exception
									}
                                 break;
                             case 1:// 图片
                                /*暂时改动只取3
                                 textlist = new ArrayList<Byte>();
                                 textlist.add(intTobyte(1));//图片类型
                                 textlist.add(intTobyte(0));
                                 textlist.add(intTobyte(0));

                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() / 256));// 图片宽高
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() % 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() / 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() % 256));//
                                 byteArrayInsList(textlist, picturetobyte(aditem.getM_texts().get(i).getmPicture()));                                 
                                 ptcount++;//图文加一
                                 mylist.addAll(textlist);
                                 textlist = new ArrayList<Byte>();//清空
                                 
                                 break;
                                 */
                             case 2://带灰度图 8个像素点为一个字节       
                            	 /*暂时改动只取3
                            	  * 
                                 textlist = new ArrayList<Byte>();
                                 textlist.add(intTobyte(2));//图片类型
                                 textlist.add(intTobyte(0));
                                 textlist.add(intTobyte(0));

                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() / 256));// 图片宽高
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() % 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() / 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() % 256));//
                                 byteArrayInsList(textlist,picturetobyteCUNSE(aditem.getM_texts().get(i).getmPicture()));
                                 ptcount++;//图文加一
                                 mylist.addAll(textlist);
                                 textlist = new ArrayList<Byte>();//清空
                                 break;
                                 */
                             case 3://全彩图片RGB2byte                                 
                            	 textlist = new ArrayList<Byte>();
                            	 textlist.add(intTobyte(3));//图片类型
                                 textlist.add(intTobyte(0));
                                 textlist.add(intTobyte(0));

                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() / 256));// 图片宽高
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getWidth() % 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() / 256));//
                                 textlist.add(intTobyte(aditem.getM_texts().get(i).getmPicture().getHeight() % 256));//
                                 byteArrayInsList(textlist,picturetobyte2byte(aditem.getM_texts().get(i).getmPicture()));                                                                 
                                 
                                 ptcount++;//图文加一
                                 mylist.addAll(textlist);
                                 textlist = new ArrayList<Byte>();//清空                      
                                 break;
                             default:
                                 break;
                         }
                     }
                     mylist.set(pos++, intTobyte(ptcount / 256));
                     mylist.set(pos, intTobyte(ptcount % 256));                     
                     break;
                 }
             case 4:{
            	 byteArrayInsList(mylist,aditem.getM_GIFdata());  
            	 mylist.add(intTobyte(0));
            	 mylist.add(intTobyte(0));
            	 mylist.add(intTobyte(0));
            	 mylist.add(intTobyte(0));
            	 mylist.add(intTobyte(0));
             };break;
             default:
                 break;
         }

         tt = intToByteArray(mylist.size());//更改长度值
         mylist.set(0, tt[0]);
         mylist.set(1, tt[1]);
         mylist.set(2, tt[2]);
         mylist.set(3, tt[3]);

         return ListTobyte(mylist);
     }

     /// <summary> 
     ///生命周期
     /// </summary>
     private byte[] byteLife(String mDie)
     {
         byte[] life = { 0, 0, 0 };
         try
         {
        	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	 Date dd=format.parse(mDie);     
        	 
             Calendar calendar =new GregorianCalendar();
             calendar.setTime(dd);
             /*
             calendar.get(Calendar.YEAR)
             calendar.get(Calendar.MONTH)
             calendar.get(Calendar.DAY_OF_MONTH)
             calendar.get(Calendar.HOUR_OF_DAY)
             calendar.get(Calendar.MINUTE)
             calendar.get(Calendar.SECOND)
             */
             life[0] = (byte)(calendar.get(Calendar.YEAR) - 2000);//生命周期的范围2000年至2256年
             life[1] = (byte)(calendar.get(Calendar.MONTH) + 1);
             life[2] = (byte)(calendar.get(Calendar.DAY_OF_MONTH));
             return  life;

         }
         catch (Exception e)
         {
             return life;
         }
         
         
     }

     //时间值转换整形
     private int timevalue(String mDie)
     {
    	 try
         {
	         int tlong =0;
	         if (mDie != null)
	         {
	        	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	 Date dd=format.parse(mDie);    
	        	 
	        	 Calendar calendar =new GregorianCalendar();
	             calendar.setTime(dd);
	             
	             tlong = calendar.get(Calendar.HOUR_OF_DAY) * 3600;
	             tlong += calendar.get(Calendar.MINUTE)  * 60;
	             tlong += calendar.get(Calendar.SECOND);
	         }
	         return tlong;
         }
         catch (Exception e)
         {
             return 0;
         }
     }

     //时间值转换整形
     private int timevalueMM(String mDie)
     {
    	 try
         {
	         int tlong = 0;
	         if (mDie != null)
	         {
	        	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	 Date dd=format.parse(mDie);  
	        	 
	             tlong = dd.getHours() * 60;
	             tlong += dd.getMinutes();                
	         }
	         return tlong;
         }
         catch (Exception e)
         {
             return 0;
         }
     }
     
   /// <summary>
     /// 回信解码
     /// </summary>
     /// <param name="ReceiveByte"></param>
     /// <param name="ReceiveCls"></param>
     /// <returns></returns>
     public GJ_LedDecodingCls GJ_Decoding(byte[] ReceiveByte)
     {
         GJ_LedDecodingCls myRcls = new GJ_LedDecodingCls();
         GJ_LedDecodingCls ReceiveCls = myRcls;
         try
         {
             if (ReceiveByte.length < 24) { return null; }//长度太短不解析
             if ((ReceiveByte[0] != 0x7E) || (ReceiveByte[ReceiveByte.length - 1] != 0x7E)) { return null; }//不符合协议结构，7E-7E

             ReceiveByte = GJ_FanZhuanYi(ReceiveByte); //反转义
             byte[] tt = new byte[4];             
             System.arraycopy(ReceiveByte, 6,tt,0,4);//命令关键字 45协议关键字
             String Key = new String(tt,"GBK");
             switch (Key)
             {
                 case "PWON"://上电报告
                 //case "TIME"://校时                    
                 //case "LIGH"://亮屏
                 //case "BLAC"://黑屏
                 //case "ALAR"://报警
                 //case "UALM"://取消报警
                 case "DRIV"://智能顶灯行车状态
                 case "EVAL"://智能顶灯星级评价
                 case "QVES"://智能顶灯查询版本
                 case "UPDG"://智能顶灯升级(新)
                 case "QRIV"://智能顶灯查询状态
                 case "QSTR"://智能顶灯查询星级                	                 	 
                     myRcls.setMcommandword(GJ_CommandWordEnum.QSTR);
                     myRcls.setMreceiveflag(ReceiveByte[10]);//回信标志
                     if (myRcls.getMreceiveflag() != 0)
                     { return null; }//回信标志错误
                     myRcls.setMpageall((int)ReceiveByte[ReceiveByte.length -22]);//总包数
                     myRcls.setMpage((int)ReceiveByte[ReceiveByte.length - 21]);//包数
                     break;
                 default:
                     if (ReceiveByte.length < 61) { return null; }//长度太短不解析
                     if ((ReceiveByte[2] != 0x47) || (ReceiveByte[3] != 0x4A) || (ReceiveByte[ReceiveByte.length - 3] != 0x45) || (ReceiveByte[ReceiveByte.length - 2] != 0x4E))
                     { return null; }//不符合协议结构，GJ-EN                

                     int alldatalen = (int)(ReceiveByte[4]) * 256 + (int)(ReceiveByte[5]);
                     if (alldatalen != ReceiveByte.length - 3) { return null; }//数据长度错误
                     GJ_CRCcls crc = new GJ_CRCcls();//crc校验
                     int crcvalue = crc.CRC_16(ReceiveByte, 4, ReceiveByte.length - 6);
                     int crcvalue1 = (int)((int)(ReceiveByte[ReceiveByte.length - 5]) * 256 + (int)(ReceiveByte[ReceiveByte.length - 4]));//crc
                     if (crcvalue != crcvalue1) { return null; }//crc校验错
                     myRcls.setMdataflows(ReceiveByte[12]);//数据流向
                     if (myRcls.getMdataflows() != 0x0B) { return null; }//不是上行数据，短路等
                     myRcls.setMxieyiType(GJ_xieyiTypeEnum.getEnum((int)ReceiveByte[6]));//协议类型
                     myRcls.setMxieyiVersion(GJ_xieyiVersionEnum.getEnum((int)ReceiveByte[7]));//协议版本

                     myRcls.setMpageall((int)(ReceiveByte[8]) * 256 + (int)(ReceiveByte[9]));//总包数
                     myRcls.setMpage((int)(ReceiveByte[10]) * 256 + (int)(ReceiveByte[11]));//包数
                     myRcls.setMdatasource(ReceiveByte[13]);//数据来源
                     //保留10
                     tt = new byte[4];
                     System.arraycopy(ReceiveByte, 24,tt,0,4);//命令关键字                     
                     Key = new String(tt,"GBK");
                     GJ_CommandWordEnum commandWordEnum=GJ_CommandWordEnum.getEnum(Key);
                     if (commandWordEnum != null)
                     { myRcls.setMcommandword(commandWordEnum); }
                     else
                     { return null; }//不可识别指令
                     
                     myRcls.setMreceiveflag(ReceiveByte[28]);//回信标志
                     if (myRcls.getMreceiveflag() != 0)
                     { return null; }//回信标志错误

                     int Y = (int)(ReceiveByte[ReceiveByte.length - 35]) + 2000;
                     byte M = ReceiveByte[ReceiveByte.length - 34];
                     byte D = ReceiveByte[ReceiveByte.length - 33];

                     byte hh = ReceiveByte[ReceiveByte.length - 32];
                     byte mm = ReceiveByte[ReceiveByte.length - 31];
                     byte ss = ReceiveByte[ReceiveByte.length - 30];
                     String sdate, stime;
                     sdate = Y + "-" + String.format("%02",(int)M) + "-" + String.format("%02",(int)D) + " ";
                     stime = String.format("%02",(int)hh) + ":" + String.format("%02",(int)mm) + ":" + String.format("%02",(int)ss);
                     myRcls.setMledtime(sdate + stime);//屏内时间                
                     byte[] bytes = new byte[4];
                     bytes[0] = ReceiveByte[ReceiveByte.length - 26];
                     bytes[1] = ReceiveByte[ReceiveByte.length - 27];
                     bytes[2] = ReceiveByte[ReceiveByte.length - 28];
                     bytes[3] = ReceiveByte[ReceiveByte.length - 29];
                     myRcls.setMlednumber(byteArrayToInt(bytes));//屏号
                     //流水号
                     Y = (int)(ReceiveByte[ReceiveByte.length - 25]) + 2000;
                     M = ReceiveByte[ReceiveByte.length - 24];
                     D = ReceiveByte[ReceiveByte.length - 23];
                     hh = ReceiveByte[ReceiveByte.length - 22];
                     mm = ReceiveByte[ReceiveByte.length - 21];
                     ss = ReceiveByte[ReceiveByte.length - 20];
                     sdate = Y + "-" + String.format("%02",(int)M) + "-" + String.format("%02",(int)D) + " ";
                     stime = String.format("%02",(int)hh) + ":" + String.format("%02",(int)mm) + ":" + String.format("%02",(int)ss);
                     int fff = (int)(ReceiveByte[ReceiveByte.length - 19]) * 256 + (int)(ReceiveByte[ReceiveByte.length - 18]);
                     myRcls.setMserialnumber(sdate +stime + "."+fff);

                     break;
             }
             int timL;
             int p;
             switch (myRcls.getMcommandword())
             {
                 case alst://添加播放列表
                 case aadv://添加广告
                     myRcls.setMreceiveidvalue((int)(ReceiveByte[29]) * 256 + (int)(ReceiveByte[30]));//列表或广告id
                     break;
                 case qlst://查询播放列表
                     int listlen = (int)(ReceiveByte[29]) * 256 + (int)(ReceiveByte[30]);//id总数
                     myRcls.setMreceivelistid(new int[listlen]);
                     for (int i = 0; i < listlen; i++)
                     {
                         myRcls.getMreceivelistid()[i] = (int)(ReceiveByte[31+i*2]) * 256 + (int)(ReceiveByte[31+i*2+1]);
                     }
                     break;
                 case qadv://查询广告列表
                     listlen = (int)(ReceiveByte[29]) * 256 + (int)(ReceiveByte[30]);//id总数
                     myRcls.setMreceiveAdlist(new GJ_ReceiveADlistcls[listlen]);
                     for (int i = 0; i < listlen; i++)
                     {
                         myRcls.getMreceiveAdlist()[i] = new GJ_ReceiveADlistcls();//
                         myRcls.getMreceiveAdlist()[i].setMid((int)(ReceiveByte[31 + i * 4]) * 256 + (int)(ReceiveByte[31 + i * 4 + 1]));
                         myRcls.getMreceiveAdlist()[i].setMcrc((int)(ReceiveByte[31 + i * 4+2]) * 256 + (int)(ReceiveByte[31 + i * 4 + 3]));
                     }
                     break;
                 case dadv://删除广告
                     break;
                 case qver://查询版本
                     byte[] tt1 = new byte[25];
                     System.arraycopy(ReceiveByte, 29, tt1, 0, 25);                     
                     myRcls.setMreceivestrvalue(new String(tt1,"GBK"));
                     
                     break;
                 case blak://黑屏
                     break;
                 case ligt://亮屏
                     break;
                 case qled://查询工作状态                        
                 case rled://上报工作状态
                     
                     myRcls.getmWorkState().setmWorkVersion(ReceiveByte[29]);
                     //保留10
                     myRcls.getmWorkState().setmBorL(ReceiveByte[40]);
                     myRcls.getmWorkState().setmLightValue(ReceiveByte[41]);
                     myRcls.getmWorkState().setmFontState(ReceiveByte[42]);
                     myRcls.getmWorkState().setmLedSetState(ReceiveByte[43]);
                     myRcls.getmWorkState().setmLedSetId((int)(ReceiveByte[44]) * 256 + (int)(ReceiveByte[45]));
                     myRcls.getmWorkState().setmADCount((int)(ReceiveByte[46]) * 256 + (int)(ReceiveByte[47]));
                     myRcls.getmWorkState().setmListCount((int)(ReceiveByte[48]) * 256 + (int)(ReceiveByte[49]));
                     
                     GJ_PlayStateEnum gj_PlayStateEnum = GJ_PlayStateEnum.getEnum((int)ReceiveByte[50]);
                     if(gj_PlayStateEnum!=null)
                     {myRcls.getmWorkState().setmPlayState(gj_PlayStateEnum);}
                     myRcls.getmWorkState().setmPlayListId((int)(ReceiveByte[51]) * 256 + (int)(ReceiveByte[52]));
                     myRcls.getmWorkState().setmPlayADid((int)(ReceiveByte[53]) * 256 + (int)(ReceiveByte[54]));

                     tt1 = new byte[25];                     
                     System.arraycopy(ReceiveByte, 55, tt1, 0, 25);                                          
                     myRcls.getmWorkState().setmLEDversion(new String(tt1,"GBK"));
                     myRcls.getmWorkState().setmPlayMode(ReceiveByte[80]);
                     myRcls.getmWorkState().setmZhaoMingState(ReceiveByte[81]);

                     //
                     p  = 82;
                     if (myRcls.getmWorkState().getmWorkVersion() == 2)
                     {
                    	 myRcls.getmWorkState().setMset0(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset1(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset2(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset3(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset4(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset5(ReceiveByte[p++]);
                    	 myRcls.getmWorkState().setMset6(ReceiveByte[p++]);                         
                     }

                     
                     break;
                 case setl://屏幕设置
                     break;
                 case sstt://平台校时
                 case ddtt://终端校时
                 case TIME:
                     //年月日
                     String strtime = ((int)(ReceiveByte[29]) + 2000) + "-" + String.format("%02",(int)ReceiveByte[30]) + "-" + String.format("%02",(int)ReceiveByte[31]) + " ";
                     //时分秒
                     strtime += String.format("%02",(int)ReceiveByte[32]) + ":" + String.format("%02",(int)ReceiveByte[33]) + ":" + String.format("%02",(int)ReceiveByte[34]);
                     myRcls.setMreceivestrvalue(strtime);
                     break;
                 case alar://报警
                 case ALAR:
                     break;
                 case unar://取消报警
                 case UALM :
                     break;
                 case updd://升级
                     break;
                 case dele://清屏
                     break;
                 case powe ://上电报告
                     break;
                 case rest://复位
                 case rrtt://请求校时
                 case dlst://删除列表
                 case seto://屏幕设置（分）
                     break;
                 case qset://查询屏参                     
                     p =29;
                     myRcls.getmSetLed().setM_version((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     p += 10;
                     myRcls.getmSetLed().setM_test(ReceiveByte[p++]);
                     myRcls.getmSetLed().setM_width((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSetLed().setM_height((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSetLed().setM_setBrightnessMode(ReceiveByte[p++]);
                     myRcls.getmSetLed().setM_fixedBrightnessValue(ReceiveByte[p++]);
                     if (myRcls.getmSetLed().getM_setBrightnessMode() == 0)
                     {
                         p += 30;
                     }
                     else
                     {                            
                         for (int i=0;i<8;i++)
                         {
                             timL = (int)(ReceiveByte[p++])* 10;
                             myRcls.getmSetLed().getM_BrightnessTimeArray()[i] = String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60));
                         }
                         for (int i = 0; i < 8; i++)
                         {
                        	 myRcls.getmSetLed().getM_BrightnessValueArray()[i] = ReceiveByte[p++];
                         }

                         p += 12;
                     }
                     myRcls.getmSetLed().setM_BootstrapperWaitingTime(ReceiveByte[p++]);
                     myRcls.getmSetLed().setM_setZhaoMingMode(ReceiveByte[p++]);
                     if (myRcls.getmSetLed().getM_setZhaoMingMode() == 0)
                     { 
                         timL = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                         myRcls.getmSetLed().setM_ZhaoMingTimeStart(String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60)));
                         timL = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                         myRcls.getmSetLed().setM_ZhaoMingTimeEnd(String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60)));
                     }
                     else
                     {
                         p += 4;
                     }
                     myRcls.getmSetLed().setM_LinkTime((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSetLed().setM_ReceiveDelay((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSetLed().setM_MaxReceiveLen((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     tt = new byte[16];
                     System.arraycopy(ReceiveByte, p, tt, 0, 16);                                                                                  
                     myRcls.getmSetLed().setM_DefaulText(new String(tt,"GBK"));
                     p += 16;
                     tt = new byte[16];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 16);
                     myRcls.getmSetLed().setM_AlarmText(new String(tt,"GBK"));                     
                     p += 16;
                     myRcls.getmSetLed().setM_Energy(ReceiveByte[p++]);
                     myRcls.getmSetLed().setM_HDR(ReceiveByte[p++]);
                     p += 4;
                     myRcls.getmSetLed().setM_Showversion(ReceiveByte[p++]);
                     
                     break;
                 case DRIV://智能顶灯行车状态
                 case QRIV:
                     myRcls.setMreceiveidvalue((int)(ReceiveByte[11]));
                     break;
                 case EVAL ://设置智能顶灯星级
                 case QSTR://查询智能顶灯星级
                     myRcls.setMreceiveidvalue((int)(ReceiveByte[11]));
                     break;
                 case QVES://查询智能顶灯版本
                     tt1 = new byte[25];                     
                     System.arraycopy(ReceiveByte, 11, tt1, 0, 25);
                     myRcls.setMreceivestrvalue(new String(tt1,"GBK"));
                     break;
                 case UPDG :
                     myRcls.setMreceiveidvalue((int)(ReceiveByte[13]) * 256 + (int)(ReceiveByte[14]));//
                     break;
                 case qinf://查询信息列表
                     p = 29;
                     int playcount  = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     int advcount = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     myRcls.setmINFOlist(new GJ_INFOlistcls());
                     myRcls.getmINFOlist().setMplaylist(new int[playcount]);
                     myRcls.getmINFOlist().setMadvlist(new int[advcount]);
                     for (int i = 0; i < playcount; i++)
                     {
                         myRcls.getmINFOlist().getMplaylist()[i] = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     }
                     for (int i = 0; i < advcount; i++)
                     {
                         myRcls.getmINFOlist().getMadvlist()[i] = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     }
                     break;
                 case set1:
                 case set2:
                 case set3:
                 case set4:
                 case set6:
                     myRcls.setMreceiveidvalue(ReceiveByte[29]);//屏参设置id
                     break;
                 case set5:
                 case qss5:
                     p = 29;
                     myRcls.setmSet5(new GJ_Set5cls());
                     myRcls.getmSet5().set_id(ReceiveByte[p++]);
                     myRcls.getmSet5().set_version(ReceiveByte[p++]);;
                     myRcls.getmSet5().set_Terminaltype((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));

                     tt = new byte[20];                      
                     System.arraycopy(ReceiveByte, p, tt, 0, 20);                                                                                                        
                     myRcls.getmSet5().set_ESIM(new String(tt,"GBK"));
                     p += 20;
                     tt = new byte[12];
                     System.arraycopy(ReceiveByte, p, tt, 0, 12);                                                                                                        
                     myRcls.getmSet5().set_MCU(new String(tt,"GBK"));                     
                     p += 12;
                     tt = new byte[12];
                     System.arraycopy(ReceiveByte, p, tt, 0, 12);                                                                                                        
                     myRcls.getmSet5().set_DTU(new String(tt,"GBK"));                     
                     p += 12;
                     break;
                 case qss1:
                     p = 29;
                     myRcls.setmSet1(new GJ_Set1cls());
                     myRcls.getmSet1().set_id(ReceiveByte[p++]);
                     myRcls.getmSet1().set_version(ReceiveByte[p++]);
                     myRcls.getmSet1().set_Showversion(ReceiveByte[p++]);
                     myRcls.getmSet1().set_LinkTime(ReceiveByte[p++]);
                     myRcls.getmSet1().set_MaxReceiveLen((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSet1().set_BootstrapperWaitingTime(ReceiveByte[p++]);
                     myRcls.getmSet1().set_test(ReceiveByte[p++]);
                     myRcls.getmSet1().set_Energy(ReceiveByte[p++]);
                     myRcls.getmSet1().set_DTULink(ReceiveByte[p++]);
                     break;
                 case qss2:
                     p = 29;
                     myRcls.setmSet2(new GJ_Set2cls());
                     myRcls.getmSet2().set_id(ReceiveByte[p++]);
                     myRcls.getmSet2().set_version(ReceiveByte[p++]);
                     tt = new byte[16];
                     System.arraycopy(ReceiveByte, p, tt, 0, 16);                     
                     myRcls.getmSet2().set_DefaulText(new String(tt,"GBK"));
                     p += 16;
                     tt = new byte[16];
                     System.arraycopy(ReceiveByte, p, tt, 0, 16);                     
                     myRcls.getmSet2().set_AlarmText(new String(tt,"GBK"));                     
                     p += 16;
                     myRcls.getmSet2().set_setZhaoMingMode(ReceiveByte[p++]);
                     timL = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     myRcls.getmSet2().set_ZhaoMingTimeStart(String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60)));
                     timL = (int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]);
                     myRcls.getmSet2().set_ZhaoMingTimeEnd(String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60)));
                     break;
                 case qss3:
                     p = 29;
                     myRcls.setmSet3(new GJ_Set3cls());
                     myRcls.getmSet3().set_id(ReceiveByte[p++]);
                     myRcls.getmSet3().set_version(ReceiveByte[p++]);
                     p += 2;
                     for (int i = 0; i < 8; i++)
                     {
                         timL = (int)(ReceiveByte[p++]) * 10;
                         myRcls.getmSet3().get_BrightnessTimeArray()[i] = String.format("%02",(int)(timL / 60)) + ":" + String.format("%02",(int)(timL % 60));
                     }
                     for (int i = 0; i < 8; i++)
                     {
                         myRcls.getmSet3().get_BrightnessValueArray()[i] = ReceiveByte[p++];
                     }
                     p += 4;
                     break;
                 case qss4:
                     p = 29;
                     myRcls.setmSet4(new GJ_Set4cls());
                     myRcls.getmSet4().set_id(ReceiveByte[p++]);
                     myRcls.getmSet4().set_version(ReceiveByte[p++]);
                     myRcls.getmSet4().set_width((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     myRcls.getmSet4().set_height((int)(ReceiveByte[p++]) * 256 + (int)(ReceiveByte[p++]));
                     tt = new byte[20];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 20);
                     myRcls.getmSet4().set_projectnumber(new String(tt,"GBK"));
                     p += 20;
                     tt = new byte[20];
                     System.arraycopy(ReceiveByte, p, tt, 0, 20);
                     myRcls.getmSet4().set_adserverip(new String(tt,"GBK"));
                     p += 20;
                     tt = new byte[6];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 6);
                     myRcls.getmSet4().set_adserverport(new String(tt,"GBK"));
                     p += 6;
                     tt = new byte[20];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 20);
                     myRcls.getmSet4().set_gpsserverip(new String(tt,"GBK"));
                     p += 20;
                     tt = new byte[6];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 6);
                     myRcls.getmSet4().set_gpsserverport(new String(tt,"GBK"));
                     p += 6;
                     myRcls.getmSet4().set_gpsmode(ReceiveByte[p++]);
                     tt = new byte[15];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 15);
                     myRcls.getmSet4().set_APN(new String(tt,"GBK"));
                     p += 15;
                     tt = new byte[15];
                     System.arraycopy(ReceiveByte, p, tt, 0, 15);
                     myRcls.getmSet4().set_APNname(new String(tt,"GBK"));
                     p += 15;
                     tt = new byte[15];
                     System.arraycopy(ReceiveByte, p, tt, 0, 15);
                     myRcls.getmSet4().set_APNpassword(new String(tt,"GBK"));
                     p += 15;
                     break;
                 case qss6:
                     p = 29;
                     myRcls.setmSet6(new GJ_Set6cls());
                     myRcls.getmSet6().set_id(ReceiveByte[p++]);
                     myRcls.getmSet6().set_version(ReceiveByte[p++]);
                     tt = new byte[16];                     
                     System.arraycopy(ReceiveByte, p, tt, 0, 16);
                     myRcls.getmSet6().set_carnumber(new String(tt,"GBK"));
                     p += 16;
                     break;
                 default:
                     break;
             }

             return myRcls;
         }
         catch (Exception e)
         {
             
             return null;
         }
     }
}
