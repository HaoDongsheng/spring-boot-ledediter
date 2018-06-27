package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MainController {
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	@RequestMapping("/main")    
    public String main(Model model,HttpServletRequest request){	
		try
		{
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/main Open");
			return "main";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
}
