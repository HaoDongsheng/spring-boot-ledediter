package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hds.NettyClient;
import org.hds.logmapper.llogMapper;
import org.hds.mapper.userMapper;
import org.hds.model.llog;
import org.hds.model.user;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class operateLog {

	@Autowired
	userMapper userMapper;
	@Autowired
	llogMapper llogMapper;

	public void writeLog(String adminName, int type, String operateMessage, String functionName, Logger logger,
			boolean isDb) {
		try {
			if (type == 0) {
				logger.info(operateMessage);
			} else {
				logger.error(operateMessage);
			}
			if (isDb) {
				Date now = new Date();
				SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				user user = userMapper.selectByPrimaryName(adminName);
				int adminid = user == null ? 0 : user.getAdminid();
				llog record = new llog();
				record.setLogtime(d1.format(now));
				record.setFunctionname(functionName);
				record.setAdminid(adminid);
				record.setOperate(operateMessage);
				record.setOperatetype(type);
				llogMapper.insert(record);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			JSONObject jObject = new JSONObject();
			jObject.put("command", "log");
			jObject.put("sendTime", sdf.format(new Date()));
			jObject.put("context", operateMessage);

			NettyClient.sendMsg(jObject.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("===写日志失败:" + e.getMessage() + "===");
		}
	}

}
