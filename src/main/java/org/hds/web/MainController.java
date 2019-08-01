package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.ImainService;
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
	@Autowired ImainService mainSer;
	
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	@RequestMapping("/main")    
    public String main(Model model,HttpServletRequest request){	
		try
		{			
			logger.info("/main Open");
			return "main";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/GetStatisticsData", method = RequestMethod.POST)    
    public JSONObject GetStatisticsData(@RequestParam("projectid") String projectid,HttpServletRequest request){
		try {			
			JSONObject JObject = mainSer.GetStatisticsData(projectid);			
			return JObject;
		} catch (Exception e) {			
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/GetStatisticsDatabyDate", method = RequestMethod.POST)    
    public JSONObject GetStatisticsDatabyDate(@RequestParam("projectid") String projectid,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,HttpServletRequest request){
		try {
			if(startDate.equals(""))
			{startDate = "1999-09-09";}
			if(endDate.equals(""))
			{endDate = "2100-09-09";}
			
			JSONObject JObject = mainSer.GetStatisticsDatabyDate(projectid, startDate, endDate);			
			return JObject;
		} catch (Exception e) {			
			return null;
		}		   
    }
		
	@ResponseBody
	@RequestMapping(value = "/GetUpdateRate", method = RequestMethod.POST)    
    public JSONObject GetUpdateRate(@RequestParam("projectid") String projectid,@RequestParam("groupid") int groupid,HttpServletRequest request){
		try {			
			JSONObject JObject = mainSer.GetUpdateRate(groupid,projectid);			
			return JObject;
		} catch (Exception e) {
			return null;
		}		   
    }
}
