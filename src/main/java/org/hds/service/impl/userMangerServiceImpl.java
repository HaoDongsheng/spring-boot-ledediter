package org.hds.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hds.mapper.projectMapper;
import org.hds.mapper.userMapper;
import org.hds.model.project;
import org.hds.model.user;
import org.hds.service.IuserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class userMangerServiceImpl implements IuserMangerService {

	@Autowired	
	userMapper adminMapper;
	@Autowired	
	projectMapper projectMapper;
	@Override
	public JSONArray getProjectList(JSONObject adminInfoJsonObject)
	{
		try {
			JSONArray JsonArray = null;
			
			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			if(isSuperuser==0)//普通用户
			{
				String projectid = adminInfoJsonObject.getString("projectid");
				project project	= projectMapper.selectByPrimaryKey(projectid);			
				List<project> projectlist = new ArrayList<project>();
				projectlist.add(project);
				
				JsonArray = JSONArray.parseArray(JSON.toJSONString(projectlist));
			}
			else//超级用户
			{
				List<project> projectlist = projectMapper.selectAll();
				JsonArray = JSONArray.parseArray(JSON.toJSONString(projectlist));
			}
			
			return JsonArray;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public JSONArray getUserList(JSONObject adminInfoJsonObject)
	{
		try {
			JSONArray JsonArray = null;
			
			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			if(isSuperuser==0)//普通用户
			{
				int adminid=adminInfoJsonObject.getIntValue("adminid");
				List<user> adminlist = adminMapper.selectByParentid(adminid);
				JsonArray = JSONArray.parseArray(JSON.toJSONString(adminlist));
			}
			else//超级用户
			{
				List<user> adminlist = adminMapper.selectAll();
				JsonArray = JSONArray.parseArray(JSON.toJSONString(adminlist));
			}
			
			return JsonArray;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public JSONObject CreatUser(String adminname,String adminpwd,int adminstatus,String expdate,String adminpermission,String projectid,int inherit,int parentid,int adminlevel)
	{
		JSONObject jObject=new JSONObject();
		try {
			int count = adminMapper.selectCountByName(adminname);
			if(count > 0)
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "用户名"+adminname+"已存在!");		
			}
			else
			{
				user record=new user();
				//record.setAdminid(adminid);
				record.setAdminname(adminname);
				record.setAdminpwd(adminpwd);
				record.setAdminstatus(adminstatus);
				record.setExpdate(expdate);
				record.setAdminpermission(adminpermission);
				record.setProjectid(projectid);
				record.setinherit(inherit);
				record.setAdmincount(0);
				record.setAdminlevel(adminlevel);
				record.setDelindex(0);
				record.setGrpcount(0);
				record.setIssuperuser(0);
				record.setParentid(parentid);
				int rowCount = adminMapper.insert(record);	
				
				if(rowCount>0)
				{
					int adminid=record.getAdminid();
					
					jObject.put("result", "success");
					jObject.put("adminid", adminid);	
					jObject.put("adminlevel", adminlevel);	
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "插入用户表失败");					
				}
			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}	
	}
	
	@Override
	public JSONObject EditUser(int adminid,String adminname,String adminpwd,int adminstatus,String expdate,String adminpermission,int inherit)
	{
		JSONObject jObject=new JSONObject();
		try {
			int count = adminMapper.selectCountByNameid(adminname, adminid);
			if(count > 0)
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "用户名"+adminname+"已存在!");		
			}
			else
			{
				user record=new user();
				record.setAdminid(adminid);
				record.setAdminname(adminname);
				record.setAdminpwd(adminpwd);
				record.setAdminstatus(adminstatus);
				record.setExpdate(expdate);
				record.setAdminpermission(adminpermission);				
				record.setinherit(inherit);
				int rowCount = adminMapper.updateByPrimaryKeySelective(record);	
				
				if(rowCount>0)
				{				
					jObject.put("result", "success");	
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "修改用户表失败");					
				}
			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}				
	}
	
	@Override
	public JSONObject DeleteUser(int adminid)
	{
		JSONObject jObject=new JSONObject();
		try {
			int rowCount = adminMapper.deleteByPrimaryKey(adminid);	
			
			if(rowCount>0)
			{				
				jObject.put("result", "success");	
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除用户失败");					
			}
				
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}						
	}
}
