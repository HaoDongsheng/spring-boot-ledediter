package org.hds.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.hds.mapper.userMapper;
import org.hds.model.user;
import org.hds.service.IloginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class loginServiceImpl implements IloginService {
	@Autowired
	userMapper adminMapper;

	@Override
	public JSONObject login(String adminName, String adminPwd) {

		user admin = adminMapper.selectByPrimaryName(adminName);
		JSONObject jObject = new JSONObject();

		String hashAlgorithmNameString = "MD5";
		Object credentials = adminPwd;
		Object salt = ByteSource.Util.bytes(adminName);
//		Object credentials="123456";
//		Object salt=ByteSource.Util.bytes("user");
		int hashIterations = 1024;

		Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);
		System.out.println(result);

		String msg = "success";
		// 1、获取Subject实例对象
		Subject currentUser = SecurityUtils.getSubject();

//	        // 2、判断当前用户是否登录
//	        if (currentUser.isAuthenticated() == false) {
		//
//	        }

		// 3、将用户名和密码封装到UsernamePasswordToken
		UsernamePasswordToken token = new UsernamePasswordToken(adminName, result.toString());

		// 4、认证
		try {
			currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
			Session session = currentUser.getSession();
			session.setAttribute("userName", adminName);
			SecurityUtils.getSubject().getSession().setTimeout(-1000L);
			// return "/index";
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			msg = "UnknownAccountException -- > 账号不存在：";
		} catch (IncorrectCredentialsException e) {
			msg = "IncorrectCredentialsException -- > 密码不正确：";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			msg = "用户验证失败";
		}
		if (admin.getIssuperuser() == 0) {
			int status = admin.getAdminstatus();
			if (status == 1) {
				msg = "用户停用状态,不能登录,请联系管理员!";
			}
			String Expdate = admin.getExpdate();
			if (Expdate != null && Expdate != "") {
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				try {
					if (d1.parse(Expdate).compareTo(now) < 0) {
						msg = "用户过期,不能登录,请联系管理员!";
					}
				} catch (ParseException ex) {
				}
			}
		}
		jObject.put("result", msg);
		if (msg.equals("success")) {
			admin.setAdminpwd("");
			jObject.put("adminInfo", admin);
		} else {
			jObject.put("resultMessage", "用户名或密码不正确");
		}

		return jObject;
	}

}
