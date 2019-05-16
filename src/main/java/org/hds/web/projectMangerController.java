package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IprojectMangerService;
import org.hds.service.IuserMangerService;
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
public class projectMangerController {

	@Autowired	
	IprojectMangerService projectMangerSer;
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	JSONObject adminInfoJsonObject;
	
	@RequestMapping("/projectManger")    
    public String projectManger(Model model,HttpServletRequest request){	
		try
		{
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/projectManger Open");
			return "projectManger";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getProjectlist", method = RequestMethod.POST) 
    public JSONArray getProjectlist(HttpServletRequest request){		
		try
		{						
			JSONArray JsonArray = projectMangerSer.getProjectlist();
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getProjectlist===");
			return JsonArray;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getProjectlist 异常:"+e.getMessage()+"===");
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/createProject", method = RequestMethod.POST) 
    public JSONObject createProject(@RequestParam("projectid") String projectid,@RequestParam("projectname") String projectname, @RequestParam("isOurModule") int isOurModule, @RequestParam("ConnectParameters") String ConnectParameters,@RequestParam("username") String username,@RequestParam("userpwd") String userpwd,@RequestParam("groupname") String groupname,@RequestParam("groupwidth") int groupwidth,@RequestParam("groupheight") int groupheight,HttpServletRequest request){		
		try
		{						
			//String projectid, String projectname, String username, String userpwd, String groupname, int groupwidth, int groupheight
			JSONObject jsonObject = projectMangerSer.createProject(projectid, projectname, isOurModule, ConnectParameters, username, userpwd, groupname, groupwidth, groupheight);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/createProject===");
			return jsonObject;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/createProject 异常:"+e.getMessage()+"===");
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST) 
    public JSONObject updateProject(@RequestParam("projectid") String projectid,@RequestParam("projectname") String projectname, @RequestParam("isOurModule") int isOurModule, @RequestParam("ConnectParameters") String ConnectParameters,HttpServletRequest request){		
		try
		{									
			JSONObject jsonObject = projectMangerSer.updateProject(projectid, projectname, isOurModule, ConnectParameters);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/createProject===");
			return jsonObject;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/createProject 异常:"+e.getMessage()+"===");
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/removeProject", method = RequestMethod.POST) 
    public JSONObject removeProject(@RequestParam("projectid") String projectid,HttpServletRequest request){		
		try
		{						
			//String projectid, String projectname, String username, String userpwd, String groupname, int groupwidth, int groupheight
			JSONObject jsonObject = projectMangerSer.removeProject(projectid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/removeProject===");
			return jsonObject;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/removeProject 异常:"+e.getMessage()+"===");
			return null;
		}
    }
}
