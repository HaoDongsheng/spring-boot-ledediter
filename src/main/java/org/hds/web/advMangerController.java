package org.hds.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hds.Graphics.GifDecoder;
import org.hds.mapper.groupMapper;
import org.hds.service.IAdvMangerService;
import org.hds.service.impl.operateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class advMangerController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IAdvMangerService advMangerSer;
	@Autowired
	groupMapper taxigroupMapper;
	@Autowired
	operateLog operateLog;
	@Autowired
	ResourceLoader loader;

	@RequestMapping("/advManger")
	public String advManger(Model model, HttpServletRequest request) {
		try {
			return "advManger";
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getadvList", method = RequestMethod.POST) public
	 * JSONArray getadvList(HttpServletRequest request){ try {
	 * if(adminInfoJsonObject==null) {adminInfoJsonObject =
	 * (JSONObject)request.getSession().getAttribute("adminInfo");} JSONArray
	 * JsonArray = advMangerSer.getadvlist(adminInfoJsonObject);
	 * logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+
	 * "/getadvList==="); return JsonArray; } catch(Exception e){
	 * logger.error("===用户:"+adminInfoJsonObject.getString("adminname")
	 * +"/getadvList 异常:"+e.getMessage()+"==="); return null; } }
	 */
	@ResponseBody
	@RequestMapping(value = "/getadvEditListbyGrpid", method = RequestMethod.POST)
	public JSONArray getadvListbyGrpid(@RequestParam("Grpid") int Grpid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONArray JsonArray = advMangerSer.getadvEditListbyGrpid(Grpid);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getadvEditListbyGrpid===",
					"/getadvEditListbyGrpid", logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getadvEditListbyGrpid 异常:" + e.getMessage() + "===",
					"/getadvEditListbyGrpid", logger, false);
			return null;
		}
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getbasemaplist", method = RequestMethod.POST)
	 * public JSONArray getbasemaplist(HttpServletRequest request){ try {
	 * if(adminInfoJsonObject==null) {adminInfoJsonObject =
	 * (JSONObject)request.getSession().getAttribute("adminInfo");} JSONArray
	 * JsonArray = advMangerSer.getbasemaplist();
	 * logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+
	 * "/getbasemaplist==="); return JsonArray; } catch(Exception e){
	 * logger.error("===用户:"+adminInfoJsonObject.getString("adminname")
	 * +"/getbasemaplist 异常:"+e.getMessage()+"==="); return null; } }
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getbasemaptypebyprojectid", method =
	 * RequestMethod.POST) public JSONArray
	 * getbasemaptypebyprojectid(@RequestParam("groupid") int
	 * groupid,@RequestParam("imgtype") int imgtype,HttpServletRequest request){ try
	 * { if(adminInfoJsonObject==null) {adminInfoJsonObject =
	 * (JSONObject)request.getSession().getAttribute("adminInfo");}
	 * 
	 * String projectid=""; if(adminInfoJsonObject.getString("projectid")!=null &&
	 * !adminInfoJsonObject.getString("projectid").equals("")) {
	 * projectid=adminInfoJsonObject.getString("projectid"); } else {
	 * projectid=taxigroupMapper.selectByPrimaryKey(groupid).getProjectid(); }
	 * 
	 * JSONArray JsonArray = advMangerSer.getimgclassifybyprojectid(projectid,
	 * imgtype); logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+
	 * "/getbasemaptypebyprojectid=="); return JsonArray; } catch(Exception e){
	 * logger.error("===用户:"+adminInfoJsonObject.getString("adminname")
	 * +"/getbasemaptypebyprojectid 异常:"+e.getMessage()+"==="); return null; } }
	 */
	@ResponseBody
	@RequestMapping(value = "/getbasemapbyprojectid", method = RequestMethod.POST)
	public JSONObject getbasemapbyprojectid(@RequestParam("groupid") int groupid, @RequestParam("imgtype") int imgtype,
			@RequestParam("classify") String classify, @RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			String projectid = taxigroupMapper.selectByPrimaryKey(groupid).getProjectid();

			JSONObject jObject = advMangerSer.getimgbyprojectid(projectid, imgtype, classify, pageNumber, pageSize);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getbasemapbyprojectid===",
					"/getbasemapbyprojectid", logger, false);
			return jObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/getbasemapbyprojectid 异常:" + e.getMessage() + "===");
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getbasemapbyprojectid 异常:" + e.getMessage() + "===",
					"/getbasemapbyprojectid", logger, false);
			return null;
		}
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getbasemapbyid", method = RequestMethod.POST)
	 * public JSONObject getbasemapbyid(@RequestParam("basemapid") int
	 * basemapid,HttpServletRequest request){ try { if(adminInfoJsonObject==null)
	 * {adminInfoJsonObject =
	 * (JSONObject)request.getSession().getAttribute("adminInfo");} JSONObject
	 * JObject = advMangerSer.getbasemapbyid(basemapid);
	 * logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+
	 * "/getbasemapbyid==="); return JObject; } catch(Exception e){
	 * logger.error("===用户:"+adminInfoJsonObject.getString("adminname")
	 * +"/getbasemapbyid 异常:"+e.getMessage()+"==="); return null; } }
	 */
	@ResponseBody
	@RequestMapping(value = "/deletebasemapbyid", method = RequestMethod.POST)
	public int deletebasemapbyid(@RequestParam("basemapid") int basemapid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			int result = advMangerSer.deletebasemapbyid(basemapid);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/deletebasemapbyid===", "/deletebasemapbyid",
					logger, true);
			return result;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/deletebasemapbyid 异常:" + e.getMessage() + "===");
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/deletebasemapbyid 异常:" + e.getMessage() + "===",
					"/deletebasemapbyid", logger, true);
			return 1;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletebasemapbyids", method = RequestMethod.POST)
	public JSONObject deletebasemapbyids(@RequestParam("basemapids") String basemapids,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		JSONObject jObject = new JSONObject();
		try {
			String[] basemapidArrayStrings = basemapids.split(",");
			for (int i = 0; i < basemapidArrayStrings.length; i++) {
				int basemapid = Integer.parseInt(basemapidArrayStrings[i]);
				int result = advMangerSer.deletebasemapbyid(basemapid);
			}
			jObject.put("result", "success");

			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/deletebasemapbyids===", "/deletebasemapbyids",
					logger, true);
			return jObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/deletebasemapbyids 异常:" + e.getMessage() + "===");
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/deletebasemapbyids 异常:" + e.getMessage() + "===",
					"/deletebasemapbyids", logger, true);

			jObject.put("result", "fail");
			jObject.put("resultMessage", e.toString());

			return jObject;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatebasemapclassify", method = RequestMethod.POST)
	public int updatebasemapclassify(@RequestParam("groupid") Integer groupid, @RequestParam("imgtype") Integer imgtype,
			@RequestParam("oldbasemapclassify") String oldbasemapclassify,
			@RequestParam("newbasemapclassify") String newbasemapclassify, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			String projectid = taxigroupMapper.selectByPrimaryKey(groupid).getProjectid();
			int result = advMangerSer.updatebasemapclassify(projectid, imgtype, oldbasemapclassify, newbasemapclassify);
			logger.info("===用户:" + adminname + "/updatebasemapclassify===");
			return result;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/updatebasemapclassify 异常:" + e.getMessage() + "===");
			return 1;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/GetImgbyGif", method = RequestMethod.POST)
	public JSONObject GetImgbyGif(@RequestParam("gifString") String gifString, int w, int h,
			HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.GetImgbyGif(gifString, w, h);
			logger.info("=== 获取gif数据 GetImgbyGif 返回结果:" + JObject.toJSONString() + "===");
			return JObject;
		} catch (Exception e) {
			logger.error("=== 获取gif数据 返回结果 GetImgbyGif 异常:" + e.getMessage() + "===");
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CreatInfo", method = RequestMethod.POST)
	public JSONObject CreatInfo(@RequestParam("infoName") String infoName, @RequestParam("groupid") int groupid,
			@RequestParam("lifeAct") String lifeAct, @RequestParam("lifeDie") String lifeDie,
			@RequestParam("BackgroundStyle") String BackgroundStyle, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.Creatinfo(infoName, groupid, lifeAct, lifeDie, BackgroundStyle, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 新建广告:" + infoName + " 返回结果:" + JObject.toJSONString() + "===",
					"/CreatInfo", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CreatInfo 新建广告:" + infoName + "异常:" + e.getMessage() + "===", "/CreatInfo",
					logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/UpdateInfo", method = RequestMethod.POST)
	public JSONObject UpdateInfo(@RequestParam("infoid") String infoid, @RequestParam("pubid") int pubid,
			@RequestParam("infoName") String infoName, @RequestParam("groupid") int groupid,
			@RequestParam("lifeAct") String lifeAct, @RequestParam("lifeDie") String lifeDie,
			@RequestParam("BackgroundStyle") String BackgroundStyle, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = new JSONObject();
			if (pubid != 0) {
				advMangerSer.DeleteinfoCodebyid(infoid, adminid);
				JSONObject JdelObject = advMangerSer.DeleteInfobyid(infoid, adminid);
				if (JdelObject.getString("result") == "success") {
					JSONObject JcreatObject = advMangerSer.Creatinfo(infoName, groupid, lifeAct, lifeDie,
							BackgroundStyle, adminid);
					String newinfoid = JcreatObject.getString("infoID");
					JSONObject JciObject = advMangerSer.CopyItem(infoid, newinfoid);
					if (JciObject.getString("result") == "success") {
						JObject.put("result", "success");
						JObject.put("returnid", newinfoid);
					} else {
						JObject.put("result", "fail");
						JObject.put("resultMessage", "复制条目失败");
					}

				} else {
					JObject.put("result", "fail");
					JObject.put("resultMessage", "删除广告败");
				}
			} else {
				JSONObject jinfo = new JSONObject();
				jinfo.put("advname", infoName);
				jinfo.put("lifeAct", lifeAct);
				jinfo.put("lifeDie", lifeDie);
				jinfo.put("groupid", groupid);
				jinfo.put("pubid", pubid);
				jinfo.put("BackgroundStyle", BackgroundStyle);

				int result = advMangerSer.Updatainfo(infoid, jinfo);
				if (result == 0) {
					JObject.put("result", "success");
				} else if (result == 2) {
					JObject.put("result", "fail");
					JObject.put("resultMessage", "广告名称已存在!");
				} else {
					JObject.put("result", "fail");
					JObject.put("resultMessage", "编码错误");
				}
			}

			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 修改广告:" + infoName + " 返回结果:" + JObject.toJSONString() + "===",
					"/UpdateInfo", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/UpdateInfo 修改广告:" + infoName + "异常:" + e.getMessage() + "===",
					"/UpdateInfo", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/CopyInfo", method = RequestMethod.POST)
	public JSONObject CopyInfo(@RequestParam("infoName") String infoName, @RequestParam("groupid") int groupid,
			@RequestParam("lifeAct") String lifeAct, @RequestParam("lifeDie") String lifeDie,
			@RequestParam("BackgroundStyle") String BackgroundStyle, @RequestParam("itemlist") String itemlist,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.CopyInfo(infoName, groupid, lifeAct, lifeDie, BackgroundStyle, itemlist);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 复制广告:" + infoName + " 返回结果:" + JObject.toJSONString() + "===", "/CopyInfo",
					logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/CopyInfo 复制广告:" + infoName + "异常:" + e.getMessage() + "===", "/CopyInfo",
					logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/DeleteInfobyid", method = RequestMethod.POST)
	public JSONObject DeleteInfobyid(@RequestParam("infoid") String infoid, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.DeleteInfobyid(infoid, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 删除广告id:" + infoid + " 返回结果:" + JObject.toJSONString() + "===",
					"/DeleteInfobyid", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/DeleteInfobyid 删除广告id:" + infoid + "异常:" + e.getMessage() + "===",
					"/DeleteInfobyid", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getSensitive", method = RequestMethod.POST)
	public JSONObject getSensitive(@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.getSensitive(adminname);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 获取敏感词汇 返回结果:" + JObject.toJSONString() + "===",
					"/getSensitive", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/getSensitive 获取敏感词汇异常:" + e.getMessage() + "===",
					"/getSensitive", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addSensitive", method = RequestMethod.POST)
	public JSONObject addSensitive(@RequestParam("sensitiveString") String sensitiveString,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.addSensitive(sensitiveString, adminname);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 添加敏感词汇 返回结果:" + JObject.toJSONString() + "===",
					"/addSensitive", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/addSensitive 添加敏感词汇异常:" + e.getMessage() + "===",
					"/addSensitive", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteSensitive", method = RequestMethod.POST)
	public JSONObject deleteSensitive(@RequestParam("sensitiveIdlist") String sensitiveIdlist,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.deleteSensitive(sensitiveIdlist, adminname);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 删除敏感词汇 返回结果:" + JObject.toJSONString() + "===",
					"/deleteSensitive", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/deleteSensitive 删除敏感词汇异常:" + e.getMessage() + "===", "/deleteSensitive",
					logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/AuditInfobyid", method = RequestMethod.POST)
	public JSONObject AuditInfobyid(@RequestParam("infoid") String infoid, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.AuditInfobyid(infoid, adminid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 送审核广告id:" + infoid + " 返回结果:" + JObject.toJSONString() + "===",
					"/AuditInfobyid", logger, true);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/AuditInfobyid 送审核广告id:" + infoid + "异常:" + e.getMessage() + "===",
					"/AuditInfobyid", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getPublishInfobyid", method = RequestMethod.POST)
	public JSONObject getPublishInfobyid(@RequestParam("infoid") String infoid,
			@RequestParam("infodata") String infodata, @RequestParam("arritem") String arritem,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject jinfo = JSONObject.parseObject(infodata);
			JSONObject jsoninfo = JSONObject.parseObject(arritem);

			// logger.info("===用户:" + adminname + " 获取编码广告id:" + infoid + " 内容集合结果:" +
			// jsoninfo.toJSONString() + "===");

			JSONObject JObject = advMangerSer.getbyteslistbyTemp(infoid, jinfo, jsoninfo);

			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 获取编码广告id:" + infoid + " 返回结果:" + JObject.toJSONString() + "===",
					"/getPublishInfobyid", logger, false);

			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/getPublishInfobyid 获取编码广告id:" + infoid + "异常:" + e.getMessage() + "===",
					"/getPublishInfobyid", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/SaveItem", method = RequestMethod.POST)
	public JSONObject SaveItem(@RequestParam("infoid") String infoid, @RequestParam("infodata") String infodata,
			@RequestParam("arritem") String arritem, @RequestParam("adminid") int adminid,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		try {
			JSONObject jinfo = JSONObject.parseObject(infodata);

			if (jinfo.getIntValue("pubid") != 0) {
				JSONObject JObject = new JSONObject();
				// 删除广告编码数据，排期数据
				advMangerSer.DeleteinfoCodebyid(infoid, adminid);

				JSONObject JdelObject = advMangerSer.DeleteInfobyid(infoid, adminid);
				if (JdelObject.getString("result") == "success") {
					JSONObject JcreatObject = advMangerSer.Creatinfo(jinfo.getString("advname"),
							jinfo.getIntValue("groupid"), jinfo.getString("lifeAct"), jinfo.getString("lifeDie"),
							jinfo.getString("BackgroundStyle"), adminid);
					String newinfoid = JcreatObject.getString("infoID");

					// JSONObject JciObject =advMangerSer.CopyItem(infoid,newinfoid);

					JSONObject jsoninfo = JSONObject.parseObject(arritem);
					JSONObject JciObject = advMangerSer.SaveItem(newinfoid, jsoninfo);

					if (JciObject.getString("result") == "success") {
						JObject.put("result", "success");
						JObject.put("returnid", newinfoid);
					} else {
						JObject.put("result", "fail");
						JObject.put("resultMessage", "复制条目失败");
					}

				} else {
					JObject.put("result", "fail");
					JObject.put("resultMessage", "删除广告败");
				}
				return JObject;
			} else {
				int result = advMangerSer.Updatainfo(infoid, jinfo);
				if (result == 0) {
					JSONObject jsoninfo = JSONObject.parseObject(arritem);
					JSONObject JObject = advMangerSer.SaveItem(infoid, jsoninfo);

					operateLog.writeLog(adminname, 0,
							"===用户:" + adminname + " 广告id:" + infoid + "保存显示项返回结果:" + JObject.toJSONString() + "===",
							"/SaveItem", logger, true);
					return JObject;
				} else {
					operateLog.writeLog(adminname, 0, "===用户:" + adminname + " 广告id:" + infoid + "保存显示项失败===",
							"/SaveItem", logger, true);
					return null;
				}
			}
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/SaveItem 广告id:" + infoid + "保存显示项异常:" + e.getMessage() + "===",
					"/SaveItem", logger, true);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/GetItem", method = RequestMethod.POST)
	public JSONObject GetItem(@RequestParam("infoid") String infoid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONObject JObject = advMangerSer.GetItembyid(infoid);
			operateLog.writeLog(adminname, 0,
					"===用户:" + adminname + " 广告id:" + infoid + "获取显示项返回结果:" + JObject.toJSONString() + "===",
					"/GetItem", logger, false);
			return JObject;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/GetItem 广告id:" + infoid + "获取显示项异常:" + e.getMessage() + "===", "/GetItem",
					logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/GetImg2DBbyBase64", method = RequestMethod.POST)
	public JSONObject GetImg2DBbyBase64(@RequestParam("groupid") int groupid, @RequestParam("imgtype") int imgtype,
			@RequestParam("classify") String classify,
			@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {
		JSONObject JObject = new JSONObject();
		try {
			String contentType = file.getContentType();
			String fileName = file.getOriginalFilename();
			FileInputStream inputStream = (FileInputStream) file.getInputStream();

			byte[] bytes = new byte[inputStream.available()];
			int length = inputStream.read(bytes);
			Base64.Encoder encoder = Base64.getEncoder();
			String base64Img = encoder.encodeToString(bytes);

			int gifFramesCount = 0;
			int giftimelength = 0;
			if (contentType.toLowerCase().equals("image/gif")) {
				GifDecoder decoder = new GifDecoder();
				FileInputStream imggifputStream = (FileInputStream) file.getInputStream();
				int status = decoder.read(imggifputStream);

				if (status != 0) {
					JObject.put("result", "fail");
					JObject.put("resultMessage", "gif文件格式不符合要求");
					return JObject;
				}
				gifFramesCount = decoder.getFrameCount(); // 得到frame的个数
				/*
				 * for(int n=0;n<gifFramesCount;n++) { int delay=decoder.getDelay(n);
				 * //System.out.println(delay); giftimelength += delay; }
				 */
				giftimelength = decoder.gettotaldelay();
			}

			FileInputStream imginputStream = (FileInputStream) file.getInputStream();
			BufferedImage sourceImg = ImageIO.read(imginputStream);

			int width = sourceImg.getWidth();
			int height = sourceImg.getHeight();
			/*
			 * List<Integer> colorlist=new ArrayList<Integer>(); for(int i=0;i<width;i++){
			 * for(int j=0;j<height;j++){ int RGB=sourceImg.getRGB(i,j);
			 * if(!colorlist.contains(RGB)) {colorlist.add(RGB);}
			 * if(colorlist.size()>=3){break;} } if(colorlist.size()>=3){break;} } int
			 * imgindex=0; if(colorlist.size()<3) {imgindex=2;} else {imgindex=3;}
			 */
			int imgindex = 3;
			float gtl = (float) Math.floor((float) giftimelength / 1000);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("imgtype", imgindex);
			jsonObject.put("imgwidth", width);
			jsonObject.put("imgheight", height);
			jsonObject.put("gifFramesCount", gifFramesCount);
			jsonObject.put("giftimelength", gtl);

			String projectid = taxigroupMapper.selectByPrimaryKey(groupid).getProjectid();

			int basemapid = advMangerSer.SaveBasemap(fileName, projectid, imgtype, classify, contentType,
					jsonObject.toJSONString(), base64Img);
			if (basemapid != 0) {
				JObject.put("result", "success");
				JObject.put("basemapid", basemapid);
				JObject.put("fileName", fileName);
				JObject.put("contentType", contentType);
				JObject.put("basemapstyle", jsonObject.toJSONString());
				JObject.put("imgBase64String", base64Img);
			} else {
				JObject.put("result", "fail");
				JObject.put("resultMessage", "底图存储失败");
			}
			logger.info("===用户:" + adminname + "获取图片返回结果:" + JObject.toJSONString() + "===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/GetImg2DBbyBase64 异常:" + e.getMessage() + "===");
			JObject.put("result", "fail");
			JObject.put("resultMessage", e.getMessage());
			return JObject;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/uploadvideo", method = RequestMethod.POST)
	public JSONObject uploadvideo(@RequestParam("groupid") int groupid, @RequestParam("imgtype") int imgtype,
			@RequestParam("duration") String duration, @RequestParam("classify") String classify,
			@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
			@RequestParam("adminname") String adminname, HttpServletRequest request) {

		JSONObject JObject = new JSONObject();
		try {
			String contentType = file.getContentType();
			String fileName = file.getOriginalFilename();

			File path = new File(java.net.URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "utf-8"));
			// 如果上传目录为/static/images/upload/，则可以如下获取：
			File upload = new File(path.getAbsolutePath(), "static/upload_video/");

			if (!upload.exists()) {
				upload.mkdirs();
			}

			// 使用输入流读取前台的file文件
			InputStream is = file.getInputStream();

			/*
			 * // in.available()返回文件的字节长度 byte[] bytes = new byte[is.available()]; //
			 * 将文件中的内容读入到数组中 is.read(bytes); Base64.Encoder encoder = Base64.getEncoder();
			 * String base64Img = encoder.encodeToString(bytes);
			 */

			// 循环读取输入流文件内容，通过输出流将内容写入新文件
			OutputStream os = new FileOutputStream(upload + File.separator + fileName);
			byte buffer[] = new byte[1024];
			int cnt = 0;
			while ((cnt = is.read(buffer)) > 0) {
				os.write(buffer, 0, cnt);
			}
			// 关闭输入输出流
			os.close();
			is.close();

			BufferedImage bufferedImage = org.hds.Graphics.grabberVideoFramer
					.GetFirstFramer(upload + File.separator + fileName);
			// bufferImage->base64
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
			// 打印
			// ImageIO.write(bufferedImage, "jpg", new File("D:/1.jpg"));

			Base64.Encoder encoder = Base64.getEncoder();
			String base64Img = encoder.encodeToString(outputStream.toByteArray());

			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("imgtype", 3);
			jsonObject.put("imgwidth", width);
			jsonObject.put("imgheight", height);

			int result = advMangerSer.Savevideo(fileName, groupid, imgtype, classify, contentType, duration,
					jsonObject.toJSONString(), base64Img);

			if (result != 0) {
				JObject.put("result", "success");
				JObject.put("videoid", result);
				JObject.put("fileName", fileName);
				JObject.put("contentType", contentType);
				JObject.put("duration", duration);
				JObject.put("videostyle", jsonObject.toJSONString());
				JObject.put("imgBase64String", base64Img);
			} else {
				JObject.put("result", "fail");
				JObject.put("resultMessage", "存储数据库失败");
			}
			logger.info("===用户:" + adminname + "上传视频返回结果:" + JObject.toJSONString() + "===");
			return JObject;
		} catch (Exception e) {
			logger.error("===用户:" + adminname + "/uploadvideo 异常:" + e.getMessage() + "===");
			JObject.put("result", "fail");
			JObject.put("resultMessage", e.getMessage());
			return JObject;
		}
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getvideotypebygroupid", method =
	 * RequestMethod.POST) public JSONArray
	 * getvideotypebygroupid(@RequestParam("groupid") int
	 * groupid,@RequestParam("imgtype") int imgtype,HttpServletRequest request){ try
	 * { if(adminInfoJsonObject==null) {adminInfoJsonObject =
	 * (JSONObject)request.getSession().getAttribute("adminInfo");} JSONArray
	 * JsonArray = advMangerSer.getvideoclassifybyGrpid(groupid, imgtype);
	 * logger.info("===用户:"+adminInfoJsonObject.getString("adminname")+
	 * "/getvideotypebygroupid==="); return JsonArray; } catch(Exception e){
	 * logger.error("===用户:"+adminInfoJsonObject.getString("adminname")
	 * +"/getvideotypebygroupid 异常:"+e.getMessage()+"==="); return null; } }
	 */
	@ResponseBody
	@RequestMapping(value = "/getvideobygroupid", method = RequestMethod.POST)
	public JSONArray getvideobygroupid(@RequestParam("groupid") int groupid, @RequestParam("imgtype") int imgtype,
			@RequestParam("classify") String classify, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			JSONArray JsonArray = advMangerSer.getvideobyGrpid(groupid, imgtype, classify);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/getvideobygroupid===", "/getvideobygroupid",
					logger, false);
			return JsonArray;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/getvideobygroupid 异常:" + e.getMessage() + "===",
					"/getvideobygroupid", logger, false);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatevideoclassify", method = RequestMethod.POST)
	public int updatevideoclassify(@RequestParam("groupid") Integer groupid,
			@RequestParam("videotype") Integer videotype, @RequestParam("oldvideoclassify") String oldvideoclassify,
			@RequestParam("newvideoclassify") String newvideoclassify, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			int result = advMangerSer.updatevideoclassify(groupid, videotype, oldvideoclassify, newvideoclassify);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/updatevideoclassify===", "/updatevideoclassify",
					logger, false);
			return result;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1,
					"===用户:" + adminname + "/updatevideoclassify 异常:" + e.getMessage() + "===", "/updatevideoclassify",
					logger, false);
			return 1;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletevideobyid", method = RequestMethod.POST)
	public int deletevideobyid(@RequestParam("basemapid") int basemapid, @RequestParam("adminname") String adminname,
			HttpServletRequest request) {
		try {
			int result = advMangerSer.deletevideobyid(basemapid);
			operateLog.writeLog(adminname, 0, "===用户:" + adminname + "/deletevideobyid===", "/deletevideobyid", logger,
					false);
			return result;
		} catch (Exception e) {
			operateLog.writeLog(adminname, 1, "===用户:" + adminname + "/deletevideobyid 异常:" + e.getMessage() + "===",
					"/deletevideobyid", logger, false);
			return 1;
		}
	}
}
