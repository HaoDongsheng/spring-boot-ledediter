package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IterminalMangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class taxiMangerController {

	@Autowired
	IterminalMangerService terminalMangerSer;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

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
			@RequestParam("sort") String sort, @RequestParam("sortOrder") String sortOrder,
			HttpServletRequest request) {
		try {
			JSONObject JSONObject = null;

			searchString = "%" + searchString + "%";
			if (issuperuser == 0)// 普通用户
			{
				JSONObject = terminalMangerSer.getTerminalsbyprojectid(pageNum, pageSize, projectid, searchString,
						groupids, sort, sortOrder);
			} else {
				JSONObject = terminalMangerSer.getTerminalsbypageNum(pageNum, pageSize, searchString, sort, sortOrder);
			}

			// JSONObject = new { total = pagerInfo.RecordCount, rows = list };
			logger.info("===用户:" + adminname + "/getTerminalsbypageNum===");
			return JSONObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/getTerminalsbypageNum 异常:" + e.getMessage() + "===");
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
			logger.info("===用户:" + adminname + "/taxiInfoEdit===");
			return JSONObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/taxiInfoEdit 异常:" + e.getMessage() + "===");
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
			logger.info("===用户:" + adminname + "/taxiInfoEdit===");
			return JSONObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/taxiInfoEdit 异常:" + e.getMessage() + "===");
			return null;
		}
	}
}
