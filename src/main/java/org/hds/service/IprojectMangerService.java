package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IprojectMangerService {
	public JSONArray getProjectlist();

	public JSONObject createProject(String projectid, String projectname, String CheckCode, int startlevelControl,
			int DefaultStartlevel, int isOurModule, String ConnectParameters, String username, String userpwd,
			String groupname, int packLength, int batchCount, int groupwidth, int groupheight);

	public JSONObject updateProject(String projectid, String projectname, int AutoGroupTo, String CheckCode,
			int startlevelControl, int DefaultStartlevel, int isOurModule, String ConnectParameters);

	public JSONObject updateProjectlimit(String projectid, String projectLimit);

	public JSONObject removeProject(String projectid);

	public JSONObject moveGroup(String sprojectid, String oprojectid, int sgroupid, int ogroupid);

	public JSONArray getGroupbyProjectid(String projectid);

	public JSONObject programlistChange();

	public JSONObject passwordEnCode();

	public JSONObject txt2db();

	public JSONObject execel2db();

	public JSONObject codeChange();
}
