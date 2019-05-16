package org.hds.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class jsCOMMController {
	final Logger logger=LoggerFactory.getLogger(this.getClass());   
	
	@RequestMapping("/jsCOMM")    
    public String index(){
		logger.info("/jsCOMM Open");
		return "jsCOMM";       
    }
}
