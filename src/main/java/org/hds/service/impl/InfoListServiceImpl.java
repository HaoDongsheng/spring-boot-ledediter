package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hds.logmapper.oldplaylistMapper;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.projectMapper;
import org.hds.model.advertisement;
import org.hds.model.group;
import org.hds.model.infocode;
import org.hds.model.item;
import org.hds.model.oldplaylist;
import org.hds.model.playlist;
import org.hds.model.playlistcode;
import org.hds.model.project;
import org.hds.service.IInfoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class InfoListServiceImpl implements IInfoListService {

	@Autowired
	groupMapper groupMapper;
	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	itemMapper itemMapper;
	@Autowired
	playlistcodeMapper playlistcodeMapper;
	@Autowired
	infocodeMapper infocodeMapper;
	@Autowired
	getCode getCode;
	@Autowired
	projectMapper projectMapper;
	@Autowired
	oldplaylistMapper oldplaylistMapper;

	@Override
	public JSONArray getGroup(JSONObject adminInfoJsonObject) {
		JSONArray jArray = new JSONArray();
		try {
			int isSuperuser = adminInfoJsonObject.getInteger("issuperuser");
			if (isSuperuser == 0) {
				String projectid = adminInfoJsonObject.getString("projectid");

				List<group> grplist = groupMapper.selectbyProjectid(projectid);
				String groupids = adminInfoJsonObject.getString("groupids");
				for (int i = 0; i < grplist.size(); i++) {
					group tg = grplist.get(i);
					int grpid = tg.getGroupid();
					String grpName = tg.getGroupname();
					int screenwidth = tg.getscreenwidth();
					int screenheight = tg.getscreenheight();
					int maxPackLength = tg.getMaxPackLength();
					int ptMode = tg.getPtMode() != null ? tg.getPtMode() : 0;
					String displayMode = tg.getDisplayMode() != null ? tg.getDisplayMode() : "1110000000011001100000";
					if (groupids == null || groupids.equals("") || adminInfoJsonObject.getInteger("adminlevel") < 2) {
						JSONObject jgrp = new JSONObject();
						jgrp.put("grpid", grpid);
						jgrp.put("grpname", grpName);
						jgrp.put("maxPackLength", maxPackLength);
						jgrp.put("batchCount", tg.getBatchCount());
						jgrp.put("projectid", tg.getProjectid());
						jgrp.put("screenwidth", screenwidth);
						jgrp.put("screenheight", screenheight);
						jgrp.put("ptMode", ptMode);
						jgrp.put("displayMode", displayMode);
						jArray.add(jgrp);
					} else if (Arrays.asList(groupids.split(",")).contains(Integer.toString(grpid))) {
						JSONObject jgrp = new JSONObject();
						jgrp.put("grpid", grpid);
						jgrp.put("grpname", grpName);
						jgrp.put("maxPackLength", maxPackLength);
						jgrp.put("batchCount", tg.getBatchCount());
						jgrp.put("projectid", tg.getProjectid());
						jgrp.put("screenwidth", screenwidth);
						jgrp.put("screenheight", screenheight);
						jgrp.put("ptMode", ptMode);
						jgrp.put("displayMode", displayMode);
						jArray.add(jgrp);
					}
				}
			} else {
				List<group> tgrpsList = groupMapper.selectAll();
				for (int i = 0; i < tgrpsList.size(); i++) {
					int grpid = tgrpsList.get(i).getGroupid();
					String grpName = tgrpsList.get(i).getGroupname();
					int screenwidth = tgrpsList.get(i).getscreenwidth();
					int screenheight = tgrpsList.get(i).getscreenheight();
					int maxPackLength = tgrpsList.get(i).getMaxPackLength();
					int ptMode = tgrpsList.get(i).getPtMode() != null ? tgrpsList.get(i).getPtMode() : 0;
					String displayMode = tgrpsList.get(i).getDisplayMode() != null ? tgrpsList.get(i).getDisplayMode()
							: "1110000000011001100000";

					JSONObject jgrp = new JSONObject();
					jgrp.put("grpid", grpid);
					jgrp.put("grpname", grpName);
					jgrp.put("maxPackLength", maxPackLength);
					jgrp.put("batchCount", tgrpsList.get(i).getBatchCount());
					jgrp.put("projectid", tgrpsList.get(i).getProjectid());
					jgrp.put("screenwidth", screenwidth);
					jgrp.put("screenheight", screenheight);
					jgrp.put("ptMode", ptMode);
					jgrp.put("displayMode", displayMode);
					jArray.add(jgrp);
				}
			}
			return jArray;
		} catch (Exception ex) {
			return jArray;
		}
	}

	@Override
	public JSONArray GetInfopubs(int groupid) {
		JSONArray jArray = new JSONArray();
		try {
			List<advertisement> advertisements = advertisementMapper.selectpubBygroupid(groupid);

			for (int i = 0; i < advertisements.size(); i++) {
				String id = advertisements.get(i).getinfoSN();
				String Advname = advertisements.get(i).getAdvname();
				String lifeAct = advertisements.get(i).getlifeAct();
				String lifeDie = advertisements.get(i).getlifeDie();
				int playTimelength = advertisements.get(i).getplayTimelength();
				int Pubidid = advertisements.get(i).getPubid();
				String publishDate = advertisements.get(i).getpublishDate();
				String remarks = advertisements.get(i).getRemarks();

				JSONObject jadv = new JSONObject();
				jadv.put("id", id);
				jadv.put("Advname", Advname);
				jadv.put("lifeAct", lifeAct);
				jadv.put("lifeDie", lifeDie);
				jadv.put("playTimelength", playTimelength);
				jadv.put("Pubidid", Pubidid);
				jadv.put("publishDate", publishDate);
				jadv.put("remarks", remarks);

				jArray.add(jadv);
			}

			return jArray;
		} catch (Exception ex) {
			return jArray;
		}
	}

	@Override
	public JSONArray GetInfoList(int groupid) {
		JSONArray jArray = new JSONArray();
		try {
//			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);

			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
			List<playlist> playlists = playlistMapper.selectbygroupidDate(groupid, d1.format(new Date()));

			for (int i = 0; i < playlists.size(); i++) {
				String id = playlists.get(i).getplaylistSN();
				int Delindex = playlists.get(i).getDelindex();
				int Grpid = playlists.get(i).getGroupid();
				int Playlistlevel = playlists.get(i).getPlaylistlevel();
				int Scheduletype = playlists.get(i).getScheduletype();
				String Playlistlifeact = playlists.get(i).getPlaylistlifeact();
				String Playlistlifedie = playlists.get(i).getPlaylistlifedie();
				String Playlistname = playlists.get(i).getPlaylistname();
				String Programlist = playlists.get(i).getProgramlist();
				String Timequantum = playlists.get(i).getTimequantum();
				String pubid = playlists.get(i).getPubid();

				JSONObject jgrp = new JSONObject();
				jgrp.put("id", id);
				jgrp.put("Delindex", Delindex);
				jgrp.put("Grpid", Grpid);
				jgrp.put("Playlistlevel", Playlistlevel);
				jgrp.put("Scheduletype", Scheduletype);
				jgrp.put("Playlistlifeact", Playlistlifeact);
				jgrp.put("Playlistlifedie", Playlistlifedie);
				jgrp.put("Playlistname", Playlistname);
				jgrp.put("Programlist", Programlist);
				jgrp.put("Timequantum", Timequantum);
				jgrp.put("pubid", pubid);
				jArray.add(jgrp);
			}

			return jArray;
		} catch (Exception ex) {
			return jArray;
		}
	}

	@Override
	public JSONObject CreatinfoList(String listname, int groupid, int listtype, String quantums, int ScheduleType,
			String lifeAct, String lifeDie, String programlist, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			int count = playlistMapper.selectByNameCount(listname, groupid);
			if (count <= 0) {
				playlist playlist = new playlist();
				String playlistSN = getCode.getIncreaseIdByCurrentTimeMillis();
				playlist.setplaylistSN(playlistSN);
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact(lifeAct);
				playlist.setPlaylistlifedie(lifeDie);
				playlist.setcreater(adminid);
				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// DateFormat d1 = DateFormat.getDateTimeInstance();
				playlist.setcreateDate(d1.format(now));
				playlist.setPlaylistname(listname);
				playlist.setProgramlist(programlist);
				playlist.setPubid("0");
				playlist.setScheduletype(ScheduleType);
				playlist.setTimequantum(quantums);

				int rowCount = playlistMapper.insert(playlist);
				if (rowCount > 0) {
					jObject.put("result", "success");
					jObject.put("infoListID", playlistSN);
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "列表名称:" + listname + "写入数据库失败!");
				}
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:" + listname + "已存在!");
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	/*
	 * 判断删除的列表中含有的广告，是否唯一，如果唯一记录广告下架时间
	 */
	private void deletePublishInfo(JSONArray oldplArray, int groupid, int adminid) {
		try {
			List<advertisement> advlist = new ArrayList<advertisement>();
			for (int j = 0; j < oldplArray.size(); j++) {
				String oldinfoid = oldplArray.getJSONObject(j).getString("infoid");
				// 判断删除的广告在同组其他播放列表中是否存在
				boolean isExist = false;
				List<Object> pList = playlistMapper.selectprogramlistbygroupid(groupid);
				for (int i = 0; i < pList.size(); i++) {
					JSONArray plArray = JSONArray.parseArray(pList.get(i).toString());
					for (int p = 0; p < plArray.size(); p++) {
						String infoid = plArray.getJSONObject(p).getString("infoid");
						if (oldinfoid.equals(infoid)) {
							isExist = true;
							break;
						}
					}
				}
				// 其他播放列表中不存在广告，确定删除
				if (!isExist) {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// DateFormat d1 = DateFormat.getDateTimeInstance();

					advertisementMapper.updatedeletedateByid(oldinfoid, adminid, d1.format(now));

					advlist.add(advertisementMapper.selectByPrimaryKey(oldinfoid));
				}
			}

			if (advlist.size() > 0) {
				// NettyClient.TcpSocketSendPublish(null, advlist);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private int isInfoFull(String listid, String listname, int groupid, int listtype, String quantums, int ScheduleType,
			String programlist) {
		int maxInfoCount = 180, maxPlaylistCount = 30;
		try {
			JSONArray jArrayLoop = null;
			Date now = new Date();
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
			// 获取要发布的广告id集合
			List<String> infosnlist = new ArrayList<String>();
			if (programlist != null && programlist != "") {
				jArrayLoop = JSONArray.parseArray(programlist);
				for (int i = 0; i < jArrayLoop.size(); i++) {
					String infoid = jArrayLoop.getJSONObject(i).getString("infoid");
					String lifeDie = jArrayLoop.getJSONObject(i).getString("lifeDie");
					if (lifeDie.equals("") || lifeDie == null) {
						lifeDie = "2100-09-09";
					}
					if (!infosnlist.contains(infoid) && lifeDie.compareTo(d1.format(now)) >= 0) {
						infosnlist.add(infoid);
					}
				}
			}

			// 判断广告编码表中数据加上infosnlist集合是否大于最大范围，大于isInfoMax=true否则isInfoMax = false
			boolean isInfoMax = false;// 广告数量小于200,没超出范围

			List<infocode> infocodeList = infocodeMapper.selectBygroupidDate(groupid, d1.format(now));
			if (infocodeList.size() + infosnlist.size() > maxInfoCount) {
				// 广告数量大于200,进一步判断生命周期
				isInfoMax = true;
			}

			// 判断播放列表编码表中数据加上infosnlist集合是否大于最大范围，大于isplMax=true否则isplMax = false
			List<playlistcode> plcodeList = playlistcodeMapper.selectByGroupid(groupid);
			JSONArray jArrayinfo = null;
			boolean isplMax = false;// 列表数量小于30,没超出范围
			if (ScheduleType == 2 && listtype != 0)// 顺序排期
			{
				jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop, true);
				if (plcodeList.size() + jArrayinfo.size() > maxPlaylistCount) {
					isplMax = true;// 列表数量大于30,进一步判断生命周期
				}

			} else // 模板排期
			{
				if (plcodeList.size() + 1 > maxPlaylistCount) {
					isplMax = true;// 列表数量大于30,进一步判断生命周期
				}
			}

			if (!isplMax && !isInfoMax)// 初步判断广告列表都在允许范围内，返回0
			{
				return 0;
			} else// 初步判断列表大于30个，进一步按生命周期查看
			{
				/* 把准备发布的列表信息加入数据库取出的plcodeList中 */
				if (jArrayinfo != null) {
					for (int i = 0; i < jArrayinfo.size(); i++) {
						JSONObject jObject = jArrayinfo.getJSONObject(i);

						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
						int comparedate = sd.parse(jObject.getString("lifeDie")).compareTo(new Date());
						if (comparedate > 0) {
							playlistcode plcode = new playlistcode();
							plcode.setPlaylistsn("0");
							plcode.setPubid(0);
							plcode.setLifeAct(jObject.getString("lifeAct"));
							plcode.setLifeDie(jObject.getString("lifeDie"));
							plcode.setplaylistCodeSN("0");
							plcode.setCodecontext("");
							JSONArray jArray = jObject.getJSONArray("advlist");
							List<String> infosns = new ArrayList<String>();
							for (int j = 0; j < jArray.size(); j++) {
								infosns.add(jArray.getJSONObject(j).getString("infoid"));
							}
							plcode.setContainADID(String.join(",", infosns));
							plcode.setGroupid(groupid);
							plcode.setPackCount(0);
							plcode.setPackLength(0);
							plcode.setPlaylistcrc(0);

							plcodeList.add(plcode);
						}
					}
				} else {
					playlistcode plcode = new playlistcode();
					plcode.setPlaylistsn("0");
					plcode.setPubid(0);
					plcode.setLifeAct("1999-09-09");
					plcode.setLifeDie("2100-09-09");
					plcode.setplaylistCodeSN("0");
					plcode.setCodecontext("");
					plcode.setContainADID(String.join(",", infosnlist));
					plcode.setGroupid(groupid);
					plcode.setPackCount(0);
					plcode.setPackLength(0);
					plcode.setPlaylistcrc(0);

					plcodeList.add(plcode);
				}
				/* end */

				List<String> datelist = new ArrayList<String>();
				for (int i = 0; i < plcodeList.size(); i++) {
					playlistcode plcode = plcodeList.get(i);

					if (plcode.getPlaylistsn().equals(listid))// 要删除的列表不参与计算
					{
						continue;
					}

					String lifeAct = plcode.getLifeAct();
					String lifeDie = plcode.getLifeDie();

					if (lifeAct.equals("")) {
						lifeAct = "1999-09-09";
					}
					if (lifeDie.equals("")) {
						lifeDie = "2100-09-09";
					}

					if (!datelist.contains(lifeAct)) {
						datelist.add(lifeAct);
					}
					if (!datelist.contains(lifeDie)) {
						datelist.add(lifeDie);
					}
				}
				Collections.sort(datelist);

				/* 判断时间范围内数据 */
				for (int i = 0; i < datelist.size() - 1; i++) {
					int iact = Integer.parseInt(datelist.get(i).replace("-", ""));
					int idie = Integer.parseInt(datelist.get(i + 1).replace("-", ""));
					int plcount = 0;
					List<String> infolist = new ArrayList<String>();
					for (int j = 0; j < plcodeList.size(); j++) {
						int ala = Integer.parseInt(plcodeList.get(j).getLifeAct().replace("-", ""));
						int ald = Integer.parseInt(plcodeList.get(j).getLifeDie().replace("-", ""));
						/* 判断生命周期 */
						if (!(idie < ala || iact > ald)) {
							plcount += 1;
							String[] infos = plcodeList.get(j).getContainADID().split(",");
							for (int p = 0; p < infos.length; p++) {
								if (!infolist.contains(infos[p])) {
									infolist.add(infos[p]);
								}
							}
						}
					}
					if (plcount > maxPlaylistCount || infolist.size() > maxInfoCount) {
						if (plcount > maxPlaylistCount) {
							return 1;
						} else {
							return 2;
						}
					}
				}
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}

	@Override
	public JSONObject UpdateinfoList(String listid, String listname, int groupid, int listtype, String quantums,
			int ScheduleType, String listlifeAct, String listlifeDie, String programlist, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			JSONArray jArrayLoop = null;
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			// 获取要发布的广告id集合
			if (programlist != null && programlist != "") {
				jArrayLoop = JSONArray.parseArray(programlist);
				for (int i = jArrayLoop.size() - 1; i >= 0; i--) {
					String lifeDie = jArrayLoop.getJSONObject(i).getString("lifeDie");
					if (!lifeDie.equals("") && lifeDie != null && lifeDie.compareTo(dt.format(new Date())) < 0) {
						jArrayLoop.remove(i);
					}
				}

				programlist = jArrayLoop.toJSONString();
			}

			int isFull = isInfoFull(listid, listname, groupid, listtype, quantums, ScheduleType, programlist);
			if (isFull != 0) {
				// 播放列表大于30或广告大于200

				jObject.put("result", "fail");
				switch (isFull) {
				case -1:
					jObject.put("resultMessage", "计算列表广告数量是否越界异常!");
					break;
				case 1:
					jObject.put("resultMessage", "播放列表数量越界!");
					break;
				case 2:
					jObject.put("resultMessage", "广告数量越界!");
					break;
				}

				return jObject;
			}

			String plpubid = playlistMapper.selectpubidid(listid).toString();
			playlist oldpl = playlistMapper.selectByPrimaryKey(listid);

			if (!plpubid.equals("0")) {
				// 已发布广告，删除原有的播放列表创新新播放列表
				int pcdeleCount = playlistcodeMapper.deleteByplaylistSN(listid);

				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				int pldeleCount = 0;

				// 判断删除排期中的广告是否需要删除
				JSONArray oldplArray = JSONArray.parseArray(oldpl.getProgramlist());
				deletePublishInfo(oldplArray, oldpl.getGroupid(), adminid);

				playlist playlist = new playlist();
				String playlistSN = getCode.getIncreaseIdByCurrentTimeMillis();
				playlist.setplaylistSN(playlistSN);
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact(listlifeAct);
				playlist.setPlaylistlifedie(listlifeDie);
				playlist.setcreater(adminid);
				playlist.setcreateDate(d1.format(now));
				playlist.setPlaylistname(listname);
				playlist.setProgramlist(programlist);
				playlist.setPubid("0");
				playlist.setScheduletype(ScheduleType);
				playlist.setTimequantum(quantums);

				int rowCount = playlistMapper.insert(playlist);
				if (rowCount > 0) {
					// pldeleCount += playlistMapper.updatedelindexByPrimaryKey(listid, 1, adminid,
					// d1.format(now));

					/*
					 * 检查开始 检查数据库中同组中存在多个重名的播放列表,存在就删除,解决出现多个列表情况
					 */
					String DelMessageString = "";
					List<String> plsns = playlistMapper.selectlistsnbyplaylistname(groupid, listname);
					if (plsns != null && plsns.size() > 0) {
						DelMessageString = "执行删除多个重复列表指令,列表个数:" + plsns.size() + "播放列表id集合:";
						for (int i = 0; i < plsns.size(); i++) {
							if (!plsns.get(i).equals(playlistSN)) {
								DelMessageString += plsns.get(i) + ",";
								pldeleCount += playlistMapper.updatedelindexByPrimaryKey(plsns.get(i), 1, adminid,
										d1.format(now));
								pcdeleCount += playlistcodeMapper.deleteByplaylistSN(plsns.get(i));
								DelMessageString += "[" + playlistSN + "]";
							}
						}
						DelMessageString += ",成功删除" + pldeleCount + "个播放列表";
					}
					/*
					 * 检查结束
					 */

					jObject.put("result", "success");
					jObject.put("returnid", playlistSN);
					jObject.put("deleMessage",
							"发布时删除旧播放列表" + pldeleCount + "个,编码" + pcdeleCount + "个,旧列表SN" + listid + DelMessageString);

				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "列表名称:" + listname + "写入数据库失败!");
				}
			} else {
				// 未发布广告直接保存
				playlist playlist = new playlist();
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setplaylistSN(listid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact(listlifeAct);
				playlist.setPlaylistlifedie(listlifeDie);
				playlist.setPlaylistname(listname);
				playlist.setProgramlist(programlist);
				playlist.setPubid("0");
				playlist.setScheduletype(ScheduleType);
				playlist.setTimequantum(quantums);

				int rowCount = playlistMapper.updateByPrimaryKeySelective(playlist);
				if (rowCount > 0) {
					jObject.put("result", "success");
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "列表名称:" + listname + "写入数据库失败!");
				}
			}

			if (playlistMapper.selectbygroupid(groupid).size() > 1) {
				String msg = jObject.getString("deleMessage");
				jObject.put("deleMessage", msg + "plpubid:" + plpubid + "listid:" + listid);
			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject UpdatePlaylistName(String playlistid, String listname) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = new playlist();
			playlist.setplaylistSN(playlistid);
			playlist.setPlaylistname(listname);

			int rowCount = playlistMapper.updateByPrimaryKeySelective(playlist);
			if (rowCount > 0) {
				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "列表名称:" + listname + "写入数据库失败!");
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject UpdateRemarks(String infosn, String remarks) {
		JSONObject jObject = new JSONObject();
		try {
			advertisement advertisement = new advertisement();
			advertisement.setinfoSN(infosn);
			advertisement.setRemarks(remarks);

			int rowCount = advertisementMapper.updateByPrimaryKeySelective(advertisement);
			if (rowCount > 0) {
				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "修改广告id" + infosn + "备注:" + remarks + "写入数据库失败!");
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject Copyinfobyid(String infosn, int groupid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			advertisement adv = advertisementMapper.selectByPrimaryKey(infosn);
			int i = 1;
			String newInfoNameString = "";
			while (true) {
				if (advertisementMapper.selectCountByName(adv.getAdvname() + "_copy" + i, groupid) <= 0) {
					newInfoNameString = adv.getAdvname() + "_copy" + i;
					break;
				}
				i += 1;
			}

			if (newInfoNameString != "") {
				Date now = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// DateFormat d1 = DateFormat.getDateTimeInstance();
				advertisement advertisement = new advertisement();
				String newinfoSN = getCode.getIncreaseIdByCurrentTimeMillis();
				advertisement.setinfoSN(newinfoSN);
				advertisement.setAdvname(newInfoNameString);
				String dts = adv.getlifeAct();
				String dte = adv.getlifeDie();
				if (!dte.equals("") && formatter.parse(adv.getlifeDie()).compareTo(now) < 0) {
					dts = "";
					dte = "";
				}
				advertisement.setlifeAct(dts);
				advertisement.setlifeDie(dte);
				advertisement.setAdvtype("0");
				advertisement.setinfoState(0);
				advertisement.setcreater(adminid);
				advertisement.setcreateDate(d1.format(now));
				advertisement.setinfoState(0);
				advertisement.setBackgroundstyle(adv.getBackgroundstyle());
				advertisement.setDelindex(0);
				advertisement.setGroupid(groupid);
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);
				advertisement.setplayTimelength(adv.getplayTimelength());// 默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if (rowCount > 0) {
					List<item> itemlist = itemMapper.selectByadid(infosn);
					for (int j = 0; j < itemlist.size(); j++) {
						item olditem = itemlist.get(j);

						item item = new item();
						item.setInfoSN(newinfoSN);
						item.setPageid(olditem.getPageid());
						item.setItemid(olditem.getItemid());
						item.setItemtype(olditem.getItemtype());
						item.setItemleft(olditem.getItemleft());
						item.setItemtop(olditem.getItemtop());
						item.setItemwidth(olditem.getItemwidth());
						item.setItemheight(olditem.getItemheight());
						item.setItemfontno(olditem.getItemfontno());
						item.setItembackcolor(olditem.getItembackcolor());
						item.setItembackopacity(olditem.getItembackopacity());
						item.setItemforecolor(olditem.getItemforecolor());
						item.setItemforeopacity(olditem.getItemforeopacity());
						item.setDelindex(0);
						item.setItemstyle(olditem.getItemstyle());
						item.setItemcontext(olditem.getItemcontext());
						item.setItemcontextjson(olditem.getItemcontextjson());
						itemMapper.insert(item);

						jObject.put("result", "success");
					}
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "复制写入数据库失败!");
				}

			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject Deleteinfobyid(String infosn, int groupid, int infopubid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			List<playlistcode> plcodes = playlistcodeMapper.selectByGroupidDate(groupid, formatter.format(new Date()));
			boolean isF = false;
			if (plcodes != null && plcodes.size() > 0) {
				for (int i = 0; i < plcodes.size(); i++) {
					if (plcodes.get(i).getContainADID() != null && plcodes.get(i).getContainADID() != "") {
						String ContainADID = plcodes.get(i).getContainADID().trim();
						String[] arrADID = ContainADID.split(",");
						for (int j = 0; j < arrADID.length; j++) {
							if (Integer.parseInt(arrADID[j]) == infopubid) {
								isF = true;
								break;
							}
						}
						if (isF) {
							break;
						}
					}
				}
			}
			if (isF) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除广告在已发布广告列表中存在不能删除!");
			} else {
				advertisementMapper.updateDelindexByid(infosn, 1);
				infocodeMapper.deleteByinfoSN(infosn);
				int result = updateLists(groupid);
				jObject.put("result", "success");
			}
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject GetInfocodebyid(String infosn) {
		JSONObject jObject = new JSONObject();
		try {
			JSONArray infocodelist = new JSONArray();
			int ADID = 100;
			List<infocode> ifcodeList = infocodeMapper.selectByinfoSN(infosn);
			for (int j = 0; j < ifcodeList.size(); j++) {
				String Codecontext = ifcodeList.get(j).getCodecontext();
				JSONArray ctArray = JSONArray.parseArray(Codecontext);
				infocodelist.addAll(ctArray);
				ADID = ifcodeList.get(j).getPubid();
			}

			List<byte[]> SendplByteList = getCode.GetplaylistCodeListbyid(ADID);

			JSONArray pjsonArray = new JSONArray();
			int PackLength = 0;
			for (int i = 0; i < SendplByteList.size(); i++) {
				byte[] bytes = SendplByteList.get(i);
				PackLength += bytes.length;
				StringBuilder buf = new StringBuilder(bytes.length * 2);
				for (byte b : bytes) { // 使用String的format方法进行转换
					buf.append(String.format("%02x", new Integer(b & 0xff)) + " ");
				}
				pjsonArray.add(buf.toString().trim());
			}
			if (PackLength > 250 * 1024) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "广告最大长度超过250K");
			} else {
				jObject.put("result", "success");
				jObject.put("infocodelist", infocodelist);
				jObject.put("plcodelist", pjsonArray);
			}

			return jObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject DeleteinfoList(String listid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist oldpl = playlistMapper.selectByPrimaryKey(listid);
			Date now = new Date();
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// DateFormat d1 = DateFormat.getDateTimeInstance();
			playlistMapper.updatedelindexByPrimaryKey(listid, 1, adminid, d1.format(now));
			int plcCount = playlistcodeMapper.selectcoutByplaylistSN(listid);
			if (plcCount > 0) {
				playlistcodeMapper.deleteByplaylistSN(listid);

				JSONArray oldplArray = JSONArray.parseArray(oldpl.getProgramlist());

				deletePublishInfo(oldplArray, oldpl.getGroupid(), adminid);
			}

			// 写入项目表，发布广告改动时间数据
			projectMapper.updateAdvUpdateTimeByPrimaryKey(
					groupMapper.selectByPrimaryKey(oldpl.getGroupid()).getProjectid(), d1.format(now));
			int result = updateLists(oldpl.getGroupid());
			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject PublishinfoListbyGroupid(String listid, int adminid, int groupid) {
		JSONObject jObject = new JSONObject();
		try {
			int ptmode = groupMapper.selectByPrimaryKey(groupid).getPtMode() != null
					? groupMapper.selectByPrimaryKey(groupid).getPtMode()
					: 0;
			switch (ptmode) {
			case 0:
				jObject = PublishinfoList(listid, adminid);
				break;
			case 1:
				jObject = PublishinfoListbydate(listid, adminid);
				break;
			case 2:
				jObject = PublishinfoListbydate2(listid, adminid);
				break;
			}
			int result = updateLists(groupid);
			return jObject;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public int updateLists(int groupid) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List<String> lpids = new ArrayList<String>();// 播放列表pubid集合
			List<String> ipids = new ArrayList<String>();// 播放列表包含广告pubid集合
			List<String> tipids = new ArrayList<String>();// 涌余广告pubid集合

			Date now = new Date();
			int interval = 2;// 时间暂时都写取未来2天的,以后改成按照分组取
			Date afterDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * interval);

			List<playlistcode> playlistcodes = playlistcodeMapper.selectByGroupidDate2(groupid, formatter.format(now),
					formatter.format(afterDate));

			for (int i = 0; i < playlistcodes.size(); i++) {
				int lpid = playlistcodes.get(i).getPubid();
				String infoidString = playlistcodes.get(i).getContainADID();
				if (!lpids.contains(Integer.toString(lpid))) {
					lpids.add(Integer.toString(lpid));
				}
				String[] iids = infoidString.split(",");
				for (int j = 0; j < iids.length; j++) {
					if (!ipids.contains(iids[j])) {
						ipids.add(iids[j]);
						String infosn = advertisementMapper.selectByPubidandGroupid(Integer.parseInt(iids[j]), groupid)
								.getinfoSN();
						advertisementMapper.updatedeletedateByid(infosn, 0, "");
					}
				}
			}

			List<infocode> infocodes = infocodeMapper.selectBygroupidDate(groupid, formatter.format(now));
			for (int i = 0; i < infocodes.size(); i++) {
				if (!tipids.contains(Integer.toString(infocodes.get(i).getPubid()))) {
					tipids.add(Integer.toString(infocodes.get(i).getPubid()));
				}
			}

			Collections.sort(lpids); // 升序排列
			Collections.sort(ipids); // 升序排列
			Collections.sort(tipids); // 升序排列

			group grp = groupMapper.selectByPrimaryKey(groupid);
			String PlayList = grp.getPlayList() == null ? "" : grp.getPlayList();
			String PlayAdList = grp.getPlayAdList() == null ? "" : grp.getPlayAdList();
			String TotalAdList = grp.getTotalAdList() == null ? "" : grp.getTotalAdList();

			if (!PlayList.equals(String.join(",", lpids)) || !PlayAdList.equals(String.join(",", ipids))
					|| !TotalAdList.equals(String.join(",", tipids))) {
				group record = new group();
				record.setGroupid(groupid);
				record.setPlayList(String.join(",", lpids));
				record.setPlayAdList(String.join(",", ipids));
				record.setTotalAdList(String.join(",", tipids));
				groupMapper.updateByPrimaryKeySelective(record);

				if (PlayList.equals(String.join(",", lpids)) && PlayAdList.equals(String.join(",", ipids))) {
					// 主要两个列表一样,不写更新时间
					return 1;
				} else {
					// 主要两个列表不一样,不写更新时间
					return 0;
				}
			} else {
				// 没变不修改
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	@Override
	public JSONObject updateAllLists() {
		JSONObject jObject = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			List<group> groups = groupMapper.selectAll();
			if (groups != null && groups.size() > 0) {
				for (int i = 0; i < groups.size(); i++) {
					int groupid = groups.get(i).getGroupid();
					int result = updateLists(groupid);

					if (result == 1) {
						Date now = new Date();
						SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// 写入项目表，发布广告改动时间数据
						projectMapper.updateAdvUpdateTimeByPrimaryKey(groups.get(i).getProjectid(), d1.format(now));
					}
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("groupid", groupid);
					jsonObject.put("result", result);
					jArray.add(jsonObject);
				}
			}

			jObject.put("result", "success");
			jObject.put("message", jArray.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.getMessage());
		}
		return jObject;
	}

	@Override
	public JSONObject PublishinfoList(String listid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = playlistMapper.selectByPrimaryKey(listid);
			String programlist = playlist.getProgramlist();
			if (programlist == null || programlist.equals("")) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}

			if (playlist.getScheduletype() == 2 || playlist.getPlaylistlevel() == 0) {
				// 顺序排期
				JSONArray jArrayLoop = JSONArray.parseArray(programlist);
				JSONArray jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop, true);

				int returnIndex = 0;
				String pidStrings = "";
				// 写入编码表
				if (jArrayinfo != null && jArrayinfo.size() > 0) {
					playlistcodeMapper.deleteByplaylistSN(listid);// 发布之前删除播放列表编码数据
					for (int i = 0; i < jArrayinfo.size(); i++) {
						int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());
						pidStrings += plpid + ",";
						JSONObject jsonObject = jArrayinfo.getJSONObject(i);
						int retplaylistIndex = getCode.GetplaylistCodeListbyid(listid, jsonObject, plpid,
								playlist.getPlaylistlevel(), playlist.getTimequantum(), playlist.getGroupid());
						if (retplaylistIndex != 0) {
							returnIndex = retplaylistIndex;
							break;
						}
					}
				}

				if (returnIndex == 0) {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// DateFormat d1 = DateFormat.getDateTimeInstance();

					List<advertisement> advlist = new ArrayList<advertisement>();
					// 更新广告发布日期
					if (programlist != null && programlist != "") {
						JSONArray plArray = JSONArray.parseArray(programlist);
						for (int i = 0; i < plArray.size(); i++) {
							String infoid = plArray.getJSONObject(i).getString("infoid");
							advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoid);
							advlist.add(advertisement);
							Object publishDate = advertisementMapper.selectpublishDateByPrimaryKey(infoid);
							if (publishDate == null || publishDate.equals("")) {
								advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
							}
						}
					}

					if (advlist.size() > 0) {
						// NettyClient.TcpSocketSendPublish(advlist, null);
					}

					if (pidStrings.length() > 0) {
						pidStrings = pidStrings.substring(0, pidStrings.length() - 1);
					}
					// 修改pubid
					playlistMapper.updatemutipubidByPrimaryKey(listid, pidStrings, adminid, d1.format(now),
							jArrayinfo.toJSONString());

					JSONArray jsonArr = new JSONArray();

					JSONObject jsonObject = Jionplaylist(playlist);
					if (jsonObject != null) {
						JSONArray delArray = jsonObject.getJSONArray("delelePlatlistSNs");
						JSONArray udeArray = jsonObject.getJSONArray("updatePlatlistSNs");

						if (delArray != null && delArray.size() > 0) {
							for (int i = 0; i < delArray.size(); i++) {
								JSONObject jObject2 = DeleteinfoList(delArray.getString(i), adminid);
								JSONObject jo = new JSONObject();
								jo.put("changtype", "delete");
								jo.put("playlistsn", delArray.getString(i));
								jsonArr.add(jo);
							}
						}

						if (udeArray != null && udeArray.size() > 0) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							String st = playlist.getPlaylistlifeact().equals("") ? "1999-09-09"
									: playlist.getPlaylistlifeact();
							Date d = formatter.parse(st);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(d);
							calendar.add(Calendar.DATE, -1);
							String newLifeDieString = formatter.format(calendar.getTime());

							for (int i = 0; i < udeArray.size(); i++) {
								JSONObject reJsonObject = updateplaylistdate(udeArray.getString(i),
										playlistMapper.selectByPrimaryKey(udeArray.getString(i)).getPlaylistlifeact(),
										newLifeDieString, adminid);
								if (reJsonObject.getString("result").equals("success")) {
									JSONObject jo = new JSONObject();
									jo.put("changtype", "update");
									jo.put("playlistsn", udeArray.getString(i));
									jo.put("newsn", reJsonObject.getString("newsn"));
									jo.put("lifeAct", reJsonObject.getString("lifeAct"));
									jo.put("lifeDie", reJsonObject.getString("lifeDie"));
									jo.put("pidStrings", reJsonObject.getString("pidStrings"));
									jsonArr.add(jo);
								}

							}
						}
					}

					// 写入项目表，发布广告改动时间数据
					projectMapper.updateAdvUpdateTimeByPrimaryKey(
							groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
					jObject.put("result", "success");
					jObject.put("pubid", pidStrings);
					jObject.put("changeInfo", jsonArr.toJSONString());
				} else {
					playlistMapper.deleteByPrimaryKey(listid);
					playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
					jObject.put("result", "fail");
					jObject.put("resultMessage", "播放列表编码错误");
				}
			} else {
				// 模板排期
				JSONArray jArrayLoop = JSONArray.parseArray(programlist);
				JSONArray jArrayinfo = getCode.GetJsonArrayinfolist2(jArrayLoop, true);

				int returnIndex = 0;
				String pidStrings = "";
				// 写入编码表
				if (jArrayinfo != null && jArrayinfo.size() > 0) {
					playlistcodeMapper.deleteByplaylistSN(listid);// 发布之前删除播放列表编码数据
					for (int i = 0; i < jArrayinfo.size(); i++) {
						int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());
						pidStrings += plpid + ",";
						JSONObject jsonObject = jArrayinfo.getJSONObject(i);
						int retplaylistIndex = getCode.GetplaylistCodeListbyid(listid, jsonObject, plpid,
								playlist.getPlaylistlevel(), playlist.getTimequantum(), playlist.getGroupid());
						if (retplaylistIndex != 0) {
							returnIndex = retplaylistIndex;
							break;
						}
					}
				}

				if (returnIndex == 0) {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// DateFormat d1 = DateFormat.getDateTimeInstance();

					List<advertisement> advlist = new ArrayList<advertisement>();
					// 更新广告发布日期
					if (programlist != null && programlist != "") {
						JSONArray plArray = JSONArray.parseArray(programlist);
						for (int i = 0; i < plArray.size(); i++) {
							String infoid = plArray.getJSONObject(i).getString("infoid");
							advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoid);
							advlist.add(advertisement);
							Object publishDate = advertisementMapper.selectpublishDateByPrimaryKey(infoid);

							if (publishDate == null || publishDate.equals("")) {
								advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
							}
						}
					}
					if (advlist.size() > 0) {
						// NettyClient.TcpSocketSendPublish(advlist, null);
					}

					if (pidStrings.length() > 0) {
						pidStrings = pidStrings.substring(0, pidStrings.length() - 1);
					}
					// 修改pubid
					playlistMapper.updatemutipubidByPrimaryKey(listid, pidStrings, adminid, d1.format(now),
							jArrayinfo.toJSONString());

					JSONArray jsonArr = new JSONArray();

					JSONObject jsonObject = Jionplaylist(playlist);
					if (jsonObject != null) {
						JSONArray delArray = jsonObject.getJSONArray("delelePlatlistSNs");
						JSONArray udeArray = jsonObject.getJSONArray("updatePlatlistSNs");

						if (delArray != null && delArray.size() > 0) {
							for (int i = 0; i < delArray.size(); i++) {
								JSONObject jObject2 = DeleteinfoList(delArray.getString(i), adminid);
								JSONObject jo = new JSONObject();
								jo.put("changtype", "delete");
								jo.put("playlistsn", delArray.getString(i));
								jsonArr.add(jo);
							}
						}

						if (udeArray != null && udeArray.size() > 0) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							String st = playlist.getPlaylistlifeact().equals("") ? "1999-09-09"
									: playlist.getPlaylistlifeact();
							Date d = formatter.parse(st);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(d);
							calendar.add(Calendar.DATE, -1);
							String newLifeDieString = formatter.format(calendar.getTime());

							for (int i = 0; i < udeArray.size(); i++) {
								JSONObject reJsonObject = updateplaylistdate(udeArray.getString(i),
										playlistMapper.selectByPrimaryKey(udeArray.getString(i)).getPlaylistlifeact(),
										newLifeDieString, adminid);
								if (reJsonObject.getString("result").equals("success")) {
									JSONObject jo = new JSONObject();
									jo.put("changtype", "update");
									jo.put("playlistsn", udeArray.getString(i));
									jo.put("newsn", reJsonObject.getString("newsn"));
									jo.put("lifeAct", reJsonObject.getString("lifeAct"));
									jo.put("lifeDie", reJsonObject.getString("lifeDie"));
									jo.put("pidStrings", reJsonObject.getString("pidStrings"));
									jsonArr.add(jo);
								}

							}
						}
					}

					// 写入项目表，发布广告改动时间数据
					projectMapper.updateAdvUpdateTimeByPrimaryKey(
							groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
					jObject.put("result", "success");
					jObject.put("pubid", pidStrings);
					jObject.put("changeInfo", jsonArr.toJSONString());
				} else {
					playlistMapper.deleteByPrimaryKey(listid);
					playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
					jObject.put("result", "fail");
					jObject.put("resultMessage", "播放列表编码错误");
				}
			}

			return jObject;

		} catch (Exception e) {
			playlistMapper.deleteByPrimaryKey(listid);
			playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject PublishinfoListbydate(String listid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = playlistMapper.selectByPrimaryKey(listid);
			String programlist = playlist.getProgramlist();
			if (programlist == null || programlist.equals("")) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}
			JSONArray jArrayLoop = JSONArray.parseArray(programlist);
			JSONArray jArrayinfo = null;
			if (playlist.getScheduletype() == 2 || playlist.getPlaylistlevel() == 0) {
				// 顺序排期
				jArrayinfo = getCode.GetJsonArrayinfolistbydate(jArrayLoop, playlist.getPlaylistlifeact(),
						playlist.getPlaylistlifedie(), 1);
			} else {
				jArrayinfo = getCode.GetJsonArrayinfolistbydate2(jArrayLoop, playlist.getPlaylistlifeact(),
						playlist.getPlaylistlifedie(), 1);
			}
			int returnIndex = 0;
			String pidStrings = "";
			// 写入编码表
			if (jArrayinfo != null && jArrayinfo.size() > 0) {
				playlistcodeMapper.deleteByplaylistSN(listid);// 发布之前删除播放列表编码数据
				for (int i = 0; i < jArrayinfo.size(); i++) {
					int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());
					pidStrings += plpid + ",";
					JSONObject jsonObject = jArrayinfo.getJSONObject(i);
					int retplaylistIndex = getCode.GetplaylistCodeListbyid(listid, jsonObject, plpid,
							playlist.getPlaylistlevel(), playlist.getTimequantum(), playlist.getGroupid());
					if (retplaylistIndex != 0) {
						returnIndex = retplaylistIndex;
						break;
					}
				}
			}

			if (returnIndex == 0) {
				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// DateFormat d1 = DateFormat.getDateTimeInstance();

				List<advertisement> advlist = new ArrayList<advertisement>();
				// 更新广告发布日期
				if (programlist != null && programlist != "") {
					JSONArray plArray = JSONArray.parseArray(programlist);
					for (int i = 0; i < plArray.size(); i++) {
						String infoid = plArray.getJSONObject(i).getString("infoid");
						advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoid);
						advlist.add(advertisement);
//							advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
						Object publishDate = advertisementMapper.selectpublishDateByPrimaryKey(infoid);
						if (publishDate == null || publishDate.equals("")) {
							advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
						}
					}
				}
				if (advlist.size() > 0) {
					// NettyClient.TcpSocketSendPublish(advlist, null);
				}

				if (pidStrings.length() > 0) {
					pidStrings = pidStrings.substring(0, pidStrings.length() - 1);
				}
				// 修改pubid
				playlistMapper.updatemutipubidByPrimaryKey(listid, pidStrings, adminid, d1.format(now),
						jArrayinfo.toJSONString());

				JSONArray jsonArr = new JSONArray();

				JSONObject jsonObject = Jionplaylist(playlist);
				if (jsonObject != null) {
					JSONArray delArray = jsonObject.getJSONArray("delelePlatlistSNs");
					JSONArray udeArray = jsonObject.getJSONArray("updatePlatlistSNs");

					if (delArray != null && delArray.size() > 0) {
						for (int i = 0; i < delArray.size(); i++) {
							JSONObject jObject2 = DeleteinfoList(delArray.getString(i), adminid);
							JSONObject jo = new JSONObject();
							jo.put("changtype", "delete");
							jo.put("playlistsn", delArray.getString(i));
							jsonArr.add(jo);
						}
					}

					if (udeArray != null && udeArray.size() > 0) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String st = playlist.getPlaylistlifeact().equals("") ? "1999-09-09"
								: playlist.getPlaylistlifeact();
						Date d = formatter.parse(st);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(d);
						calendar.add(Calendar.DATE, -1);
						String newLifeDieString = formatter.format(calendar.getTime());

						for (int i = 0; i < udeArray.size(); i++) {
							JSONObject reJsonObject = updateplaylistdate(udeArray.getString(i),
									playlistMapper.selectByPrimaryKey(udeArray.getString(i)).getPlaylistlifeact(),
									newLifeDieString, adminid);
							if (reJsonObject.getString("result").equals("success")) {
								JSONObject jo = new JSONObject();
								jo.put("changtype", "update");
								jo.put("playlistsn", udeArray.getString(i));
								jo.put("newsn", reJsonObject.getString("newsn"));
								jo.put("lifeAct", reJsonObject.getString("lifeAct"));
								jo.put("lifeDie", reJsonObject.getString("lifeDie"));
								jo.put("pidStrings", reJsonObject.getString("pidStrings"));
								jsonArr.add(jo);
							}

						}
					}
				}

				// 写入项目表，发布广告改动时间数据
				projectMapper.updateAdvUpdateTimeByPrimaryKey(
						groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
				jObject.put("result", "success");
				jObject.put("pubid", pidStrings);
				jObject.put("changeInfo", jsonArr.toJSONString());
			} else {
				playlistMapper.deleteByPrimaryKey(listid);
				playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表编码错误");
			}
			return jObject;

		} catch (Exception e) {
			playlistMapper.deleteByPrimaryKey(listid);
			playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject PublishinfoListbydate2(String listid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = playlistMapper.selectByPrimaryKey(listid);
			String programlist = playlist.getProgramlist();
			if (programlist == null || programlist.equals("")) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}
			JSONArray jArrayLoop = JSONArray.parseArray(programlist);
			JSONArray jArrayinfo = null;
			if (playlist.getScheduletype() == 2 || playlist.getPlaylistlevel() == 0) {
				// 顺序排期
				jArrayinfo = getCode.GetJsonArrayinfolistbydate(jArrayLoop, playlist.getPlaylistlifeact(),
						playlist.getPlaylistlifedie(), 2);
			} else {
				jArrayinfo = getCode.GetJsonArrayinfolistbydate2(jArrayLoop, playlist.getPlaylistlifeact(),
						playlist.getPlaylistlifedie(), 2);
			}
			int returnIndex = 0;
			String pidStrings = "";
			// 写入编码表
			if (jArrayinfo != null && jArrayinfo.size() > 0) {
				playlistcodeMapper.deleteByplaylistSN(listid);// 发布之前删除播放列表编码数据
				for (int i = 0; i < jArrayinfo.size(); i++) {
					int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());
					pidStrings += plpid + ",";
					JSONObject jsonObject = jArrayinfo.getJSONObject(i);
					int retplaylistIndex = getCode.GetplaylistCodeListbyid(listid, jsonObject, plpid,
							playlist.getPlaylistlevel(), playlist.getTimequantum(), playlist.getGroupid());
					if (retplaylistIndex != 0) {
						returnIndex = retplaylistIndex;
						break;
					}
				}
			}

			if (returnIndex == 0) {
				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// DateFormat d1 = DateFormat.getDateTimeInstance();

				List<advertisement> advlist = new ArrayList<advertisement>();
				// 更新广告发布日期
				if (programlist != null && programlist != "") {
					JSONArray plArray = JSONArray.parseArray(programlist);
					for (int i = 0; i < plArray.size(); i++) {
						String infoid = plArray.getJSONObject(i).getString("infoid");
						advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoid);
						advlist.add(advertisement);
//							advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
						Object publishDate = advertisementMapper.selectpublishDateByPrimaryKey(infoid);
						if (publishDate == null || publishDate.equals("")) {
							advertisementMapper.updatepublishDateByid(infoid, adminid, d1.format(now));
						}
					}
				}
				if (advlist.size() > 0) {
					// NettyClient.TcpSocketSendPublish(advlist, null);
				}

				if (pidStrings.length() > 0) {
					pidStrings = pidStrings.substring(0, pidStrings.length() - 1);
				}
				// 修改pubid
				playlistMapper.updatemutipubidByPrimaryKey(listid, pidStrings, adminid, d1.format(now),
						jArrayinfo.toJSONString());

				JSONArray jsonArr = new JSONArray();

				JSONObject jsonObject = Jionplaylist(playlist);
				if (jsonObject != null) {
					JSONArray delArray = jsonObject.getJSONArray("delelePlatlistSNs");
					JSONArray udeArray = jsonObject.getJSONArray("updatePlatlistSNs");

					if (delArray != null && delArray.size() > 0) {
						for (int i = 0; i < delArray.size(); i++) {
							JSONObject jObject2 = DeleteinfoList(delArray.getString(i), adminid);
							JSONObject jo = new JSONObject();
							jo.put("changtype", "delete");
							jo.put("playlistsn", delArray.getIntValue(i));
							jsonArr.add(jo);
						}
					}

					if (udeArray != null && udeArray.size() > 0) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String st = playlist.getPlaylistlifeact().equals("") ? "1999-09-09"
								: playlist.getPlaylistlifeact();
						Date d = formatter.parse(st);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(d);
						calendar.add(Calendar.DATE, -1);
						String newLifeDieString = formatter.format(calendar.getTime());

						for (int i = 0; i < udeArray.size(); i++) {
							JSONObject reJsonObject = updateplaylistdate(udeArray.getString(i),
									playlistMapper.selectByPrimaryKey(udeArray.getString(i)).getPlaylistlifeact(),
									newLifeDieString, adminid);
							if (reJsonObject.getString("result").equals("success")) {
								JSONObject jo = new JSONObject();
								jo.put("changtype", "update");
								jo.put("playlistsn", udeArray.getIntValue(i));
								jo.put("newsn", reJsonObject.getIntValue("newsn"));
								jo.put("lifeAct", reJsonObject.getString("lifeAct"));
								jo.put("lifeDie", reJsonObject.getString("lifeDie"));
								jo.put("pidStrings", reJsonObject.getString("pidStrings"));
								jsonArr.add(jo);
							}

						}
					}
				}

				// 写入项目表，发布广告改动时间数据
				projectMapper.updateAdvUpdateTimeByPrimaryKey(
						groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
				jObject.put("result", "success");
				jObject.put("pubid", pidStrings);
				jObject.put("changeInfo", jsonArr.toJSONString());
			} else {
				playlistMapper.deleteByPrimaryKey(listid);
				playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表编码错误");
			}
			return jObject;

		} catch (Exception e) {
			playlistMapper.deleteByPrimaryKey(listid);
			playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	/*
	 * 判断列表和原有列表生命周期时间段是否有交集 有交集的 旧列表开始日期>=新列表开始日期,提示并删除旧列表 旧列表开始日期<新列表开始日期,提示并截断旧列表
	 */
	private JSONObject Jionplaylist(playlist playlist) {
		JSONObject jObject = new JSONObject();
		try {
			List<String> delelePlatlistSNs = new ArrayList<String>();
			List<String> updatePlatlistSNs = new ArrayList<String>();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			List<playlist> playlists = playlistMapper.selectbygroupidDate(playlist.getGroupid(),
					formatter.format(new Date()));
			if (playlists != null && playlists.size() > 0) {
				String st = playlist.getPlaylistlifeact().equals("") ? "1999-09-09" : playlist.getPlaylistlifeact();
				String et = playlist.getPlaylistlifedie().equals("") ? "2100-09-09" : playlist.getPlaylistlifedie();

				JSONObject jsonObject = JSONObject.parseObject(playlist.getTimequantum());
				JSONArray jsonArray = jsonObject.getJSONArray("timelist");

				for (int i = 0; i < playlists.size(); i++) {
					playlist item = playlists.get(i);
					if (!item.getplaylistSN().equals(playlist.getplaylistSN())
							&& item.getPlaylistlevel() == playlist.getPlaylistlevel()) {
						String act = item.getPlaylistlifeact().equals("") ? "1999-09-09" : item.getPlaylistlifeact();
						String die = item.getPlaylistlifedie().equals("") ? "2100-09-09" : item.getPlaylistlifedie();
						if (!(act.compareTo(et) > 0 || die.compareTo(st) < 0))// 判断生命周期是否有交集
						{
							/*
							 * 判断时间集合是否有交集
							 */
							boolean isJoin = false;
							for (int j = 0; j < jsonArray.size(); j++) {
								int stval = jsonArray.getJSONObject(j).getIntValue("lifeAct");
								int etval = jsonArray.getJSONObject(j).getIntValue("lifeDie") == 0 ? 24 * 60
										: jsonArray.getJSONObject(j).getIntValue("lifeDie");

								JSONObject itemObject = JSONObject.parseObject(item.getTimequantum());
								JSONArray itemArray = itemObject.getJSONArray("timelist");

								for (int z = 0; z < itemArray.size(); z++) {
									int lstval = itemArray.getJSONObject(z).getIntValue("lifeAct");
									int letval = itemArray.getJSONObject(z).getIntValue("lifeDie") == 0 ? 24 * 60
											: itemArray.getJSONObject(z).getIntValue("lifeDie");

									if ((stval >= lstval && stval < letval) || (etval > lstval && etval < letval)
											|| (stval <= lstval && etval >= letval)) {
										isJoin = true;
										break;
									}
								}
							}
							if (isJoin) {
								int swith = 0;
								if (act.compareTo(st) >= 0)// 旧列表开始日期>=新列表开始日期,提示并删除旧列表
								{
									swith = 1;
								}
								if (act.compareTo(st) < 0)// 旧列表开始日期<新列表开始日期,提示并截断旧列表
								{
									swith = 2;
								}
								switch (swith) {
								case 1:
									/*
									 * 删除列表
									 */
//								JSONObject jObject2 = DeleteinfoList(item.getplaylistSN(), adminid);
									delelePlatlistSNs.add(item.getplaylistSN());
									break;

								case 2:
									/*
									 * 截断列表
									 */
//								Date d = formatter.parse(st);
//								Calendar calendar = Calendar.getInstance();
//								calendar.setTime(d);
//								calendar.add(Calendar.DATE, -1);
//
//								item.setPlaylistlifedie(formatter.format(calendar.getTime()));
//								playlistMapper.updateByPrimaryKeySelective(item);
									// 重新编码
									updatePlatlistSNs.add(item.getplaylistSN());
									break;
								}
							}
						}
					}
				}
			}

			jObject.put("delelePlatlistSNs", delelePlatlistSNs);
			jObject.put("updatePlatlistSNs", updatePlatlistSNs);
			return jObject;

		} catch (Exception e) {
			return null;
		}
	}

	private JSONObject updateplaylistdate(String playlistSN, String lifeAct, String lifeDie, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = playlistMapper.selectByPrimaryKey(playlistSN);// 获取原播放列表

			Date now = new Date();
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int delpCount = playlistMapper.updatedelindexByPrimaryKey(playlistSN, 1, adminid, d1.format(now));// 数据库原列表加删除标识
			int delpcCount = playlistcodeMapper.deleteByplaylistSN(playlistSN);// 数据库原列表编码删除

			playlist item = new playlist();
			String newplaylistSN = getCode.getIncreaseIdByCurrentTimeMillis();
			item.setplaylistSN(newplaylistSN);

			item.setDelindex(0);
			item.setGroupid(playlist.getGroupid());
			item.setPlaylistlevel(playlist.getPlaylistlevel());
			item.setPlaylistlifeact(lifeAct);
			item.setPlaylistlifedie(lifeDie);
			item.setcreater(adminid);
			item.setcreateDate(d1.format(now));
			item.setPlaylistname(playlist.getPlaylistname());
			item.setProgramlist(playlist.getProgramlist());
			item.setPubid("0");
			item.setScheduletype(playlist.getScheduletype());
			item.setTimequantum(playlist.getTimequantum());

			int rowCount = playlistMapper.insert(item);

			int ptmode = groupMapper.selectByPrimaryKey(playlist.getGroupid()).getPtMode();
			JSONObject jsonObject = null;
			switch (ptmode) {
			case 0:

				break;
			case 1:
				jsonObject = PublishinfoListbydate(item.getplaylistSN(), adminid);
				break;
			case 2:
				jsonObject = PublishinfoListbydate2(item.getplaylistSN(), adminid);
				break;
			default:
				break;
			}
			if (jsonObject.getString("result").equals("success")) {
				String pidStrings = jsonObject.getString("pubid");
				jObject.put("result", "success");
				jObject.put("oldsn", playlistSN);
				jObject.put("newsn", item.getplaylistSN());
				jObject.put("lifeAct", lifeAct);
				jObject.put("lifeDie", lifeDie);
				jObject.put("pidStrings", pidStrings);
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表编码错误");
			}
			return jObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject getbyteslistbyTemp(int listtype, String quantums, int ScheduleType, String programlist) {
		JSONObject jObject = new JSONObject();
		try {
			if (programlist == null || programlist.equals("")) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "播放列表没有排期数据!");
				return jObject;
			}

			JSONArray jsonArray = new JSONArray();

			if (ScheduleType == 2) {
				JSONArray jArrayLoop = JSONArray.parseArray(programlist);
				JSONArray jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop, true);

				if (jArrayinfo != null && jArrayinfo.size() > 0) {
					for (int j = 0; j < jArrayinfo.size(); j++) {
						JSONObject jsonObject = jArrayinfo.getJSONObject(j);
						List<byte[]> SendByteList = getCode.GetplaylistCodeListbyid(listtype, ScheduleType, 100 + j,
								quantums, jsonObject.toJSONString());

						if (SendByteList != null) {
							int PackLength = 0;
							for (int i = 0; i < SendByteList.size(); i++) {
								byte[] bytes = SendByteList.get(i);
								PackLength += bytes.length;
								StringBuilder buf = new StringBuilder(bytes.length * 2);
								for (byte b : bytes) { // 使用String的format方法进行转换
									buf.append(String.format("%02x", new Integer(b & 0xff)) + " ");
								}
								jsonArray.add(buf.toString().trim());
							}
							/*
							 * if(PackLength > 250*1024) { jObject.put("result", "fail");
							 * jObject.put("resultMessage", "广告最大长度超过250K"); }
							 */
						} else {
							jObject.put("result", "fail");
							jObject.put("resultMessage", "编码错误");

							return jObject;
						}
					}
				}
			} else {
				List<byte[]> SendByteList = getCode.GetplaylistCodeListbyid(listtype, ScheduleType, 100, quantums,
						programlist);
				if (SendByteList != null) {
					for (int i = 0; i < SendByteList.size(); i++) {
						byte[] bytes = SendByteList.get(i);
						StringBuilder buf = new StringBuilder(bytes.length * 2);
						for (byte b : bytes) { // 使用String的format方法进行转换
							buf.append(String.format("%02x", new Integer(b & 0xff)) + " ");
						}
						jsonArray.add(buf.toString().trim());
					}
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "编码错误");

					return jObject;
				}
			}

			JSONArray jArrayLoop = JSONArray.parseArray(programlist);

			JSONArray infocodelist = new JSONArray();
			List<String> conInfoidList = new ArrayList<String>();
			for (int i = 0; i < jArrayLoop.size(); i++) {
				String infoid = jArrayLoop.getJSONObject(i).getString("infoid");
				if (!conInfoidList.contains(infoid)) {
					conInfoidList.add(infoid);
					List<infocode> ifcodeList = infocodeMapper.selectByinfoSN(infoid);
					for (int j = 0; j < ifcodeList.size(); j++) {
						String Codecontext = ifcodeList.get(j).getCodecontext();
						JSONArray ctArray = JSONArray.parseArray(Codecontext);
						infocodelist.addAll(ctArray);
					}
				}
			}

			jObject.put("result", "success");
			jObject.put("infocodelist", infocodelist);
			jObject.put("plcodelist", jsonArray);

			return jObject;

		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject deleteList2old(String playlistSN) {
		JSONObject jObject = new JSONObject();
		try {
			playlist plist = playlistMapper.selectByPrimaryKey(playlistSN);

			oldplaylistMapper.deleteByPrimaryKey(plist.getplaylistSN());
			oldplaylist record = new oldplaylist();

			record.setCreatedate(plist.getcreateDate());
			record.setCreater(plist.getcreater());
			record.setDeletedate(plist.getdeleteDate());
			record.setDeleter(plist.getdeleter());
			record.setDelindex(plist.getDelindex());

			record.setGroupid(plist.getGroupid());
			record.setMutiprogramlist(plist.getMutiProgramlist());
			record.setPlaylistlevel(plist.getPlaylistlevel());
			record.setPlaylistlifeact(plist.getPlaylistlifeact());
			record.setPlaylistlifedie(plist.getPlaylistlifedie());

			record.setPlaylistname(plist.getPlaylistname());
			record.setPlaylistsn(plist.getplaylistSN());
			record.setProgramlist(plist.getProgramlist());
			record.setPubid(plist.getPubid());
			record.setPublishdate(plist.getpublishDate());

			record.setPublisher(plist.getpublisher());
			record.setScheduletype(plist.getScheduletype());
			record.setTimequantum(plist.getTimequantum());

			oldplaylistMapper.insertSelective(record);

			playlistMapper.deleteByPrimaryKey(playlistSN);
			playlistcodeMapper.deleteByplaylistSN(playlistSN);

			jObject.put("result", "success");
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}

		return jObject;
	}

	@Override
	public JSONObject DeleteList2oldbyDay() {
		JSONObject jObject = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject();
			JSONArray jArraySuccess = new JSONArray();
			JSONArray jArrayfail = new JSONArray();

			List<project> projects = projectMapper.selectAll();
			for (int p = 0; p < projects.size(); p++) {
				String projectid = projects.get(p).getProjectid();
				String limit = projects.get(p).getProjectLimit();
				int daylimit = 60;
				if (limit != null && !limit.equals("")) {
					JSONObject jsonLimit = JSONObject.parseObject(limit);
					if (jsonLimit != null) {
						daylimit = jsonLimit.getIntValue("ExpTime");
					}
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar date = Calendar.getInstance();
				date.setTime(new Date());
				date.set(Calendar.DATE, date.get(Calendar.DATE) - daylimit);
				String DeleteDate = df.format(date.getTime());

				List<playlist> playlists = playlistMapper.selectByDeleteDateProjectid(DeleteDate, projectid);

				if (playlists != null && playlists.size() > 0) {
					for (int i = 0; i < playlists.size(); i++) {
						JSONObject infojObject = deleteList2old(playlists.get(i).getplaylistSN());
						if (infojObject.getString("result").equals("success")) {
							jArraySuccess.add(playlists.get(i).getplaylistSN());
							System.out.println("删除列表" + playlists.get(i).getplaylistSN() + "成功]");
						} else {
							jArrayfail.add(playlists.get(i).getplaylistSN());
							System.out.println("删除列表" + playlists.get(i).getplaylistSN() + "失败]");
						}
					}
				}
			}

			System.out.println("成功删除列表数量" + jArraySuccess.size() + "个]");
			System.out.println("失败删除列表数量" + jArrayfail.size() + "个]");

			jsonObject.put("jArraySuccess", jArraySuccess);
			jsonObject.put("jArrayfail", jArrayfail);

			jObject.put("result", "success");
			jObject.put("infoMessage", jsonObject);

		} catch (Exception e) {
			// TODO: handle exception
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}
}
