package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IAdvMangerService {
	public JSONArray getadvlist(JSONObject adminInfoJsonObject);

	public JSONObject Deleteinfo2oldbyDay();

	public JSONArray getadvListbyGrpid(int Grpid);

	public JSONArray getadvEditListbyGrpid(int Grpid);

	public JSONObject getadvListDelbyGrpid(int Grpid, int type, String infoName, String lifeAct, String lifeDie,
			int pageNum, int pageSize, String sort, String sortOrder);

	public JSONArray getadvListbyGrpidState(int Grpid, int infoState);

	public JSONObject GetImgbyGif(String gifString, int w, int h);

	public JSONObject Creatinfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			int adminid);

	public JSONObject CopyInfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			String itemlist);

	public JSONObject CopyExpinfobyid(String infosn, int groupid, int adminid);

	public JSONObject deleteInfo2old(String infoSN);

	public JSONObject DeleteInfobyid(String infoSN, int adminid);

	public JSONObject DeleteinfoCodebyid(String infoSN, int adminid);

	public JSONObject SaveItem(String infoSN, JSONObject jsoninfo);

	public JSONObject CopyItem(String oldinfoSN, String newinfoSN);

	public int Updatainfo(String infoSN, JSONObject jsoninfo);

	public JSONObject GetItembyid(String infoSN);

	public JSONObject Publishinfobyid(String infoSN, int adminid);

	public JSONObject getbyteslistbyid(String infoSN);

	public JSONObject getbyteslistbyTemp(String infoSN, JSONObject jsoninfo, JSONObject jsonarritem);

	public JSONObject RefuseInfobyid(String infoSN, int adminid);

	public JSONObject AuditInfobyid(String infoSN, int adminid);

	public JSONObject getSensitive(String adminname);

	public JSONObject addSensitive(String sensitiveString, String adminname);

	public JSONObject deleteSensitive(String sensitiveIdlist, String adminname);

	public int SaveBasemap(String fileName, String projectid, Integer imgtype, String basemapclassify,
			String contentType, String basemapstyle, String base64Img);

	public JSONArray getbasemaplist();

	public JSONObject getbasemapbyid(int basemapid);

	public int deletebasemapbyid(int basemapid);

	public JSONArray getimgclassifybyprojectid(String projectid, int imgtype);

	public JSONObject getimgbyprojectid(String projectid, int imgtype, String classify, int pageNumber, int pageSize);

	public int updatebasemapclassify(String projectid, Integer imgtype, String oldbasemapclassify,
			String newbasemapclassify);

	public int Savevideo(String fileName, Integer groupid, Integer videotype, String videoclassify, String contentType,
			String duration, String videostyle, String base64Img);

	public JSONArray getvideoclassifybyGrpid(int Grpid, int imgtype);

	public JSONArray getvideobyGrpid(int Grpid, int imgtype, String classify);

	public int updatevideoclassify(Integer groupid, Integer videotype, String oldvideoclassify,
			String newvideoclassify);

	public int deletevideobyid(int basemapid);
}
