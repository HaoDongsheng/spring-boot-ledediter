package org.hds.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IAdvMangerService {
	public JSONArray getadvlist();
	public JSONObject Creatinfo(String infoName);
	public JSONObject DeleteInfobyid(int infoid);
	public JSONObject SaveItem(int infoid,JSONObject jsoninfo);
	public JSONObject GetItembyid(int infoid);
}
