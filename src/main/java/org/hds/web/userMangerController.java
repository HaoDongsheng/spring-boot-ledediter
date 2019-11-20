package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IuserMangerService;
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
public class userMangerController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IuserMangerService userMangerSer;
	@Autowired
	operateLog operateLog;

	@RequestMapping("/userManger")
	public String userManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/userManger Open===");
			return "userManger";
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public JSONArray getUserList(@RequestParam("adminInfo") String adminInfo, HttpServletRequest request) {
		JSONObject adminInfoJsonObject = JSONObject.parseObject(adminInfo);
		try {
			JSONArray JsonArray = userMangerSer.getUserList(adminInfoJsonObject);

			operateLog.writeLog(adminInfoJsonObject.getString("adminname"), 0,
					"===用户:" + adminInfoJsonObject.getString("adminname") + "/getUserList===", "/getUserList", logger,
					false);
			return JsonArray;
		} catch (Exception e) {

			operateLog.writeLog(adminInfoJsonObject.getString("adminname"), 1,
					"===用户:" + adminInfoJsonObject.getString("adminname") + "/getUserList 异常:" + e.getMessage() + "===",
					"/getUserList", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getProjectListbyuser", method = RequestMethod.POST)
	public JSONArray getProjectList(@RequestParam("adminInfo") String adminInfo, HttpServletRequest request) {
		JSONObject adminInfoJsonObject = JSONObject.parseObject(adminInfo);
		try {
			// JSONObject adminInfoJsonObject = (JSONObject)
			// request.getSession().getAttribute("adminInfo");

			JSONArray JsonArray = userMangerSer.getProjectList(adminInfoJsonObject);

			operateLog.writeLog(adminInfoJsonObject.getString("adminname"), 0,
					"===用户:" + adminInfoJsonObject.getString("adminname") + "/getProjectListbyuser===",
					"/getProjectListbyuser", logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(
					adminInfoJsonObject.getString("adminname"), 1, "===用户:" + adminInfoJsonObject.getString("adminname")
							+ "/getProjectListbyuser 异常:" + e.getMessage() + "===",
					"/getProjectListbyuser", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CreatUser", method = RequestMethod.POST)
	public JSONObject CreatUser(@RequestParam("adminname") String adminname, @RequestParam("adminpwd") String adminpwd,
			@RequestParam("adminstatus") int adminstatus, @RequestParam("expdate") String expdate,
			@RequestParam("adminpermission") String adminpermission, @RequestParam("admingrps") String admingrps,
			@RequestParam("projectid") String projectid, @RequestParam("inherit") int inherit,
			@RequestParam("adminInfo") String adminInfo, HttpServletRequest request) {
		try {
			JSONObject adminInfoJsonObject = JSONObject.parseObject(adminInfo);

			int userid = adminInfoJsonObject.getIntValue("adminid");
			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			int userlevel = adminInfoJsonObject.getIntValue("adminlevel");
			int userparentid = adminInfoJsonObject.getIntValue("parentid");
			if (isSuperuser == 1) {
				userlevel = 1;
			} else {
				userlevel += 1;
			}
			JSONObject JObject = userMangerSer.CreatUser(adminname, adminpwd, adminstatus, expdate, adminpermission,
					admingrps, projectid, inherit, userid, userlevel);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 新建用户:" + adminname + " 返回结果:" + JObject.toJSONString() + "===",
					"/CreatUser", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CreatUser 新建用户:" + adminname + " 异常:" + e.getMessage() + "===",
					"/CreatUser", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/EditUser", method = RequestMethod.POST)
	public JSONObject EditUser(@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			@RequestParam("adminpwd") String adminpwd, @RequestParam("adminstatus") int adminstatus,
			@RequestParam("expdate") String expdate, @RequestParam("adminpermission") String adminpermission,
			@RequestParam("admingrps") String admingrps, @RequestParam("inherit") int inherit,
			@RequestParam("padminname") String padminname, HttpServletRequest request) {
		try {
			JSONObject JObject = userMangerSer.EditUser(adminid, adminname, adminpwd, adminstatus, expdate,
					adminpermission, admingrps, inherit);
			operateLog.writeLog(padminname, 0,
					"===用户:" + padminname + " 编辑用户:" + adminname + " 返回结果:" + JObject.toJSONString() + "===",
					"/EditUser", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(padminname, 1,
					"===用户:" + padminname + "/EditUser 编辑用户:" + adminname + " 异常:" + e.getMessage() + "===",
					"/EditUser", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
	public JSONObject DeleteUser(@RequestParam("adminid") int adminid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = userMangerSer.DeleteUser(adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 删除用户id:" + adminid + " 返回结果:" + JObject.toJSONString() + "===",
					"/DeleteUser", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + "/EditUser 删除用户id:" + adminid + " 异常:" + e.getMessage() + "===",
					"/DeleteUser", logger, true);
			return null;
		}
	}
}
