package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IInfoListService {
	public JSONArray getGroup(JSONObject adminInfoJsonObject);
	public JSONArray GetInfoList(int groupid);
	public JSONArray GetInfopubs(int groupid);
	public JSONObject CreatinfoList(String listname,int groupid,int listtype,String quantums,int ScheduleType,String programlist,int adminid);
	public JSONObject UpdateinfoList(int listid,String listname,int groupid,int listtype,String quantums,int ScheduleType,String programlist,int adminid);
	public JSONObject UpdatePlaylistName(int playlistid,String listname);
	public JSONObject DeleteinfoList(int listid,int adminid);
	public JSONObject Deleteinfobyid(int infosn,int groupid,int infopubid,int adminid);
	public JSONObject Copyinfobyid(int infosn,int groupid,int adminid);
	public JSONObject GetInfocodebyid(int infosn);
	public JSONObject PublishinfoList(int listid,int adminid);
	public JSONObject getbyteslistbyTemp(int listtype,String quantums,int ScheduleType,String programlist);
}
