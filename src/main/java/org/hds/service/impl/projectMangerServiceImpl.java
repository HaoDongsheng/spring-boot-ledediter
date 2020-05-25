package org.hds.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.hds.Encryption;
import org.hds.GJ_coding.GJ_Set1cls;
import org.hds.GJ_coding.GJ_Set2cls;
import org.hds.GJ_coding.GJ_Set3cls;
import org.hds.GJ_coding.GJ_codingCls;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.basemapMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.sensitiveMapper;
import org.hds.mapper.statisticMapper;
import org.hds.mapper.terminalMapper;
import org.hds.mapper.userMapper;
import org.hds.model.advertisement;
import org.hds.model.group;
import org.hds.model.infocode;
import org.hds.model.playlist;
import org.hds.model.playlistcode;
import org.hds.model.project;
import org.hds.model.sensitive;
import org.hds.model.terminal;
import org.hds.model.user;
import org.hds.service.IAdvMangerService;
import org.hds.service.IInfoListService;
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
	IAdvMangerService advMangerSer;
	@Autowired
	IInfoListService InfoListSer;
	@Autowired
	infocodeMapper infocodeMapper;
	@Autowired
	basemapMapper basemapMapper;
	@Autowired
	statisticMapper statisticMapper;
	@Autowired
	itemMapper itemMapper;
	@Autowired
	sensitiveMapper sensitiveMapper;
	@Autowired
	getCode getCode;

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
					String projectLimit = project.getProjectLimit();
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
					jsonObject.put("grouplist", grpnameString);
					jsonObject.put("userlist", usernameString);
					jsonObject.put("ConnectParameters", ConnectParameters);
					jsonObject.put("projectLimit", projectLimit);

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
			int DefaultStartlevel, int isOurModule, String ConnectParameters, String username, String userpwd,
			String groupname, int packLength, int batchCount, int groupwidth, int groupheight) {
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
			group.setBatchCount(batchCount);
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
			record.setConnectParameters(ConnectParameters);
			JSONObject JprojectLimit = new JSONObject();
			JprojectLimit.put("ExpTime", 90);
			JprojectLimit.put("ExpDisplay", 0);
			record.setProjectLimit(JprojectLimit.toJSONString());
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
			int startlevelControl, int DefaultStartlevel, int isOurModule, String ConnectParameters) {
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
			record.setConnectParameters(ConnectParameters);
			projectMapper.updateByPrimaryKeySelective(record);

//			if (disconnect == 1) {
//				List<terminal> terminals = terminalMapper.SelectTerminalAllByprojectid(projectid);
//				if (terminals != null) {
//					for (int i = 0; i < terminals.size(); i++) {
//						String taxiName = terminals.get(i).getName();
//						if (getCode.isCarnumberNO(taxiName)) {
//							terminals.get(i).setPlaymode(1);
//							terminalMapper.updateByPrimaryKeySelective(terminals.get(i));
//						}
//					}
//				}
//			}

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
	public JSONObject updateProjectlimit(String projectid, String projectLimit) {
		JSONObject jObject = new JSONObject();
		try {
			project record = new project();
			record.setProjectid(projectid);
			record.setProjectLimit(projectLimit);
			projectMapper.updateByPrimaryKeySelective(record);

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

			result = userMapper.deleteByprojectid(projectid);

			result = terminalMapper.deleteByprojectid(projectid);

			result = basemapMapper.deleteByprojectid(projectid);

			result = statisticMapper.deleteByProjectid(projectid);

			List<group> gList = groupMapper.selectbyProjectid(projectid);
			for (int i = 0; i < gList.size(); i++) {
				int groupid = gList.get(i).getGroupid();
				List<advertisement> advlist = advertisementMapper.selectidBygroupid(groupid, 0);
				for (int j = 0; j < advlist.size(); j++) {
					String infosn = advlist.get(j).getinfoSN();
					result = advertisementMapper.deleteByPrimaryKey(infosn);
					result = itemMapper.deleteByadid(infosn);
					result = infocodeMapper.deleteByinfoSN(infosn);
				}

				advlist = advertisementMapper.selectidBygroupid(groupid, 1);
				for (int j = 0; j < advlist.size(); j++) {
					String infosn = advlist.get(j).getinfoSN();
					result = advertisementMapper.deleteByPrimaryKey(infosn);
					result = itemMapper.deleteByadid(infosn);
					result = infocodeMapper.deleteByinfoSN(infosn);
				}

				result = playlistMapper.deleteBygroupid(groupid);
				result = playlistcodeMapper.deleteBygroupid(groupid);
			}

			result = groupMapper.deleteByprojectid(projectid);

			result = projectMapper.deleteByPrimaryKey(projectid);

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

	@Override
	public JSONObject execel2db() {
		JSONObject jObject = new JSONObject();
		try {

			String FileName = "C:\\Users\\86139\\Desktop\\update.xlsx";
			InputStream inputStream = new FileInputStream(FileName);// 文件的存放路径

			Workbook workbook = null;
			if (FileName.indexOf(".xlsx") > 0) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}

			if (workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);
				// getFirstRowNum()获取第一行
				// getLastRowNum()获取最后一行

				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					Cell cell = row.getCell(0);
					if (cell != null) {
						String cellvalue = cell.toString().trim();
						if (cellvalue.indexOf("'") >= 0) {
							cellvalue = cellvalue.replace("'", "");
						}

						if (cellvalue.indexOf("-") >= 0) {
							cellvalue = cellvalue.replace("-", "");
						}

						Cell cell1 = row.getCell(1);
						String dtuString = cellvalue;
						String projectid = cell1.toString().trim();

						int groupid = 0;
						switch (projectid) {
						case "haicheng":
							groupid = 50;
							break;
						case "eerduosi":
							groupid = 56;
							break;
						case "zhaotong":
							groupid = 19;
							break;
						}

						if (terminalMapper.selectByPrimaryKey(dtuString).getProjectid().trim().equals(projectid)) {
							System.out.println("DTUkey:" + dtuString + ",原来就在项目id:" + projectid);
						} else {
							terminal record = new terminal();
							record.setDtukey(dtuString);
							record.setName(dtuString);
							record.setGroupid(groupid);
							record.setProjectid(projectid);
							int result = terminalMapper.updateByPrimaryKeySelective(record);
							if (result != 1) {
								System.out.println("影响结果不为1");
							}
						}

					} else {
						System.out.println("cell为空");
					}
				}

//				FileOutputStream fos = new FileOutputStream("C:\\Users\\86139\\Desktop\\dianxin1.xls");
//
//				workbook.write(fos);
//				fos.flush();
//				fos.close();
			}

//			String FileName = "C:\\Users\\86139\\Desktop\\haichengdiushi.xls";
//			String FileNameError = "C:\\Users\\86139\\Desktop\\error.xls";
//			InputStream inputStream = new FileInputStream(FileName);// 文件的存放路径
//			InputStream inputStreamError = new FileInputStream(FileNameError);// 文件的存放路径
//			Workbook workbook = null;
//			if (FileName.indexOf(".xls") < 0) {
//				workbook = new XSSFWorkbook(inputStream);
//			} else {
//				workbook = new HSSFWorkbook(inputStream);
//			}
//
//			Workbook workbookError = null;
//			if (FileNameError.indexOf(".xls") < 0) {
//				workbookError = new XSSFWorkbook(inputStreamError);
//			} else {
//				workbookError = new HSSFWorkbook(inputStreamError);
//			}
//
//			if (workbook != null && workbookError != null) {
//				Sheet sheet = workbook.getSheetAt(0);
//				Sheet sheetError = workbookError.getSheetAt(0);
//				// getFirstRowNum()获取第一行
//				// getLastRowNum()获取最后一行
//				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
//					Row row = sheet.getRow(i);
//					Cell cell = row.getCell(1);
//					if (cell != null) {
//						String cellvalue = cell.toString().trim();
//						if (cellvalue.indexOf("'") >= 0) {
//							cellvalue = cellvalue.replace("'", "");
//						}
//
//						if (cellvalue.indexOf("-") >= 0) {
//							cellvalue = cellvalue.replace("-", "");
//						}
//
//						String dtuString = cellvalue;
//						for (int j = sheetError.getFirstRowNum(); j <= sheetError.getLastRowNum(); j++) {
//							Row rowError = sheetError.getRow(j);
//							Cell cellError = rowError.getCell(5);
//							if (cellError != null) {
//								String cellErrorvalue = cellError.toString().trim();
//								if (cellErrorvalue.equals(dtuString)) {
//									row.createCell(11);
//									Cell newcell = row.getCell(11);
//									newcell.setCellValue("有");
//
//									rowError.createCell(7);
//									Cell Enewcell = rowError.getCell(7);
//									Enewcell.setCellValue("有");
//								}
//							}
//						}
//					}
//				}
//				FileOutputStream fos = new FileOutputStream("C:\\Users\\86139\\Desktop\\haichengdiushi1.xls");
//
//				workbook.write(fos);
//				fos.flush();
//				fos.close();
//
//				FileOutputStream fosE = new FileOutputStream("C:\\Users\\86139\\Desktop\\error1.xls");
//
//				workbookError.write(fosE);
//				fosE.flush();
//				fosE.close();
//			}

			jObject.put("result", "success");
			return jObject;
		} catch (

		Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	@Override
	public JSONObject codeChange() {
		JSONObject jObject = new JSONObject();
		try {
//			JSONObject jObjectinfo = advMangerSer.Deleteinfo2oldbyDay();
//
//			JSONObject jObjectlist = InfoListSer.DeleteList2oldbyDay();
			List<String> delInfosnsList = new ArrayList<String>();
			List<String> delPlaylistsnsList = new ArrayList<String>();

			List<infocode> infocodes = infocodeMapper.selectAll();
			if (infocodes != null && infocodes.size() > 0) {
				for (int i = 0; i < infocodes.size(); i++) {
					String SingleCode = infocodes.get(i).getSingleCodeContext();
					if (SingleCode == null || SingleCode.equals("")) {
						String infosn = infocodes.get(i).getInfoSN();

						StringBuilder codeString = SingleCodebyCode(infocodes.get(i).getCodecontext());

						if (codeString != null) {
							infocode record = new infocode();
							record.setInfoCodeSN(infocodes.get(i).getInfoCodeSN());
							record.setSingleCodeContext(codeString.toString().trim());
							infocodeMapper.updateByPrimaryKeySelective(record);
						} else {
							delInfosnsList.add(infosn);
							group ggGroup = groupMapper.selectByPrimaryKey(infocodes.get(i).getGroupid());
							String messageString = "广告编号:" + infosn;
							messageString += "广告名称:" + advertisementMapper.selectByPrimaryKey(infosn).getAdvname();
							messageString += "项目:" + ggGroup.getProjectid();
							messageString += "分组:" + ggGroup.getGroupname();
							delPlaylistsnsList.add(messageString);
						}
					}
				}
			}

			List<playlistcode> playlistcodes = playlistcodeMapper.selectAll();
			for (int i = 0; i < playlistcodes.size(); i++) {
				String SingleCode = playlistcodes.get(i).getSingleCodeContext();
				if (SingleCode == null || SingleCode.equals("")) {
					String playlistCodeSN = playlistcodes.get(i).getplaylistCodeSN();
					String playlistSN = playlistcodes.get(i).getPlaylistsn();
					String codecontext = playlistcodes.get(i).getCodecontext();

					StringBuilder codeString = SingleCodebyCode(codecontext);
					if (codeString != null) {
						playlistcode record = new playlistcode();
						record.setplaylistCodeSN(playlistCodeSN);
						record.setSingleCodeContext(codeString.toString().trim());
						playlistcodeMapper.updateByPrimaryKeySelective(record);
					} else {
						group ggGroup = groupMapper.selectByPrimaryKey(playlistcodes.get(i).getGroupid());
						String messageString = "列表编号:" + playlistCodeSN;
						messageString += "列表名称:" + playlistMapper.selectByPrimaryKey(playlistSN).getPlaylistname();
						messageString += "项目:" + ggGroup == null ? "" : ggGroup.getProjectid();
						messageString += "分组:" + ggGroup == null ? "" : ggGroup.getGroupname();
						delPlaylistsnsList.add(messageString);
					}
				}
			}
			// infocodeMapper.updateByPrimaryKeySelective(record)
			jObject.put("result", "success");
			jObject.put("delinfosns", String.join(",", delInfosnsList));
			jObject.put("delplaylistsns", String.join(",", delPlaylistsnsList));
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}

	private StringBuilder SingleCodebyCode(String codecontext) {
		StringBuilder codeString = new StringBuilder();
		try {
			JSONArray jArray = JSONArray.parseArray(codecontext);
			if (jArray != null && jArray.size() > 0) {
				for (int j = 0; j < jArray.size(); j++) {
					String dataString = jArray.getString(j);
					dataString = dataString.replace(" ", "");
					int m = 0, n = 0;
					int byteLen = dataString.length() / 2; // 每两个字符描述一个字节
					byte[] byteArray = new byte[byteLen];
					for (int z = 0; z < byteLen; z++) {
						m = z * 2 + 1;
						n = m + 1;
						int intVal = Integer.decode("0x" + dataString.substring(z * 2, m) + dataString.substring(m, n));
						byteArray[z] = Byte.valueOf((byte) intVal);
					}

					GJ_codingCls cls = new GJ_codingCls(950, false, true);
					byte[] fzyByte = cls.GJ_FanZhuanYi(byteArray);

					int bytelength = (fzyByte[31] & 0xFF) | (fzyByte[30] & 0xFF) << 8;

					byte[] dataByte = new byte[bytelength];
					System.arraycopy(fzyByte, 32, dataByte, 0, bytelength);

					StringBuilder buf = new StringBuilder(dataByte.length * 2);
					for (byte b : dataByte) { // 使用String的format方法进行转换
						buf.append(String.format("%02x", new Integer(b & 0xff)));
					}

					codeString.append(buf);
				}
			}
			return codeString;
		} catch (Exception e) {
			return null;
		}
	}
}
