package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IAdvMangerService;
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
public class auditMangerController {

	@Autowired
	IAdvMangerService advMangerSer;
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	JSONObject adminInfoJsonObject;
	
	@RequestMapping("/auditManger")    
    public String auditManger(Model model,HttpServletRequest request){	
		try
		{
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/auditManger Open");
			return "auditManger";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getadvListbyGrpidState", method = RequestMethod.POST) 
    public JSONArray getadvListbyGrpidState(@RequestParam("Grpid") int Grpid,HttpServletRequest request){		
		try
		{						
			JSONArray JsonArray = advMangerSer.getadvListbyGrpidState(Grpid,1);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getadvListbyGrpidState===");
			return JsonArray;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getadvListbyGrpidState 异常:"+e.getMessage()+"===");
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/PublishInfobyid", method = RequestMethod.POST)    
    public JSONObject PublishInfobyid(@RequestParam("infoid") int infoid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			
			JSONObject JObject =advMangerSer.Publishinfobyid(infoid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 发布广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/PublishInfobyid 发布广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/RefuseInfobyid", method = RequestMethod.POST)    
    public JSONObject RefuseInfobyid(@RequestParam("infoid") int infoid,HttpServletRequest request){
		try {
			int adminid = adminInfoJsonObject.getIntValue("adminid");
			
			JSONObject JObject =advMangerSer.RefuseInfobyid(infoid,adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 拒绝广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/RefuseInfobyid 拒绝广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/getInfobytesbyid", method = RequestMethod.POST)    
    public JSONObject getInfobytesbyid(@RequestParam("infoid") int infoid,HttpServletRequest request){
		try {			
						
			JSONObject JObject =advMangerSer.getbyteslistbyid(infoid);
			
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 获取编码广告id:"+infoid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getInfobytesbyid 获取编码广告id:"+infoid+"异常:"+e.getMessage()+"===");
			return null;
		}		   
    }
}
