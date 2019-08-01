package org.hds.service.impl;

import java.io.File;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import org.hds.GJ_coding.GJ_Set1cls;
import org.hds.GJ_coding.GJ_Set2cls;
import org.hds.GJ_coding.GJ_Set3cls;
import org.hds.mapper.groupMapper;
import org.hds.mapper.projectMapper;
import org.hds.model.user;
import org.hds.model.group;
import org.hds.service.IgroupMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class groupMangerServiceImpl implements IgroupMangerService{

	@Autowired	
	groupMapper taxigroupMapper;
	@Autowired
	projectMapper projectMapper;
	
	@Override
	public JSONObject CreatGroup(String grpname,String projectid,int packLength,int width,int height)
	{
		JSONObject jObject=new JSONObject();
		try {
			int count = taxigroupMapper.selectCountByName(grpname);
			if(count > 0)
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "分组名"+grpname+"已存在!");		
			}
			else
			{
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
				
				group record=new group();
				record.setGroupname(grpname);
				record.setProjectid(projectid);
				record.setMaxPackLength(packLength);
				record.setscreenwidth(width);
				record.setscreenheight(height);
				record.setPara1_Basic(setPara1);
				record.setPara2_User(setPara2);
				record.setPara3_TimeLight(setPara3);
				record.setDelindex(0);
				record.setPubid(100);
				record.setplpubid(100);
				
				int rowCount = taxigroupMapper.insert(record);	
				
				if(rowCount>0)
				{
					int groupid=record.getGroupid();
					
					Date now = new Date(); 				       
					DateFormat d1 = DateFormat.getDateTimeInstance();
					//写入项目表，发布广告改动时间数据
					projectMapper.updateParameterUpdateTimeByPrimaryKey(taxigroupMapper.selectByPrimaryKey(groupid).getProjectid(), d1.format(now));
					
					jObject.put("result", "success");
					jObject.put("groupid", groupid);					
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "插入用户表失败");					
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
	public JSONObject EditGroup(int grpid,String grpname,int packLength,int width,int height)
	{
		JSONObject jObject=new JSONObject();
		try {
			int count = taxigroupMapper.selectCountByNameid(grpname, grpid);
			if(count > 0)
			{
				jObject.put("result", "fail");
				jObject.put("resultMessage", "分组名"+grpname+"已存在!");		
			}
			else
			{
				group record=new group();
				record.setGroupid(grpid);
				record.setGroupname(grpname);
				record.setMaxPackLength(packLength);				
				record.setscreenwidth(width);
				record.setscreenheight(height);
	
				int rowCount = taxigroupMapper.updateByPrimaryKeySelective(record);	
				
				if(rowCount>0)
				{				
					jObject.put("result", "success");	
				}
				else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "修改分组表失败");					
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
	public JSONObject DeleteGroup(int grpid)
	{
		JSONObject jObject=new JSONObject();
		try {
			String projectid = taxigroupMapper.selectByPrimaryKey(grpid).getProjectid();
			int rowCount = taxigroupMapper.deleteByPrimaryKey(grpid);	
			
			if(rowCount>0)
			{			
				Date now = new Date(); 				       
				DateFormat d1 = DateFormat.getDateTimeInstance();
				//写入项目表，发布广告改动时间数据
				projectMapper.updateParameterUpdateTimeByPrimaryKey(projectid, d1.format(now));
				
				jObject.put("result", "success");	
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除分组失败");					
			}
				
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}	
	}
	
	@Override
	public JSONObject Set_parameter(String parameter)
	{
		JSONObject jObject=new JSONObject();
		try {
			
			JSONObject Jparameter = JSONObject.parseObject(parameter);
			
			group record=new group();
			record.setGroupid(Jparameter.getIntValue("groupid"));			
			String setType = Jparameter.getString("setType");
			switch (setType) {
				case "user":{
					GJ_Set2cls set2cls=new GJ_Set2cls();
					set2cls.set_id(new Random().nextInt(255));
					set2cls.set_version(1);
					set2cls.set_DefaulText(Jparameter.getString("DefaulText"));
					set2cls.set_AlarmText(Jparameter.getString("AlarmText"));
					set2cls.set_setZhaoMingMode(Jparameter.getIntValue("setZhaoMingMode"));
					set2cls.set_ZhaoMingTimeStart(Jparameter.getString("m_ZhaoMingTimeStart"));
					set2cls.set_ZhaoMingTimeEnd(Jparameter.getString("m_ZhaoMingTimeEnd"));
					
					String setPara2 = JSONObject.toJSONString(set2cls);
					record.setPara2_User(setPara2);
				};break;
				case "brightness":{
					GJ_Set3cls set3cls=new GJ_Set3cls();
					set3cls.set_id(new Random().nextInt(255));
					set3cls.set_version(1);
					int SetBrightnessMode = Jparameter.getIntValue("SetBrightnessMode");
					set3cls.set_SetBrightnessMode(SetBrightnessMode);
					set3cls.set_Area_ProvinceName(Jparameter.getString("ProvinceName"));
					set3cls.set_Area_CityName(Jparameter.getString("CityName"));										
					
					//获取跟目录
					/*
					File path = new File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "UTF-8"));			
					if(!path.exists())
					{
						jObject.put("result", "fail");
						jObject.put("resultMessage", "读取ini文件失败");
						
						return jObject;
					}

					File file = new File(URLDecoder.decode(path.getAbsolutePath(), "UTF-8"),"static/Area.ini");
					if(!file.exists()) {
						jObject.put("result", "fail");
						jObject.put("resultMessage", "读取ini文件失败");
						
						return jObject;
					}
					
					org.ini4j.Wini ini = new org.ini4j.Wini(file);
					*/
					ClassPathResource classPathResource=new ClassPathResource("static/Area.ini");
					org.ini4j.Wini ini = new org.ini4j.Wini(classPathResource.getInputStream());
					String Longitude_latitude = ini.get(Jparameter.getString("ProvinceName"), Jparameter.getString("CityName"));
					
					set3cls.set_Area_Longitude(Double.parseDouble(Longitude_latitude.split("\\|")[0].trim()));
					set3cls.set_Area_Latitude(Double.parseDouble(Longitude_latitude.split("\\|")[1].trim()));
					
					String[] sBrightnessValueArray = Jparameter.getString("BrightnessValueArray").split("\\|");
					if(SetBrightnessMode == 2)
					{set3cls.set_BrightnessTimeArray(sBrightnessValueArray);}
					else {
						set3cls.set_BrightnessTimeArray(Jparameter.getString("BrightnessTimeArray").split("\\|"));
					}
					
					byte[] BrightnessValueArray=new byte[8];
					for(int i=0;i<sBrightnessValueArray.length;i++)
					{
						BrightnessValueArray[i] =(byte) (Integer.parseInt(sBrightnessValueArray[i]) & 0xFF);
					}
					set3cls.set_BrightnessValueArray(BrightnessValueArray);

					String setPara3 = JSONObject.toJSONString(set3cls);
					record.setPara3_TimeLight(setPara3);
					
					Date now = new Date(); 				       
					DateFormat d1 = DateFormat.getDateTimeInstance();
					//写入项目表，发布广告改动时间数据
					projectMapper.updateParameterUpdateTimeByPrimaryKey(taxigroupMapper.selectByPrimaryKey(Jparameter.getIntValue("groupid")).getProjectid(), d1.format(now));
				};break;
				case "basic":{
					GJ_Set1cls set1cls=new GJ_Set1cls();
					set1cls.set_id(new Random().nextInt(255));
					set1cls.set_version(1);
					set1cls.set_Showversion(Jparameter.getIntValue("Showversion"));
					set1cls.set_LinkTime(Jparameter.getIntValue("LinkTime"));
					set1cls.set_MaxReceiveLen(Jparameter.getIntValue("MaxReceiveLen"));
					set1cls.set_BootstrapperWaitingTime(Jparameter.getIntValue("BootstrapperWaitingTime"));
					set1cls.set_test(Jparameter.getIntValue("Test"));
					set1cls.set_Energy(Jparameter.getIntValue("Energy"));
					set1cls.set_DTULink(Jparameter.getIntValue("DTULink"));
					
					String setPara1 = JSONObject.toJSONString(set1cls);					
					record.setPara1_Basic(setPara1);					
				};break;
			}
			taxigroupMapper.updateByPrimaryKeySelective(record);	
			
			jObject.put("result", "success");			
				
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}	
	}
	
	@Override
	public JSONObject Get_parameter(String parameter)
	{
		JSONObject jObject=new JSONObject();
		try {
			
			JSONObject Jparameter = JSONObject.parseObject(parameter);
						
			int groupid = Jparameter.getIntValue("groupid");
			group group = taxigroupMapper.selectByPrimaryKey(groupid);
			String setType = Jparameter.getString("setType");
			switch (setType) {
				case "user":{
					String Para2_User = group.getPara2_User();
					JSONObject Juser = JSONObject.parseObject(Para2_User);
					jObject.put("result", "success");
					jObject.put("DefaulText", Juser.getString("DefaulText"));
					jObject.put("AlarmText", Juser.getString("AlarmText"));
					jObject.put("setZhaoMingMode", Juser.getIntValue("setZhaoMingMode"));
					jObject.put("m_ZhaoMingTimeStart", Juser.getString("ZhaoMingTimeStart"));
					jObject.put("m_ZhaoMingTimeEnd", Juser.getString("ZhaoMingTimeEnd"));					
				};break;
				case "brightness":{
					String Para3_Brightness = group.getPara3_TimeLight();
					JSONObject JBrightness = JSONObject.parseObject(Para3_Brightness);
					jObject.put("result", "success");
					jObject.put("SetBrightnessMode", JBrightness.getIntValue("SetBrightnessMode"));
					jObject.put("ProvinceName", JBrightness.getString("Area_ProvinceName"));
					jObject.put("CityName", JBrightness.getString("Area_CityName"));					
					jObject.put("BrightnessTimeArray", JBrightness.getString("BrightnessTimeArray"));

					byte[] BrightnessValueArray = JBrightness.getBytes("BrightnessValueArray");
					String strBrightnessValueArray="";
					for(int i=0;i<BrightnessValueArray.length;i++)
					{
						strBrightnessValueArray += (int)BrightnessValueArray[i] +"|";
					}
					if(strBrightnessValueArray.length()>0)
					{strBrightnessValueArray=strBrightnessValueArray.substring(0,strBrightnessValueArray.length() - 1);}

					jObject.put("BrightnessValueArray", strBrightnessValueArray);
				};break;
				case "basic":{					
					String Para1_Basic = group.getPara1_Basic();
					JSONObject JBasic = JSONObject.parseObject(Para1_Basic);
					jObject.put("result", "success");
					jObject.put("Showversion", JBasic.getIntValue("Showversion"));
					jObject.put("LinkTime", JBasic.getIntValue("LinkTime"));
					jObject.put("MaxReceiveLen", JBasic.getIntValue("MaxReceiveLen"));
					jObject.put("BootstrapperWaitingTime", JBasic.getIntValue("BootstrapperWaitingTime"));
					jObject.put("Test", JBasic.getIntValue("test"));
					jObject.put("Energy", JBasic.getIntValue("Energy"));
					jObject.put("DTULink", JBasic.getIntValue("DTULink"));										
				};break;
			}															
				
			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
			
			return jObject;
		}	
	}
}
