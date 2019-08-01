package org.hds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hds.mapper.groupMapper;
import org.hds.mapper.projectMapper;
import org.hds.mapper.statisticMapper;
import org.hds.model.statistic;
import org.hds.service.ImainService;

@Service
public class mainSeviceImpl implements ImainService {

	@Autowired
	statisticMapper statisticMapper;
	@Autowired
	groupMapper groupMapper;
	@Autowired
	projectMapper projectMapper;
	
	@Override
	public JSONObject GetStatisticsData(String projectid)
	{
		JSONObject jObject=new JSONObject();
		try {
			
			List<statistic> statisticList =statisticMapper.selectidByprojectid(projectid);
			if(statisticList!=null && statisticList.size()>0)
			{
				String recordingtimeArr[] = new String[statisticList.size()];
				int TotalArr[] = new int[statisticList.size()];
				int DtuArr[] = new int[statisticList.size()];
				int LedArr[] = new int[statisticList.size()];
				int UpdatedArr[] = new int[statisticList.size()];
				int WaitingArr[] = new int[statisticList.size()];
				double UpdateRateArr[] = new double[statisticList.size()];
				for(int i=0;i<statisticList.size();i++)
				{
					statistic statistic = statisticList.get(i);
					String Recordingtime = statistic.getRecordingtime();
					int Total = statistic.getTotal();
					int Dtu = statistic.getDtu();
					int Led = statistic.getLed();
					int Updated = statistic.getUpdated();
					int Waiting = statistic.getWaiting();
					double UpdateRate = 0;
					if(statistic.getUpdateRate()!=null)
					{UpdateRate = statistic.getUpdateRate();}
					UpdateRate = (double)((int)(UpdateRate*10000))/100;
					recordingtimeArr[i] = Recordingtime;
					TotalArr[i] = Total;
					DtuArr[i] = Dtu;
					LedArr[i] = Led;
					UpdatedArr[i] = Updated;
					WaitingArr[i] = Waiting;
					UpdateRateArr[i] = UpdateRate;
				}
				jObject.put("result", "success");
				jObject.put("recordingtimeArr", recordingtimeArr);
				jObject.put("TotalArr", TotalArr);
				jObject.put("DtuArr", DtuArr);
				jObject.put("LedArr", LedArr);
				jObject.put("UpdatedArr", UpdatedArr);
				jObject.put("WaitingArr", WaitingArr);
				jObject.put("UpdateRateArr", UpdateRateArr);
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "数据不存在");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally
		{return jObject;}
	}
	
	public JSONObject GetStatisticsDatabyDate(String projectid, String startDate, String endDate)
	{
		JSONObject jObject=new JSONObject();
		try {
			
			List<statistic> statisticList =statisticMapper.selectidByprojectidDate(projectid, startDate, endDate);
			if(statisticList!=null && statisticList.size()>0)
			{
				String recordingtimeArr[] = new String[statisticList.size()];
				int TotalArr[] = new int[statisticList.size()];
				int DtuArr[] = new int[statisticList.size()];
				int LedArr[] = new int[statisticList.size()];
				int UpdatedArr[] = new int[statisticList.size()];
				int WaitingArr[] = new int[statisticList.size()];
				double UpdateRateArr[] = new double[statisticList.size()];
				String maxDate="1999-09-09 0:00:00",minDate="2100-09-09 23:59:59";
				for(int i=0;i<statisticList.size();i++)
				{
					statistic statistic = statisticList.get(i);
					String Recordingtime = statistic.getRecordingtime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    java.util.Date rDate = sdf.parse(Recordingtime);
				    int compareTo = rDate.compareTo(sdf.parse(maxDate));
				    if(compareTo > 0)
				    {maxDate = sdf.format(rDate);}
				    
				    compareTo = rDate.compareTo(sdf.parse(minDate));
				    if(compareTo < 0)
				    {minDate = sdf.format(rDate);}
				    
					int Total = statistic.getTotal();
					int Dtu = statistic.getDtu();
					int Led = statistic.getLed();
					int Updated = statistic.getUpdated();
					int Waiting = statistic.getWaiting();
					double UpdateRate = statistic.getUpdateRate();
					UpdateRate = (double)((int)(UpdateRate*10000))/100;
					recordingtimeArr[i] = Recordingtime;
					TotalArr[i] = Total;
					DtuArr[i] = Dtu;
					LedArr[i] = Led;
					UpdatedArr[i] = Updated;
					WaitingArr[i] = Waiting;
					UpdateRateArr[i] = UpdateRate;
				}
				jObject.put("result", "success");
				jObject.put("minDate", minDate);
				jObject.put("maxDate", maxDate);
				jObject.put("recordingtimeArr", recordingtimeArr);
				jObject.put("TotalArr", TotalArr);
				jObject.put("DtuArr", DtuArr);
				jObject.put("LedArr", LedArr);
				jObject.put("UpdatedArr", UpdatedArr);
				jObject.put("WaitingArr", WaitingArr);
				jObject.put("UpdateRateArr", UpdateRateArr);
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "数据不存在");
			}
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally
		{return jObject;}
	}

	@Override
	public JSONObject GetUpdateRate(int groupid,String projectid)
	{
		JSONObject jObject=new JSONObject();
		try {
			double UpdateRate=0;
			try {							
				if(groupid==0)
				{
					if(projectid.equals(""))
					{
						projectid = projectMapper.selectAll().get(0).getProjectid();
					}
					UpdateRate = projectMapper.selectUpdateRateByPrimaryKey(projectid);
				}
				else {
					UpdateRate = groupMapper.selectUpdateRateBygroupid(groupid);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}			 
			UpdateRate = (double) Math.round(UpdateRate * 10000) / 100;
			
			jObject.put("result", "success");
			jObject.put("UpdateRate", UpdateRate);
			jObject.put("unUpdateRate", 100 - UpdateRate);
						
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally
		{return jObject;}
	}
}
