var lineH=1;
//layer选择改变
function selectchangeLayer(pageid)
{	
	$("#layer_list .page_header canvas").css('border','2px outset');		
	$("#layer_list .collapse").removeClass('show');
			
	$("#layer_list #list_page_"+pageid).css('border','2px inset');
	$("#layer_list #page_collapse_"+pageid).addClass('show');
	
	if(selectpageid!=pageid)
	{
		$('#workarea_canvas').clearCanvas();
		$('#workarea_canvas').removeLayers();
		if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item = itemmap[pageid][i];					
					if(i==0)
					{
						 $('.page_context canvas').css("border-color","black");
						 $('#list_page'+pageid+'_item'+item.itemid).css("border-color","yellow");
						 			 
						 selectChangeItem(pageid,item.itemid);
						 updateCanvasItem(pageid,item.itemid,true,true);	
					}				
					else {
						updateCanvasItem(pageid,item.itemid,false,true);	
					}
				}
		}
		selectpageid=pageid;
	}
}
//列表单击
function listitemclick(obj)
{
	$('.page_context canvas').css("border-color","black");
	$(obj).css("border-color","yellow");
	var canvasid = $(obj)[0].id;
	var pageid = canvasid.substring(9,canvasid.indexOf("_item"));
	selectchangeLayer(pageid);
	var itemid = canvasid.substring(canvasid.indexOf("_item")+5);
	selectChangeItem(pageid,itemid);				  
	var layer = $('#workarea_canvas').getLayer('item'+itemid);				 
	DrawControl('#workarea_canvas',layer.x,layer.y,layer.width,layer.height);
	//window.event.cancelBubble = true; 
}
//列表双击
function listitemdblclick(obj)
{	
	var canvasid = $(obj)[0].id;
	var pageid = canvasid.substring(9,canvasid.indexOf("_item"));	
	var itemid = canvasid.substring(canvasid.indexOf("_item")+5);
	if(!ispermission) {alertMessage(1, "警告", "没有相关操作权限,请联系管理员!");return;}	 
	    if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i];
					if(item.itemid==itemid)
						{																
//						var background_position = $('.edui-default .edui-for-customimage .edui-icon').css('background-position');
//						$('#myModalEdit').modal('show');
//						$("#select_div").val("0");
//						$("#div_tp").css("display","inline");
//						$("#div_gif").css("display","none");
//						tinymce.activeEditor.setContent(item.context);
//						initspecial(item.itemstyle.special);
						switch(item.type)
						{
						case 0:{
							var background_position = $('.edui-default .edui-for-customimage .edui-icon').css('background-position');
							$('#myModalEdit').modal('show');										
							$("#myModalEdit").attr("data-type",0);
							$("#div_tp").css("display","inline");
							$("#div_gif").css("display","none");										
							//ue.setContent(item.context);
							tinymce.activeEditor.setContent(item.context);
							initspecial(item.itemstyle.special);
						};break;
						case 4:{
							$('#myModalEdit').modal('show');										
							$("#myModalEdit").attr("data-type",1);
							$("#div_tp").css("display","none");
							$("#div_gif").css("display","inline");
							initgif(1,9,true);
						};break;
						}
						break;
						}
				}
		}			    
}
//交集
function array_intersection(a, b) { // 交集
    var result = [];
    for(var i = 0; i < b.length; i ++) {
        var temp = b[i];
        for(var j = 0; j < a.length; j ++) {
            if(temp === a[j]) {
                result.push(temp);
                break;
            }
        }
    }
    return result;
}
//差集 a - b
function array_difference(a, b) { 
    //clone = a
    var clone = a.slice(0);
    for(var i = 0; i < b.length; i ++) {
        var temp = b[i];
        for(var j = 0; j < clone.length; j ++) {
            if(temp === clone[j]) {
                //remove clone[j]
                clone.splice(j,1);
            }
        }
    }
    return clone;
}
//获取空闲位置
function getRect()
{
	var colCount = parseInt(Math.ceil(screenw/lineH));
	var cols = [];
	
	for(var c=0;c<colCount;c++)
		{
		for(var i=0;i<screenh;i++)
			{
				if(cols[c]==null){cols[c]=[];}
				cols[c][i]=i;
			}
		}
	
	var itemid=0;
	for(var i=0;i<itemmap[selectpageid].length;i++)
	{
		var item=itemmap[selectpageid][i];
		var sr=[];
		for(var r=0;r<item.height;r++)
			{sr[r] = item.top + r;}
		
		var startr= parseInt(Math.floor(item.left/lineH));
		var endr= parseInt(Math.floor((item.left + item.width - 1)/lineH));
		for(var r=startr;r<=endr;r++)
			{
				//var col = array_intersection(sr,cols[r])
				var col = array_difference(cols[r],sr)
				//var col1 = cols[r].filter((val)=>!new Set(sr).has(val));
				cols[r] = col;
			}						
	}
	
	var items=[];
	var x=-1,y=-1,w=- 1,h=- 1;
	for(var i=0;i<cols.length;i++)
	{
		var col=cols[i];//获取行数据		
		if(col!=null && col.length>=16)//如果本行有空余数据进入
		{								
			var hh=0,index=0;//每行的连续值是宽度
			for(var j=0;j<col.length - 1;j++)
			{
				var now=col[j];
				var next=col[j + 1];
				if(next == now + 1)
					{															
					if(next - col[index]>hh)
						{hh = next - col[index];}
					}
				else {
				index = j + 1;
				}
			}
			
			var item={
				x:i,
				y:index,
				h:hh + 1
			};
			
			items.push(item);
		}
	}
	var index=0,ww=0;
	for(var i=0;i<items.length - 1;i++)
	{		
		var now=items[i].x;
		var next=items[i + 1].x;
		if(next == now + 1)
			{
			if(items[i].y == items[i + 1].y && items[i].h == items[i + 1].h)
				{
				if(next - items[index].x + 1>ww)
					{ww = next - items[index].x + 1;}
				if(ww >= 16)
					{
					w = ww;
					}
				}
			else {
				index = i+1;	
				}
			}
		else {
			if(w>0)
				{break;}
			index = i+1;
		}
	}
	
	if(items!=null && items.length>0)
		{
		return map={
				x:items[index].x,
				y:items[index].y,//y,
				w:w,//w,
				h:items[index].h//h
		};
		}
	else {
		return map={
				x:-1,
				y:-1,
				w:0,
				h:0
		};
	}
}
//添加列表
function list_additem(obj)
{	
	if(!ispermission) {alertMessage(1, "警告", "没有相关操作权限,请联系管理员!");return;}
	if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid))
	{				
		var rect = getRect();
		if(rect.x==-1 || rect.y==-1 || rect.w<16 || rect.h<16)
			{alertMessage(1, "警告", "没有空余空间添加显示项,显示项最小大小为16*16!");return;}
		var itemid=0;
		for(var i=0;i<itemmap[selectpageid].length;i++)
		{
			var item=itemmap[selectpageid][i];	
			if(item.itemid>itemid)
				{itemid=item.itemid;}
			updateitem(selectpageid,item.itemid,["linkmove"],[1]);									
		}

		var itemid = itemid + 1
		additem(selectpageid,itemid, parseInt(rect.x),parseInt(rect.y),parseInt(rect.w),parseInt(rect.h),0,'',100,'',100,0,"<p>文明驾驶安全行车</p>",null);
		
		var scaleone = ($("#tool_right").width()-30) / screenw;
		var wo =screenw*scaleone;
		var ho =screenh*scaleone;
		
		for(var i=0;i<itemmap[selectpageid].length;i++)
			{
			var item=itemmap[selectpageid][i];
			if(item.itemid==itemid)
				{
				var playtime = item.itemstyle.playtime;
		 		
		 		$("#page_footer_"+selectpageid).append("<a id='list_page"+selectpageid+"_itempt"+itemid+"' style='font-size:10px;color:white;'>"+playtime+"|</a>");
				break;
				}
			}
 		
		$('#page_context_'+selectpageid).append("<canvas id='list_page"+selectpageid+"_item"+itemid+"' width="+wo+"px height="+ho+"px style='border:2px dashed; onclick='listitemclick(this)'></canvas>");
		
		$('#list_page'+selectpageid+'_item'+itemid).click(function(){    	
			listitemclick(this);
	    });
		
		$('#list_page'+selectpageid+'_item'+itemid).dblclick(function(){    	
			listitemdblclick(this);
	    });
				
		updatalistbackground('list_page'+selectpageid+'_item'+itemid,selectinfoid);
		updateCanvasItem(selectpageid,itemid,true,true);
		
		if(infomap.hasOwnProperty(selectinfoid))
		{
			infomap[selectinfoid].isSave=1;			
		}
		setTimeout(listitemclick,200,$('#list_page'+selectpageid+'_item'+itemid));		
	}
}
//删除列表
function list_deleteitem(obj)
{
	if(!ispermission) {alertMessage(1, "警告", "没有相关操作权限,请联系管理员!");return;}
	if(selectpageid!=0 && itemmap.hasOwnProperty(selectpageid) && selectitemid!=0)
	{			
		
		var returndata = deleteitem(selectpageid,selectitemid)
		 if(returndata.isSucess==1)
			 {alertMessage(1, "警告", "只有一个显示项不能删除");}
		 else
			 {
				 $('#workarea_canvas').removeLayer('item'+selectitemid);
				 $('list_page_'+selectpageid).removeLayer('item'+selectitemid);
				 $('#list_page'+selectpageid+'_item'+selectitemid).remove();
				 
				 $('#list_page'+selectpageid+'_itempt'+selectitemid).remove();
				 var pid = returndata.pageid;
				 var iid = returndata.itemid;
				 
				 if(pid!=selectpageid)
					 {
					 $('#page_'+selectpageid).remove();
					 }
				 				 
				 $('.page_context canvas').css("border-color","black");
				 $('#list_page'+pid+'_item'+iid).css("border-color","yellow");
				 selectchangeLayer(pid);				 
				 selectChangeItem(pid,iid);
				 
				 var layer = $('#workarea_canvas').getLayer('item'+iid);				 
				 DrawControl('#workarea_canvas',layer.x,layer.y,layer.width,layer.height);
				 window.event.cancelBubble = true; 
			 }		
	}		
}
//添加图层
function addlayer(pageid,itemlist,isSelect)
{	
	var divlayer="<div id='page_"+pageid+"' class='listbox'></div>"
	var divheader="<div id='page_header_"+pageid+"' class='page_header' data-toggle='collapse'></div>";
	var divcollapse="<div id='page_collapse_"+pageid+"' class='collapse' style='border:2px solid #d0e4fe;padding: 5px;background-color:#C36464;'></div>"
	var divcontext="<div id='page_context_"+pageid+"' class='page_context'></div>";
	var divfooter="<div id='page_footer_"+pageid+"'><a><i class='fa fa-file' style='font-size:16px;color:white;float:right;' onclick='list_additem(this)'></i></a><a><i class='fa fa-trash' style='font-size:16px;color:white;float:right;'  onclick='list_deleteitem(this)'></i></a><a><i class='fa fa-arrow-down' style='font-size:16px;color:white;float:right;'></i></a><a><i class='fa fa-arrow-up' style='font-size:16px;color:white;float:right;'></i></a></div>"
	
	$("#layer_list").append(divlayer);
	
	$("#page_"+pageid).append(divheader);	
	$("#page_"+pageid).append(divcollapse);
	
	//$("#layer_H_list").append(divcontext);
	$("#page_collapse_"+pageid).append(divcontext);	
	$("#page_collapse_"+pageid).append(divfooter);
		   	
    var scale = ($("#tool_right").width()-10) / screenw;
    //scale=2;
	var w =screenw*scale;
	var h =screenh*scale;
	
	var scaleone = ($("#tool_right").width()-30) / screenw;
	//scaleone=2;
	var wo =screenw*scaleone;
	var ho =screenh*scaleone;
	var page ="<canvas id='list_page_"+pageid+"' width="+w+"px height="+h+"px style='border:2px outset;'></canvas>";	
	$("#page_header_"+pageid).append(page);
	
//	var page ="<canvas id='list_page_"+pageid+"' width="+w+"px height="+h+"px style='margin:5px 5px;'></canvas>";
//	$("#layer_items").append(page);
	
	
	updatalistbackground('list_page_'+pageid,selectinfoid);
	
	for(var i=0;i<itemlist.length;i++)
 	{
 		var item = itemlist[i];
 		var playtime = item.itemstyle.playtime;
 		
 		$("#page_footer_"+pageid).append("<a id='list_page"+pageid+"_itempt"+item.itemid+"' style='font-size:10px;color:white;'>"+playtime+"|</a>");	
 		
 		$('#page_context_'+pageid).append("<canvas id='list_page"+pageid+"_item"+item.itemid+"' width="+wo+"px height="+ho+"px style='border:2px dashed;' onclick='listitemclick(this)' ondblclick='listitemdblclick(this)'></canvas>");
 		updatalistbackground('list_page'+pageid+'_item'+item.itemid,selectinfoid);
 		updateCanvasItem(pageid,item.itemid,isSelect,true); 		
 	}
	
	$("#page_header_"+pageid).click(function(){    	
    	var pageid = parseInt(this.id.slice(12));
    	selectchangeLayer(pageid);
    });
	
	if(isSelect)
	{selectchangeLayer(pageid);}
}
//删除图层
function deletelayer(pageid)
{
	 if(itemmap.hasOwnProperty(pageid))
		{		 
		 delete itemmap[pageid]; 
		}
}
//画背景图到列表项
function updatalistbackground(canvasid,infoid)
{
	var infodata=infomap[selectinfoid];
    var bjsJson = JSON.parse(infodata.BackgroundStyle);
    //screenw=bjsJson.width;
    //screenh=bjsJson.height;
    
    var basemapStyle={};
    var bmslist = bjsJson.basemapStyle;
    if(bmslist!=null && bmslist.length>0)
    	{
	    for(var i=0;i<bmslist.length;i++)
	    	{
		    	var bms = bmslist[i];
	    		var basemalistmode = bms.basemalistmode;
	    		basemapStyle.type=basemalistmode;
	    		switch(basemalistmode)
	    		{
		    		case 2:{
		    			var basemabackcolor = bms.basemabackcolor;  
		    			basemapStyle.basemabackcolor=basemabackcolor;		    			   			
		    		};break;
		    		case 3:{	    					    			
		    			var basemalistsrc = bms.basemalistsrc;			    			
		    			basemapStyle.basemabackgroud=basemalistsrc;
		    		};break;
	    		}
	    		break;
	    	}
    	}
    else
    	{basemapStyle.type=0;}
	if(basemapStyle!=null)
		{
			switch(basemapStyle.type)
			{
			case 0:{
				$("#"+canvasid).css("background-image","");    			
				$("#"+canvasid).css("background-size","");
				$("#"+canvasid).css("background-repeat","");
		    	$("#"+canvasid).css("background-color","#ffffff");
			};break;
			case 2:{
				$("#"+canvasid).css("background-image","");    			
    			$("#"+canvasid).css("background-size","");
    			$("#"+canvasid).css("background-repeat","");
    			$("#"+canvasid).css("background-color",basemapStyle.basemabackcolor);    			
			};break;
			case 3:{
				var urlbase64 = "url('"+basemapStyle.basemabackgroud+"')";
				$("#"+canvasid).css("background-color","");
    			$("#"+canvasid).css("background-image",urlbase64);
    			
    			var size =$("#"+canvasid).attr('width')+" "+$("#"+canvasid).attr('height');
    			$("#"+canvasid).css("background-size",size);
    			$("#"+canvasid).css("background-repeat",'no-repeat');
			};break;
			}
		}		
}