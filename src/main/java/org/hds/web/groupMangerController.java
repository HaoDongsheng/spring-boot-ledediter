package org.hds.web;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IgroupMangerService;
import org.hds.service.impl.operateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class groupMangerController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IgroupMangerService groupMangerSer;
	@Autowired
	operateLog operateLog;
	JSONObject adminInfoJsonObject;

	@RequestMapping("/groupManger")
	public String groupManger(Model model, HttpServletRequest request) {
		try {
			return "groupManger";
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CreatGroup", method = RequestMethod.POST)
	public JSONObject CreatGroup(@RequestParam("grpname") String grpname, @RequestParam("projectid") String projectid,
			@RequestParam("packLength") int packLength, @RequestParam("grpwidth") int grpwidth,
			@RequestParam("grpheight") int grpheight, @RequestParam("grpbelong") int grpbelong,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = groupMangerSer.CreatGroup(grpname, projectid, packLength, grpwidth, grpheight,
					grpbelong, adminname);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 新建分组:" + grpname + " 返回结果:" + JObject.toJSONString() + "===",
					"/CreatGroup", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CreatGroup 新建分组:" + grpname + " 异常:" + e.getMessage() + "===",
					"/CreatGroup", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/EditGroup", method = RequestMethod.POST)
	public JSONObject EditGroup(@RequestParam("grpid") int grpid, @RequestParam("grpname") String grpname,
			@RequestParam("packLength") int packLength, @RequestParam("grpwidth") int grpwidth,
			@RequestParam("grpheight") int grpheight, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {

			JSONObject JObject = groupMangerSer.EditGroup(grpid, grpname, packLength, grpwidth, grpheight);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 编辑分组:" + grpname + " 返回结果:" + JObject.toJSONString() + "===", "/EditGroup",
					logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/EditGroup 编辑分组:" + grpname + " 异常:" + e.getMessage() + "===", "/EditGroup",
					logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/DeleteGroup", method = RequestMethod.POST)
	public JSONObject DeleteGroup(@RequestParam("grpid") int grpid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = groupMangerSer.DeleteGroup(grpid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 删除分组id:" + grpid + " 返回结果:" + JObject.toJSONString() + "===",
					"/DeleteGroup", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/DeleteGroup 删除分组id:" + grpid + " 异常:" + e.getMessage() + "===",
					"/DeleteGroup", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/GetProvinceList", method = RequestMethod.POST)
	public JSONObject GetProvinceList(@RequestParam("adminname") String adminname, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			// 获取跟目录
			/*
			 * String pathString=ResourceUtils.getURL("classpath:").getPath();
			 * logger.info("path:"+ pathString); File path = new
			 * File(URLDecoder.decode(pathString, "UTF-8")); if(!path.exists())
			 * {logger.error("path:"+path.getAbsolutePath());}
			 * 
			 * File file = new File(URLDecoder.decode(path.getAbsolutePath(),
			 * "UTF-8"),"static/Area.ini"); if(!file.exists())
			 * {logger.error("upload:"+file.getAbsolutePath());}
			 * 
			 * 
			 * org.ini4j.Wini ini = new org.ini4j.Wini(file);
			 */
			ClassPathResource classPathResource = new ClassPathResource("static/Area.ini");
			org.ini4j.Wini ini = new org.ini4j.Wini(classPathResource.getInputStream());
			String strProvinceList = ini.get("全国各省及直辖市", "名称列表");

			jsonObject.put("result", "success");
			jsonObject.put("ProvinceList", strProvinceList);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 获取省份 返回结果:" + jsonObject.toJSONString() + "===",
					"/GetProvinceList", logger, false);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/GetProvinceList 异常:" + e.getMessage() + "===",
					"/GetProvinceList", logger, false);
			jsonObject.put("result", "fail");
			jsonObject.put("resultMessage", e.toString());

			return jsonObject;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/GetCityList", method = RequestMethod.POST)
	public JSONObject GetCityList(@RequestParam("ProvinceName") String ProvinceName,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			// 获取跟目录
			/*
			 * File path = new
			 * File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),
			 * "UTF-8")); if(!path.exists()) {logger.error("path:"+path.getAbsolutePath());}
			 * 
			 * File file = new File(URLDecoder.decode(path.getAbsolutePath(),
			 * "UTF-8"),"static/Area.ini"); if(!file.exists())
			 * {logger.error("upload:"+file.getAbsolutePath());}
			 * 
			 * org.ini4j.Wini ini = new org.ini4j.Wini(file);
			 */
			ClassPathResource classPathResource = new ClassPathResource("static/Area.ini");
			org.ini4j.Wini ini = new org.ini4j.Wini(classPathResource.getInputStream());

			String strCityList = ini.get(ProvinceName, "名称列表");

			jsonObject.put("result", "success");
			jsonObject.put("CityList", strCityList);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 获取城市 返回结果:" + jsonObject.toJSONString() + "===",
					"/GetCityList", logger, false);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/GetCityList 异常:" + e.getMessage() + "===",
					"/GetCityList", logger, false);
			jsonObject.put("result", "fail");
			jsonObject.put("resultMessage", e.toString());

			return jsonObject;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/Set_parameter", method = RequestMethod.POST)
	public JSONObject Set_parameter(@RequestParam("parameter") String parameter,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject jsonObject = groupMangerSer.Set_parameter(parameter);

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 设置参数 返回结果:" + jsonObject.toJSONString() + "===",
					"/Set_parameter", logger, false);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/Set_parameter 异常:" + e.getMessage() + "===",
					"/Set_parameter", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/Get_parameter", method = RequestMethod.POST)
	public JSONObject Get_parameter(@RequestParam("parameter") String parameter,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject jsonObject = groupMangerSer.Get_parameter(parameter);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 获取参数 返回结果:" + jsonObject.toJSONString() + "===",
					"/Get_parameter", logger, false);
			return jsonObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/Get_parameter 异常:" + e.getMessage() + "===",
					"/Get_parameter", logger, false);
			return null;
		}
	}
}
