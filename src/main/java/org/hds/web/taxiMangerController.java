package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IprojectMangerService;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class taxiMangerController {

	@Autowired	
	IterminalMangerService terminalMangerSer;
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	JSONObject adminInfoJsonObject;
	
	@RequestMapping("/taxiManger")    
    public String projectManger(Model model,HttpServletRequest request){	
		try
		{
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/taxiManger Open");
			return "taxiManger";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getTerminalsbypageNum", method = RequestMethod.POST) 
    public JSONObject getTerminalsbypageNum(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,HttpServletRequest request){		
		try
		{					
			JSONObject JSONObject=null;
			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			if(isSuperuser==0)//普通用户
			{
				String projectid=adminInfoJsonObject.getString("projectid");
				JSONObject = terminalMangerSer.getTerminalsbyprojectid(pageNum, pageSize,projectid);				
			}
			else {
				JSONObject = terminalMangerSer.getTerminalsbypageNum(pageNum, pageSize);
			}
			
			//JSONObject = new { total = pagerInfo.RecordCount, rows = list };
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getTerminalsbypageNum===");
			return JSONObject;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getTerminalsbypageNum 异常:"+e.getMessage()+"===");
			return null;
		}
    }
}
