package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IhistoryMangerService;
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

import com.alibaba.fastjson.JSONObject;

@Controller
public class historyMangerController {

	@Autowired
	IhistoryMangerService historyMangerSer;
	@Autowired
	operateLog operateLog;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/historyManger")
	public String historyManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/historyManger Open");
			return "historyManger";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/gethistorybypageNum", method = RequestMethod.POST)
	public JSONObject gethistorybypageNum(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
			@RequestParam("lifeAct") String lifeAct, @RequestParam("lifeDie") String lifeDie,
			@RequestParam("adminname") String adminname, @RequestParam("groupid") int groupid,
			@RequestParam("sort") String sort, @RequestParam("sortOrder") String sortOrder,
			HttpServletRequest request) {
		try {

			JSONObject JSONObject = historyMangerSer.gethistorybypageNum(pageNum, pageSize, lifeAct, lifeDie, groupid,
					sort, sortOrder);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/gethistorybypageNum===", "/gethistorybypageNum",
					logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/gethistorybypageNum 异常:" + e.getMessage() + "===", "/gethistorybypageNum",
					logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/gethistoryinfobypageNum", method = RequestMethod.POST)
	public JSONObject gethistoryinfobypageNum(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize, @RequestParam("lifeAct") String lifeAct,
			@RequestParam("lifeDie") String lifeDie, @RequestParam("adminname") String adminname,
			@RequestParam("groupid") int groupid, @RequestParam("sort") String sort,
			@RequestParam("sortOrder") String sortOrder, HttpServletRequest request) {
		try {
			JSONObject JSONObject = historyMangerSer.gethistorybyDate(pageNum, pageSize, lifeAct, lifeDie, groupid,
					sort, sortOrder);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/gethistorybypageNum===", "/gethistorybypageNum",
					logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/gethistorybypageNum 异常:" + e.getMessage() + "===", "/gethistorybypageNum",
					logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/RefreshHistoryData", method = RequestMethod.POST)
	public JSONObject RefreshHistoryData(@RequestParam("groupid") int groupid,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JSONObject = historyMangerSer.gethistorybyDate(startDate, endDate, groupid);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/RefreshHistoryData===", "/RefreshHistoryData",
					logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/RefreshHistoryData 异常:" + e.getMessage() + "===",
					"/RefreshHistoryData", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getplaylistinfo", method = RequestMethod.POST)
	public JSONObject getplaylistinfo(@RequestParam("playlistsn") String playlistsn,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = historyMangerSer.getplaylistinfobysn(playlistsn);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 列表id:" + playlistsn + "获取显示项返回结果:" + JObject.toJSONString() + "===",
					"/getplaylistinfo", logger, false);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/GetItem 列表id:" + playlistsn + "获取显示项异常:" + e.getMessage() + "===",
					"/getplaylistinfo", logger, false);
			return null;
		}
	}
}
