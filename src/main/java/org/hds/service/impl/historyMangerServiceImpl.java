package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hds.mapper.advertisementMapper;
import org.hds.mapper.oldadvMapper;
import org.hds.mapper.playlistMapper;
import org.hds.model.advertisement;
import org.hds.model.oldadv;
import org.hds.model.playlist;
import org.hds.service.IhistoryMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class historyMangerServiceImpl implements IhistoryMangerService {

	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	advertisementMapper advMapper;
	@Autowired
	oldadvMapper oldadvMapper;
	@Autowired
	getCode getCode;

	@Override
	public JSONObject gethistorybypageNum(int pageNum, int pageSize, String lifeAct, String lifeDie, int groupid,
			String sort, String sortOrder) {
		JSONObject hjObject = new JSONObject();
		try {
			/*
			 * List<playlist> playlistAll = playlistMapper.selectAll(); SimpleDateFormat d1
			 * = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for (int i = 0; i <
			 * playlistAll.size(); i++) { playlist item = playlistAll.get(i); String cd =
			 * item.getcreateDate(); String pd = item.getpublishDate(); String dd =
			 * item.getdeleteDate();
			 * 
			 * if (cd != null) { Date date = d1.parse(cd); String ccd = d1.format(date);
			 * item.setcreateDate(ccd); } if (pd != null) { Date date = d1.parse(pd); String
			 * ppd = d1.format(date); item.setpublishDate(ppd); } if (dd != null) { Date
			 * date = d1.parse(dd); String ddd = d1.format(date); item.setdeleteDate(ddd); }
			 * 
			 * playlistMapper.updateByPrimaryKeySelective(item); }
			 */
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int startoffset = (pageNum - 1) * pageSize;
			lifeAct += " 0:00:00";
			lifeDie += " 23:59:59";
			List<playlist> playlists = playlistMapper.selecthistorybypageNum(startoffset, pageSize, lifeAct, lifeDie,
					groupid, sort, sortOrder);

			int terminalCount = playlistMapper.selectCount(lifeAct, lifeDie, groupid);

			JSONArray jsonArray = new JSONArray();
			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist item = playlists.get(i);
					String pdString = item.getpublishDate();
					String ddString = item.getdeleteDate();
					if (ddString == null) {
						Date now = new Date();
						ddString = d1.format(now);
					}
					JSONObject tjObject = JSONObject.parseObject(item.getTimequantum());

					JSONObject jobject = new JSONObject();

					jobject.put("playlistsn", item.getplaylistSN());
					jobject.put("publishDate", pdString);
					jobject.put("deleteDate", ddString);
					jobject.put("playlistlevel", item.getPlaylistlevel());
					jobject.put("timequantum", tjObject.getString("timelist"));
					jobject.put("templateCycle", tjObject.getIntValue("listcycle"));

					List<String> infoidString = new ArrayList<String>();
					String plString = item.getProgramlist();
					if (item.getScheduletype() == 1)// 模板排期
					{
						if (plString != null) {
							JSONArray jArray = JSONArray.parseArray(plString);
							for (int j = 0; j < jArray.size(); j++) {
								int infoid = jArray.getJSONObject(j).getIntValue("infoid");
								advertisement adver = advMapper.selectByPrimaryKey(infoid);
								String act = "";
								String die = "";
								String infoname = "";
								if (adver == null) {
									oldadv oldadv = oldadvMapper.selectByPrimaryinfosn(infoid);
									act = oldadv.getLifeact();
									die = oldadv.getLifedie();
									infoname = oldadv.getAdvname();
								} else {
									act = adver.getlifeAct();
									die = adver.getlifeDie();
									infoname = adver.getAdvname();
								}

								if (act == null || act == "") {
									act = "1999-09-09";
								}
								if (die == null || die == "") {
									die = "2100-09-09";
								}
								act += " 0:00:00";
								die += " 0:00:00";
								if (!(d1.parse(act).compareTo(d1.parse(ddString)) > 0
										|| d1.parse(die).compareTo(d1.parse(pdString)) < 0)) {

									if (!infoidString.contains(infoname)) {
										infoidString.add(infoname);
									}
								}
							}
						}
					} else// 顺序排期
					{
						if (plString != null) {
							JSONArray jArray = JSONArray.parseArray(plString);
							for (int j = 0; j < jArray.size(); j++) {
								int infosn = jArray.getJSONObject(j).getIntValue("infosn");

								advertisement adver = advMapper.selectByPrimaryKey(infosn);

								String act = "";
								String die = "";
								String infoname = "";
								if (adver == null) {
									oldadv oldadv = oldadvMapper.selectByPrimaryinfosn(infosn);
									act = oldadv.getLifeact();
									die = oldadv.getLifedie();
									infoname = oldadv.getAdvname();
								} else {
									act = adver.getlifeAct();
									die = adver.getlifeDie();
									infoname = adver.getAdvname();
								}
								if (act == null || act == "") {
									act = "1999-09-09";
								}
								if (die == null || die == "") {
									die = "2100-09-09";
								}
								act += " 0:00:00";
								die += " 0:00:00";
								if (!(d1.parse(act).compareTo(d1.parse(ddString)) > 0
										|| d1.parse(die).compareTo(d1.parse(pdString)) < 0)) {
									if (!infoidString.contains(infoname)) {
										infoidString.add(infoname);
									}
								}
							}
						}
					}

//					JSONArray jArray = new JSONArray();
//					String mplString = item.getMutiProgramlist();
//					if (mplString != null) {
//						JSONArray mplJsonArray = JSONArray.parseArray(mplString);
//						for (int j = 0; j < mplJsonArray.size(); j++) {
//							String act = mplJsonArray.getJSONObject(j).getString("lifeAct") + " 0:00:00";
//							String die = mplJsonArray.getJSONObject(j).getString("lifeDie") + " 0:00:00";
//							if (!(d1.parse(act).compareTo(d1.parse(ddString)) > 0
//									|| d1.parse(die).compareTo(d1.parse(pdString)) < 0)) {
//								int totalTimeLength = mplJsonArray.getJSONObject(j).getIntValue("totalTimeLength");
//								if (totalTimeLength != 0) {
//									jobject.put("templateCycle", totalTimeLength);
//								}
//								JSONArray adlistJsonArray = mplJsonArray.getJSONObject(j).getJSONArray("advlist");
//								for (int z = 0; z < adlistJsonArray.size(); z++) {
//									int infoid = adlistJsonArray.getJSONObject(z).getIntValue("infoid");
//									int timelenght = adlistJsonArray.getJSONObject(z).getIntValue("timelenght");
//									JSONArray offsetlist = adlistJsonArray.getJSONObject(z).getJSONArray("offsetlist");
//
//									if (d1.parse(act).compareTo(d1.parse(pdString)) < 0) {
//										act = pdString;
//									}
//
//									if (d1.parse(die).compareTo(d1.parse(ddString)) > 0) {
//										die = ddString;
//									}
//									JSONObject jb = new JSONObject();
//									jb.put("lifeAct", act);
//									jb.put("lifeDie", die);
//									jb.put("infosn", infoid);
//									// jb.put("templateCycle", totalTimeLength);
//									jb.put("timelenght", timelenght);
//									jb.put("offsetval", offsetlist);
//									jArray.add(jb);
//								}
//							}
//						}
//					}
//					jobject.put("infosnlist", jArray.toJSONString());
					jobject.put("infosnlist", String.join(",", infoidString));

					jsonArray.add(jobject);

				}
			}
			hjObject.put("total", terminalCount);
			hjObject.put("rows", jsonArray);
			return hjObject;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject gethistorybyDate(int pageNum, int pageSize, String lifeAct, String lifeDie, int groupid,
			String sort, String sortOrder) {
		JSONObject hjObject = new JSONObject();
		try {
			Map<Integer, JSONObject> infosMap = new HashMap<Integer, JSONObject>();

			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int startoffset = (pageNum - 1) * pageSize;
			lifeAct += " 0:00:00";
			lifeDie += " 23:59:59";
			List<playlist> playlists = playlistMapper.selecthistorybyDate(lifeAct, lifeDie, groupid);

			int terminalCount = playlistMapper.selectCount(lifeAct, lifeDie, groupid);

			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist item = playlists.get(i);
					String pdString = item.getpublishDate();
					String ddString = item.getdeleteDate();
					if (ddString == null) {
						Date now = new Date();
						ddString = new SimpleDateFormat("yyyy-MM-dd").format(now) + " 23:59:59";
					}

					List<String> infoidString = new ArrayList<String>();
					String MutiplString = item.getMutiProgramlist();
					if (MutiplString != null) {
						JSONArray jArray = JSONArray.parseArray(MutiplString);
						for (int j = 0; j < jArray.size(); j++) {
							JSONObject plObject = jArray.getJSONObject(j);
							String pllifeAct = plObject.getString("lifeAct") + " 0:00:00";
							String pllifeDie = plObject.getString("lifeDie") + " 23:59:59";
							// 判断列表时间是否在查询时间范围内并且在列表有效期内
							if (!(d1.parse(pllifeAct).compareTo(d1.parse(lifeDie)) > 0
									|| d1.parse(pllifeDie).compareTo(d1.parse(lifeAct)) < 0)
									&& !(d1.parse(pllifeAct).compareTo(d1.parse(ddString)) > 0
											|| d1.parse(pllifeDie).compareTo(d1.parse(pdString)) < 0)) {
								// 如果列表开始时间小于列表发布时间,取列表发布时间
								if (d1.parse(pllifeAct).compareTo(d1.parse(pdString)) < 0) {
									pllifeAct = pdString;
								}
								// 如果列表结束时间大于列表删除时间,取列表删除时间
								if (d1.parse(pllifeDie).compareTo(d1.parse(ddString)) > 0) {
									pllifeDie = ddString;
								}

								// 如果列表开始时间小于查询开始时间,取查询开始时间
								if (d1.parse(pllifeAct).compareTo(d1.parse(lifeAct)) < 0) {
									pllifeAct = lifeAct;
								}
								// 如果列表结束时间大于查询结束时间,取查询结束时间
								if (d1.parse(pllifeDie).compareTo(d1.parse(lifeDie)) > 0) {
									pllifeDie = lifeDie;
								}

								JSONArray plArray = plObject.getJSONArray("advlist");

								for (int z = 0; z < plArray.size(); z++) {
									JSONObject infoObject = plArray.getJSONObject(z);
									int infoid = infoObject.getIntValue("infoid");

									advertisement adver = advMapper.selectByPrimaryKey(infoid);

									String act = "";
									String die = "";
									String infoname = "";
									if (adver == null) {
										oldadv oldadv = oldadvMapper.selectByPrimaryinfosn(infoid);
										act = oldadv.getLifeact();
										die = oldadv.getLifedie();
										infoname = oldadv.getAdvname();
									} else {
										act = adver.getlifeAct();
										die = adver.getlifeDie();
										infoname = adver.getAdvname();
									}

									if (infosMap.containsKey(infoid)) {
										JSONObject jsonObject = infosMap.get(infoid);

										JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("offsetlist"));

										JSONObject tObject = JSONObject.parseObject(item.getTimequantum());
										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));
										jsonArray.add(tObject);
										jsonObject.put("offsetlist", jsonArray.toJSONString());

										int playCount = getPlayCount(tObject, pllifeAct, pllifeDie);

										jsonObject.put("offsetlistCount", jsonObject.getIntValue("offsetlistCount")
												+ playCount * infoObject.getJSONArray("offsetlist").size());
									} else {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("infoName", infoname);
										jsonObject.put("LifeAct", act);
										jsonObject.put("LifeDie", die);
										jsonObject.put("timelength", infoObject.getIntValue("timelenght"));

										JSONObject tObject = JSONObject.parseObject(item.getTimequantum());
										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										int playCount = getPlayCount(tObject, pllifeAct, pllifeDie);

										JSONArray jsonArray = new JSONArray();
										jsonArray.add(tObject);

										jsonObject.put("offsetlist", jsonArray.toJSONString());
										jsonObject.put("offsetlistCount",
												playCount * infoObject.getJSONArray("offsetlist").size());

										infosMap.put(infoid, jsonObject);
									}

								}
							}
						}
					}
//					jobject.put("infosnlist", String.join(",", infoidString));

//					jsonArray.add(jobject);

				}
			}

			JSONArray jsonArray = new JSONArray();
			for (int infoid : infosMap.keySet()) {
				JSONObject jsonObject = infosMap.get(infoid);
				jsonObject.put("infoid", infoid);
				jsonArray.add(jsonObject);
			}
			hjObject.put("total", jsonArray.size());
			hjObject.put("rows", jsonArray);
			return hjObject;
		} catch (Exception e) {
			return null;
		}
	}

	private int getPlayCount(JSONObject tObject, String pllifeAct, String pllifeDie) {
		int playCount = 0;
		try {
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long diff = d1.parse(pllifeDie).getTime() - d1.parse(pllifeAct).getTime();// 这样得到的差值是毫秒级别
			int days = (int) Math.ceil((double) diff / (1000 * 60 * 60 * 24));

			int listcycle = tObject.getIntValue("listcycle");
			JSONArray timelist = tObject.getJSONArray("timelist");

			for (int k = 0; k < timelist.size(); k++) {
				int tact = timelist.getJSONObject(k).getIntValue("lifeAct");
				int tdie = timelist.getJSONObject(k).getIntValue("lifeDie");
				if (tdie == 0) {
					tdie = 24 * 60;
				}

				int plActmintue = Integer
						.parseInt(pllifeAct.substring(pllifeAct.indexOf(" ") + 1, pllifeAct.indexOf(":"))) * 60
						+ Integer.parseInt(pllifeAct.substring(pllifeAct.indexOf(":") + 1, pllifeAct.lastIndexOf(":")));
				int plDiemintue = Integer
						.parseInt(pllifeDie.substring(pllifeDie.indexOf(" ") + 1, pllifeDie.indexOf(":"))) * 60
						+ Integer.parseInt(pllifeDie.substring(pllifeDie.indexOf(":") + 1, pllifeDie.lastIndexOf(":")))
						+ 1;
				if (days == 1) {
					if (!(tact > plDiemintue || tdie < plActmintue)) {
						int act = plActmintue > tact ? plActmintue : tact;
						int die = plDiemintue < tdie ? plDiemintue : tdie;
						int tl = die - act;
						if (tl > 0) {
							playCount = tl * 60 / listcycle;
						} else {
							playCount = 0;
						}
					} else {
						playCount = 0;
					}
				} else {
					if (tdie > plActmintue) {
						int act = plActmintue > tact ? plActmintue : tact;
						playCount += (tdie - act) * 60 / listcycle;
					}
					if (tact < plDiemintue) {
						int die = plDiemintue < tdie ? plDiemintue : tdie;
						playCount += (die - tact) * 60 / listcycle;
					}

					playCount += (tdie - tact) * 60 / listcycle * (days - 2);

				}
			}
			return playCount;
		} catch (Exception e) {
			return playCount;
		}
	}

	@Override
	public JSONObject getplaylistinfobysn(int playlistsn) {
		JSONObject jObject = new JSONObject();
		try {
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			playlist playlist = playlistMapper.selectByPrimaryKey(playlistsn);
			String pdString = playlist.getpublishDate();
			String ddString = playlist.getdeleteDate();
			if (ddString == null) {
				Date now = new Date();
				ddString = d1.format(now);
			}

			JSONArray jsonArray = new JSONArray();

			String mplString = playlist.getMutiProgramlist();
			if (mplString == null) {
				if (playlist.getScheduletype() == 2 && playlist.getPlaylistlevel() != 0) {
					// 顺序排期
					JSONArray jArrayLoop = JSONArray.parseArray(playlist.getProgramlist());
					JSONArray jArrayinfo = getCode.GetJsonArrayinfolist(jArrayLoop, false);
					mplString = jArrayinfo.toJSONString();
				} else {
					// 模板排期
					JSONArray jArrayLoop = JSONArray.parseArray(playlist.getProgramlist());
					JSONArray jArrayinfo = getCode.GetJsonArrayinfolist2(jArrayLoop, false);
					mplString = jArrayinfo.toJSONString();
				}
			}

			if (mplString != null) {
				JSONArray mplJsonArray = JSONArray.parseArray(mplString);
				for (int j = 0; j < mplJsonArray.size(); j++) {
					String act = mplJsonArray.getJSONObject(j).getString("lifeAct") + " 0:00:00";
					String die = mplJsonArray.getJSONObject(j).getString("lifeDie") + " 0:00:00";
					if (!(d1.parse(act).compareTo(d1.parse(ddString)) > 0
							|| d1.parse(die).compareTo(d1.parse(pdString)) < 0)) {

						if (d1.parse(act).compareTo(d1.parse(pdString)) < 0) {
							act = pdString;
						}

						if (d1.parse(die).compareTo(d1.parse(ddString)) > 0) {
							die = ddString;
						}

						JSONObject jb = mplJsonArray.getJSONObject(j);
						jb.put("lifeAct", act);
						jb.put("lifeDie", die);
						JSONArray jarr = jb.getJSONArray("advlist");
						for (int z = 0; z < jarr.size(); z++) {
							int infosn = jarr.getJSONObject(z).getIntValue("infoid");
							advertisement adver = advMapper.selectByPrimaryKey(infosn);
							String infoname = "";
							if (adver == null) {
								oldadv oldadv = oldadvMapper.selectByPrimaryinfosn(infosn);
								infoname = oldadv.getAdvname();
							} else {
								infoname = adver.getAdvname();
							}
							jarr.getJSONObject(z).put("infoname", infoname);
						}
						jsonArray.add(jb);
					}
				}
			}
			jObject.put("result", "success");
			jObject.put("itemlist", jsonArray);
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}
}
