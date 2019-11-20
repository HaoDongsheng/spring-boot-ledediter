package org.hds.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hds.mapper.logMapper;
import org.hds.mapper.userMapper;
import org.hds.model.log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class operateLog {

	@Autowired
	userMapper userMapper;
	@Autowired
	logMapper logMapper;

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

				int adminid = userMapper.selectByPrimaryName(adminName).getAdminid();
				log record = new log();
				record.setLogtime(d1.format(now));
				record.setFunctionname(functionName);
				record.setAdminid(adminid);
				record.setOperate(operateMessage);
				record.setOperatetype(type);
				logMapper.insert(record);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("===写日志失败:" + e.getMessage() + "===");
		}
	}

}
