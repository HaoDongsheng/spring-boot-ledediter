var svgcanvas;
var screenw=192,screenh=32;
var itemmap={};
var infomap={};
var selectspecial={};
var selectinfoid=0,selectpageid=0,selectitemid=0;
var selectItem,selectGrip;
var defaultFontsize=32;
var ue;
var scrollValue=0;
var ispermission=true;

$(function(){				
	console.log($(window).height()); //浏览器时下窗口可视区域高度
	console.log($(document).height()); //浏览器时下窗口文档的高度 
	console.log($(document.body).height());//浏览器时下窗口文档body的高度
	
	//ue = UE.getEditor('ueditor_context');		   		
	permission();
	
	getGroup();
	
	initSvgcanvas();
	
	initpage();	
	
	/*
	$('#next').click(function() {
		scrollValue += 100;
		$('#layer_items').scrollLeft(scrollValue);
		if($('#layer_items').scrollLeft() < scrollValue)
			{
			scrollValue=$('#layer_items').scrollLeft();
			}
	});
	
	$('#pre').click(function() {
		scrollValue -= 100;
		if(scrollValue<0){scrollValue=0;}
		$('#layer_items').scrollLeft(scrollValue);
	});
	*/
	 	 			
});
//权限功能封闭
function permission() {
	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));		
	
	if(adminInfo.issuperuser!=1)
		{
		var adminpermission = adminInfo.adminpermission;
		var isable = parseInt(adminpermission[0]);
		if(isable==1)
			{
			ispermission = true;
//			$('#info_create').removeAttr("disabled");
//			$('#info_save').removeAttr("disabled");
//			$('#info_delete').removeAttr("disabled");
//			$('#info_publish').removeAttr("disabled");
//			$('#info_copy').removeAttr("disabled");
//			
//			$('#creat_layer').removeAttr("disabled");	
			
			$('#info_parameter input').removeAttr("disabled");
			$('#info_parameter select').removeAttr("disabled");
			}
		else {
			ispermission = false;
//			$('#info_create').attr("disabled","disabled");	
//			$('#info_save').attr("disabled","disabled");
//			$('#info_delete').attr("disabled","disabled");
//			$('#info_publish').attr("disabled","disabled");
//			$('#info_copy').attr("disabled","disabled");
//			
//			$('#creat_layer').attr("disabled","disabled");	
			
			$('#info_parameter input').attr("disabled","disabled");
			$('#info_parameter select').attr("disabled","disabled");
		}		
		}
}
//自定义 图片
function ueditor_customImage()
{
	$('#modal_pic_select').attr("data-imgtype",1);

	initbasemap(1);
	
	$('#modal_pic_select').modal('show');
}
//自定义 特效
function ueditor_customSpecial()
{
	$('#modal_special_select').modal('show');
}

function isSensitive(content) {
	var sensitiveString="";
	var sensitivelist = JSON.parse(sessionStorage.getItem('sensitivelist'));	
	for(var i=0;i<sensitivelist.length;i++)
		{
		if(content.indexOf(sensitivelist[i].svstring)!=-1)
			{
			sensitiveString = sensitivelist[i].svstring;
			break;
			}
		}
	return sensitiveString;
}

function initpage()
{			
	$('#info_operation_group').css("left",$('.container').offset().left - $('#info_operation_group').width());
	$('#info_operation_group').css("top",$('#badge_draft').offset().top);
	
	$(".modal").draggable();
	
	getsensitive();
	
	$("#btn_sensitive_add").click(function(){	
		var sensitiveString = $("#input_sensitive").val().trim();
		if(sensitiveString==null || sensitiveString=="")
			{
			alertMessage(1, "异常", "添加敏感词不能为空!");
			return;
			}
		
		$.ajax({  
	        url:"/addSensitive",         
	        data:{
	        	sensitiveString:sensitiveString,
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname       	
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success")
	        		{            			
	        		var sensitivelist = JSON.parse(sessionStorage.getItem('sensitivelist'));	        		
	        		
        			var svid = data.svid;
        			var projectid = data.projectid;
        			var svstring = data.svstring;
        			$('#svlist').append('<option value='+svid+' data-projectid ="'+projectid+'">'+svstring+'</option>');	
        			
        			var arr  = {
	        		         svid : svid,
	        		         projectid : projectid,
	        		         svstring : svstring,
	        		         svdelindex : 0
	        		};
	        		sensitivelist.push(arr);
	        		sessionStorage.setItem('sensitivelist', JSON.stringify(sensitivelist));
	        		
        			alertMessage(0, "成功", "添加敏感词汇成功!");
	        		}
	        	else
	        		{
	        			alertMessage(1, "异常", data.resultMessage);				        			
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  addSensitive 错误");			        	            
	          }  
	    });
	});

	$("#btn_sensitive_delete").click(function(){	
		var sensitivelist = $("#svlist").val().join(",");
		if(sensitivelist==null || sensitivelist=="")
			{
			alertMessage(1, "异常", "请选择要删除的敏感词!");
			return;
			}
		
		$.ajax({  
	        url:"/deleteSensitive",         
	        data:{
	        	sensitiveIdlist:sensitivelist,
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname       	
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success")
	        		{            			
	        		var sensitivelist = JSON.parse(sessionStorage.getItem('sensitivelist'));
	        		var svlist=$("#svlist").val();
	        		for(var i=0;i<svlist.length;i++)
	        			{
	        			var svid = parseInt(svlist[i]);
	        			$("#svlist option[value="+svid+"]").remove();  //删除Select中Value='3'的Option
	        			
	        			for(var j=0;j<sensitivelist.length;j++)
	        				{
	        				if(sensitivelist[j].svid == svid)
	        					{
	        					sensitivelist.splice(j,1);
	        					break;
	        					}
	        				}
	        			}	        		
        	
	        		sessionStorage.setItem('sensitivelist', JSON.stringify(sensitivelist));
	        		
        			alertMessage(0, "成功", "shanchu敏感词汇成功!");
	        		}
	        	else
	        		{
	        			alertMessage(1, "异常", data.resultMessage);				        			
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  deleteSensitive 错误");			        	            
	          }  
	    });
	});		
	
	$("#workarea").click(function(){	
		selectitem=0;
	});			
	
	$("#creat_layer").click(function(){
		var pid=0;
		$("#layer_list .listbox").each(function(){
			var pageid = parseInt(this.id.slice(5));
			if(pageid>pid){pid=pageid;}
		  });		
		additem(pid+1,1,0,0,screenw,screenh,0,'',100,'',100,0,"<p>文明驾驶安全行车</p>",null);
		addlayer(pid+1,itemmap[pid+1],true);
		
		if(infomap.hasOwnProperty(selectinfoid))
		{
			infomap[selectinfoid].isSave=1;
		}
	});

	$("#btn_sensitive_ok").click(function(){
		var Content = $("#modal_sensitive").attr("data-content");
		updateitem(selectpageid,selectitemid,["itemtype","special","context"],[0,selectspecial,Content]);
		updateCanvasItem(selectpageid,selectitemid,true,false);
		 
		$("#myModalEdit").modal('hide');
		$('#modal_sensitive').modal('hide');
	});

	$("#editItem").click(function(){
//		var selcet_div = parseInt($("#select_div").val());
		var dataType = parseInt($("#myModalEdit").attr("data-type"));
		switch(dataType)
		{
		case 0/*图文*/:{
			if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
			{			
			 var Content = tinymce.activeEditor.getContent();
			 var text = tinymce.activeEditor.getContent( { 'format' : 'text' } );
			 var sensitiveString = isSensitive(text);
			 if(sensitiveString!="")
				 {
				 $('#modal_sensitive .modal-body').text("内容包含敏感词汇["+sensitiveString+"],是否继续保存?");
				 $('#modal_sensitive').modal('show');
				 $("#modal_sensitive").attr("data-content",Content);
				 }
			 else {
				 updateitem(selectpageid,selectitemid,["itemtype","special","context"],[0,selectspecial,Content]);
				 updateCanvasItem(selectpageid,selectitemid,true,false);
				 
				 $("#myModalEdit").modal('hide');	
			}
			 
			}
		};break;
		case 1/*Gif动画*/:{
			$("#div_giflist .basemapStyle input").each(function(){
				if(this.checked)
					{
						var data_imgtype = $('#modal_gif_select').attr("data-imgtype");
						var divid=$(this).parent()[0].id;
						
						var src = $("#"+divid+" img")[0].src;
						var datamapstyle = $("#"+divid+" img")[0].dataset.mapstyle;
								
						var jsonds=JSON.parse(datamapstyle);
						//"{"imgwidth":48,"gifFramesCount":13,"imgheight":32,"imgtype":3}"
						var imgwidth = screenw<jsonds.imgwidth?screenw:jsonds.imgwidth;
						var imgheight= screenh<jsonds.imgheight?screenh:jsonds.imgheight;
						
						var context="<p><img src='"+src+"' data-mapstyle='"+datamapstyle+"'/></p>";
						
						updateitem(selectpageid,selectitemid,["itemtype","width","height","context"],[4,imgwidth,imgheight,context]);
						updateCanvasItem(selectpageid,selectitemid,true,false);										
					}
			});
			 $("#myModalEdit").modal('hide');
		};break;
		}
		
	});

	$("#svg_animation_linkmove").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{			
			updateitem(selectpageid,selectitemid,["linkmove"],[$("#svg_animation_linkmove").val()]);
		}
	});
	
	$("#svg_animation_playtype").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["playtype"],[$("#svg_animation_playtype").val()]);		 
		}
	});
	
	$("#svg_animation_playspeed").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["playspeed"],[$("#svg_animation_playspeed").val()]);		 
		}
	});

	$("#svg_animation_stoptime").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["stoptime"],[$("#svg_animation_stoptime").val()]);		 
		}
	});
	
	$("#svg_animation_looptime").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["looptime"],[$("#svg_animation_looptime").val()]);		 
		}
	});
	
	$("#svg_animation_Mandatorytime").change(function(){
		if($("#svg_animation_Mandatorytime").val()==0)
		{
		$("#svg_animation_playspeed").parent().css("display","block");							
		$("#svg_animation_stoptime").parent().css("display","block");
		$("#svg_animation_MandatorytimeLength").parent().css("display","none");
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["Mandatorytime","playspeed","stoptime"],[0,$("#svg_animation_playspeed").val(),$("#svg_animation_stoptime").val()]);		 
		}
		}
		else
		{
		$("#svg_animation_playspeed").parent().css("display","none");
		$("#svg_animation_stoptime").parent().css("display","none");
		$("#svg_animation_MandatorytimeLength").parent().css("display","block");
		$("#svg_animation_MandatorytimeLength").val(5);
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["Mandatorytime","MandatorytimeLength"],[1,$("#svg_animation_MandatorytimeLength").val()]);		 
		}
		
		}
	});
	
	$("#svg_animation_MandatorytimeLength").change(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
		{
		 updateitem(selectpageid,selectitemid,["MandatorytimeLength"],[$("#svg_animation_MandatorytimeLength").val()]);		 
		}
	});
	
	$("#grouplist").change(function(){		
		getadvListbyGrpid(parseInt($("#grouplist").val()));	    
	  });
	
	$("#info_create").click(function(){
		infoCreate();
	});
	
	$("#info_save").click(function(){
		if(selectinfoid != 0)
			{
			var infoid = selectinfoid;
			infoSavebyid(infoid);
			}		
	});
	
	$("#info_delete").click(function(){
		if(selectinfoid != 0)
		{
		var infoid = selectinfoid;
		infoDeletebyid(infoid);
		}
	});
	
	$("#info_publish").click(function(){
		if(selectinfoid != 0)
		{
		var infoid = selectinfoid;
		infoAuditbyid(infoid);
		}
	});
	
	$("#info_copy").click(function(){
		if(selectinfoid != 0)
		{
		var infoid = selectinfoid;
		infoCopybyid(infoid);
		}
	});
	
	$("#info_attribute").click(function(){
		if(selectinfoid != 0)
		{
		var infoid = selectinfoid;
		infoAttributebyid(infoid);
		}
	});
		
	//设置串口
	$("#btn_setSerialPort").click(function(){
		var SerialPort={
			"ComName":$("#ComName").val(),
			"BaudRate":parseInt($("#BaudRate").val())
		};
		localStorage.setItem("SerialPort",JSON.stringify(SerialPort));
		$("#modal_SerialPort").modal('hide');		
	});
	
	//串口发送
	$("#info_SerialPort").click(function(){
		if(selectinfoid != 0)
		{
		var infoid = selectinfoid;
		SendSerialData(infoid);
		}
	});	
	
	//模拟显示
	$("#info_play").click(function(){
		if(selectinfoid != 0)
		{
			if($('#info_play i').hasClass('fa-play'))
				{
				isStop= false;
				$('#info_play i').removeClass('fa fa-play');		
				
				$('#info_play i').addClass('fa fa-stop');
				play(itemmap);
				}
			else {
				isStop=true;
				$('#info_play i').removeClass('fa fa-stop');		
				
				$('#info_play i').addClass('fa fa-play');
			}
		}
	});	
}

function getGroup()
{
	var selectGrpid = sessionStorage.getItem('selectgrpid');
	var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	if(grpsinfo==null || grpsinfo.length<=0)
		{
		$.ajax({  
	        url:"/getGroup",          
	        type:"post",  
	        dataType:"json", 
	        data:{
	        	adminInfo:localStorage.getItem("adminInfo")
	        	},
	        success:function(data)  
	        {       	  
	        	if(data!=null && data.length>0)    		
	        		{		        				        			
	        			sessionStorage.setItem('grpsinfo', JSON.stringify(data));		        			
	        			grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	        				        				        			
	        			for(var i=0;i<grpsinfo.length;i++)
	        			{
	        				var grpid=grpsinfo[i].grpid;
	        				var grpname = grpsinfo[i].grpname;	        				
	        				if(selectGrpid==0 || selectGrpid==null)
	        					{
		        				if(i==0)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								getadvListbyGrpid(grpid);
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
	        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								getadvListbyGrpid(grpid);
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
							}
	        			}		
	        			
	        			if(grpsinfo.length>1)
	        				{
	        				$('#input_group_grouplist').css("display","inline");
	        				}
	        			else
	        				{$('#input_group_grouplist').css("display","none");}
	        		}
	        },  
	        error: function() {  
	        	alertMessage(2, "异常", "ajax 函数  getGroup 错误");	        			        
	          }  
	    });
		}
	else
		{
			for(var i=0;i<grpsinfo.length;i++)
			{
				var grpid=grpsinfo[i].grpid;
				var grpname = grpsinfo[i].grpname;
				if(selectGrpid==0 || selectGrpid==null)
				{
				if(i==0)
				{
				$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
				getadvListbyGrpid(grpid);
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid==grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					getadvListbyGrpid(grpid);
					}
					else
					{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
			}
			
			if(grpsinfo.length>1)
			{
			$('#input_group_grouplist').css("display","inline");
			}
		else
			{$('#input_group_grouplist').css("display","none");}
		}
}

//判断文字显示项是否内码,不是生成图片
function updateJsonContext(item,JsonContext) {
	//var fontName='SimSun',fontSize=16,foreColor='',backColor;	
	var special = item.itemstyle.special;
    var sketch=null,shadow=null,gradient=null;
    if(special!=null)
    	{    		
    		if(special.sketch!=null)
			{sketch = special.sketch;}
    		if(special.shadow!=null)
    		{shadow = special.shadow;}
    		if(special.gradient!=null)
    		{gradient = special.gradient;}
    	}
    
	var arrNode = new Array()
	var contextJson = JSON.parse(JsonContext);	
	for(var r=0;r<contextJson.length;r++)
	{											
		var rownode=contextJson[r];
		var arrRow = new Array()
		for(var i=0;i<rownode.length;i++)
			{
			var itemnode=rownode[i];
			if(itemnode.itemType==0)
				{
					var isIncodefont=0;
					switch(item.fontno)
					{
						case 0:{
							if(itemnode.fontName!="SimSun" || itemnode.fontSize!=16)
								{isIncodefont=1;}
						};break;
						case 1:{
							if(itemnode.fontName!="SimSun" || itemnode.fontSize!=24)
							{isIncodefont=1;}
						};break;
						case 2:{
							if(itemnode.fontName!="SimSun" || itemnode.fontSize!=32)
							{isIncodefont=1;}
						};break;
						default:{isIncodefont=1;};
					}
					
					if(isIncodefont==1 || itemnode.foreColor!=item.forecolor || itemnode.backColor!=item.backcolor || special!=null)
 					{
	 					var tmpcanvas = document.createElement("canvas");											    
					    
						$(tmpcanvas).drawText({	
							  layer: true,
							  name: 'myText',
							  fillStyle: itemnode.foreColor,							  
							  x: 0, y: 0,
							  fontSize: itemnode.fontSize+'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  fromCenter: false
							});
						
						var imgwidth = $(tmpcanvas).measureText('myText').width;																		
													
						var devicePixelRatio = 2;
						var canvas = document.createElement("canvas");							
					    canvas.width = imgwidth;
					    canvas.height = item.height;// itemnode.fontSize;
					    					    
					    if(itemnode.backColor!="")
					    	{
						    $(canvas).drawRect({
						    	  fillStyle: itemnode.backColor,
						    	  x: 0, y: 0,
						    	  width: canvas.width,
						    	  height: canvas.height,
						    	  fromCenter: false
						    	});
					    	}
					    
					    var linespace=0;
	 					if(itemnode.fontSize<item.height)
	 						{
	 						linespace = parseInt((item.height-itemnode.fontSize)/2);
	 						}
	 					
						$(canvas).drawText({
							  layer: true,
	 						  name: 'myText',
	 						  imageSmoothing:true,
							  fillStyle: itemnode.foreColor,							  
							  x: 0, y: linespace,
							  fontSize: itemnode.fontSize+'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  fromCenter: false,
							});
						
						//特效开始
						//描边	 					
	 					 if(sketch!=null)
	 					 {			 
	 						$(canvas).setLayer('myText', {
	 						 strokeStyle: sketch.strokeStyle,
	 						 strokeWidth: sketch.strokeWidth
	 						})
	 						.drawLayers();
	 					 }
	 					 else
	 					 {
	 					 	$(canvas).setLayer('myText', {	 						 
	 						 strokeWidth: 0
	 						})
	 						.drawLayers();
	 					 }
	 					 //阴影
	 					if(shadow!=null)
	 					 {			 
	 						$(canvas).setLayer('myText', {
	 				 		  shadowColor: shadow.shadowColor,
	 						  shadowBlur: 0,// shadow.shadowBlur,
	 						  shadowX: shadow.shadowBlur, shadowY: shadow.shadowBlur
	 						})
	 						.drawLayers();
	 					 }
	 					else
	 					 {
	 						$(canvas).setLayer('myText', {	 				 		  
	 						  shadowBlur: 0,
	 						  shadowX: 0, shadowY: 0
	 						})
	 						.drawLayers();
	 					 }
	 					//渐变
	 					if(gradient!=null)
	 				 	{
	 					 	var linear = null;
	 					 		 					 	
	 					 	switch(gradient.gradientdirection)
	 					 	{
	 					 	case 'horizontal':{
	 					 		linear = $('canvas').createGradient({
	 								  x1: 0, y1: 0,
	 								  x2: canvas.width, y2: 0,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	case 'vertical':{
	 					 		linear = $('canvas').createGradient({
	 								  x1: 0, y1: 0,
	 								  x2: 0, y2: canvas.height,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	case 'oblique':{
	 					 		linear = $('canvas').createGradient({
	 					 			  x1: 0, y1: 0,
	 								  x2: canvas.width, y2: canvas.height,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	}
	 			
	 					 
	 					 	$(canvas).setLayer('myText', {
	 				 		 fillStyle: linear
	 						})
	 						.drawLayers();			    
	 				 	}
	 				 	else
	 					 {
	 				 		$(canvas).setLayer('myText', {
	 				 		 fillStyle: itemnode.foreColor
	 						})
	 						.drawLayers();		
	 					 }
						//特效结束
	 					
						data={	 						
		 						itemType:2,
		 						width:canvas.width,
		 						height:canvas.height,
		 						value:$(canvas).getCanvasImage('bmp')				
		 				};			 	
						arrRow.push(data);
 					}
					else
						{arrRow.push(itemnode);}
				}
			else{arrRow.push(itemnode);}
			}
		arrNode.push(arrRow);
		}
	return arrNode;
}
//选择显示项改变
function selectChangeItem(pageid,itemid)
{
	selectpageid=pageid;
	selectitemid=itemid;
	
	$('#list_page'+pageid+'_item'+itemid).css("border-color","yellow")
	if(itemmap.hasOwnProperty(pageid))
	{
		for(var i=0;i<itemmap[pageid].length;i++)
			{
				var item=itemmap[pageid][i];
				if(item.itemid==itemid)
					{	
				        $("#svg_color_fill").val(item.backcolor);
						$("#svg_color_opacity").val(item.backopacity);	
						var itemstyle=item.itemstyle;
						if(itemstyle!=null)
							{							
							$("#svg_animation_linkmove").val(itemstyle.linkmove);
							$("#svg_animation_playtype").val(itemstyle.playtype);
							$("#svg_animation_playspeed").val(itemstyle.playspeed);							
							//$("#svg_animation_rollstop").val(itemstyle.rollstop);
							$("#svg_animation_stoptime").val(itemstyle.stoptime);
							$("#svg_animation_looptime").val(itemstyle.looptime);
												
							if(itemstyle.gamma!=null)
								{
								$("#svg_animation_gamma").val(itemstyle.gamma);
								}
							
							if(itemstyle.Mandatorytime!=null)
								{
								$("#svg_animation_Mandatorytime").val(itemstyle.Mandatorytime);
								}
							else {
								$("#svg_animation_Mandatorytime").val(0);
							}
							if(itemstyle.MandatorytimeLength!=null)
								{
								$("#svg_animation_MandatorytimeLength").val(itemstyle.MandatorytimeLength);
								}
							}
						
						if(item.type==0)
							{
							$("#svg_animation_playtype").parent().css("display","block");							
							$("#svg_animation_Mandatorytime").parent().css("display","block");
							if($("#svg_animation_Mandatorytime").val()==0)
								{
								$("#svg_animation_playspeed").parent().css("display","block");							
								$("#svg_animation_stoptime").parent().css("display","block");
								$("#svg_animation_MandatorytimeLength").parent().css("display","none");
								}
							else
								{
								$("#svg_animation_playspeed").parent().css("display","none");
								$("#svg_animation_stoptime").parent().css("display","none");
								$("#svg_animation_MandatorytimeLength").parent().css("display","block");
								}							
							}
						else
							{
							$("#svg_animation_playtype").parent().css("display","none");
							$("#svg_animation_playspeed").parent().css("display","none");
							$("#svg_animation_Mandatorytime").parent().css("display","none");
							$("#svg_animation_MandatorytimeLength").parent().css("display","none");
							$("#svg_animation_stoptime").parent().css("display","none");
							}
						//DrawControl('#workarea_canvas',item.left * itemscale,item.top * itemscale,item.width * itemscale,item.height * itemscale);
					}
			}
	}	  
}
//初始化画布
function initSvgcanvas()
{	
    var scalew = parseInt(($("#workarea").width() - 40)/screenw);
    
    var scaleh = parseInt(($("#workarea").height() - 40)/screenh);
    
    var scale=scalew>scaleh?scaleh:scalew;
    
	$("#divsvg").width(screenw*scale);
	$("#divsvg").height(screenh*scale);
	
	var workarea_canvas = document.getElementById('workarea_canvas');//获取ID
	workarea_canvas.width=screenw*scale;
	workarea_canvas.height=screenh*scale;
	
	var svgx=parseInt(($("#workarea").width() - $("#divsvg").width())/2);
	var svgy=parseInt(($("#workarea").height() - $("#divsvg").height())/2);
	svgx=svgx>0?svgx:0;
	svgy=svgy>0?svgy:0;
	
	$("#divsvg").css("left",svgx);
	$("#divsvg").css("top",svgy);
	
	$("#workarea_canvas").css("display","none");
	$("#workarea_canvas").css("background-color","#ffffff");
	
}
//按组取广告列表
function getadvListbyGrpid(grpid)
{	
	sessionStorage.setItem('selectgrpid', grpid);	
	
	var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	
	for(var i=0;i<grpsinfo.length;i++)
	{				
		if(grpid==grpsinfo[i].grpid)
			{
				var screenwidth=grpsinfo[i].screenwidth;
				var screenheight=grpsinfo[i].screenheight;
				var displayMode = grpsinfo[i].displayMode;
				
				for(var j=0;j<displayMode.length;j++)
				{					
					if(displayMode[j]=='0')
						{								
						$("#svg_animation_playtype option[value="+j+"]").css("display","none");
						}
					else {
						$("#svg_animation_playtype option[value="+j+"]").css("display","inline");
					}					
				}
				screenw=screenwidth;
				screenh=screenheight;
				initSvgcanvas();
				selectinfoid=0;
				break;
			}
	}		
	
	$.ajax({  
        url:"/getadvEditListbyGrpid", 
        data:{
        	Grpid:grpid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",
        success:function(data)  
        {       
        	$('#infolist_draft').empty();			
			$('#badge_draft').text(0);								
			
        	if(data!=null && data.length>0)
        		{
        			infomap={};
	        		for(var i=0;i<data.length;i++)
					{
	        			var id=data[i].id;
	        			var advname=data[i].advname;
	        			var groupid=data[i].groupid;
	        			var lifeAct=data[i].lifeAct;
	        			var lifeDie=data[i].lifeDie;
	        			var advType=data[i].advType;
	        			var BackgroundStyle=data[i].BackgroundStyle;
	        			var playMode=data[i].playMode;
	        			var pubid=data[i].pubid;
	        			var playTimelength=data[i].playTimelength;
	        			var DelIndex=data[i].DelIndex;
	        			
        				adddivinfo(id,advname);

        				addinfomap(id,advname,groupid,lifeAct,lifeDie,advType,BackgroundStyle,playMode,pubid,playTimelength,DelIndex,0);						
        				
        				if(selectinfoid==0)
        				{infoSelectChange(id);}
        				
					}
        			
        		}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getadvListbyGrpid 错误");        	          
          }  
    });
}
//添加广告列表div
function adddivinfo(id,advname)
{
	var pl="<a id='info_"+id+"'><span>"+advname+"</span></a>";
	var div="<div id='div_"+id+"' style='text-align:center;background:#F7F7F7;' onclick='infoSelectChange("+id+")' class='list-group-item' onmouseover='mOver(this)' onmouseout='mOut(this)'>";
	div=div+pl+"</div>";
	
	$('#infolist_draft').append(div);
	var count = parseInt($('#badge_draft').text()) + 1;
	$('#badge_draft').text(count);																										
}
//添加广告信息
function addinfomap(id,advname,groupid,lifeAct,lifeDie,advType,BackgroundStyle,playMode,pubid,playTimelength,DelIndex,isSave)
{
	var infodata={
			id:id,
			advname:advname,
			groupid:groupid,
			lifeAct:lifeAct,
			lifeDie:lifeDie,
			advType:advType,
			BackgroundStyle:BackgroundStyle,
			playMode:playMode,
			pubid:pubid,			
			playTimelength:playTimelength,
			isSave:isSave
	}						
	infomap[id]=infodata;
}
//鼠标进入
function mOver(obj){
	$(obj).css("background","#007BFF");
}
//鼠标移除
function mOut(obj){
	$(obj).css("background","#F7F7F7");
}
//广告选择改变
function infoSelectChange(infoid)
{
	if(infoid!=selectinfoid)
	{				
		$('#info_operation_group').css("left",$('.container').offset().left - $('#info_operation_group').width());
		$('#info_operation_group').css("top",$("#div_"+infoid).offset().top);
		
		$('#workarea').clearCanvas();
		$('#workarea').removeLayers();
		
		selectpageid=0;
		selectitemid=0;
		$("#layer_list").empty();
		if(selectinfoid!=0)
			{				
				saveitem(selectinfoid);					
				selectinfoid=infoid;				
			}
		else{selectinfoid=infoid;}
		
		$('#infolist_draft span').removeClass('mws-ic-16 ic-accept');		
		
		$("#div_"+selectinfoid+" span").addClass('mws-ic-16 ic-accept');						
		
		getitem(infoid);
				
		updataSvgcanvas();
	}
}
//初始化画布
function updataSvgcanvas()
{
	 //"{"width":192,"height":32,"basemapStyle":[{"basemalistmode":2,"basemabackcolor":"#000000"}]}"
    var infodata=infomap[selectinfoid];
    var bjsJson = JSON.parse(infodata.BackgroundStyle);
    
    //var sw=bjsJson.width;
    //var sh=bjsJson.height;
    var sw = screenw;
    var sh = screenh;
    $("#workarea_canvas").css("display","inline");
    
    var scalew = parseInt(($("#workarea").width() - 40)/sw);
    
    var scaleh = parseInt(($("#workarea").height() - 40)/sh);
    
    var scale=scalew>scaleh?scaleh:scalew;
    
	$("#divsvg").width(sw*scale);
	$("#divsvg").height(sh*scale);
	
	var workarea_canvas = document.getElementById('workarea_canvas');//获取ID
	workarea_canvas.width=sw*scale;
	workarea_canvas.height=sh*scale;
	
	var svgx=parseInt(($("#workarea").width() - $("#divsvg").width())/2);
	var svgy=parseInt(($("#workarea").height() - $("#divsvg").height())/2);
	svgx=svgx>0?svgx:0;
	svgy=svgy>0?svgy:0;
	$("#divsvg").css("left",svgx);
	$("#divsvg").css("top",svgy);	
	    
    var bmslist = bjsJson.basemapStyle;
    if(bmslist!=null && bmslist.length>0)
    	{
	    for(var i=0;i<bmslist.length;i++)
	    	{
	    		var bms = bmslist[i];
	    		var basemalistmode = bms.basemalistmode;
	    		switch(basemalistmode)
	    		{
	    		case 2:{
	    			var basemabackcolor = bms.basemabackcolor;    
	    			$("#workarea_canvas").css("background-image","");    			
	    			$("#workarea_canvas").css("background-size","");
	    			$("#workarea_canvas").css("background-repeat","");
	    			$("#workarea_canvas").css("background-color",basemabackcolor);	    			
	    		};break;
	    		case 3:{	    			
	    			var basemalistsn = bms.basemalistsn;
 	    			var basemalistsrc = bms.basemalistsrc;
 	    			var basemalistmapstyle = bms.basemalistmapstyle;
 	    			
 	    			var urlbase64 = "url('"+basemalistsrc+"')";
 	    			var size =$("#workarea_canvas").width()+"px "+$("#workarea_canvas").height()+"px";
 	    			
 	    			$("#workarea_canvas").css("background-image",urlbase64);    			
	    			$("#workarea_canvas").css("background-size",size);
	    			$("#workarea_canvas").css("background-repeat","");
	    			$("#workarea_canvas").css("background-color","");
	    		};break;
	    		}    		
	    	}
    	}
    else
    	{    	
    	$("#workarea_canvas").css("background-image","");    			
		$("#workarea_canvas").css("background-size","");
		$("#workarea_canvas").css("background-repeat","");
    	$("#workarea_canvas").css("background-color","#ffffff");
    	}
}
//保存显示项
function saveitem(infoid)
{		
	if(infomap.hasOwnProperty(infoid))
	 {
		if(infomap[infoid].isSave==1)
			{
			if(infomap[infoid].pubid!=0)
				{
					$("#modal_save").attr("data-type","update_item");
					$('#modal_save').modal('show');
				}
			else
				{
				var jsonString = JSON.stringify(itemmap);
				$.ajax({  
			        url:"/SaveItem",         
			        data:{
			        	infoid:infoid,
			        	infodata:JSON.stringify(infomap[infoid]),
			        	arritem:jsonString,
			        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
			        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
						},  
			        type:"post",  
			        dataType:"json", 
			        success:function(data)  
			        {       	  
			        	if(data.result=="success")
			        		{    
			        			infomap[infoid].isSave=0;
			        			
    							alertMessage(0, "成功", "保存成功!");
			        						        			
			        		}
			        	else
			        		{
			        			alertMessage(1, "异常", data.resultMessage);				        			
			        		}
			        },  
			        error: function() { 
			        	alertMessage(2, "异常", "ajax 函数  SaveItem 错误");			        	            
			          }  
			    });
				}
			}
	 }
}
//获取显示项
function getitem(infoid)
{	
	$.ajax({  
        url:"/GetItem",         
        data:{
        	infoid:infoid,        	
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{       
        			itemmap={};				
        			if(data.itemlist!=null && data.itemlist.length>0)
        				{       
        					for(var i=0;i<data.itemlist.length;i++)
        						{
        							var jitem=data.itemlist[i];
        							var pageid =jitem.pageid;        							
        							var delindex =jitem.delindex;        							
        							if(delindex==0)
        								{
        								additem(pageid,jitem.itemid,jitem.itemleft,jitem.itemtop,jitem.itemwidth,jitem.itemheight,jitem.itemfontno,jitem.itembackcolor,100,jitem.itemforecolor,100,jitem.itemtype,jitem.itemcontext,jitem.itemstyle);
        								}
        						}
        					
        					var firstindex=0;
        					for(var key in itemmap)
        						{
        							if(firstindex==0)
    								{addlayer(key,itemmap[key],true);}
        							else
        							{addlayer(key,itemmap[key],false);}
        							
        							firstindex++;
        						}    
    						    						
        				}
        			else
        				{        				    
        				    additem(1,1,0,0,screenw,screenh,0,'',100,'',100,0,"<p>文明驾驶安全行车</p>",null);
        				    addlayer(1,itemmap[1],true)   
        				}
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  GetItem 错误");        	          
          }  
    });
}
//添加item
function additem(pageid,itemid,left,top,width,height,fontno,backcolor,backopacity,forecolor,foreopacity,type,context,itemstyle)
{
	var item=new Object();
    item.itemid=itemid;
    item.left=left;
    item.top=top;
    item.width=width;
    item.height=height;
    item.fontno=fontno;
    item.backcolor=backcolor;
    item.backopacity=backopacity;
    item.forecolor=forecolor;
    item.foreopacity=foreopacity;
    item.type=type;
    item.context=context;
    if(itemstyle==null || itemstyle=="")
    	{
    	item.itemstyle={};
    	item.itemstyle.linkmove=0		
    	item.itemstyle.playtype=1;
    	item.itemstyle.playspeed=5;
    	item.itemstyle.rollstop=0
    	item.itemstyle.stoptime=0;
    	item.itemstyle.looptime=1;
    	item.itemstyle.gamma=20;
    	item.itemstyle.Mandatorytime=0;
    	item.itemstyle.MandatorytimeLength=5;
    	}
    else
    	{
    	item.itemstyle = JSON.parse(itemstyle);
    	if(item.itemstyle.special!=null)
    		{selectspecial = item.itemstyle.special;}
    	}
	 
	var arrNode = updateitembfcolorReturnjson(item);
	 
	var contextJson = JSON.stringify(arrNode);
	item.contextJson=contextJson;	//不改变item类型 											 
	 
	if(itemmap.hasOwnProperty(pageid))
	{		
		itemmap[pageid].push(item);
	}
	else
	{        				
		var itemlist=[];
		itemlist.push(item);
		itemmap[pageid]=itemlist;
	}
		
	 var playtime = getplaytime(pageid,itemid);
	 item.itemstyle.playtime=playtime;
	 getinfototaltimelenght();
}
//修改item
function updateitem(pageid,itemid,proNames,values)
{
	if(infomap.hasOwnProperty(selectinfoid))
	 {
		infomap[selectinfoid].isSave=1;
	 }
	
	 if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i];
					if(item.itemid==itemid)
						{	
							for(var p=0;p<proNames.length;p++)
								{
									var proName=proNames[p];
									switch(proName)
									{
										case 'context':{											    
											 item.context=values[p];
											 
											 var arrNode = updateitembfcolorReturnjson(item);
											 
											 var contextJson = JSON.stringify(arrNode);
											 
											 item.contextJson = contextJson;//不改变item类型 
											 //var cj = updateJsonContext(item,contextJson);
											 //item.contextJson=JSON.stringify(cj);											 
											 
											 var playtime = getplaytime(pageid,itemid);
											 item.itemstyle.playtime=playtime;
											 getinfototaltimelenght();
										};break;
										case 'itemtype':{
											item.type=values[p];
											
											if(item.type==0)
											{
											$("#svg_animation_playtype").parent().css("display","block");							
											$("#svg_animation_Mandatorytime").parent().css("display","block");
											if($("#svg_animation_Mandatorytime").val()==0)
												{
												$("#svg_animation_playspeed").parent().css("display","block");							
												$("#svg_animation_stoptime").parent().css("display","block");
												$("#svg_animation_MandatorytimeLength").parent().css("display","none");
												}
											else
												{
												$("#svg_animation_playspeed").parent().css("display","none");
												$("#svg_animation_stoptime").parent().css("display","none");
												$("#svg_animation_MandatorytimeLength").parent().css("display","block");
												}							
											}
										else
											{
											$("#svg_animation_playtype").parent().css("display","none");
											$("#svg_animation_playspeed").parent().css("display","none");
											$("#svg_animation_Mandatorytime").parent().css("display","none");
											$("#svg_animation_MandatorytimeLength").parent().css("display","none");
											$("#svg_animation_stoptime").parent().css("display","none");
											}
											//var playtime = getplaytime(pageid,itemid);
											//item.itemstyle.playtime=playtime;
											//getinfototaltimelenght();
										};break;
										case 'left':{
											item.left=values[p];
											//$("#svg_position_X").val(item.left);
											var x2 = parseInt(item.width) + parseInt(item.left);												
											if(x2>screenw)
												{
													var width=screenw - parseInt(item.left);
													//$("#svg_size_width").val(width);
													updateitem(pageid,itemid,["width"],[width]);
												}		
											
											//$("#svg_size_width").attr("max",parseInt(screenw) - item.left);
										};break;
										case 'top':{
											item.top=values[p];
											//$("#svg_position_Y").val(item.top);
											var y2 = parseInt(item.height) + parseInt(item.top); 
											if(y2>screenh)
												{
													var height=screenh - parseInt(item.top);
													//$("#svg_size_height").val(height);
													updateitem(pageid,itemid,["height"],[height]);
												}
											//$("#svg_size_height").attr("max",parseInt(screenh) - item.top);
										};break;
										case 'width':{
											var x2=parseInt(item.left) + parseInt(values[p]);
											if(x2>screenw)
											{
												var width=screenw - parseInt(item.left);
												item.width = width;
												//$("#svg_size_width").val(width);																							
											}
											else
											{
												item.width = values[p];
												//$("#svg_size_width").val(item.width);
											}
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
										};break;
										case 'height':{
											item.height = values[p];
											//$("#svg_size_height").val(item.height);
										};break;
										case 'backcolor':{item.backcolor=values[p];};break;
										case 'backopacity':{item.backopacity=values[p];};break;
										case 'linkmove':{
											item.itemstyle.linkmove=values[p];
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;
										case 'playtype':{
											item.itemstyle.playtype=values[p];
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
										};break;
										case 'playspeed':{
											item.itemstyle.playspeed=values[p];
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;
										case 'rollstop':{item.itemstyle.rollstop=values[p];};break;
										case 'stoptime':{
											item.itemstyle.stoptime=values[p];
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;
										case 'looptime':{
											item.itemstyle.looptime=values[p];
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;
										case 'gamma':{
											item.itemstyle.gamma=values[p];											
											};break;
										case 'Mandatorytime':{
											item.itemstyle.Mandatorytime=values[p];	
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;
										case 'MandatorytimeLength':{
											item.itemstyle.MandatorytimeLength=values[p];	
											var playtime = getplaytime(pageid,itemid);
											item.itemstyle.playtime=playtime;
											getinfototaltimelenght();
											};break;											
										case 'special':{item.itemstyle.special=values[p];};break;
									}
								}						
						break;
						}
				}
		}
}
//改变集合默认前景色背景色并返回json集合
function updateitembfcolorReturnjson(item)
{
	 //var root = UE.htmlparser(item.context, true);
	 var rootNode = tinymce.html.DomParser({validate: true}).parse(item.context);
	 tinyarrNode= new Array();
	 tinymceParseNode(rootNode,null);
	 /*
	 var arrNode = new Array();
	 var isexit=0; //退出循环标示
	 for(var j=0;j<root.children.length;j++)
		 {
		 		var node=root.children[j];
		 		//fontName='SimSun',fontSize=16,forecolor='#000000';backcolor='';
		 		var nodelist=ParseNode(node);
		 		
		 		if(isexit==0)
		 			{
			 		if(nodelist.length>0)
			 			{												 				
			 				for(var n=0;n<nodelist.length;n++)
			 					{
			 						var node=nodelist[n];
			 						if(node.itemType==0)
			 							{
			 								if(node.fontName=='SimSun')
			 									{
			 										switch(node.fontSize)
			 										{
			 										case 16:{
			 											item.fontno=0;
			 											item.forecolor=node.foreColor;
						 								item.backcolor=node.backColor;
						 								isexit=1;
			 										};break;
			 										case 24:{
			 											item.fontno=1;
			 											item.forecolor=node.foreColor;
						 								item.backcolor=node.backColor;
						 								isexit=1;
			 										};break;
			 										case 32:{
			 											item.fontno=2;
			 											item.forecolor=node.foreColor;
						 								item.backcolor=node.backColor;
						 								isexit=1;
			 										};break;												 										
			 										}
			 										if(isexit==1){break}//取到值退出循环
			 									}												 																				 								
			 							}
			 					}
			 			}
		 			}
		 		
		 		arrNode.push(nodelist);
		 }
	 return arrNode;
	 */
	 return tinyarrNode;
}
//删除item
function deleteitem(pageid,itemid)
{
	var returndata={};		
	returndata.isSucess=1;
	var pagecount = Object.getOwnPropertyNames(itemmap).length;
	
	 if(itemmap.hasOwnProperty(pageid))
		{
		 if(pagecount==1)
			{
			 if(itemmap[selectpageid].length>1)
				 {
				 for(var i=0;i<itemmap[pageid].length;i++)
					{
						var item=itemmap[pageid][i]; 
						if(item.itemid==itemid)
							{	
							itemmap[pageid].splice(i, 1);
							returndata.isSucess=0;
							break;							
							}
					}
				 	returndata.pageid=pageid;
					returndata.itemid=itemmap[pageid][0].itemid;		 	
				 }			 
			}
		 else
			 {
				for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i]; 
					if(item.itemid==itemid)
						{	
						itemmap[pageid].splice(i, 1);	
						returndata.isSucess=0;
						break;
						}
				}
				if(itemmap[pageid].length<=0)
					{
						delete itemmap[pageid];
						
						for(var key in itemmap){
							 if(itemmap.hasOwnProperty(key))
								 {
								 	returndata.pageid=key;
									returndata.itemid=itemmap[key][0].itemid;
								 }
							 
						 }									
					}
				else{
					returndata.pageid=pageid;
					returndata.itemid=itemmap[pageid][0].itemid;
				}
			 }
	
		}
	 if(returndata.isSucess==0)
		 {
			 if(infomap.hasOwnProperty(selectinfoid))
			 {
				infomap[selectinfoid].isSave=1;
				
				if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid))
				{		
					var itemid=0;
					for(var i=0;i<itemmap[selectpageid].length;i++)
					{
						var item=itemmap[selectpageid][i];	
						if(item.itemid>itemid)
							{itemid=item.itemid;}						
					}
					updateitem(selectpageid,item.itemid,["linkmove"],[0]);
				}
			 }
		 }
	 return returndata;
}
//删除广告通过id
function infoDeletebyid(infoid)
{	
	$.ajax({  
        url:"/DeleteInfobyid", 
        data:{
        	infoid:infoid,
        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)
        {       	  
        	if(data.result=="success")
        		{        			
    				var count_draft = parseInt($('#badge_draft').text()) - 1;
        			if(count_draft<0){count_draft=0;}
					$('#badge_draft').text(count_draft);					
        			
        			$("#div_"+infoid).remove();
					if(infomap.hasOwnProperty(infoid))
					 {
						delete infomap[infoid]; 
					 }	
					selectinfoid=0;					
					initSvgcanvas();
					$("#layer_list").empty();
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  DeleteInfobyid 错误");          	         
            return false;
          }  
    });
}
//送审核广告通过id
function infoAuditbyid(infoid)
{			
	if(infomap.hasOwnProperty(infoid))
	 {
		if(infomap[infoid].isSave==1)
			{
			var jsonString = JSON.stringify(itemmap);
			$.ajax({  
		        url:"/SaveItem",         
		        data:{
		        	infoid:infoid,
		        	infodata:JSON.stringify(infomap[infoid]),
		        	arritem:jsonString,
		        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        type:"post",  
		        dataType:"json", 
		        success:function(data)  
		        {       	  
		        	if(data.result=="success")
		        		{    
		        			infomap[infoid].isSave=0;
		        			$.ajax({  
		        		        url:"/AuditInfobyid", 
		        		        data:{
		        		        	infoid:infoid,
		        		        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
		        		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
		        					},  
		        		        type:"post",  
		        		        dataType:"json", 
		        		        success:function(data)  
		        		        {       	  
		        		        	if(data.result=="success")
		        		        		{        		              				        		        		
			        		        		$("#div_"+infoid).remove();
			    		        			var count_draft = parseInt($('#badge_draft').text()) - 1;
			    		        			if(count_draft<0){count_draft=0;}
			    							$('#badge_draft').text(count_draft);
			    							
			    							if(infomap.hasOwnProperty(infoid))
			    							 {
			    								delete infomap[infoid]; 
			    							 }
			    							selectinfoid=0;			    							
			    							initSvgcanvas();
			    							$("#layer_list").empty();
			    							
			    							alertMessage(0, "成功", "送审核成功!");
		        		        		}
		        		        	else
		        		        		{
		        		        			alertMessage(1, "警告", data.resultMessage); 	        		        				     			
		        		        		}
		        		        },  
		        		        error: function() { 
		        		        	alertMessage(2, "异常", "ajax 函数  AuditInfobyid 错误"); 		        		        		        		           
		        		            return false;
		        		          }  
		        		    });
		        		}
		        	else
		        		{
		        			alertMessage(1, "警告", data.resultMessage); 		        			
		        		}		        	
		        },  
		        error: function() { 
		        	alertMessage(2, "异常", "ajax 函数  SaveItem 错误"); 		        		           
		          }  
		    });
			}
		else
			{
			$.ajax({  
		        url:"/AuditInfobyid", 
		        data:{
		        	infoid:infoid,
		        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        type:"post",  
		        dataType:"json", 
		        success:function(data)  
		        {       	  
		        	if(data.result=="success")
		        		{        		              		
		        			$("#div_"+infoid).remove();
		        			var count_draft = parseInt($('#badge_draft').text()) - 1;
		        			if(count_draft<0){count_draft=0;}
							$('#badge_draft').text(count_draft);
							
							if(infomap.hasOwnProperty(infoid))
							 {
								delete infomap[infoid]; 
							 }
							alertMessage(0, "成功", "送审核成功!");
		        		}
		        	else
		        		{		        			
		        			alertMessage(1, "警告", data.resultMessage);
		        		}
		        },  
		        error: function() {  		        	
		        	alertMessage(2, "异常", "ajax 函数  AuditInfobyid 错误"); 
		            return false;
		          }  
		    });
			}
	 }
}
//保存信息
function infoSavebyid(infoid)
{
	saveitem(infoid);
}
//广告详情
function infoAttributebyid(infoid)
{
	if(infomap.hasOwnProperty(infoid))
	 {
		$('#imgSel div').remove();
		
		 $('#creat_infoname').val(infomap[infoid].advname);
		 $('#creat_infolifeAct').val(infomap[infoid].lifeAct);//creat_lifeAct
		 $('#creat_infolifeDie').val(infomap[infoid].lifeDie);//creat_lifeDie
		 
		 var bjsJson = JSON.parse(infomap[infoid].BackgroundStyle);
	     		 
	     var bmslist = bjsJson.basemapStyle;
	     if(bmslist!=null && bmslist.length>0)
	     	{
	 	    for(var i=0;i<bmslist.length;i++)
	 	    	{
	 	    		var bms = bmslist[i];
	 	    		var basemalistmode = bms.basemalistmode;
	 	    		   		
	 	    		switch(basemalistmode)
	 	    		{
		 	    		case 2:{
		 	    			$('#basemapselect').val('背景色');
		 	    			 $('#div_backcolor').css('display','inline');
		 					 $('#div_background').css('display','none');
		 	    			var basemabackcolor = bms.basemabackcolor;    
		 	    			$('#div_backcolor').val(basemabackcolor);		 	    			
		 	    			break;
		 	    		};break;
		 	    		case 3:{
		 	    			$('#basemapselect').val('背景图');
		 	    			$('#div_backcolor').css('display','none');
		 					$('#div_background').css('display','inline');
		 	    			var basemalistsn = bms.basemalistsn;
		 	    			var basemalistsrc = bms.basemalistsrc;
		 	    			var basemalistmapstyle = bms.basemalistmapstyle;
		 	    			
		 	    			addimg2div2('#imgSel',basemalistsn,basemalistmapstyle,basemalistsrc,false);
		 	    		};break;
	 	    		}    		
	 	    	}
	     	}
	     else
	     	{
	    	 $('#basemapselect').val('无背景');
	    	 $('#div_backcolor').css('display','none');
			 $('#div_background').css('display','none');
	     	
	     	 $("#workarea_canvas").css("background-image","");    			
	 		 $("#workarea_canvas").css("background-size","");
	 		 $("#workarea_canvas").css("background-repeat","");
	     	 $("#workarea_canvas").css("background-color","#ffffff");
	     	}
	 }
	
	$("#modal_creatinfo").attr("data-type","update_info");
	$('#modal_creatinfo').modal('show');
}
//新建广告
function infoCreate()
{
	$("#creat_infoname").val("");
	$("#basemapselect").val('无背景');			
	$('#div_backcolor').css('display','none');
	$('#div_background').css('display','none');
	$('#imgSel div').remove();	
	
	$("#modal_creatinfo").attr("data-type","create_info");
	$('#modal_creatinfo').modal('show');		
}
//复制信息
function infoCopybyid(infoid)
{
	if(infomap.hasOwnProperty(infoid))
	 {
		var infoName=infomap[infoid].advname;
		var groupid=infomap[infoid].groupid;
		var lifeAct=infomap[infoid].lifeAct;
		var lifeDie=infomap[infoid].lifeDie;
		$.ajax({  
	        url:"/CopyInfo", 
	        data:{
	        	infoName:infoName,
	        	groupid:groupid,
	        	lifeAct:lifeAct,
	        	lifeDie:lifeDie,
	        	BackgroundStyle:infomap[infoid].BackgroundStyle,
	        	itemlist:JSON.stringify(itemmap),
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success")
	        		{
	        			var id = data.infoID;
	        			var advname = data.infoName;	        			

						adddivinfo(id,advname);
						
						addinfomap(id,advname,data.groupid,lifeAct,lifeDie,data.advType,data.BackgroundStyle,data.playMode,data.pubid,0,data.DelIndex,1);
						
						infoSelectChange(id);
	        		}
	        	else
	        		{
	        			alertMessage(1, "警告", data.resultMessage); 	        			
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  CopyInfo 错误"); 	        	           
	          }  
	    });
	 }
}
//计算播放时长
function getplaytime(pageid,itemid)
{
	var playtime=0;
	 if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i];
					if(item.itemid==itemid && item.itemstyle.linkmove==0)
						{	
						
						var playtype = item.itemstyle.playtype;
						
						var result;
						
						switch(item.type)
						{
						case 0:{
							result = getitemsize(item.width,item.contextJson,item.itemstyle.playtype);
							if(item.itemstyle.Mandatorytime!=null && item.itemstyle.Mandatorytime!=0)
								{								
								if(playtype==1)//左滚
								{
									var width=result.itemw + parseInt(item.width);
									var min = Math.floor(parseFloat(0.005 * width));
									if(min<1){min=1;}
									var max = Math.floor(parseFloat(1 * width));
									if(item.itemstyle.MandatorytimeLength<min || item.itemstyle.MandatorytimeLength>max)
										{										
										alertMessage(1, "警告", "当前显示项时间范围在"+min+"---"+max+"之间");
										item.itemstyle.MandatorytimeLength = min;
										$('#svg_animation_MandatorytimeLength').val(min);
										}
								}
								playtime = item.itemstyle.MandatorytimeLength * item.itemstyle.looptime;
								}
							else {																						
							//itemw
							if(playtype==1)//左滚
							{
								var plt=0;
								//playtime=result.Srceencount * 3 * item.itemstyle.looptime;
								var width=result.itemw
								if(item.itemstyle.stoptime==0)//停留时间为0，显示项长度加一屏
									{
									width += parseInt(item.width);									
									}
								else
									{
									//item.itemstyle.rollstop//停留方式0最终1每屏
									//item.itemstyle.stoptime//停留时间
									if(item.itemstyle.rollstop==0)
										{plt += parseFloat(item.itemstyle.stoptime);}
									else
										{plt += result.Srceencount * parseFloat(item.itemstyle.stoptime);}
									}
								
								var playspeed = parseInt(item.itemstyle.playspeed);
								var pl=0;
								switch(playspeed)
								{
									case 0:{
										pl = parseFloat(0.005 * width);
									};break
									case 1:{
										pl = parseFloat(0.01 * width);
									};break
									case 2:{
										pl = parseFloat(0.015 * width);
									};break
									case 3:{
										pl = parseFloat(0.02 * width);
									};break
									case 4:{
										pl = parseFloat(0.025 * width);
									};break
									case 5:{
										pl = parseFloat(0.03 * width);
									};break
									case 6:{
										pl = parseFloat(0.035 * width);
									};break
									case 7:{
										pl = parseFloat(0.04 * width);
									};break
									case 8:{
										pl = parseFloat(0.05 * width);
									};break
									case 9:{
										pl = parseFloat(0.06 * width);
									};break
									case 10:{
										pl = parseFloat(0.07 * width);
									};break
								}
								pl = parseFloat(pl.toFixed(2));
								plt += pl;
								playtime = parseFloat(plt * item.itemstyle.looptime);
							}
							else//其他
							{playtime = parseFloat( parseInt(result.Srceencount) * parseFloat(item.itemstyle.stoptime) * parseInt(item.itemstyle.looptime));}
							}
						};break;
						case 3:{
							var contextJson = JSON.parse(item.contextJson);
							for(var c=0;c<contextJson.length;c++)
								{
								var alt = JSON.parse(contextJson[c][0].alt);
								playtime += parseFloat(alt.duration);
								}
						};break;
						case 4:{
							var contextJson = JSON.parse(item.contextJson);
							for(var c=0;c<contextJson.length;c++)
								{								
								playtime += Math.floor(parseFloat(contextJson[c][0].giftimelength)) * item.itemstyle.looptime;
								}
						};break;
						default:{};break;
						}						
						break;
						}
				}
		}
	 $('#list_page'+pageid+'_itempt'+itemid).text(playtime.toFixed(2) +"|");
	 
	 return parseFloat(playtime.toFixed(2));
}
//计算广告总时长
function getinfototaltimelenght()
{
	var tplt=0;
	 for(var key in itemmap)
	 {
		for(var i=0;i<itemmap[key].length;i++)
		{
			var item=itemmap[key][i];
			var ptl = parseFloat(item.itemstyle.playtime);
			tplt += ptl;
		}
	 }  
	  
	 var atp = "<a style='font-size:10px;color:white;'>广告总时长:"+tplt.toFixed(2)+"秒</a>";
	 $('#layer_timelength').empty();
	 $('#layer_timelength').append(atp);
	 //$('#info_timelength').text("广告总时长:"+ tplt.toFixed(2) +"秒");
}
//计算item行数,长度
function getitemsize(itemw,contextJson,type)
{
	var scaleX = 1;
	if(selectspecial!=null && selectspecial.scale!=null)
		{
		scaleX = selectspecial.scale.scaleX/100;
		}	
	var contextJson = JSON.parse(contextJson);	
	var Srceencount=0,totalw=0,left = 0;
	if(type==1)
		{
		for(var r=0;r<contextJson.length;r++)
		{											
			var rownode=contextJson[r];
			var arrRow = new Array()
			var rowwidth = 0,left =	totalw;
			for(var i=0;i<rownode.length;i++)
			{
			var itemnode=rownode[i];			
			
			if(itemnode.itemType == 0)
				{			
			    var txtwidth=0;
			    var str = itemnode.value;
			    for(var j = 0; j < str.length; j++){
			    	var charw=0;
				    if (str.charCodeAt(j) > 128/* && str.charCodeAt(j)!=160*/) {
				    	charw=itemnode.fontSize;
				    	txtwidth += charw;
				    } 
				    else {
				    	charw=itemnode.fontSize/2;
				    	txtwidth += charw;
				    }
			    }
			    
			    rowwidth += parseInt(txtwidth * scaleX);
				}
			else
				{
				var nodewidth = parseInt(itemnode.width);			
				var w = nodewidth;
				 rowwidth += w;
				}										
			}
			totalw +=rowwidth;
		}
		var rowCount = parseInt(Math.ceil(totalw/8));
		totalw = parseInt(rowCount*8);
		}
	else {
		for(var r=0;r<contextJson.length;r++)
		{											
			var rownode=contextJson[r];
			var arrRow = new Array()
			var rowwidth = 0,left =	0;
			for(var i=0;i<rownode.length;i++)
			{
			var itemnode=rownode[i];			
			
			if(itemnode.itemType == 0)
				{						    
			    var str = itemnode.value;
			    for(var j = 0; j < str.length; j++){
			    	var charw=0;
				    if (str.charCodeAt(j) > 128/*&& str.charCodeAt(j)!=160*/) {
				    	charw=itemnode.fontSize;				    	
				    } 
				    else {
				    	charw=itemnode.fontSize/2;
				    	
				    }		
				    
				    charw = parseInt(charw * scaleX);
				    if(left%itemw!=0 && Math.floor(left/itemw) != Math.floor((left + charw - 1)/itemw))
		        	{			        		
//		        		left = Math.floor((left + charw)/itemw) * itemw + charw;
		        		left = Math.floor(left / itemw + 1) * itemw + charw;
		        	}
		        	else {				        		
		        		left += charw;	
					}		
			    }
			    rowwidth = left;
				}
			else
				{
				var nodewidth = parseInt(itemnode.width);			
				var w = nodewidth;
				 if(left%itemw!=0 && Math.floor(left/itemw) != Math.floor((left + w - 1)/itemw))
		        	{				        	
//		        		left = Math.floor((left + w)/itemw)*itemw + w;
		        		left = Math.floor(left / itemw + 1) * itemw + w;
		        	}
		        	else {						        				        		
		        		left += w;	
					}		
				 rowwidth = left;
				}																	
			}
			var rowCount = parseInt(Math.ceil(rowwidth/itemw));			
			totalw +=rowCount*itemw;
		}
	}

	
	Srceencount = Math.ceil(totalw/itemw);
	var result={
			Srceencount:Srceencount,
			itemw:totalw
	};
	return result;
}
//获取敏感词汇
function getsensitive()
{		
	var sensitivelist = JSON.parse(sessionStorage.getItem('sensitivelist'));
	if(sensitivelist==null || sensitivelist.length<=0)
		{
		$.ajax({  
	        url:"/getSensitive",         
	        data:{        	
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname       	
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success" && data.sensitivelist!=null)
	        		{ 
	        		$('#svlist').empty();
	        		sensitivelist = JSON.parse(data.sensitivelist);
	        		sessionStorage.setItem('sensitivelist', data.sensitivelist);
	        		for(var i=0;i<sensitivelist.length;i++)
	        			{
	        			var svid = sensitivelist[i].svid;
	        			var projectid = sensitivelist[i].projectid;
	        			var svstring = sensitivelist[i].svstring;
	        			$('#svlist').append('<option value='+svid+' data-projectid ="'+projectid+'">'+svstring+'</option>');
	        			}
	        		}
	        	else
	        		{
	        			alertMessage(1, "异常", data.resultMessage);				        			
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  getSensitive 错误");			        	            
	          }  
	    });	
		}
	else {
		$('#svlist').empty();
		for(var i=0;i<sensitivelist.length;i++)
		{
		var svid = sensitivelist[i].svid;
		var projectid = sensitivelist[i].projectid;
		var svstring = sensitivelist[i].svstring;
		$('#svlist').append('<option value='+svid+' data-projectid ="'+projectid+'">'+svstring+'</option>');
		}
	}
}
//
function SendCallback(SN,infocodelist,i)
{
	var timesRun = 0,reSend = 0;
	var interval = setInterval(function(){
		if(infocodelist.length > i)
		{		    
//		    timesRun += 1;  
//		    if(timesRun >= 10){    
//		    	$('#progress').css('height','0px');				
//		        var closeJsonObj={
//		    			command:"closeSerialPort",
//						commandSN:getSN()
//		    		};	        
//		        wssend(JSON.stringify(closeJsonObj));		    	
//		    	alertMessage(1, "警告", "通讯不畅");
//		        clearInterval(interval);    		        
//		    }
		    
		    if(receiveMap[SN]!=null)
			{
				receiveData=receiveMap[SN]; 
				console.log(receiveData.returnData);
				var prs = (i+1)/parseFloat(infocodelist.length);
				prs = parseFloat(prs.toFixed(2)) *100;
				$('#progress .progress-bar').css("width",prs+"%")
				$('#progress .progress-bar span').text( (i+1) +"/" +infocodelist.length);
				var newSN = getSN();
				i = i + 1;
				sendinfoDataCallback(newSN,infocodelist,i,SendCallback(newSN,infocodelist,i));
				
				
				clearInterval(interval);
			}
		    
		    timesRun += 1;  
		    if(timesRun >= 10){ 		    	
		    	var JsonObj={
						command:"sendSerialData",
						commandSN:SN,
						sendData:infocodelist[i]
					};
			    wssend(JSON.stringify(JsonObj));
			    
		    	reSend += 1;
		    }
		    
		    if(reSend >= 3)
		    	{
		    	$('#progress').css('height','0px');
		    	var closeJsonObj={
		    			command:"closeSerialPort",
						commandSN:getSN()
		    		};	        
		        wssend(JSON.stringify(closeJsonObj));		    	
		    	alertMessage(1, "警告", "通讯不畅");
		        clearInterval(interval); 
		    	}
		}
		else{clearInterval(interval);}
	}, 500);
}
//串口发布广告通过id
function infoSerialPublishbyid(infoid)
{				
	$('#progress').css('height','20px');
	
	var jsonString = JSON.stringify(itemmap);
			
	$.ajax({  
        url:"/getPublishInfobyid", 
        data:{
        	infoid:infoid,         	
        	infodata:JSON.stringify(infomap[infoid]),
        	arritem:jsonString,        	
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{        
        		var infocodelist = data.infocodelist;
        		var plcodelist = data.plcodelist;
        		var sendArray=[];
        		sendArray.push("7E 46 47 4A 00 36 01 01 00 01 00 00 41 61 00 00 00 00 00 00 00 00 00 00 64 65 6C 65 00 00 00 00 E8 5E 13 03 0C 0F 30 01 02 B4 00 00 00 00 00 00 00 00 00 00 00 00 45 4E 7E");
        		sendArray = sendArray.concat(infocodelist);
        		sendArray = sendArray.concat(plcodelist);
        		sendArray.push("7E 46 47 4A 00 36 01 01 00 01 00 00 41 61 00 00 00 00 00 00 00 00 00 00 71 61 64 76 00 00 00 00 BB 58 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 45 4E 7E"); 
        		var length=sendArray.length;
        		var SN = getSN();
        		sendinfoDataCallback(SN,sendArray,0,SendCallback(SN,sendArray,0));        		
        		}
        	else
        		{
	        		var SN = getSN();
			        var closeJsonObj={
			    			command:"closeSerialPort",
							commandSN:SN
			    		};	        
			        wssend(JSON.stringify(closeJsonObj));
			        $('#progress').css('height','0px');
			        alertMessage(1, "警告", data.resultMessage);	    			     		
        		}        	
        },  
        error: function() {  
        	var SN = getSN();
	        var closeJsonObj={
	    			command:"closeSerialPort",
					commandSN:SN
	    		};	        
	        wssend(JSON.stringify(closeJsonObj));
	        $('#progress').css('height','0px');
	        alertMessage(2, "异常", "ajax 函数  getPublishInfobyid 错误");        	        
          }  
    });    	 
}
//发送串口数据
function SendSerialData(infoid)
{
	var SerialPort = localStorage.getItem("SerialPort");
	if(SerialPort!=null && SerialPort!="")
		{
		intiWebSocket();
		        		        		
		ws.addEventListener('open', function () { 
		
			var jsonSerialPort;
			try
			{jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));}
			catch(err){
				$("#modal_SerialPort .modal-title").text("串口不存在或异常,选择其他串口");
    			$("#modal_SerialPort").modal('show');
			}
			    			
    		var ComName = jsonSerialPort.ComName;
    		var BaudRate = jsonSerialPort.BaudRate;
    		
    		var SN = getSN();
	        var JsonObj={
	    			command:"openSerialPort",
					commandSN:SN,
					serialPortName:ComName,
					baudRate:parseInt(BaudRate)
	    		};	        
	    	sendDataCallback(JsonObj,function(JsonObj){
	    		
	    		var timesRun = 0;
	    		var interval = setInterval(function(){
	    		    timesRun += 1; 
	    		    if(timesRun >= 10){  
	    				var SN = getSN();
	    		        var closeJsonObj={
	    		    			command:"closeSerialPort",
	    						commandSN:SN
	    		    		};	        
	    		        wssend(JSON.stringify(closeJsonObj));	    		        
	    		        alertMessage(1, "警告", "通讯不畅");
	    		        clearInterval(interval);    		        
	    		    }
	    		    
	    		    if(receiveMap[JsonObj.commandSN]!=null)
	        		{
	        		receiveData=receiveMap[JsonObj.commandSN]; 
	        		if(receiveData.SerialPort)
	        			{
	        			//串口打开发广告
	        			infoSerialPublishbyid(infoid);
	        			}
	        		else
	        			{
	        			//串口打不开设置
	        			$("#modal_SerialPort .modal-title").text("串口不存在或异常,选择其他串口");
	        			$("#modal_SerialPort").modal('show');
	        			}
	        		clearInterval(interval);
	        		}			
	    		    
	    		    //do whatever here..
	    		}, 500);    		        	
	    	});
		});
		}
	else
		{      
		$("#modal_SerialPort .modal-title").text("设置串口");
    	$("#modal_SerialPort").modal('show');
		}				
}