package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IuserMangerService {
	public JSONArray getUserList(JSONObject adminInfoJsonObject);
	public JSONArray getProjectList(JSONObject adminInfoJsonObject);
	public JSONObject CreatUser(String adminname,String adminpwd,int adminstatus,String expdate,String adminpermission,String projectid,int inherit,int parentid,int adminlevel);
	public JSONObject EditUser(int adminid,String adminname,String adminpwd,int adminstatus,String expdate,String adminpermission,int inherit);
	public JSONObject DeleteUser(int adminid);
}
