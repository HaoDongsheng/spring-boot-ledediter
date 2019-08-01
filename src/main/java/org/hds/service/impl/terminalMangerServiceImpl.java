package org.hds.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hds.mapper.advertisementMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.terminalMapper;
import org.hds.model.advertisement;
import org.hds.model.terminal;
import org.hds.service.IterminalMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class terminalMangerServiceImpl implements IterminalMangerService {

	@Autowired
	terminalMapper terminalMapper;
	@Autowired
	groupMapper groupMapper;
	@Autowired
	projectMapper projectMapper;
	@Autowired
	advertisementMapper advMapper;

	@Override
	public JSONObject getTerminalsbypageNum(int pageNum, int pageSize, String searchString, String sort,
			String sortOrder) {
		JSONObject jObject = new JSONObject();
		try {
			int terminalCount = terminalMapper.selectCount(searchString);
			int startoffset = (pageNum - 1) * pageSize;
			List<terminal> terminalList = terminalMapper.SelectTerminalsBypageNum(startoffset, pageSize, searchString,
					sort, sortOrder);
			JSONArray jsonArray = new JSONArray();
			if (terminalList != null && terminalList.size() > 0) {
				for (int i = 0; i < terminalList.size(); i++) {
					JSONObject jterminal = new JSONObject();
					terminal terminal = terminalList.get(i);
					jterminal.put("name", terminal.getName());
					String Groupname = "无分组";
					if (terminal.getGroupid() == null) {
						jterminal.put("groupid", Groupname);
					} else {
						if (groupMapper.selectByPrimaryKey(terminal.getGroupid()) != null) {
							Groupname = groupMapper.selectByPrimaryKey(terminal.getGroupid()).getGroupname();
						}
						jterminal.put("groupid", Groupname);
					}
					jterminal.put("LED_ID", terminal.getLedId());
					jterminal.put("SIMNo", terminal.getSimno());
					jterminal.put("DtuKey", terminal.getDtukey());
					String Projectname = "无项目";
					if (terminal.getProjectid() == null) {
						jterminal.put("projectid", Projectname);
					} else {
						if (projectMapper.selectByPrimaryKey(terminal.getProjectid()) != null) {
							Projectname = projectMapper.selectByPrimaryKey(terminal.getProjectid()).getProjectname();
						}
						jterminal.put("projectid", Projectname);
					}

					jterminal.put("LedStateString", terminal.getLedstatestring());

					JSONArray jadvArray = new JSONArray();
					if (terminal.getGroupid() != null && terminal.getAdidlist() != null
							&& terminal.getAdidlist() != "") {
						String[] adidStrings = terminal.getAdidlist().split(",");
						for (int j = 0; j < adidStrings.length; j++) {
							int adid = Integer.parseInt(adidStrings[j]);
							advertisement advertisement = advMapper.selectByPubidandGroupid(adid,
									terminal.getGroupid());
							JSONObject advjObject = new JSONObject();
							if (advertisement != null) {
								String Advname = advertisement.getAdvname();
								String lifeDie = advertisement.getlifeDie();
								if (lifeDie == "") {
									lifeDie = "2100-09-09";
								}
								int Delindex = advertisement.getDelindex();
								String advstatus = "";
								if (Delindex == 1) {
									advstatus = "已删除";
								}
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date datenow = new Date();

								if (sdf.parse(lifeDie).getTime() < datenow.getTime()) {
									advstatus = "已删除";
								}
								advjObject.put("pubid", adid);
								advjObject.put("advname", Advname);
								advjObject.put("advstatus", advstatus);
							} else {
								advjObject.put("pubid", adid);
								advjObject.put("advname", "已删除");
								advjObject.put("advstatus", "已删除");
							}

							jadvArray.add(advjObject);
						}
					}

					if (jadvArray.size() > 0) {
						jterminal.put("AdIdList", jadvArray.toJSONString());
					} else {
						jterminal.put("AdIdList", null);
					}

					jterminal.put("AdIdListCount", jadvArray.size());
					jterminal.put("PlayList", terminal.getPlaylist());
					double Updater = 0;
					if (terminal.getUpdaterate() != null) {
						Updater = terminal.getUpdaterate();
					}
					String UpdateRate = (double) ((int) (Updater * 10000)) / 100 + "%";
					jterminal.put("UpdateRate", UpdateRate);
					jterminal.put("LedVersion", terminal.getLedversion());
					jterminal.put("StateLedVersion", terminal.getStateledversion());
					jterminal.put("RegisterDate", terminal.getRegisterdate());
					jterminal.put("StarLevel", terminal.getStarlevel());
					jterminal.put("StarLevelSet", terminal.getStarlevelset());

					jsonArray.add(jterminal);
				}
			}

			jObject.put("total", terminalCount);
			jObject.put("rows", jsonArray);
			return jObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject getTerminalsbyprojectid(int pageNum, int pageSize, String projectid, String searchString,
			String groupids, String sort, String sortOrder) {
		JSONObject jObject = new JSONObject();
		try {
			int terminalCount = 0;
			if (groupids != null && groupids != "") {
				List<String> listids = Arrays.asList(groupids.split(","));
				terminalCount = terminalMapper.selectCountbyprojectidgrp(projectid, searchString, listids);
			} else {
				terminalCount = terminalMapper.selectCountbyprojectid(projectid, searchString);
			}
			int startoffset = (pageNum - 1) * pageSize;

			List<terminal> terminalList = null;
			if (groupids != null && groupids != "") {
				List<String> listids = Arrays.asList(groupids.split(","));
				terminalList = terminalMapper.SelectTerminalsByprojectidgrp(startoffset, pageSize, projectid,
						searchString, listids, sort, sortOrder);
			} else {
				terminalList = terminalMapper.SelectTerminalsByprojectid(startoffset, pageSize, projectid, searchString,
						sort, sortOrder);
			}
			JSONArray jsonArray = new JSONArray();
			if (terminalList != null && terminalList.size() > 0) {
				for (int i = 0; i < terminalList.size(); i++) {
					JSONObject jterminal = new JSONObject();
					terminal terminal = terminalList.get(i);
					jterminal.put("name", terminal.getName());
					String Groupname = "无分组";
					if (terminal.getGroupid() == null) {
						jterminal.put("groupid", Groupname);
					} else {
						if (groupMapper.selectByPrimaryKey(terminal.getGroupid()) != null) {
							Groupname = groupMapper.selectByPrimaryKey(terminal.getGroupid()).getGroupname();
						}
						jterminal.put("groupid", Groupname);
					}
					jterminal.put("LED_ID", terminal.getLedId());
					jterminal.put("SIMNo", terminal.getSimno());
					jterminal.put("DtuKey", terminal.getDtukey());
					String Projectname = "无项目";
					if (terminal.getProjectid() == null) {
						jterminal.put("projectid", Projectname);
					} else {
						if (projectMapper.selectByPrimaryKey(terminal.getProjectid()) != null) {
							Projectname = projectMapper.selectByPrimaryKey(terminal.getProjectid()).getProjectname();
						}
						jterminal.put("projectid", Projectname);
					}

					jterminal.put("LedStateString", terminal.getLedstatestring());
					JSONArray jadvArray = new JSONArray();
					if (terminal.getGroupid() != null && terminal.getAdidlist() != null
							&& terminal.getAdidlist() != "") {
						String[] adidStrings = terminal.getAdidlist().split(",");
						for (int j = 0; j < adidStrings.length; j++) {
							int adid = Integer.parseInt(adidStrings[j]);
							advertisement advertisement = advMapper.selectByPubidandGroupid(adid,
									terminal.getGroupid());
							JSONObject advjObject = new JSONObject();
							if (advertisement != null) {
								String Advname = advertisement.getAdvname();
								String lifeDie = advertisement.getlifeDie();
								if (lifeDie == "") {
									lifeDie = "2100-09-09";
								}
								int Delindex = advertisement.getDelindex();
								String advstatus = "";
								if (Delindex == 1) {
									advstatus = "已删除";
								}
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date datenow = new Date();

								if (sdf.parse(lifeDie).getTime() < datenow.getTime()) {
									advstatus = "已删除";
								}
								advjObject.put("pubid", adid);
								advjObject.put("advname", Advname);
								advjObject.put("advstatus", advstatus);
							} else {
								advjObject.put("pubid", adid);
								advjObject.put("advname", "已删除");
								advjObject.put("advstatus", "已删除");
							}

							jadvArray.add(advjObject);
						}
					}
					if (jadvArray.size() > 0) {
						jterminal.put("AdIdList", jadvArray.toJSONString());
					} else {
						jterminal.put("AdIdList", null);
					}
					jterminal.put("AdIdListCount", jadvArray.size());
					jterminal.put("PlayList", terminal.getPlaylist());
					double Updater = 0;
					if (terminal.getUpdaterate() != null) {
						Updater = terminal.getUpdaterate();
					}
					String UpdateRate = (double) ((int) (Updater * 10000)) / 100 + "%";
					jterminal.put("UpdateRate", UpdateRate);
					jterminal.put("LedVersion", terminal.getLedversion());
					jterminal.put("StateLedVersion", terminal.getStateledversion());
					jterminal.put("RegisterDate", terminal.getRegisterdate());
					jterminal.put("StarLevel", terminal.getStarlevel());
					jterminal.put("StarLevelSet", terminal.getStarlevelset());

					jsonArray.add(jterminal);
				}
			}

			jObject.put("total", terminalCount);
			jObject.put("rows", jsonArray);
			return jObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject updateTerminalInfo(String dtukey, String taxiname, int groupid, int StarLevelset) {
		JSONObject jObject = new JSONObject();
		try {
			terminal record = new terminal();
			record.setDtukey(dtukey);
			record.setName(taxiname);
			record.setGroupid(groupid);
			record.setProjectid(groupMapper.selectByPrimaryKey(groupid).getProjectid());
			record.setStarlevelset(StarLevelset);
			terminalMapper.updateByPrimaryKeySelective(record);

			jObject.put("result", "success");
			jObject.put("projectName", projectMapper
					.selectByPrimaryKey(groupMapper.selectByPrimaryKey(groupid).getProjectid()).getProjectname());
			jObject.put("groupName", groupMapper.selectByPrimaryKey(groupid).getGroupname());

			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
			// 写入项目表，发布广告改动时间数据
			projectMapper.updateTerminalUpdateTimeByPrimaryKey(groupMapper.selectByPrimaryKey(groupid).getProjectid(),
					d1.format(now));
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject updateTerminalInfobygroup(String projectid, int groupid, int StarLevelset) {
		JSONObject jObject = new JSONObject();
		try {
			if (groupid == 0)// 选择全部分组
			{
				if (projectid == null)// 超级用户
				{
					terminalMapper.updateStarLevelAll(StarLevelset);
				} else // 非超级用户
				{
					terminalMapper.updateStarLevelByprojectid(projectid, StarLevelset);
				}
			} else {
				terminalMapper.updateStarLevelBygroupid(groupid, StarLevelset);
			}

			jObject.put("result", "success");
			// jObject.put("projectName",
			// projectMapper.selectByPrimaryKey(groupMapper.selectByPrimaryKey(groupid).getProjectid()).getProjectname());
			String groupName = "全部分组";
			if (groupid != 0) {
				groupName = groupMapper.selectByPrimaryKey(groupid).getGroupname();
			}
			jObject.put("groupName", groupName);

			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
			// 写入项目表，发布广告改动时间数据
			if (groupid == 0)// 选择全部分组
			{
				if (projectid == null)// 超级用户
				{
					projectMapper.updateTerminalUpdateTimeByAll(d1.format(now));
				} else // 非超级用户
				{
					projectMapper.updateTerminalUpdateTimeByPrimaryKey(projectid, d1.format(now));
				}
			} else {
				projectMapper.updateTerminalUpdateTimeByPrimaryKey(
						groupMapper.selectByPrimaryKey(groupid).getProjectid(), d1.format(now));
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}
}
