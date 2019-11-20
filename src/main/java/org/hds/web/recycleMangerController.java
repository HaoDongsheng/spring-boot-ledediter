package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IAdvMangerService;
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
public class recycleMangerController {

	@Autowired
	IAdvMangerService advMangerSer;
	@Autowired
	operateLog operateLog;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/recycleManger")
	public String recycleManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/recycleManger Open");
			return "recycleManger";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getDelinfolistbypageNum", method = RequestMethod.POST)
	public JSONObject getDelinfolistbypageNum(@RequestParam("Grpid") int Grpid, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize, @RequestParam("adminname") String adminname,
			@RequestParam("sort") String sort, @RequestParam("sortOrder") String sortOrder,
			HttpServletRequest request) {
		try {
			JSONObject JSONObject = advMangerSer.getadvListDelbyGrpid(Grpid, pageNum, pageSize, sort, sortOrder);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getDelinfolistbypageNum===",
					"/getDelinfolistbypageNum", logger, false);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getDelinfolistbypageNum 异常:" + e.getMessage() + "===",
					"/getDelinfolistbypageNum", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/DeleteInfo2old", method = RequestMethod.POST)
	public JSONObject DeleteInfo2old(@RequestParam("infosn") int infosn, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JSONObject = advMangerSer.deleteInfo2old(infosn);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/DeleteInfo2old 广告id:" + infosn + "===",
					"/DeleteInfo2old", logger, true);
			return JSONObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/DeleteInfo2old 广告id" + infosn + " 异常:" + e.getMessage() + "===",
					"/DeleteInfo2old", logger, true);
			return null;
		}
	}
}
