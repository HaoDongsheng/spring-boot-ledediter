package org.hds.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.hds.service.IAdvMangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

@Controller
public class advMangerController {
	final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IAdvMangerService advMangerSer;
	@Autowired
	ResourceLoader loader;
	@RequestMapping("/advManger")    
    public String main(Model model,HttpServletRequest request){	
		try
		{
			JSONObject adminInfoJsonObject = (JSONObject)request.getSession().getAttribute("adminInfo");
			model.addAttribute("userName", adminInfoJsonObject.getString("adminName"));
			logger.info("/advManger Open");
			return "advManger";
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	@ResponseBody
	@RequestMapping(value = "/getadvList", method = RequestMethod.POST) 
    public JSONArray getadvList(HttpServletRequest request){	
		try
		{
			JSONArray JsonArray = advMangerSer.getadvlist();
			logger.info("/getadvList");
			return JsonArray;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "/CreatInfo", method = RequestMethod.POST)    
    public JSONObject CreatInfo(@RequestParam("infoName") String infoName,HttpServletRequest request){
		try {
			JSONObject JObject =advMangerSer.Creatinfo(infoName);
			logger.info("新建广告返回结果:"+JObject.toJSONString());
			return JObject;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/DeleteInfobyid", method = RequestMethod.POST)    
    public JSONObject DeleteInfobyid(@RequestParam("infoid") int infoid,HttpServletRequest request){
		try {
			JSONObject JObject =advMangerSer.DeleteInfobyid(infoid);
			logger.info("保存item返回结果:"+JObject.toJSONString());
			return JObject;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/SaveItem", method = RequestMethod.POST)    
    public JSONObject SaveItem(@RequestParam("infoid") int infoid,@RequestParam("arritem") String arritem,HttpServletRequest request){
		try {			
			JSONObject jsoninfo=JSONObject.parseObject(arritem);
			JSONObject JObject = advMangerSer.SaveItem(infoid, jsoninfo);			
			logger.info("保存显示项返回结果:"+JObject.toJSONString());
			return JObject;			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		   
    }
	
	@ResponseBody
	@RequestMapping(value = "/GetItem", method = RequestMethod.POST)    
    public JSONObject GetItem(@RequestParam("infoid") int infoid,HttpServletRequest request){
		try {							
			JSONObject JObject = advMangerSer.GetItembyid(infoid);			
			logger.info("获取显示项返回结果:"+JObject.toJSONString());
			return JObject;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		   
    }
	
	//处理文件上传
    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    public @ResponseBody String uploadimg(@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
            HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();        
        logger.info("上传的文件名为：" + fileName);
        logger.info("上传的后缀名为：" + contentType);  
        // 文件上传后的路径
        String filePath = "E:/test/";        
        //String filePath = request.getSession().getServletContext().getRealPath("imgupload/");        
        try {
        	
            // 解决中文问题，liunx下中文路径，图片显示问题
            // fileName = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);                                               
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            return fileName;
        } catch (Exception e) {
        	return "";
        }                
    }
    
}
