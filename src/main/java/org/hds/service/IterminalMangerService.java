package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface IterminalMangerService {
	public JSONObject getTerminalsbypageNum(int pageNum, int pageSize, String searchString, String sort,
			String sortOrder);

	public JSONObject getTerminalsbyprojectid(int pageNum, int pageSize, String projectid, String searchString,
			String groupids, String sort, String sortOrder);

	public JSONObject updateTerminalInfo(String dtukey, String taxiname, int groupid, int StarLevelset);

	public JSONObject updateTerminalInfobygroup(String projectid, int groupid, int StarLevelset);
}
