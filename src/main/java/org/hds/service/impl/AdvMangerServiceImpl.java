package org.hds.service.impl;

import java.util.List;

import org.hds.service.IAdvMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hds.model.advertisement;
import org.hds.mapper.advertisementMapper;
import org.hds.model.item;
import org.hds.mapper.itemMapper;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.PreparedStatement.ParseInfo;

@Service
public class AdvMangerServiceImpl implements IAdvMangerService{
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	itemMapper itemMapper;
	@Override
	public JSONArray getadvlist()
	{
		List<advertisement> advlist=advertisementMapper.selectAll();
		JSONArray JSONArray =new JSONArray();
		if(advlist!=null && advlist.size()>0)
		{	
			for(int i=0;i<advlist.size();i++)
			{
				JSONObject jitem=new JSONObject();
				jitem.put("id", advlist.get(i).getId());
				jitem.put("advname", advlist.get(i).getAdvname());
				
				JSONArray.add(jitem);
			}
		}
		return JSONArray;
	}
	
	@Override
	public JSONObject Creatinfo(String infoName)
	{
		JSONObject jObject=new JSONObject();
		try
		{
			if(advertisementMapper.selectidByName(infoName)==null)
			{
				advertisement advertisement=new advertisement();
				advertisement.setAdvname(infoName);
				advertisementMapper.insert(advertisement);
				
				jObject.put("result", "success");
				jObject.put("infoID", advertisementMapper.selectidByName(infoName));
				jObject.put("infoName", infoName);
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "广告名称:"+infoName+"已存在!");
			}
		}
		catch(Exception e){
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally{return jObject;}
	}
	
	@Override
	public JSONObject DeleteInfobyid(int infoid)
	{
		JSONObject jObject=new JSONObject();
		try
		{
			advertisementMapper.deleteByPrimaryKey(infoid);
				
			jObject.put("result", "success");
			jObject.put("infoID", infoid);			
		}
		catch(Exception e){
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally{return jObject;}
	}
	@Override
	public JSONObject SaveItem(int infoid,JSONObject jsoninfo)
	{
		JSONObject jObject=new JSONObject();
		try
		{		
			itemMapper.deleteByadid(infoid);
			for(String dataKey : jsoninfo.keySet())   {  
				JSONArray jArrpage= jsoninfo.getJSONArray(dataKey);
				for(int i=0;i<jArrpage.size();i++)
				{
					JSONObject jitem=jArrpage.getJSONObject(i);
					item item=new item();
					item.setAdid(infoid);
					item.setPageid(Integer.parseInt(dataKey));
					item.setItemid(Integer.parseInt(jitem.getString("name").substring(4)));
					item.setItemtype(jitem.getInteger("type"));
					item.setItemleft(jitem.getInteger("left"));
					item.setItemtop(jitem.getInteger("top"));
					item.setItemwidth(jitem.getInteger("width"));
					item.setItemheight(jitem.getInteger("height"));
					item.setItembackcolor(jitem.getString("backcolor"));
					item.setDelindex(0);
					
					item.setItemcontext(jitem.getJSONArray("pt").toString());
					itemMapper.insert(item);
				}
			} 

			jObject.put("result", "success");
			jObject.put("infoID", infoid);			
		}
		catch(Exception e){
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally{return jObject;}
	}
	
	@Override
	public JSONObject GetItembyid(int infoid)
	{
		JSONObject jObject=new JSONObject();
		try
		{
			List<item> itemlist = itemMapper.selectByadid(infoid);
				
			 JSONArray jsonArray = new JSONArray();
	         if (itemlist!=null && !itemlist.isEmpty()) {		            
		        for (item objitem : itemlist) {
		            jsonArray.add(objitem);
		        }
	         }
		        
			jObject.put("result", "success");
			jObject.put("itemlist", jsonArray);			
		}
		catch(Exception e){
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());
		}
		finally{return jObject;}
	}
}
