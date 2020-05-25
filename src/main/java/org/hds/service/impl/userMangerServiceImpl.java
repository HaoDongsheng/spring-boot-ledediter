package org.hds.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.hds.Encryption;
import org.hds.mapper.projectMapper;
import org.hds.mapper.userMapper;
import org.hds.model.project;
import org.hds.model.user;
import org.hds.service.IuserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class userMangerServiceImpl implements IuserMangerService {

	@Autowired
	userMapper adminMapper;
	@Autowired
	projectMapper projectMapper;

	@Override
	public JSONArray getProjectList(JSONObject adminInfoJsonObject) {
		try {
			JSONArray JsonArray = null;

			int isSuperuser = adminInfoJsonObject.getIntValue("issuperuser");
			if (isSuperuser == 0)// 普通用户
			{
				String projectid = adminInfoJsonObject.getString("projectid");
				project project = projectMapper.selectByPrimaryKey(projectid);
				List<project> projectlist = new ArrayList<project>();
				projectlist.add(project);

				JsonArray = JSONArray.parseArray(JSON.toJSONString(projectlist));
			} else// 超级用户
			{
				List<project> projectlist = projectMapper.selectAll();
				JsonArray = JSONArray.parseArray(JSON.toJSONString(projectlist));
			}

			return JsonArray;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONArray getUserList(String projectid, JSONObject adminInfoJsonObject) {
		try {
			JSONArray JsonArray = null;

			int adminid = adminInfoJsonObject.getIntValue("adminid");
			int adminlevel = adminInfoJsonObject.getIntValue("adminlevel");
			List<user> adminlist = new ArrayList<user>();
			if (adminlevel < 2) {
				adminlist = adminMapper.selectByprojectid(projectid);
			} else {
				adminlist.add(adminMapper.selectByPrimaryKey(adminid));
			}
			// List<user> adminlist = adminMapper.selectByParentid(adminid);
			JsonArray = JSONArray.parseArray(JSON.toJSONString(adminlist));

			return JsonArray;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONObject CreatUser(String adminname, String adminpwd, int adminstatus, String expdate,
			String adminpermission, String admingrps, String projectid, int inherit, int parentid, int adminlevel) {
		JSONObject jObject = new JSONObject();
		try {
			int count = adminMapper.selectCountByName(adminname);
			if (count > 0) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "用户名" + adminname + "已存在!");
			} else {
				String hashAlgorithmNameString = "MD5";
				Object credentials = adminpwd;
				Object salt = ByteSource.Util.bytes(adminname);
				int hashIterations = 1024;
				Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);
				String codeString = Encryption.encode(adminpwd);

				user record = new user();
				// record.setAdminid(adminid);
				record.setAdminname(adminname);
				record.setAdminpwd(result.toString());
				record.setAdminstatus(adminstatus);
				record.setExpdate(expdate);
				record.setAdminpermission(adminpermission);
				record.setGroupids(admingrps);
				record.setProjectid(projectid);
				record.setinherit(inherit);
				record.setAdmincount(0);
				if (inherit == 0) {
					record.setAdminlevel(2);
				} else {
					record.setAdminlevel(1);
				}
				record.setDelindex(0);
				record.setGrpcount(0);
				record.setIssuperuser(0);
				record.setParentid(parentid);
				record.setAdminRemarks(codeString);
				int rowCount = adminMapper.insert(record);

				if (rowCount > 0) {
					int adminid = record.getAdminid();

					jObject.put("result", "success");
					jObject.put("adminid", adminid);
					jObject.put("adminlevel", adminlevel);
				} else {
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
	public JSONObject EditUser(int adminid, String adminname, String adminpwd, int adminstatus, String expdate,
			String adminpermission, String admingrps, int inherit) {
		JSONObject jObject = new JSONObject();
		try {
			int count = adminMapper.selectCountByNameid(adminname, adminid);
			if (count > 0) {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "用户名" + adminname + "已存在!");
			} else {

				boolean ischangePwd = false;
				;
				user itemUser = adminMapper.selectByPrimaryKey(adminid);
				if (!itemUser.getAdminpwd().equals(adminpwd)) {
					ischangePwd = true;
				}

				user record = new user();
				record.setAdminid(adminid);
				record.setAdminname(adminname);
				if (ischangePwd) {
					String hashAlgorithmNameString = "MD5";
					Object credentials = adminpwd;
					Object salt = ByteSource.Util.bytes(adminname);
					int hashIterations = 1024;
					Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);
					String codeString = Encryption.encode(adminpwd);
					record.setAdminpwd(result.toString());
					record.setAdminRemarks(codeString);
				}

				record.setAdminstatus(adminstatus);
				record.setExpdate(expdate);
				record.setAdminpermission(adminpermission);
				record.setGroupids(admingrps);
				record.setinherit(inherit);

				int rowCount = adminMapper.updateByPrimaryKeySelective(record);

				if (rowCount > 0) {
					jObject.put("result", "success");
				} else {
					jObject.put("result", "fail");
					jObject.put("resultMessage", "修改用户表失败");
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
	public JSONObject DeleteUser(int adminid) {
		JSONObject jObject = new JSONObject();
		try {
			int rowCount = adminMapper.deleteByPrimaryKey(adminid);

			if (rowCount > 0) {
				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "删除用户失败");
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@Override
	public JSONObject ChangPassword(int adminid, String oldPwd, String newPwd) {
		JSONObject jObject = new JSONObject();
		try {
			user user = adminMapper.selectByPrimaryKey(adminid);

			String hashAlgorithmNameString = "MD5";
			Object credentials = oldPwd;
			Object salt = ByteSource.Util.bytes(user.getAdminname());
			int hashIterations = 1024;
			Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);

			if (result.toString().equals(user.getAdminpwd())) {
				credentials = newPwd;
				result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);
				String codeString = Encryption.encode(newPwd);

				user.setAdminpwd(result.toString());
				user.setAdminRemarks(codeString);

				adminMapper.updateByPrimaryKeySelective(user);

				jObject.put("result", "success");
			} else {
				jObject.put("result", "fail");
				jObject.put("resultMessage", "旧密码不正确!");
			}

			return jObject;
		} catch (Exception e) {
			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}
}
