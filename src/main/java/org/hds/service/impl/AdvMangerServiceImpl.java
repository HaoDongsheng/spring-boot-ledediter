package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hds.NettyClient;
import org.hds.Graphics.DrawTextGraphics;
import org.hds.logmapper.oldlogadvMapper;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.basemapMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.sensitiveMapper;
import org.hds.mapper.userMapper;
import org.hds.mapper.videoMapper;
import org.hds.model.advertisement;
import org.hds.model.basemap;
import org.hds.model.group;
import org.hds.model.item;
import org.hds.model.oldlogadv;
import org.hds.model.playlist;
import org.hds.model.project;
import org.hds.model.sensitive;
import org.hds.model.user;
import org.hds.model.video;
import org.hds.service.IAdvMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class AdvMangerServiceImpl implements IAdvMangerService {
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	itemMapper itemMapper;
	@Autowired
	groupMapper taxigroupMapper;
	@Autowired
	infocodeMapper infocodeMapper;
	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	playlistcodeMapper playlistcodeMapper;
	@Autowired
	basemapMapper basemapMapper;
	@Autowired
	videoMapper videoMapper;
	@Autowired
	getCode getCode;
	@Autowired
	oldlogadvMapper oldlogadvMapper;
	@Autowired
	userMapper userMapper;
	@Autowired
	sensitiveMapper sensitiveMapper;
	@Autowired
	projectMapper projectMapper;

	@Override
	public JSONArray getadvlist(JSONObject adminInfoJsonObject) {
		JSONArray itemJSONArray = new JSONArray();

		int isSuperuser = adminInfoJsonObject.getInteger("isSuperuser");
		if (isSuperuser == 0) {
			String adminGrps = adminInfoJsonObject.getString("adminGrps");
			String[] grpStrings = adminGrps.split(",");
			for (int i = 0; i < grpStrings.length; i++) {
				int grpid = Integer.parseInt(
						grpStrings[i].substring(grpStrings[i].indexOf('#') + 1, grpStrings[i].lastIndexOf('#')));
				String grpName = taxigroupMapper.selectByPrimaryKey(grpid).getGroupname();
				JSONObject jgrp = new JSONObject();
				jgrp.put("grpid", grpid);
				jgrp.put("grpname", grpName);

				List<advertisement> advlist = advertisementMapper.selectidBygroupid(grpid, 0);
				if (advlist != null && advlist.size() > 0) {
					for (int j = 0; j < advlist.size(); j++) {
						JSONObject jitem = new JSONObject();
						jitem.put("id", advlist.get(j).getinfoSN());
						jitem.put("advname", advlist.get(j).getAdvname());
						jitem.put("groupid", advlist.get(j).getGroupid());
						jitem.put("lifeAct", advlist.get(j).getlifeAct());
						jitem.put("lifeDie", advlist.get(j).getlifeDie());
						jitem.put("advType", advlist.get(j).getAdvtype());
						jitem.put("BackgroundStyle", advlist.get(j).getBackgroundstyle());
						jitem.put("playMode", advlist.get(j).getPlaymode());
						jitem.put("pubid", advlist.get(j).getPubid());
						jitem.put("playTimelength", advlist.get(j).getplayTimelength());
						jitem.put("DelIndex", advlist.get(j).getDelindex());
						if (jgrp.containsKey("itemlist")) {
							JSONArray JSONArray = jgrp.getJSONArray("itemlist");
							JSONArray.add(jitem);
						} else {
							JSONArray JSONArray = new JSONArray();
							JSONArray.add(jitem);
							jgrp.put("itemlist", JSONArray);
						}
					}
				}
				itemJSONArray.add(jgrp);
			}
		} else {
			List<group> tgrpsList = taxigroupMapper.selectAll();
			for (int i = 0; i < tgrpsList.size(); i++) {
				int grpid = tgrpsList.get(i).getGroupid();
				String grpName = tgrpsList.get(i).getGroupname();
				JSONObject jgrp = new JSONObject();
				jgrp.put("grpid", grpid);
				jgrp.put("grpname", grpName);
				itemJSONArray.add(jgrp);
			}

			List<advertisement> advlist = advertisementMapper.selectAll();

			if (advlist != null && advlist.size() > 0) {
				for (int i = 0; i < advlist.size(); i++) {
					JSONObject jitem = new JSONObject();
					jitem.put("id", advlist.get(i).getinfoSN());
					jitem.put("advname", advlist.get(i).getAdvname());
					jitem.put("groupid", advlist.get(i).getGroupid());
					jitem.put("lifeAct", advlist.get(i).getlifeAct());
					jitem.put("lifeDie", advlist.get(i).getlifeDie());
					jitem.put("advType", advlist.get(i).getAdvtype());
					jitem.put("BackgroundStyle", advlist.get(i).getBackgroundstyle());
					jitem.put("playMode", advlist.get(i).getPlaymode());
					jitem.put("pubid", advlist.get(i).getPubid());
					jitem.put("playTimelength", advlist.get(i).getplayTimelength());
					jitem.put("DelIndex", advlist.get(i).getDelindex());

					for (int j = 0; j < itemJSONArray.size(); j++) {
						JSONObject jobject = itemJSONArray.getJSONObject(j);
						if (jobject.getInteger("grpid") == advlist.get(i).getGroupid()) {
							if (jobject.containsKey("itemlist")) {
								JSONArray JSONArray = jobject.getJSONArray("itemlist");
								JSONArray.add(jobject);
							} else {
								JSONArray JSONArray = new JSONArray();
								JSONArray.add(jobject);
								jobject.put("itemlist", JSONArray);
							}
							break;
						}
					}
				}
			}
		}
		return itemJSONArray;
	}

	@Override
	public JSONObject Deleteinfo2oldbyDay() {
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

				List<advertisement> advertisements = advertisementMapper.selectByDeleteDateProjectid(DeleteDate,
						projectid);

				if (advertisements != null && advertisements.size() > 0) {
					for (int i = 0; i < advertisements.size(); i++) {
						JSONObject infojObject = deleteInfo2old(advertisements.get(i).getinfoSN());
						if (infojObject.getString("result").equals("success")) {
							jArraySuccess.add(advertisements.get(i).getinfoSN());
							System.out.println("删除广告" + advertisements.get(i).getinfoSN() + "成功]");
						} else {
							jArrayfail.add(advertisements.get(i).getinfoSN());
							System.out.println("删除广告" + advertisements.get(i).getinfoSN() + "失败]");
						}
					}
				}
			}

			System.out.println("成功删除广告数量" + jArraySuccess.size() + "个]");
			System.out.println("失败删除广告数量" + jArrayfail.size() + "个]");
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

	@Override
	public JSONArray getadvListbyGrpid(int Grpid) {
		JSONArray itemJSONArray = new JSONArray();

		List<advertisement> advlist = advertisementMapper.selectidBygroupid(Grpid, 0);
		if (advlist != null && advlist.size() > 0) {
			for (int j = 0; j < advlist.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", advlist.get(j).getinfoSN());
				jitem.put("advname", advlist.get(j).getAdvname());
				jitem.put("groupid", advlist.get(j).getGroupid());
				jitem.put("lifeAct", advlist.get(j).getlifeAct());
				jitem.put("lifeDie", advlist.get(j).getlifeDie());
				jitem.put("advType", advlist.get(j).getAdvtype());
				jitem.put("infoState", advlist.get(j).getinfoState());
				jitem.put("BackgroundStyle", advlist.get(j).getBackgroundstyle());
				jitem.put("playMode", advlist.get(j).getPlaymode());
				jitem.put("pubid", advlist.get(j).getPubid());
				jitem.put("playTimelength", advlist.get(j).getplayTimelength());
				jitem.put("DelIndex", advlist.get(j).getDelindex());

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONObject getadvListDelbyGrpid(int Grpid, int type, String infoName, String lifeAct, String lifeDie,
			int pageNum, int pageSize, String sort, String sortOrder) {

		String colName = "lifeDie";
		switch (sort) {
		case "infoname":
			colName = "advName";
			break;
		case "timelenght":
			colName = "playTimelength";
			break;
		default:
			colName = sort;
			break;
		}

		lifeAct = lifeAct == null || lifeAct.equals("") ? "1999-09-09" : lifeAct;
		lifeDie = lifeDie == null || lifeDie.equals("") ? "2100-09-09" : lifeDie;

		JSONObject jObject = new JSONObject();
		// int delCount = advertisementMapper.selectDelCountBygroupid(Grpid);
		int startoffset = (pageNum - 1) * pageSize;

		int delCount = 0;
		JSONArray itemJSONArray = new JSONArray();

		if (type == 0) {
			infoName = "%" + infoName + "%";
			delCount = advertisementMapper.selectDelCountBygroupid2(Grpid, infoName, lifeAct, lifeDie);
			List<advertisement> advlist = advertisementMapper.selectDelBygroupid2(Grpid, infoName, lifeAct, lifeDie,
					startoffset, pageSize, colName, sortOrder);

			if (advlist != null && advlist.size() > 0) {
				for (int j = 0; j < advlist.size(); j++) {
					JSONObject jitem = new JSONObject();
					jitem.put("infosn", advlist.get(j).getinfoSN());
					jitem.put("infoname", advlist.get(j).getAdvname());
					jitem.put("lifeAct", advlist.get(j).getlifeAct());
					jitem.put("lifeDie", advlist.get(j).getlifeDie());
					jitem.put("timelenght", advlist.get(j).getplayTimelength());
					JSONArray itemArray = new JSONArray();
					List<item> ItemList = itemMapper.selectByadid(advlist.get(j).getinfoSN());
					if (ItemList != null)
						for (int i = 0; i < ItemList.size(); i++) {
							itemArray.add(ItemList.get(i).getItemcontext());
						}
					jitem.put("items", itemArray);

					itemJSONArray.add(jitem);
				}

			}
		} else {
			delCount = oldlogadvMapper.selectDelCountBygroupid(Grpid, infoName, lifeAct, lifeDie);
			List<oldlogadv> advlist = oldlogadvMapper.selectDelBygroupid(Grpid, infoName, lifeAct, lifeDie, startoffset,
					pageSize, sort, sortOrder);

			if (advlist != null && advlist.size() > 0) {
				for (int j = 0; j < advlist.size(); j++) {
					JSONObject jitem = new JSONObject();
					jitem.put("infosn", advlist.get(j).getInfosn());
					jitem.put("infoname", advlist.get(j).getAdvname());
					jitem.put("lifeAct", advlist.get(j).getLifeact());
					jitem.put("lifeDie", advlist.get(j).getLifedie());
					jitem.put("timelenght", advlist.get(j).getPlaytimelength());
					JSONArray itemArray = new JSONArray();
					JSONArray jArray = JSONArray.parseArray(advlist.get(j).getItemlist());
					for (int i = 0; i < jArray.size(); i++) {
						itemArray.add(jArray.getJSONObject(i).getString("itemcontext"));
					}
					jitem.put("items", itemArray);
					itemJSONArray.add(jitem);
				}
			}
		}

		jObject.put("total", delCount);
		jObject.put("rows", itemJSONArray);

		return jObject;
	}

	@Override
	public JSONArray getadvEditListbyGrpid(int Grpid) {
		JSONArray itemJSONArray = new JSONArray();

		List<advertisement> advlist = advertisementMapper.selectEditlistBygroupid(Grpid, 0);
		if (advlist != null && advlist.size() > 0) {
			for (int j = 0; j < advlist.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", advlist.get(j).getinfoSN());
				jitem.put("advname", advlist.get(j).getAdvname());
				jitem.put("groupid", advlist.get(j).getGroupid());
				jitem.put("lifeAct", advlist.get(j).getlifeAct());
				jitem.put("lifeDie", advlist.get(j).getlifeDie());
				jitem.put("advType", advlist.get(j).getAdvtype());
				jitem.put("infoState", advlist.get(j).getinfoState());
				jitem.put("BackgroundStyle", advlist.get(j).getBackgroundstyle());
				jitem.put("playMode", advlist.get(j).getPlaymode());
				jitem.put("pubid", advlist.get(j).getPubid());
				jitem.put("playTimelength", advlist.get(j).getplayTimelength());
				jitem.put("DelIndex", advlist.get(j).getDelindex());

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONArray getadvListbyGrpidState(int Grpid, int infoState) {
		JSONArray itemJSONArray = new JSONArray();

		List<advertisement> advlist = advertisementMapper.selectidBygroupidState(Grpid, infoState);
		if (advlist != null && advlist.size() > 0) {
			for (int j = 0; j < advlist.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", advlist.get(j).getinfoSN());
				jitem.put("advname", advlist.get(j).getAdvname());
				jitem.put("groupid", advlist.get(j).getGroupid());
				jitem.put("lifeAct", advlist.get(j).getlifeAct());
				jitem.put("lifeDie", advlist.get(j).getlifeDie());
				jitem.put("advType", advlist.get(j).getAdvtype());
				jitem.put("infoState", advlist.get(j).getinfoState());
				jitem.put("BackgroundStyle", advlist.get(j).getBackgroundstyle());
				jitem.put("playMode", advlist.get(j).getPlaymode());
				jitem.put("pubid", advlist.get(j).getPubid());
				jitem.put("playTimelength", advlist.get(j).getplayTimelength());
				jitem.put("DelIndex", advlist.get(j).getDelindex());

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONObject GetImgbyGif(String gifString, int w, int h) {
		JSONObject jObject = new JSONObject();
		try {
			jObject = DrawTextGraphics.Gif2Img(gifString, w, h);
			jObject.put("result", "success");
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;

	}

	@Override
	public JSONObject Creatinfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			if (advertisementMapper.selectCountByName(infoName, groupid) <= 0) {
				advertisement advertisement = new advertisement();
				String infoSN = getCode.getIncreaseIdByCurrentTimeMillis();
				advertisement.setinfoSN(infoSN);
				advertisement.setAdvname(infoName);
				advertisement.setlifeAct(lifeAct);
				advertisement.setlifeDie(lifeDie);
				advertisement.setAdvtype("0");
				advertisement.setinfoState(0);
				advertisement.setcreater(adminid);
				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				advertisement.setcreateDate(d1.format(now));
				advertisement.setBackgroundstyle(BackgroundStyle);
				advertisement.setDelindex(0);
				advertisement.setGroupid(groupid);
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);
				advertisement.setplayTimelength(10);// 默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if (rowCount > 0) {
					jObject.put("result", "success");
					jObject.put("infoID", infoSN);
					jObject.put("infoName", infoName);

					jObject.put("groupid", groupid);
					jObject.put("advType", 0);
					jObject.put("BackgroundStyle", BackgroundStyle);
					jObject.put("playMode", 0);
					jObject.put("pubid", 0);
					jObject.put("DelIndex", 0);

				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "广告名称:" + infoName + "写入数据库失败!");
				}

			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "广告名称:" + infoName + "已存在!");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;

	}

	@Override
	public JSONObject CopyInfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			String itemlist) {
		JSONObject jObject = new JSONObject();
		try {
			int i = 1;
			String newInfoNameString = "";
			while (true) {
				if (advertisementMapper.selectCountByName(infoName + "_copy" + i, groupid) <= 0) {
					newInfoNameString = infoName + "_copy" + i;
					break;
				}
				i += 1;
			}

			if (newInfoNameString != "") {
				advertisement advertisement = new advertisement();
				String infoSN = getCode.getIncreaseIdByCurrentTimeMillis();
				advertisement.setinfoSN(infoSN);
				advertisement.setAdvname(newInfoNameString);
				advertisement.setlifeAct(lifeAct);
				advertisement.setlifeDie(lifeDie);
				advertisement.setAdvtype("0");
				advertisement.setinfoState(0);
				advertisement.setBackgroundstyle(BackgroundStyle);
				advertisement.setDelindex(0);
				advertisement.setGroupid(groupid);
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);
				advertisement.setplayTimelength(10);// 默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if (rowCount > 0) {
					JSONObject jsoninfo = JSONObject.parseObject(itemlist);
					JSONObject JitemObject = SaveItem(infoSN, jsoninfo);
					if (JitemObject.getString("result") == "success") {
						jObject.put("result", "success");
						jObject.put("infoID", infoSN);
						jObject.put("infoName", newInfoNameString);

						jObject.put("groupid", groupid);
						jObject.put("advType", 0);
						jObject.put("BackgroundStyle", BackgroundStyle);
						jObject.put("playMode", 0);
						jObject.put("pubid", 0);
						jObject.put("DelIndex", 0);
					} else {
						jObject.put("result", "fail");
						jObject.put("resultMessage", "广告名称:" + infoName + "复制条目写入数据库失败!");
					}
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "广告名称:" + infoName + "复制写入数据库失败!");
				}

			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}

		return jObject;
	}

	@Override
	public JSONObject CopyExpinfobyid(String infosn, int groupid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			oldlogadv adv = oldlogadvMapper.selectByPrimaryinfosn(infosn);
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

				advertisement advertisement = new advertisement();
				String newinfoSN = getCode.getIncreaseIdByCurrentTimeMillis();
				advertisement.setinfoSN(newinfoSN);
				advertisement.setAdvname(newInfoNameString);
				String dts = adv.getLifeact();
				String dte = adv.getLifedie();
				if (!dte.equals("") && formatter.parse(adv.getLifedie()).compareTo(now) < 0) {
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
				advertisement.setplayTimelength(adv.getPlaytimelength());// 默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if (rowCount > 0) {
					JSONArray jArray = JSONArray.parseArray(adv.getItemlist());
					for (int j = 0; j < jArray.size(); j++) {
						JSONObject jsonObject = jArray.getJSONObject(j);// .getString("itemcontext");

						item item = new item();
						item.setInfoSN(newinfoSN);
						item.setPageid(jsonObject.getIntValue("pageid"));
						item.setItemid(jsonObject.getIntValue("itemid"));
						item.setItemtype(jsonObject.getIntValue("itemtype"));
						item.setItemleft(jsonObject.getIntValue("itemleft"));
						item.setItemtop(jsonObject.getIntValue("itemtop"));
						item.setItemwidth(jsonObject.getIntValue("itemwidth"));
						item.setItemheight(jsonObject.getIntValue("itemheight"));
						item.setItemfontno(jsonObject.getIntValue("itemfontno"));
						item.setItembackcolor(jsonObject.getString("itembackcolor"));
						item.setItembackopacity(jsonObject.getIntValue("itembackopacity"));
						item.setItemforecolor(jsonObject.getString("itemforecolor"));
						item.setItemforeopacity(jsonObject.getIntValue("itemforeopacity"));
						item.setDelindex(0);
						item.setItemstyle(jsonObject.getString("itemstyle"));
						item.setItemcontext(jsonObject.getString("itemcontext"));
						item.setItemcontextjson(jsonObject.getString("itemcontextjson"));
						itemMapper.insert(item);
					}

					jObject.put("result", "success");
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
	public JSONObject deleteInfo2old(String infoSN) {
		JSONObject jObject = new JSONObject();
		try {
			advertisement adv = advertisementMapper.selectByPrimaryKey(infoSN);

			List<item> itemlist = itemMapper.selectByadid(infoSN);

			JSONArray jsonArray = new JSONArray();
			if (itemlist != null && !itemlist.isEmpty()) {
				for (item objitem : itemlist) {
					jsonArray.add(objitem);
				}
			}
			oldlogadvMapper.deleteByPrimaryKey(adv.getinfoSN());
			oldlogadv record = new oldlogadv();
			record.setInfosn(adv.getinfoSN());
			record.setAdvname(adv.getAdvname());
			record.setGroupid(adv.getGroupid());
			record.setLifeact(adv.getlifeAct());
			record.setLifedie(adv.getlifeDie());

			record.setAdvtype(adv.getAdvtype());
			record.setInfostate(adv.getinfoState());
			record.setCreater(adv.getcreater());
			record.setCreatedate(adv.getcreateDate());
			record.setAuditor(adv.getauditor() == null ? null : adv.getauditor().toString());

			record.setAuditdate(adv.getauditDate());
			record.setPublisher(adv.getpublisher());
			record.setPublishdate(adv.getpublishDate());
			record.setDeleter(adv.getdeleter() == null ? null : adv.getdeleter().toString());
			record.setDeletedate(adv.getdeleteDate());

			record.setBackgroundstyle(adv.getBackgroundstyle());
			record.setPlaymode(adv.getPlaymode());
			record.setPubid(adv.getPubid());
			record.setPlaytimelength(adv.getplayTimelength());
			record.setItemlist(jsonArray.toJSONString());

			oldlogadvMapper.insertSelective(record);

			advertisementMapper.deleteByPrimaryKey(infoSN);
			itemMapper.deleteByadid(infoSN);
			infocodeMapper.deleteByinfoSN(infoSN);

			jObject.put("result", "success");
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}

		return jObject;
	}

	@Override
	public JSONObject DeleteInfobyid(String infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			int result = 0;
			int infocodeCount = infocodeMapper.selectCountByinfoSN(infoSN);
			if (infocodeCount > 0) {
				advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoSN);
				result = DeletePlayListbyinfoSN(advertisement.getGroupid(), infoSN, adminid);
			}

			if (result == 0) {
				Object deleteDate = advertisementMapper.selectdeleteDateByPrimaryKey(infoSN);
				if (deleteDate != null) {
					advertisementMapper.updateDelindexByid(infoSN, 1);
				} else {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					advertisementMapper.updateDelindexdateByid(infoSN, 1, adminid, d1.format(now));
				}
				List<advertisement> advlist = new ArrayList<advertisement>();
				advlist.add(advertisementMapper.selectByPrimaryKey(infoSN));
				// itemMapper.deleteByadid(infoSN);
				infocodeMapper.deleteByinfoSN(infoSN);

				if (advlist.size() > 0) {
					NettyClient.TcpSocketSendPublish(null, advlist);
				}

				jObject.put("result", "success");
				jObject.put("infoID", infoSN);
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除播放列表失败");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;

	}

	@Override
	public JSONObject DeleteinfoCodebyid(String infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			int result = 0;
			int infocodeCount = infocodeMapper.selectCountByinfoSN(infoSN);

			if (infocodeCount > 0) {
				advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoSN);
				result = DeletePlayListbyinfoSN(advertisement.getGroupid(), infoSN, adminid);
			}

			if (result == 0) {
				infocodeMapper.deleteByinfoSN(infoSN);

				jObject.put("result", "success");
				jObject.put("infoID", infoSN);
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除播放列表失败");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	private int DeletePlayListbyinfoSN(int groupid, String infosn, int adminid) {
		try {
			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);
			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist playlist = playlists.get(i);
					JSONArray jarray = JSONArray.parseArray(playlist.getProgramlist());
					if (jarray != null && jarray.size() > 0) {
						for (int j = 0; j < jarray.size(); j++) {
							JSONObject jsonObject = jarray.getJSONObject(j);
							if (jsonObject.getString("infoid") == infosn) {
								jarray.remove(j);
								break;
							}
						}
					}
				}
			}

			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public JSONObject getbyteslistbyTemp(String infoid, JSONObject jsoninfo, JSONObject jsonarritem) {
		try {
			JSONObject jObject = new JSONObject();
			List<byte[]> SendByteList = getCode.GetCodeListbyid(jsoninfo, jsonarritem);
			if (SendByteList != null) {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < SendByteList.size(); i++) {
					byte[] bytes = SendByteList.get(i);
					StringBuilder buf = new StringBuilder(bytes.length * 2);
					for (byte b : bytes) { // 使用String的format方法进行转换
						buf.append(String.format("%02x", new Integer(b & 0xff)) + " ");
					}
					jsonArray.add(buf.toString().trim());
				}

				List<byte[]> SendplByteList = getCode.GetplaylistCodeListbyid(100);

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
					jObject.put("infocodelist", jsonArray);
					jObject.put("plcodelist", pjsonArray);
				}

			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "获取编码失败");
			}

			return jObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject getbyteslistbyid(String infoSN) {
		try {
			JSONObject jObject = new JSONObject();
			advertisement adv = advertisementMapper.selectByPrimaryKey(infoSN);

			List<byte[]> SendByteList = getCode.GetCodeListbyid(adv);

			if (SendByteList != null) {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < SendByteList.size(); i++) {
					byte[] bytes = SendByteList.get(i);
					StringBuilder buf = new StringBuilder(bytes.length * 2);
					for (byte b : bytes) { // 使用String的format方法进行转换
						buf.append(String.format("%02x", new Integer(b & 0xff)) + " ");
					}
					jsonArray.add(buf.toString().trim());
				}

				List<byte[]> SendplByteList = getCode.GetplaylistCodeListbyid(100);

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
					jObject.put("infocodelist", jsonArray);
					jObject.put("plcodelist", pjsonArray);
				}
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "获取编码失败");
			}

			return jObject;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JSONObject Publishinfobyid(String infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		advertisement adv = advertisementMapper.selectByPrimaryKey(infoSN);
		int plt = adv.getplayTimelength();
		if (plt < 2) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", "广告时长不能小于2秒");
			return jObject;
		}
		int pubid = getCode.GetpubidbyGroupid(adv.getGroupid());
		try {
			if (NettyClient.hdscontrol == 1) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "权限错误");
				return jObject;
			}
			if (pubid == 0) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "生成发布id错误");
			} else {
				int retIndex = getCode.GetCodeListbyid(adv, pubid);
				switch (retIndex) {
				case 0: {
					// adv.setPubid(pubid);
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					advertisement record = new advertisement();
					record.setinfoSN(infoSN);
					record.setPubid(pubid);
					record.setauditor(adminid);
					record.setauditDate(d1.format(now));
					record.setinfoState(3);
					advertisementMapper.updateByPrimaryKeySelective(record);
					// advertisementMapper.updatepubidByid(infoSN,pubid,adminid,d1.format(now));
					jObject.put("result", "success");
					jObject.put("pubid", pubid);
				}
					;
					break;
				case 1: {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "编码错误");
				}
					;
					break;
				case 2: {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "读取不到显示项");
				}
					;
					break;
				case 3: {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "单个显示项不能超过50个图文");
				}
					;
					break;
				case 8: {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "广告最大长度超过250K");
				}
					;
					break;
				default: {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "编码错误");
				}
					;
					break;
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
	public JSONObject RefuseInfobyid(String infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			Date now = new Date();
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			advertisement record = new advertisement();
			record.setinfoSN(infoSN);
			record.setauditor(adminid);
			record.setauditDate(d1.format(now));
			record.setinfoState(2);
			int row = advertisementMapper.updateByPrimaryKeySelective(record);
			if (row > 0) {
				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "广告id" + infoSN + "不存在!");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject AuditInfobyid(String infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
//			StringBuilder textBuilder = new StringBuilder();
//			List<item> itemlist = itemMapper.selectByadid(infoSN);
//			if (itemlist != null && itemlist.size() > 0) {
//				for (int i = 0; i < itemlist.size(); i++) {
//					item item = itemlist.get(i);
//
//					String contextJson = item.getItemcontextjson();
//					JSONArray jonArray = JSONArray.parseArray(contextJson);
//					for (int z = 0; z < jonArray.size(); z++) {
//						for (int q = 0; q < jonArray.getJSONArray(z).size(); q++) {
//							if (jonArray.getJSONArray(z).getJSONObject(q).getIntValue("itemType") == 0) {
//								textBuilder.append(jonArray.getJSONArray(z).getJSONObject(q).getString("value"));
//							}
//						}
//					}
//				}
//			}
//			ClassPathResource classPathResource = new ClassPathResource("static/不良词汇表.txt");
//
//			InputStream inputStream = classPathResource.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//			String errorString = "";
//			String s = "";
//			while ((s = br.readLine()) != null) {
//				int index = textBuilder.indexOf(s);
//				if (index != -1) {
//					errorString = s;
//					break;
//				}
//			}

//			Date now = new Date();
//			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			advertisement record = new advertisement();
			record.setinfoSN(infoSN);
			record.setinfoState(1);
			int row = advertisementMapper.updateByPrimaryKeySelective(record);
			if (row > 0) {
				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "广告id" + infoSN + "不存在!");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject getSensitive(String adminname) {
		JSONObject jObject = new JSONObject();
		try {
			user user = userMapper.selectByPrimaryName(adminname);
			int issuperuser = user.getIssuperuser();
			String projectid = user.getProjectid();
			JSONArray jArray = new JSONArray();
			List<String> prdelStrings = new ArrayList<String>();
			if (issuperuser == 0) {
				List<sensitive> sensitivelist = sensitiveMapper.selectByprojectid(projectid);
				for (int i = 0; i < sensitivelist.size(); i++) {
					if (sensitivelist.get(i).getDelindex() == 0) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("svid", sensitivelist.get(i).getId());
						jsonObject.put("projectid", sensitivelist.get(i).getProjectid());
						jsonObject.put("svstring", sensitivelist.get(i).getSensitivestring());
						jsonObject.put("svdelindex", sensitivelist.get(i).getDelindex());
						jArray.add(jsonObject);
					} else {
						prdelStrings.add(sensitivelist.get(i).getSensitivestring());
					}
				}
			}
			List<sensitive> sensitivelist = sensitiveMapper.selectByprojectid("super");
			for (int i = 0; i < sensitivelist.size(); i++) {
				if (sensitivelist.get(i).getDelindex() == 0
						&& !prdelStrings.contains(sensitivelist.get(i).getSensitivestring())) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("svid", sensitivelist.get(i).getId());
					jsonObject.put("projectid", sensitivelist.get(i).getProjectid());
					jsonObject.put("svstring", sensitivelist.get(i).getSensitivestring());
					jsonObject.put("svdelindex", sensitivelist.get(i).getDelindex());
					jArray.add(jsonObject);
				}
			}

			jObject.put("result", "success");
			jObject.put("sensitivelist", jArray.toJSONString());
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject addSensitive(String sensitiveString, String adminname) {
		JSONObject jObject = new JSONObject();
		try {
			user user = userMapper.selectByPrimaryName(adminname);
			int issuperuser = user.getIssuperuser();
			String projectid = user.getProjectid();

			int count = sensitiveMapper.selectcountByprojectidString("super", sensitiveString);
			if (count == 0 && issuperuser == 0) {
				count = sensitiveMapper.selectcountByprojectidString(projectid, sensitiveString);
			}
			if (count == 0) {
				sensitive record = new sensitive();
				record.setDelindex(0);
				if (issuperuser == 0) {
					record.setProjectid(projectid);
				} else {
					record.setProjectid("super");
				}
				record.setSensitivestring(sensitiveString);
				sensitiveMapper.insert(record);
				jObject.put("result", "success");
				jObject.put("svid", record.getId());
				jObject.put("projectid", record.getProjectid());
				jObject.put("svstring", record.getSensitivestring());
				jObject.put("svdelindex", record.getDelindex());
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "敏感词:" + sensitiveString + "已存在!");
			}

		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject deleteSensitive(String sensitiveIdlist, String adminname) {
		JSONObject jObject = new JSONObject();
		try {
			user user = userMapper.selectByPrimaryName(adminname);
			int issuperuser = user.getIssuperuser();
			String projectid = user.getProjectid();
			String[] svidArray = sensitiveIdlist.split(",");

			if (issuperuser == 0) {
				for (int i = 0; i < svidArray.length; i++) {
					int svid = Integer.parseInt(svidArray[i]);
					sensitive sv = sensitiveMapper.selectByPrimaryKey(svid);
					if (sv.getProjectid().equals("super")) {
						sensitive record = new sensitive();
						record.setDelindex(1);
						record.setProjectid(projectid);
						record.setSensitivestring(sv.getSensitivestring());
						sensitiveMapper.insert(record);
					} else {
						sensitiveMapper.deleteByPrimaryKey(svid);
					}
				}
			} else {
				for (int i = 0; i < svidArray.length; i++) {
					int svid = Integer.parseInt(svidArray[i]);
					sensitiveMapper.deleteByPrimaryKey(svid);
				}
			}
			jObject.put("result", "success");

		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public int Updatainfo(String infoSN, JSONObject jsoninfo) {
		try {
			if (advertisementMapper.selectCountByName2(jsoninfo.getString("advname"), jsoninfo.getIntValue("groupid"),
					infoSN) > 0) {
				return 2;
			} else {
				advertisement advertisement = new advertisement();
				advertisement.setinfoSN(infoSN);
				advertisement.setAdvname(jsoninfo.getString("advname"));
				advertisement.setlifeAct(jsoninfo.getString("lifeAct"));
				advertisement.setlifeDie(jsoninfo.getString("lifeDie"));
				advertisement.setAdvtype("0");
				advertisement.setBackgroundstyle(jsoninfo.getString("BackgroundStyle"));
				advertisement.setDelindex(0);
				advertisement.setGroupid(jsoninfo.getIntValue("groupid"));
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);//
				// advertisement.setplayTimelength(jsoninfo.getIntValue("playTimelength"));

				advertisementMapper.updateByPrimaryKeySelective(advertisement);

				return 0;
			}

		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public JSONObject CopyItem(String oldinfoid, String newinfoid) {
		JSONObject jObject = new JSONObject();
		try {
			List<item> itemlist = itemMapper.selectByadid(oldinfoid);
			if (itemlist != null && itemlist.size() > 0) {
				for (int i = 0; i < itemlist.size(); i++) {
					item item = itemlist.get(i);
					item.setInfoSN(newinfoid);
					itemMapper.insert(item);
				}
			}
			jObject.put("result", "success");
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject SaveItem(String infoSN, JSONObject jsoninfo) {
		JSONObject jObject = new JSONObject();
		try {
			itemMapper.deleteByadid(infoSN);
			double playTimelength = 0;
			for (String dataKey : jsoninfo.keySet()) {
				JSONArray jArrpage = jsoninfo.getJSONArray(dataKey);
				for (int i = 0; i < jArrpage.size(); i++) {
					JSONObject jitem = jArrpage.getJSONObject(i);
					item item = new item();
					item.setInfoSN(infoSN);
					item.setPageid(Integer.parseInt(dataKey));
					item.setItemid(jitem.getInteger("itemid"));
					item.setItemtype(jitem.getInteger("type"));
					item.setItemleft(jitem.getInteger("left"));
					item.setItemtop(jitem.getInteger("top"));
					item.setItemwidth(jitem.getInteger("width"));
					item.setItemheight(jitem.getInteger("height"));
					item.setItemfontno(jitem.getInteger("fontno"));
					item.setItembackcolor(jitem.getString("backcolor"));
					item.setItembackopacity(jitem.getInteger("backopacity"));
					item.setItemforecolor(jitem.getString("forecolor"));
					item.setItemforeopacity(jitem.getInteger("foreopacity"));
					item.setDelindex(0);
					item.setItemstyle(jitem.getString("itemstyle"));
					double playtime = jitem.getJSONObject("itemstyle").getDoubleValue("playtime");
					playTimelength += playtime;
					item.setItemcontext(jitem.getString("context"));
					item.setItemcontextjson(jitem.getString("contextJson"));
					itemMapper.insert(item);
				}
			}

			advertisementMapper.updateplaytimelengthByid(infoSN, (int) Math.ceil(playTimelength));

			jObject.put("result", "success");
			jObject.put("infoID", infoSN);
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public JSONObject GetItembyid(String infoSN) {
		JSONObject jObject = new JSONObject();
		try {
			List<item> itemlist = itemMapper.selectByadid(infoSN);

			JSONArray jsonArray = new JSONArray();
			if (itemlist != null && !itemlist.isEmpty()) {
				for (item objitem : itemlist) {
					jsonArray.add(objitem);
				}
			}

			jObject.put("result", "success");
			jObject.put("itemlist", jsonArray);
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		return jObject;
	}

	@Override
	public int SaveBasemap(String fileName, String projectid, Integer imgtype, String basemapclassify,
			String contentType, String basemapstyle, String base64Img) {
		try {
			basemap basemap = new basemap();
			basemap.setBasemapclassify(basemapclassify);
			basemap.setBasemapname(fileName);
			basemap.setprojectid(projectid);
			basemap.setimgtype(imgtype);
			basemap.setBasemaptype(contentType);
			basemap.setbasemapstyle(basemapstyle);
			basemap.setBasemapdata(base64Img);
			basemap.setdelindex(0);
			basemapMapper.insert(basemap);
			return basemap.getId();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public JSONArray getbasemaplist() {
		JSONArray itemJSONArray = new JSONArray();

		List<basemap> basemaps = basemapMapper.selectAll();
		if (basemaps != null && basemaps.size() > 0) {
			for (int j = 0; j < basemaps.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("basemapid", basemaps.get(j).getId());
				jitem.put("fileName", basemaps.get(j).getBasemapname());
				jitem.put("projectid", basemaps.get(j).getprojectid());
				jitem.put("imgtype", basemaps.get(j).getimgtype());
				jitem.put("classify", basemaps.get(j).getBasemapclassify());
				jitem.put("contentType", basemaps.get(j).getBasemaptype());
				jitem.put("basemapstyle", basemaps.get(j).getbasemapstyle());
				jitem.put("imgBase64String", basemaps.get(j).getBasemapdata());

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONArray getimgclassifybyprojectid(String projectid, int imgtype) {
		JSONArray itemJSONArray = new JSONArray();
		List<String> basemaps = basemapMapper.selectbasemapclassifybyproject(projectid, imgtype);
		if (basemaps != null && basemaps.size() > 0) {
			for (int j = 0; j < basemaps.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("classify", basemaps.get(j));

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONObject getimgbyprojectid(String projectid, int imgtype, String classify, int pageNumber, int pageSize) {
		JSONObject jObject = new JSONObject();
		JSONArray itemJSONArray = new JSONArray();

		List<basemap> basemaps = null;
		int startoffset = (pageNumber - 1) * pageSize;

		int itemCount = 0;
		if (classify == "") {
			basemaps = basemapMapper.selectbasemap2byproject(projectid, imgtype, startoffset, pageSize);
			itemCount = basemapMapper.selectbasemap2byprojectCount(projectid, imgtype);
			int pages = (int) Math.ceil(itemCount / (double) pageSize);
			if (pageNumber > pages && pages > 0) {
				pageNumber = pages;
				startoffset = (pageNumber - 1) * pageSize;
				basemaps = basemapMapper.selectbasemap2byproject(projectid, imgtype, startoffset, pageSize);
			}
		} else {
			basemaps = basemapMapper.selectbasemapbyproject(projectid, imgtype, classify, startoffset, pageSize);
			itemCount = basemapMapper.selectbasemapbyprojectCount(projectid, imgtype, classify);

			int pages = (int) Math.ceil(itemCount / (double) pageSize);
			if (pageNumber > pages && pages > 0) {
				pageNumber = pages;
				startoffset = (pageNumber - 1) * pageSize;
				basemaps = basemapMapper.selectbasemap2byproject(projectid, imgtype, startoffset, pageSize);
			}
		}

		if (basemaps != null && basemaps.size() > 0) {
			for (int j = 0; j < basemaps.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("basemapid", basemaps.get(j).getId());
				jitem.put("fileName", basemaps.get(j).getBasemapname());
				jitem.put("projectid", basemaps.get(j).getprojectid());
				jitem.put("imgtype", basemaps.get(j).getimgtype());
				jitem.put("classify", basemaps.get(j).getBasemapclassify());
				jitem.put("contentType", basemaps.get(j).getBasemaptype());
				jitem.put("basemapstyle", basemaps.get(j).getbasemapstyle());
				jitem.put("imgBase64String", basemaps.get(j).getBasemapdata());

				itemJSONArray.add(jitem);
			}
		}
		jObject.put("totalCount", itemCount);
		jObject.put("itemJSONArray", itemJSONArray);

		return jObject;
	}

	@Override
	public JSONObject getbasemapbyid(int basemapid) {
		JSONObject jitem = new JSONObject();

		basemap basemap = basemapMapper.selectByPrimaryKey(basemapid);

		jitem.put("basemapid", basemap.getId());
		jitem.put("fileName", basemap.getBasemapname());
		jitem.put("projectid", basemap.getprojectid());
		jitem.put("imgtype", basemap.getimgtype());
		jitem.put("classify", basemap.getBasemapclassify());
		jitem.put("contentType", basemap.getBasemaptype());
		jitem.put("basemapstyle", basemap.getbasemapstyle());
		jitem.put("imgBase64String", basemap.getBasemapdata());

		return jitem;
	}

	@Override
	public int deletebasemapbyid(int basemapid) {
		try {
			basemapMapper.deleteByPrimaryKey(basemapid);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public int updatebasemapclassify(String projectid, Integer imgtype, String oldbasemapclassify,
			String newbasemapclassify) {
		try {
			basemapMapper.updatebasemapclassify(projectid, imgtype, oldbasemapclassify, newbasemapclassify);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public int Savevideo(String fileName, Integer groupid, Integer videotype, String videoclassify, String contentType,
			String duration, String videostyle, String base64Img) {
		try {
			video video = new video();
			video.setVideoclassify(videoclassify);
			video.setVideoname(fileName);
			video.setGroupid(groupid);
			video.setVideotype(videotype);
			video.setFiletype(contentType);
			video.setduration(duration);
			video.setVideostyle(videostyle);
			video.setVideofirst(base64Img);
			video.setDelindex(0);
			videoMapper.insert(video);
			return video.getvideoSN();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public JSONArray getvideoclassifybyGrpid(int Grpid, int imgtype) {
		JSONArray itemJSONArray = new JSONArray();
		List<String> videoclassifys = videoMapper.selectvideoclassifybygroupid(Grpid, imgtype);
		if (videoclassifys != null && videoclassifys.size() > 0) {
			for (int j = 0; j < videoclassifys.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("classify", videoclassifys.get(j));

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public JSONArray getvideobyGrpid(int Grpid, int imgtype, String classify) {
		JSONArray itemJSONArray = new JSONArray();
		List<video> videos = null;
		if (classify == "") {
			videos = videoMapper.selectvideo2bygroupid(Grpid, imgtype);
		} else {
			videos = videoMapper.selectvideobygroupid(Grpid, imgtype, classify);
		}

		if (videos != null && videos.size() > 0) {
			for (int j = 0; j < videos.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("videoid", videos.get(j).getvideoSN());
				jitem.put("fileName", videos.get(j).getVideoname());
				jitem.put("groupid", videos.get(j).getGroupid());
				jitem.put("videotype", videos.get(j).getVideotype());
				jitem.put("classify", videos.get(j).getVideoclassify());
				jitem.put("contentType", videos.get(j).getFiletype());
				jitem.put("duration", videos.get(j).getduration());
				jitem.put("videostyle", videos.get(j).getVideostyle());
				jitem.put("imgBase64String", videos.get(j).getVideofirst());

				itemJSONArray.add(jitem);
			}
		}

		return itemJSONArray;
	}

	@Override
	public int updatevideoclassify(Integer groupid, Integer videotype, String oldvideoclassify,
			String newvideoclassify) {
		try {
			videoMapper.updatevideoclassify(groupid, videotype, oldvideoclassify, newvideoclassify);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public int deletevideobyid(int basemapid) {
		try {
			videoMapper.deleteByPrimaryKey(basemapid);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}
}
