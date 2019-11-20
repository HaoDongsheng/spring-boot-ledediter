package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import org.hds.model.playlist;
import org.hds.model.playlistcode;
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
					if (groupids == null || groupids == "" || adminInfoJsonObject.getInteger("adminlevel") < 2) {
						String grpName = tg.getGroupname();
						int screenwidth = tg.getscreenwidth();
						int screenheight = tg.getscreenheight();
						int maxPackLength = tg.getMaxPackLength();
						JSONObject jgrp = new JSONObject();
						jgrp.put("grpid", grpid);
						jgrp.put("grpname", grpName);
						jgrp.put("maxPackLength", maxPackLength);
						jgrp.put("projectid", tg.getProjectid());
						jgrp.put("screenwidth", screenwidth);
						jgrp.put("screenheight", screenheight);
						jArray.add(jgrp);
					} else if (Arrays.asList(groupids.split(",")).contains(Integer.toString(grpid))) {
						String grpName = tg.getGroupname();
						int screenwidth = tg.getscreenwidth();
						int screenheight = tg.getscreenheight();
						int maxPackLength = tg.getMaxPackLength();
						JSONObject jgrp = new JSONObject();
						jgrp.put("grpid", grpid);
						jgrp.put("grpname", grpName);
						jgrp.put("maxPackLength", maxPackLength);
						jgrp.put("projectid", tg.getProjectid());
						jgrp.put("screenwidth", screenwidth);
						jgrp.put("screenheight", screenheight);
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
					JSONObject jgrp = new JSONObject();
					jgrp.put("grpid", grpid);
					jgrp.put("grpname", grpName);
					jgrp.put("maxPackLength", maxPackLength);
					jgrp.put("projectid", tgrpsList.get(i).getProjectid());
					jgrp.put("screenwidth", screenwidth);
					jgrp.put("screenheight", screenheight);
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
				int id = advertisements.get(i).getinfoSN();
				String Advname = advertisements.get(i).getAdvname();
				String lifeAct = advertisements.get(i).getlifeAct();
				String lifeDie = advertisements.get(i).getlifeDie();
				int playTimelength = advertisements.get(i).getplayTimelength();
				int Pubidid = advertisements.get(i).getPubid();
				String publishDate = advertisements.get(i).getpublishDate();

				JSONObject jadv = new JSONObject();
				jadv.put("id", id);
				jadv.put("Advname", Advname);
				jadv.put("lifeAct", lifeAct);
				jadv.put("lifeDie", lifeDie);
				jadv.put("playTimelength", playTimelength);
				jadv.put("Pubidid", Pubidid);
				jadv.put("publishDate", publishDate);

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
			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);

			for (int i = 0; i < playlists.size(); i++) {
				int id = playlists.get(i).getplaylistSN();
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
			String programlist, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			Object objid = playlistMapper.selectidByName(listname, groupid);
			if (objid == null) {
				playlist playlist = new playlist();
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact("");
				playlist.setPlaylistlifedie("");
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
					int returnid = playlist.getplaylistSN();

					jObject.put("result", "success");
					jObject.put("infoListID", returnid);
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
				int oldinfoid = oldplArray.getJSONObject(j).getIntValue("infoid");
				// 判断删除的广告在同组其他播放列表中是否存在
				boolean isExist = false;
				List<Object> pList = playlistMapper.selectprogramlistbygroupid(groupid);
				for (int i = 0; i < pList.size(); i++) {
					JSONArray plArray = JSONArray.parseArray(pList.get(i).toString());
					for (int p = 0; p < plArray.size(); p++) {
						int infoid = plArray.getJSONObject(p).getIntValue("infoid");
						if (oldinfoid == infoid) {
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

	private int isInfoFull(int listid, String listname, int groupid, int listtype, String quantums, int ScheduleType,
			String programlist) {
		int maxInfoCount = 200, maxPlaylistCount = 30;
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
					if (lifeDie.equals("") || lifeDie.equals(null)) {
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
							plcode.setPlaylistsn(0);
							plcode.setPubid(0);
							plcode.setLifeAct(jObject.getString("lifeAct"));
							plcode.setLifeDie(jObject.getString("lifeDie"));
							plcode.setplaylistCodeSN(0);
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
					plcode.setPlaylistsn(0);
					plcode.setPubid(0);
					plcode.setLifeAct("1999-09-09");
					plcode.setLifeDie("2100-09-09");
					plcode.setplaylistCodeSN(0);
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

					if (plcode.getPlaylistsn() == listid)// 要删除的列表不参与计算
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
					/*
					 * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //
					 * 加上时间 // 必须捕获异常
					 * 
					 * try { java.util.Date date = simpleDateFormat.parse(lifeDie); Calendar
					 * calendar = new GregorianCalendar(); calendar.setTime(date);
					 * calendar.add(Calendar.DATE, 1); date = calendar.getTime();
					 * 
					 * lifeDie = simpleDateFormat.format(date); } catch (ParseException px) { }
					 */

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
	public JSONObject UpdateinfoList(int listid, String listname, int groupid, int listtype, String quantums,
			int ScheduleType, String programlist, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			JSONArray jArrayLoop = null;
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			// 获取要发布的广告id集合
			if (programlist != null && programlist != "") {
				jArrayLoop = JSONArray.parseArray(programlist);
				for (int i = jArrayLoop.size() - 1; i >= 0; i--) {
					String lifeDie = jArrayLoop.getJSONObject(i).getString("lifeDie");
					if (!lifeDie.equals("") && !lifeDie.equals(null) && lifeDie.compareTo(dt.format(new Date())) < 0) {
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
				// DateFormat d1 = DateFormat.getDateTimeInstance();

				int pldeleCount = 0;

				// 判断删除排期中的广告是否需要删除
				JSONArray oldplArray = JSONArray.parseArray(oldpl.getProgramlist());
				deletePublishInfo(oldplArray, oldpl.getGroupid(), adminid);

				playlist playlist = new playlist();
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact("");
				playlist.setPlaylistlifedie("");
				playlist.setcreater(adminid);
				playlist.setcreateDate(d1.format(now));
				playlist.setPlaylistname(listname);
				playlist.setProgramlist(programlist);
				playlist.setPubid("0");
				playlist.setScheduletype(ScheduleType);
				playlist.setTimequantum(quantums);

				int rowCount = playlistMapper.insert(playlist);
				if (rowCount > 0) {
					pldeleCount = playlistMapper.updatedelindexByPrimaryKey(listid, 1, adminid, d1.format(now));
					int returnid = playlist.getplaylistSN();
					jObject.put("result", "success");
					jObject.put("returnid", returnid);
					jObject.put("deleMessage", "发布时删除旧播放列表" + pldeleCount + "个,编码" + pcdeleCount + "个,就列表SN" + listid);

				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "列表名称:" + listname + "写入数据库失败!");
				}
			} else {
				// 未发布广告直接保存
				playlist playlist = new playlist();
				playlist.setDelindex(0);
				playlist.setGroupid(groupid);
				playlist.setId(listid);
				playlist.setPlaylistlevel(listtype);
				playlist.setPlaylistlifeact("");
				playlist.setPlaylistlifedie("");
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
			// playlistMapper.deleteByPrimaryKey(listid);
			// playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject UpdatePlaylistName(int playlistid, String listname) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = new playlist();
			playlist.setId(playlistid);
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
	public JSONObject Copyinfobyid(int infosn, int groupid, int adminid) {
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
					int newinfoSN = advertisement.getinfoSN();
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
	public JSONObject Deleteinfobyid(int infosn, int groupid, int infopubid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			List<playlistcode> plcodes = playlistcodeMapper.selectByGroupid(groupid);
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
	public JSONObject GetInfocodebyid(int infosn) {
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
	public JSONObject DeleteinfoList(int listid, int adminid) {
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

			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject PublishinfoList(int listid, int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			playlist playlist = playlistMapper.selectByPrimaryKey(listid);
			String programlist = playlist.getProgramlist();
			if (programlist == null || programlist == "") {
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
							int infoid = plArray.getJSONObject(i).getIntValue("infoid");
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
					// 写入项目表，发布广告改动时间数据
					projectMapper.updateAdvUpdateTimeByPrimaryKey(
							groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
					jObject.put("result", "success");
					jObject.put("pubid", pidStrings);
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
							int infoid = plArray.getJSONObject(i).getIntValue("infoid");
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
					// 写入项目表，发布广告改动时间数据
					projectMapper.updateAdvUpdateTimeByPrimaryKey(
							groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(), d1.format(now));
					jObject.put("result", "success");
					jObject.put("pubid", pidStrings);
				} else {
					playlistMapper.deleteByPrimaryKey(listid);
					playlistcodeMapper.deleteByplaylistSN(listid);// 发布失败删除播放列表编码数据
					jObject.put("result", "fail");
					jObject.put("resultMessage", "播放列表编码错误");
				}

				/*
				 * int plpid = getCode.GetplpubidbyGroupid(playlist.getGroupid());//
				 * 每次发布重新生成发布id
				 * 
				 * Date now = new Date(); DateFormat d1 = DateFormat.getDateTimeInstance();
				 * 
				 * // 写入编码 表 int retplaylistIndex = getCode.GetplaylistCodeListbyid(playlist,
				 * plpid, playlist.getGroupid()); // 更新广告发布日期 if (retplaylistIndex == 0) { if
				 * (programlist != null && programlist != "") { JSONArray plArray =
				 * JSONArray.parseArray(programlist); for (int i = 0; i < plArray.size(); i++) {
				 * int infoid = plArray.getJSONObject(i).getIntValue("infoid"); Object
				 * publishDate = advertisementMapper.selectpublishDateByPrimaryKey(infoid); if
				 * (publishDate == null) { advertisementMapper.updatepublishDateByid(infoid,
				 * adminid, d1.format(now)); } } } // 修改pubid
				 * playlistMapper.updatepubidByPrimaryKey(listid, Integer.toString(plpid),
				 * adminid, d1.format(now)); // 写入项目表，发布广告改动时间数据
				 * projectMapper.updateAdvUpdateTimeByPrimaryKey(
				 * groupMapper.selectByPrimaryKey(playlist.getGroupid()).getProjectid(),
				 * d1.format(now)); jObject.put("result", "success"); jObject.put("pubid",
				 * Integer.toString(plpid)); } else { jObject.put("result", "fail");
				 * jObject.put("resultMessage", "播放列表编码错误"); }
				 */
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
	public JSONObject getbyteslistbyTemp(int listtype, String quantums, int ScheduleType, String programlist) {
		JSONObject jObject = new JSONObject();
		try {
			if (programlist == null || programlist == "") {
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
			List<Integer> conInfoidList = new ArrayList<Integer>();
			for (int i = 0; i < jArrayLoop.size(); i++) {
				int infoid = jArrayLoop.getJSONObject(i).getIntValue("infoid");
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
}
