package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IInfoListService;
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
public class userMangerController {
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired	
	IuserMangerService userMangerSer;
	JSONObject adminInfoJsonObject;
	@RequestMapping("/userManger")    
    public String userManger(Model model,HttpServletRequest request){	
		try
		{
			adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/userManger Open===");			
			return "userManger";
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/userManger Open 异常:"+e.getMessage()+"===");			
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST) 
    public JSONArray getUserList(HttpServletRequest request){	
		try
		{			
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			
			JSONArray JsonArray = userMangerSer.getUserList(adminInfoJsonObject);
			
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getUserList===");
			return JsonArray;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getUserList 异常:"+e.getMessage()+"===");			
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST) 
    public JSONArray getProjectList(HttpServletRequest request){	
		try
		{			
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
						
			JSONArray JsonArray = userMangerSer.getProjectList(adminInfoJsonObject);
			
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+"/getUserList===");
			return JsonArray;
		}
		catch(Exception e){
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/getUserList 异常:"+e.getMessage()+"===");			
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/CreatUser", method = RequestMethod.POST)    
    public JSONObject CreatUser(@RequestParam("adminname") String adminname,@RequestParam("adminpwd") String adminpwd,@RequestParam("adminstatus") int adminstatus,@RequestParam("expdate") String expdate,@RequestParam("adminpermission") String adminpermission,@RequestParam("projectid") String projectid,@RequestParam("inherit") int inherit,HttpServletRequest request){
		try {
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			
			int userid = adminInfoJsonObject.getIntValue("adminid");
			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			int userlevel = adminInfoJsonObject.getIntValue("adminlevel");			
			int userparentid = adminInfoJsonObject.getIntValue("parentid");			
			if(isSuperuser==1)
			{userlevel=1;}
			else {userlevel += 1;}
			JSONObject JObject =userMangerSer.CreatUser(adminname, adminpwd, adminstatus, expdate, adminpermission, projectid, inherit, userid, userlevel);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 新建用户:"+adminname+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/CreatUser 新建用户:"+adminname+" 异常:"+e.getMessage()+"===");			
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/EditUser", method = RequestMethod.POST)    
    public JSONObject EditUser(@RequestParam("adminid") int adminid,@RequestParam("adminname") String adminname,@RequestParam("adminpwd") String adminpwd,@RequestParam("adminstatus") int adminstatus,@RequestParam("expdate") String expdate,@RequestParam("adminpermission") String adminpermission,@RequestParam("inherit") int inherit,HttpServletRequest request){
		try {
			
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
									
			JSONObject JObject = userMangerSer.EditUser(adminid, adminname, adminpwd, adminstatus, expdate, adminpermission, inherit);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 编辑用户:"+adminname+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/EditUser 编辑用户:"+adminname+" 异常:"+e.getMessage()+"===");			
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)    
    public JSONObject DeleteUser(@RequestParam("adminid") int adminid,HttpServletRequest request){
		try {			
			JSONObject JObject = userMangerSer.DeleteUser(adminid);
			logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+" 删除用户id:"+adminid+" 返回结果:"+JObject.toJSONString()+"===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:"+adminInfoJsonObject.getString("adminname")+"/EditUser 删除用户id:"+adminid+" 异常:"+e.getMessage()+"===");			
			return null;
		}		   
    }
}
