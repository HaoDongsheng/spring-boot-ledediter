package org.hds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IAdvMangerService {
	public JSONArray getadvlist(JSONObject adminInfoJsonObject);
	public JSONArray getadvListbyGrpid(int Grpid);
	public JSONArray getadvEditListbyGrpid(int Grpid);
	public JSONObject getadvListDelbyGrpid(int Grpid,int pageNum, int pageSize);
	public JSONArray getadvListbyGrpidState(int Grpid,int infoState);
	public JSONObject GetImgbyGif(String gifString, int w, int h);
	public JSONObject Creatinfo(String infoName,int groupid,String lifeAct,String lifeDie,String BackgroundStyle,int adminid);
	public JSONObject CopyInfo(String infoName,int groupid,String lifeAct,String lifeDie,String BackgroundStyle,String itemlist);
	public JSONObject DeleteInfobyid(int infoid,int adminid);
	public JSONObject DeleteinfoCodebyid(int infoid,int adminid);	
	public JSONObject SaveItem(int infoid,JSONObject jsoninfo);
	public JSONObject CopyItem(int oldinfoid,int newinfoid);
	public int Updatainfo(int infoid,JSONObject jsoninfo);
	public JSONObject GetItembyid(int infoid);
	public JSONObject Publishinfobyid(int infoid,int adminid);
	public JSONObject getbyteslistbyid(int infoid);
	public JSONObject getbyteslistbyTemp(int infoid, JSONObject jsoninfo, JSONObject jsonarritem);
	public JSONObject RefuseInfobyid(int infoid,int adminid);
	public JSONObject AuditInfobyid(int infoid,int adminid);
	public int SaveBasemap(String fileName,String projectid,Integer imgtype,String basemapclassify,String contentType,String basemapstyle,String base64Img);
	public JSONArray getbasemaplist();
	public JSONObject getbasemapbyid(int basemapid);
	public int deletebasemapbyid(int basemapid);
	public JSONArray getimgclassifybyprojectid(String projectid,int imgtype);
	public JSONArray getimgbyprojectid(String projectid,int imgtype,String classify);
	public int updatebasemapclassify(String projectid,Integer imgtype,String oldbasemapclassify,String newbasemapclassify);
	public int Savevideo(String fileName,Integer groupid,Integer videotype,String videoclassify,String contentType,String duration,String videostyle,String base64Img);
	public JSONArray getvideoclassifybyGrpid(int Grpid,int imgtype);
	public JSONArray getvideobyGrpid(int Grpid,int imgtype,String classify);
	public int updatevideoclassify(Integer groupid,Integer videotype,String oldvideoclassify,String newvideoclassify);
	public int deletevideobyid(int basemapid);
}
