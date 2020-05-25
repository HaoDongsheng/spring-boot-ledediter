package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface IgroupMangerService {

	public JSONObject CreatGroup(String grpname, String projectid, int packLength, int batchCount, int width,
			int height, int grpbelong, String adminname);

	public JSONObject EditGroup(int grpid, String grpname, int packLength, int batchCount, int width, int height);

	public JSONObject DeleteGroup(int grpid);

	public JSONObject Set_parameter(String parameter);

	public JSONObject Get_parameter(String parameter);
}
