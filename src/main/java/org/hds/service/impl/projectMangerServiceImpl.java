package org.hds.service.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.hds.GJ_coding.GJ_Set1cls;
import org.hds.GJ_coding.GJ_Set2cls;
import org.hds.GJ_coding.GJ_Set3cls;
import org.hds.mapper.groupMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.userMapper;
import org.hds.model.group;
import org.hds.model.project;
import org.hds.model.user;
import org.hds.service.IprojectMangerService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public JSONArray getProjectlist()
	{
		try {
			JSONArray JsonArray = new JSONArray();
			List<project> proList = projectMapper.selectAll();
			if(proList!=null && proList.size()>0)
			{
				for(int i=0;i<proList.size();i++)
				{
					project project=proList.get(i);
					String projectid = project.getProjectid();
					String projectname = project.getProjectname();
					int IsOurModule = project.getIsOurModule();
					String ConnectParameters = project.getConnectParameters();
					String grpnameString="";
					List<group> grplist = groupMapper.selectbyProjectid(projectid);
					for(int j=0;j<grplist.size();j++)
					{
						grpnameString += grplist.get(j).getGroupname() + ",";
					}
					if(grpnameString.length()>0)
					{grpnameString=grpnameString.substring(0,grpnameString.length() - 1);}
					
					String usernameString="";
					List<user> userlist = userMapper.selectByprojectid(projectid);
					for(int j=0;j<userlist.size();j++)
					{
						usernameString += userlist.get(j).getAdminname() + ",";
					}
					if(usernameString.length()>0)
					{usernameString=usernameString.substring(0,usernameString.length() - 1);}
					
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("projectid", projectid);
					jsonObject.put("projectname", projectname);
					jsonObject.put("IsOurModule", IsOurModule);
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
	public JSONObject createProject(String projectid, String projectname, int isOurModule, String ConnectParameters, String username, String userpwd, String groupname, int packLength, int groupwidth, int groupheight)
	{
		JSONObject jObject=new JSONObject();
		try {						
			user user=new user();
			user.setAdmincount(0);
			user.setAdminlevel(1);
			user.setAdminname(username);
			user.setAdminpermission("111111");
			user.setAdminpwd(userpwd);
			user.setAdminstatus(0);
			user.setDelindex(0);
			user.setExpdate("");
			user.setGrpcount(0);
			user.setinherit(0);
			user.setIssuperuser(0);
			user.setParentid(1);
			user.setProjectid(projectid);
			userMapper.insert(user);
			
			GJ_Set2cls set2cls=new GJ_Set2cls();
			set2cls.set_id(1);
			set2cls.set_version(1);
			set2cls.set_DefaulText("文明驾驶安全行车");
			set2cls.set_AlarmText("我被打劫,请报警!");
			set2cls.set_setZhaoMingMode(0);
			set2cls.set_ZhaoMingTimeStart("17:00");
			set2cls.set_ZhaoMingTimeEnd("07:00");				
			String setPara2 = JSONObject.toJSONString(set2cls);
			
			GJ_Set3cls set3cls=new GJ_Set3cls();
			set3cls.set_id(1);
			set3cls.set_version(1);				
			set3cls.set_SetBrightnessMode(2);
			set3cls.set_Area_ProvinceName("北京");
			set3cls.set_Area_CityName("北京");										
			set3cls.set_Area_Longitude(116.46);
			set3cls.set_Area_Latitude(39.92);
			String[] BrightnessTimeArray=new String[8];
			BrightnessTimeArray[0]="03:00";
			BrightnessTimeArray[1]="05:00";
			BrightnessTimeArray[2]="06:00";
			BrightnessTimeArray[3]="07:00";
			BrightnessTimeArray[4]="16:00";
			BrightnessTimeArray[5]="17:00";
			BrightnessTimeArray[6]="19:00";
			BrightnessTimeArray[7]="21:00";
			set3cls.set_BrightnessTimeArray(BrightnessTimeArray);								
			byte[] BrightnessValueArray=new byte[8];				
			BrightnessValueArray[0]=(byte) (6 & 0xFF);
			BrightnessValueArray[1]=(byte) (9 & 0xFF);
			BrightnessValueArray[2]=(byte) (12 & 0xFF);
			BrightnessValueArray[3]=(byte) (16 & 0xFF);
			BrightnessValueArray[4]=(byte) (13 & 0xFF);
			BrightnessValueArray[5]=(byte) (10 & 0xFF);
			BrightnessValueArray[6]=(byte) (7 & 0xFF);
			BrightnessValueArray[7]=(byte) (4 & 0xFF);				
			set3cls.set_BrightnessValueArray(BrightnessValueArray);
			String setPara3 = JSONObject.toJSONString(set3cls);
			
			GJ_Set1cls set1cls=new GJ_Set1cls();
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
			
			group group=new group();
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
			
			project record=new project();
			record.setProjectid(projectid);
			record.setProjectname(projectname);
			record.setAutoGroupTo(group.getGroupid());
			record.setIsOurModule(isOurModule);
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
	public JSONObject updateProject(String projectid, String projectname, int isOurModule, String ConnectParameters)
	{
		JSONObject jObject=new JSONObject();
		try {										
			project record=new project();
			record.setProjectid(projectid);
			record.setProjectname(projectname);			
			record.setIsOurModule(isOurModule);
			record.setConnectParameters(ConnectParameters);
			projectMapper.updateByPrimaryKeySelective(record);
									
			Date now = new Date(); 				       
			DateFormat d1 = DateFormat.getDateTimeInstance();
			//写入项目表，发布广告改动时间数据
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
	public JSONObject removeProject(String projectid)
	{
		JSONObject jObject=new JSONObject();
		try {
			
			projectMapper.deleteByPrimaryKey(projectid);
			
			userMapper.deleteByprojectid(projectid);
			
			groupMapper.deleteByprojectid(projectid);
			jObject.put("result", "success");
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			return jObject;
		}
	}
}
