package org.hds.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.hds.Encryption;
import org.hds.GJ_coding.GJ_Set1cls;
import org.hds.GJ_coding.GJ_Set2cls;
import org.hds.GJ_coding.GJ_Set3cls;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.basemapMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.oldadvMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.sensitiveMapper;
import org.hds.mapper.statisticMapper;
import org.hds.mapper.terminalMapper;
import org.hds.mapper.userMapper;
import org.hds.model.advertisement;
import org.hds.model.group;
import org.hds.model.playlist;
import org.hds.model.project;
import org.hds.model.sensitive;
import org.hds.model.terminal;
import org.hds.model.user;
import org.hds.service.IprojectMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class projectMangerServiceImpl implements IprojectMangerService {

	@Autowired
	projectMapper projectMapper;
	@Autowired
	groupMapper groupMapper;
	@Autowired
	userMapper userMapper;
	@Autowired
	terminalMapper terminalMapper;
	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	playlistcodeMapper playlistcodeMapper;
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	infocodeMapper infocodeMapper;
	@Autowired
	basemapMapper basemapMapper;
	@Autowired
	statisticMapper statisticMapper;
	@Autowired
	oldadvMapper oldadvMapper;
	@Autowired
	itemMapper itemMapper;
	@Autowired
	sensitiveMapper sensitiveMapper;

	@Override
	public JSONArray getProjectlist() {
		try {
			JSONArray JsonArray = new JSONArray();
			List<project> proList = projectMapper.selectAll();
			if (proList != null && proList.size() > 0) {
				for (int i = 0; i < proList.size(); i++) {
					project project = proList.get(i);
					String projectid = project.getProjectid();
					String projectname = project.getProjectname();
					String projectpwd = project.getCheckCode();
					int autoGrp = 0;
					if (project.getAutoGroupTo() != null) {
						autoGrp = project.getAutoGroupTo();
					}
					int startLevelControl = project.getStartlevelControl();
					int defaultStartLevel = project.getDefaultStartlevel();
					int IsOurModule = project.getIsOurModule();
					String ConnectParameters = project.getConnectParameters();
					String grpnameString = "";
					List<group> grplist = groupMapper.selectbyProjectid(projectid);
					for (int j = 0; j < grplist.size(); j++) {
						grpnameString += grplist.get(j).getGroupid() + ",";
					}
					if (grpnameString.length() > 0) {
						grpnameString = grpnameString.substring(0, grpnameString.length() - 1);
					}

					String usernameString = "";
					List<user> userlist = userMapper.selectByprojectid(projectid);
					for (int j = 0; j < userlist.size(); j++) {
						usernameString += userlist.get(j).getAdminname() + ",";
					}
					if (usernameString.length() > 0) {
						usernameString = usernameString.substring(0, usernameString.length() - 1);
					}

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("projectid", projectid);
					jsonObject.put("projectname", projectname);
					jsonObject.put("projectPwd", projectpwd);
					jsonObject.put("autoGrp", autoGrp);
					jsonObject.put("startLevelControl", startLevelControl);
					jsonObject.put("defaultStartLevel", defaultStartLevel);
					jsonObject.put("IsOurModule", IsOurModule);
					jsonObject.put("disconnect", project.getDisconnect());
					jsonObject.put("grouplist", grpnameString);
					jsonObject.put("userlist", usernameString);
					jsonObject.put("ConnectParameters", ConnectParameters);

					JsonArray.add(jsonObject);
				}
			}
			return JsonArray;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject createProject(String projectid, String projectname, String CheckCode, int startlevelControl,
			int DefaultStartlevel, int isOurModule, int disconnect, String ConnectParameters, String username,
			String userpwd, String groupname, int packLength, int groupwidth, int groupheight) {
		JSONObject jObject = new JSONObject();
		try {
			String hashAlgorithmNameString = "MD5";
			Object credentials = userpwd;
			Object salt = ByteSource.Util.bytes(username);
			int hashIterations = 1024;
			Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);
			String codeString = Encryption.encode(userpwd);

			user user = new user();
			user.setAdmincount(0);
			user.setAdminlevel(1);
			user.setAdminname(username);
			user.setAdminpermission("111111");
			user.setAdminpwd(result.toString());
			user.setAdminstatus(0);
			user.setDelindex(0);
			user.setExpdate("");
			user.setGrpcount(0);
			user.setinherit(0);
			user.setIssuperuser(0);
			user.setParentid(1);
			user.setProjectid(projectid);
			user.setAdminRemarks(codeString);
			userMapper.insert(user);

			GJ_Set2cls set2cls = new GJ_Set2cls();
			set2cls.set_id(1);
			set2cls.set_version(1);
			set2cls.set_DefaulText("文明驾驶安全行车");
			set2cls.set_AlarmText("我被打劫,请报警!");
			set2cls.set_setZhaoMingMode(0);
			set2cls.set_ZhaoMingTimeStart("17:00");
			set2cls.set_ZhaoMingTimeEnd("07:00");
			String setPara2 = JSONObject.toJSONString(set2cls);

			GJ_Set3cls set3cls = new GJ_Set3cls();
			set3cls.set_id(1);
			set3cls.set_version(1);
			set3cls.set_SetBrightnessMode(2);
			set3cls.set_Area_ProvinceName("北京");
			set3cls.set_Area_CityName("北京");
			set3cls.set_Area_Longitude(116.46);
			set3cls.set_Area_Latitude(39.92);
			String[] BrightnessTimeArray = new String[8];
			BrightnessTimeArray[0] = "03:00";
			BrightnessTimeArray[1] = "05:00";
			BrightnessTimeArray[2] = "06:00";
			BrightnessTimeArray[3] = "07:00";
			BrightnessTimeArray[4] = "16:00";
			BrightnessTimeArray[5] = "17:00";
			BrightnessTimeArray[6] = "19:00";
			BrightnessTimeArray[7] = "21:00";
			set3cls.set_BrightnessTimeArray(BrightnessTimeArray);
			byte[] BrightnessValueArray = new byte[8];
			BrightnessValueArray[0] = (byte) (6 & 0xFF);
			BrightnessValueArray[1] = (byte) (9 & 0xFF);
			BrightnessValueArray[2] = (byte) (12 & 0xFF);
			BrightnessValueArray[3] = (byte) (15 & 0xFF);
			BrightnessValueArray[4] = (byte) (13 & 0xFF);
			BrightnessValueArray[5] = (byte) (10 & 0xFF);
			BrightnessValueArray[6] = (byte) (7 & 0xFF);
			BrightnessValueArray[7] = (byte) (4 & 0xFF);
			set3cls.set_BrightnessValueArray(BrightnessValueArray);
			String setPara3 = JSONObject.toJSONString(set3cls);

			GJ_Set1cls set1cls = new GJ_Set1cls();
			set1cls.set_id(1);
			set1cls.set_version(1);
			set1cls.set_Showversion(1);
			set1cls.set_LinkTime(2);
			set1cls.set_MaxReceiveLen(512);
			set1cls.set_BootstrapperWaitingTime(2);
			set1cls.set_test(0);
			set1cls.set_Energy(1);
			set1cls.set_DTULink(3);
			String setPara1 = JSONObject.toJSONString(set1cls);

			group group = new group();
			group.setDelindex(0);
			group.setGroupname(groupname);
			group.setplpubid(0);
			group.setPubid(0);
			group.setPara1_Basic(setPara1);
			group.setPara2_User(setPara2);
			group.setPara3_TimeLight(setPara3);
			group.setMaxPackLength(packLength);
			group.setscreenheight(groupheight);
			group.setscreenwidth(groupwidth);
			group.setProjectid(projectid);
			groupMapper.insert(group);

			project record = new project();
			record.setProjectid(projectid);
			record.setProjectname(projectname);
			record.setCheckCode(CheckCode);
			record.setStartlevelControl(startlevelControl);
			record.setDefaultStartlevel(DefaultStartlevel);
			record.setAutoGroupTo(group.getGroupid());
			record.setIsOurModule(isOurModule);
			record.setDisconnect(disconnect);
			record.setConnectParameters(ConnectParameters);
			projectMapper.insert(record);

			jObject.put("result", "success");
			jObject.put("Groupid", group.getGroupid());
			jObject.put("groupheight", groupheight);
			jObject.put("groupwidth", groupwidth);

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject updateProject(String projectid, String projectname, int AutoGroupTo, String CheckCode,
			int startlevelControl, int DefaultStartlevel, int isOurModule, int disconnect, String ConnectParameters) {
		JSONObject jObject = new JSONObject();
		try {
			project record = new project();
			record.setProjectid(projectid);
			record.setProjectname(projectname);
			record.setAutoGroupTo(AutoGroupTo);
			record.setCheckCode(CheckCode);
			record.setStartlevelControl(startlevelControl);
			record.setDefaultStartlevel(DefaultStartlevel);
			record.setIsOurModule(isOurModule);
			record.setDisconnect(disconnect);
			record.setConnectParameters(ConnectParameters);
			projectMapper.updateByPrimaryKeySelective(record);

			if (disconnect == 1) {
				List<terminal> terminals = terminalMapper.SelectTerminalAllByprojectid(projectid);
				if (terminals != null) {
					for (int i = 0; i < terminals.size(); i++) {
						String taxiName = terminals.get(i).getName();
						if (getCode.isCarnumberNO(taxiName)) {
							terminals.get(i).setDisconnect(1);
							terminalMapper.updateByPrimaryKeySelective(terminals.get(i));
						}
					}
				}
			}

			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
			// 写入项目表，发布广告改动时间数据
			projectMapper.updateParameterUpdateTimeByPrimaryKey(projectid, d1.format(now));

			jObject.put("result", "success");

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject removeProject(String projectid) {
		JSONObject jObject = new JSONObject();
		try {

			int result = 0;
			result = projectMapper.deleteByPrimaryKey(projectid);

			result = userMapper.deleteByprojectid(projectid);

			result = terminalMapper.deleteByprojectid(projectid);

			result = basemapMapper.deleteByprojectid(projectid);

			List<group> gList = groupMapper.selectbyProjectid(projectid);
			for (int i = 0; i < gList.size(); i++) {
				int groupid = gList.get(i).getGroupid();
				List<advertisement> advlist = advertisementMapper.selectidBygroupid(groupid, 0);
				for (int j = 0; j < advlist.size(); j++) {
					int infosn = advlist.get(j).getinfoSN();
					result = advertisementMapper.deleteByPrimaryKey(infosn);
					result = itemMapper.deleteByadid(infosn);
					result = infocodeMapper.deleteByinfoSN(infosn);
				}

				advlist = advertisementMapper.selectidBygroupid(groupid, 1);
				for (int j = 0; j < advlist.size(); j++) {
					int infosn = advlist.get(j).getinfoSN();
					result = advertisementMapper.deleteByPrimaryKey(infosn);
					result = itemMapper.deleteByadid(infosn);
					result = infocodeMapper.deleteByinfoSN(infosn);
				}

				result = playlistMapper.deleteBygroupid(groupid);
				result = playlistcodeMapper.deleteBygroupid(groupid);
			}

			result = groupMapper.deleteByprojectid(projectid);
			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject moveGroup(String sprojectid, String oprojectid, int sgroupid, int ogroupid) {
		JSONObject jObject = new JSONObject();
		try {
			terminalMapper.updatemoveGroup(sprojectid, oprojectid, sgroupid, ogroupid);

			Date now = new Date();
			DateFormat d1 = DateFormat.getDateTimeInstance();
			// 写入项目表，发布广告改动时间数据
			projectMapper.updateTerminalUpdateTimeByPrimaryKey(sprojectid, d1.format(now));
			projectMapper.updateParameterUpdateTimeByPrimaryKey(sprojectid, d1.format(now));
			projectMapper.updateTerminalUpdateTimeByPrimaryKey(oprojectid, d1.format(now));
			projectMapper.updateParameterUpdateTimeByPrimaryKey(oprojectid, d1.format(now));
			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONArray getGroupbyProjectid(String projectid) {
		JSONObject jObject = new JSONObject();
		try {
			int AutoGroupid = projectMapper.selectByPrimaryKey(projectid).getAutoGroupTo();
			JSONArray JsonArray = new JSONArray();
			List<group> grplist = groupMapper.selectbyProjectid(projectid);
			for (int i = 0; i < grplist.size(); i++) {
				int grpid = grplist.get(i).getGroupid();
				String grpname = grplist.get(i).getGroupname();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("grpid", grpid);
				jsonObject.put("grpname", grpname);
				if (grpid == AutoGroupid) {
					jsonObject.put("isSelect", 1);
				} else {
					jsonObject.put("isSelect", 0);
				}
				JsonArray.add(jsonObject);
			}
			return JsonArray;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject programlistChange() {
		JSONObject jObject = new JSONObject();
		try {
			List<playlist> playlists = playlistMapper.selectAll();
			for (int i = 0; i < playlists.size(); i++) {
				playlist playlist = playlists.get(i);
				if (playlist.getScheduletype() == 2) {
					String Programlist = playlist.getProgramlist();
					if (Programlist.indexOf("infosn") > 0) {
						String plString = Programlist.replaceAll("infosn", "infoid");
						System.out.println(plString);
						playlist.setProgramlist(plString);
						playlistMapper.updateByPrimaryKeySelective(playlist);
					}
				}
			}

			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject passwordEnCode() {
		JSONObject jObject = new JSONObject();
		try {
			List<user> users = userMapper.selectAll();
			for (int i = 0; i < users.size(); i++) {
				user user = users.get(i);
				String pwdString = user.getAdminpwd();
				String hashAlgorithmNameString = "MD5";
				Object credentials = pwdString;
				Object salt = ByteSource.Util.bytes(user.getAdminname());
				int hashIterations = 1024;
				Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);

				String codeString = Encryption.encode(pwdString);
				// String pwdString = Encryption.decode(codeString);

				user.setAdminpwd(result.toString());
				user.setAdminRemarks(codeString);
				userMapper.updateByPrimaryKeySelective(user);
			}

			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject txt2db() {
		JSONObject jObject = new JSONObject();
		try {
			ClassPathResource classPathResource = new ClassPathResource("static/不良词汇表.txt");
			InputStream inputStream = classPathResource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String s = "";
			while ((s = br.readLine()) != null) {
				sensitive record = new sensitive();
				record.setDelindex(0);
				record.setProjectid("super");
				record.setSensitivestring(s);
				sensitiveMapper.insert(record);
			}

			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}
}
