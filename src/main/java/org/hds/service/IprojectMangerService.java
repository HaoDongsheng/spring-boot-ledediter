package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IprojectMangerService {
	public JSONArray getProjectlist();
	public JSONObject createProject(String projectid, String projectname, int isOurModule, String ConnectParameters, String username, String userpwd, String groupname, int groupwidth, int groupheight);
	public JSONObject updateProject(String projectid, String projectname, int isOurModule, String ConnectParameters);
	public JSONObject removeProject(String projectid);
}
