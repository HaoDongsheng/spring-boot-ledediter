package org.hds.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hds.NettyClient;
import org.hds.Graphics.DrawTextGraphics;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.basemapMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.videoMapper;
import org.hds.model.advertisement;
import org.hds.model.basemap;
import org.hds.model.group;
import org.hds.model.item;
import org.hds.model.playlist;
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
	public JSONObject getadvListDelbyGrpid(int Grpid, int pageNum, int pageSize) {

		JSONObject jObject = new JSONObject();
		int delCount = advertisementMapper.selectDelCountBygroupid(Grpid);
		int startoffset = (pageNum - 1) * pageSize;

		JSONArray itemJSONArray = new JSONArray();
		List<advertisement> advlist = advertisementMapper.selectDelBygroupid(Grpid, startoffset, pageSize);
		if (advlist != null && advlist.size() > 0) {
			for (int j = 0; j < advlist.size(); j++) {
				JSONObject jitem = new JSONObject();
				jitem.put("infosn", advlist.get(j).getinfoSN());
				jitem.put("infoname", advlist.get(j).getAdvname());
				jitem.put("lifeAct", advlist.get(j).getlifeAct());
				jitem.put("lifeDie", advlist.get(j).getlifeDie());
				jitem.put("timelenght", advlist.get(j).getplayTimelength());

				itemJSONArray.add(jitem);
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject Creatinfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			if (advertisementMapper.selectCountByName(infoName, groupid) <= 0) {
				advertisement advertisement = new advertisement();
				advertisement.setAdvname(infoName);
				advertisement.setlifeAct(lifeAct);
				advertisement.setlifeDie(lifeDie);
				advertisement.setAdvtype("0");
				advertisement.setinfoState(0);
				advertisement.setcreater(adminid);
				Date now = new Date();
				DateFormat d1 = DateFormat.getDateTimeInstance();
				advertisement.setcreateDate(d1.format(now));
				advertisement.setBackgroundstyle(BackgroundStyle);
				advertisement.setDelindex(0);
				advertisement.setGroupid(groupid);
				advertisement.setPlaymode("0");
				advertisement.setPubid(0);
				advertisement.setplayTimelength(10);// 默认暂时写10秒
				int rowCount = advertisementMapper.insert(advertisement);
				if (rowCount > 0) {
					int infoSN = advertisement.getinfoSN();
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject CopyInfo(String infoName, int groupid, String lifeAct, String lifeDie, String BackgroundStyle,
			String itemlist) {
		JSONObject jObject = new JSONObject();
		try {
			int i = 1;
			String newInfoNameString = "";
			while (true) {
				if (advertisementMapper.selectCountByName(infoName + i, groupid) <= 0) {
					newInfoNameString = infoName + i;
					break;
				}
				i += 1;
			}

			if (newInfoNameString != "") {
				advertisement advertisement = new advertisement();
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

					int infoSN = advertisement.getinfoSN();
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject DeleteInfobyid(int infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			int result = 0;
			int infocodeCount = infocodeMapper.selectCountByinfoSN(infoSN);
			if (infocodeCount > 0) {
				advertisement advertisement = advertisementMapper.selectByPrimaryKey(infoSN);
				;
				result = DeletePlayListbyinfoSN(advertisement.getGroupid(), infoSN, adminid);
			}

			if (result == 0) {
				Object deleteDate = advertisementMapper.selectdeleteDateByPrimaryKey(infoSN);
				if (deleteDate != null) {
					advertisementMapper.updateDelindexByid(infoSN, 1);
				} else {
					Date now = new Date();
					DateFormat d1 = DateFormat.getDateTimeInstance();

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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject DeleteinfoCodebyid(int infoSN, int adminid) {
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
		} finally {
			return jObject;
		}
	}

	private int DeletePlayListbyinfoSN(int groupid, int infosn, int adminid) {
		try {
			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);
			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist playlist = playlists.get(i);
					JSONArray jarray = JSONArray.parseArray(playlist.getProgramlist());
					if (jarray != null && jarray.size() > 0) {
						for (int j = 0; j < jarray.size(); j++) {
							JSONObject jsonObject = jarray.getJSONObject(j);
							if (jsonObject.getIntValue("infoid") == infosn) {
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
	public JSONObject getbyteslistbyTemp(int infoid, JSONObject jsoninfo, JSONObject jsonarritem) {
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
	public JSONObject getbyteslistbyid(int infoSN) {
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
	public JSONObject Publishinfobyid(int infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		advertisement adv = advertisementMapper.selectByPrimaryKey(infoSN);
		int pubid = getCode.GetpubidbyGroupid(adv.getGroupid());
		try {
			if (pubid == 0) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "生成发布id错误");
			} else {
				int retIndex = getCode.GetCodeListbyid(adv, pubid);
				switch (retIndex) {
				case 0: {
					// adv.setPubid(pubid);
					Date now = new Date();
					DateFormat d1 = DateFormat.getDateTimeInstance();
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
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject RefuseInfobyid(int infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject AuditInfobyid(int infoSN, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
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
		} finally {
			return jObject;
		}
	}

	@Override
	public int Updatainfo(int infoSN, JSONObject jsoninfo) {
		try {
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
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	public JSONObject CopyItem(int oldinfoid, int newinfoid) {
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject SaveItem(int infoSN, JSONObject jsoninfo) {
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
		} finally {
			return jObject;
		}
	}

	@Override
	public JSONObject GetItembyid(int infoSN) {
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
		} finally {
			return jObject;
		}
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
	public JSONArray getimgbyprojectid(String projectid, int imgtype, String classify) {
		JSONArray itemJSONArray = new JSONArray();

		List<basemap> basemaps = null;

		if (classify == "") {
			basemaps = basemapMapper.selectbasemap2byproject(projectid, imgtype);
		} else {
			basemaps = basemapMapper.selectbasemapbyproject(projectid, imgtype, classify);
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

		return itemJSONArray;
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
