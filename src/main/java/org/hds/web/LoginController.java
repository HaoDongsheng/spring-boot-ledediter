package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IloginService;
import org.hds.service.impl.operateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IloginService loginSer;
	@Autowired
	operateLog operateLog;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(@RequestParam("adminname") String adminname, @RequestParam("adminpwd") String adminpwd,
			HttpServletRequest request) {
		try {
			JSONObject adminInfoJsonObject = loginSer.login(adminname, adminpwd);
//			request.getSession().setAttribute("adminInfo", adminInfoJsonObject.getJSONObject("adminInfo"));
//			// int MaxInactiveInterval = request.getSession().getMaxInactiveInterval();
//			// session.setMaxInactiveInterval(30 * 60);
//			request.getSession().setMaxInactiveInterval(60 * 60);// 设置单位为秒，设置为-1永不过期
			operateLog.writeLog(adminname, 0,
					"===用户登录:" + adminname + "返回结果:" + adminInfoJsonObject.toJSONString() + "===", "/login", logger,
					true);
			return adminInfoJsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/login 异常:" + e.getMessage() + "===", "/login",
					logger, true);
			return null;
		}
	}

	@RequestMapping("/index")
	public String index() {
		logger.info("/Index Open");
		return "index";
	}
}
