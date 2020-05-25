$(function(){
	init_modal();
	
	init_modal_special();
	
	var opts = {            
        lines: 13, // 花瓣数目
        length: 20, // 花瓣长度
        width: 10, // 花瓣宽度
        radius: 30, // 花瓣距中心半径
        corners: 1, // 花瓣圆滑度 (0-1)
        rotate: 0, // 花瓣旋转角度
        direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
        color: '#000', // 花瓣颜色
        speed: 1, // 花瓣旋转速度
        trail: 60, // 花瓣旋转时的拖影(百分比)
        shadow: false, // 花瓣是否显示阴影
        hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
        className: 'spinner', // spinner css 样式名称
        zIndex: 2e9, // spinner的z轴 (默认是2000000000)
        top: '25%', // spinner 相对父容器Top定位 单位 px
        left: '50%'// spinner 相对父容器Left定位 单位 px
    };

    spinner = new Spinner(opts);
});

var spinner;
//按钮初始化
function init_modal()
{	
	$("#modal_creatinfo").on("shown.bs.modal",function(){		
		document.getElementById('creat_infoname').focus();
		
		$("#creat_infolifeAct").datetimepicker({
			format: 'yyyy-mm-dd',
			minView: "month", //选择日期后，不会再跳转去选择时分秒 
			autoclose: true,
	        todayBtn: true,
	        language: 'zh-CN',
	        pickerPosition: "bottom-left"
		});
		
		$("#creat_infolifeDie").datetimepicker({
			format: 'yyyy-mm-dd',
			minView: "month", //选择日期后，不会再跳转去选择时分秒 
			autoclose: true,
	        todayBtn: true,
	        language: 'zh-CN',
	        pickerPosition: "bottom-left"
		});
	});
	
	$("#modal_picnav_add").on("shown.bs.modal",function(){
		var data_type = $("#modal_picnav_add").attr("data-type");
		switch(data_type)
		{
		case "add_pic":{$("#model_classifyname").val("");};break;
		case "update_pic":{
			var oldclassifyname = $('#nav_pictype .active')[0].text;
			$("#model_classifyname").val(oldclassifyname);
		};break;
		case "add_video":{$("#model_classifyname").val("");};break;
		case "update_video":{
			var oldclassifyname = $('#nav_videotype .active')[0].text;
			$("#model_classifyname").val(oldclassifyname);
		};break;
		}		
		document.getElementById('model_classifyname').focus();
		});
	
	$("#btn_CreatInfo").click(function(){	

		var infoname = $("#creat_infoname").val().trim();
		var lifeAct = $("#creat_infolifeAct").val().trim();
		var lifeDie = $("#creat_infolifeDie").val().trim();
		
		if(infoname==null || infoname=="")
			{alertMessage(1, "警告", "广告名称不能为空!");return;}
		if(lifeAct>lifeDie)
			{alertMessage(1, "警告", "开始日期不能大于结束日期!");return;}
		
		var strbackgroundStyle = GetbackgroundStyle();		
		
		var datatype = $("#modal_creatinfo").attr("data-type");
		if(datatype=="create_info")//新建
			{
			$.ajax({  
		        url:"/CreatInfo", 
		        data:{
		        	infoName:infoname,
		        	groupid:parseInt($("#grouplist").val()),
		        	lifeAct:lifeAct,
		        	lifeDie:lifeDie,
		        	BackgroundStyle:strbackgroundStyle,
		        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        type:"post",  
		        dataType:"json", 
		        success:function(data)  
		        {       	  
		        	if(data.result=="success")
		        		{
		        			var id = parseInt(data.infoID);
		        			var advname = data.infoName;	        			
		        		    
							adddivinfo(id,advname,0);
							
	    					$("#modal_creatinfo").modal('hide');
	    					
	    					addinfomap(id,advname,data.groupid,$("#creat_infolifeAct").val().trim(),$("#creat_infolifeDie").val().trim(),data.advType,data.BackgroundStyle,data.playMode,data.pubid,0,data.DelIndex,1);
	    					
	    					infoSelectChange(id);
		        		}
		        	else
		        		{		        			
		        			alertMessage(1, "警告", data.resultMessage);
		        		}
		        },  
		        error: function() { 
		        	alertMessage(2, "异常", "ajax 函数  CreatInfo 错误");		        		           
		          }  
		    });
			}
		else//修改
			{
			var isChange=0;
			
			if(selectinfoid!=0 && infomap.hasOwnProperty(selectinfoid))
			{
				if(infoname!=infomap[selectinfoid].advname)
				{isChange=1;}
				if(lifeAct!=infomap[selectinfoid].lifeAct)
				{isChange=3;}
				if(lifeDie!=infomap[selectinfoid].lifeDie)
				{isChange=3;}
				if(strbackgroundStyle!=infomap[selectinfoid].BackgroundStyle)
				{isChange=2;}
				
				if(isChange==0)
					{return;}
				
				var newadvname = infoname;
				var advname=infomap[selectinfoid].advname;
				var isc=false;
				for (var key in infomap) {
					if(key!=selectinfoid && infomap[key].advname==newadvname)
						{isc=true;break;}
				}
				
				if(isc)
				{					
					alertMessage(1, "警告", "广告名称"+newadvname+"已存在");
					$("#creat_infoname").val(advname);
					return
				}
				
				if(infomap[selectinfoid].pubid!=0)
					{					
						$("#modal_save").attr("data-type","update_info");
						$('#modal_save').modal('show');						
					}
				else
					{
					$.ajax({  
				        url:"/UpdateInfo", 
				        data:{
				        	infoid:selectinfoid,
				        	pubid:infomap[selectinfoid].pubid,
				        	infoName:infoname,
				        	groupid:parseInt($("#grouplist").val()),
				        	lifeAct:lifeAct,
				        	lifeDie:lifeDie,
				        	BackgroundStyle:strbackgroundStyle,
				        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
				        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
							},  
				        type:"post",  
				        dataType:"json", 
				        success:function(data)  
				        {       	  
				        	if(data.result=="success")
				        		{		
				        			if(data.returnid!=null)
				        				{				        										        				
				        				if(infomap.hasOwnProperty(selectinfoid))
					   					 {
				        					addinfomap(data.returnid,infoname,parseInt($("#grouplist").val()),lifeAct,lifeDie,infomap[selectinfoid].advType,strbackgroundStyle,infomap[selectinfoid].playMode,0,0,infomap[selectinfoid].playTimelength,0);
					   						delete infomap[selectinfoid]; 
					   					 }
				        				
										infoSelectChange(data.returnid);
				        				}
				        			else
				        				{
				        				infomap[selectinfoid].advname = infoname;
										$("#info_"+selectinfoid).text(infoname);
										
										infomap[selectinfoid].lifeAct=lifeAct;
										infomap[selectinfoid].lifeDie=lifeDie;
										
										infomap[selectinfoid].BackgroundStyle=strbackgroundStyle;
										if(isChange==2)
											{
											updataSvgcanvas();								
											updataAlllistbackground();	
											}
				        				}				        			
			    					$("#modal_creatinfo").modal('hide');	    					
				        		}
				        	else
				        		{
				        			alertMessage(1, "警告", data.resultMessage);				        			
				        		}
				        },  
				        error: function() {  
				        	alertMessage(2, "异常", "ajax 函数  UpdateInfo 错误");				        				            
				          }  
				    });
					}
			}			
			}		
	});	
	//上传图片
	$('#input_upload').change(function(){
		input_upload(this);
	});
	//上传动画
	$('#input_gif_upload').change(function(){
		input_gif_upload(this);
	});
	//上传视频
	$('#input_video_upload').change(function(){
		
		var video = this.files[0];  
	    var url = URL.createObjectURL(video);  
	    //console.log(url);  	    
	    
	    $("#video_player").empty();		
		
		//{"id":9,"fileName":"123.mp4","contentType":"video/mp4"}
		var video ="<source src='"+url+"'>";
		$("#video_player").append(video);

		//input_video_upload(this);
	});
	//背景选择
	$("#basemapselect").change(function() {
		var basemap = $("#basemapselect").val();
		switch(basemap)
		{
		case "无背景":{
			$('#div_backcolor').css('display','none');
			$('#div_background').css('display','none');		
		};break;
		case "背景色":{
			$('#div_backcolor').css('display','inline');
			$('#div_background').css('display','none');			
		};break;
		case "背景图":{
			$('#div_backcolor').css('display','none');
			$('#div_background').css('display','inline');			
		};break;
		}
	});
	
	//图片选择
	$("#btn_selectimg").click(function(){
		$("#div_imglist .basemapStyle input").each(function(){
			if(this.checked)
				{
					var data_imgtype = $('#modal_pic_select').attr("data-imgtype");
					var divid=$(this).parent()[0].id;
					
					var src = $("#"+divid+" img")[0].src;
					var datamapstyle = $("#"+divid+" img")[0].dataset.mapstyle;
					if(data_imgtype==1)
						{						
						var img="<img src='"+src+"' data-mapstyle='"+datamapstyle+"'>";					
						//ue.execCommand('insertHtml', img);	
						tinymce.execCommand('mceInsertContent', false, img);
						}
					else
						{
							var pid = "#" + $('#modal_pic_select').attr("data-parentdivid");
							var basemapid =divid.substring(divid.indexOf("basemap") + 7);
							addimg2div2(pid,basemapid,datamapstyle,src,true);							
						}
				}
		});			
		
		$('#modal_pic_select').modal('hide');		
	});
	//动画选择
	$("#btn_selectgif").click(function(){
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
		
		$('#modal_gif_select').modal('hide');
		$('#myModalEdit').modal('hide');
	});
	//视频选择
	$("#btn_selectvideo").click(function(){
		$("#div_videolist .basemapStyle input").each(function(){
			if(this.checked)
				{				
					var divid=$(this).parent()[0].id;	
					var selimg=$("#"+divid+" img")[0];
					var src = selimg.src;
					var fiename=selimg.alt;
															
					var datamapstyle = $("#"+divid+" img")[0].dataset.mapstyle;
					
					var context="<p><img src='"+src+"' alt='"+fiename+"' data-mapstyle='"+datamapstyle+"'/></p>";
					
					updateitem(selectpageid,selectitemid,["itemtype","context"],[3,context]);
					updateCanvasItem(selectpageid,selectitemid,true,false);
				}
		});			
		
		$('#modal_video_select').modal('hide');
		$('#myModalEdit').modal('hide');		
	});
	//特效确定按钮
	$("#btn_selectspecial").click(function(){		
		btn_selectspecial();	
	});
	//添加图片分类	
	$("#add_pic_nav").click(function(){	
		$("#modal_picnav_add").modal('show');	
		$("#modal_picnav_add").attr("data-type","add_pic");
	});
	//添加视频分类
	$("#add_video_nav").click(function(){	
		$("#modal_picnav_add").modal('show');	
		$("#modal_picnav_add").attr("data-type","add_video");
	});
	//添加分类
	$("#btn_addnav").click(function(){	
		btn_addnav();
	});	
	//确认保存广告
	$("#btn_saveModel").click(function(){
		if(infomap.hasOwnProperty(selectinfoid))
		 {			
			var datatype = $("#modal_save").attr("data-type");
			if(datatype=="update_item")//修改条目
				{
				var jsonString = JSON.stringify(itemmap);
				$.ajax({  
			        url:"/SaveItem",         
			        data:{
			        	infoid:selectinfoid,
			        	infodata:JSON.stringify(infomap[selectinfoid]),
			        	arritem:jsonString      	
						},  
			        type:"post",  
			        dataType:"json", 
			        success:function(data)  
			        {       	  
			        	if(data.result=="success")
		        		{    
			        		if(data.returnid!=null)
	        				{				        										        				
	        				if(infomap.hasOwnProperty(selectinfoid))
		   					 {
	        					addinfomap(data.returnid,infomap[selectinfoid].advname,parseInt($("#grouplist").val()),infomap[selectinfoid].lifeAct,infomap[selectinfoid].lifeDie,infomap[selectinfoid].advType,infomap[selectinfoid].BackgroundStyle,infomap[selectinfoid].playMode,0,0,infomap[selectinfoid].playTimelength,0);
		   						delete infomap[selectinfoid]; 
		   						
		   						var count_publish = parseInt($('#badge_publish').text()) - 1;
								if(count_publish<0){count_publish=0;}							
								$('#badge_publish').text(count_publish);
																								
								$("#div_"+selectinfoid).remove();							
								adddivinfo(data.returnid,infomap[data.returnid].advname, 0);	
		   					 }
	        				
							infoSelectChange(data.returnid);
	        				}
		        			else
	        				{
			        			infomap[selectinfoid].isSave=0;
			        						        									
								var count_publish = parseInt($('#badge_publish').text()) - 1;
								if(count_publish<0){count_publish=0;}							
								$('#badge_publish').text(count_publish);
								
								if(infomap.hasOwnProperty(selectinfoid))
								{
									 infomap[selectinfoid].pubid = 0; 
								}
								var infoname = $("#info_"+selectinfoid).text();
								$("#div_"+selectinfoid).remove();							
								adddivinfo(selectinfoid,infoname, 0);								
	        				}
							$('#modal_save').modal('hide');
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
			else//修改广告信息
				{
				var advname = $("#creat_infoname").val().trim();
				var lifeAct = $("#creat_infolifeAct").val().trim();
				var lifeDie = $("#creat_infolifeDie").val().trim();
				
				if(advname == null || advname == "")
					{return;}				
				var strbackgroundStyle = GetbackgroundStyle();
				$.ajax({  
			        url:"/UpdateInfo", 
			        data:{
			        	infoid:selectinfoid,
			        	pubid:infomap[selectinfoid].pubid,
			        	infoName:advname,
			        	groupid:parseInt($("#grouplist").val()),
			        	lifeAct:lifeAct,
			        	lifeDie:lifeDie,
			        	BackgroundStyle:strbackgroundStyle
						},  
			        type:"post",  
			        dataType:"json", 
			        success:function(data)  
			        {       	  
			        	if(data.result=="success")
			        		{
		        			if(data.returnid!=null)
	        				{				        										        				
	        				if(infomap.hasOwnProperty(selectinfoid))
		   					 {
	        					addinfomap(data.returnid,advname,parseInt($("#grouplist").val()),lifeAct,lifeDie,infomap[selectinfoid].advType,strbackgroundStyle,infomap[selectinfoid].playMode,0,0,infomap[selectinfoid].playTimelength,0);
		   						delete infomap[selectinfoid]; 
		   						
		   						var count_publish = parseInt($('#badge_publish').text()) - 1;
								if(count_publish<0){count_publish=0;}							
								$('#badge_publish').text(count_publish);
																								
								$("#div_"+selectinfoid).remove();							
								adddivinfo(data.returnid,advname, 0);			
		   					 }
	        				
							infoSelectChange(data.returnid);
	        				}
		        			else
	        				{
			        			infomap[selectinfoid].advname = advname;
								$("#info_"+selectinfoid).text(advname);
								
								infomap[selectinfoid].lifeAct=lifeAct;
								infomap[selectinfoid].lifeDie=lifeDie;
								
								infomap[selectinfoid].BackgroundStyle=strbackgroundStyle;
								
								var count_publish = parseInt($('#badge_publish').text()) - 1;
								if(count_publish<0){count_publish=0;}							
								$('#badge_publish').text(count_publish);
								
								if(infomap.hasOwnProperty(selectinfoid))
								{
									 infomap[selectinfoid].pubid = 0; 
								}
								var infoname = $("#info_"+selectinfoid).text();
								$("#div_"+selectinfoid).remove();							
								adddivinfo(selectinfoid,infoname, 0);								
        					}
							$('#modal_save').modal('hide');
							
	    					$("#modal_creatinfo").modal('hide');	    					
		        		}
			        	else
		        		{		        			
		        			alertMessage(1, "警告", data.resultMessage);
		        		}
			        },  
			        error: function() {  
			        	alertMessage(2, "异常", "ajax 函数  UpdateInfo 错误");			        				            
			          }  
			    });
				}									
		 }
	});
	//确认广告
	$("#btn_saveModelColse").click(function(){	
		var datatype = $("#modal_save").attr("data-type");
		if(datatype=="update_item")//修改条目
			{}
		else
			{}
	});		
	
	$("#btn_gif_back").click(function() {
		$("#div_tp").css("display","inline");
		$("#div_gif").css("display","none");
		$("#myModalEdit").attr("data-type",0);
	});	
	
	$("#btn_gif_add").click(function() {
		$('#input_gif_upload').click();
	});	
	
	$("#btn_gif_remove").click(function() {
		var length = $("#div_giflist .basemapStyle input:checked").length;
		if(length>0)
			{ 
			var basemapidString = "";
			$("#div_giflist .basemapStyle input:checked").each(function(){
				var basemapid = $(this).parent().attr("id").substring(7);
				basemapidString += basemapid + ",";
			});
			if(basemapidString.length>0)
				{
				basemapidString = basemapidString.substring(0,basemapidString.length - 1);
				}
			
			$.ajax({  
		        url:"/deletebasemapbyids",         
		        data:{
		        	basemapids:basemapidString,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        type:"post",  
		        dataType:"json", 
		        success:function(data)  
		        {       	  
		        	if(data.result=="success")
		        		{    		        			
		        			var currentPage = $('#pagination').data('pagination').currentPage + 1;
		        			
		        			initgif(currentPage,9,true);		        			
		        		}
		        	else
		        		{        			
		        			alertMessage(1, "警告", "删除图片失败:"+data.resultMessage);
		        		}
		        },  
		        error: function() {
		        	alertMessage(2, "异常", "ajax 函数  deletebasemapbyids 错误");        	           
		          }  
		    });
			}
	});
	
	$("#btn_pic_remove").click(function() {
		var length = $("#div_imglist .basemapStyle input:checked").length;
		if(length>0)
			{ 
			var basemapidString = "";
			$("#div_imglist .basemapStyle input:checked").each(function(){
				var basemapid = $(this).parent().attr("id").substring(7);
				basemapidString += basemapid + ",";
			});
			if(basemapidString.length>0)
				{
				basemapidString = basemapidString.substring(0,basemapidString.length - 1);
				}
			
			$.ajax({  
		        url:"/deletebasemapbyids",         
		        data:{
		        	basemapids:basemapidString,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        type:"post",  
		        dataType:"json", 
		        success:function(data)  
		        {       	  
		        	if(data.result=="success")
		        		{    		        			
		        			var currentPage = $('#pagination_pic').data('pagination').currentPage + 1;
		        					        			
		        			showdivimg("",1,currentPage,9,true)
		        		}
		        	else
		        		{        			
		        			alertMessage(1, "警告", "删除图片失败:"+data.resultMessage);
		        		}
		        },  
		        error: function() {
		        	alertMessage(2, "异常", "ajax 函数  deletebasemapbyids 错误");        	           
		          }  
		    });
			}
	});
	
}
//
function GetbackgroundStyle()
{
	var bmidlist=[];
	
	var basemap = $("#basemapselect").val();
	switch(basemap)
	{
	case "无背景":{				
	};break;
	case "背景色":{
		bmdb={
				basemalistmode:2,
				basemabackcolor:$("#info_backcolor").val()
			}
		bmidlist.push(bmdb);
	};break;
	case "背景图":{	
		$("#imgSel .basemapStyle").each(function(){				
			var bmid=this.id.substring(this.id.indexOf("basemap")+7);
			var src = $('#basemap' + bmid +' img')[0].src;
			var data_mapstyle = $('#basemap' + bmid +' img')[0].dataset.mapstyle;						
			var bmdb={};
			bmdb.basemalistmode=3;
			bmdb.basemalistsn=bmid;
			bmdb.basemalistsrc=src;
			bmdb.basemalistmapstyle=data_mapstyle;
			bmidlist.push(bmdb);					
		});
	};break;
	}
	backgroundStyle={};
	backgroundStyle.width=screenw;
	backgroundStyle.height=screenh;
	backgroundStyle.basemapStyle=bmidlist;
	
	var strbackgroundStyle = JSON.stringify(backgroundStyle);
	
	return strbackgroundStyle;
}
//特效初始化
function init_modal_special()
{	
	 $('#modal_special_select .input-group').colorpicker({
	      useAlpha: false
	    }).on("change", function (e) {
		    var id = $(this)[0].id;
		    var val=e.value;
		    switch(id)
	        {
		    case 'sketch_color':{$('#sketch_check').prop('checked',true);};break;
		    case 'shadow_color':{$('#shadow_check').prop('checked',true);};break;
		    case 'gradient_color1':{$('#gradient_check').prop('checked',true);};break;
		    case 'gradient_color2':{$('#gradient_check').prop('checked',true);};break;
	        }		    
		    
		    Draw_preview_canvas();
	});
	 $("#sketch_check").change(function(){		 
		 Draw_preview_canvas();
	 });
	 
	 $("#shadow_check").change(function(){		 
		 Draw_preview_canvas();
	 });
	 
	 $("#gradient_check").change(function(){		 
		 Draw_preview_canvas();
	 });
	 
	 $("#gradient_direction").change(function(){
		 $('#gradient_check').prop('checked',true);
		 Draw_preview_canvas();
	 });
	 
	 $("#sketch_width").ionRangeSlider({
	 	type: "single",
	    min: 1,
	    max: 10,
	    from: 5,
	    onChange: function (data) {
	    	var sketch_width = data.from;	
	    	$('#sketch_check').prop('checked',true);
	    	Draw_preview_canvas();
	    }
	    });
	 
	 $("#shadow_width").ionRangeSlider({
		 	type: "single",
		    min: 1,
		    max: 10,
		    from: 5,
		    onChange: function (data) {
		    	var shadow_width = data.from;	
		    	$('#shadow_check').prop('checked',true);
		    	Draw_preview_canvas();
		    }
		    });
	 $("#scale_horizontal").val(100);
	 $("#scale_vertical").val(100);
}
//画特效预览图
function Draw_preview_canvas()
{
	var w = $("#preview_canvas").width();
	var h = $("#preview_canvas").height();		
	if(w<=0){w=100;}
	if(h<=0){h=100;}
	var sketch_check = $('#sketch_check').prop('checked');
	var shadow_check = $('#shadow_check').prop('checked');
	var gradient_check = $('#gradient_check').prop('checked');			

    $("#preview_canvas").drawRect({
	  layer: true,
	  name: 'myBox',
  	  fillStyle: '#00ee00',  	  
  	  x: (w - w/2)/2, y: (h - h/2)/2,	    	  
  	  width: w/2,
  	  height: h/2,
  	  fromCenter: false
  	});
    
    var layer = $("#preview_canvas").getLayer('myBox');
	 if(layer!=null)
		 {
		 if(sketch_check)
			 {			 
			 $("#preview_canvas").setLayer('myBox', {
				 strokeStyle: $('#sketch_color input').val(),
				 strokeWidth: $('#sketch_width').val()
				})
				.drawLayers();
			 }
		 else
			 {
			 $("#preview_canvas").setLayer('myBox', {
				 strokeStyle: $('#sketch_color input').val(),
				 strokeWidth: 0
				})
				.drawLayers();
			 }
		 if(shadow_check)
			 {			 
			 	$("#preview_canvas").setLayer('myBox', {
		 		  shadowColor: $('#shadow_color input').val(),
				  shadowBlur: $('#shadow_width').val(),
				  shadowX: $('#shadow_width').val(), shadowY: $('#shadow_width').val()
				})
				.drawLayers();
			 }
		 else
			 {
			 $("#preview_canvas").setLayer('myBox', {
		 		  shadowColor: $('#shadow_color input').val(),
				  shadowBlur: 0,
				  shadowX: 0, shadowY: 0
				})
				.drawLayers();
			 }
		 if(gradient_check)
		 	{
			 	var linear = null;
			 	
			 	var selval=$("#gradient_direction").val();
			 	switch(selval)
			 	{
			 	case 'horizontal':{
			 		linear = $('canvas').createGradient({
						  x1: (w - w/2)/2, y1: (h - h/2)/2,
						  x2: (w - w/2)/2 + w/2, y2: (h - h/2)/2,
						  c1: $('#gradient_color1 input').val(),
						  c2: $('#gradient_color2 input').val()
						});
			 	};break;
			 	case 'vertical':{
			 		linear = $('canvas').createGradient({
						  x1: (w - w/2)/2, y1: (h - h/2)/2,
						  x2: (w - w/2)/2, y2: (h - h/2)/2 + h/2,
						  c1: $('#gradient_color1 input').val(),
						  c2: $('#gradient_color2 input').val()
						});
			 	};break;
			 	case 'oblique':{
			 		linear = $('canvas').createGradient({
						  x1: (w - w/2)/2, y1: (h - h/2)/2,
						  x2: (w - w/2)/2 + w/2, y2: (h - h/2)/2 + h/2,
						  c1: $('#gradient_color1 input').val(),
						  c2: $('#gradient_color2 input').val()
						});
			 	};break;
			 	}
	
			 
			 	$("#preview_canvas").setLayer('myBox', {
		 		 fillStyle: linear
				})
				.drawLayers();			    
		 	}
		 else
			 {
			 $("#preview_canvas").setLayer('myBox', {
		 		 fillStyle: '#00ee00'
				})
				.drawLayers();		
			 }
		 }		 
}
//特效确定按钮
function btn_selectspecial()
{
	var sketch_check = $('#sketch_check').prop('checked');
	var shadow_check = $('#shadow_check').prop('checked');
	var gradient_check = $('#gradient_check').prop('checked');
	
	var special={};
	//描边
	if(sketch_check)
	{
		var sketch={
		 strokeStyle: $('#sketch_color input').val(),
		 strokeWidth: $('#sketch_width').val()
		};
		special.sketch=sketch;
	}
	//阴影
	if(shadow_check)
	{
		var shadow={
			shadowColor: $('#shadow_color input').val(),
			shadowBlur: $('#shadow_width').val()
		};
		special.shadow=shadow;
	}
	//渐变
	if(gradient_check)
	{
		var gradient={
			gradientdirection: $("#gradient_direction").val(),
			gradientcolor1: $('#gradient_color1 input').val(),
			gradientcolor2: $('#gradient_color2 input').val()
		};
		special.gradient=gradient;
	}
	var scale={
		scaleX:	parseInt($("#scale_horizontal").val()),
		scaleY:	parseInt($("#scale_vertical").val())
	};
	special.scale=scale;
	
	selectspecial=special;	 
	$('#modal_special_select').modal('hide');	
}
//建立背景
function creatinfo_addbasemap(parentdivid)
{	
	$('#modal_pic_select').attr("data-imgtype",0);
	$('#modal_pic_select').attr("data-parentdivid",parentdivid);

	initbasemap(0);
	
	$('#modal_pic_select').modal('show');
}
//gif动画初始化
function initgif(pageNumber,pageSize,isInit)
{
	//请求时spinner出现
	var target = $("#myModalEdit .modal-body").get(0);
    spinner.spin(target);
	$.ajax({  
        url:"/getbasemapbyprojectid",		
        data:{     
        	groupid:parseInt($("#grouplist").val()),
        	imgtype:2,
        	classify:"",
        	pageNumber:pageNumber,
        	pageSize:pageSize,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	       
        	$('#div_giflist div').remove();
        	if(data!=null)
    		{	        
        		var itemJSONArray = data.itemJSONArray;
        		for(var i=itemJSONArray.length - 1;i>=0;i--)
				{
        			var basemapid=itemJSONArray[i].basemapid;
        			var fileName=itemJSONArray[i].fileName;
        			var groupid=itemJSONArray[i].groupid;
        			var classify=itemJSONArray[i].classify;
        			var contentType=itemJSONArray[i].contentType;
        			var basemapstyle =itemJSONArray[i].basemapstyle;
        			var imgBase64String=itemJSONArray[i].imgBase64String;
        			if(groupid==0)
        				{
        				addimg2div("#div_giflist",basemapid,fileName,contentType,0,basemapstyle,imgBase64String,false);
        				}
        			else
        				{
        				addimg2div("#div_giflist",basemapid,fileName,contentType,0,basemapstyle,imgBase64String,true);
        				}        			
				}
        		var pages = parseInt(Math.ceil(data.totalCount/parseFloat(pageSize)));
        		if(isInit)
        			{      
        			if(pageNumber>pages){pageNumber=pages;}
	        		$('#pagination').pagination({
	        			pages: pages,
	        			edges: 2,
	        			cssStyle: 'pagination-sm',
	        			displayedPages: 5,
	        			currentPage:pageNumber,
	        			onPageClick: function(pageNumber, event) {
	        				//点击时调用        				
	        				initgif(pageNumber,pageSize,false)
	        			},
	        			onInit: function(getid) {
	        				//刷新时调用
//	        				alert(getid);
	        			}
	
	        		});
        			}
    		}
        	//关闭spinner  
            spinner.spin();
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getbasemapbyprojectid 错误");   
        	//关闭spinner  
            spinner.spin();
          }  
    });
}
//背景初始化
function initbasemap(imgtype,pageNumber,pageSize,isInit)
{
	showdivimg("",imgtype,pageNumber,pageSize,isInit);
}
//视频初始化
function initvideo(imgtype)
{
	showdivvideo("",imgtype);	
}
//获取图片组
function showdivimg(classify,imgtype,pageNumber,pageSize,isInit)
{	
	$.ajax({  
        url:"/getbasemapbyprojectid",		
        data:{     
        	groupid:parseInt($("#grouplist").val()),
        	imgtype:imgtype,
        	classify:classify,
        	pageNumber:pageNumber,
        	pageSize:pageSize,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	$('#div_imglist div').remove();
        	if(data!=null)
    		{	        
        		var itemJSONArray = data.itemJSONArray;
        		for(var i=itemJSONArray.length - 1;i>=0;i--)
				{
        			var basemapid=itemJSONArray[i].basemapid;
        			var fileName=itemJSONArray[i].fileName;
        			var groupid=itemJSONArray[i].groupid;
        			var classify=itemJSONArray[i].classify;
        			var contentType=itemJSONArray[i].contentType;
        			var basemapstyle =itemJSONArray[i].basemapstyle;
        			var imgBase64String=itemJSONArray[i].imgBase64String;
        			if(groupid==0)
        				{
        				addimg2div("#div_imglist",basemapid,fileName,contentType,0,basemapstyle,imgBase64String,false);
        				}
        			else
        				{
        				addimg2div("#div_imglist",basemapid,fileName,contentType,0,basemapstyle,imgBase64String,true);
        				}        			
				}
        		
        		var pages = parseInt(Math.ceil(data.totalCount/parseFloat(pageSize)));
        		if(isInit)
        			{        	
        			if(pageNumber>pages){pageNumber=pages;}
	        		$('#pagination_pic').pagination({
	        			pages: pages,
	        			edges: 2,
	        			cssStyle: 'pagination-sm',
	        			displayedPages: 5,
	        			currentPage:pageNumber,
	        			onPageClick: function(pageNumber, event) {
	        				//点击时调用        					        				
	        				showdivimg("",1,pageNumber,pageSize,false)
	        			},
	        			onInit: function(getid) {
	        				//刷新时调用
//	        				alert(getid);
	        			}
	
	        		});
        			}
    		}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getbasemapbyprojectid 错误");        	            
          }  
    });
}
//获取视频组
function showdivvideo(classify,imgtype)
{	
	//$('#div_imglist')
	$.ajax({  
        url:"/getvideobygroupid",		
        data:{
        	groupid:parseInt($("#grouplist").val()),
        	imgtype:imgtype,
        	classify:classify,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	$('#div_videolist div').remove();
        	if(data!=null && data.length>0)
    		{	        		        		
        		for(var i=0;i<data.length;i++)
				{    				
        			var videoid=data[i].videoid;
        			var fileName=data[i].fileName;
        			var groupid=data[i].groupid;
        			var classify=data[i].classify;
        			var contentType=data[i].contentType;
        			var duration=data[i].duration;
        			var videostyle =data[i].videostyle;
        			var imgBase64String=data[i].imgBase64String;
        			if(videostyle==0)
        				{
        				addimg2div("#div_videolist",videoid,fileName,contentType,duration,videostyle,imgBase64String,false);
        				}
        			else
        				{
        				addimg2div("#div_videolist",videoid,fileName,contentType,duration,videostyle,imgBase64String,true);
        				}        			
				}
    		}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getvideobygroupid 错误");         	            
          }  
    });
}
//打开图片分类添加模态框
function dbldivimg(classify,imgtype)
{	
	$("#modal_picnav_add").modal('show');
	$("#modal_picnav_add").attr("data-type","update_pic");
}
//打开视频分类添加模态框
function dbldivvideo(classify,imgtype)
{	
	$("#modal_picnav_add").modal('show');
	$("#modal_picnav_add").attr("data-type","update_video");
}
//上传图片
function input_upload(file)
{
	var navitem = $('#nav_pictype .active')[0];
	var imgtype=0;
	var mimgtype = $('#modal_pic_select').attr("data-imgtype");
	if(mimgtype!=null)
		{imgtype=mimgtype;}
	
	var sendData=new FormData();
	sendData.append('groupid',parseInt($("#grouplist").val()));
	sendData.append('imgtype',imgtype);
	sendData.append('classify',"");
    sendData.append('file',file.files[0]);    
    sendData.append('adminname',JSON.parse(localStorage.getItem("adminInfo")).adminname);
    
	$.ajax({  
        url:"/GetImg2DBbyBase64", 
        data:sendData,  
        type:"post",
        //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
        contentType:false,
        //取消帮我们格式化数据，是什么就是什么
        processData:false,
        success:function(data)  
        {     
        	if(data.result=="success")
    		{     
//        		addimg2div("#div_imglist",data.basemapid,data.fileName,data.contentType,0,data.basemapstyle,data.imgBase64String,true);
        		showdivimg("",1,1,9,true);
    		}
    	else
    		{    			
    			alertMessage(1, "警告", data.resultMessage); 
    		}
        	$('#input_upload').val("");
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  GetImg2DBbyBase64 错误");         	
        	$('#input_upload').val("");
          }  
    });
}
//上传图片
function input_gif_upload(file)
{
	var sendData=new FormData();
	sendData.append('groupid',parseInt($("#grouplist").val()));
	sendData.append('imgtype',2);
	sendData.append('classify',"");
    sendData.append('file',file.files[0]);
    sendData.append('adminname',JSON.parse(localStorage.getItem("adminInfo")).adminname);
    
    var fileKB = file.files[0].size / 1024;
    
    if(fileKB > 250)
    	{    	
    	alertMessage(1, "警告", "上传文件大小不能超过250KB"); 
    	return;
    	}
    
	$.ajax({  
        url:"/GetImg2DBbyBase64", 
        data:sendData,  
        type:"post",
        //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
        contentType:false,
        //取消帮我们格式化数据，是什么就是什么
        processData:false,
        success:function(data)  
        {     
        	if(data.result=="success")
    		{   
        		initgif(1,9,true)
        		
//        		if($("#div_giflist .basemapStyle").length>=9)
//        			{
//        			$("#div_giflist .basemapStyle:last").remove();
//        			}
//        		addimg2div("#div_giflist",data.basemapid,data.fileName,data.contentType,0,data.basemapstyle,data.imgBase64String,true);        		
    		}
    	else
    		{    			
    			alertMessage(1, "警告", data.resultMessage);
    		}
        	$('#input_gif_upload').val("");
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  GetImg2DBbyBase64 错误");        	
        	$('#input_gif_upload').val("");
          }  
    });
}
//上传图片
function input_video_upload(file)
{
	if($('#input_video_upload')[0].files==null || $('#input_video_upload')[0].files.length<=0)
		{return;}
	var duration = file.duration;
	if(parseInt(duration)>30)
		{alertMessage(1, "警告", "视频时长大于30秒不能上传!");return;}
	var navitem = $('#nav_videotype .active')[0];
	var imgtype=1;
	
	var sendData=new FormData();
	sendData.append('groupid',parseInt($("#grouplist").val()));
	sendData.append('imgtype',imgtype);
	sendData.append('duration',duration);
	sendData.append('classify',navitem.text);
    sendData.append('file',$('#input_video_upload')[0].files[0]);
    sendData.append('adminname',JSON.parse(localStorage.getItem("adminInfo")).adminname);
    
	$.ajax({  
        url:"/uploadvideo", 
        data:sendData,  
        type:"post",
        //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
        contentType:false,
        //取消帮我们格式化数据，是什么就是什么
        processData:false,
        success:function(data)  
        {   
        	
        	if(data.result=="success")
    		{     
        		var videoid = data.videoid;
        		var fileName = data.fileName;
        		var contentType = data.contentType;	
        		var duration = data.duration;	
        		var videostyle = data.videostyle;	
        		var imgBase64String = data.imgBase64String;	
			            	      		
        		if(navitem.text=="共享")
        			{addimg2div("#div_videolist",videoid,fileName,contentType,duration,videostyle,imgBase64String,false);}
        		else
        			{addimg2div("#div_videolist",videoid,fileName,contentType,duration,videostyle,imgBase64String,true);}  
    			     		
    		}
    	else
    		{    			
    			alertMessage(1, "警告", data.resultMessage);
    		}
        	
        	$('#input_video_upload').val("");
    		
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  uploadvideo 错误");        	
        	$('#input_video_upload').val("");
          }  
    });
}
//添加图片
function addimg2div(parentdiv,basemapid,fileName,contentType,duration,basemapstyle,imgBase64String,isdelete)
{
	var urlbase64 = "data:"+contentType+";base64," + imgBase64String;
	var div="<div id='basemap"+basemapid+"' title='"+fileName+"' class='basemapStyle'>";	
	
	var deletefn='',imgClickfn='';
	switch(parentdiv)
	{
		case "#div_videolist":{
			deletefn="imgdeletevideo2db("+basemapid+")";
			imgClickfn="videoClick("+basemapid+")";
		};break;
		case "#div_imglist":{
			deletefn="imgdeletepic2db("+basemapid+")";
			imgClickfn="imgClick("+basemapid+")";
			};break;
		case "#div_giflist":{
			deletefn="imgdeletepic2db("+basemapid+")";
			imgClickfn="imgClick("+basemapid+")";
			};break;
	}
	
	var altlist={};
	altlist.id=basemapid;
	altlist.fileName=fileName;
	altlist.contentType=contentType;
	altlist.duration=duration;
	
	var altstring=JSON.stringify(altlist);
	
	var img="<img src='"+urlbase64+"' data-mapstyle='"+basemapstyle+"' onclick='"+imgClickfn+"' alt='"+altstring+"' width='128' height='21'/>";
	
	//var img = "<video width='64' height='64'><source src='"+urlbase64+"' type='video/mp4'></video>"
	  
	var checkbox="<input type='checkbox'>";
	var deletebox="<a href='#' onclick='"+deletefn+"'><i class='fa fa-trash' style='font-size:16px;color:white;'></i></a>";
	var namebox="<div style='width:128px;height:20px;overflow: hidden;'>"+fileName+"</div>";
	
	var html="";
	
	if(isdelete)
		{html=div+img+checkbox+namebox+"</div>";}
	else
		{html=div+img+checkbox+namebox+"</div>";}	
	
	$(parentdiv).prepend(html);			
}
//更新背景
function updataAlllistbackground()
{
	for(var pageid in itemmap)
	{
		updatalistbackground('list_page_'+pageid,selectinfoid);
		var itemlist = itemmap[pageid]
		for(var i=0;i<itemlist.length;i++)
	 	{
	 		var item = itemlist[i];  							 		
	 		updatalistbackground('list_page'+pageid+'_item'+item.itemid,selectinfoid);
	 	}				
		
	}		
}
//删除图片
function imgdeletepic2db(basemapid)
{	
	$.ajax({  
        url:"/deletebasemapbyid",         
        data:{
        	basemapid:basemapid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data==0)
        		{    
        			$('#div_imglist #basemap'+basemapid).remove();
        			$('#div_giflist #basemap'+basemapid).remove();
        			
        			var currentPage = $('#pagination').data('pagination').currentPage + 1;
        			
        			initgif(currentPage,9,true);        			
        		}
        	else
        		{        			
        			alertMessage(1, "警告", "删除图片失败");
        		}
        },  
        error: function() {
        	alertMessage(2, "异常", "ajax 函数  deletebasemapbyid 错误");        	           
          }  
    });
}
//删除图片
function imgdeletevideo2db(basemapid)
{	
	$.ajax({  
        url:"/deletevideobyid",         
        data:{
        	basemapid:basemapid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data==0)
        		{    
        			$('#div_videolist #basemap'+basemapid).remove();
        		}
        	else
        		{
        			alertMessage(1, "警告", "删除视频失败");        			
        		}
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  deletevideobyid 错误");        	        
          }  
    });
}
//图片选择
function imgClick(basemapid)
{
	var input = $("#basemap"+basemapid+" input")[0];	
	var val=input.checked;
	input.checked=!val;
}
//视频选择
function videoClick(basemapid)
{
	$("#basemap"+basemapid+" input").each(function(){
		this.checked=false;
	});	
	var input = $("#basemap"+basemapid+" input")[0];	
	var val=input.checked;
	input.checked=!val;
	if(input.checked)
		{
		$("#video_player").empty();		
		var altString = $(input).parent().children("img").attr("alt");
		
		var srcString = $(input).parent().children("img").attr("src");
		var JsonAlt = JSON.parse(altString);
		var videoName=JsonAlt.fileName;
		//{"id":9,"fileName":"123.mp4","contentType":"video/mp4"}
		var video ="<source src='upload_video/"+videoName+"' type='video/mp4'>";
		//var video ="<source src='"+srcString+"' type='video/mp4'>";
		$("#video_player").append(video);
		}
}
//添加分类
function btn_addnav()
{
	var classifyname = $("#model_classifyname").val().trim();
	if(classifyname!="")
		{						
			var data_type = $("#modal_picnav_add").attr("data-type");
			switch(data_type)
			{
				case "add_pic":{
					
					var imgtype=0;
					var mimgtype = $('#modal_pic_select').attr("data-imgtype");
					if(mimgtype!=null)
					{imgtype=mimgtype;}
					
					var isc=false;
					$('#nav_pictype .nav-link').each(function(){
					    var nav_name = $(this).text();
					    if(nav_name==classifyname)
					    	{isc=true;}
					  });
					if(isc)
						{alertMessage(1, "警告", "分类名称已存在");}
					else
						{							
							var navitem = $('#nav_pictype .active').removeClass('active');
							
							var dblfn="dbldivimg('"+classifyname+"',"+imgtype+")";			        			
							var fn= "showdivimg('"+classifyname+"',"+imgtype+")";
							var nav = "<li class='nav-item itempage'><a class='nav-link active' data-toggle='pill' href='#' onclick="+fn+" ondblclick="+dblfn+">"+classifyname+"</a></li>"
							$('#nav_pictype').prepend(nav);
							
							showdivimg(classifyname,imgtype);
							$("#modal_picnav_add").modal('hide');
						}
				};break;
				case "add_video":{
					var imgtype=1;
					
					var isc=false;
					$('#nav_videotype .nav-link').each(function(){
					    var nav_name = $(this).text();
					    if(nav_name==classifyname)
					    	{isc=true;}
					  });
					if(isc)
						{alertMessage(1, "警告", "分类名称已存在");}
					else
						{
							var navitem = $('#nav_videotype .active').removeClass('active');
							
							var fn= "showdivvideo('"+classifyname+"',"+imgtype+")";
							var dblfn="dbldivvideo('"+classifyname+"',"+imgtype+")";
							var nav = "<li class='nav-item itempage'><a class='nav-link active' data-toggle='pill' href='#' onclick="+fn+" ondblclick="+dblfn+">"+classifyname+"</a></li>"
							$('#nav_videotype').prepend(nav);
							
							showdivvideo(classifyname,imgtype);
							$("#modal_picnav_add").modal('hide');
						}
				};break;
				case "update_pic":{

					var imgtype=0;
					var mimgtype = $('#modal_pic_select').attr("data-imgtype");
					if(mimgtype!=null)
					{imgtype=mimgtype;}
					
					var oldclassifyname = $('#nav_pictype .active')[0].text;
					if(oldclassifyname!=classifyname)
						{
							var isc=false;
							$('#nav_pictype .nav-link').each(function(){
							    var nav_name = $(this).text();
							    if(nav_name==classifyname)
							    	{isc=true;}
							  });
							if(isc)
							{alertMessage(1, "警告", "分类名称已存在");}
							else
							{
								

								$.ajax({  
							        url:"/updatebasemapclassify",         
							        data:{							        	
							        	imgtype:imgtype,
							        	oldbasemapclassify:oldclassifyname,
							        	newbasemapclassify:classifyname
										},  
							        type:"post",  
							        dataType:"json", 
							        success:function(data)  
							        {       	  
							        	if(data==0)
							        		{    
							        			$('#nav_pictype .active')[0].text=classifyname;
							        			$("#modal_picnav_add").modal('hide');
							        		}
							        	else
							        		{
							        			alertMessage(1, "警告", "修改分组名称失败");							        			
							        		}
							        },  
							        error: function() { 
							        	alertMessage(2, "异常", "ajax 函数  updatebasemapclassify 错误");							        							            
							          }  
							    });
							}
						}
					else
					{$("#modal_picnav_add").modal('hide');}
				};break;
				case "update_video":{

					var imgtype=1;
					
					var oldclassifyname = $('#nav_videotype .active')[0].text;
					if(oldclassifyname!=classifyname)
						{
							var isc=false;
							$('#nav_videotype .nav-link').each(function(){
							    var nav_name = $(this).text();
							    if(nav_name==classifyname)
							    	{isc=true;}
							  });
							if(isc)
							{alertMessage(1, "警告", "分类名称已存在");}
							else
							{
								

								$.ajax({  
							        url:"/updatevideoclassify",         
							        data:{
							        	groupid:parseInt($("#grouplist").val()),
							        	videotype:imgtype,
							        	oldvideoclassify:oldclassifyname,
							        	newvideoclassify:classifyname,
							        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
										},  
							        type:"post",  
							        dataType:"json", 
							        success:function(data)  
							        {       	  
							        	if(data==0)
							        		{    
							        			$('#nav_videotype .active')[0].text=classifyname;
							        			$("#modal_picnav_add").modal('hide');
							        		}
							        	else
							        		{							        			
							        			alertMessage(1, "警告", "修改分组名称失败");
							        		}
							        },  
							        error: function() {
							        	alertMessage(2, "异常", "ajax 函数  updatevideoclassify 错误");							        							            
							          }  
							    });
							}
						}
					else
					{$("#modal_picnav_add").modal('hide');}
				};break;
			}				
		}
}
//视频切换图文
function modelchange()
{
	$('#modal_video_select').modal('hide');	
	$('#modal_gif_select').modal('hide');	
	$('#myModalEdit').modal('show');	
}
//颜色转16进制
function colorRGB2Hex(color) {
    var rgb = color.split(',');
    var r = parseInt(rgb[0].split('(')[1]);
    var g = parseInt(rgb[1]);
    var b = parseInt(rgb[2].split(')')[0]);
 
    var hex = "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
    return hex;
 }
//matrix取旋转角度
function getmatrix(matrix){  
    var aa=Math.round(180*Math.asin(matrix.a)/ Math.PI);  
    var bb=Math.round(180*Math.acos(matrix.b)/ Math.PI);  
    var cc=Math.round(180*Math.asin(matrix.c)/ Math.PI);  
    var dd=Math.round(180*Math.acos(matrix.d)/ Math.PI);  
    var deg=0;  
    if(aa==bb||-aa==bb){  
        deg=dd;  
    }else if(-aa+bb==180){  
        deg=180+cc;  
    }else if(aa+bb==180){  
        deg=360-cc||360-dd;  
    }  
    return deg>=360?0:deg;  
    //return (aa+','+bb+','+cc+','+dd);  
} 