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

import com.alibaba.fastjson.JSONArray;

@Controller
public class infoMangerController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IAdvMangerService advMangerSer;
	@Autowired
	operateLog operateLog;

	@RequestMapping("/infoManger")
	public String auditManger(Model model, HttpServletRequest request) {
		try {
			logger.info("/infoManger Open");
			return "infoManger";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getinfoListbyGrpidState", method = RequestMethod.POST)
	public JSONArray getinfoListbyGrpidState(@RequestParam("Grpid") int Grpid, @RequestParam("State") int State,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONArray JsonArray;
			if (State == -1) {
				JsonArray = advMangerSer.getadvListbyGrpid(Grpid);
			} else {
				JsonArray = advMangerSer.getadvListbyGrpidState(Grpid, 1);
			}

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getinfoListbyGrpidState===",
					"/getinfoListbyGrpidState", logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getinfoListbyGrpidState 异常:" + e.getMessage() + "===",
					"/getinfoListbyGrpidState", logger, false);
			return null;
		}
	}
}
