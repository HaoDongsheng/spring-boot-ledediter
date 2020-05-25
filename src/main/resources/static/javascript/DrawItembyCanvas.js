var intiScale = 10;
var itemscale=5;
var moveInterval = 8;
$(function(){
	//getItemscale();
});

function getItemscale()
{
	var scalew = parseInt(($("#workarea").width() - 40)/screenw);    
    var scaleh = parseInt(($("#workarea").height() - 40)/screenh);    
    itemscale=scalew>scaleh?scaleh:scalew;
}

function escape2Html(str) {
 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
 str = str.replace(/&#39;/g, "'");
 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
}
/**
 画图函数
 画item
 */
function ParseNode(node) {
	var arrNode = new Array()
	for(var i=0;i<node.children.length;i++)
	 {
	 		var nodeType = node.children[i].tagName;
	 		switch(nodeType)
	 		{
	 			case "span":{	 				
 					arrNode = arrNode.concat(ParseNode(node.children[i]));
	 			};break;
	 			case "img":{
	 				var width=0,height=0;
	 				var strmapstyle = escape2Html(node.children[i].attrs['data-mapstyle']);
	 				var stralt = "";
	 				if(node.children[i].attrs['alt']!=null && node.children[i].attrs['alt']!='')
	 				{stralt = escape2Html(node.children[i].attrs['alt']);}
	 				var mapstyle = JSON.parse(strmapstyle);
	 				
	 				var gifFramesCount = mapstyle.gifFramesCount;
	 				var giftimelength = mapstyle.giftimelength;
	 				
	 				if(node.children[i].attrs['width']!=undefined)
 					{width= parseInt(node.children[i].attrs['width']);}
	 				else{width=mapstyle.imgwidth;}
	 				
	 				if(node.children[i].attrs['height']!=undefined)
 					{height= parseInt(node.children[i].attrs['height']);}
	 				else{height=mapstyle.imgheight;}
	 				
	 				var imgtype=mapstyle.imgtype;
	 				
	 				var data={	 						
	 						itemType:imgtype,
	 						width:width,
	 						height:height,
	 						gifFramesCount:gifFramesCount,
	 						giftimelength:giftimelength,
	 						alt:stralt,
	 						value:node.children[i].attrs['src']					
	 				};
	 				arrNode.push(data);
	 			};break;
	 			case "video":{
	 				var width=0,height=0;
	 				if(node.children[i].attrs['width']!=undefined)
 					{width= parseInt(node.children[i].attrs['width']);}
	 				if(node.children[i].attrs['height']!=undefined)
 					{height= parseInt(node.children[i].attrs['height']);}
	 				var data={	 						
	 						itemType:4,
	 						width:width,
	 						height:height,
	 						value:node.children[i].attrs['src']					
	 				};
	 				arrNode.push(data);
	 			};break;
	 			default:{
	 				var fontName='SimSun';fontSize=16;forecolor='#ffff00';backcolor='#000000';
	 				if(node.tagName!='p')
	 					{	 					
		 				var style = escape2Html(node.children[i].parentNode.attrs['style']);
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
	 								 			case 'font-family':{
	 								 				var arrfn=attrArr[1].split(",");
	 								 				if(arrfn.length>0)
	 								 					{fontName=arrfn[0].trim();}
	 								 				else
	 								 					{fontName='SimSun';}	 								 				
	 								 			};break;
	 	                                        case 'font-size':{
	 	                                        	if(attrArr[1]!=null && attrArr[1]!="")
	 	                                        	{fontSize=parseInt(attrArr[1].replace("px", ""));}
	 	                                        	else{fontSize=16;}
	 	                                        };break;
	 	                                        case 'color':{
	 	                                        	if(attrArr[1]!=null && attrArr[1]!="")
	 	                                        	{forecolor=colorRGB2Hex(attrArr[1]);}
	 	                                        	else{forecolor='#ffff00';}
	 	                                        };break;
	 	                                        case 'background-color':{
	 	                                        	if(attrArr[1]!=null && attrArr[1]!="")
	 	                                        	{backcolor=colorRGB2Hex(attrArr[1]);}
	 	                                        	else{backcolor='#000000';}
	 	                                        	};break; 	                                       
	 								 			}
	 								 		}
	 								}
	 						}
	 					}
	 				var data={
	 						fontName:fontName,
	 						fontSize:fontSize,
	 						foreColor:forecolor,
	 						backColor:backcolor,
	 						itemType:0,
	 						value:escape2Html(node.children[i].data)	 						
	 				};
	 				arrNode.push(data);
					
	 			};break;
	 		}
	 }
	return arrNode;
}

var tinyarrNode= new Array()
//获取JSON数据
function tinymceParseNode(node,arrNode) {
	var firstChild = node.firstChild;
	var name = node.name;
	var attributes = node.attributes;
	var next = node.next;	
	switch(name)
	{
		case "span":{
			attributes
		};break;
		case "p":{
			arrNode= new Array();
			tinyarrNode.push(arrNode);
		};break;
		case "#text":{
			var styleJson={
				fontName:'',
				fontSize:0,
				foreColor:'',
				backColor:''
			};
			styleJson = tinymceGetStyle(node,styleJson)
			var fontName=styleJson.fontName;
			var fontSize=styleJson.fontSize;
			var foreColor=styleJson.foreColor;
			var backColor=styleJson.backColor;
			if(fontName=='')
			{fontName='SimSun';}
			if(fontSize==0)
			{fontSize=16;}
			if(foreColor=='')
			{foreColor='#ffff00';}
			if(backColor=='')
			{backColor='#000000';}
			
			var txtstring = node.value;
			var tmp = ""; 
		    for(var j=0;j<txtstring.length;j++){ 
		        if(txtstring.charCodeAt(j)==160){ 
		            tmp= tmp+ " "; 
		        }
		        else {
		        	tmp= tmp+ txtstring[j]; 
				}										         
		    } 
			
			var data={	
				fontName:fontName,
				fontSize:fontSize,
				foreColor:foreColor,
				backColor:backColor,
				itemType:0,
				value:tmp	 						
			};
			arrNode.push(data);
		};break;
		case "img":{
			var src="",mapstyle="";
			var width=0,height=0;
			for(var i=0;i<attributes.length;i++)
				{
					switch (attributes[i].name) {
					case "src":
					{src = attributes[i].value;};
					break;
					case "data-mapstyle":
					{mapstyle = attributes[i].value;};
					break;
					case "width":
					{width = attributes[i].value;};
					break;
					case "height":
					{height = attributes[i].value;};					
					break;				
					}
				}
			
			
			var strmapstyle = escape2Html(mapstyle);

			var mapstyle = JSON.parse(strmapstyle);
			
			var gifFramesCount = mapstyle.gifFramesCount;
			var giftimelength = mapstyle.giftimelength;
			
			if(width==0)
			{width=mapstyle.imgwidth;}			
			
			if(height==0)
			{height=mapstyle.imgheight;}			
			
			var imgtype=mapstyle.imgtype;
			
			var data={	 						
					itemType:imgtype,
					width:width,
					height:height,
					gifFramesCount:gifFramesCount,
					giftimelength:giftimelength,					
					value:src				
			};
			arrNode.push(data);
		};break;
	}
	
	if(firstChild!=null)
		{
			arrNode = tinymceParseNode(firstChild,arrNode);
		}
	if(next!=null)
		{
			arrNode = tinymceParseNode(next,arrNode);
		}
	
	return arrNode;
}
//获取文字样式
function tinymceGetStyle(node,styleJson) {
	var parent = node.parent;
	var name = node.name;
	var attributes = node.attributes;	
	switch(name)
	{
		case "span":{			
			var style = escape2Html(attributes.map.style);
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
						 			case 'font-family':{
						 				var fontName='SimSun';
						 				var arrfn=attrArr[1].split(",");
						 				if(arrfn.length>0)
						 					{
						 					fontName=arrfn[0].trim();
						 					fontName = fontName.replace(/'/g,'');
						 					}
						 				if(styleJson.fontName=='')
						 				{styleJson.fontName=fontName;}		
						 			};break;
                                 case 'font-size':{
                                	var fontSize=16;
                                 	if(attrArr[1]!=null && attrArr[1]!="")
                                 	{fontSize=parseInt(attrArr[1].replace("px", ""));} 
                                 	if(styleJson.fontSize==0)
                                 	{styleJson.fontSize=fontSize;}
                                 };break;
                                 case 'color':{
                                	var forecolor='#ffff00';
                                 	if(attrArr[1]!=null && attrArr[1]!="")
                                 	{forecolor=attrArr[1].trim();}   
                                 	if(forecolor.indexOf('rgb')>-1)
                                 		{
                                 			forecolor=colorRGB2Hex(forecolor);
                                 		}
                                 	if(styleJson.foreColor=='')
                                 	{styleJson.foreColor=forecolor;}
                                 };break;
                                 case 'background-color':{
                                	var backcolor='#000000';
                                 	if(attrArr[1]!=null && attrArr[1]!="")
                                 	{backcolor=attrArr[1].trim();}   
                                 	if(backcolor.indexOf('rgb')>-1)
                             		{
                                 		backcolor=colorRGB2Hex(backcolor);
                             		}
                                 	if(styleJson.backColor=='')
                                 	{styleJson.backColor=backcolor;}
                                 	};break; 	                                       
						 			}
						 		}
						}
				}
		};break;
		case "p":{
			return styleJson;
		};break;
	}
	
	if(parent!=null)
		{
		styleJson = tinymceGetStyle(parent,styleJson);
		}
	
	return styleJson;
}
//itemlist转src集合
function item2Src(item)
{		
	getItemscale();
	var canvas = document.createElement("canvas");							
    canvas.width = item.width * intiScale;
    canvas.height = item.height * intiScale;
    
    if(item.backcolor!=null && item.backcolor!="")
   	{
	    $(canvas).drawRect({
	    	  layer: true,
	    	  fillStyle: item.backcolor,
	    	  x: 0, y: 0,	    	  
	    	  width: canvas.width,
	    	  height: canvas.height,
	    	  fromCenter: false
	    	});
   	}
    else {
    	$(canvas).drawRect({
	    	  layer: true,
	    	  fillStyle: '#000000',
	    	  x: 0, y: 0,	    	  
	    	  width: canvas.width,
	    	  height: canvas.height,
	    	  fromCenter: false
	    	});
	}
    
    var special = item.itemstyle.special;
    var sketch=null,shadow=null,gradient=null,txtscale=null;
    if(special!=null)
    	{    		    		
    		if(special.sketch!=null)
			{sketch = special.sketch;}
    		if(special.shadow!=null)
    		{shadow = special.shadow;}
    		if(special.gradient!=null)
    		{gradient = special.gradient;}
    		if(special.scale!=null)
    		{txtscale = special.scale;}
    	}
    
	var contextJson = JSON.parse(item.contextJson);
	var top=0;
	for(var r=0;r<contextJson.length;r++)
	{											
		var rownode=contextJson[r];
		var arrRow = new Array()
		var left=0,itemH=0;
		for(var i=0;i<rownode.length;i++)
			{
				var itemnode=rownode[i]; 	
				if(itemnode.value==null && itemnode.value=="")
					{continue;} 							
 				switch(itemnode.itemType)
 				{
	 				case 0:{
	 					//var ii = itemnode.value.indexOf(" ");
	 					//var jj = itemnode.value.indexOf("");
	 					var scaleX = 1,scaleY = 1;
	 					if(txtscale!=null)
	 						{
	 						scaleX = parseFloat(txtscale.scaleX)/100;
	 						scaleY = parseFloat(txtscale.scaleY)/100;
	 						}
	 					
	 					var linespace=0;
	 					if(itemnode.fontSize * scaleY < item.height)
	 						{
	 						linespace = parseInt((item.height - itemnode.fontSize)/2) * intiScale;
	 						}
	 				    
	 					$(canvas).drawText({	
	 						  layer: true,
	 						  name: 'myText'+ r + i,
							  fillStyle: itemnode.foreColor,
							  x: left, y: top + linespace,
							  fontSize: itemnode.fontSize * intiScale + 'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  fromCenter: false,
							});

	 					var mytextw=$(canvas).measureText('myText'+ r + i).width * scaleX;
	 					var mytexth=item.height * intiScale;//$(canvas).measureText('myText').height;
	 					$(canvas).removeLayer('myText'+ r + i);
	 					$(canvas).drawRect({
	 				    	  layer: true,
	 				    	  fillStyle: itemnode.backColor,
	 				    	  x: left, y: top,	    	  
	 				    	  width: mytextw,
	 				    	  height: mytexth,
	 				    	  fromCenter: false
	 				    	}).drawLayers();
	 					$(canvas).removeLayer('myText'+ r + i);
	 					/*
	 					$(canvas).drawText({	
	 						  layer: true,
	 						  name: 'myText' + i,
							  fillStyle: itemnode.foreColor,
							  x: left, y: top + linespace,
							  fontSize: itemnode.fontSize * intiScale + 'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  fromCenter: false,
							}).drawLayers();
	 					*/
	 					///*
	 					$(canvas).drawText({	
	 						  layer: true,
	 						  name: 'myText'+ r + i,
							  fillStyle: itemnode.foreColor,
							  x: left + mytextw/2, y: top + mytexth/2,
							  fontSize: itemnode.fontSize * intiScale + 'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  scaleX: scaleX, scaleY: scaleY,
							  fromCenter: true
							}).drawLayers();
	 					//*/
	 					//描边	 					
	 					 if(sketch!=null)
	 					 {			 
	 						$(canvas).setLayer('myText'+ r + i, {
	 						 strokeStyle: sketch.strokeStyle,
	 						 strokeWidth: sketch.strokeWidth * intiScale
	 						})
	 						.drawLayers();
	 					 }
	 					 else
	 					 {
	 					 	$(canvas).setLayer('myText'+ r + i, {	 						 
	 						 strokeWidth: 0
	 						})
	 						.drawLayers();
	 					 }
	 					 //阴影
	 					if(shadow!=null)
	 					 {			 
	 						$(canvas).setLayer('myText'+ r + i, {
	 				 		  shadowColor: shadow.shadowColor,
	 						  shadowBlur: shadow.shadowBlur * intiScale,
	 						  shadowX: shadow.shadowBlur * intiScale, shadowY: shadow.shadowBlur * intiScale
	 						})
	 						.drawLayers();
	 					 }
	 					else
	 					 {
	 						$(canvas).setLayer('myText'+ r + i, {	 				 		  
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
	 								  x1: left, y1: top,
	 								  x2: left + mytextw, y2: top,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	case 'vertical':{
	 					 		linear = $('canvas').createGradient({
	 								  x1: left, y1: top,
	 								  x2: left, y2: top + mytexth,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	case 'oblique':{
	 					 		linear = $('canvas').createGradient({
	 					 			  x1: left, y1: top,
	 					 			  x2: left + mytextw, y2: intiScale + mytexth,
	 								  c1: gradient.gradientcolor1,
	 								  c2: gradient.gradientcolor2
	 								});
	 					 	};break;
	 					 	}
	 			
	 					 
	 					 	$(canvas).setLayer('myText'+ r + i, {
	 				 		 fillStyle: linear
	 						})
	 						.drawLayers();			    
	 				 	}
	 				 	else
	 					 {
	 				 		$(canvas).setLayer('myText'+ r + i, {
	 				 		 fillStyle: itemnode.foreColor
	 						})
	 						.drawLayers();		
	 					 }
	 					//item.contextJson[r][i].imgval=$(canvas).getCanvasImage('png');
	 					//rownode[i].imgval=$(canvas).getCanvasImage('png');
	 					left += mytextw;
	 					if(itemH<mytexth){itemH=mytexth;}
	 					
	 				};break;
	 				case 2:
	 				case 3:{	 					
	 					var w = itemnode.width * intiScale;
	 					var h = itemnode.height * intiScale;
	 					if(item.type == 4)
 						{
	 						w = item.width * intiScale;
	 						h = item.height * intiScale;
 						}
	 					
	 					var linespace=0;
	 					if(h < item.height * intiScale)
 						{
	 						linespace = parseInt((item.height*intiScale -h )/2);
 						}

	 					
	 					$(canvas).drawImage({	
	 						layer: true,
	 						  name: 'myPic' + i,
							  source: itemnode.value,						  
							  x: left, y: top + linespace,
							  width: w,
							  height: h,
							  fromCenter: false
							});
	 					if(itemH < h){ itemH = h;}
	 					left += w;
	 				};break;
 				} 				
			}
		top+=itemH;
	}
	//item.contextJson = JSON.stringify(contextJson);	
	return $(canvas);
}
//补0
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function rectangleCol(x1,y1,w1,h1,x2,y2,w2,h2){
	var maxX,maxY,minX,minY;

	/*
	maxX = x1+w1 >= x2+w2 ? x1+w1 : x2+w2;
	maxY = y1+h1 >= y2+h2 ? y1+h1 : y2+h2;
	minX = x1 <= x2 ? x1 : x2;
	minY = y1 <= y2 ? y1 : y2;
	*/
	maxX = x1 >= x2 ? x1 : x2;
	maxY = y1 >= y2 ? y1 : y2;
	minX = x1 + w1 <= x2 + w2 ? x1 + w1 : x2 + w2;
	minY = y1 + h1 <= y2 + h2 ? y1 + h1 : y2 + h2;
	
	//if(maxX - minX < w1+w2 && maxY - minY < h1+h2){
	if(maxX < minX && maxY < minY){
	  return true;
	}else{
	  return false;
	}
}

function Hlimit(x,w) {
	
	var sellayer = $('#workarea_canvas').getLayer('item'+selectitemid);
	var layers = $('#workarea_canvas').getLayers();
	for(var i=0;i<layers.length;i++)
		{
			var name = layers[i].name;
			if(name.indexOf("item")>=0)
				{
					var itemid = parseInt(name.substring(name.indexOf("item") + 4));
					if(selectitemid != itemid)
						{
						var isc = rectangleCol(layers[i].x,layers[i].y,layers[i].width,layers[i].height,x,sellayer.y,sellayer.width,sellayer.height);
						if(isc)
							{return sellayer.x;}
						}
				}
		}
	
	if(x<0)
	{return 0;}
	else if(x + w > screenw * itemscale)
	{return screenw * itemscale - w;}
	else{				
		if(x%(itemscale*moveInterval)==0)
	    {
	    	return x;
	    }
	    else {
	    	var c = parseInt(Math.round(x/(itemscale*moveInterval)));
	    	var rx = c * (itemscale*moveInterval);
	    	if(rx > screenw * itemscale)
			{rx = screenw * itemscale}
	    	return rx;
		}
	}
}

function Vlimit(y,h) {
	var sellayer = $('#workarea_canvas').getLayer('item'+selectitemid);
	var layers = $('#workarea_canvas').getLayers();
	for(var i=0;i<layers.length;i++)
		{
			var name = layers[i].name;
			if(name.indexOf("item")>=0)
				{
					var itemid = parseInt(name.substring(name.indexOf("item") + 4));
					if(selectitemid != itemid)
						{
						var isc = rectangleCol(layers[i].x,layers[i].y,layers[i].width,layers[i].height,sellayer.x,y,sellayer.width,sellayer.height);
						if(isc)
							{return sellayer.y;}
						}
				}
		}
	
	if(y<0)
	{return 0;}
	else if(y + h > screenh * itemscale)
	{return screenh * itemscale - h;}
	else{				
		 if(y%(itemscale*moveInterval)==0)
		    {
		    	return y;
		    }
		    else {				    	
		 		var c = parseInt(Math.round(y/(itemscale*moveInterval)));
		 		var ry = c * (itemscale*moveInterval);
		 		if(ry > screenh * itemscale)
		 			{ry = screenh * itemscale}
			    	return ry;				    						    	
			}
	}
}

function pointHlimit(layer, x) {
	var layerid="#workarea_canvas";
	var rx,ry,rw,rh;
	switch(layer.name)
	{
		case 'controlpoint1':{	
			rx=x;
			ry=layer.y;
		    rw = $(layerid).getLayer('controlpoint5').x - x;
		    rh = $(layerid).getLayer('controlpoint5').y - layer.y;		    		
		};break;
		case 'controlpoint2':{				
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=layer.y;
			rw = $(layerid).getLayer('controlpoint5').x - rx;
			rh = $(layerid).getLayer('controlpoint5').y - layer.y;
		};break;
		case 'controlpoint3':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=layer.y;
			rw = x - rx
			rh = $(layerid).getLayer('controlpoint7').y - layer.y;
		};break;
		case 'controlpoint4':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = x - rx;
			rh = $(layerid).getLayer('controlpoint7').y - $(layerid).getLayer('controlpoint1').y;
		};break;
		case 'controlpoint5':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = x - rx;
			rh = layer.y - $(layerid).getLayer('controlpoint1').y;
		};break;
		case 'controlpoint6':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - rx;
			rh = layer.y - ry;
		};break;
		case 'controlpoint7':{
			rx=x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - x;
			rh = layer.y - $(layerid).getLayer('controlpoint3').y;
		};break;
		case 'controlpoint8':{
			rx=x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - x;
			rh = $(layerid).getLayer('controlpoint7').y - $(layerid).getLayer('controlpoint1').y;
		};break;
	}
	
	if(rw <= 16 * itemscale)
		{
		return layer.x;
		}
	
	var layers = $('#workarea_canvas').getLayers();
	for(var i=0;i<layers.length;i++)
		{
			var name = layers[i].name;
			if(name.indexOf("item")>=0)
				{
					var itemid = parseInt(name.substring(name.indexOf("item") + 4));
					if(selectitemid != itemid)
						{
						var isc = rectangleCol(layers[i].x,layers[i].y,layers[i].width,layers[i].height,rx,ry,rw,rh);
						if(isc)
							{return layer.x;}
						}
				}
		}
	
	if(x%(itemscale*moveInterval)==0)
    {
    	return x;
    }
    else {
    	var c = parseInt(Math.round(x/(itemscale*moveInterval)));
    	var rx = c * (itemscale*moveInterval);
    	if(rx >= screenw * itemscale)
		{rx = screenw * itemscale}
    	return rx;
	}
}

function pointVlimit(layer,y) { 
	var layerid="#workarea_canvas";
	var rx,ry,rw,rh;
	switch(layer.name)
	{
		case 'controlpoint1':{	
			rx=layer.x;
			ry=y;
		    rw = $(layerid).getLayer('controlpoint5').x - layer.x;
		    rh = $(layerid).getLayer('controlpoint5').y - y;		    		
		};break;
		case 'controlpoint2':{				
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=y;
			rw = $(layerid).getLayer('controlpoint5').x - rx;
			rh = $(layerid).getLayer('controlpoint5').y - y;
		};break;
		case 'controlpoint3':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=y;
			rw = layer.x - $(layerid).getLayer('controlpoint7').x;
			rh = $(layerid).getLayer('controlpoint7').y - y;
		};break;
		case 'controlpoint4':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = layer.x - $(layerid).getLayer('controlpoint8').x;
			rh = $(layerid).getLayer('controlpoint7').y - $(layerid).getLayer('controlpoint1').y;
		};break;
		case 'controlpoint5':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = layer.x - rx;
			rh = y - ry;
		};break;
		case 'controlpoint6':{
			rx=$(layerid).getLayer('controlpoint1').x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - rx;
			rh = y - ry;
		};break;
		case 'controlpoint7':{
			rx=layer.x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - layer.x;
			rh = y - ry;
		};break;
		case 'controlpoint8':{
			rx=layer.x;
			ry=$(layerid).getLayer('controlpoint1').y;
			rw = $(layerid).getLayer('controlpoint3').x - layer.x;
			rh = $(layerid).getLayer('controlpoint7').y - ry;
		};break;
	}	
	
	if(rh <= 16 * itemscale)
	{
	return layer.y;
	}
	
	var layers = $('#workarea_canvas').getLayers();
	for(var i=0;i<layers.length;i++)
		{
			var name = layers[i].name;
			if(name.indexOf("item")>=0)
				{
					var itemid = parseInt(name.substring(name.indexOf("item") + 4));
					if(selectitemid != itemid)
						{
						var isc = rectangleCol(layers[i].x,layers[i].y,layers[i].width,layers[i].height,rx,ry,rw,rh);
						if(isc)
							{return layer.y;}
						}
				}
		}
	
	 if(y%(itemscale*moveInterval)==0)
	    {
	    	return y;
	    }
	    else {				    	
	 		var c = parseInt(Math.round(y/(itemscale*moveInterval)));
	 		var ry = c * (itemscale*moveInterval);
	 		if(ry >= screenh * itemscale)
	 			{ry = screenh * itemscale}
		    	return ry;				    						    	
		}
}
//画显示项
function DrawDisplayItem(pageid,itemid,isCreat,isSelect,itemleft,itemtop,width,height,imgItem)
{	    	    
	var parentid="workarea_canvas";
	var itemname="item"+itemid;
	var draggableEnable=true;
	if(!ispermission) {draggableEnable=false;}
	var cw=width * itemscale,ch=height * itemscale;	
	var cx=itemleft * itemscale,cy=itemtop * itemscale;
	if(isCreat)
		{
		$('#'+parentid).drawImage({
			  layer: true,
			  name: itemname,
			  source: imgItem,
			  draggable: draggableEnable,
			  //bringToFront: true,
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  updateDragX: function (layer, x) {
				    return Hlimit(x,cw);
				  },
			  updateDragY: function (layer, y) {
				    return Vlimit(y,ch);
				  },
			  fromCenter: false,
			  cursors: {
			    // 显示悬停时的指点器
			    mouseover: 'pointer',
			    // 显示鼠标按下时的'move'指针
			    mousedown: 'move',
			    // 在鼠标弹起时反转指针
			    mouseup: 'pointer'
			  },
			  dragstart: function() {
				  selectChangeItem(pageid,itemid);				  
				  var layer = $('#'+parentid).getLayer(itemname);				 
				  DrawControl('#'+parentid,layer.x,layer.y,layer.width,layer.height);				  
			  },
			  drag: function(layer) {				  
				  var layer = $('#'+parentid).getLayer(itemname);				  
				  DrawControl('#'+parentid,layer.x,layer.y,layer.width,layer.height);					
			  },
			  dragstop: function(layer) {				  
				  var layer = $('#'+parentid).getLayer(itemname);				 
				  itemDragstopUpdate('#'+parentid);	
				  DrawControl('#'+parentid,layer.x,layer.y,layer.width,layer.height);				  				 
			  },
			  mousedown: function(layer) {
				  selectChangeItem(pageid,itemid);				  
				  var layer = $('#'+parentid).getLayer(itemname);					  
				  DrawControl('#'+parentid,layer.x,layer.y,layer.width,layer.height);
				  window.event.cancelBubble = true; 
				  },
			  dblclick: function(layer) {
				  	if(!ispermission) {alertMessage(1, "警告", "没有相关操作权限,请联系管理员!");return;}
				    if(itemmap.hasOwnProperty(pageid))
					{
						for(var i=0;i<itemmap[pageid].length;i++)
							{
								var item=itemmap[pageid][i];
								if(item.itemid==itemid)
									{				
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
									case 3:{
										$('#modal_video_select').modal('show');
										initvideo(1);
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
			}).drawLayers();
		 if(isSelect)
		 {DrawControl('#'+parentid,cx,cy,cw,ch);}
		}
	else
		{
		$('#'+parentid).setLayer(itemname, {
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  updateDragX: function (layer, x) {
				    return Hlimit(x,cw);
				  },
			  updateDragY: function (layer, y) {
				    return Vlimit(y,ch);
				  },			  
			  source: imgItem
			})
			.drawLayers();
		
		  var layer = $('#'+parentid).getLayer(itemname);				 
		  DrawControl('#'+parentid,layer.x,layer.y,layer.width,layer.height);
		}	
}
//画列表显示项
function DrawListItem(pageid,itemid,isCreat,itemleft,itemtop,width,height,imgItem)
{	
	var parentid="list_page_"+pageid;
	var itemscale = ($("#tool_right").width()-10) / screenw;
	
	//itemscale=2;
	var itemname="item"+itemid;	
	
	var cw=width * itemscale,ch=height * itemscale;	
	var cx=itemleft * itemscale,cy=itemtop * itemscale;
	if(isCreat)
		{
		$('#'+parentid).drawImage({
			  layer: true,
			  name: itemname,
			  source: imgItem,
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  fromCenter: false
			});
		}
	else
		{
		$('#'+parentid).setLayer(itemname, {
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  source: imgItem
			})
			.drawLayers();		
		}
}
//画列表显示项
function DrawListItemone(pageid,itemid,isCreat,itemleft,itemtop,width,height,imgItem)
{	
	var parentid="list_page"+pageid+"_item"+itemid;
	var itemscale = ($("#tool_right").width()-30) / screenw;
	//itemscale=2;
	var itemname="item"+itemid;	
	
	var cw=width * itemscale,ch=height * itemscale;	
	var cx=itemleft * itemscale,cy=itemtop * itemscale;
	if(isCreat)
		{
		$('#'+parentid).drawImage({
			  layer: true,
			  name: itemname,
			  source: imgItem,
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  fromCenter: false
			});
		}
	else
		{
		$('#'+parentid).setLayer(itemname, {
			  x: cx, y: cy,				  
			  width: cw,
			  height: ch,
			  source: imgItem
			})
			.drawLayers();		
		}
}
//画控制点
function DrawControl(layerid,x,y,width,height)
{	
	drawPointSize(x,y,width,height);
	var draggableEnable=true;
	if(!ispermission) {draggableEnable=false;}
	
	 $(layerid).removeLayer('controlRect').drawLayers();
	 $(layerid).removeLayer('controlpoint1').drawLayers();
	 $(layerid).removeLayer('controlpoint2').drawLayers();
	 $(layerid).removeLayer('controlpoint3').drawLayers();
	 $(layerid).removeLayer('controlpoint4').drawLayers();
	 $(layerid).removeLayer('controlpoint5').drawLayers();
	 $(layerid).removeLayer('controlpoint6').drawLayers();
	 $(layerid).removeLayer('controlpoint7').drawLayers();
	 $(layerid).removeLayer('controlpoint8').drawLayers();
	 var layer = $(layerid).getLayer('controlRect');
	 if(layer==null)
		 {
		 $(layerid).drawRect({
			  layer: true,
			  name: 'controlRect',
			  strokeStyle: '#FFD700',
			  strokeWidth: 2,
			  strokeDash: [5],
			  strokeDashOffset: 0,
			  bringToFront: true,
			  x: x, y: y,				  
			  width: width,
			  height: height,
			  intangible: true,
			  fromCenter: false
			});
		 var fillcolor='#fff',strokecolor='#000',strokew=0.5,r=5;
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint1',	
			 draggable: draggableEnable,
			 bringToFront: true,			 
			 cursors: {
				    mouseover: 'nw-resize',				    
				    mousedown: 'nw-resize',
				    mouseup: 'nw-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);
				  				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x, y: y,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint2',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'n-resize',				    
				    mousedown: 'n-resize',
				    mouseup: 'n-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x + width/2, y: y,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint3',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'ne-resize',				    
				    mousedown: 'ne-resize',
				    mouseup: 'ne-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x + width, y: y,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint4',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'e-resize',				    
				    mousedown: 'e-resize',
				    mouseup: 'e-resize'
				  },
				  updateDragX: function (layer, x) {
					  return pointHlimit(layer, x);
					  },
				  updateDragY: function (layer, y) {
					  return pointVlimit(layer,y);
					  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x + width, y: y + height/2,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint5',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'se-resize',				    
				    mousedown: 'se-resize',
				    mouseup: 'se-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x + width, y: y + height,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint6',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 's-resize',				    
				    mousedown: 's-resize',
				    mouseup: 's-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x + width/2, y: y + height,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint7',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'sw-resize',				    
				    mousedown: 'sw-resize',
				    mouseup: 'sw-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);				    
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x , y: y + height,
			 radius: r
		 	});
		 
		 $(layerid).drawArc({
			 layer: true,
			 name: 'controlpoint8',	
			 draggable: draggableEnable,
			 bringToFront: true,
			 cursors: {
				    mouseover: 'w-resize',				    
				    mousedown: 'w-resize',
				    mouseup: 'w-resize'
				  },
			  updateDragX: function (layer, x) {
				  return pointHlimit(layer, x);
				  },
			  updateDragY: function (layer, y) {
				  return pointVlimit(layer,y);
				  },
			  drag: function(itemlayer) {
				  itemResize(layerid,itemlayer);				  		 
			  },
			  dragstop: function(layer) {
				  itemDragstopUpdate(layerid);
			  },
			 fillStyle: fillcolor,
			 strokeStyle: strokecolor,
			 strokeWidth: strokew,
			 x: x , y: y + height/2,
			 radius: r
		 	});
		 
		 }
	 else
		 {
		 
		 $(layerid).moveLayer('controlRect', 1);
		 $(layerid).moveLayer('controlpoint1', 1);
		 $(layerid).moveLayer('controlpoint2', 1);
		 $(layerid).moveLayer('controlpoint3', 1);
		 $(layerid).moveLayer('controlpoint4', 1);
		 $(layerid).moveLayer('controlpoint5', 1);
		 $(layerid).moveLayer('controlpoint6', 1);
		 $(layerid).moveLayer('controlpoint7', 1);
		 $(layerid).moveLayer('controlpoint8', 1);
		 
		 $(layerid).setLayer('controlRect', {
		 	  x: x, y: y,	
			  bringToFront: true,
			  width: width,
			  height: height
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint1', {			 
			 bringToFront: true,
			 x: x, y: y
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint2', {
			 bringToFront: true,
			 x: x + width/2, y: y
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint3', {
			 bringToFront: true,
			 x: x + width, y: y
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint4', {
			 bringToFront: true,
			 x: x + width, y: y + height/2
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint5', {
			 bringToFront: true,
			 x: x + width, y: y + height
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint6', {
			 bringToFront: true,
			 x: x + width/2, y: y + height
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint7', {
			 bringToFront: true,
			 x: x , y: y + height
			}).drawLayers();
		 
		 $(layerid).setLayer('controlpoint8', {
			 bringToFront: true,
			 x: x , y: y + height/2
			}).drawLayers();
		 }
}
//计算坐标大小
function drawPointSize(x,y,w,h)
{
	$('#tooltip_xy').text(x/itemscale +","+y/itemscale);
	$('#tooltip_xy').css('left',x);
	$('#tooltip_xy').css('top',y);
	$('#tooltip_xy').css('visibility','visible');
	
	$('#tooltip_w').text(w/itemscale);
	$('#tooltip_w').css('left',x + w/2);
	$('#tooltip_w').css('top',y);
	$('#tooltip_w').css('visibility','visible');
	
	$('#tooltip_h').text(h/itemscale);
	$('#tooltip_h').css('left',x);
	$('#tooltip_h').css('top',y + h/2);
	$('#tooltip_h').css('visibility','visible');
}
//大小改变
function itemResize(layerid,itemlayer)
{
	var x,y,w,h;
	switch(itemlayer.name)
	{
		case 'controlpoint1':{	
			x=itemlayer.x;
			y=itemlayer.y;
		    w = $(layerid).getLayer('controlpoint5').x - itemlayer.x;
		    h = $(layerid).getLayer('controlpoint5').y - itemlayer.y;		    		
		};break;
		case 'controlpoint2':{				
			x=$(layerid).getLayer('controlpoint1').x;
			y=itemlayer.y;
			w = $(layerid).getLayer('controlpoint5').x - x;
			h = $(layerid).getLayer('controlpoint5').y - itemlayer.y;
		};break;
		case 'controlpoint3':{
			x=$(layerid).getLayer('controlpoint1').x;
			y=itemlayer.y;
			w = itemlayer.x - $(layerid).getLayer('controlpoint7').x;
			h = $(layerid).getLayer('controlpoint7').y - itemlayer.y;
		};break;
		case 'controlpoint4':{
			x=$(layerid).getLayer('controlpoint1').x;
			y=$(layerid).getLayer('controlpoint1').y;
			w = itemlayer.x - $(layerid).getLayer('controlpoint8').x;
			h = $(layerid).getLayer('controlpoint7').y - $(layerid).getLayer('controlpoint1').y;
		};break;
		case 'controlpoint5':{
			x=$(layerid).getLayer('controlpoint1').x;
			y=$(layerid).getLayer('controlpoint1').y;
			w = itemlayer.x - $(layerid).getLayer('controlpoint1').x;
			h = itemlayer.y - $(layerid).getLayer('controlpoint1').y;
		};break;
		case 'controlpoint6':{
			x=$(layerid).getLayer('controlpoint1').x;
			y=$(layerid).getLayer('controlpoint1').y;
			w = $(layerid).getLayer('controlpoint3').x - $(layerid).getLayer('controlpoint1').x;
			h = itemlayer.y - $(layerid).getLayer('controlpoint2').y;
		};break;
		case 'controlpoint7':{
			x=itemlayer.x;
			y=$(layerid).getLayer('controlpoint1').y;
			w = $(layerid).getLayer('controlpoint3').x - itemlayer.x;
			h = itemlayer.y - $(layerid).getLayer('controlpoint3').y;
		};break;
		case 'controlpoint8':{
			x=itemlayer.x;
			y=$(layerid).getLayer('controlpoint1').y;
			w = $(layerid).getLayer('controlpoint3').x - itemlayer.x;
			h = $(layerid).getLayer('controlpoint7').y - $(layerid).getLayer('controlpoint1').y;
		};break;
	}	
    	
	var width = w,height = h;
	$(layerid).setLayer('controlRect', {
	 	  x: x, y: y,	
		  bringToFront: true,
		  width: width,
		  height: height
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint1', {			 
		 bringToFront: true,
		 x: x, y: y
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint2', {
		 bringToFront: true,
		 x: x + width/2, y: y
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint3', {
		 bringToFront: true,
		 x: x + width, y: y
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint4', {
		 bringToFront: true,
		 x: x + width, y: y + height/2
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint5', {
		 bringToFront: true,
		 x: x + width, y: y + height
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint6', {
		 bringToFront: true,
		 x: x + width/2, y: y + height
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint7', {
		 bringToFront: true,
		 x: x , y: y + height
		}).drawLayers();
	 
	 $(layerid).setLayer('controlpoint8', {
		 bringToFront: true,
		 x: x , y: y + height/2
		}).drawLayers();	 

	 drawPointSize(x,y,w,h);
	//DrawControl(layerid,x,y,w,h);
}
//控制点拖动结束
function itemDragstopUpdate(layerid)
{	   	
	var left=parseInt(Math.ceil($(layerid).getLayer('controlRect').x / itemscale));
	var top=parseInt(Math.ceil($(layerid).getLayer('controlRect').y / itemscale));
	var width=parseInt(Math.ceil($(layerid).getLayer('controlRect').width / itemscale));
	var height=parseInt(Math.ceil($(layerid).getLayer('controlRect').height / itemscale));
	
	updateitem(selectpageid,selectitemid,["left","top","width","height"],[left,top,width,height]);
	updateCanvasItem(selectpageid,selectitemid,true,false);	
	
	//DrawControl(layerid,left,top,width,height);
}
//更新显示
function DrawDisplay(dataSrc)
{
	var itemid = dataSrc.itemid;
	var pageid = dataSrc.pageid;
	var isCreat = dataSrc.isCreat;
	var isSelect = dataSrc.isSelect;									 	
	var left = dataSrc.left;
	var top = dataSrc.top;
	var width = dataSrc.width;
	var height = dataSrc.height;		
	var itemsrc = dataSrc.itemsrc.getCanvasImage();
			
	DrawListItem(pageid,itemid,isCreat,left,top,width,height,itemsrc);
	DrawListItemone(pageid,itemid,isCreat,left,top,width,height,itemsrc);
	/*
	if(isSelect)
	{DrawDisplayItem(pageid,itemid,isCreat,left,top,width,height,itemsrc);}
	*/
	DrawDisplayItem(pageid,itemid,isCreat,isSelect,left,top,width,height,itemsrc);
}
//更新显示item
function updateCanvasItem(pageid,itemid,isSelect,isCreat)
{				
	if(itemmap.hasOwnProperty(pageid))
		{
			for(var i=0;i<itemmap[pageid].length;i++)
				{
					var item=itemmap[pageid][i];
					if(item.itemid==itemid)
						{
						var canvasitem = item2Src(item);
						//var im = canvasitem.getCanvasImage('png');
						//var contextJson = JSON.parse(item.contextJson);
						dataSrc={	
								itemid:itemid,
							 	pageid:pageid,
							 	isCreat:isCreat,
							 	isSelect:isSelect,									 	
							 	left:item.left,
							 	top:item.top,
								width:item.width,
								height:item.height,	
								isSelect:isSelect,
								itemsrc:canvasitem
						};
						
						setTimeout(DrawDisplay,0,dataSrc);						
						}
				}
		}
}
//画特效文字
function initspecial(special)
{	
	if(special!=null)
	{		
		selectspecial=special;
		
		var sketch=null,shadow=null,gradient=null,txtscale=null;
		
		if(special.sketch!=null)
		{sketch = special.sketch;}
		if(special.shadow!=null)
		{shadow = special.shadow;}
		if(special.gradient!=null)
		{gradient = special.gradient;}	
		if(special.scale!=null)
		{txtscale = special.scale;}	

		//特效开始
		//描边	 					
		 if(sketch!=null)
		 {			
			$('#sketch_check').prop('checked',true);
			
			$('#sketch_color').colorpicker('setValue',sketch.strokeStyle);
									
			var slider = $("#sketch_width").data("ionRangeSlider");
			slider.update({			    
			    from: sketch.strokeWidth
			});
			slider.reset();			
		 }
		 else
		 {
			$('#sketch_check').prop('checked',false);
		 }
		 //阴影
		if(shadow!=null)
		 {		
			$('#shadow_check').prop('checked',true);
			
			$('#shadow_color').colorpicker('setValue',shadow.shadowColor);
			
			var slider = $("#shadow_width").data("ionRangeSlider");
			slider.update({			    
			    from: shadow.shadowBlur
			});
			slider.reset();
		 }
		else
		 {
			$('#shadow_check').prop('checked',false);
		 }
		//渐变
		if(gradient!=null)
	 	{
			$('#gradient_check').prop('checked',true);
			$('#gradient_direction').val(gradient.gradientdirection);
			$('#gradient_color1').colorpicker('setValue',gradient.gradientcolor1);
			$('#gradient_color2').colorpicker('setValue',gradient.gradientcolor2);		    
	 	}
	 	else
		 {
	 		$('#gradient_check').prop('checked',false);		
		 }
		if(txtscale!=null)
			{
			$("#scale_horizontal").val(txtscale.scaleX);
			$("#scale_vertical").val(txtscale.scaleY);
			}
		else
			{
			$("#scale_horizontal").val(100);
			$("#scale_vertical").val(100);
			}
		//特效结束
	}
	else
	{
		$('#sketch_check').prop('checked',false);
		$('#shadow_check').prop('checked',false);
		$('#gradient_check').prop('checked',false);
		selectspecial=null;
	}
}