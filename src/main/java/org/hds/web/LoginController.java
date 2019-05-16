package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.hds.service.IloginService;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController {
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	IloginService loginSer;
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)    
    public JSONObject login(@RequestParam("adminname") String adminname,@RequestParam("adminpwd") String adminpwd,HttpServletRequest request){
		try {
			JSONObject adminInfoJsonObject=loginSer.login(adminname, adminpwd);
			request.getSession().setAttribute("adminInfo", adminInfoJsonObject.getJSONObject("adminInfo"));
			//int MaxInactiveInterval = request.getSession().getMaxInactiveInterval();
			//session.setMaxInactiveInterval(30 * 60);  
			request.getSession().setMaxInactiveInterval(60 * 60);//设置单位为秒，设置为-1永不过期
			logger.info("===用户登录:"+adminname+"===");
			logger.info("===用户登录数据:" + adminInfoJsonObject.toJSONString()+"===");
			return adminInfoJsonObject;     
		} catch (Exception e) {
			logger.error("===用户:"+adminname+"/login 异常:"+e.getMessage()+"===");
			return null;
		}		   
    }    
		
	@RequestMapping("/index")    
    public String index(){
		logger.info("/Index Open");
		return "index";       
    }
}
