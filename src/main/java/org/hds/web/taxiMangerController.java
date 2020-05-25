package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IterminalMangerService;
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
public class taxiMangerController {

	@Autowired
	IterminalMangerService terminalMangerSer;
	@Autowired
	operateLog operateLog;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@RequestMapping(value = "/updateTerminalinfo", method = RequestMethod.GET)
	public String updateTerminalinfo(@RequestParam("DTUNo") String DTUNo, @RequestParam("groupName") String groupName,
			HttpServletRequest request) {
		if (DTUNo.trim().equals("") || groupName.trim().equals("")) {
			return "ERROR4";
		}
		return terminalMangerSer.updateTerminalinfobySerial(DTUNo.trim(), groupName.trim());
	}

	@RequestMapping("/taxiManger")
	public String projectManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/taxiManger Open");
			return "taxiManger";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getTerminalsbypageNum", method = RequestMethod.POST)
	public JSONObject getTerminalsbypageNum(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize, @RequestParam("searchString") String searchString,
			@RequestParam("issuperuser") int issuperuser, @RequestParam("projectid") String projectid,
			@RequestParam("adminname") String adminname, @RequestParam("groupids") String groupids,
			@RequestParam("adminlevel") int adminlevel, @RequestParam("sort") String sort,
			@RequestParam("sortOrder") String sortOrder, HttpServletRequest request) {
		try {
			JSONObject JSONObject = null;

			searchString = "%" + searchString + "%";
//			if (issuperuser == 0)// 普通用户
//			{
			JSONObject = terminalMangerSer.getTerminalsbyprojectid(pageNum, pageSize, projectid, searchString, groupids,
					adminlevel, sort, sortOrder);
//			} else {
//				JSONObject = terminalMangerSer.getTerminalsbypageNum(pageNum, pageSize, searchString, sort, sortOrder);
//			}

			// JSONObject = new { total = pagerInfo.RecordCount, rows = list };

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getTerminalsbypageNum===",
					"/getTerminalsbypageNum", logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getTerminalsbypageNum 异常:" + e.getMessage() + "===",
					"/getTerminalsbypageNum", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getTerminalsUpdate", method = RequestMethod.POST)
	public JSONObject getTerminalsUpdate(@RequestParam("Dtukeys") String Dtukeys,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JSONObject = null;
			JSONArray jArray = JSONArray.parseArray(Dtukeys);
			JSONObject = terminalMangerSer.getTerminalsUpdate(jArray);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getTerminalsUpdate===", "/getTerminalsUpdate",
					logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/getTerminalsUpdate 异常:" + e.getMessage() + "===",
					"/getTerminalsUpdate", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/taxiInfoEdit", method = RequestMethod.POST)
	public JSONObject taxiInfoEdit(@RequestParam("dtukey") String dtukey, @RequestParam("taxiname") String taxiname,
			@RequestParam("groupid") int groupid, @RequestParam("StarLevelset") int StarLevelset,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JSONObject = terminalMangerSer.updateTerminalInfo(dtukey, taxiname, groupid, StarLevelset);

			// JSONObject = new { total = pagerInfo.RecordCount, rows = list };
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/taxiInfoEdit dtukey:" + dtukey + "===",
					"/taxiInfoEdit", logger, true);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/taxiInfoEdit dtukey:" + dtukey + " 异常:" + e.getMessage() + "===",
					"/taxiInfoEdit", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/taxiInfoDelete", method = RequestMethod.POST)
	public JSONObject taxiInfoEdit(@RequestParam("dtukey") String dtukey, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JSONObject = terminalMangerSer.deleteTerminalInfo(dtukey);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/taxiInfoDelete dtukey:" + dtukey + "===",
					"/taxiInfoDelete", logger, true);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/taxiInfoDelete dtukey:" + dtukey + " 异常:" + e.getMessage() + "===",
					"/taxiInfoDelete", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/taxiInfoEditbyGroup", method = RequestMethod.POST)
	public JSONObject taxiInfoEditbyGroup(@RequestParam("groupid") int groupid,
			@RequestParam("StarLevelset") int StarLevelset, @RequestParam("issuperuser") int issuperuser,
			@RequestParam("projectid") String projectid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JSONObject = null;

			if (issuperuser == 0)// 普通用户
			{
				JSONObject = terminalMangerSer.updateTerminalInfobygroup(projectid, groupid, StarLevelset);
			} else {
				JSONObject = terminalMangerSer.updateTerminalInfobygroup(null, groupid, StarLevelset);
			}

			// JSONObject JSONObject = terminalMangerSer.updateTerminalInfobygroup(groupid,
			// StarLevelset);

			// JSONObject = new { total = pagerInfo.RecordCount, rows = list };
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + "/taxiInfoEditbyGroup groupid:" + groupid + "星级:" + StarLevelset + "===",
					"/taxiInfoEditbyGroup", logger, true);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/taxiInfoEditbyGroup groupid:" + groupid + "星级:"
					+ StarLevelset + " 异常:" + e.getMessage() + "===", "/taxiInfoEditbyGroup", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/gettGroupbyProjectid", method = RequestMethod.POST)
	public JSONArray getGroupbyProjectid(@RequestParam("projectid") String projectid,
			@RequestParam("adminInfo") String adminInfo, HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.parseObject(adminInfo);
		try {
			JSONArray JsonArray = terminalMangerSer.getGroupbyProjectid(projectid, jsonObject);
			operateLog.writeLog(jsonObject.getString("adminname"), 0,
					"===用户:" + jsonObject.getString("adminname") + "/getGroupbyProjectid===", "/getGroupbyProjectid",
					logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(jsonObject.getString("adminname"), 1,
					"===用户:" + jsonObject.getString("adminname") + "/getGroupbyProjectid 异常:" + e.getMessage() + "===",
					"/getGroupbyProjectid", logger, false);
			return null;
		}
	}
}
