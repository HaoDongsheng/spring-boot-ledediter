var playPageid=-1,playLastPageid=-1,playLastitemid=-1,gif=null,isStop=false;
//全体播放
function play(itemmap)
{	
	gif = new GIF({
		 workers: 2,//启用两个worker。
		 quality: 10,//图像质量
		 workerScript:'../gif.js-master/dist/gif.worker.js'
		});//创建一个GIF实例
	//testGif(null);
	
	$('#tooltip_xy').css('visibility','hidden');
	$('#tooltip_w').css('visibility','hidden');
	$('#tooltip_h').css('visibility','hidden');
	
	var pageTime =0;				
	var index=0;
	for(var pageid in itemmap)
	{		
		var mapsize=Object.keys(itemmap).length;
		if(index + 1 == mapsize)
			{playLastPageid = pageid;}
		if(index==0)
		{setTimeout(DrawPlayPage,pageTime,pageid);}					
		
		for(var i=0;i<itemmap[pageid].length;i++)
		{
			pageTime += itemmap[pageid][i].itemstyle.playtime * 1000;
		}
		index += 1;
	}
	
}
//画页
function DrawPlayPage(pageid)
{
	if(isStop)
		{
		ExitLoop(1,1)
		return;
		}
	$('#workarea_canvas').removeLayers();
	playPageid = pageid;
	for(var i=0;i<itemmap[pageid].length;i++)
	{				
		var item=itemmap[pageid][i];
		if(i + 1 == itemmap[pageid].length)
		{playLastitemid = item.itemid;}
		playitem(pageid,item);
	}
}
//画显示项
function playitem(pageid,item)
{
	var canvasitem = item2Canvas(item);	
	
	dataSrc={	
			pageid:pageid,
			item:item,		 			
			imgItem:canvasitem
	};
	
	setTimeout(getScaleimg,0,dataSrc);	
}
//获取显示比例图
function getScaleimg(dataSrc) {
	
	var item =dataSrc.item;
	var imgItem =dataSrc.imgItem;	
	var itemname="itemp"+item.itemid;
	
	var w = imgItem[0].width / intiScale * itemscale;
	var h = imgItem[0].height / intiScale * itemscale;
	
	var canvasS = document.createElement("canvas");							
	canvasS.width = w;
	canvasS.height = h;
	
	$(canvasS).drawImage({
		  layer: true,
		  name: itemname,
		  source: imgItem.getCanvasImage(),		  
		  x: 0, y: 0,
		  width: w,
		  height: h,
		  fromCenter: false
		}).drawLayers();

	var im = $(canvasS).getCanvasImage('png');		
	
	sdataSrc={	
			pageid:dataSrc.pageid,
			item:item,	
			index:0,
			imgItem:$(canvasS)
	};
	
	setTimeout(DrawPlayItem,0,sdataSrc);	
}
var delayArr=null,imgArr=null;
//itemlist转src集合
function item2Canvas(item)
{		
	getItemscale();
	
	var contextJson = JSON.parse(item.contextJson);
	var canvasW=0,canvasH=0;
	imgArr=null;
	delayArr=null;
	if(item.type == 4)/*GIF动画*/
	{
		var gifString="";
		for(var r=0;r<contextJson.length;r++)
		{											
			var rownode=contextJson[r];					
			for(var i=0;i<rownode.length;i++)
				{
				gifString=rownode[i].value;
				gifString=gifString.substring(gifString.indexOf(',')+1);
				break;
				}
		}
		$.ajax({  
	        url:"/GetImgbyGif",		
	        data:{
	        	gifString:gifString,
	        	w:item.width,
	        	h:item.height
				},  
	        type:"post",  
	        async: false,
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null && data.result=="success")
	    		{	
	        		//base64img = "data:image/gif;base64,"+data.base64img;
	        		delayArr = data.delayArr;
	        		imgArr = data.imgArr;
	        		canvasW = item.width * delayArr.length * intiScale;
	    			canvasH=item.height * intiScale;
	    		}
	        	else
        		{
	        		alertMessage(1, "警告", data.resultMessage);        			
        		}
	        },  
	        error: function() {  
	        	alertMessage(2, "异常", "ajax 函数  GetImgbyGif 错误");	        	          
	          }  
	    });
	}
	else {
		var result = getitemsize(item.width,item.contextJson,item.itemstyle.playtype);
		//Srceencount, itemw
		switch (parseInt(item.itemstyle.playtype)) {
		case 1:{
			canvasW = (result.itemw + item.width) * intiScale;
			canvasH=item.height * intiScale;
		};break;
		case 2:{
			canvasW = item.width * intiScale;
			canvasH=item.height * (result.Srceencount + 1) * intiScale;
		};break;
		default:{
			canvasW = item.width * result.Srceencount * intiScale;
			canvasH=item.height * intiScale;
		};break;
		}		
		
	}	
	
	var canvas = document.createElement("canvas");							
    canvas.width = canvasW;
    canvas.height = canvasH;
    
    $(canvas).drawRect({
     	  layer: true,
     	  fillStyle: '#000000',
     	  x: 0, y: 0,	    	  
     	  width: canvas.width,
     	  height: canvas.height,
     	  fromCenter: false
     	}).drawLayers();
    
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
    
    
    if(item.type == 4)/*GIF动画*/
	{
    	/*
    	$(canvas).drawImage({	
			  layer: true,
			  name: 'myPic1',
			  source: base64img,						  
			  x: 0, y: 0,
			  width: canvasW,
			  height: canvasH,
			  fromCenter: false
			});
    	*/
	}
	else {
		switch (parseInt(item.itemstyle.playtype)) {
		case 1:{
			canvas = DrawLeftrollItem(item,canvas);
		};break;
		case 2:{
			canvas = DrawUprollItem(item,canvas);			
		};break;
		default:{
			canvas = DrawDefaultItem(item, canvas);
		};break;
		}		
	}
		
    return $(canvas);
}
//画左滚原图
function DrawLeftrollItem(item, canvas)
{
	var contextJson = JSON.parse(item.contextJson);
	
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
    
	var top=0,left=0;
	for(var r=0;r<contextJson.length;r++)
	{											
		var rownode=contextJson[r];
		var arrRow = new Array()
		var itemH=0;
		
		if(r==0 && item.itemstyle.playtype==1 && item.type != 4)
		{
			left = item.width * intiScale;
		}
		for(var i=0;i<rownode.length;i++)
			{
				var itemnode=rownode[i]; 	
				if(itemnode.value==null && itemnode.value=="")
					{continue;} 							
 				switch(itemnode.itemType)
 				{
	 				case 0:{
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
	 						  name: 'myText' + r + i,
							  fillStyle: itemnode.foreColor,
							  x: left, y: top + linespace,
							  fontSize: itemnode.fontSize * intiScale + 'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  fromCenter: false,
							});

	 					var mytextw=$(canvas).measureText('myText' + r + i).width * scaleX;
	 					var mytexth=item.height * intiScale;//$(canvas).measureText('myText').height;
	 					
	 					$(canvas).drawRect({
	 				    	  layer: true,
	 				    	  fillStyle: itemnode.backColor,
	 				    	  x: left, y: top,	    	  
	 				    	  width: mytextw,
	 				    	  height: mytexth,
	 				    	  fromCenter: false
	 				    	}).drawLayers();
	 					$(canvas).removeLayer('myText' + r + i);
	 					
	 					$(canvas).drawText({	
	 						  layer: true,
	 						  name: 'myText' + r + i,
							  fillStyle: itemnode.foreColor,
							  x: left + mytextw/2, y: top + mytexth/2,
							  fontSize: itemnode.fontSize * intiScale + 'px',
							  fontFamily: itemnode.fontName,
							  text: itemnode.value,
							  scaleX: scaleX, scaleY: scaleY,
							  fromCenter: true,
							}).drawLayers();
	 					
	 					//描边	 					
	 					 if(sketch!=null)
	 					 {			 
	 						$(canvas).setLayer('myText' + r + i, {
	 						 strokeStyle: sketch.strokeStyle,
	 						 strokeWidth: sketch.strokeWidth * intiScale
	 						}).drawLayers();
	 					 }
	 					 else
	 					 {
	 					 	$(canvas).setLayer('myText' + r + i, {	 						 
	 						 strokeWidth: 0
	 						}).drawLayers();
	 					 }
	 					 //阴影
	 					if(shadow!=null)
	 					 {			 
	 						$(canvas).setLayer('myText' + r + i, {
	 				 		  shadowColor: shadow.shadowColor,
	 						  shadowBlur: shadow.shadowBlur * intiScale,
	 						  shadowX: shadow.shadowBlur * intiScale, shadowY: shadow.shadowBlur * intiScale
	 						}).drawLayers();
	 					 }
	 					else
	 					 {
	 						$(canvas).setLayer('myText' + r + i, {	 				 		  
	 						  shadowBlur: 0,
	 						  shadowX: 0, shadowY: 0
	 						}).drawLayers();
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
	 			
	 					 
	 					 	$(canvas).setLayer('myText' + r + i, {
	 				 		 fillStyle: linear
	 						}).drawLayers();			    
	 				 	}
	 				 	else
	 					 {
	 				 		$(canvas).setLayer('myText' + r + i, {
	 				 		 fillStyle: itemnode.foreColor
	 						}).drawLayers();		
	 					 }
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
		//top+=itemH;
	}
	return canvas;
}
//画上滚原图
function DrawUprollItem(item, canvas)
{
	var contextJson = JSON.parse(item.contextJson);		
	
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
    
	var top=item.height*intiScale;
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
	 					
	 				    for(var c=0;c<itemnode.value.length;c++)
	 				    	{
		 					$(canvas).drawText({	
		 						  layer: true,
		 						  name: 'myText' + r + i + c,
								  fillStyle: itemnode.foreColor,
								  x: left, y: top + linespace,
								  fontSize: itemnode.fontSize * intiScale + 'px',
								  fontFamily: itemnode.fontName,
								  text: itemnode.value[c],
								  fromCenter: false,
								});
	
		 					var mytextw=$(canvas).measureText('myText' + r + i + c).width * scaleX;
		 					var mytexth=item.height * intiScale;
		 					mytextw = parseInt(mytextw * scaleY);
		 							 								 							 							 							 					
		 					$(canvas).removeLayer('myText' + r + i + c);		 							 					
		 					
		 					if(Math.floor(left/item.width/intiScale)!= Math.floor((left + mytextw - 1)/item.width/intiScale))
				        	{	
		 						top +=item.height*intiScale
		 						var intW = Math.floor((left + mytextw)/item.width/intiScale) * item.width*intiScale - left
		 						left = 0;// Math.floor((left + mytextw)/item.width/intiScale) * item.width*intiScale;
		 						$(canvas).drawRect({
			 				    	  layer: true,
			 				    	  fillStyle: itemnode.backColor,
			 				    	  x: left - intW, y: top,	    	  
			 				    	  width: mytextw + intW,
			 				    	  height: mytexth,
			 				    	  fromCenter: false
			 				    	}).drawLayers();
		 						$(canvas).drawText({	
			 						  layer: true,
			 						  name: 'myText' + r + i + c,
									  fillStyle: itemnode.foreColor,
									  x: left + mytextw/2, y: top + mytexth/2,
									  fontSize: itemnode.fontSize * intiScale + 'px',
									  fontFamily: itemnode.fontName,
									  text: itemnode.value[c],
									  scaleX: scaleX, scaleY: scaleY,
									  fromCenter: true,
									}).drawLayers();		 						
		 						
		 						left += mytextw;
				        	}
		 					else {
		 						if(left>=item.width*intiScale)
		 							{
		 							top +=item.height*intiScale			 						
			 						left = 0;
		 							}
		 						$(canvas).drawRect({
			 				    	  layer: true,
			 				    	  fillStyle: itemnode.backColor,
			 				    	  x: left, y: top,	    	  
			 				    	  width: mytextw,
			 				    	  height: mytexth,
			 				    	  fromCenter: false
			 				    	}).drawLayers();
		 						
		 						$(canvas).drawText({	
			 						  layer: true,
			 						  name: 'myText' + r + i + c,
									  fillStyle: itemnode.foreColor,
									  x: left + mytextw/2, y: top + mytexth/2,
									  fontSize: itemnode.fontSize * intiScale + 'px',
									  fontFamily: itemnode.fontName,
									  text: itemnode.value[c],
									  scaleX: scaleX, scaleY: scaleY,
									  fromCenter: true,
									}).drawLayers();
		 						
		 						//描边	 					
			 					 if(sketch!=null)
			 					 {			 
			 						$(canvas).setLayer('myText' + r + i + c, {
			 						 strokeStyle: sketch.strokeStyle,
			 						 strokeWidth: sketch.strokeWidth * intiScale
			 						}).drawLayers();
			 					 }
			 					 else
			 					 {
			 					 	$(canvas).setLayer('myText' + r + i + c, {	 						 
			 						 strokeWidth: 0
			 						}).drawLayers();
			 					 }
			 					 //阴影
			 					if(shadow!=null)
			 					 {			 
			 						$(canvas).setLayer('myText' + r + i + c, {
			 				 		  shadowColor: shadow.shadowColor,
			 						  shadowBlur: shadow.shadowBlur * intiScale,
			 						  shadowX: shadow.shadowBlur * intiScale, shadowY: shadow.shadowBlur * intiScale
			 						}).drawLayers();
			 					 }
			 					else
			 					 {
			 						$(canvas).setLayer('myText' + r + i + c, {	 				 		  
			 						  shadowBlur: 0,
			 						  shadowX: 0, shadowY: 0
			 						}).drawLayers();
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
			 			
			 					 
			 					 	$(canvas).setLayer('myText' + r + i + c, {
			 				 		 fillStyle: linear
			 						}).drawLayers();			    
			 				 	}
			 				 	else
			 					 {
			 				 		$(canvas).setLayer('myText' + r + i + c, {
			 				 		 fillStyle: itemnode.foreColor
			 						}).drawLayers();		
			 					 }
			 					
								left += mytextw;
							}
	 				    	}
	 				    	 			
	 					//left += mytextw;
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

	 					if(left%item.width!=0 && Math.floor(left/item.width/intiScale)!= Math.floor((left + w - 1)/item.width/intiScale))
			        	{
	 						top+=item.height*intiScale;
	 						left = 0;// Math.floor((left + w)/item.width/intiScale) * item.width*intiScale;
	 						$(canvas).drawImage({	
		 						layer: true,
		 						  name: 'myPic' + i,
								  source: itemnode.value,						  
								  x: left, y: top + linespace,
								  width: w,
								  height: h,
								  fromCenter: false
								});
			        	}
	 					else
 						{
	 						if(left>=item.width*intiScale)
 							{
 							top +=item.height*intiScale			 						
	 						left = 0;
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
 						}
	 					left += w;
	 					
	 					if(itemH < h){ itemH = h;}
	 					
	 				};break;
 				} 				
			}
		top+=itemH;
	}
	return canvas;
}
//画其他原图
function DrawDefaultItem(item, canvas)
{
	var contextJson = JSON.parse(item.contextJson);
	
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
    
	var top=0;
	var left=0;
	for(var r=0;r<contextJson.length;r++)
	{											
		var rownode=contextJson[r];
		var arrRow = new Array()
		var itemH=0;
		left = r * (Math.floor(left/item.width/intiScale) + 1) * item.width * intiScale;
		for(var i=0;i<rownode.length;i++)
			{
				var itemnode=rownode[i]; 	
				if(itemnode.value==null && itemnode.value=="")
					{continue;} 				
 				switch(itemnode.itemType)
 				{
	 				case 0:{
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
	 					
	 				    for(var c=0;c<itemnode.value.length;c++)
	 				    	{
		 					$(canvas).drawText({	
		 						  layer: true,
		 						  name: 'myText' + r + i + c,
								  fillStyle: itemnode.foreColor,
								  x: left, y: top + linespace,
								  fontSize: itemnode.fontSize * intiScale + 'px',
								  fontFamily: itemnode.fontName,
								  text: itemnode.value[c],
								  fromCenter: false,
								});
	
		 					var mytextw=$(canvas).measureText('myText' + r + i + c).width * scaleX;
		 					var mytexth=item.height * intiScale;
		 					mytextw = parseInt(mytextw * scaleY);
		 							 								 							 							 							 					
		 					$(canvas).removeLayer('myText' + r + i + c);		 							 					
		 					
		 					if(Math.floor(left/item.width/intiScale)!= Math.floor((left + mytextw - 1)/item.width/intiScale))
				        	{	
		 						var intW = Math.floor((left + mytextw)/item.width/intiScale) * item.width*intiScale - left
		 						left = Math.floor((left + mytextw)/item.width/intiScale) * item.width*intiScale;
		 						$(canvas).drawRect({
			 				    	  layer: true,
			 				    	  fillStyle: itemnode.backColor,
			 				    	  x: left - intW, y: top,	    	  
			 				    	  width: mytextw + intW,
			 				    	  height: mytexth,
			 				    	  fromCenter: false
			 				    	}).drawLayers();
		 						$(canvas).drawText({	
			 						  layer: true,
			 						  name: 'myText' + r + i + c,
									  fillStyle: itemnode.foreColor,
									  x: left + mytextw/2, y: top + mytexth/2,
									  fontSize: itemnode.fontSize * intiScale + 'px',
									  fontFamily: itemnode.fontName,
									  text: itemnode.value[c],
									  scaleX: scaleX, scaleY: scaleY,
									  fromCenter: true,
									}).drawLayers();		 						
		 						
		 						left += mytextw;
				        	}
		 					else {
		 						
		 						$(canvas).drawRect({
			 				    	  layer: true,
			 				    	  fillStyle: itemnode.backColor,
			 				    	  x: left, y: top,	    	  
			 				    	  width: mytextw,
			 				    	  height: mytexth,
			 				    	  fromCenter: false
			 				    	}).drawLayers();
		 						
		 						$(canvas).drawText({	
			 						  layer: true,
			 						  name: 'myText' + r + i + c,
									  fillStyle: itemnode.foreColor,
									  x: left + mytextw/2, y: top + mytexth/2,
									  fontSize: itemnode.fontSize * intiScale + 'px',
									  fontFamily: itemnode.fontName,
									  text: itemnode.value[c],
									  scaleX: scaleX, scaleY: scaleY,
									  fromCenter: true,
									}).drawLayers();
		 						
		 						//描边	 					
			 					 if(sketch!=null)
			 					 {			 
			 						$(canvas).setLayer('myText' + r + i + c, {
			 						 strokeStyle: sketch.strokeStyle,
			 						 strokeWidth: sketch.strokeWidth * intiScale
			 						}).drawLayers();
			 					 }
			 					 else
			 					 {
			 					 	$(canvas).setLayer('myText' + r + i + c, {	 						 
			 						 strokeWidth: 0
			 						}).drawLayers();
			 					 }
			 					 //阴影
			 					if(shadow!=null)
			 					 {			 
			 						$(canvas).setLayer('myText' + r + i + c, {
			 				 		  shadowColor: shadow.shadowColor,
			 						  shadowBlur: shadow.shadowBlur * intiScale,
			 						  shadowX: shadow.shadowBlur * intiScale, shadowY: shadow.shadowBlur * intiScale
			 						}).drawLayers();
			 					 }
			 					else
			 					 {
			 						$(canvas).setLayer('myText' + r + i + c, {	 				 		  
			 						  shadowBlur: 0,
			 						  shadowX: 0, shadowY: 0
			 						}).drawLayers();
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
			 			
			 					 
			 					 	$(canvas).setLayer('myText' + r + i + c, {
			 				 		 fillStyle: linear
			 						}).drawLayers();			    
			 				 	}
			 				 	else
			 					 {
			 				 		$(canvas).setLayer('myText' + r + i + c, {
			 				 		 fillStyle: itemnode.foreColor
			 						}).drawLayers();		
			 					 }
			 					
								left += mytextw;
							}
	 				    	}
	 				    	 			
	 					//left += mytextw;
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

	 					if(left%item.width!=0 && Math.floor(left/item.width/intiScale)!= Math.floor((left + w - 1)/item.width/intiScale))
			        	{
	 						left = Math.floor((left + w)/item.width/intiScale) * item.width*intiScale;
	 						$(canvas).drawImage({	
		 						layer: true,
		 						  name: 'myPic' + i,
								  source: itemnode.value,						  
								  x: left, y: top + linespace,
								  width: w,
								  height: h,
								  fromCenter: false
								});
			        	}
	 					else
 						{
	 						$(canvas).drawImage({	
		 						layer: true,
		 						  name: 'myPic' + i,
								  source: itemnode.value,						  
								  x: left, y: top + linespace,
								  width: w,
								  height: h,
								  fromCenter: false
								});	 						
 						}
	 					left += w;
	 					
	 					if(itemH < h){ itemH = h;}
	 					
	 				};break;
 				} 				
			}
		//top+=itemH;
	}
	return canvas;
}
//画每帧显示项
function DrawPlayItem(dataSrc)
{
	if(isStop)
	{
	ExitLoop(1,1)
	return;
	}
	//$('#workarea_canvas').removeLayers();
	if(dataSrc.pageid!=playPageid)
		{return;}
	var item =dataSrc.item;
	var imgItem =dataSrc.imgItem;
	var index =dataSrc.index;
	var cw = item.width * itemscale, ch =item.height * itemscale;	
	var cx = item.left * itemscale, cy =item.top * itemscale;
	var itemname="itemp"+item.itemid;
	
	$('#workarea_canvas').removeLayer(itemname);
	var canvasT = document.createElement("canvas");							
	canvasT.width = cw;
	canvasT.height = ch;
	
	var interval = 1 * itemscale;
	if(item.type == 4)/*GIF动画*/
		{
		interval = item.width * itemscale;
		$('#workarea_canvas').drawImage({
			  layer: true,
			  name: itemname,
			  source: "data:image/gif;base64,"+imgArr[index],		  
			  x: cx, y: cy,
			  sx: cx, sy: cy,
			  sWidth: item.width,
			  sHeight: item.height,
			  width: cw,
			  height: ch,
			  fromCenter: false
			}).drawLayers();
		
		intervalTime = 1000;
		if(delayArr!=null && delayArr.length > index)
			{
			intervalTime = delayArr[index];
			}
		dataSrc={	
				pageid:dataSrc.pageid,
				item:item,		 
				index:index + 1,
				imgItem:imgItem
		};
		}
	else {
		interval = item.width * itemscale;
		switch(parseInt(item.itemstyle.playtype))
		{
		case 0:/*静止*/{						
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			
			intervalTime = getIntervalTime(item);
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index + 1,
					imgItem:imgItem
			};
		};break;
		case 1:/*左滚动*/{
			interval = 1 * itemscale;
			var lastw=0;
			if(parseInt(item.itemstyle.stoptime)==0)
				{
				if(imgItem[0].width - index * interval<cw)
					{
					cw = imgItem[0].width - index * interval;
					}
				}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			
			intervalTime = getIntervalTime(item);
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index + 1,
					imgItem:imgItem
			};
			if(parseInt(item.itemstyle.stoptime)!=0)
			{
			if(imgItem[0].width - index * interval<cw)
				{
				intervalTime = parseInt(item.itemstyle.stoptime*1000);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem
				};
				}
			}
		};break;
		case 2:/*上滚*/{		
			interval = item.height * itemscale;
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}			
			
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx, sy: cy + dataSrc.iy * itemscale,
				  sWidth: cw,
				  sHeight: ch,				  
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > (dataSrc.index + 1) * item.height)
			{				
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:dataSrc.iy
				};
			}
		};break;
		case 3:/*左拉帘*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: dataSrc.ix*itemscale,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 1;
			if(dataSrc.ix > item.width)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 4:/*右拉帘*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx + cw - dataSrc.ix*itemscale, y: cy,
				  sx: cx + index * interval + cw - dataSrc.ix*itemscale, sy: cy,
				  sWidth: dataSrc.ix*itemscale,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width)
			{				
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 5:/*左右拉帘*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
			  layer: true,
			  //name: itemname,
			  source: imgItem.getCanvasImage(),		  
			  x: cx, y: cy,
			  sx: cx + index * interval, sy: cy,
			  sWidth: dataSrc.ix*itemscale,
			  sHeight: ch,
			  fromCenter: false
			}).drawImage({
			  layer: true,
			  //name: itemname+"1",
			  source: imgItem.getCanvasImage(),		  
			  x: cx + cw - dataSrc.ix*itemscale, y: cy,
			  sx: cx + index * interval + cw - dataSrc.ix*itemscale, sy: cy,
			  sWidth: dataSrc.ix*itemscale,
			  sHeight: ch,
			  fromCenter: false
			}).drawLayers();
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width/2)
			{
				$('canvas').removeLayers();
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 6:/*上拉帘*/{
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: dataSrc.iy*itemscale,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height)
			{
				dataSrc.iy=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 7:/*下拉帘*/{
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy + ch - dataSrc.iy*itemscale,
				  sx: cx + index * interval, sy: cy + ch - dataSrc.iy*itemscale,
				  sWidth: cw,
				  sHeight: dataSrc.iy*itemscale,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height)
			{
				dataSrc.iy=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 8:/*上下拉帘*/{
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('#workarea_canvas').drawImage({
			  layer: true,			  
			  source: imgItem.getCanvasImage(),		  
			  x: cx, y: cy,
			  sx: cx + index * interval, sy: cy,
			  sWidth: cw,
			  sHeight: dataSrc.iy*itemscale,
			  fromCenter: false
			}).drawImage({
			  layer: true,				  
			  source: imgItem.getCanvasImage(),		  
			  x: cx, y: cy + ch - dataSrc.iy*itemscale,
			  sx: cx + index * interval, sy: cy + ch - dataSrc.iy*itemscale,
			  sWidth: cw,
			  sHeight: dataSrc.iy*itemscale,
			  fromCenter: false
			}).drawLayers();
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height/2)
			{
				$('canvas').removeLayers();
				dataSrc.iy=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 9:/*渐亮*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			var changCount = 10;
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  opacity:dataSrc.ix/changCount,
				  fromCenter: false
				}).drawLayers();				
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 50;
			if(dataSrc.ix > 10)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 10:/*渐灭*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			var changCount = 10;
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  opacity:1 - dataSrc.ix/changCount,
				  fromCenter: false
				}).drawLayers();				
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 50;
			if(dataSrc.ix > 10)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 11:/*左移进*/{			
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx + cw - dataSrc.ix * itemscale, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 12:/*右移进*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx - cw + dataSrc.ix * itemscale, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 13:/*左右移进*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('#workarea_canvas').drawImage({
			  layer: true,			  
			  source: imgItem.getCanvasImage(),		  
			  x: cx - cw/2 + dataSrc.ix * itemscale, y: cy,
			  sx: cx + index * interval, sy: cy,
			  sWidth: cw/2,
			  sHeight: ch,
			  fromCenter: false
			}).drawImage({
			  layer: true,			  
			  source: imgItem.getCanvasImage(),		  
			  x: cx + cw - dataSrc.ix * itemscale, y: cy,
			  sx: cx + index * interval + cw/2, sy: cy,
			  sWidth: cw/2,
			  sHeight: ch,
			  fromCenter: false
			}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width/2)
			{
				$('canvas').removeLayers();
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 14:/*隔行左右对进*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('canvas').removeLayers();
			
			for(var h=0;h<item.height;h=h+2)
				{
				$('#workarea_canvas').drawImage({
				  layer: true,			  
				  source: imgItem.getCanvasImage(),		  
				  x: cx - cw + dataSrc.ix * itemscale, y: cy + h*itemscale,
				  sx: cx + index * interval, sy: cy + h*itemscale,
				  sWidth: cw,
				  sHeight: itemscale,
				  fromCenter: false
				}).drawImage({
				  layer: true,			  
				  source: imgItem.getCanvasImage(),		  
				  x: cx + cw - dataSrc.ix * itemscale, y: cy + (h+1)*itemscale,
				  sx: cx + index * interval + cw, sy: cy + (h+1)*itemscale,
				  sWidth: cw,
				  sHeight: itemscale,
				  fromCenter: false
				}).drawLayers();
				}
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > item.width)
			{
				$('canvas').removeLayers();
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 15:/*上移进*/{			
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy +  ch - dataSrc.iy * itemscale,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height)
			{
				dataSrc.iy=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 16:/*下移进*/{			
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('#workarea_canvas').drawImage({
				  layer: true,
				  name: itemname,
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy -  ch + dataSrc.iy * itemscale,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height)
			{
				dataSrc.iy=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 17:/*上下隔行移进*/{
			if(dataSrc.iy==null)
			{dataSrc.iy=0;}
			$('canvas').removeLayers();
			for(var w=0;w<item.width;w=w+2)
			{
				$('#workarea_canvas').drawImage({
				  layer: true,			  
				  source: imgItem.getCanvasImage(),		  
				  x: cx + w*itemscale, y: cy -  ch + dataSrc.iy * itemscale,
				  sx: cx + index * interval + w*itemscale, sy: cy,
				  sWidth: itemscale,
				  sHeight: ch,
				  fromCenter: false
				}).drawImage({
				  layer: true,			  
				  source: imgItem.getCanvasImage(),		  
				  x: cx + (w+1)*itemscale, y: cy +  ch - dataSrc.iy * itemscale,
				  sx: cx + index * interval + (w+1)*itemscale, sy: cy,
				  sWidth: itemscale,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			}
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					iy:dataSrc.iy + 1
			};
			
			intervalTime = 10;
			if(dataSrc.iy > item.height)
			{
				dataSrc.iy=0;
				$('canvas').removeLayers();
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						iy:0
				};
			}
		};break;
		case 18:/*闪烁*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			if(dataSrc.ix%2==0)
				{
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				}
			else {
				$('canvas').drawRect({
				  fillStyle: '#000',
				  layer: true,
				  name: itemname,
				  x: cx, y: cy,
				  width: cw,
				  height: ch,
				  fromCenter: false
				});
			}
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 50;
			if(dataSrc.ix > 10)
			{
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 19:/*百叶*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('canvas').removeLayers();
			for(var w=0;w<item.width;w=w+4)
			{
			$('#workarea_canvas').drawImage({
				  layer: true,				  
				  source: imgItem.getCanvasImage(),		  
				  x: cx + w*itemscale, y: cy,
				  sx: cx + index * interval + w*itemscale, sy: cy,
				  sWidth: dataSrc.ix*itemscale,
				  sHeight: ch,
				  fromCenter: false
				}).drawLayers();
			}
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > 4)
			{
				dataSrc.ix=0;
				$('canvas').removeLayers();
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 20:/*放大 左上角->全屏*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('canvas').removeLayers();
			var changeCount=10;
			
			$('#workarea_canvas').drawImage({
				  layer: true,				  
				  source: imgItem.getCanvasImage(),		  
				  x: cx, y: cy,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  width: cw/changeCount*(dataSrc.ix+1),
				  height: ch/changeCount*(dataSrc.ix+1),
				  fromCenter: false
				}).drawLayers();
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > changeCount)
			{
				$('canvas').removeLayers();
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		case 21:/*放大 中间->全屏*/{
			if(dataSrc.ix==null)
			{dataSrc.ix=0;}
			$('canvas').removeLayers();
			var changeCount=10;
			
			$('#workarea_canvas').drawImage({
				  layer: true,				  
				  source: imgItem.getCanvasImage(),		  
				  x: cx + cw/2 - cw/changeCount*(dataSrc.ix+1)/2, y: cy + ch/2 - ch/changeCount*(dataSrc.ix+1)/2,
				  sx: cx + index * interval, sy: cy,
				  sWidth: cw,
				  sHeight: ch,
				  width: cw/changeCount*(dataSrc.ix+1),
				  height: ch/changeCount*(dataSrc.ix+1),
				  fromCenter: false
				}).drawLayers();
			
			dataSrc={	
					pageid:dataSrc.pageid,
					item:item,		 
					index:index,
					imgItem:imgItem,
					ix:dataSrc.ix + 1
			};
			
			intervalTime = 10;
			if(dataSrc.ix > changeCount)
			{
				$('canvas').removeLayers();
				dataSrc.ix=0;
				$('#workarea_canvas').drawImage({
					  layer: true,
					  name: itemname,
					  source: imgItem.getCanvasImage(),		  
					  x: cx, y: cy,
					  sx: cx + index * interval, sy: cy,
					  sWidth: cw,
					  sHeight: ch,
					  fromCenter: false
					}).drawLayers();
				
				intervalTime = getIntervalTime(item);
				dataSrc={	
						pageid:dataSrc.pageid,
						item:item,		 
						index:index + 1,
						imgItem:imgItem,
						ix:0
				};
			}
		};break;
		}
	}
	var count=0;
	if(item.itemstyle.playtype==2 && item.type != 4)
	{count = Math.ceil(imgItem[0].height/interval) - 1;}
	else if(item.itemstyle.playtype==1 && item.type != 4)
	{
		if(parseInt(item.itemstyle.stoptime)==0)
			{count = Math.ceil(imgItem[0].width/interval);}
		else
			{count = Math.ceil((imgItem[0].width - item.width*itemscale)/interval) + 2;}
	}
	else
	{count = Math.ceil(imgItem[0].width/interval);}	
	if(index >= count){//索引大于数量退出画图		
		ExitLoop(dataSrc.pageid,item.itemid);
		return;
	}
		
	setTimeout(DrawPlayItem,intervalTime,dataSrc);	
	//setTimeout(testGif,0,$('#workarea_canvas'));		
}
//获取停留时间
function getIntervalTime(item)
{
	var intervalTime=0;
	if(item.itemstyle.playtype!=1 || item.type == 4)
	{	
		intervalTime = item.itemstyle.stoptime * 1000;
	}
	else {		
		var playspeed = parseInt(item.itemstyle.playspeed);		
		switch(playspeed)
		{
			case 0:{
				intervalTime = 0.005 * 1000;
			};break
			case 1:{
				intervalTime = 0.01 * 1000;				
			};break
			case 2:{
				intervalTime = 0.015 * 1000;
			};break
			case 3:{
				intervalTime = 0.02 * 1000;				
			};break
			case 4:{
				intervalTime = 0.025 * 1000;				
			};break
			case 5:{
				intervalTime = 0.03 * 1000;				
			};break
			case 6:{				
				intervalTime = 0.035 * 1000;
			};break
			case 7:{
				intervalTime = 0.04 * 1000;				
			};break
			case 8:{				
				intervalTime = 0.05 * 1000;
			};break
			case 9:{				
				intervalTime = 0.06 * 1000;
			};break
			case 10:{				
				intervalTime = 0.07 * 1000;
			};break
		}		
	}
	return intervalTime;
}//画特效
//退出循环
function ExitLoop(pageid,itemid)
{
	//gif.render();//开始启动
	var isS=false;
	for(var ppageid in itemmap)
	{
		if(isS)
			{
			DrawPlayPage(ppageid);
			break;
			}
		if(ppageid==pageid)
			{isS=true;}
	}	
	if((pageid == playLastPageid && playLastitemid == itemid) || isStop)
	{		
		$('#info_play i').removeClass('fa fa-stop');		
		
		$('#info_play i').addClass('fa fa-play');
		$('#workarea_canvas').removeLayers();
		for(var i=0;i<itemmap[selectpageid].length;i++)
		{
			var item = itemmap[selectpageid][i];					
			if(item.itemid==selectitemid)
			{
				 $('.page_context canvas').css("border-color","black");
				 $('#list_page'+selectpageid+'_item'+item.itemid).css("border-color","yellow");
				 			 
				 selectChangeItem(selectpageid,item.itemid);
				 updateCanvasItem(selectpageid,item.itemid,true,true);	
			}				
			else {
				updateCanvasItem(selectpageid,item.itemid,false,true);	
			}
		}			
	}
}

function testGif(canvasElement) {
	
	var canvas = document.createElement("canvas");							
    canvas.width = 144;
    canvas.height = 24;
    
    $(canvas).drawRect({
     	  layer: true,
     	  fillStyle: '#000000',
     	  x: 0, y: 0,	    	  
     	  width: canvas.width,
     	  height: canvas.height,
     	  fromCenter: false
     	}).drawLayers();   
    var img = document.getElementById("gif_upload");
    
	// 核心方法，向gif中加一帧图像，参数可以是img/canvas元素，还可以从ctx中复制一帧
	//gif.addFrame(img);
	 
	// or a canvas element
	gif.addFrame(canvasElement[0], {delay: 200,copy:true});//一帧时长是200
	 
	// or copy the pixels from a canvas context
	//gif.addFrame(ctx, {copy: true});
	 
	gif.on('finished', function(blob,data) {//最后生成一个blob对象
	 var url = URL.createObjectURL(blob);
	});
	
	startTime = null;
	gif.on('start', function() {
	    return startTime = "ss";
	});
	gif.on('progress', function(p) {
	    return "Rendering";
	  });	
}