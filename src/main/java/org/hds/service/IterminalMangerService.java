package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface IterminalMangerService {
	public JSONObject getTerminalsbypageNum(int pageNum, int pageSize);
	public JSONObject getTerminalsbyprojectid(int pageNum, int pageSize, String projectid);
}
