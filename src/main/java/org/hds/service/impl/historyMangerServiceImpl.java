package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hds.logmapper.oldlogadvMapper;
import org.hds.logmapper.oldplaylistMapper;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.model.advertisement;
import org.hds.model.item;
import org.hds.model.oldlogadv;
import org.hds.model.oldplaylist;
import org.hds.model.playlist;
import org.hds.service.IhistoryMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@Service
public class historyMangerServiceImpl implements IhistoryMangerService {

	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	advertisementMapper advMapper;
	@Autowired
	oldlogadvMapper oldlogadvMapper;
	@Autowired
	oldplaylistMapper oldplaylistMapper;
	@Autowired
	itemMapper itemMapper;
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

					if (plString != null) {
						JSONArray jArray = JSONArray.parseArray(plString);
						for (int j = 0; j < jArray.size(); j++) {
							String infoid = jArray.getJSONObject(j).getString("infoid");
							advertisement adver = advMapper.selectByPrimaryKey(infoid);
							String act = "";
							String die = "";
							String infoname = "";
							if (adver == null) {
								oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infoid);
								act = oldadv.getLifeact();
								die = oldadv.getLifedie();
								infoname = oldadv.getAdvname();
							} else {
								act = adver.getlifeAct();
								die = adver.getlifeDie();
								infoname = adver.getAdvname();
							}

							if (act == null || act.equals("")) {
								act = "1999-09-09";
							}
							if (die == null || die.equals("")) {
								die = "2100-09-09";
							}
							act += " 0:00:00";
							die += " 23:59:59";
							if (!(d1.parse(act).compareTo(d1.parse(ddString)) > 0
									|| d1.parse(die).compareTo(d1.parse(pdString)) < 0)) {

								if (!infoidString.contains(infoname)) {
									infoidString.add(infoname);
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
			boolean isDetails = false;
			if (lifeAct.equals(lifeDie)) {
				isDetails = true;
			}
			Map<String, JSONObject> infosMap = new HashMap<String, JSONObject>();

			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int startoffset = (pageNum - 1) * pageSize;
			lifeAct += " 0:00:00";
			lifeDie += " 23:59:59";
			List<playlist> playlists = playlistMapper.selecthistorybyDate(lifeAct, lifeDie, groupid);

			List<oldplaylist> oldplaylists = oldplaylistMapper.selecthistorybyDate(lifeAct, lifeDie, groupid);
			int terminalCount = playlistMapper.selectCount(lifeAct, lifeDie, groupid);

			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist item = playlists.get(i);
					String pdString = item.getpublishDate();
					String ddString = item.getdeleteDate();
					if (ddString == null) {
						Date now = new Date();
//						ddString = new SimpleDateFormat("yyyy-MM-dd").format(now) + " 23:59:59";
						ddString = lifeDie;
					}

					List<String> infoidString = new ArrayList<String>();
					String MutiplString = item.getMutiProgramlist();
					if (MutiplString != null) {
						JSONArray jArray = JSONArray.parseArray(MutiplString);
						for (int j = 0; j < jArray.size(); j++) {
							JSONObject plObject = jArray.getJSONObject(j);
							String pllifeAct = plObject.getString("lifeAct") + " 0:00:00";
							String pllifeDie = plObject.getString("lifeDie") + " 23:59:59";
							JSONObject tObject = JSONObject.parseObject(item.getTimequantum());
							if (item.getScheduletype() == 2) {
								int ttl = plObject.getIntValue("totalTimeLength");
								tObject.put("listcycle", ttl);

							}

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
									String infoid = infoObject.getString("infoid");

									String act = "";
									String die = "";
									String infoname = "";
									if (infoObject.containsKey("infoname")) {
										infoname = infoObject.getString("infoname");
									}

									if (infoObject.containsKey("lifeAct")) {
										act = infoObject.getString("lifeAct");
									}

									if (infoObject.containsKey("lifeDie")) {
										die = infoObject.getString("lifeDie");
									}

									if (infoname.equals("")) {
										advertisement adver = advMapper.selectByPrimaryKey(infoid);
										if (adver == null) {
											oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infoid);
											act = oldadv.getLifeact();
											die = oldadv.getLifedie();
											infoname = oldadv.getAdvname();
										} else {
											act = adver.getlifeAct();
											die = adver.getlifeDie();
											infoname = adver.getAdvname();
										}
									}
									if (infosMap.containsKey(infoid)) {
										JSONObject jsonObject = infosMap.get(infoid);

										JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("offsetlist"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));
//										jsonArray.add(tObject);
//										jsonObject.put("offsetlist", jsonArray.toJSONString());										

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

										if (isDetails) {
											jsonArray.addAll(jpObject.getJSONArray("playtimes"));

											List<Integer> sorList = new ArrayList<Integer>();
											for (int k = 0; k < jsonArray.size(); k++) {
												sorList.add(jsonArray.getInteger(k));
											}
											Collections.sort(sorList);
											JSONArray array = JSONArray.parseArray(JSON.toJSONString(sorList));

											jsonObject.put("offsetlist", array);
										} else {
											jsonObject.put("offsetlist", null);
										}

										jsonObject.put("offsetlistCount",
												jsonObject.getIntValue("offsetlistCount")
														+ jpObject.getIntValue("playCount")
																* infoObject.getJSONArray("offsetlist").size());
									} else {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("infoName", infoname);
										jsonObject.put("LifeAct", act);
										jsonObject.put("LifeDie", die);
										jsonObject.put("timelength", infoObject.getIntValue("timelenght"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

//										JSONArray jsonArray = new JSONArray();
//										jsonArray.add(tObject);

//										jsonObject.put("offsetlist", jsonArray.toJSONString());
										if (isDetails) {
											jsonObject.put("offsetlist", jpObject.getJSONArray("playtimes"));
										} else {
											jsonObject.put("offsetlist", null);
										}
										jsonObject.put("offsetlistCount", jpObject.getIntValue("playCount")
												* infoObject.getJSONArray("offsetlist").size());

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
			for (String infoid : infosMap.keySet()) {
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

	@Override
	public JSONObject gethistorybyDate(String lifeAct, String lifeDie, int groupid) {
		JSONObject hjObject = new JSONObject();
		try {
			Map<String, JSONObject> infosMap = new HashMap<String, JSONObject>();

			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			List<playlist> playlists = playlistMapper.selecthistorybyDate(lifeAct, lifeDie, groupid);

			List<oldplaylist> oldplaylists = oldplaylistMapper.selecthistorybyDate(lifeAct, lifeDie, groupid);

			lifeAct += " 0:00:00";
			lifeDie += " 23:59:59";

			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					playlist item = playlists.get(i);
					String pdString = item.getpublishDate() == null || item.getpublishDate().equals("")
							? "1999-09-09 0:00:00"
							: item.getpublishDate();
					String ddString = item.getdeleteDate() == null || item.getdeleteDate().equals("")
							? "2100-09-09 23:59:59"
							: item.getdeleteDate();

					String playlistlifeAct = item.getPlaylistlifeact() == null || item.getPlaylistlifeact().equals("")
							? "1999-09-09 0:00:00"
							: item.getPlaylistlifeact() + " 0:00:00";
					String playlistlifeDie = item.getPlaylistlifedie() == null || item.getPlaylistlifedie().equals("")
							? "2100-09-09 23:59:59"
							: item.getPlaylistlifedie() + " 23:59:59";

					String startTime = pdString.compareTo(playlistlifeAct) > 0 ? pdString : playlistlifeAct;
					String endTime = ddString.compareTo(playlistlifeDie) < 0 ? ddString : playlistlifeDie;

					if (endTime == null || endTime.equals("2100-09-09 23:59:59")) {
						endTime = d1.format(new Date());
					}

					List<String> infoidString = new ArrayList<String>();
					String MutiplString = item.getMutiProgramlist();
					if (MutiplString != null) {
						JSONArray jArray = JSONArray.parseArray(MutiplString);
						for (int j = 0; j < jArray.size(); j++) {
							JSONObject plObject = jArray.getJSONObject(j);
							String pllifeAct = plObject.getString("lifeAct") + " 0:00:00";
							String pllifeDie = plObject.getString("lifeDie") + " 23:59:59";
							JSONObject tObject = JSONObject.parseObject(item.getTimequantum());
							if (item.getScheduletype() == 2) {
								int ttl = plObject.getIntValue("totalTimeLength");
								tObject.put("listcycle", ttl);
							}

							// 判断列表时间是否在查询时间范围内并且在列表有效期内
							if (!(d1.parse(pllifeAct).compareTo(d1.parse(lifeDie)) > 0
									|| d1.parse(pllifeDie).compareTo(d1.parse(lifeAct)) < 0)
									&& !(d1.parse(pllifeAct).compareTo(d1.parse(endTime)) > 0
											|| d1.parse(pllifeDie).compareTo(d1.parse(startTime)) < 0)) {
								// 如果列表开始时间小于列表发布时间,取列表发布时间
								if (d1.parse(pllifeAct).compareTo(d1.parse(startTime)) < 0) {
									pllifeAct = startTime;
								}
								// 如果列表结束时间大于列表删除时间,取列表删除时间
								if (d1.parse(pllifeDie).compareTo(d1.parse(endTime)) > 0) {
									pllifeDie = endTime;
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
									String infoid = infoObject.getString("infoid");

									String act = "";
									String die = "";
									String infoname = "";
									if (infoObject.containsKey("infoname")) {
										infoname = infoObject.getString("infoname");
									}

									if (infoObject.containsKey("lifeAct")) {
										act = infoObject.getString("lifeAct");
									}

									if (infoObject.containsKey("lifeDie")) {
										die = infoObject.getString("lifeDie");
									}

									if (infoname.equals("")) {
										advertisement adver = advMapper.selectByPrimaryKey(infoid);
										if (adver == null) {
											oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infoid);
											if (oldadv == null) {
												continue;
											} else {
												act = oldadv.getLifeact();
												die = oldadv.getLifedie();
												infoname = oldadv.getAdvname();
											}

										} else {
											act = adver.getlifeAct();
											die = adver.getlifeDie();
											infoname = adver.getAdvname();
										}
									}

									if (infosMap.containsKey(infoid)) {
										JSONObject jsonObject = infosMap.get(infoid);

										JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("offsetlist"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										JSONArray offsetlists = JSONArray
												.parseArray(jsonObject.getString("offsetlists"));
										offsetlists.add(tObject);
										jsonObject.put("offsetlists", offsetlists);

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

										jsonObject.put("offsetlistCount",
												jsonObject.getIntValue("offsetlistCount")
														+ jpObject.getIntValue("playCount")
																* infoObject.getJSONArray("offsetlist").size());
									} else {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("infoName", infoname);
										jsonObject.put("LifeAct", act);
										jsonObject.put("LifeDie", die);
										jsonObject.put("timelength", infoObject.getIntValue("timelenght"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										JSONArray offsetlists = new JSONArray();
										offsetlists.add(tObject);
										jsonObject.put("offsetlists", offsetlists);

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

										jsonObject.put("offsetlistCount", jpObject.getIntValue("playCount")
												* infoObject.getJSONArray("offsetlist").size());

										infosMap.put(infoid, jsonObject);
									}

								}
							}
						}
					}
				}
			}

			if (oldplaylists != null && oldplaylists.size() > 0) {
				for (int i = 0; i < oldplaylists.size(); i++) {
					oldplaylist item = oldplaylists.get(i);
					String pdString = item.getPublishdate() == null || item.getPublishdate().equals("")
							? "1999-09-09 0:00:00"
							: item.getPublishdate();
					String ddString = item.getDeletedate() == null || item.getDeletedate().equals("")
							? "2100-09-09 23:59:59"
							: item.getDeletedate();

					String playlistlifeAct = item.getPlaylistlifeact() == null || item.getPlaylistlifeact().equals("")
							? "1999-09-09 0:00:00"
							: item.getPlaylistlifeact() + " 0:00:00";
					String playlistlifeDie = item.getPlaylistlifedie() == null || item.getPlaylistlifedie().equals("")
							? "2100-09-09 23:59:59"
							: item.getPlaylistlifedie() + " 23:59:59";

					String startTime = pdString.compareTo(playlistlifeAct) > 0 ? pdString : playlistlifeAct;
					String endTime = ddString.compareTo(playlistlifeDie) < 0 ? ddString : playlistlifeDie;

					if (endTime == null || endTime.equals("2100-09-09 23:59:59")) {
						endTime = d1.format(new Date());
					}

					List<String> infoidString = new ArrayList<String>();
					String MutiplString = item.getMutiprogramlist();
					if (MutiplString != null) {
						JSONArray jArray = JSONArray.parseArray(MutiplString);
						for (int j = 0; j < jArray.size(); j++) {
							JSONObject plObject = jArray.getJSONObject(j);
							String pllifeAct = plObject.getString("lifeAct") + " 0:00:00";
							String pllifeDie = plObject.getString("lifeDie") + " 23:59:59";
							JSONObject tObject = JSONObject.parseObject(item.getTimequantum());
							if (item.getScheduletype() == 2) {
								int ttl = plObject.getIntValue("totalTimeLength");
								tObject.put("listcycle", ttl);
							}

							// 判断列表时间是否在查询时间范围内并且在列表有效期内
							if (!(d1.parse(pllifeAct).compareTo(d1.parse(lifeDie)) > 0
									|| d1.parse(pllifeDie).compareTo(d1.parse(lifeAct)) < 0)
									&& !(d1.parse(pllifeAct).compareTo(d1.parse(endTime)) > 0
											|| d1.parse(pllifeDie).compareTo(d1.parse(startTime)) < 0)) {
								// 如果列表开始时间小于列表发布时间,取列表发布时间
								if (d1.parse(pllifeAct).compareTo(d1.parse(startTime)) < 0) {
									pllifeAct = startTime;
								}
								// 如果列表结束时间大于列表删除时间,取列表删除时间
								if (d1.parse(pllifeDie).compareTo(d1.parse(endTime)) > 0) {
									pllifeDie = endTime;
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
									String infoid = infoObject.getString("infoid");

									String act = "";
									String die = "";
									String infoname = "";
									if (infoObject.containsKey("infoname")) {
										infoname = infoObject.getString("infoname");
									}

									if (infoObject.containsKey("lifeAct")) {
										act = infoObject.getString("lifeAct");
									}

									if (infoObject.containsKey("lifeDie")) {
										die = infoObject.getString("lifeDie");
									}

									if (infoname.equals("")) {
										advertisement adver = advMapper.selectByPrimaryKey(infoid);
										if (adver == null) {
											oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infoid);
											if (oldadv == null) {
												continue;
											} else {
												act = oldadv.getLifeact();
												die = oldadv.getLifedie();
												infoname = oldadv.getAdvname();
											}

										} else {
											act = adver.getlifeAct();
											die = adver.getlifeDie();
											infoname = adver.getAdvname();
										}
									}
									if (infosMap.containsKey(infoid)) {
										JSONObject jsonObject = infosMap.get(infoid);

										JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("offsetlist"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										JSONArray offsetlists = JSONArray
												.parseArray(jsonObject.getString("offsetlists"));
										offsetlists.add(tObject);
										jsonObject.put("offsetlists", offsetlists);

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

										jsonObject.put("offsetlistCount",
												jsonObject.getIntValue("offsetlistCount")
														+ jpObject.getIntValue("playCount")
																* infoObject.getJSONArray("offsetlist").size());
									} else {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("infoName", infoname);
										jsonObject.put("LifeAct", act);
										jsonObject.put("LifeDie", die);
										jsonObject.put("timelength", infoObject.getIntValue("timelenght"));

										tObject.put("pllifeAct", pllifeAct);
										tObject.put("pllifeDie", pllifeDie);
										tObject.put("offsetlist", infoObject.getJSONArray("offsetlist"));

										JSONArray offsetlists = new JSONArray();
										offsetlists.add(tObject);
										jsonObject.put("offsetlists", offsetlists);

										JSONObject jpObject = getPlayCount(tObject, pllifeAct, pllifeDie);

										jsonObject.put("offsetlistCount", jpObject.getIntValue("playCount")
												* infoObject.getJSONArray("offsetlist").size());

										infosMap.put(infoid, jsonObject);
									}

								}
							}
						}
					}
				}
			}

			JSONArray jsonArray = new JSONArray();
			for (String infoid : infosMap.keySet()) {
				JSONObject jsonObject = infosMap.get(infoid);

				JSONArray offsetlists = jsonObject.getJSONArray("offsetlists");
				JSONArray newoffsetlists = getNewoffsetlists(offsetlists);
				jsonObject.put("offsetlists", newoffsetlists);
				jsonObject.put("infoid", infoid);
				JSONArray itemArray = new JSONArray();

				List<item> ItemList = itemMapper.selectByadid(infoid);
				if (ItemList == null) {
					oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infoid);
					if (oldadv == null) {
						continue;
					} else {
						JSONArray jArray = JSONArray.parseArray(oldadv.getItemlist());
						for (int i = 0; i < jArray.size(); i++) {
							itemArray.add(jArray.getJSONObject(i).getString("itemcontext"));
						}
					}
				} else {
					for (int i = 0; i < ItemList.size(); i++) {
						itemArray.add(ItemList.get(i).getItemcontext());
					}
				}
				jsonObject.put("items", itemArray);
				jsonArray.add(jsonObject);
			}
			hjObject.put("total", jsonArray.size());
			hjObject.put("rows", jsonArray);
			return hjObject;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * 分析排期数据,把连续的相同的排期数据捏在一起
	 */
	private JSONArray getNewoffsetlists(JSONArray offsetlists) {
		try {
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			JSONArray jArray = new JSONArray();

			if (offsetlists != null && offsetlists.size() > 1) {

				offsetlists = jsonArraySort(offsetlists, "pllifeDie");

				for (int i = 0; i < offsetlists.size(); i++) {
					JSONObject item = offsetlists.getJSONObject(i);
					String lifeAct = item.getString("pllifeAct");
					String lifeDie = item.getString("pllifeDie");
					int listcycle = item.getIntValue("listcycle");
					String timelist = item.getString("timelist");
					String offsetlist = item.getString("offsetlist");

					int addindex = 0;

					for (int j = 0; j < jArray.size(); j++) {
						JSONObject jObject = jArray.getJSONObject(j);
						String jlifeAct = jObject.getString("pllifeAct");
						String jlifeDie = jObject.getString("pllifeDie");
						int jlistcycle = jObject.getIntValue("listcycle");
						String jtimelist = jObject.getString("timelist");
						String joffsetlist = jObject.getString("offsetlist");

						if (d1.parse(jlifeDie).getTime() + 1000 == d1.parse(lifeAct).getTime()
								&& listcycle == jlistcycle && timelist.equals(jtimelist)
								&& offsetlist.equals(joffsetlist)) {
							jObject.put("pllifeDie", lifeDie);
							jArray.set(j, jObject);
							addindex = 1;
							break;
						}
					}

					if (addindex == 0) {
						jArray.add(item);
					}

				}
				return jArray;
			} else {
				return offsetlists;
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 按照JSONArray中的对象的某个字段进行排序(采用fastJson)
	 * 
	 * @param jsonArrStr json数组字符串
	 * 
	 */
	public JSONArray jsonArraySort(JSONArray jsonArr, String colName) {
		JSONArray sortedJsonArray = new JSONArray();
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonValues.add(jsonArr.getJSONObject(i));
		}

		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			// You can change "Name" with "ID" if you want to sort by ID
			private final String KEY_NAME = colName;

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();
				try {
					// 这里是a、b需要处理的业务，需要根据你的规则进行修改。
					String aStr = a.getString(KEY_NAME);
					valA = aStr.replaceAll("-", "");
					String bStr = b.getString(KEY_NAME);
					valB = bStr.replaceAll("-", "");
				} catch (JSONException e) {
					// do something
				}
				return valA.compareTo(valB);
				// if you want to change the sort order, simply use the following:
				// return -valA.compareTo(valB);
			}
		});

		for (int i = 0; i < jsonArr.size(); i++) {
			sortedJsonArray.add(jsonValues.get(i));
		}
		return sortedJsonArray;
	}

	private JSONObject getPlayCount(JSONObject tObject, String pllifeAct, String pllifeDie) {
		JSONObject jObject = new JSONObject();
		int playCount = 0;
		try {
			List<Integer> timeArray = new ArrayList<Integer>();
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
							for (int i = 0; i < tObject.getJSONArray("offsetlist").size(); i++) {
								for (int j = act * 60; j < die * 60; j += listcycle) {
									// Collections.sort(timeArray);
									int val = j + tObject.getJSONArray("offsetlist").getIntValue(i);

									timeArray.add(val);
								}
							}
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
			jObject.put("playCount", playCount);
			Collections.sort(timeArray);
			JSONArray array = JSONArray.parseArray(JSON.toJSONString(timeArray));
			jObject.put("playtimes", array);
			return jObject;
		} catch (Exception e) {
			return jObject;
		}
	}

	@Override
	public JSONObject getplaylistinfobysn(String playlistsn) {
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
							String infosn = jarr.getJSONObject(z).getString("infoid");
							advertisement adver = advMapper.selectByPrimaryKey(infosn);
							String infoname = "";
							if (adver == null) {
								oldlogadv oldadv = oldlogadvMapper.selectByPrimaryinfosn(infosn);
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
