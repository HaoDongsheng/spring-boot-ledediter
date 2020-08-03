package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IInfoListService {
	public JSONArray getGroup(JSONObject adminInfoJsonObject);

	public JSONArray GetInfoList(int groupid);

	public JSONArray GetInfopubs(int groupid);

	public JSONObject CreatinfoList(String listname, int groupid, int listtype, String quantums, int ScheduleType,
			String lifeAct, String lifeDie, String programlist, int adminid);

	public JSONObject UpdateinfoList(String listid, String listname, int groupid, int listtype, String quantums,
			int ScheduleType, String listlifeAct, String listlifeDie, String programlist, int adminid);

	public JSONObject UpdatePlaylistName(String playlistid, String listname);

	public JSONObject UpdateRemarks(String infosn, String remarks);

	public JSONObject DeleteinfoList(String listid, int adminid);

	public JSONObject Deleteinfobyid(String infosn, int groupid, int infopubid, int adminid);

	public JSONObject Copyinfobyid(String infosn, int groupid, int adminid);

	public JSONObject GetInfocodebyid(String infosn);

	public JSONObject PublishinfoList(String listid, int adminid);

	public JSONObject PublishinfoListbydate(String listid, int adminid);

	public JSONObject PublishinfoListbydate2(String listid, int adminid);

	public JSONObject PublishinfoListbyGroupid(String listid, int adminid, int groupid);

	public JSONObject getbyteslistbyTemp(int listtype, String quantums, int ScheduleType, String programlist);

	public JSONObject deleteList2old(String playlistSN);

	public JSONObject DeleteList2oldbyDay();

	public int updateLists(int groupid);

	public JSONObject updateAllLists();
}
