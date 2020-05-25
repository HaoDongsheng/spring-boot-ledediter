package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IInfoListService;
import org.hds.service.impl.operateLog;
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

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IInfoListService infoListSer;
	@Autowired
	operateLog operateLog;

	@RequestMapping("/infoList")
	public String infoList(Model model, HttpServletRequest request) {
		return "infoList";
	}

	@ResponseBody
	@RequestMapping(value = "/getGroup", method = RequestMethod.POST)
	public JSONArray getGroup(@RequestParam("adminInfo") String adminInfo, HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.parseObject(adminInfo);
		try {
			JSONArray JsonArray = infoListSer.getGroup(jsonObject);
			operateLog.writeLog(jsonObject.getString("adminname"), 0,
					"===用户:" + jsonObject.getString("adminname") + "/getGroup===", "/getGroup", logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(jsonObject.getString("adminname"), 1,
					"===用户:" + jsonObject.getString("adminname") + "/getGroup 异常:" + e.getMessage() + "===",
					"/getGroup", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getInfoList", method = RequestMethod.POST)
	public JSONArray GetInfoList(@RequestParam("groupid") int groupid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONArray JsonArray = infoListSer.GetInfoList(groupid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 获取广告播放列表返回结果:" + JsonArray.toJSONString() + "===", "/getInfoList", logger,
					false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/GetInfoList 异常:" + e.getMessage() + "===",
					"/getInfoList", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getInfopubs", method = RequestMethod.POST)
	public JSONArray GetInfopubs(@RequestParam("groupid") int groupid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONArray JsonArray = infoListSer.GetInfopubs(groupid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 获取已发布广告信息返回结果:" + JsonArray.toJSONString() + "===", "/getInfopubs", logger,
					false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/getInfopubs 异常:" + e.getMessage() + "===",
					"/getInfopubs", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CreatInfoList", method = RequestMethod.POST)
	public JSONObject CreatInfoList(@RequestParam("listname") String listname, @RequestParam("groupid") int groupid,
			@RequestParam("listtype") int listtype, @RequestParam("quantums") String quantums,
			@RequestParam("ScheduleType") int ScheduleType, @RequestParam("lifeAct") String lifeAct,
			@RequestParam("lifeDie") String lifeDie, @RequestParam("programlist") String programlist,
			@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.CreatinfoList(listname, groupid, listtype, quantums, ScheduleType, lifeAct,
					lifeDie, programlist, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 新建播放列表:" + listname + " 返回结果:" + JObject.toJSONString() + "===",
					"/CreatInfoList", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CreatInfoList 新建播放列表:" + listname + " 异常:" + e.getMessage() + "===",
					"/CreatInfoList", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/UpdateInfoList", method = RequestMethod.POST)
	public JSONObject UpdateInfoList(@RequestParam("listid") String listid, @RequestParam("listname") String listname,
			@RequestParam("groupid") int groupid, @RequestParam("listtype") int listtype,
			@RequestParam("quantums") String quantums, @RequestParam("ScheduleType") int ScheduleType,
			@RequestParam("lifeAct") String lifeAct, @RequestParam("lifeDie") String lifeDie,
			@RequestParam("programlist") String programlist, @RequestParam("isPublish") Boolean isPublish,
			@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.UpdateinfoList(listid, listname, groupid, listtype, quantums, ScheduleType,
					lifeAct, lifeDie, programlist, adminid);
			if (JObject.getString("deleMessage") != null) {
				operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 更新播放列表:" + listname + " 返回删除旧列表结果:"
						+ JObject.getString("deleMessage") + "===", "/UpdateInfoList", logger, true);
			}
			if (JObject.getString("result").equals("success")) {
				if (isPublish)// 发布
				{
					String newlistid = JObject.getString("returnid");

					if (newlistid == null || newlistid.equals("0")) {
						newlistid = listid;
					}
					logger.info("===用户:" + adminname + " 获取新播放列表sn:" + newlistid + "===");

					JSONObject JObjectp = null;

					JObjectp = infoListSer.PublishinfoListbyGroupid(newlistid, adminid, groupid);

					if (JObjectp.getString("result") == "success") {
						JObject.put("pubid", JObjectp.getString("pubid"));
						JObject.put("changeInfo", JObjectp.getString("changeInfo"));
						operateLog.writeLog(adminname, 0,
								"===用户:" + adminname + " 新播放列表发布编码成功 发布id:" + JObjectp.getString("pubid") + "===",
								"/UpdateInfoList", logger, true);
					} else {
						JObject = JObjectp;
					}
				}
			}
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 更新播放列表:" + listname + " 返回结果:" + JObject.toJSONString() + "===",
					"/UpdateInfoList", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/UpdateInfoList 更新播放列表:" + listname + " 异常:" + e.getMessage() + "===",
					"/UpdateInfoList", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/UpdatePlaylistName", method = RequestMethod.POST)
	public JSONObject UpdatePlaylistName(@RequestParam("playlistid") String playlistid,
			@RequestParam("listname") String listname, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.UpdatePlaylistName(playlistid, listname);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 新建播放列表:" + listname + " 返回结果:" + JObject.toJSONString() + "===",
					"/UpdatePlaylistName", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/UpdatePlaylistName 新建播放列表:" + listname + " 异常:" + e.getMessage() + "===",
					"/UpdatePlaylistName", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateRemarks", method = RequestMethod.POST)
	public JSONObject updateRemarks(@RequestParam("infosn") String infosn, @RequestParam("remarks") String remarks,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.UpdateRemarks(infosn, remarks);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 修改备注:" + remarks + " 返回结果:" + JObject.toJSONString() + "===",
					"/UpdatePlaylistName", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/updateRemarks 修改备注:" + remarks + " 异常:" + e.getMessage() + "===",
					"/UpdatePlaylistName", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteplaylistbyid", method = RequestMethod.POST)
	public JSONObject deleteplaylistbyid(@RequestParam("playlistid") String playlistid,
			@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.DeleteinfoList(playlistid, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 删除播放列表id:" + playlistid + " 返回结果:" + JObject.toJSONString() + "===",
					"/deleteplaylistbyid", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/deleteplaylistbyid 删除播放列表id:" + playlistid
					+ " 异常:" + e.getMessage() + "===", "/deleteplaylistbyid", logger, true);
			return null;
		}
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/Publishplaylistbyid", method = RequestMethod.POST)
	 * public JSONObject Publishplaylistbyid(@RequestParam("playlistid") int
	 * playlistid,@RequestParam("adminid") int adminid,@RequestParam("adminname")
	 * String adminname,HttpServletRequest request){ try { JSONObject JObject
	 * =infoListSer.PublishinfoList(playlistid,adminid);
	 * logger.info("===用户:"+adminname+" 发布播放列表id:"+playlistid+" 返回结果:"+JObject.
	 * toJSONString()+"==="); return JObject; } catch (Exception e) {
	 * logger.error("===用户:"+adminname+"/Publishplaylistbyid 发布播放列表id:"
	 * +playlistid+" 异常:"+e.getMessage()+"==="); return null; } }
	 */
	@ResponseBody
	@RequestMapping(value = "/getPublishListbyTemp", method = RequestMethod.POST)
	public JSONObject getPublishListbyTemp(@RequestParam("listtype") int listtype,
			@RequestParam("quantums") String quantums, @RequestParam("ScheduleType") int ScheduleType,
			@RequestParam("programlist") String programlist, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.getbyteslistbyTemp(listtype, quantums, ScheduleType, programlist);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "串口发布播放列表 返回结果:" + JObject.toJSONString() + "===",
					"/getPublishListbyTemp", logger, false);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getPublishListbyTemp 串口发布播放列表  异常:" + e.getMessage() + "===",
					"/getPublishListbyTemp", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteinfobyid", method = RequestMethod.POST)
	public JSONObject deleteinfobyid(@RequestParam("infosn") String infosn, @RequestParam("groupid") int groupid,
			@RequestParam("infopubid") int infopubid, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.Deleteinfobyid(infosn, groupid, infopubid, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 删除已审核广告id:" + infosn + " 返回结果:" + JObject.toJSONString() + "===",
					"/deleteinfobyid", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/deleteinfobyid 删除已审核广告id:" + infosn + " 异常:" + e.getMessage() + "===",
					"/deleteinfobyid", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CopyInfotodraft", method = RequestMethod.POST)
	public JSONObject CopyInfotodraft(@RequestParam("infosn") String infosn, @RequestParam("groupid") int groupid,
			@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.Copyinfobyid(infosn, groupid, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 复制到草稿广告id:" + infosn + " 返回结果:" + JObject.toJSONString() + "===",
					"/CopyInfotodraft", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CopyInfotodraft 复制到草稿广告id:" + infosn + " 异常:" + e.getMessage() + "===",
					"/CopyInfotodraft", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getinfoListbyinfoid", method = RequestMethod.POST)
	public JSONObject getinfoListbyinfoid(@RequestParam("infosn") String infosn,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = infoListSer.GetInfocodebyid(infosn);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 串口发送广告id:" + infosn + " 返回结果:" + JObject.toJSONString() + "===",
					"/getinfoListbyinfoid", logger, false);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getinfoListbyinfoid 串口发送广告id:" + infosn + " 异常:" + e.getMessage() + "===",
					"/getinfoListbyinfoid", logger, false);
			return null;
		}
	}
}
