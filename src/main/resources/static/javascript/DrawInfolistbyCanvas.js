var marginLeft=20;marginTop=20;
var pointW=20,pointH=20;
var colCount=30,rowCount=0;
var selectlistcycle=0;
var itemlist=[];
var selectinfoid=0;
var backColor='#DAD7DB';//'#515151';
var strokeColor='#FFFFFF';
var strokeW=0.5;
var itembackColor='#505052';//'#9D91A2';//'#FFEFD5AA';//'#8F8F8FAA';
var itemSelectbackColor='#48D2CA';//'#287FD3';//'#B3D4FDAA';
var linestrokeStyle ='#CD8500';
var lineWidth =2;
var Interval = 3;
var isMove = false;
var MovestratIndex = -1;

function tooltipshow(x,y,txt)
{
	//$('#tooltip').text(txt);
	$('#tooltip').empty();
	$('#tooltip').append(txt);
	$('#tooltip').css('left',$('#pt_template').offset().left + x);
	$('#tooltip').css('top',$('#pt_template').offset().top + y);
	$('#tooltip').css('visibility','visible');
}

function tooltiphide()
{
	$('#tooltip').css('visibility','hidden');
}
//画排期图背景
function DrawBackground(listcycle)
{	
//	console.warn("画背景 开始时间" + getNowFormatDatetime());
	selectlistcycle=listcycle;
	var canvasWidth = $('#info_workarea').width();
	//colCount=parseInt((canvasWidth - 2*marginLeft)/pointW);
	pointW=parseInt((canvasWidth - 2*marginLeft)/colCount);
	rowCount=Math.ceil(listcycle/colCount);
	var canvasHeight=rowCount*pointH + 2*marginTop;
	
	var canvas = document.createElement("canvas");							
    canvas.width = canvasWidth;
    canvas.height = canvasHeight;
    
	$('#info_canvas').attr('width',canvasWidth);
	$('#info_canvas').attr('height',canvasHeight);
	
	$('#info_canvas').clearCanvas();
	$('#info_canvas').removeLayers();
	
	$(canvas).drawRect({
		  layer: true,
		  name:'backgroundrect',
		  groups: ['background'],
		  fillStyle: backColor,
		  x: marginLeft, y: marginTop,
		  width: colCount * pointW,
		  height: rowCount * pointH,		  
		  fromCenter: false,
		  /*
		  mousemove: function(layer) {
			  if(!isMove)
				  {
				  	var distX, distY;
				    distX = layer.eventX;
				    distY = layer.eventY;	
				    
				    var stratIndex = point2Int(distX,distY);				    
					tt = stratIndex + "秒";
					tooltipshow(distX,distY,'开始时间:' + tt);
				  }
			  },
		  mouseout: function(layer) {
			    isMove=false;
				tooltiphide();
			  },
		  mousedown: function(layer) {
			  if(!isMove)
				  {
			  	var distX, distY;
			    distX = layer.eventX;// - layer.x;
			    distY = layer.eventY;// - layer.y;	
			    
			    var stratIndex = point2Int(distX,distY);

			    if(selectinfoid!=0)
			    	{
				    	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
				    	
				    	for(var i=0;i<infopubs.length;i++)
						{
		        			var infosn=infopubs[i].id;	
		        			var infoname=infopubs[i].Advname;
		        			var lifeAct=infopubs[i].lifeAct;
		        			var lifeDie=infopubs[i].lifeDie;
		        			var playTimelength=infopubs[i].playTimelength;
		        			var infopubid=infopubs[i].Pubidid;	
		        			if(infosn==selectinfoid)
		        				{		        				
		        				addlistitem(selectinfoid,stratIndex,playTimelength);
		        				break;
		        				}
						}			    	
			    	}
				  }
			  }
			  */
		});
	
	 for(var t=0;t<rowCount;t++)
	    {
	        var top=t*pointH +marginTop ;

	        if((t + 1)%5==0 && t!=0)
	        {	 
	        	//画列文字
				$(canvas).drawText({
					  layer: true,	
					  fillStyle: '#9cf',
					  groups: ['background'],
					  x: marginLeft - 20, y: top + pointH,
					  fontSize: '12px',
					  fontFamily: 'Arial',
					  text: t * colCount + 's',
					  fromCenter: false,
					  rotate: 270
					});			
				
				//画线			
				$(canvas).drawLine({
					  layer: true,	
					  groups: ['background'],
					  strokeStyle: strokeColor,
					  strokeWidth: strokeW*2,
					  x1: marginLeft, y1: top + pointH,
					  x2: marginLeft + colCount * pointW, y2: top + pointH,
					  fromCenter: false
					});			
	        }
	        else
	        	{	   
	        	$(canvas).drawLine({
					  layer: true,	
					  groups: ['background'],
					  strokeDash: [1],
					  strokeDashOffset: 1,
					  strokeStyle: strokeColor,
					  strokeWidth: strokeW,
					  x1: marginLeft, y1: top + pointH,
					  x2: marginLeft + colCount * pointW, y2: top + pointH,
					  fromCenter: false
					});			
	        	}	        
	        
	        if(t + 1==rowCount){
	        	var lastrowcount = listcycle%colCount;
	        	if(lastrowcount!=0)
	        		{
	        		$(canvas).drawRect({
	        			  layer: true,	  
	        			  groups: ['background'],
	        			  fillStyle: '#fff',
	        			  x: marginLeft + lastrowcount*pointW, y: top,
	        			  width: (colCount - lastrowcount) * pointW,
	        			  height: pointH,
	        			  fromCenter: false
	        			});
	        		}
	        	continue;
	        }
	    }
	 
	 
     for(var l=0;l<=colCount;l++)
     {
     	//if(l==0)
     	//	{continue;}
         if(l%5==0)
         {	
			//画列文字
			$(canvas).drawText({
				  layer: true,
				  groups: ['background'],
				  fillStyle: '#9cf',
				  x: l * pointW + 2 + marginLeft, y: marginTop - 20,
				  fontSize: '12px',
				  fontFamily: 'Arial',
				  text: l + 's',
				  fromCenter: false
				});
			//画线	
			$(canvas).drawLine({
				  layer: true,	
				  groups: ['background'],
				  strokeStyle: strokeColor,
				  strokeWidth: strokeW*2,
				  x1: l * pointW + 2 + marginLeft, y1: marginTop,
				  x2: l * pointW + 2 + marginLeft, y2: marginTop + rowCount * pointH,
				  fromCenter: false
				});										
         }
         else
         	{
         	//画线		
         	$(canvas).drawLine({
			  layer: true,	
			  groups: ['background'],
			  strokeDash: [1],
			  strokeDashOffset: 1,
			  strokeStyle: strokeColor,
			  strokeWidth: strokeW,
			  x1: l * pointW + 2 + marginLeft, y1: marginTop,
			  x2: l * pointW + 2 + marginLeft, y2: marginTop + rowCount * pointH,
			  fromCenter: false
			});        	
         	}
     }    
     
     DrawBackgroundImg($(canvas).getCanvasImage(),canvasWidth,canvasHeight);
     
//     console.warn("画背景 结束时间" + getNowFormatDatetime());
}
//
function DrawBackgroundImg(backimg,canvasWidth,canvasHeight)
{
	$('#info_canvas').drawImage({
		  layer: true,
		  name: 'background',
		  source: backimg,
		  x: 0, y: 0,				  
		  width: canvasWidth,
		  height: canvasHeight,
		  fromCenter: false,
		  mousemove: function(layer) {
			  if(!isMove)
				  {
				  	var distX, distY;
				    distX = layer.eventX;
				    distY = layer.eventY;	
				    
				    var stratIndex = point2Int(distX,distY);				    
					tt = stratIndex + "秒";
					tooltipshow(distX,distY,'开始时间:' + tt);
				  }
			  },
		  mouseout: function(layer) {
			    isMove=false;
				tooltiphide();
			  },
		  mousedown: function(layer) {
			  if(!isMove)
				  {
				  if(!ispermission){return;}
			  	var distX, distY;
			    distX = layer.eventX;// - layer.x;
			    distY = layer.eventY;// - layer.y;	
			    
			    var stratIndex = point2Int(distX,distY);

			    if(selectinfoid!=0)
			    	{
				    	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
				    	
				    	for(var i=0;i<infopubs.length;i++)
						{
		        			var infosn=infopubs[i].id;	
		        			var infoname=infopubs[i].Advname;
		        			var lifeAct=infopubs[i].lifeAct;
		        			var lifeDie=infopubs[i].lifeDie;
		        			var playTimelength=infopubs[i].playTimelength;
		        			var infopubid=infopubs[i].Pubidid;	
		        			if(infosn==selectinfoid)
		        				{		        				
		        				addlistitem(selectinfoid,stratIndex,playTimelength);
		        				break;
		        				}
						}			    	
			    	}
				  }
			  }
	});
}
//获取指定时间 内的排期数据并绘制
function GetitemList(dts,dte)
{
//	console.warn("画条目 开始时间" + getNowFormatDatetime());
	if(dts==''){dts='1999-09-09';}
	if(dte==''){dte='2100-09-09';}
	$('#infolist_draft div').css("background","#F7F7F7");
	$('#infolist_draft div').css("color","#000");
	if(itemlist!=null && itemlist!="")
	{
		var jsonpl = itemlist;
		if(jsonpl!=null && jsonpl.length>0)
		{
			for(var p=0;p<jsonpl.length;p++)
				{
					var pl=jsonpl[p];
					var infoid=pl.infoid;
					var offsetlist=pl.offsetlist;
					var timelenght=pl.timelenght;
					
					if(sessionStorage.getItem('infopubs')==null || sessionStorage.getItem('infopubs')=="")
						{return;}
					var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
			    	
			    	for(var i=0;i<infopubs.length;i++)
					{
	        			var infosn=infopubs[i].id;	
	        			var infoname=infopubs[i].Advname;
	        			var lifeAct=infopubs[i].lifeAct;
	        			var lifeDie=infopubs[i].lifeDie;
	        			if(lifeAct==''){lifeAct='1999-09-09';}
	        			if(lifeDie==''){lifeDie='2100-09-09';}
	        			var playTimelength=infopubs[i].playTimelength;
	        			var infopubid=infopubs[i].Pubidid;		        			
	    				
	        			if(infosn==infoid)
	        				{
	        				var isjion = dtJion(dts,dte,lifeAct,lifeDie);//判断日期是否有交集
	        				if(isjion)
	        					{
		        					$('#div_'+infosn).css("background","#31b0d5");
		    	    				$('#div_'+infosn).css("color","#fff");
		        					if(offsetlist!=null && offsetlist.length>0)
		    						{
		    						for(var f=0;f<offsetlist.length;f++)
		    							{
		    							Drawlistitem(infoid,offsetlist[f],timelenght);
		    							}
		    						}
	        					}
	        				break;
	        				}
					}			    						
				}
		}
	}
//	console.warn("画条目 结束时间" + getNowFormatDatetime());
}
//清理绘制图层
function clearlayeritem()
{
	// 删除所所有排期图层	
	$('#info_canvas').removeLayers(function(layer) {
		var layername = layer.name;		
		if(layername!=null && layername!="" &&layername.indexOf("offset") != -1)
		  {return true;}
	}).drawLayers();		
}
//位置转时间值
function point2Int(x,y)
{
	var rowIndex = parseInt((y - marginLeft)/pointH);
	var colIndex = parseInt((x - marginTop)/pointW);
	var stratIndex = rowIndex*colCount +  colIndex;
	
	return stratIndex;
}
//判断两个时间是否有交集
function dtJion(srcdts,srcdte,desdts,desdte)
{
	 if(srcdte<desdts||srcdts>desdte){
	 	return false;
	 }else{
		 return true;
	 }
}
 //判断两个时间是否有交集
function itemJion(srcdts,srcdte,desdts,desdte)
{
	 if(srcdte<=desdts||srcdts>=desdte){
	 	return false;
	 }else{
		 return true;
	 }
}
 //判断是否可以插入
function judgeitem(offsetvalue,timelenght)
{
	if(offsetvalue+timelenght>selectlistcycle)
		{return false;}
	if(itemlist!=null && itemlist.length>0)
		{
		var isF=true;
		var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
		for(var i=0;i<itemlist.length;i++)
			{						
			var seldts='',seldte='',dts='',dte='';
				    	
	    	for(var j=0;j<infopubs.length;j++)
			{
    			var infosn=infopubs[j].id;	
    			var infoname=infopubs[j].Advname;
    			var lifeAct=infopubs[j].lifeAct;
    			var lifeDie=infopubs[j].lifeDie;
    			if(lifeAct==''){lifeAct='1999-09-09';}
    			if(lifeDie==''){lifeDie='2100-09-09';}
    			if(itemlist[i].infoid==infosn)
    				{
	    				dts=lifeAct;
						dte=lifeDie;
    				}
    			if(selectinfoid==infosn)
    				{
    					seldts=lifeAct;
    					seldte=lifeDie;
    				}
    			if(seldts!=''&&seldte!=''&&dts!=''&&dte!='')
    				{break;}
			}
	    	var isjion = dtJion(seldts,seldte,dts,dte);//判断日期是否有交集
			if(!isjion){continue;}	    		    	
			var offsetlist = itemlist[i].offsetlist;
			if(offsetlist!=null && offsetlist.length>0)
				{
					for(var j=0;j<offsetlist.length;j++)
						{
						var st = offsetlist[j];
						var et = st + itemlist[i].timelenght;
						var isitemjion = itemJion(offsetvalue,offsetvalue + timelenght,st,et);
						if(isitemjion)						
							{
							isF=false;break;
							}
						}
				}
			if(!isF){break;}
			}
		return isF;
		}
	else
		{return true;}
}

function Hlimit(x,w) {
	if(x<marginLeft)
	{return marginLeft;}
	else if(x + pointW > colCount * pointW + marginLeft)
	{return colCount * pointW + marginLeft - pointW;}
	else{								
		if((x + marginLeft)%pointW==0)
	    {
	    	return x;
	    }
	    else {
	    	var c = parseInt(Math.round((x + marginLeft)/pointW));
	    	var rx = c * pointW + marginLeft;
	    	if(rx > colCount * pointW + marginLeft - pointW)
			{rx = colCount * pointW + marginLeft - pointW;}
	    	return rx;
		}		
	}
}

function Vlimit(y,h) {
	if(y<marginTop)
	{return marginTop;}
	else if(y + h > rowCount * pointH + marginTop)
	{return rowCount * pointH + marginTop - h;}
	else{				
		 if(y%pointH==0)
		    {
		    	return y;
		    }
		    else {				    	
		 		var c = parseInt(Math.round(y/pointH));
		 		var ry = c * pointH;
		 		if(ry > rowCount * pointH + marginTop - h)
	 			{ry = rowCount * pointH + marginTop - h}
		    	return ry;				    						    	
			}
	}
}
//条目移动
function itemDragMove(layer,stratIndex,timelenght)
{
	var layerid=layer.name.substring(0,layer.name.indexOf('part'));
	var selectsn=parseInt(layer.name.substring(layer.name.indexOf('part')+4));
	 
	//var infoid=parseInt(layerid.substring(0,layerid.indexOf('offset')));
	//var offset=parseInt(layerid.substring(layerid.indexOf('offset') + 6));
	//var offset = stratIndex - selectsn;
	
	var lyrGrp = $('#info_canvas').getLayerGroup(layerid);
	if(stratIndex - selectsn<0)
	{
		for(var i=0;i<lyrGrp.length;i++)
		  {
		  var lyrname = lyrGrp[i].name;
		  var sn = parseInt(lyrname.substring(lyrname.indexOf('part')+4));

		  var rowIndex = parseInt(sn/colCount);
	      var colIndex = sn%colCount;
	      var left= marginLeft + colIndex*pointW;
	      var top= marginTop + rowIndex*pointH;
		
	      var lineInterval=0;
	      if(sn==0)
	    	  {lineInterval = Interval;}
		  $('#info_canvas').setLayer(layerid + 'part' + sn, {				  
			  x: left + lineInterval,y:top + Interval					  
			})
			.drawLayers();  
		  }
	}
	else if(stratIndex + timelenght>=selectlistcycle){
		for(var i=0;i<lyrGrp.length;i++)
		  {
		  var lyrname = lyrGrp[i].name;
		  var sn = parseInt(lyrname.substring(lyrname.indexOf('part')+4));
		  var lyrIndex = selectlistcycle - timelenght + sn;
		  var rowIndex = parseInt(lyrIndex/colCount);
	      var colIndex = lyrIndex%colCount;
	      var left= marginLeft + colIndex*pointW;
	      var top= marginTop + rowIndex*pointH;
		
	      var lineInterval=0;
	      if(sn==0)
	    	  {lineInterval = Interval;}
		  $('#info_canvas').setLayer(layerid + 'part' + sn, {			  
			  x: left + lineInterval,y:top + Interval					  
			})
			.drawLayers();  
		  }
	}
	else {		
		for(var i=0;i<lyrGrp.length;i++)
		  {
		  var lyrname = lyrGrp[i].name;
		  var sn = parseInt(lyrname.substring(lyrname.indexOf('part')+4));
		  
		  var lyrIndex=stratIndex + sn - selectsn;
		  var rowIndex = parseInt(lyrIndex/colCount);
	      var colIndex = lyrIndex%colCount;
	      var left= marginLeft + colIndex*pointW;
	      var top= marginTop + rowIndex*pointH;
		
	      var lineInterval=0;
	      if(sn==0)
	    	  {lineInterval = Interval;}
		  $('#info_canvas').setLayer(layerid + 'part' + sn, {					  
			  x: left + lineInterval,y:top + Interval
			})
			.drawLayers();
		  }	
	}
	
	var val = stratIndex - selectsn;
	if(val<0){val=0;}
	tooltipshow(layer.x,layer.y,'开始时间:' + val);
}
//移动停止
function itemDragStop(layer,stratIndex,timelenght)
{
	var layerid=layer.name.substring(0,layer.name.indexOf('part'));
	var selectsn=parseInt(layer.name.substring(layer.name.indexOf('part')+4));
	
	var lyrIndex=stratIndex - selectsn;
	
	var infoid=parseInt(layerid.substring(0,layerid.indexOf('offset')));
	
	var sindex=lyrIndex;
	if(!judgeitem(lyrIndex,timelenght))
		{
		sindex=MovestratIndex;
		alertMessage(1, "警告", "移动位置不能容纳本条广告,回到原位置"+sindex+"秒!");
		}		
    
	adddatalistitem(infoid,sindex,timelenght);
	    
	var lyrGrp = $('#info_canvas').getLayerGroup(layerid);
	var ll = lyrGrp.length;
	for(var i=ll - 1;i >= 0;i--)
	  {
		var lyrname = lyrGrp[i].name;
		var sn = parseInt(lyrname.substring(lyrname.indexOf('part')+4));
		
		var lindex = sindex + sn;
		
		var rowIndex = parseInt(lindex/colCount);
	    var colIndex = lindex%colCount;
	    var left= marginLeft + colIndex*pointW;
	    var top= marginTop + rowIndex*pointH;
	    var lineInterval=0;
	    if(sn==0)
    	  {lineInterval = Interval;}
	      
		$('#info_canvas').setLayer(layerid + 'part' +sn, {			  
		  name:infoid +'offset'+ sindex + 'part' + sn,
		  groups: [infoid +'offset'+ sindex],
		  x: left + lineInterval,y:top + Interval
		})
		.drawLayers();		  
	  }	
	
	itemChange();
	MovestratIndex=-1;		
}
//画排期条目
function Drawlistitem(infoid,offsetvalue,timelenght)
{		
	var isDrag = false;
	var itemColor = itembackColor;
	if(infoid!=selectinfoid)
	{
		var rowIndex = parseInt(offsetvalue/colCount);
		var colIndex = offsetvalue%colCount;
			
		var layerid=infoid+"offset"+offsetvalue;
		
		var left= marginLeft + colIndex*pointW;
		var top= marginTop + rowIndex*pointH;	
		lineInterval = Interval;
		var itemRowCount = Math.ceil((colIndex + timelenght)/colCount);
		if(itemRowCount<=1)
			{
			$('#info_canvas').drawRect({
				  layer: true,
				  name:layerid + 'part0',
				  groups: [layerid],
				  mouseover: function(layer) {
					  itemmouseover(layer);
				  },	
				  mouseout: function(layer) {
					  itemmouseout(layer);
				  },
				  click: function(layer) {
					  itemclick(layer);
				  },
				  fillStyle: itemColor,
				  x: left + lineInterval, y: top + Interval,
				  width: timelenght*pointW - lineInterval,
				  height: pointH - Interval*2,
				  fromCenter: false
				});
			}
		else
			{
				for(var i=0;i<itemRowCount;i++)
					{
						if(i==0)
							{
							$('#info_canvas').drawRect({
								  layer: true,
								  name:layerid + 'part' + i,
								  groups: [layerid],
								  mouseover: function(layer) {
									  itemmouseover(layer);
								  },	
								  mouseout: function(layer) {
									  itemmouseout(layer);
								  },
								  click: function(layer) {
									  itemclick(layer);
								  },
								  fillStyle: itemColor,
								  x: left + lineInterval, y: top + Interval,
								  width: (colCount -  colIndex) * pointW - lineInterval,
								  height: pointH - Interval*2,
								  fromCenter: false
								});
							}
						else
							{
							if(i!=itemRowCount - 1)//非尾行
								{
								$('#info_canvas').drawRect({
									  layer: true,
									  name:layerid + 'part' + i,
									  groups: [layerid],
									  mouseover: function(layer) {
										  itemmouseover(layer);
									  },	
									  mouseout: function(layer) {
										  itemmouseout(layer);
									  },
									  click: function(layer) {
										  itemclick(layer);
									  },
									  fillStyle: itemColor,
									  x: marginLeft + lineInterval, y: top + i * pointH - Interval,
									  width: colCount * pointW - lineInterval,
									  height: pointH - Interval*2,
									  fromCenter: false
									});
								}
							else//最后一行
								{
								$('#info_canvas').drawRect({
									  layer: true,
									  name:layerid + 'part' + i,
									  groups: [layerid],
									  mouseover: function(layer) {
										  itemmouseover(layer);
									  },	
									  mouseout: function(layer) {
										  itemmouseout(layer);
									  },
									  click: function(layer) {
										  itemclick(layer);
									  },
									  fillStyle: itemColor,
									  x: marginLeft + lineInterval, y: top + i * pointH + Interval,
									  width: (timelenght - (colCount - colIndex)) % colCount * pointW - lineInterval,
									  height: pointH - Interval*2,
									  fromCenter: false
									});
								}
							}
					}
			}

	}	
	else {		
	itemColor = itemSelectbackColor;isDrag=true;
	
	if(!ispermission){isDrag=false;}
	var layerid=infoid+"offset"+offsetvalue;		
	
	for(var i=0;i<timelenght;i++)
		{
		var lineInterval=0;
		if(i == 0){lineInterval = Interval;}
		var startIndex=offsetvalue + i;
		var rowIndex = parseInt(startIndex/colCount);
		var colIndex = startIndex%colCount;
		var left= marginLeft + colIndex*pointW;
		var top= marginTop + rowIndex*pointH;
		$('#info_canvas').drawRect({
			  layer: true,
			  name:layerid + 'part' + i,
			  groups: [layerid],
			  draggable: isDrag,
			  cursors: {
			    mouseover: 'pointer',
			    mousedown: 'move',
			    mouseup: 'pointer'
			  },
			  updateDragX: function (layer, x) {
				
			    return Hlimit(x,pointW);
			  },
			  updateDragY: function (layer, y) {
			    return Vlimit(y,pointH);
			  },
			  dragstart: function(layer) {
				  isMove=true;	
				  tooltiphide();				  
				  
				  var layerid=layer.name.substring(0,layer.name.indexOf('part'));					
				  var offset=parseInt(layerid.substring(layerid.indexOf('offset') + 6));
					
				  if(itemlist!=null && itemlist.length>0)
					{
						for(var l=0;l<itemlist.length;l++)
						{
							var item = itemlist[l];
										
							if(item.infoid==infoid)
							{
								var index = item.offsetlist.indexOf(offset);
								if(index!=-1)
								{
									item.offsetlist.splice(index, 1);					
									if(item.offsetlist.length <= 0)
										{
										itemlist.splice(l, 1)
										}					
									itemChange();
								}				
								break;				
							}			
						}
					}				  				  	
				  
				  MovestratIndex = offset;
			  },
			  drag: function(layer) {				  
				  var stratIndex = point2Int(layer.x,layer.y);
				  //var endIndex = stratIndex + timelenght;
				  //console.warn("开始:"+stratIndex+"秒,结束:"+ endIndex +"秒");
				  itemDragMove(layer,stratIndex,timelenght);
				  
			  },
			  dragstop: function(layer) {				  
				  isMove=false;	
				  var stratIndex = point2Int(layer.x,layer.y);
				  itemDragStop(layer,stratIndex,timelenght);
			  },
			  mouseover: function(layer) {
				  if(!isMove)
				  {itemmouseover(layer);}				  
			  },	
			  mousemove: function(layer) {
				  if(!isMove)
				  {itemmouseover(layer);}
			  },	
			  mouseout: function(layer) {
				  isMove=false;
				  if(!isMove)
				  {itemmouseout(layer);}
			  },
			  dblclick: function(layer) {
				  itemclick(layer);
			  },
			  //strokeStyle: linestrokeStyle,
			  //strokeWidth: lineWidth,
			  fillStyle: itemColor,
			  x: left + lineInterval, y: top + Interval,
			  width: pointW- lineInterval,
			  height: pointH - Interval*2,			  
			  fromCenter: false
			});
		}
	}
}

//添加数据到排期集合中
function adddatalistitem(infoid,offsetvalue,timelenght)
{	
	var lifeAct="";
	var lifeDie="";
	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
	for(var i=0;i<infopubs.length;i++)
	{
		var infosn=infopubs[i].id;
		if(infosn==infoid)
		{
		lifeAct=infopubs[i].lifeAct;
		lifeDie=infopubs[i].lifeDie;
		break;
		}
	}
	if(itemlist!=null && itemlist.length>0)
		{
		var isf=false;
		for(var i=0;i<itemlist.length;i++)
		{
			var item = itemlist[i]
			if(item.infoid==infoid)
				{
				item.timelenght=timelenght;
				item.offsetlist.push(offsetvalue);				
				isf=true;
				break
				}
		} 
		if(!isf)
			{
				var offsetlist=[];
				offsetlist.push(offsetvalue);
				var item={
					infoid:infoid,	
					lifeAct:lifeAct,
					lifeDie:lifeDie,
					timelenght:timelenght,
					offsetlist:offsetlist
				};				
				itemlist.push(item);			
			}
		}
	else
		{
		var offsetlist=[];
		offsetlist.push(offsetvalue);
		var item={
			infoid:infoid,	
			lifeAct:lifeAct,
			lifeDie:lifeDie,
			timelenght:timelenght,
			offsetlist:offsetlist
		};
						
		itemlist.push(item);
		}		
}
//添加条目
function addlistitem(infoid,offsetvalue,timelenght)
{
	var isst = judgeitem(offsetvalue,timelenght);
	if(isst){
		Drawlistitem(infoid,offsetvalue,timelenght);
		adddatalistitem(infoid,offsetvalue,timelenght);
		
		itemChange();
		return true;
	}	
	else {
		return false;
	}
}
//添加条目
function mutiladdlistitem(startindex,endindex,interval,infoid,timelenght)
{	
	var arrayitem=[];
	for(var i=startindex;i<=endindex-timelenght;i=i+interval)
	{
		var offsetvalue = i;
		var isst = judgeitem(offsetvalue,timelenght);
		if(isst){
			Drawlistitem(infoid,offsetvalue,timelenght);
			adddatalistitem(infoid,offsetvalue,timelenght);				
			arrayitem.push(offsetvalue);
		}	
		else {
			for(var j=i;j<=i+interval-timelenght;j++)
				{
					var isst = judgeitem(j,timelenght);
					if(isst){
						Drawlistitem(infoid,j,timelenght);
						adddatalistitem(infoid,j,timelenght);						
						arrayitem.push(j);
						break;
					}
				}
		}
	}
	if(arrayitem.length>0)
	{itemChange();}
	return arrayitem;
}
//添加条目
function getmutiladdlistitem(startindex,endindex,interval,infoid,timelenght)
{	
	var arrayitem=[];
	for(var i=startindex;i<=endindex-timelenght;i=i+interval)
	{
		var offsetvalue = i;
		var isst = judgeitem(offsetvalue,timelenght);
		if(isst){						
			arrayitem.push(offsetvalue);
		}	
		else {
			for(var j=i;j<=i+interval-timelenght;j++)
				{
					var isst = judgeitem(j,timelenght);
					if(isst){												
						arrayitem.push(j);
						break;
					}
				}
		}
	}
	if(arrayitem.length>0)
	{itemChange();}
	return arrayitem;
}
//鼠标进入
function itemmouseover(layer)
{
	var groupname =layer.groups.toString();//261offset63
	var infoid=groupname.substring(0,groupname.indexOf("offset"));
	var offset=groupname.substring(groupname.indexOf("offset") + 6,groupname.lenght);
	
	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
	
	for(var i=0;i<infopubs.length;i++)
	{
		var infosn=infopubs[i].id;	
		var infoname=infopubs[i].Advname;
		var lifeAct=infopubs[i].lifeAct;
		var lifeDie=infopubs[i].lifeDie;
		if(lifeAct==''){lifeAct='1999-09-09';}
		if(lifeDie==''){lifeDie='2100-09-09';}
		var playTimelength=infopubs[i].playTimelength;
		var infopubid=infopubs[i].Pubidid;	
		if(infosn==infoid)
			{	
			
			//$('#infolist_draft div').css("background","#F7F7F7");
			//$('#divinfo_'+ infoid).css("background","#007BFF");			
			
			var hh = parseInt(offset / 3600);
			var mm = parseInt(offset % 3600 / 60);
			var ss = parseInt(offset % 3600 % 60);
			
			//var tt = hh + "小时" + mm + "分" + ss + "秒";
			var tt = offset + "秒";

			var distX = layer.eventX;
			var distY = layer.eventY;
			    
			tooltipshow(distX,distY,"广告名称:" + infoname +"<br/>开始时间:"+ tt + "<br/>播放时长:" + playTimelength + "秒")
			var layerid=layer.groups[0];
			
			
			$('#info_canvas').setLayerGroup(layerid, {				  
				  fillStyle: '#0000ff'
				})
				.drawLayers();
			}
	}
}
//鼠标移出
function itemmouseout(layer)
{			
	var layerid=layer.groups[0];
	
	var infoid=layerid.substring(0,layerid.indexOf("offset"));
	
	var itemColor = itembackColor;
	if(infoid==selectinfoid)
	{itemColor = itemSelectbackColor;}
	
	$('#info_canvas').setLayerGroup(layerid, {		  
		  //strokeStyle: linestrokeStyle,
		  //strokeWidth: lineWidth
		  fillStyle: itemColor
		})
		.drawLayers();	
}
//单击
function itemclick(layer)
{
	if(!ispermission){return;}
	var layerid=layer.groups[0];
	var infoid=parseInt(layerid.substring(0,layerid.indexOf("offset")));
	if(infoid==selectinfoid)
		{
		$("#modal_deleteitem .modal-body").text("是否删除此排期?");
		$("#modal_deleteitem").attr("data-type","delete_modelInfo");
		$("#modal_deleteitem").attr("data-layerid",layerid);
		$('#modal_deleteitem').modal('show');
		}
}