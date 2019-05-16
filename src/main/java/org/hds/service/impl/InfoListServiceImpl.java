package org.hds.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hds.NettyClient;
import org.hds.GJ_coding.GJ_LoopADidlistcls;
import org.hds.GJ_coding.GJ_Playlistcls;
import org.hds.GJ_coding.GJ_Schedulingcls;
import org.hds.GJ_coding.GJ_Templatecls;
import org.hds.GJ_coding.GJ_Timequantumcls;
import org.hds.GJ_coding.GJ_codingCls;
import org.hds.GJ_coding.GJ_xieyiTypeEnum;
import org.hds.GJ_coding.GJ_xieyiVersionEnum;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.projectMapper;
import org.hds.model.advertisement;
import org.hds.model.infocode;
import org.hds.model.item;
import org.hds.model.playlist;
import org.hds.model.playlistcode;
import org.hds.model.group;
import org.hds.service.IInfoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.awt.image.GifImageDecoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class InfoListServiceImpl implements IInfoListService {

	@Autowired
	groupMapper groupMapper;
	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	itemMapper itemMapper;
	@Autowired
	playlistcodeMapper playlistcodeMapper;
	@Autowired
	infocodeMapper infocodeMapper;	
	@Autowired
	getCode getCode;
	
	@Override
	public JSONArray getGroup(JSONObject adminInfoJsonObject)
	{
		JSONArray jArray=new JSONArray();
		try
		{
			int isSuperuser=adminInfoJsonObject.getInteger("issuperuser");		
			if(isSuperuser==0){
				String projectid =adminInfoJsonObject.getString("projectid");

				List<group> grplist = groupMapper.selectbyProjectid(projectid);
				
				for(int i=0;i<grplist.size();i++)
				{					
					group tg=grplist.get(i);
					int grpid=tg.getGroupid();
					String grpName = tg.getGroupname();
					int screenwidth=tg.getscreenwidth();
					int screenheight=tg.getscreenheight();
					JSONObject jgrp=new JSONObject();
					jgrp.put("grpid", grpid);
					jgrp.put("grpname", grpName);
					jgrp.put("screenwidth", screenwidth);
					jgrp.put("screenheight", screenheight);
					jArray.add(jgrp);
				}			
			}
			else {
				List<group> tgrpsList = groupMapper.selectAll();
				for(int i=0;i<tgrpsList.size();i++)
				{
					int grpid = tgrpsList.get(i).getGroupid();
					String grpName=tgrpsList.get(i).getGroupname();
					int screenwidth=tgrpsList.get(i).getscreenwidth();
					int screenheight=tgrpsList.get(i).getscreenheight();
					JSONObject jgrp=new JSONObject();
					jgrp.put("grpid", grpid);
					jgrp.put("grpname", grpName);
					jgrp.put("screenwidth", screenwidth);
					jgrp.put("screenheight", screenheight);
					jArray.add(jgrp);
				}
			}
			return jArray;
		}
		catch(Exception ex)
		{
			return jArray;
		}
	}
	
	@Override
	public JSONArray GetInfopubs(int groupid)
	{
		JSONArray jArray=new JSONArray();
		try
		{		
			List<advertisement> advertisements = advertisementMapper.selectpubBygroupid(groupid);
			
			for(int i=0;i<advertisements.size();i++)
			{
				int id = advertisements.get(i).getinfoSN();
				String Advname = advertisements.get(i).getAdvname();
				String lifeAct = advertisements.get(i).getlifeAct();
				String lifeDie = advertisements.get(i).getlifeDie();
				int playTimelength = advertisements.get(i).getplayTimelength();
				int Pubidid = advertisements.get(i).getPubid();

				
				JSONObject jadv=new JSONObject();
				jadv.put("id", id);
				jadv.put("Advname", Advname);
				jadv.put("lifeAct", lifeAct);
				jadv.put("lifeDie", lifeDie);
				jadv.put("playTimelength", playTimelength);
				jadv.put("Pubidid", Pubidid);
			
				jArray.add(jadv);
			}
			
			return jArray;
		}
		catch(Exception ex)
		{
			return jArray;
		}
	}
	
	@Override
	public JSONArray GetInfoList(int groupid)
	{
		JSONArray jArray=new JSONArray();
		try
		{		
			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);
			
			for(int i=0;i<playlists.size();i++)
			{
				int id = playlists.get(i).getplaylistSN();
				int Delindex = playlists.get(i).getDelindex();
				int Grpid = playlists.get(i).getGroupid();
				int Playlistlevel = playlists.get(i).getPlaylistlevel();
				int Scheduletype = playlists.get(i).getScheduletype();
				String Playlistlifeact = playlists.get(i).getPlaylistlifeact();
				String Playlistlifedie = playlists.get(i).getPlaylistlifedie();
				String Playlistname = playlists.get(i).getPlaylistname();
				String Programlist = playlists.get(i).getProgramlist();
				String Timequantum = playlists.get(i).getTimequantum();
				String pubid = playlists.get(i).getPubid();
				
				JSONObject jgrp=new JSONObject();
				jgrp.put("id", id);
				jgrp.put("Delindex", Delindex);
				jgrp.put("Grpid", Grpid);
				jgrp.put("Playlistlevel", Playlistlevel);
				jgrp.put("Scheduletype", Scheduletype);
				jgrp.put("Playlistlifeact", Playlistlifeact);
				jgrp.put("Playlistlifedie", Playlistlifedie);
				jgrp.put("Playlistname", Playlistname);
				jgrp.put("Programlist", Programlist);
				jgrp.put("Timequantum", Timequantum);
				jgrp.put("pubid", pubid);
				jArray.add(jgrp);
			}
			
			return jArray;
		}
		catch(Exception ex)
		{
			return jArray;
		}
	}
	
	@Override
	public JSONObject CreatinfoList(String listname,int groupid,int listtype,String quantums,int ScheduleType,String programlist,int adminid)
	{
		JSONObject jObject=new JSONObject();
		try
		{	
			Object objid = playlistMapper.selectidByName(listname, groupid);
			if(objid==null)
			{
				playlist playlist=new playlist();
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact("");
				playlist.setPlaylistlifedie("");
				playlist.setcreater(adminid);
				Date now = new Date(); 				       
				DateFormat d1 = DateFormat.getDateTimeInstance();
				playlist.setcreateDate(d1.format(now));
				playlist.setPlaylistname(listname);
				playlist.setProgramlist(programlist);
				playlist.setPubid("0");
				playlist.setScheduletype(ScheduleType);
				playlist.setTimequantum(quantums);
				
				int rowCount = playlistMapper.insert(playlist);
				if(rowCount>0)
				{
					int returnid=playlist.getplaylistSN();
					
					jObject.put("result", "success");
					jObject.put("infoListID", returnid);	
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "列表名称:"+listname+"写入数据库失败!");					
				}
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:"+listname+"已存在!");
			}
		
			return jObject;
		}
		catch(Exception e)
		{
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	/*
	 * 判断删除的列表中含有的广告，是否唯一，如果唯一记录广告下架时间
	 */
	private void deletePublishInfo(JSONArray oldplArray, int groupid, int adminid) {
		try {			
			List<advertisement> advlist=new ArrayList<advertisement>();
			for(int j=0;j<oldplArray.size();j++)
			{
				int oldinfoid = oldplArray.getJSONObject(j).getIntValue("infoid");
				//判断删除的广告在同组其他播放列表中是否存在
				boolean isExist=false;
				List<Object> pList = playlistMapper.selectprogramlistbygroupid(groupid);
				for(int i=0;i<pList.size();i++)
				{
					JSONArray plArray=JSONArray.parseArray(pList.get(i).toString());
					for(int p=0;p<plArray.size();p++)
					{
						int infoid = plArray.getJSONObject(p).getIntValue("infoid");
						if(oldinfoid==infoid)
						{
							isExist=true;break;
						}
					}
				}
				//其他播放列表中不存在广告，确定删除
				if(!isExist)
				{
					Date now = new Date(); 				       
					DateFormat d1 = DateFormat.getDateTimeInstance();
					
					advertisementMapper.updatedeletedateByid(oldinfoid, adminid, d1.format(now));
					
					advlist.add(advertisementMapper.selectByPrimaryKey(oldinfoid));
				}
			}
			
			if(advlist.size()>0)
			{NettyClient.TcpSocketSendPublish(null,advlist);}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public JSONObject UpdateinfoList(int listid,String listname,int groupid,int listtype,String quantums,int ScheduleType,String programlist,int adminid)
	{
		JSONObject jObject=new JSONObject();
		try
		{
		String plpubid = playlistMapper.selectpubidid(listid).toString();
		playlist oldpl = playlistMapper.selectByPrimaryKey(listid);		
		
		if(!plpubid.equals("0"))
		{
			//已发布广告，删除原有的播放列表创新新播放列表
			playlistcodeMapper.deleteByplaylistSN(listid);
			
			Date now = new Date(); 				       
			DateFormat d1 = DateFormat.getDateTimeInstance();
			
			playlistMapper.updatedelindexByPrimaryKey(listid,1,adminid,d1.format(now));
			
			//判断删除排期中的广告是否需要删除
			JSONArray oldplArray=JSONArray.parseArray(oldpl.getProgramlist());
			deletePublishInfo(oldplArray, oldpl.getGroupid(), adminid);
			
			playlist playlist=new playlist();
			playlist.setDelindex(0);
			playlist.setGroupid(groupid);
			playlist.setPlaylistlevel(listtype);
			playlist.setPlaylistlifeact("");
			playlist.setPlaylistlifedie("");
			playlist.setcreater(adminid);			
			playlist.setcreateDate(d1.format(now));
			playlist.setPlaylistname(listname);
			playlist.setProgramlist(programlist);
			playlist.setPubid("0");
			playlist.setScheduletype(ScheduleType);
			playlist.setTimequantum(quantums);
			
			int rowCount = playlistMapper.insert(playlist);
			if(rowCount>0)
			{
				int returnid=playlist.getplaylistSN();
				jObject.put("result", "success");
				jObject.put("returnid", returnid);
				
			}
			else {	
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:"+listname+"写入数据库失败!");
			}						
		}
		else {	
			//未发布广告直接保存
			playlist playlist=new playlist();
			playlist.setDelindex(0);
			playlist.setGroupid(groupid);
			playlist.setId(listid);
			playlist.setPlaylistlevel(listtype);
			playlist.setPlaylistlifeact("");
			playlist.setPlaylistlifedie("");
			playlist.setPlaylistname(listname);
			playlist.setProgramlist(programlist);
			playlist.setPubid("0");
			playlist.setScheduletype(ScheduleType);
			playlist.setTimequantum(quantums);
			
			int rowCount = playlistMapper.updateByPrimaryKeySelective(playlist);
			if(rowCount>0)
			{	
				jObject.put("result", "success");
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:"+listname+"写入数据库失败!");
			}
		}		
		return jObject;
		}
		catch(Exception e)
		{
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject UpdatePlaylistName(int playlistid,String listname)
	{
		JSONObject jObject=new JSONObject();
		try
		{
			playlist playlist=new playlist();			
			playlist.setId(playlistid);
			playlist.setPlaylistname(listname);
			
			int rowCount = playlistMapper.updateByPrimaryKeySelective(playlist);
			if(rowCount>0)
			{	
				jObject.put("result", "success");
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:"+listname+"写入数据库失败!");
			}
			
			return jObject;
		}
		catch(Exception e)
		{
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject Copyinfobyid(int infosn,int groupid,int adminid)
	{
		JSONObject jObject=new JSONObject();
		try {			
			advertisement adv= advertisementMapper.selectByPrimaryKey(infosn);
			int i=1;
			String newInfoNameString="";
			while(true)
			{
				if(advertisementMapper.selectCountByName(adv.getAdvname() + i,groupid)<=0)
				{
					newInfoNameString=adv.getAdvname() + i;
					break;
				}
				i+=1;
			}
			
			if(newInfoNameString!="")
			{
				advertisement advertisement=new advertisement();
				advertisement.setAdvname(newInfoNameString);
				advertisement.setlifeAct(adv.getlifeAct());
				advertisement.setlifeDie(adv.getlifeDie());
				advertisement.setAdvtype("0");	
				advertisement.setinfoState(0);
				advertisement.setcreater(adminid);
				Date now = new Date(); 				       
				DateFormat d1 = DateFormat.getDateTimeInstance();
				advertisement.setcreateDate(d1.format(now));
				advertisement.setinfoState(0);
				advertisement.setBackgroundstyle(adv.getBackgroundstyle());
				advertisement.setDelindex(0);
				advertisement.setGroupid(groupid);
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);
				advertisement.setplayTimelength(adv.getplayTimelength());//默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if(rowCount>0)
				{
					int newinfoSN=advertisement.getinfoSN();
					List<item> itemlist = itemMapper.selectByadid(infosn);				
					for(int j=0;j<itemlist.size();j++)
					{
						item olditem = itemlist.get(j);
						
						item item=new item();
						item.setInfoSN(newinfoSN);
						item.setPageid(olditem.getPageid());
						item.setItemid(olditem.getItemid());
						item.setItemtype(olditem.getItemtype());
						item.setItemleft(olditem.getItemleft());
						item.setItemtop(olditem.getItemtop());
						item.setItemwidth(olditem.getItemwidth());
						item.setItemheight(olditem.getItemheight());
						item.setItemfontno(olditem.getItemfontno());
						item.setItembackcolor(olditem.getItembackcolor());
						item.setItembackopacity(olditem.getItembackopacity());
						item.setItemforecolor(olditem.getItemforecolor());
						item.setItemforeopacity(olditem.getItemforeopacity());
						item.setDelindex(0);
						item.setItemstyle(olditem.getItemstyle());						
						item.setItemcontext(olditem.getItemcontext());
						item.setItemcontextjson(olditem.getItemcontextjson());
						itemMapper.insert(item);
						
						jObject.put("result", "success");
					}										
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "复制写入数据库失败!");
				}
				
			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject Deleteinfobyid(int infosn,int groupid,int infopubid,int adminid)
	{
		JSONObject jObject=new JSONObject();
		try {
			List<playlistcode> plcodes = playlistcodeMapper.selectByGroupid(groupid);
			boolean isF=false;
			if(plcodes!=null && plcodes.size()>0)
			{				
				for(int i=0;i<plcodes.size();i++)
				{
					if(plcodes.get(i).getContainADID()!=null && plcodes.get(i).getContainADID()!="")
					{
					String ContainADID = plcodes.get(i).getContainADID().trim();
					String[] arrADID = ContainADID.split(",");
					for(int j=0;j<arrADID.length;j++)
					{
						if(Integer.parseInt(arrADID[j]) == infopubid)
						{isF=true;break;}
					}
					if(isF){break;}
					}
				}
			}
			if(isF)
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除广告在已发布广告列表中存在不能删除!");
			}
			else {
				advertisementMapper.updateDelindexByid(infosn, 1);
				infocodeMapper.deleteByinfoSN(infosn);
				jObject.put("result", "success");
			}			
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject GetInfocodebyid(int infosn)
	{
		JSONObject jObject=new JSONObject();
		try {
			JSONArray infocodelist=new JSONArray();
			int ADID=100;						
			List<infocode> ifcodeList = infocodeMapper.selectByinfoSN(infosn);
			for(int j=0;j<ifcodeList.size();j++)
			{
				String Codecontext = ifcodeList.get(j).getCodecontext();
				JSONArray ctArray=JSONArray.parseArray(Codecontext);
				infocodelist.addAll(ctArray);
				ADID = ifcodeList.get(j).getPubid();
			}
			
			List<byte[]> SendplByteList = getCode.GetplaylistCodeListbyid(ADID);
			
			JSONArray pjsonArray=new JSONArray();				
			for(int i=0;i<SendplByteList.size();i++)
			{
				byte[] bytes=SendplByteList.get(i);					
				StringBuilder buf = new StringBuilder(bytes.length * 2);
		        for(byte b : bytes) { // 使用String的format方法进行转换
		            buf.append(String.format("%02x", new Integer(b & 0xff))+" ");
		        }		
		        pjsonArray.add(buf.toString().trim());
			}
			
			jObject.put("result", "success");
			jObject.put("infocodelist", infocodelist);
			jObject.put("plcodelist", pjsonArray);
			
			return jObject;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public JSONObject DeleteinfoList(int listid,int adminid)
	{
		JSONObject jObject=new JSONObject();
		try {
			playlist oldpl = playlistMapper.selectByPrimaryKey(listid);	
			Date now = new Date(); 				       
			DateFormat d1 = DateFormat.getDateTimeInstance();
			playlistMapper.updatedelindexByPrimaryKey(listid,1,adminid,d1.format(now));
			int plcCount = playlistcodeMapper.selectcoutByplaylistSN(listid);
			if(plcCount>0)
			{
				playlistcodeMapper.deleteByplaylistSN(listid);
				
				JSONArray oldplArray=JSONArray.parseArray(oldpl.getProgramlist());
				
				deletePublishInfo(oldplArray, oldpl.getGroupid(), adminid);							
			}			
			
			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject PublishinfoList(int listid,int adminid)
	{
 		JSONObject jObject=new JSONObject();
		try {			
			playlist playlist = playlistMapper.selectByPrimaryKey(listid);
			String programlist = playlist.getProgramlist();
			if(programlist == null || programlist == "")
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}
			if(playlist.getScheduletype()==2 && playlist.getPlaylistlevel()!=0)
			{
				JSONArray jArrayLoop =JSONArray.parseArray(programlist);
				JSONArray jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop);
				
				int returnIndex=0;
				String pidStrings = "";				
				if(jArrayinfo!=null && jArrayinfo.size()>0)
				{
					playlistcodeMapper.deleteByplaylistSN(listid);//发布之前删除播放列表编码数据
					for(int i=0;i<jArrayinfo.size();i++)
					{
						int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());
						pidStrings += plpid + ",";
						JSONObject jsonObject=jArrayinfo.getJSONObject(i);						
						int retplaylistIndex = getCode.GetplaylistCodeListbyid(listid,jsonObject,plpid,playlist.getPlaylistlevel(),playlist.getTimequantum(),playlist.getGroupid());
						if(retplaylistIndex!=0){returnIndex=retplaylistIndex;break;}
					}
				}
				
				if(returnIndex==0)
				{
					Date now = new Date(); 				       
					DateFormat d1 = DateFormat.getDateTimeInstance();
					
					List<advertisement> advlist=new ArrayList<advertisement>();
					
					if(programlist!=null && programlist!="")
					{						
						JSONArray plArray = JSONArray.parseArray(programlist);
						for(int i=0;i<plArray.size();i++)
						{
							int infoid = plArray.getJSONObject(i).getIntValue("infosn");
							advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoid);
							advlist.add(advertisement);
							Object publishDate =advertisementMapper.selectpublishDateByPrimaryKey(infoid);
							if(publishDate==null)
							{							
								advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
							}
						}
					}
					if(advlist.size()>0)
					{NettyClient.TcpSocketSendPublish(advlist, null);}
					
					if(pidStrings.length()>0)
					{
						pidStrings = pidStrings.substring(0, pidStrings.length() - 1);
					}					
					
					playlistMapper.updatemutipubidByPrimaryKey(listid, pidStrings, adminid, d1.format(now),jArrayinfo.toJSONString());
					
					jObject.put("result", "success");
					jObject.put("pubid", pidStrings);
				}
				else {	
					playlistcodeMapper.deleteByplaylistSN(listid);//发布失败删除播放列表编码数据
					jObject.put("result", "fail");
					jObject.put("resultMessage", "播放列表编码错误");
				}				
			}
			else {			
				int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());//每次发布重新生成发布id
				
				Date now = new Date(); 				       
				DateFormat d1 = DateFormat.getDateTimeInstance();
							
				int retplaylistIndex = getCode.GetplaylistCodeListbyid(playlist,plpid,playlist.getGroupid());
				if(retplaylistIndex==0)
				{
					if(programlist!=null && programlist!="")
					{
						JSONArray plArray = JSONArray.parseArray(programlist);
						for(int i=0;i<plArray.size();i++)
						{
							int infoid = plArray.getJSONObject(i).getIntValue("infoid");
							Object publishDate =advertisementMapper.selectpublishDateByPrimaryKey(infoid);
							if(publishDate==null)
							{							
								advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
							}
						}
					}
					
					playlistMapper.updatepubidByPrimaryKey(listid, Integer.toString(plpid), adminid, d1.format(now));
					
					jObject.put("result", "success");
					jObject.put("pubid", Integer.toString(plpid));
				}
				else {	
					jObject.put("result", "fail");
					jObject.put("resultMessage", "播放列表编码错误");
				}
			}						
						
			return jObject;
			
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
	
	@Override
	public JSONObject getbyteslistbyTemp(int listtype,String quantums,int ScheduleType,String programlist)
	{		
		JSONObject jObject=new JSONObject();
		try {						
			if(programlist == null || programlist == "")
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}
			
			JSONArray jsonArray=new JSONArray();
			
			if(ScheduleType==2)
			{
				JSONArray jArrayLoop =JSONArray.parseArray(programlist);
				JSONArray jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop);
													
				if(jArrayinfo!=null && jArrayinfo.size()>0)
				{					
					for(int j=0;j<jArrayinfo.size();j++)
					{												
						JSONObject jsonObject=jArrayinfo.getJSONObject(j);						
						List<byte[]> SendByteList = getCode.GetplaylistCodeListbyid(listtype,ScheduleType,100 + j,quantums,jsonObject.toJSONString());
						
						if(SendByteList!=null)
						{									
							for(int i=0;i<SendByteList.size();i++)
							{
								byte[] bytes=SendByteList.get(i);					
								StringBuilder buf = new StringBuilder(bytes.length * 2);
						        for(byte b : bytes) { // 使用String的format方法进行转换
						            buf.append(String.format("%02x", new Integer(b & 0xff))+" ");
						        }		
						        jsonArray.add(buf.toString().trim());
							}										
						}
						else {
							jObject.put("result", "fail");
							jObject.put("resultMessage", "编码错误");
							
							return jObject;
						}
					}
				}												
			}
			else {														
				List<byte[]> SendByteList = getCode.GetplaylistCodeListbyid(listtype,ScheduleType,100,quantums,programlist);
				if(SendByteList!=null)
				{									
					for(int i=0;i<SendByteList.size();i++)
					{
						byte[] bytes=SendByteList.get(i);					
						StringBuilder buf = new StringBuilder(bytes.length * 2);
				        for(byte b : bytes) { // 使用String的format方法进行转换
				            buf.append(String.format("%02x", new Integer(b & 0xff))+" ");
				        }		
				        jsonArray.add(buf.toString().trim());
					}										
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "编码错误");
					
					return jObject;
				}
			}
			
			JSONArray jArrayLoop = JSONArray.parseArray(programlist);			
			
			JSONArray infocodelist=new JSONArray();
			List<Integer> conInfoidList=new ArrayList<Integer>(); 
			for(int i=0;i<jArrayLoop.size();i++)
			{
				int infoid = jArrayLoop.getJSONObject(i).getIntValue("infosn");
				if(!conInfoidList.contains(infoid))
				{
					conInfoidList.add(infoid);
					List<infocode> ifcodeList = infocodeMapper.selectByinfoSN(infoid);
					for(int j=0;j<ifcodeList.size();j++)
					{
						String Codecontext = ifcodeList.get(j).getCodecontext();
						JSONArray ctArray=JSONArray.parseArray(Codecontext);
						infocodelist.addAll(ctArray);
					}
				}
			}
			
			jObject.put("result", "success");
			jObject.put("infocodelist", infocodelist);
			jObject.put("plcodelist", jsonArray);
						
			return jObject;
			
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}
	}
}
