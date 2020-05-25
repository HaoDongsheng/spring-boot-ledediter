package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IprojectMangerService;
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
public class projectMangerController {

	@Autowired
	IprojectMangerService projectMangerSer;
	@Autowired
	operateLog operateLog;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/projectManger")
	public String projectManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/projectManger Open");
			return "projectManger";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getProjectlist", method = RequestMethod.POST)
	public JSONArray getProjectlist(@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONArray JsonArray = projectMangerSer.getProjectlist();
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getProjectlist===", "/getProjectlist", logger,
					false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/getProjectlist 异常:" + e.getMessage() + "===",
					"/getProjectlist", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupbyProjectid", method = RequestMethod.POST)
	public JSONArray getGroupbyProjectid(@RequestParam("projectid") String projectid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONArray JsonArray = projectMangerSer.getGroupbyProjectid(projectid);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getGroupbyProjectid===", "/getGroupbyProjectid",
					logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getGroupbyProjectid 异常:" + e.getMessage() + "===", "/getGroupbyProjectid",
					logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	public JSONObject createProject(@RequestParam("projectid") String projectid,
			@RequestParam("projectname") String projectname, @RequestParam("CheckCode") String CheckCode,
			@RequestParam("startlevelControl") int startlevelControl,
			@RequestParam("DefaultStartlevel") int DefaultStartlevel, @RequestParam("isOurModule") int isOurModule,
			@RequestParam("ConnectParameters") String ConnectParameters, @RequestParam("username") String username,
			@RequestParam("userpwd") String userpwd, @RequestParam("groupname") String groupname,
			@RequestParam("packLength") int packLength, @RequestParam("batchCount") int batchCount,
			@RequestParam("groupwidth") int groupwidth, @RequestParam("groupheight") int groupheight,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
//			CheckCode:$('#project_pwd').val(),
//        	startlevelControl:parseInt($("#select_start").val()),
//        	DefaultStartlevel:parseInt($("#project_startLevel").val()),
			JSONObject jsonObject = projectMangerSer.createProject(projectid, projectname, CheckCode, startlevelControl,
					DefaultStartlevel, isOurModule, ConnectParameters, username, userpwd, groupname, packLength,
					batchCount, groupwidth, groupheight);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/createProject 项目id:" + projectid + "===",
					"/createProject", logger, true);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/createProject 项目id:" + projectid + "异常:" + e.getMessage() + "===",
					"/createProject", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public JSONObject updateProject(@RequestParam("projectid") String projectid,
			@RequestParam("projectname") String projectname, @RequestParam("AutoGroupTo") int AutoGroupTo,
			@RequestParam("CheckCode") String CheckCode, @RequestParam("startlevelControl") int startlevelControl,
			@RequestParam("DefaultStartlevel") int DefaultStartlevel, @RequestParam("isOurModule") int isOurModule,
			@RequestParam("ConnectParameters") String ConnectParameters, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject jsonObject = projectMangerSer.updateProject(projectid, projectname, AutoGroupTo, CheckCode,
					startlevelControl, DefaultStartlevel, isOurModule, ConnectParameters);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/updateProject 项目id:" + projectid + "===",
					"/updateProject", logger, true);
			return jsonObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/updateProject 异常:" + e.getMessage() + "===");
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/updateProject 项目id:" + projectid + "异常:" + e.getMessage() + "===",
					"/updateProject", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateProjectlimit", method = RequestMethod.POST)
	public JSONObject updateProjectlimit(@RequestParam("projectid") String projectid,
			@RequestParam("projectLimit") String projectLimit, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject jsonObject = projectMangerSer.updateProjectlimit(projectid, projectLimit);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/updateProjectlimit 项目id:" + projectid + "===",
					"/updateProjectlimit", logger, true);
			return jsonObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/updateProjectlimit 异常:" + e.getMessage() + "===");
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/updateProjectlimit 项目id:" + projectid + "异常:" + e.getMessage() + "===",
					"/updateProject", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/removeProject", method = RequestMethod.POST)
	public JSONObject removeProject(@RequestParam("projectid") String projectid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			// String projectid, String projectname, String username, String userpwd, String
			// groupname, int groupwidth, int groupheight
			JSONObject jsonObject = projectMangerSer.removeProject(projectid);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/removeProject 项目id:" + projectid + "===",
					"/removeProject", logger, true);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/removeProject 项目id:" + projectid + "异常:" + e.getMessage() + "===",
					"/removeProject", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/moveGroup", method = RequestMethod.POST)
	public JSONObject moveGroup(@RequestParam("sprojectid") String sprojectid,
			@RequestParam("oprojectid") String oprojectid, @RequestParam("sgroupid") int sgroupid,
			@RequestParam("ogroupid") int ogroupid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject jsonObject = projectMangerSer.moveGroup(sprojectid, oprojectid, sgroupid, ogroupid);
			logger.info("===用户:" + adminname + "/moveGroup===");
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/moveGroup 原项目id" + sprojectid + "原分组id"
					+ sgroupid + "新项目id" + oprojectid + "新分组id" + ogroupid + "===", "/moveGroup", logger, true);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(
					adminname, 1, "===用户:" + adminname + "/moveGroup 异常:" + e.getMessage() + "原项目id" + sprojectid
							+ "原分组id" + sgroupid + "新项目id" + oprojectid + "新分组id" + ogroupid + "===",
					"/moveGroup", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/programlistChange", method = RequestMethod.POST)
	public JSONObject programlistChange(HttpServletRequest request) {
		try {
			JSONObject jsonObject = projectMangerSer.programlistChange();
			return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/passwordEnCode", method = RequestMethod.POST)
	public JSONObject passwordEnCode(HttpServletRequest request) {
		try {
			JSONObject jsonObject = projectMangerSer.passwordEnCode();
			return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/txt2db", method = RequestMethod.POST)
	public JSONObject txt2db(HttpServletRequest request) {
		try {
			// JSONObject jsonObject = projectMangerSer.txt2db();
			JSONObject jsonObject = projectMangerSer.execel2db();
			return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/codeChangeSingle", method = RequestMethod.POST)
	public JSONObject codeChangeSingle(HttpServletRequest request) {
		try {
			// JSONObject jsonObject = projectMangerSer.txt2db();
			JSONObject jsonObject = projectMangerSer.codeChange();

			operateLog.writeLog("", 0, "===用户: " + jsonObject.toJSONString() + "===", "/removeProject", logger, true);

			return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}
}
