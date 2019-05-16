package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IInfoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class infoListController {

	final Logger logger=LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	IInfoListService infoListSer;
	JSONObject adminInfoJsonObject;
	
	@RequestMapping("/infoList")
    public String infoList(Model model,HttpServletRequest request){
		adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
		logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/infoList Open===");		
		return "infoList";       
    }
	
	@ResponseBody
	@RequestMapping(value = "/getGroup", method = RequestMethod.POST) 
    public JSONArray getGroup(HttpServletRequest request){	
		try
		{			
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");			
			JSONArray JsonArray = infoListSer.getGroup(adminInfoJsonObject);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getGroup===");
			return JsonArray;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getGroup 异常:"+e.getMessage()+"===");
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getInfoList", method = RequestMethod.POST)    
    public JSONArray GetInfoList(@RequestParam("groupid") int groupid,HttpServletRequest request){
		try {
			JSONArray JsonArray =infoListSer.GetInfoList(groupid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 获取广告播放列表返回结果:"+JsonArray.toJSONString()+"===");
			return JsonArray;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/GetInfoList 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/getInfopubs", method = RequestMethod.POST)    
    public JSONArray GetInfopubs(@RequestParam("groupid") int groupid,HttpServletRequest request){
		try {
			JSONArray JsonArray =infoListSer.GetInfopubs(groupid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 获取已发布广告信息返回结果:"+JsonArray.toJSONString()+"===");
			return JsonArray;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getInfopubs 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/CreatInfoList", method = RequestMethod.POST)    
    public JSONObject CreatInfoList(@RequestParam("listname") String listname,@RequestParam("groupid") int groupid,@RequestParam("listtype") int listtype,@RequestParam("quantums") String quantums,@RequestParam("ScheduleType") int ScheduleType,@RequestParam("programlist") String programlist,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject =infoListSer.CreatinfoList(listname,groupid,listtype,quantums,ScheduleType,programlist,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 新建播放列表:"+listname+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/CreatInfoList 新建播放列表:"+listname+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/UpdateInfoList", method = RequestMethod.POST)    
    public JSONObject UpdateInfoList(@RequestParam("listid") int listid,@RequestParam("listname") String listname,@RequestParam("groupid") int groupid,@RequestParam("listtype") int listtype,@RequestParam("quantums") String quantums,@RequestParam("ScheduleType") int ScheduleType,@RequestParam("programlist") String programlist,@RequestParam("isPublish") Boolean isPublish,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject = infoListSer.UpdateinfoList(listid,listname,groupid,listtype,quantums,ScheduleType,programlist,adminid);
			if(JObject.getString("result").equals("success"))
			{
				if(isPublish)//发布
				{
					int newlistid=JObject.getIntValue("returnid");
					if(newlistid==0)
					{newlistid = listid;}
					JSONObject JObjectp = infoListSer.PublishinfoList(newlistid,adminid);
					if(JObjectp.getString("result") == "success")
					{
						JObject.put("pubid", JObjectp.getString("pubid"));
					}
					else {
						JObject = JObjectp;
					}
				}
			}
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 更新播放列表:"+listname+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/UpdateInfoList 更新播放列表:"+listname+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
			
	@ResponseBody
	@RequestMapping(value = "/UpdatePlaylistName", method = RequestMethod.POST)    
    public JSONObject UpdatePlaylistName(@RequestParam("playlistid") int playlistid,@RequestParam("listname") String listname,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject =infoListSer.UpdatePlaylistName(playlistid,listname);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 新建播放列表:"+listname+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/UpdatePlaylistName 新建播放列表:"+listname+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/deleteplaylistbyid", method = RequestMethod.POST)    
    public JSONObject deleteplaylistbyid(@RequestParam("playlistid") int playlistid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject =infoListSer.DeleteinfoList(playlistid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 删除播放列表id:"+playlistid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/deleteplaylistbyid 删除播放列表id:"+playlistid+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/Publishplaylistbyid", method = RequestMethod.POST)    
    public JSONObject Publishplaylistbyid(@RequestParam("playlistid") int playlistid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject =infoListSer.PublishinfoList(playlistid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 发布播放列表id:"+playlistid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/Publishplaylistbyid 发布播放列表id:"+playlistid+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }	
	
	@ResponseBody
	@RequestMapping(value = "/getPublishListbyTemp", method = RequestMethod.POST)    
    public JSONObject getPublishListbyTemp(@RequestParam("listtype") int listtype,@RequestParam("quantums") String quantums,@RequestParam("ScheduleType") int ScheduleType,@RequestParam("programlist") String programlist,HttpServletRequest request){
		try {						
			JSONObject JObject = infoListSer.getbyteslistbyTemp(listtype,quantums,ScheduleType,programlist);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"串口发布播放列表 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getPublishListbyTemp 串口发布播放列表  异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/deleteinfobyid", method = RequestMethod.POST)    
    public JSONObject deleteinfobyid(@RequestParam("infosn") int infosn,@RequestParam("groupid") int groupid,@RequestParam("infopubid") int infopubid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject = infoListSer.Deleteinfobyid(infosn,groupid,infopubid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 删除已审核广告id:"+infosn+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/deleteinfobyid 删除已审核广告id:"+infosn+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/CopyInfotodraft", method = RequestMethod.POST)    
    public JSONObject CopyInfotodraft(@RequestParam("infosn") int infosn,@RequestParam("groupid") int groupid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject JObject = infoListSer.Copyinfobyid(infosn,groupid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 复制到草稿广告id:"+infosn+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/CopyInfotodraft 复制到草稿广告id:"+infosn+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/getinfoListbyinfoid", method = RequestMethod.POST)    
    public JSONObject getinfoListbyinfoid(@RequestParam("infosn") int infosn,HttpServletRequest request){
		try {			
			JSONObject JObject = infoListSer.GetInfocodebyid(infosn);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 串口发送广告id:"+infosn+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getinfoListbyinfoid 串口发送广告id:"+infosn+" 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
}
