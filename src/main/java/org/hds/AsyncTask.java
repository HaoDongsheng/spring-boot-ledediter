package org.hds;

import org.hds.service.IAdvMangerService;
import org.hds.service.IInfoListService;
import org.hds.service.impl.operateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
//@Async
public class AsyncTask {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IAdvMangerService advMangerSer;
	@Autowired
	IInfoListService InfoListSer;
	@Autowired
	operateLog operateLog;

//	@Scheduled(fixedRate = 20000)
//	public void test() {
//		System.out.println("[ " + "当前时间 : " + new Date() + " ]");
//	}

	@Scheduled(cron = "0 05 23 * * *")
	public void Delete2oldbyDay() {
		try {
			// System.out.println("[ " + "222当前时间 : " + new Date() + " ]");
			JSONObject jObjectinfo = advMangerSer.Deleteinfo2oldbyDay();
			operateLog.writeLog("", 0, "删除过期广告信息:" + jObjectinfo.toJSONString(), "/Deleteinfo2oldbyDay", logger, true);
		} catch (Exception e) {
			// System.out.println("[ " + "222当前时间 : " + new Date() + " ]");
		}
		try {
//			JSONObject jObjectlist = InfoListSer.DeleteList2oldbyDay();
//			operateLog.writeLog("", 0, "删除过期列表信息:" + jObjectlist.toJSONString(), "/DeleteList2oldbyDay", logger, true);
		} catch (Exception e) {
			// System.out.println("[ " + "222当前时间 : " + new Date() + " ]");
		}
	}

}
