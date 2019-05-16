package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface ImainService {
	public JSONObject GetStatisticsData(String projectid);
	public JSONObject GetStatisticsDatabyDate(String projectid, String startDate, String endDate);
	public JSONObject GetUpdateRate(int groupid,String projectid);
}
