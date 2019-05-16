package org.hds.service.impl;

import java.util.List;

import org.hds.mapper.projectMapper;
import org.hds.mapper.terminalMapper;
import org.hds.model.terminal;
import org.hds.service.IterminalMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class terminalMangerServiceImpl implements IterminalMangerService {
			
	@Autowired
	terminalMapper terminalMapper;
	
	@Override
	public JSONObject getTerminalsbypageNum(int pageNum, int pageSize)
	{
		JSONObject jObject=new JSONObject();
		try {
			int terminalCount = terminalMapper.selectCount();
			int startoffset = (pageNum - 1) * pageSize;
			List<terminal> terminalList = terminalMapper.SelectTerminalsBypageNum(startoffset, pageSize);
			JSONArray jsonArray=new JSONArray();
			if(terminalList!=null && terminalList.size()>0)
			{
				for(int i=0;i<terminalList.size();i++)
				{
					JSONObject jterminal=new JSONObject();
					terminal terminal=terminalList.get(i);
					jterminal.put("name", terminal.getName());
					jterminal.put("groupid", terminal.getGroupid());
					jterminal.put("LED_ID", terminal.getLedId());
					jterminal.put("SIMNo", terminal.getSimno());
					jterminal.put("DtuKey", terminal.getDtukey());
					
					jsonArray.add(jterminal);
				}
			}
			           
			jObject.put("total", terminalCount);
			jObject.put("rows", jsonArray);
			return jObject;
		} catch (Exception e) {			
			return null;
		}
	}
	
	@Override
	public JSONObject getTerminalsbyprojectid(int pageNum, int pageSize, String projectid)
	{
		JSONObject jObject=new JSONObject();
		try {
			int terminalCount = terminalMapper.selectCountbyprojectid(projectid);
			int startoffset = (pageNum - 1) * pageSize;
			List<terminal> terminalList = terminalMapper.SelectTerminalsByprojectid(startoffset, pageSize,projectid);
			JSONArray jsonArray=new JSONArray();
			if(terminalList!=null && terminalList.size()>0)
			{
				for(int i=0;i<terminalList.size();i++)
				{
					JSONObject jterminal=new JSONObject();
					terminal terminal=terminalList.get(i);
					jterminal.put("name", terminal.getName());
					jterminal.put("groupid", terminal.getGroupid());
					jterminal.put("LED_ID", terminal.getLedId());
					jterminal.put("SIMNo", terminal.getSimno());
					jterminal.put("DtuKey", terminal.getDtukey());
					
					jsonArray.add(jterminal);
				}
			}
			           
			jObject.put("total", terminalCount);
			jObject.put("rows", jsonArray);
			return jObject;
		} catch (Exception e) {			
			return null;
		}
	}
}
