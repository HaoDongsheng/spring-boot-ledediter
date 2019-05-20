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
public class recycleMangerController {

	@Autowired
	IAdvMangerService advMangerSer;
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	JSONObject adminInfoJsonObject;
	
	@RequestMapping("/recycleManger")    
    public String recycleManger(Model model,HttpServletRequest request){	
		try
		{
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/recycleManger Open");
			return "recycleManger";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getDelinfolistbypageNum", method = RequestMethod.POST) 
    public JSONObject getDelinfolistbypageNum(@RequestParam("Grpid") int Grpid,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,HttpServletRequest request){		
		try
		{		
			if(adminInfoJsonObject==null)
			{adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");}
			JSONObject JSONObject = advMangerSer.getadvListDelbyGrpid(Grpid,pageNum,pageSize);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getDelinfolistbypageNum===");
			return JSONObject;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getDelinfolistbypageNum 异常:"+e.getMessage()+"===");
			return null;
		}
    }
		
}
