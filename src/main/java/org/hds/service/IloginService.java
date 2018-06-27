package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface IloginService {

	public JSONObject login(String adminName,String adminPwd);
}
