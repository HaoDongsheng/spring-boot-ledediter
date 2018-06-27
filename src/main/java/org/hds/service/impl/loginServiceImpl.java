package org.hds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hds.service.IloginService;
import org.hds.model.admin;
import org.hds.mapper.adminMapper;

import com.alibaba.fastjson.JSONObject;

@Service
public class loginServiceImpl implements IloginService {
	@Autowired
	adminMapper adminMapper;
	@Override
	public JSONObject login(String adminName, String adminPwd) {
		admin admin = adminMapper.selectByPrimaryName(adminName);
		JSONObject jObject=new JSONObject();
		if(admin!=null)
		{		
			if(admin.getAdminpwd().equals(adminPwd))
			{
				jObject.put("result", "success");
				jObject.put("adminID", admin.getAdminid());
				jObject.put("adminName", adminName);
				jObject.put("adminPermission", admin.getAdminpermission());
				jObject.put("adminGrps", admin.getAdmingrps());
							
			}
			else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "用户名密码不匹配");
			}
		}
		else {
			jObject.put("result", "fail");
			jObject.put("resultMessage", "用户名不存在");
		}
		return jObject;
	}
	
}
