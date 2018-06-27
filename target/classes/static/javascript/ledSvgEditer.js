const minWidth=10,minHeight=10;

var dx=0,dy=0,ux=0,uy=0,ox=-1,oy=-1;
var svgcanvas;
var screenw=192,screenh=32;
var itemmap={};
var selectinfoid=0,selectpageid=0;
var selectItem,selectGrip;
var defaultFontsize=32;
var isMove=false;
var ue;

$(function(){	 
	initSvgcanvas();	
	initpage();	
	getadvList();	
	
	 ue = UE.getEditor('container');	
     
     UE.Editor.prototype._bkGetActionUrl=UE.Editor.prototype.getActionUrl;
     UE.Editor.prototype.getActionUrl=function(action){
         if (action == 'uploadimage'){
             return 'uploadimage';    // 这里填上你自己的上传图片的action
         }else{
             return this._bkGetActionUrl.call(this, action);
         }
     };
     
     /*
     UE.Editor.prototype.getActionUrl = function(action) {
         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
             return 'http://localhost:8011/imgUpdate'; //在这里返回我们实际的上传图片地址
         } else {
             return this._bkGetActionUrl.call(this, action);
         }
     }
     */
     
	/*	 
	$(document).keydown(function(event){
		switch(event.keyCode)
		{
		case 37:{alert("左键");};break;
		case 39:{alert("右键");};break;
		case 46:{alert("del");};break;
		case 8:{alert("退格");};break;
		default:console.log(event.keyCode);
		}	　　　　
	　　});
	*/
});

function initpage()
{
	$("#creat_layer").click(function(){
		var pid=0;
		$("#layer_list svg").each(function(){
			var pageid = parseInt(this.id.slice(4));
			if(pageid>pid){pid=pageid;}
		  });		
		additem(pid+1,1,0,0,192,32,'#fff',100,0,"[{"+'"'+"color"+'"'+":"+'"'+"#00f"+'"'+","+'"'+"fontsize"+'"'+":32,"+'"'+"text"+'"'+":"+'"'+"文明驾驶安全行车"+'"'+","+'"'+"type"+'"'+":0}]");
		addlayer(pid+1,itemmap[pid+1],true) 
						
		//svgAsPngUri($('#divsvg svg')[0], null, uri => $("#layer_list").append('<img src="' + uri + '" />'));		
	});
	

	$("#btn_CreatInfo").click(function(){		
		$.ajax({  
	        url:"/CreatInfo", 
	        data:{
	        	infoName:$("#creat_infoname").val().trim()      	
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success")
	        		{
	        			var id = parseInt(data.infoID);
	        			var advname = data.infoName;
	        			var el="<li id='adv"+id+"' class='list-group-item list-group-item-info list-group-item-action' onclick='infoSelectChange("+id+")'>"+advname+"</li>";    					
    					$("#list_group_draft").append(el);
    					
    					$("#modal_creatinfo").modal('hide');
	        		}
	        	else
	        		{
	        			alert(data.resultMessage);
	        		}
	        },  
	        error: function() {  
	            alert("error");  
	          }  
	    });
	});	
	$("#item_Creat").click(function(){
		if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid))
		{
			var itemid = parseInt(itemmap[selectpageid][itemmap[selectpageid].length - 1].name) + 1
			additem(selectpageid,itemid,0,0,192,32,'#fff',100,0,"[{"+'"'+"color"+'"'+":"+'"'+"#00f"+'"'+","+'"'+"fontsize"+'"'+":32,"+'"'+"text"+'"'+":"+'"'+"文明驾驶安全行车"+'"'+","+'"'+"type"+'"'+":0}]");
			addSvgItem(selectpageid,itemid,true);
		}		
	});
	
	$("#item_Delete").click(function(){
		if(selectItem!=null)
			{
				selectItem.remove();
			}
	});
	
	$("#editItem").click(function(){
		 var strHtml = ue.getContent()
		 var root = UE.htmlparser(strHtml, true);
		 var arrNode = new Array();
		 for(var i=0;i<root.children.length;i++)
			 {
			 		var node=root.children[i];
			 		fontName='宋体';fontSize=16;
			 		arrNode.push(ParseNode(node));
			 }	 
	});
	
	$("#svg_position_X").change(function(){
		if(selectItem!=null)
		{
			selectItem.attr({"x": $("#svg_position_X").val()});
			selectChangeSvg(selectItem);
			alert($("#svg_position_X").val());
		}		
	});
	$("#svg_position_Y").change(function(){
		if(selectItem!=null)
		{
			selectItem.attr({"y": $("#svg_position_Y").val()});
			selectChangeSvg(selectItem);
			alert($("#svg_position_Y").val());
		}				
	});
	$("#svg_size_width").change(function(){
		if(selectItem!=null)
		{
			selectItem.attr({"width": $("#svg_size_width").val()});
			selectChangeSvg(selectItem);
			alert($("#svg_size_width").val());
		}
	});
	$("#svg_size_height").change(function(){
		selectItem.attr({"height": $("#svg_size_height").val()});
		selectChangeSvg(selectItem);
		alert($("#svg_size_height").val());		
	});
	
	$("#svg_color_stroke").change(function(){
		if(selectItem!=null)
		{
			//selectItem.attr({"y": $("#svg_position_Y").val()});
			//selectChangeSvg(selectItem);
			alert($("#svg_color_stroke").val());
		}				
	});
	$("#svg_color_fill").change(function(){
		if(selectItem!=null)
		{
			selectItem.select("rect").attr({"fill":$("#svg_color_fill").val()});			
			alert($("#svg_color_fill").val());
		}
	});
	$("#svg_color_opacity").change(function(){
		if(selectItem!=null)
		{
			selectItem.select("rect").attr({"fill-opacity":$("#svg_color_opacity").val()});
			alert($("#svg_color_opacity").val());
		}	
	});
}

function ParseNode(node) {
	var arrNode = new Array()
	for(var i=0;i<node.children.length;i++)
	 {
	 		var nodeType = node.children[i].tagName;
	 		switch(nodeType)
	 		{
	 			case "Text":{};break;
	 			case "span":{
	 				fontName="SimSun";
	 				fontSize=16;
	 				forecolor="#000000";
	 				var style = node.children[i].attrs['style'];
	 				var ArraySl = style.split(";"); 
 					for(var s=0;s<ArraySl.length;s++)
 						{
 							var attr =ArraySl[s].trim();
 							if(attr.trim()!="")
 								{
 								 	var attrArr =attr.split(":");
 								 	if(attrArr.length>=2)
 								 		{
 								 			var attrName=attrArr[0];
 								 			switch(attrName.trim())
 								 			{
 								 			case 'font-family':{fontName=attrArr[1].split(",")[1].trim();};break;
 	                                        case 'font-size':{fontSize=parseInt(attrArr[1].replace("px", ""));};break;
 	                                        case 'color':{forecolor=colorRGB2Hex(attrArr[1]);};break;
 								 			}
 								 		}
 								}
 						}
 					arrNode = arrNode.concat(ParseNode(node.children[i]));
	 			};break;
	 			case "img":{
	 				var data={
	 						fontName:fontName,
	 						fontSize:fontSize,
	 						itemType:1,
	 						value:node.children[i].attrs['src']					
	 				};
	 				arrNode.push(data);
	 			};break;
	 			default:{
	 				if(node.tagName=='p'){fontName='宋体';fontSize=16;}
	 				var data={
	 						fontName:fontName,
	 						fontSize:fontSize,
	 						forecolor:forecolor,
	 						itemType:0,
	 						value:node.children[i].data	 						
	 				};
	 				arrNode.push(data);
	 			};break;
	 		}
	 }
	return arrNode;
}

function addSvgItem(pageid,itemid,isSelect)
{		
	var scale = parseInt(($("#workarea").width() - 40)/screenw);
	if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i];
					if(item.name==itemid)
						{
							var itemtype='text';
							switch(item.type)
							{
							case 0:{itemtype='text';};break;
							case 1:{itemtype='video';};break;
							}
							
							var svg = svgcanvas.paper.svg(item.left*scale, item.top*scale, item.width*scale, item.height*scale).attr({id:"item"+item.name,target:itemtype});							
							
							switch(item.type)
							{
								case 0:{
									
									svg.paper.rect(0, 0, item.width*scale, item.height*scale).attr({
						    			fill:item.backcolor,
									    "fill-opacity": 100   
									});
						    		 		
								    var jsonObj =  JSON.parse(item.context);
								    for(var j=0;j<jsonObj.length;j++){
							            var jsonpt=jsonObj[j]	            
							            switch(jsonpt.type)
							            {
							            	case 0:{							            		
							            		svg.paper.text(0, (item.height*scale - jsonpt.fontsize*scale)/2 + jsonpt.fontsize*scale, jsonpt.text).attr({				
						    						fill: jsonpt.color,
						    					    style:"font-size:"+jsonpt.fontsize*scale+"px;"
						    					});						    					
							            	};break;
							            	case 1:{var image=jsonpt.image};break;
							            }	            
								    }
								};break;
								case 1:{itemtype='video';};break;
							}
							if(isSelect)
							{selectChangeSvg(svg);}	
							break;
						}
				}
		}
}

//图片路径转base64
function getBase64Image(img) {
  var canvas = document.createElement("canvas");
  canvas.width = img.width;
  canvas.height = img.height;

  var ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0, img.width, img.height);
  var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
  var dataURL = canvas.toDataURL("image/"+ext);
  return dataURL;
}

//初始化画布
function initSvgcanvas()
{
	var scale = parseInt(($("#workarea").width() - 40)/screenw);
	$("#divsvg").width(screenw*scale);
	$("#divsvg").height(screenh*scale);
	
	$("#svgcanvas").width(screenw*scale);
	$("#svgcanvas").height(screenh*scale);
	
	var svgx=parseInt(($("#workarea").width() - $("#divsvg").width())/2);
	var svgy=parseInt(($("#workarea").height() - $("#divsvg").height())/2);
	svgx=svgx>0?svgx:0;
	svgy=svgy>0?svgy:0;
	$("#divsvg").css("left",svgx);
	$("#divsvg").css("top",svgy);
	
	$("#svgcanvas").css("background-color","#ffffff");
	
	svgcanvas = Snap("#svgcanvas");	
	
	updateSelectorGrip(null);
	
	svgcanvas.mousedown(function(MouseEvent) {		
    	dx=MouseEvent.offsetX;
    	dy=MouseEvent.offsetY;
    	svgMouseDown();
	});
    
    svgcanvas.mousemove(function(MouseEvent) {	
    	if(isMove && selectItem!=null)
    		{
        		mx=MouseEvent.offsetX;
        		my=MouseEvent.offsetY;	
        		svgMouseMove(mx,my);
    		}            		            		
	});
	
	svgcanvas.mouseup(function(MouseEvent) {
		ux=MouseEvent.offsetX;
		uy=MouseEvent.offsetY;	
		svgMouseUp();
	});
	
}
//鼠标按下
function svgMouseDown()
{		          				
		var x=$("#svgcanvas").offset().left + dx;
		var y=$("#svgcanvas").offset().top + dy;
		var c_in = Snap.getElementByPoint(x, y);
		if(c_in.type=="svg")
		{		            			
				$("#adjust").css("display","none");
				$("#layer").css("display","block");	   
				selectChangeSvg(null);
		}
		else{
				isMove=true;	            					
				$("#adjust").css("display","block");
				$("#layer").css("display","none");
				if(c_in.type=="circle")
					{
					ox = parseInt(c_in.attr("cx"));
			        oy = parseInt(c_in.attr("cy"));
			        selectGrip=c_in;
			        }
				else{						
					ox = parseInt(c_in.parent().attr("x"));
			        oy = parseInt(c_in.parent().attr("y"));
			        selectChangeSvg(c_in.parent());						
				}
		}	            				     
}
//鼠标移动
function svgMouseMove(mx,my)
{		
	if(selectGrip!=null)
		{
			selectGrip.attr({"cx": ox + mx - dx, "cy": oy + my - dy});
			var cx = parseInt(selectGrip.attr("cx"));
			var cy = parseInt(selectGrip.attr("cy"));
			
			switch(selectGrip.attr("id"))
			{
				case "selectorGrip_resize_nw":/*左上*/{	
					var secx = parseInt(svgcanvas.select("#selectorGrip_resize_se").attr("cx"));
					var secy = parseInt(svgcanvas.select("#selectorGrip_resize_se").attr("cy"));
					if(cx > secx - minWidth){cx = secx - minWidth;this.attr({"cx": secx - minWidth});}
					if(cy > secy - minHeight){cy = secy - minHeight;this.attr({"cy": secy - minHeight});}					
					svgResize(cx,cy,secx - cx,secy - cy);
				};break;
				case "selectorGrip_resize_n":/*上*/{
					var scx = parseInt(svgcanvas.select("#selectorGrip_resize_s").attr("cx"));
					var scy = parseInt(svgcanvas.select("#selectorGrip_resize_s").attr("cy"));
					
					if(cy > scy - minHeight){cy = scy - minHeight;this.attr({"cy": scy - minHeight});}
					
					var left=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cx"));
					var right=parseInt(svgcanvas.select("#selectorGrip_resize_ne").attr("cx"));
										
					svgResize(left,cy,right - left,scy - cy);	
					
				};break;
				case "selectorGrip_resize_ne":/*右上*/{
					var swcx = parseInt(svgcanvas.select("#selectorGrip_resize_sw").attr("cx"));
					var swcy = parseInt(svgcanvas.select("#selectorGrip_resize_sw").attr("cy"));
					
					if(cx < swcx + minWidth){cx = swcx + minWidth;this.attr({"cx": swcx + minWidth});}
					if(cy > swcy - minHeight){cy = swcy - minHeight;this.attr({"cy": swcy - minHeight});}
					
					var left=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cx"));
					var right=cx
										
					svgResize(left,cy,right - left,swcy - cy);	
				};break;
				case "selectorGrip_resize_e":/*右*/{
					var wcx = parseInt(svgcanvas.select("#selectorGrip_resize_w").attr("cx"));
					var wcy = parseInt(svgcanvas.select("#selectorGrip_resize_w").attr("cy"));
					
					if(cx < wcx + minWidth){cx = wcx + minWidth;this.attr({"cx": wcx + minWidth});}
					
					var left=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cx"));
					var top=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cy"));
					var right=cx;
										
					svgResize(left,top,right - left,parseInt(svgcanvas.select("#selectorGrip_resize_s").attr("cy")) - top);	
				};break;
				case "selectorGrip_resize_se":/*右下*/{
					var nwcx = parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cx"));
					var nwcy = parseInt(svgcanvas.select("#selectorGrip_resize_ne").attr("cy"));
					
					if(cx < nwcx + minWidth){cx = nwcx + minWidth;this.attr({"cx": nwcx + minWidth});}
					if(cy < nwcy + minHeight){cy = nwcy + minHeight;this.attr({"cy": nwcy + minHeight});}
					
					var left=nwcx;
					var top=nwcy;
					var right=cx
										
					svgResize(left,top,right - left,cy - top);	
				};break;
				case "selectorGrip_resize_s":/*下*/{
					var ncx = parseInt(svgcanvas.select("#selectorGrip_resize_n").attr("cx"));
					var ncy = parseInt(svgcanvas.select("#selectorGrip_resize_n").attr("cy"));
					
					if(cy < ncy + minHeight){cy = ncy + minHeight;this.attr({"cy": ncy + minHeight});}
					
					var left=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cx"));
					var right=parseInt(svgcanvas.select("#selectorGrip_resize_ne").attr("cx"));
										
					svgResize(left,ncy,right - left,cy - ncy);	
				};break;
				case "selectorGrip_resize_sw"/*左下*/:{
					var necx = parseInt(svgcanvas.select("#selectorGrip_resize_ne").attr("cx"));
					var necy = parseInt(svgcanvas.select("#selectorGrip_resize_ne").attr("cy"));
					
					if(cx > necx - minWidth){cx = necx - minWidth;this.attr({"cx": necx - minWidth});}
					if(cy < necy + minHeight){cy = necy + minHeight;this.attr({"cy": necy + minHeight});}
											
					var right=necx
										
					svgResize(cx,necy,right - cx,cy - necy);	
				};break;
				case "selectorGrip_resize_w":/*左*/{
					var ecx = parseInt(svgcanvas.select("#selectorGrip_resize_e").attr("cx"));
					var ecy = parseInt(svgcanvas.select("#selectorGrip_resize_e").attr("cy"));
					
					if(cx > ecx - minWidth){cx = ecx - minWidth;this.attr({"cx": ecx - minWidth});}
					
					var left=cx
					var top=parseInt(svgcanvas.select("#selectorGrip_resize_nw").attr("cy"));
					var right=ecx;
										
					svgResize(cx,top,right - left,parseInt(svgcanvas.select("#selectorGrip_resize_s").attr("cy")) - top);	
				};break;
			}
		}
	else
		{
		selectItem.attr({"x": ox + mx - dx, "y": oy + my - dy});
		selectChangeSvg(selectItem);
		}
}
//鼠标抬起
function svgMouseUp()
{
	isMove=false;
	selectGrip=null	
}
//大小改变
function svgResize(left, top, width, height)
{				
	selectItem.attr({
		x:left,
	    y: top,
	    width: width,
	    height:height
	});
	var target =selectItem.attr("target");
	switch(target)
	{
		case "text":{
			selectItem.select("rect").attr({
				x:0,
				y:0,
				width: width,
			    height:height
			});
			
			selectItem.select("text").attr({
				x:0,
				y:height				
			});
		};break;
	}
	selectChangeSvg(selectItem);	
}
//更新控制点
function updateSelectorGrip(svgitem)
{
	if(svgitem!=null)
		{
		var pointSize=4;
		var fillcolor="#0f03";
		var strokecolor="#f00";
		var strokeWidth=0.1;
		var pointArray=new Array(8)
		var left,top,width,height;
		svgcanvas.select("#selectPointGroup").before(svgitem);
		left=parseInt(svgitem.attr("x"));
		top=parseInt(svgitem.attr("y"));
		width=parseInt(svgitem.attr("width"));
		height=parseInt(svgitem.attr("height"));
		  
		  pointArray[0]={x:parseInt(left),y:parseInt(top)};
		  pointArray[1]={x:parseInt(left+width/2),y:parseInt(top)};
		  pointArray[2]={x:parseInt(left+width),y:parseInt(top)};
		  pointArray[3]={x:parseInt(left+width),y:parseInt(top+height/2)};
		  
		  pointArray[4]={x:parseInt(left+width),y:parseInt(top+height)};
		  pointArray[5]={x:parseInt(left+width/2),y:parseInt(top+height)};
		  pointArray[6]={x:parseInt(left),y:parseInt(top+height)};
		  pointArray[7]={x:parseInt(left),y:parseInt(top+height/2)};
		
		svgcanvas.select("#selectPointGroup").attr({
			display:"inline"					  
		});		
		
		svgcanvas.select("#selectorGrip_resize_nw").attr({
			cx:pointArray[0].x,
			cy:pointArray[0].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_n").attr({
			cx:pointArray[1].x,
			cy:pointArray[1].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_ne").attr({
			cx:pointArray[2].x,
			cy:pointArray[2].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_e").attr({
			cx:pointArray[3].x,
			cy:pointArray[3].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_se").attr({
			cx:pointArray[4].x,
			cy:pointArray[4].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_s").attr({
			cx:pointArray[5].x,
			cy:pointArray[5].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_sw").attr({
			cx:pointArray[6].x,
			cy:pointArray[6].y,
			r:pointSize
		});
		svgcanvas.select("#selectorGrip_resize_w").attr({
			cx:pointArray[7].x,
			cy:pointArray[7].y,
			r:pointSize
		});
	}
	else
		{
		var pointSize=4;
		var fillcolor="#0f03";
		var strokecolor="#f00";
		var strokeWidth=0.1;
		var left=0,top=0,width=0,height=0;
		if(width<minWidth){width=minWidth;}
		if(height<minHeight){width=minHeight;}
		var nwPoint = svgcanvas.paper.circle(left, top, pointSize).attr({
			id:"selectorGrip_resize_nw",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"nw-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var nPoint = svgcanvas.paper.circle(left+width/2, top, pointSize).attr({
			id:"selectorGrip_resize_n",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"n-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var nePoint = svgcanvas.paper.circle(left+width, top, pointSize).attr({
			id:"selectorGrip_resize_ne",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"ne-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var ePoint = svgcanvas.paper.circle(left+width, top+height/2, pointSize).attr({
			id:"selectorGrip_resize_e",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"e-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var sePoint = svgcanvas.paper.circle(left+width, top+height, pointSize).attr({
			id:"selectorGrip_resize_se",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"se-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var sPoint = svgcanvas.paper.circle(left+width/2, top+height, pointSize).attr({
			id:"selectorGrip_resize_s",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"s-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var swPoint = svgcanvas.paper.circle(left, top+height, pointSize).attr({
			id:"selectorGrip_resize_sw",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"sw-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		var wPoint = svgcanvas.paper.circle(left, top+height/2, pointSize).attr({
			id:"selectorGrip_resize_w",
			fill:fillcolor,
		    stroke: strokecolor,
		    strokeWidth: strokeWidth,
		    cursor:"w-resize",
		    //target:"item",
		    pointerevents:"all"			    
		});
		
		var g = svgcanvas.paper.g().attr({
			id:"selectPointGroup",
			display:"none"
		});
		g.add(nwPoint, nPoint,nePoint, ePoint,sePoint, sPoint,swPoint, wPoint);
		}
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
//选择显示项改变
function selectChangeSvg(svg)
{
	selectItem=svg;		
	if(svg==null) {svgcanvas.select("#selectPointGroup").attr({display:"none"});	return;}
	$("#adjust").css("display","block");
	$("#layer").css("display","none");
	updateSelectorGrip(svg);
	var target=svg.attr("target");
	var item = svg.select(target);
	switch(target)
	{				
		case "rect":{			
	        $("#svg_position_X").val(parseInt(svg.attr("x")));
	        $("#svg_position_Y").val(parseInt(svg.attr("y")));
	        $("#svg_size_width").val(parseInt(svg.attr("width")));
	        $("#svg_size_height").val(parseInt(svg.attr("height")));
	        $("#svg_stroke_width").val(parseFloat(item.attr("strokeWidth")));
	        $("#svg_color_stroke").val(colorRGB2Hex(item.attr("stroke")));
	        $("#svg_color_fill").val(colorRGB2Hex(item.attr("fill")));
			$("#svg_rotate_corner").css("display","block");	   
	        $("#svg_rotate_corner").val(parseInt(item.attr("rx")));
	        $("#svg_stroke_dashed").val(parseInt(item.attr("stroke-dasharray")));
	        $("#svg_color_opacity").val(parseFloat(item.attr("fill-opacity")));
		};break;
		case "ellipse":{		       	        
	        $("#svg_position_X").val(parseInt(svg.attr("x")));
	        $("#svg_position_Y").val(parseInt(svg.attr("y")));
	        $("#svg_size_width").val(parseInt(svg.attr("width")));
	        $("#svg_size_height").val(parseInt(svg.attr("height")));
	        $("#svg_stroke_width").val(parseFloat(item.attr("strokeWidth")));	       
	        $("#svg_color_stroke").val(colorRGB2Hex(item.attr("stroke")));
	        $("#svg_color_fill").val(colorRGB2Hex(item.attr("fill")));	        
	        $("#svg_rotate_corner").css("display","none");
	        $("#svg_stroke_dashed").val(parseInt(item.attr("stroke-dasharray")));
	        $("#svg_color_opacity").val(parseFloat(item.attr("fill-opacity")));
		};break;	
		case "image":{
	        $("#svg_position_X").val(parseInt(svg.attr("x")));
	        $("#svg_position_Y").val(parseInt(svg.attr("y")));
	        $("#svg_size_width").val(parseInt(svg.attr("width")));
	        $("#svg_size_height").val(parseInt(svg.attr("height")));
	        /*
	        $("#svg_stroke_width").val(parseFloat(svg.attr("strokeWidth")));
	        $("#svg_color_stroke").val(colorRGB2Hex(svg.attr("stroke")));
	        $("#svg_color_fill").val(colorRGB2Hex(svg.attr("fill")));	        
	        var deg = getmatrix(svg.transform().localMatrix);
	        $("#svg_rotate_value").val(deg);	        
			$("#svg_rotate_corner").css("display","block");	   
	        $("#svg_rotate_corner").val(parseInt(svg.attr("rx")));
	        $("#svg_stroke_dashed").val(parseInt(svg.attr("stroke-dasharray")));
	        $("#svg_color_opacity").val(parseFloat(svg.attr("fill-opacity")));
	        */
		};break;
		case "text":{
	        $("#svg_position_X").val(parseInt(svg.attr("x")));
	        $("#svg_position_Y").val(parseInt(svg.attr("y")));
	        $("#svg_size_width").val(parseInt(svg.attr("width")));
	        $("#svg_size_height").val(parseInt(svg.attr("height")));	      
		};break;
	}       
}
//取数据库广告列表
function getadvList()
{
	$.ajax({  
        url:"/getadvList",          
        type:"post",
        success:function(data)  
        {       
        	if(data!=null && data.length>0)
        		{
        			for(var i=0;i<data.length;i++)
        				{
        					var id=data[i].id;
        					var advname=data[i].advname;            					
        					var btndel="<button type='button' class='btn btn-default btn-xs btn-outline-success float-right' onclick='infoDeletebyid("+id+")'><span class='fa fa-remove'></span></button>";
        					var el="<li id='adv"+id+"' class='list-group-item list-group-item-info list-group-item-action'><a onclick='infoSelectChange("+id+")'>"+advname+"</a>"+btndel+"</li>";        					
        					$("#list_group_draft").append(el);
        				}
        		}
        },  
        error: function() {  
            alert("error");  
          }  
    }); 
}
//广告选择改变
function infoSelectChange(infoid)
{
	if(infoid!=selectinfoid)
	{
		if(selectinfoid!=0)
			{				
				saveitem(selectinfoid);					
				selectinfoid=infoid;
			}
		else{selectinfoid=infoid;}
		
		getitem(infoid);
	}
}
//保存显示项
function saveitem(infoid)
{		
	var jsonString = JSON.stringify(itemmap);
	$.ajax({  
        url:"/SaveItem",         
        data:{
        	infoid:infoid,
        	arritem:jsonString      	
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{        		
        		}
        	else
        		{
        			alert(data.resultMessage);
        		}
        },  
        error: function() {  
            alert("error");  
          }  
    });
}
//layer选择改变
function selectchangeLayer(pageid)
{
	var setlist = svgcanvas.selectAll("svg");
	if(setlist.length>0)
		{					
			setlist.forEach(function(element, index) {
			    element.remove();						    
			});				
		}	
	//var scale = parseInt(($("#workarea").width() - 40)/screenw);
    
	if(itemmap.hasOwnProperty(pageid))
	{
		for(var i=0;i<itemmap[pageid].length;i++)
			{
				
				var item = itemmap[pageid][i];
				addSvgItem(pageid,item.name,false);
				/*
				var itemtype='text';
				switch(item.type)
				{
				case 0:{itemtype='text';};break;
				case 1:{itemtype='video';};break;
				}				
	    		var itemsvg = svgcanvas.paper.svg(item.left*scale, item.top*scale, item.width*scale, item.height*scale).attr({id:"item"+item.name,target:itemtype});
	    		
	    		itemsvg.paper.rect(0, 0, item.width*scale, item.height*scale).attr({	
	    			fill:item.backcolor,
				    "fill-opacity": 100   
				});
	    		
	    		var jsonObj =  JSON.parse(item.context);
			    for(var j=0;j<jsonObj.length;j++){
		            var jsonpt=jsonObj[j]	            
		            switch(jsonpt.type)
		            {
		            	case 0:{
	    				    itemsvg.paper.text(0, (item.height*scale - jsonpt.fontsize*scale)/2 + jsonpt.fontsize*scale, jsonpt.text).attr({				
	    						fill: jsonpt.color,
	    					    style:"font-size:"+jsonpt.fontsize*scale+"px;pointer-events: none;"
	    					});
		            	};break;
		            	case 1:{var image=jsonpt.image};break;
		            }	            
			    }
			    */
			}
	}		
	selectpageid=pageid;
}
//添加图层
function addlayer(pageid,itemlist,isSelect)
{	
    var scale = $("#layer_list").width() / screenw;	    
    
	var svg = Snap(screenw*scale, screenh*scale).attr({
		left:0,		
		fill:"#00f",
		id:"page"+pageid
	}).drag().click(function() {
	    var pageid = parseInt(this.attr("id").slice(4));
	    if(selectpageid!=pageid)
	    	{selectchangeLayer(pageid);}
	});	
	
    for(var i=0;i<itemlist.length;i++)
    	{
    		var item = itemlist[i];
    		var itemtype='text';
			switch(item.type)
			{
			case 0:{itemtype='text';};break;
			case 1:{itemtype='video';};break;
			}
    		var itemsvg = svg.paper.svg(item.left*scale, item.top*scale, item.width*scale, item.height*scale).attr({id:"item"+item.name,target:itemtype});
    		
    		itemsvg.paper.rect(0, 0, item.width*scale, item.height*scale).attr({
    			fill:item.backcolor,
			    "fill-opacity": 100   
			});
    		    		
		    var jsonObj =  JSON.parse(item.context);
		    for(var j=0;j<jsonObj.length;j++){
	            var jsonpt=jsonObj[j]	            
	            switch(jsonpt.type)
	            {
	            	case 0:{
    				    itemsvg.paper.text(0, (item.height*scale - jsonpt.fontsize*scale)/2 + jsonpt.fontsize*scale, jsonpt.text).attr({				
    						fill: jsonpt.color,
    					    style:"font-size:"+jsonpt.fontsize*scale+"px;pointer-events: none;"
    					});
	            	};break;
	            	case 1:{var image=jsonpt.image};break;
	            }	            
		    }
    	}    	
	$("#layer_list").append(svg.node);	
	
	if(isSelect)
		{selectchangeLayer(pageid);}
}
//获取显示项
function getitem(infoid)
{	
	$.ajax({  
        url:"/GetItem",         
        data:{
        	infoid:infoid     	
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{       
        			if(data.itemlist!=null && data.itemlist.length>0)
        				{       
        					itemmap={};
        					
        					for(var i=0;i<data.itemlist.length;i++)
        						{
        							var jitem=data.itemlist[i];
        							var pageid =jitem.pageid;
        							var delindex =jitem.delindex;        							
        							if(delindex==0)
        								{
        								additem(pageid,jitem.itemid,jitem.itemleft,jitem.itemtop,jitem.itemwidth,jitem.itemheight,jitem.itembackcolor,100,jitem.itemtype,jitem.itemcontext);
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
        				    additem(1,1,0,0,192,32,'#fff',100,0,"[{"+'"'+"color"+'"'+":"+'"'+"#00f"+'"'+","+'"'+"fontsize"+'"'+":32,"+'"'+"text"+'"'+":"+'"'+"文明驾驶安全行车"+'"'+","+'"'+"type"+'"'+":0}]");
        					addlayer(1,itemmap[1],true)   
        				}
        		}
        	else
        		{
        			alert(data.resultMessage);
        		}
        },  
        error: function() {  
            alert("error");  
          }  
    });
}
//添加item
function additem(pageid,itemid,left,top,width,height,backcolor,backopacity,type,context)
{
	var item=new Object();
    item.name=itemid;
    item.left=left;
    item.top=top;
    item.width=width;
    item.height=height;
    item.backcolor=backcolor;
    item.backopacity=backopacity;
    item.type=type;
    item.context=context;
    
	if(itemmap.hasOwnProperty(pageid))
	{		
		itemmap[pageid].push(item);
	}
else
	{        				
		var itemlist=[]
		itemlist.push(item);
		itemmap[pageid]=itemlist;
	}
}
//删除广告通过id
function infoDeletebyid(infoid)
{	
	$.ajax({  
        url:"/DeleteInfobyid", 
        data:{
        	infoid:parseInt(infoid)      	
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{
        			$("#adv"+infoid).remove();        			
        		}
        	else
        		{
        			alert(data.resultMessage);
        		}
        },  
        error: function() {  
            alert("error");  
          }  
    });
}
