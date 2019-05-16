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
	
	@ResponseBody
	@RequestMapping(value = "/GetStatisticsData", method = RequestMethod.POST)    
    public JSONObject GetStatisticsData(@RequestParam("projectid") String projectid,HttpServletRequest request){
		try {
			//int adminid = adminInfoJsonObject.getIntValue("adminid");
			
			JSONObject JObject = mainSer.GetStatisticsData(projectid);
			//logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 发布广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			//logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/PublishInfobyid 发布广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/GetStatisticsDatabyDate", method = RequestMethod.POST)    
    public JSONObject GetStatisticsDatabyDate(@RequestParam("projectid") String projectid,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,HttpServletRequest request){
		try {
			//int adminid = adminInfoJsonObject.getIntValue("adminid");
			if(startDate.equals(""))
			{startDate = "1999-09-09";}
			if(endDate.equals(""))
			{endDate = "2100-09-09";}
			
			JSONObject JObject = mainSer.GetStatisticsDatabyDate(projectid, startDate, endDate);
			//logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 发布广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			//logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/PublishInfobyid 发布广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
		
	@ResponseBody
	@RequestMapping(value = "/GetUpdateRate", method = RequestMethod.POST)    
    public JSONObject GetUpdateRate(@RequestParam("groupid") int groupid,HttpServletRequest request){
		try {
			//int adminid = adminInfoJsonObject.getIntValue("adminid");
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			String projectid = adminInfoJsonObject.getString("projectid");
			JSONObject JObject = mainSer.GetUpdateRate(groupid,projectid);
			//logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 发布广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			//logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/PublishInfobyid 发布广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
}
