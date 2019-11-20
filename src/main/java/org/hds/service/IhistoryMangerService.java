package org.hds.service;

import com.alibaba.fastjson.JSONObject;

public interface IhistoryMangerService {
	public JSONObject gethistorybypageNum(int pageNum, int pageSize, String lifeAct, String lifeDie, int groupid,
			String sort, String sortOrder);

	public JSONObject gethistorybyDate(int pageNum, int pageSize, String lifeAct, String lifeDie, int groupid,
			String sort, String sortOrder);

	public JSONObject getplaylistinfobysn(int playlistsn);
}
