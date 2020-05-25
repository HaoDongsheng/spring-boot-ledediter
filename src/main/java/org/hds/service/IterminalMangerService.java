package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IterminalMangerService {
	public JSONObject getTerminalsbypageNum(int pageNum, int pageSize, String searchString, String sort,
			String sortOrder);

	public JSONObject getTerminalsbyprojectid(int pageNum, int pageSize, String projectid, String searchString,
			String groupids, int adminlevel, String sort, String sortOrder);

	public JSONObject updateTerminalInfo(String dtukey, String taxiname, int groupid, int StarLevelset);

	public JSONObject deleteTerminalInfo(String dtukey);

	public JSONObject updateTerminalInfobygroup(String projectid, int groupid, int StarLevelset);

	public String updateTerminalinfobySerial(String DTUNo, String groupName);

	public JSONArray getGroupbyProjectid(String projectid, JSONObject jsonObject);

	public JSONObject getTerminalsUpdate(JSONArray DtukeyArray);
}
