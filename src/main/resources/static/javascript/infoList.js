var selectplaylistid=0,rowIndex=-1,ptrowIndex=-1;

$(function(){
	
	$('#playlist_StartTime').timepicker({
		defaultTime:'9:00',
		minuteStep:1,
		showInputs:false,		
		explicitMode:true,
		showMeridian:false
		});
	
	$('#playlist_EndTime').timepicker({
		defaultTime:'9:00',
		minuteStep:1,
		showInputs:false,		
		explicitMode:true,
		showMeridian:false
		});
	
	getGroup();
	
	initInfolist();			
    
	$('#infolist_table').on('click-row.bs.table', function (e,row,$element) {
        
        if($($element).hasClass("changeColor"))
        	{
        	$($element).removeClass('changeColor');
        	rowIndex=-1;        	
        	}
        else {
        	$('#infolist_table .changeColor').removeClass('changeColor');
        	$($element).addClass('changeColor');
        	rowIndex = $element[0].rowIndex;
		}  
        $('#infolist_table').bootstrapTable('refresh');
	});
	
	$("#start_index").change(function(){					
		mutiadditemtip();
	  });
	
	$("#end_index").change(function(){					
		mutiadditemtip();
	  });
	
	$("#interval_index").change(function(){			
		mutiadditemtip();
	  });
	$("#grouplist").change(function(){			
		getInfoList(parseInt($("#grouplist").val()));	    
	  });
	
	$("#list_create").click(function(){
		playlistCreate();
	});		
	
	$("#list_delete").click(function(){
		if(selectplaylistid != 0)
		{
			$("#modal_deleteitem .modal-body").text("是否删除此列表?");
			$("#modal_deleteitem").attr("data-type","delete_list");
			$('#modal_deleteitem').modal('show');
		}
	});
	
	$("#list_publish").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		playlistPublishbyid(playlistid);
		}
	});		
	
	$("#list_attribute").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		playlistAttributebyid(playlistid);
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
	$("#list_SerialPort").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		SendSerialData(playlistid);
		}
	});
		
	$("#playlist").change(function(){
		playlistSelectChange(parseInt($("#playlist").val()));
	});	
	
	$("#btn_muti_add").click(function(){		
		var endindex = parseInt($('#end_index').val());												
		var startindex = parseInt($('#start_index').val());												
		var intervalindex = parseInt($('#interval_index').val());					
		
		var dataval = JSON.parse($('#modal_mutiAddpt').attr('data-value'));
		if(intervalindex!=0 && intervalindex<dataval.timelenght)
			{
			alertMessage(1, "警告", "间隔时间不为0时不能小于广告时长"+dataval.timelenght+"秒");
			$('#interval_index').val(dataval.timelenght);
			return;
			}
		var isS = false;
		if(intervalindex==0)
			{
			for(var i=startindex;i<selectlistcycle;i++)
				{
					isS = addlistitem(dataval.infosn,i,dataval.timelenght);
					if(isS){
						alert("添加排期1条:"+i+"秒");
						break;}
				}
			}
		else {
			var arrayitem = mutiladdlistitem(startindex,endindex,intervalindex,dataval.infosn,dataval.timelenght);
			if(arrayitem.length>0)
				{
					isS=true;
					alert("添加排期"+arrayitem.length+"条:"+arrayitem);					 					
				}
		}
		
		if(!isS)
			{alertMessage(1, "警告", "没有容纳本条广告的空间");}
		else {
			$('#modal_mutiAddpt').modal('hide');
		}
		
	});
		
	initBTabel();
});

function mutiadditemtip() {
	var endindex = parseInt($('#end_index').val());												
	var startindex = parseInt($('#start_index').val());												
	var intervalindex = parseInt($('#interval_index').val());					
	
	var dataval = JSON.parse($('#modal_mutiAddpt').attr('data-value'));
	if(intervalindex!=0 && intervalindex<dataval.timelenght)
	{
	alertMessage(1, "警告", "间隔时间不为0时不能小于广告时长"+dataval.timelenght+"秒");
	$('#interval_index').val(dataval.timelenght);
	return;
	}
	var isS = false;
	if(intervalindex==0)
		{
		for(var i=startindex;i<selectlistcycle;i++)
			{					
				var isS = judgeitem(i,dataval.timelenght);
				if(isS){						
					alertMessage(1, "警告", '预计添加1条排期,排期时间:'+i+"秒"); 
					break;
				}
			}
		}
	else {
		var arrayitem = getmutiladdlistitem(startindex,endindex,intervalindex,dataval.infosn,dataval.timelenght);
		
		if(arrayitem.length>0)
			{
				isS=true;
				alertMessage(1, "警告", '预计添加'+arrayitem.length+'条排期,排期时间:'+arrayitem.join(',')+"秒"); 					
			}
	}
	if(!isS)
	{alertMessage(1, "警告", '没有空间容纳本条广告!');}
}
//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function initBTabel()
{    
    $('#infolist_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        //toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色       
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式        
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        rowStyle:function(row,index)
        {        							
        	if(row.infosn==selectinfoid)
        		{
	        	return { classes: 'infochangeColor'};
        		}
        	else {
        		return {	        			
        		};
			}        	
        },
        columns: [{
            title: '序号',
            field: 'id',    
            visible: true,
            formatter: function (value, row, index) {
                var pageSize = $('#infolist_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#infolist_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'infosn',
            title: '广告id',
            visible: false
            
        }, {
            field: 'infoname',
            title: '广告名称'
            
        }, {
            field: 'lifeAct',
            title: '开始日期'
         
        }, {
            field: 'lifeDie',
            title: '结束日期'
         
        }, {
            field: 'timelenght',
            title: '播放时长(秒)'
          
        }, {
            field: 'indexNo',
            title: '索引号',
            visible: false
          
        }, {
        	field: 'operate',
        	title: '操作',
        	align: 'center',       
        	events: advoperateEvents,
        	formatter: advoperateFormatter
        }]
    });    
   
    $('#info_details_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        toolbar: '#details_toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式        
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
            title: '序号',
            field: 'id',            
            formatter: function (value, row, index) {
                var pageSize = $('#info_details_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#info_details_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'itemContext',
            title: '内容'
            
        }, {
            field: 'itemLocationSize',
            title: '位置大小'
            
        }, {
            field: 'itemStyle',
            title: '参数'
         
        }
        ]
    });
}

function advoperateFormatter(value, row, index) {
    return [
    	'<a class="delete" href="javascript:void(0)" title="下架">',
        '<i class="fa fa-trash-o"></i>下架',
        '</a>'
    ].join('');
}

window.advoperateEvents = {
	'click .delete': function (e, value, row, index) {	
		$("#infolist_table").bootstrapTable("remove", {field: "indexNo",values: [row.indexNo]}); 
		
		itemlist.splice(row.indexNo, 1);
		
		var lifeAct = getNowFormatDate();
		var lifeDie = getNowFormatDate();
		if(selectinfoid!=0)
			{
				var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
				for(var i=0;i<infopubs.length;i++)
				{
					var infosn=infopubs[i].id;																	
					if(infosn==selectinfoid)
						{
						lifeAct=infopubs[i].lifeAct;
						lifeDie=infopubs[i].lifeDie;
						break;
						}
				}
			}
		
		refreshTablebyDate(lifeAct,lifeDie);
		
    	itemChange();
    	
    	refreshTableRowSelect();
    }
};

function infopublish() {
	var infoid = selectinfoid;
	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
	if(selectplaylistid==0) return;
	for(var i=0;i<infopubs.length;i++)
	{
		var infosn=infopubs[i].id;	
		var infoname=infopubs[i].Advname;
		var lifeAct=infopubs[i].lifeAct;
		var lifeDie=infopubs[i].lifeDie;
		var playTimelength=infopubs[i].playTimelength;
		var infopubid=infopubs[i].Pubidid;	
		if(infosn==infoid)
			{		        				
			var item={    
					infosn:infosn,
					infoname:infoname,
					lifeAct:lifeAct,
					lifeDie:lifeDie,
					timelenght:playTimelength
				};   
						
			var Scheduletype = 1;
			var listcycle=0;
			var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	    	if(playlists!=null && playlists.length>0)//存在直接取数据    		
			{
	    		for(var i=0;i<playlists.length;i++)
				{
	    			var id = playlists[i].id;
	    			if(id == selectplaylistid)
	    				{
	    				Scheduletype = parseInt(playlists[i].Scheduletype);
	    				listcycle = JSON.parse(playlists[i].Timequantum).listcycle;
	    				break;
	    				}
				}
			}	
	    	
	    	switch (Scheduletype) {
			case 1://模板
				{
				$('#end_index').attr('max',selectlistcycle);
				$('#end_index').val(selectlistcycle);										
				
				$('#end_index').bind('input propertychange', function() { 
					if(parseInt($('#end_index').val()) > parseInt(selectlistcycle))
					{$('#end_index').val(parseInt(selectlistcycle));} 
					});
				
				$('#start_index').attr('max',selectlistcycle);
				$('#start_index').val(0);										
				
				$('#start_index').bind('input propertychange', function() { 
					if(parseInt($('#start_index').val()) > parseInt(selectlistcycle))
					{$('#start_index').val(parseInt(selectlistcycle));} 
					});
				
				$('#interval_index').attr('max',selectlistcycle);				
				$('#interval_index').val(0);										
				
				$('#interval_index').bind('input propertychange', function() { 
					if(parseInt($('#interval_index').val()) > parseInt(selectlistcycle))
					{$('#interval_index').val(parseInt(selectlistcycle));} 
					});
				var dataval={
					infosn:item.infosn,
					timelenght:item.timelenght
				}
				$('#modal_mutiAddpt').attr('data-value',JSON.stringify(dataval));
				$('#modal_mutiAddpt').modal('show');
				
				};
				break;

			case 2://顺序
				{
				if(rowIndex==-1)
				{
					itemlist.push(item);
					item.indexNo=itemlist.length - 1;
					$('#infolist_table').bootstrapTable('append', item);					 
				}
				else {
					var bootdata = $("#infolist_table").bootstrapTable("getData");					
					itemlist.splice(bootdata[rowIndex].indexNo,0,item);
					//item.indexNo=bootdata[rowIndex].indexNo;
					//var params = {index:rowIndex, row:item};
		    		//$('#infolist_table').bootstrapTable('insertRow', params);
		    		
		    		refreshTablebyDate(lifeAct,lifeDie);
		    		refreshTableRowSelect();
				}
				
				alertMessage(0, "成功", "上架成功!"); 
		    	//itemlist = $("#infolist_table").bootstrapTable("getData");
		    	
		    	itemChange();  
				};
				break;
			}		    			    			    	  		    	    		    	    		   
			break;
			}
	}		
}
//界面初始化
function initInfolist()
{
	$(".modal" ).draggable();
	
	$("#modal_addplaylist").on("shown.bs.modal",function(){		
		document.getElementById('creat_playlist_name').focus();
		});
	
	$('#btn_add_playlist').click(function(){	
		playlist_creat();		
	});
	
	$('#btn_delete_item').click(function(){
		
		var datatype = $("#modal_deleteitem").attr("data-type");
		switch (datatype) {
		case "delete_list":{
			var playlistid = selectplaylistid;
			playlistDeletebyid(playlistid);
		};break;
		case "delete_modelInfo":{
			var layerid = $("#modal_deleteitem").attr("data-layerid");
			var infoid=parseInt(layerid.substring(0,layerid.indexOf("offset")));
			var st=parseInt(layerid.substring(layerid.indexOf("offset") + 6));
			itemDeletebyst(infoid,st);
		};break;
		case "removeAll_Info":{
			var infosn = parseInt($("#modal_deleteitem").attr("data-infosn"));
	    	var infopubid = parseInt($("#modal_deleteitem").attr("data-infopubid"));
	    	
	    	var Scheduletype = 1;
			var listcycle=0;
			var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	    	if(playlists!=null && playlists.length>0)//存在直接取数据    		
			{
	    		for(var i=0;i<playlists.length;i++)
				{
	    			var id = playlists[i].id;
	    			if(id == selectplaylistid)
	    				{
	    				Scheduletype = parseInt(playlists[i].Scheduletype);
	    				listcycle = JSON.parse(playlists[i].Timequantum).listcycle;
	    				break;
	    				}
				}
			}	
	    	
	    	switch (Scheduletype) {	
		    	case 1://模板
				{	
		    		if(itemlist!=null && itemlist.length>0)
			    	{
			    		for(var l=0;l<itemlist.length;l++)
			    		{
			    			var item = itemlist[l];
			    						
			    			if(item.infoid==infosn)
			    			{
			    				for(var f=0;f<item.offsetlist.length;f++)
			    					{
			    					var st=item.offsetlist[f];
			    					$('#info_canvas').removeLayerGroup(infosn+"offset"+st).drawLayers();		    						    				
			    					}
			    				itemlist.splice(l, 1);
			    				itemChange();
			    				break;				
			    			}			
			    		}
			    	}
				};
				break;	
			case 2://顺序
				{
				$("#infolist_table").bootstrapTable("remove", {field: "infosn",values: [infosn]});
				
				if(itemlist!=null && itemlist.length>0)
		    	{
		    		for(var l=0;l<itemlist.length;l++)
		    		{
		    			var item = itemlist[l];
		    						
		    			if(item.infosn==infosn)
		    			{		    				
		    				itemlist.splice(l, 1);
		    				l=l-1;
		    			}			
		    		}
		    	}
				var lifeAct = getNowFormatDate();
				var lifeDie = getNowFormatDate();
				if(selectinfoid!=0)
					{
						var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
						for(var i=0;i<infopubs.length;i++)
						{
							var infosn=infopubs[i].id;																	
							if(infosn==selectinfoid)
								{
								lifeAct=infopubs[i].lifeAct;
								lifeDie=infopubs[i].lifeDie;
								break;
								}
						}
					}
				
				refreshTablebyDate(lifeAct,lifeDie);
						
		    	itemChange();
		    			    	
		    	refreshTableRowSelect();
				};
				break;
			}		    			    			    	  		    	    		    	    		   				    		    		    			    		    	
		};break;		
		case "delete_tabeInfo":{
			var infosn = parseInt($("#modal_deleteitem").attr("data-infosn"));
	    	var infopubid = parseInt($("#modal_deleteitem").attr("data-infopubid"));
			
	    	$.ajax({  
		        url:"/deleteinfobyid",          
		        type:"post",		        
		        data:{
		        	infosn:infosn,
		        	groupid:parseInt($("#grouplist").val()),
		        	infopubid:infopubid,
		        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
		        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
					},  
		        dataType:"json", 
		        success:function(data)  
		        {       	        	
		        	if(data.result=='success')
					{
		        		$("#div_" + infosn).remove();		        		
					}
				else
					{alertMessage(1, "警告", data.resultMessage);}
		        },  
		        error: function() {
		        	alertMessage(2, "异常", "ajax 函数  deleteinfobyid 错误"); 		        	          
		          }  
		    });
	    	
		};break;
		}		
		
		$('#modal_deleteitem').modal('hide');
	});
	
	$('#btn_add_quantum').click(function(){
		var st = $('#playlist_StartTime').val();
		var et = $('#playlist_EndTime').val();
		if(st>et && et!='00:00')
			{alertMessage(1, "警告", "开始时间不能大于结束时间");return;}
				
		var optionCount = $('#creat_playlist_quantum option').length;
		var isJoin=false;
		if(optionCount>0)
			{		
			$("#creat_playlist_quantum option").each(function(){
				var lst = this.value.substring(0,this.value.indexOf("---"));
				var let = this.value.substring(this.value.indexOf("---") + 3);
				
				var lstval=parseInt(lst.substring(0,lst.indexOf(":")))*60 + parseInt(lst.substring(lst.indexOf(":") + 1));
				var letval=parseInt(let.substring(0,let.indexOf(":")))*60 + parseInt(let.substring(let.indexOf(":") + 1));
				if(letval==0){letval=24*60;}
				
				var stval=parseInt(st.substring(0,st.indexOf(":")))*60 + parseInt(st.substring(st.indexOf(":") + 1));
				var etval=parseInt(et.substring(0,et.indexOf(":")))*60 + parseInt(et.substring(et.indexOf(":") + 1));
				if(etval==0){etval=24*60;}
				
				if((stval>=lstval && stval<letval) || (etval>lstval && etval<letval) || (stval<=lstval && etval>=letval))
				{isJoin=true;return;}					
									
			  });					
			}
			 					
		if(isJoin)
			{alertMessage(1, "警告", "时间段不能和已存在的时间段存在交集");}
		else
			{			
			var oisJ = isJionCreattime(st,et,$("#creat_playlist_level").val());
			if(oisJ)
				{alertMessage(1, "警告", "时间段不能和已存在的时间段存在交集");}
			else
				{
				var txt = st+"---"+et;
				var option = "<option value='"+txt+"'>"+txt+"</option>";
				$('#creat_playlist_quantum').append(option);
				
				$('#modal_addquantum').modal('hide');
				}
			}					
	});	
	
	$('#btn_del_quantum').click(function(){
		
		var checkText=$("#creat_playlist_quantum").val();   //获取Select选择的Text
		$("#creat_playlist_quantum option[value='"+checkText+"']").remove(); 
	});
	
	$('#creat_playlist_type').change(function(){
		var playlist_type=parseInt($('#creat_playlist_type').val());
		switch(playlist_type)
		{
			case 2:{
				$('#creat_playlist_cycle').parents('.row').css('display','none');
			};break;
			
			case 1:{
				$('#creat_playlist_cycle').parents('.row').css('display','block');
			};break;
		}
	});	
	
	$('#creat_playlist_level').change(function(){
		var playlist_level = parseInt($('#creat_playlist_level').val());
		if(playlist_level==0)
			{
			$('#creat_playlist_type').parents('.row').css('display','none');
			$('#creat_playlist_quantum').parents('.row').css('display','none');			
			$('#creat_playlist_cycle').parents('.row').css('display','none');
			}
		else {			
			$('#creat_playlist_type').parents('.row').css('display','block');
			$('#creat_playlist_quantum').parents('.row').css('display','block');
			var playlist_type=parseInt($('#creat_playlist_type').val());
			switch(playlist_type)
			{
				case 2:{
					$('#creat_playlist_cycle').parents('.row').css('display','none');
				};break;
				
				case 1:{
					$('#creat_playlist_cycle').parents('.row').css('display','block');
				};break;
			}			
		}
	});
	
}

//获取分组信息
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
							getInfoList(grpid);
							}
	        				else
							{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								getInfoList(grpid);
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
			if(grpsinfo.length>1)
			{
			$('#input_group_grouplist').css("display","inline");
			}
			else
			{$('#input_group_grouplist').css("display","none");}
		
			for(var i=0;i<grpsinfo.length;i++)
			{
				var grpid=grpsinfo[i].grpid;
				var grpname = grpsinfo[i].grpname;
				if(selectGrpid==0 || selectGrpid==null)
				{
				if(i==0)
				{
				$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
				getInfoList(grpid);
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid == grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					getInfoList(grpid);
					}
    				else
					{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
				}
			}
		}
}
//获取播放列表信息靠分组id
function getInfoList(groupid)
{
	sessionStorage.setItem('playlists', "");
	sessionStorage.setItem('selectgrpid', groupid);
	$.ajax({  
        url:"/getInfoList",          
        type:"post", 
        data:{
        	groupid:groupid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {     
			$('#playlist').empty();
        	if(data!=null && data.length>0)    		
        		{        			
        			var fristid=0;
	        		for(var i=0;i<data.length;i++)
					{
	        			var id=data[i].id;
						var Playlistname=data[i].Playlistname;							
						var pubid=data[i].pubid;
												
						addinfolistnav(id,Playlistname,pubid);												
						
						if(i==0)
						{fristid=id;}						
					}
	        		
	        		sessionStorage.setItem('playlists', JSON.stringify(data));
	        		
	        		GetInfopubsbygrpid(groupid);
	        		
	        		playlistSelectChange(fristid);
        		}
        	else
        	{
        		        		
        		$("#infolist_table").bootstrapTable('removeAll');
        		
        		sessionStorage.setItem('playlists', "[]");
        		
        		GetInfopubsbygrpid(groupid);
        	}
        },  
        error: function() {
        	alertMessage(2, "异常", "ajax 函数  getInfoList 错误");         	          
          }  
    });	
}
//鼠标进入
function mOver(obj){
	//$(obj).css("background","#31b0d5");
	//$(obj).css("color","#fff");		
}
//鼠标移除
function mOut(obj){
	//$(obj).css("background","#F7F7F7");
	//$(obj).css("color","#000");	
}
//获取分组下已发布广告集合
function GetInfopubsbygrpid(groupid)
{
	sessionStorage.setItem('infopubs', "");
	$.ajax({  
        url:"/getInfopubs",          
        type:"post", 
        async:false, 
        data:{
        	groupid:groupid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	        	
        	$('#infolist_draft').empty();			
			$('#badge_infolist').text(0);			
        	if(data!=null && data.length>0)    		
        		{        
        			//var ArrayTable = [];
        			
	        		for(var i=0;i<data.length;i++)
					{
	        			
	        			var infosn=data[i].id;	
	        			var infoname=data[i].Advname;
	        			var lifeAct=data[i].lifeAct;
	        			var lifeDie=data[i].lifeDie;
	        			var playTimelength=data[i].playTimelength;
	        			var infopubid=data[i].Pubidid;	
	        			/*	    
	        			var item={
        					infosn:infosn,
        					infoname:infoname,
        					lifeAct:lifeAct,
        					lifeDie:lifeDie,
        					timelenght:playTimelength,
        					infopubid:infopubid
	        			};
	        			ArrayTable.push(item);
	        			*/
	        			if(lifeAct==""){lifeAct="永久";}
	        			if(lifeDie==""){lifeDie="永久";}
	        			var tooltip="生命周期:"+lifeAct+"---"+lifeDie;
	        			tooltip+=" 时长"+playTimelength+"秒";
	        			var pl="<a id='info_"+infosn+"'><span>"+infoname+"</span></a>";//<i class='fa fa-ellipsis-v' style='float:right;color:#00f'></i>
	        			var div="<div id='div_"+infosn+"' data-pubid="+infopubid+" title='"+tooltip+"' style='text-align:center;background:#F7F7F7;' onclick='infoSelectChange("+infosn+",true)' class='list-group-item' onmouseover='mOver(this)' onmouseout='mOut(this)'>";
	        			div=div+pl+"</div>";	        			
	        			$('#infolist_draft').append(div);
	        			var count = parseInt($('#badge_draft').text()) + 1;
	        			$('#badge_draft').text(count);
					}
	        		
					//$("#infoitem_table").bootstrapTable('load', ArrayTable);
	        		
	        		sessionStorage.setItem('infopubs', JSON.stringify(data));
	        		
	        		var divheight = $('#infolist_draft').parent('.col-md-2').height();
	        		if(divheight>600)
	        			{
	        			$('#infolist_draft').parent('.col-md-2').css('height','600px');
	        			$('#infolist_draft').parent('.col-md-2').css('overflow','auto');	        			
	        			}
	        		else
	        			{$('#infolist_draft').parent('.col-md-2').css('overflow','hide');}
        		}
        },  
        error: function() {
        	alertMessage(2, "异常", "ajax 函数  getInfopubs 错误");         	          
          }  
    });
}
//判断新建广告添加时间段是否和已有广告有交集
function isJionCreattime(st,et,playlist_level)
{
	var isJoin=false;
	var stval=parseInt(st.substring(0,st.indexOf(":")))*60 + parseInt(st.substring(st.indexOf(":") + 1));
	var etval=parseInt(et.substring(0,et.indexOf(":")))*60 + parseInt(et.substring(et.indexOf(":") + 1));
	if(etval==0){etval=24*60;}
	
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var Playlistlevel=playlists[i].Playlistlevel;
			if(parseInt(playlist_level) == parseInt(Playlistlevel))
				{
				var Timequantum=playlists[i].Timequantum;					
				
				var jsontq = JSON.parse(Timequantum);			
				var timelist = jsontq.timelist
				if(timelist!=null && timelist.length>0)
				{
					for(var q=0;q<timelist.length;q++)
						{													
							var lstval=parseInt(timelist[q].lifeAct);
							var letval=parseInt(timelist[q].lifeDie);
							if(letval==0){letval=24*60;}
							
							if((stval>=lstval && stval<letval) || (etval>lstval && etval<letval) || (stval<=lstval && etval>=letval))
							{isJoin=true;break;}										
						}
				}
				}
		}
	}
	return isJoin;
}
//播放列表选择改变
function playlistSelectChange(playlistid)
{
	if(playlistid!=selectplaylistid)
	{
		$("#playlist option").removeAttr("selected","selected");
		$("#playlist option[value="+playlistid+"]").attr("selected","selected"); 		
		
		itemlist=[];
		selectplaylistid=playlistid;  
		var playlists= JSON.parse(sessionStorage.getItem('playlists'));
    	if(playlists!=null && playlists.length>0)//存在直接取数据    		
		{
    		for(var i=0;i<playlists.length;i++)
			{
    			var id=playlists[i].id;
    			if(id==playlistid)
    				{    				
					var Scheduletype=parseInt(playlists[i].Scheduletype);
					var Timequantum=playlists[i].Timequantum;
					var Programlist=playlists[i].Programlist;
										
					var jsontq = JSON.parse(Timequantum);					
					var timelist = jsontq.timelist
					
					if(Programlist=="")
					{itemlist=[];}
					else
					{itemlist=JSON.parse(Programlist);}	
					
					switch(Scheduletype)
					{
						case 2:{
							$("#pt_sort").css("display","inline");
							$("#pt_template").css("display","none");
							refreshTablebyDate(getNowFormatDate(),getNowFormatDate());
						};break;
						case 1:{
							$("#pt_sort").css("display","none");
							$("#pt_template").css("display","inline");
							if(jsontq.listcycle!=null && jsontq.listcycle!="" && jsontq.listcycle!=0)
							{DrawBackground(jsontq.listcycle);}
							GetitemList('','');
						};break;
					}														
					
					var divheight = $('#pt_sort').parent('.col-md-10').height();
	        		if(divheight>600)
	        			{
	        			$('#pt_sort').parent('.col-md-10').css('height','600px');
	        			$('#pt_sort').parent('.col-md-10').css('overflow','auto');	        			
	        			}
	        		else
	        			{$('#pt_sort').parent('.col-md-10').css('overflow','hide');}
					break;
    				}
			}
		}
    	else//不存在数据库重新取
    		{alertMessage(1, "警告", "playlists=null");}
    	
    	  	
	}
}
//刷新选中列表
function refreshTableRowSelect() {
	
	if(rowIndex != -1)
		{
		var trlist = $("#infolist_table tbody tr");
		for(var i=0;i<trlist.length;i++)
			{
				var index = trlist[i].rowIndex;
				if(index==rowIndex)
					{					
					$(trlist[i]).addClass('changeColor');
		        	break;
					}
			}
		}
		
}
//
function refreshTablebyDate(dts,dte)
{	
	if(dts==''){dts='1999-09-09';}
	if(dte==''){dte='2100-09-09';}
	
	var nowdate = getNowFormatDate();
	if(dts<nowdate){dts=nowdate;}
	$('#infolist_draft div').css("background","#F7F7F7");
	$('#infolist_draft div').css("color","#000");
	
	var Scheduletype=2;
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==selectplaylistid)
				{    				
				var Scheduletype=parseInt(playlists[i].Scheduletype);
				}
		}
	}
	
	switch(Scheduletype)
	{
		case 2:{
			$('#infolist_table .changeColor').removeClass('changeColor');
			var ArrayTable = [];
			if(itemlist.length>0)
				{
					for(var r=0;r<itemlist.length;r++)
					{
					var lifeAct = itemlist[r].lifeAct;
					var lifeDie = itemlist[r].lifeDie;
					var infosn = parseInt(itemlist[r].infosn);
					if(lifeAct==''){lifeAct='1999-09-09';}
					if(lifeDie==''){lifeDie='2100-09-09';}
					
					var isjion = dtJion(dts,dte,lifeAct,lifeDie);//判断日期是否有交集
					if(isjion)
						{	
						var item=itemlist[r];
						item.indexNo=r;
						ArrayTable.push(itemlist[r]);
						
						//$('#div_'+infosn).addClass("infochangeColor");
						$('#div_'+infosn).css("background","#31b0d5");
						$('#div_'+infosn).css("color","#fff");
						}
					}
				}
			$("#infolist_table").bootstrapTable('load', ArrayTable);
		};break;
		case 1:{
			clearlayeritem();
			GetitemList(dts,dte);
		};break;
	}					
}
//播放列表选择改变
function infoSelectChange(infoid,isCancle)
{		
	$('#infoMore').popover('hide');
	$('#infoMore').remove();	
	
	if(selectinfoid != infoid)
		{
		selectinfoid=infoid;		
			
		var dts = '';
		var dte = '';
				
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
				dts=lifeAct;
				dte=lifeDie;
    			break;
				}
		}
		var nowdate = getNowFormatDate();
		if(dts<nowdate){dts=nowdate;}
		
		refreshTablebyDate(dts,dte);

    	refreshTableRowSelect();    		
				 
    	$('#infolist_draft span').removeClass('mws-ic-16 ic-accept');		
		
		$("#div_"+selectinfoid+" span").addClass('mws-ic-16 ic-accept');
		
		$("#div_"+selectinfoid).append("<a id='infoMore' data-toggle='popover' style='float:right;color:#00f;font-size:16px'><i class='fa fa-ellipsis-v'></i></a>");
		
		
		var html ='<div class="btn-group-vertical btn-group-sm"><span id="morepublish" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-plus"></i>上架</span>'+
		'<span id="moreDownAll" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-remove"></i>下架</span>'+
        '<span id="moredetails" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-exclamation-circle"></i>详情</span>'+
        '<span id="moredelete" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-trash-o"></i>删除</span>'+
        '<span id="morecopy" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-copy"></i>复制</span>'+
        '<span id="moreserialPort" class="btn btn-link" href="javascript:void(0)"><i class="fa fa-magnet"></i>串口</span></div>';
		
		$('[data-toggle="popover"]').each(function () {
            var element = $(this);
            element.popover({
                trigger: 'manual',
                placement: 'right', //top, bottom, left or right
                title: '',
                html: true,
                content: html,
            }).on("mouseenter", function () {
                var _this = this;
                $(this).popover("show");
                $(this).siblings(".popover").on("mouseleave", function () {
                    $(_this).popover('hide');
                });
                //上架
                $('#morepublish').click(function() {
                	$(_this).popover('hide');
        			infopublish();
        		});
                //下架                
                $('#moreDownAll').click(function() {
                	$(_this).popover('hide');
                	$("#modal_deleteitem .modal-body").text("是否下架此广告全部排期?");
                	$("#modal_deleteitem").attr("data-type","removeAll_Info");
                	$("#modal_deleteitem").attr("data-infosn",selectinfoid);                	                	
                	$("#modal_deleteitem").attr("data-infopubid",parseInt($("#div_"+selectinfoid).attr("data-pubid")));
            		$('#modal_deleteitem').modal('show');
        		});
                //详情
                $('#moredetails').click(function() {
                	$(_this).popover('hide');
                	$("#modal_details").attr("data-type", selectinfoid);
                	$("#modal_details").modal('show'); 
                	getitem(selectinfoid);
        		});
                //删除
                $('#moredelete').click(function() {
                	$(_this).popover('hide');
                	$("#modal_deleteitem .modal-body").text("删除此广告全部列表将下架此广告,是否下架此广告?");
                	$("#modal_deleteitem").attr("data-type","delete_tabeInfo");
                	$("#modal_deleteitem").attr("data-infosn",selectinfoid);                	                	
                	$("#modal_deleteitem").attr("data-infopubid",parseInt($("#div_"+selectinfoid).attr("data-pubid")));
            		$('#modal_deleteitem').modal('show');
        		});
                //复制
                $('#morecopy').click(function() {
                	$(_this).popover('hide');

                	var infoid = selectinfoid;
                	$.ajax({  
            	        url:"/CopyInfotodraft", 
            	        data:{
            	        	infosn:infoid,
            	        	groupid:parseInt($("#grouplist").val()),
            	        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
            	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
            				},  
            	        type:"post",  
            	        dataType:"json", 
            	        success:function(data)  
            	        {       	  
            	        	if(data.result=="success")
            	        		{	        			
            	        			alertMessage(0, "成功", "复制成功");
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
        		});
                //下架串口
                $('#moreserialPort').click(function() {
                	$(_this).popover('hide');
                	var infoid = selectinfoid;
                	infoSendSerialData(infoid);
        		});
            }).on("mouseleave", function () {
                var _this = this;
                setTimeout(function () {
                    if (!$(".popover:hover").length) {
                        $(_this).popover("hide")
                    }
                }, 100);
            });
        });
		
		if(itemlist!=null && itemlist.length>0)
		{
		for(var l=0;l<itemlist.length;l++)
			{
				var item = itemlist[l];
				
				var itemColor = itembackColor;
				if(item.infoid==selectinfoid)
				{
					itemColor = itemSelectbackColor;
				}
				
				var offsetlist = item.offsetlist;
				if(offsetlist!=null && offsetlist.length>0)
				{
					for(var j=0;j<offsetlist.length;j++)
						{
						var st = offsetlist[j];
						
	        			var layerid=item.infoid + "offset" + st;		        					        					        					        			
	        			$('#info_canvas').setLayerGroup(layerid, {
	        				  fillStyle: itemColor		        					        				  
	        				})
	        				.drawLayers();	        					        			
						}
				}		        						
			}
		}
	}
	else if(isCancle)
	{			
	selectinfoid = 0;	
	$('#infolist_draft span').removeClass('mws-ic-16 ic-accept');
	refreshTablebyDate(getNowFormatDate(),getNowFormatDate())	 
	}
}
//删除列表
function playlistDeletebyid(playlistid)
{
	$.ajax({  
        url:"/deleteplaylistbyid",          
        type:"post", 
        data:{
        	playlistid:playlistid,
        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null)    		
        		{
	        		if(data.result=='success')
					{
	        			/*
						if($("#div_"+playlistid).parent()[0].id=='playlist_draft')
        				{
        				var count_draft = parseInt($('#badge_draft').text()) - 1;
            			if(count_draft<0){count_draft=0;}
    					$('#badge_draft').text(count_draft);
    					}
        			else
        				{
        				var count_publish = parseInt($('#badge_publish').text()) - 1;
            			if(count_publish <0){count_publish =0;}
    					$('#badge_publish').text(count_publish );
    					}
						
						$("#div_"+playlistid).remove();	
						*/
	        			
	        			$("#playlist option[value="+playlistid+"]").remove(); 
	        			
						var playlists= JSON.parse(sessionStorage.getItem('playlists'));
												
						if(playlists!=null && playlists.length>0)//存在直接取数据    		
						{
				    		for(var i=0;i<playlists.length;i++)
							{
				    			var id=playlists[i].id;
				    			if(id==playlistid)
				    				{
				    				playlists.splice(i, 1);
				    				break;
				    				}
							}
						}
						
						sessionStorage.setItem('playlists', JSON.stringify(playlists));
						
						$('#info_canvas').removeLayers().drawLayers();
						itemlist=[];
					}
	        		else
					{alertMessage(1, "警告", data.resultMessage);}
        		}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  deleteplaylistbyid 错误");        	          
          }  
    });
}
//发布列表
function playlistPublishbyid(playlistid)
{
	if(itemlist == null || itemlist.length<=0)
		{		
		alertMessage(1, "警告", "排期数据为空不能发布");
		return;
		}
	
	var mapData = getListData(playlistid);
	
	if(mapData.issave != 0)
	{ajaxSaveInfoList(playlistid,mapData.listname,mapData.listtype,mapData.strquantums,mapData.ScheduleType,itemlist,false,true);}	
}
//获取数据
function getListData(playlistid)
{
	var listname = "";
	var listtype = "";				
	var strquantums="";
	var ScheduleType=1;
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	var issave = 0;
	var pubid = "0";
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==selectplaylistid)
				{
				pubid=playlists[i].pubid;
				listname=playlists[i].Playlistname;
				ScheduleType=playlists[i].Scheduletype;
				listtype=playlists[i].Playlistlevel					
				strquantums=playlists[i].Timequantum
				if(playlists[i].isSave==null || playlists[i].isSave==0)
					{issave=0;}
				else
					{issave=1;}								
				break;
				}
		}
	}
	
	var mapData={
		listid:playlistid,
		listname:listname,
		listtype:listtype,
		strquantums:strquantums,
		ScheduleType:ScheduleType,		
		issave:issave,
		pubid:pubid
	};
	
	return mapData;
}
//广告详情
function playlistAttributebyid(playlistid)
{
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id = playlists[i].id;
			if(id == playlistid)
				{
				var Playlistname=playlists[i].Playlistname;							
				//var pubid=playlists[i].pubid;					
				var Playlistlevel=playlists[i].Playlistlevel;
				//var Grpid=playlists[i].Grpid;
				var Scheduletype=playlists[i].Scheduletype;
				var Timequantum=playlists[i].Timequantum;
				//var Programlist=playlists[i].Programlist;
				//var Delindex=playlists[i].Delindex;	
				
				$('#creat_playlist_name').val(Playlistname);
				$('#creat_playlist_type').val(Scheduletype);
				$('#creat_playlist_level').val(Playlistlevel);
				
				$('#playlist_StartTime').val('00:00');
				$('#playlist_EndTime').val('00:00');					
				
				$('#creat_playlist_quantum').empty();
				var jsontq = JSON.parse(Timequantum);
				$('#creat_playlist_cycle').val(jsontq.listcycle);
				
				var timelist = jsontq.timelist
				if(timelist!=null && timelist.length>0)
				{
					for(var q=0;q<timelist.length;q++)
						{							
							var st=(Array(2).join(0) + parseInt(timelist[q].lifeAct/60)).slice(-2)+":"+(Array(2).join(0) + timelist[q].lifeAct%60).slice(-2);
							var et=(Array(2).join(0) + parseInt(timelist[q].lifeDie/60)).slice(-2)+":"+(Array(2).join(0) + timelist[q].lifeDie%60).slice(-2);
							var option = "<option>"+st+"---"+et+"</option>";
							$('#creat_playlist_quantum').append(option);
						}
				}				
				break;
				}
		}
	}
	
	$("#modal_addplaylist").attr("data-type","update_playlist");
	$('#modal_addplaylist').modal('show');
}
//点击创建标示，打开模态窗口
function playlistCreate()
{
	$('#creat_playlist_name').val();
	$('#creat_playlist_type').val("2");
	$('#creat_playlist_level').val("1");
	$('#creat_playlist_quantum').empty();
	$('#creat_playlist_cycle').val(300);
	
	$("#modal_addplaylist").attr("data-type","create_playlist");
	$('#modal_addplaylist').modal('show');
}
//改变需要保存
function itemChange()
{
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==selectplaylistid)
				{
				playlists[i].isSave=1;				
				break;
				}
		}
	}
	sessionStorage.setItem('playlists', JSON.stringify(playlists));
}
//删除排期
function itemDeletebyst(infoid,st)
{	
	if(itemlist!=null && itemlist.length>0)
	{
		for(var l=0;l<itemlist.length;l++)
		{
			var item = itemlist[l];
						
			if(item.infoid==infoid)
			{
				var index = item.offsetlist.indexOf(st);
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
		
	// 删除所所有排期图层	
	$('#info_canvas').removeLayerGroup(infoid+"offset"+st).drawLayers();	
	
}
//创建播放列表
function playlist_creat()
{		
	var listname = $('#creat_playlist_name').val();
	var ScheduleType = $('#creat_playlist_type').val();
	var listlevel = $('#creat_playlist_level').val();
	var listcycle = $('#creat_playlist_cycle').val();
	
	var quantumcount = $('#creat_playlist_quantum option').length;
	var timelist=[];
	var quantums={};			
	
	if(listname==null || listname=='')
		{alertMessage(1, "警告", "列表名称不能为空");return;}
	
	if(listlevel==null || listlevel=='')
	{alertMessage(1, "警告", "列表类型不能为空");return;}
	
	if(listcycle==null || listcycle=='' || listcycle==0)
	{alertMessage(1, "警告", "列表周期不能为空");return;}
	
	if(listlevel==0)
	{
		ScheduleType=2;
		quantums.listcycle =300;
		var tms={};
		tms.lifeAct=0;
		tms.lifeDie=0;				
		timelist.push(tms);
		quantums.timelist = timelist;
	}
	else {
		if(quantumcount<=0)
		{alertMessage(1, "警告", "时间集合不能为空");return;}
		else
			{
			$("#creat_playlist_quantum option").each(function(){
				var lst = this.value.substring(0,this.value.indexOf("---"));
				var let = this.value.substring(this.value.indexOf("---") + 3);
				
				var lstval=parseInt(lst.substring(0,lst.indexOf(":")))*60 + parseInt(lst.substring(lst.indexOf(":") + 1));
				var letval=parseInt(let.substring(0,let.indexOf(":")))*60 + parseInt(let.substring(let.indexOf(":") + 1));
				
				var tms={};
				tms.lifeAct=lstval.toString();
				tms.lifeDie=letval.toString();				
				timelist.push(tms);
			  });
			}
		quantums.listcycle = listcycle;
		quantums.timelist = timelist;	
	}		
	
	var datatype = $("#modal_addplaylist").attr("data-type");
	if(datatype=="create_playlist")
	{
		ajaxCreateInfoList(listname,listlevel,quantums,ScheduleType);	
	}
	else
	{
		var playlists= JSON.parse(sessionStorage.getItem('playlists'));
		var issave=0;
		var pubid = "0";
		if(playlists!=null && playlists.length>0)//存在直接取数据    		
		{
			for(var i=0;i<playlists.length;i++)
			{
				var id=playlists[i].id;
				if(id==selectplaylistid)
					{					
					pubid=playlists[i].pubid;
					
					if(playlists[i].isSave==null || playlists[i].isSave==0)
						{issave=0;}
					else
						{issave=1;}	
					
					if(playlists[i].Playlistname!=listname)
					{issave=1;}
					
					if(playlists[i].Playlistlevel!=listlevel)
					{issave=3;}
					
					if(playlists[i].Timequantum!=JSON.stringify(quantums))
					{issave=3;}
					
					if(JSON.parse(playlists[i].Timequantum).listcycle!=quantums.listcycle)
					{issave=2;}
					
					break;
					}
			}
		}
		
		if(issave==0){$('#modal_addplaylist').modal('hide');return;}
		
		var isRefresh = false,isPublish = false;
		if(issave == 2)
			{isRefresh = true;isPublish = true;}
		if(issave==3)
		{isPublish = true;}
		if(pubid!=0 && pubid!="0")//已发布
			{
			if(isPublish)
				{
				ajaxSaveInfoList(selectplaylistid,listname,listlevel,JSON.stringify(quantums),ScheduleType,itemlist,isRefresh,isPublish);
				}
			else
				{
				//只修改了列表名称
				ajaxUpdatePlaylistName(selectplaylistid,listname);
				}
			}
		else
			{
			ajaxSaveInfoList(selectplaylistid,listname,listlevel,JSON.stringify(quantums),ScheduleType,itemlist,isRefresh,false);
			}	
	}
}
//添加列表按钮
function addinfolistnav(id,listname,pubid)
{
	$('#playlist').append("<option value='"+id+"'>"+listname+"</option>");
}
//ajax 创建列表
function ajaxCreateInfoList(listname,listtype,quantums,ScheduleType)
{
	$.ajax({  
	    url:"/CreatInfoList",          
	    type:"post",  
	    data:{
	    	listname:listname,
	    	groupid:parseInt($("#grouplist").val()),
	    	listtype:listtype,
	    	quantums:JSON.stringify(quantums),
	    	ScheduleType:ScheduleType,
	    	programlist:'',
	    	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
	    	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
	    dataType:"json", 
	    success:function(data)  
	    {       	  
	    	if(data!=null)    		
	    		{
	    			if(data.result=='success')
	    				{
	    					itemlist=[];
	    					
	    					var infoListID=data.infoListID;
								
							addinfolistnav(infoListID,listname,0);
							
							var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							var jpl={};    							
							jpl.id=infoListID;
							
							jpl.Delindex=0;
							jpl.Grpid=infoListID;
							jpl.Playlistlevel=listtype;
							jpl.Scheduletype=ScheduleType;							
							jpl.Playlistname=listname;
							jpl.Programlist=JSON.stringify(itemlist);
							jpl.Timequantum=JSON.stringify(quantums);
							jpl.pubid="0";
							    							    							
							playlists.push(jpl);
							
							sessionStorage.setItem('playlists', JSON.stringify(playlists));
							
							playlistSelectChange(infoListID);
							
							$('#modal_addplaylist').modal('hide');
	    				}
	    			else
	    				{alertMessage(1, "警告", data.resultMessage);}
	    		}
	    	else{alertMessage(1, "警告", "存储列表返回数据为null");}	        	   
	    },  
	    error: function() {
	    	alertMessage(2, "异常", "ajax 函数  CreatInfoList 错误");	    		        
	      }  
	});
}
//ajax 保存列表
function ajaxSaveInfoList(playlistid,listname,listtype,strquantums,ScheduleType,itemlist,isRefresh,isPublish)
{
	$.ajax({  
        url:"/UpdateInfoList",          
        type:"post",  
        data:{
        	listid:playlistid,
        	listname:listname,
        	groupid:parseInt($("#grouplist").val()),
        	listtype:listtype,
        	quantums:strquantums,
        	ScheduleType:ScheduleType,
        	programlist:JSON.stringify(itemlist),
        	isPublish:isPublish,
        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null)    		
        		{
        			if(data.result=='success')
        				{        		
	        				var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							if(playlists!=null && playlists.length>0)//存在直接取数据    		
							{
					    		for(var i=0;i<playlists.length;i++)
								{
					    			var id = playlists[i].id;
					    			if(id == playlistid)
					    				{
					    				if(data.returnid != null)//生成新列表
					    					{
					    						addinfolistnav(data.returnid,listname,0);
					    						playlists.splice(i, 1);					    						
					    						
					    						var jpl={
					    								id:data.returnid,
					    								Playlistname:listname,
					    								pubid:"0",
					    								Playlistlifeact:"",
					    								Playlistlifedie:"",
					    								Playlistlevel:listtype,
					    								Grpid:parseInt($("#grouplist").val()),
					    								Scheduletype:ScheduleType,
					    								Timequantum:strquantums,
					    								Programlist:JSON.stringify(itemlist),
					    								Delindex:0
					    						};
					    	        			
					    						playlists.push(jpl);
												
												sessionStorage.setItem('playlists', JSON.stringify(playlists));
												
					    						playlistSelectChange(data.returnid);
					    						
					    						$("#playlist option[value="+playlistid+"]").remove(); 
					    						
					    						/*
					    						$("#div_"+playlistid).remove();
					    						
												var count = parseInt($('#badge_publish').text()) - 1;
												$('#badge_publish').text(count);
					        					
												$('#modal_save').modal('hide');
												*/
												$('#modal_addplaylist').modal('hide');					    						
					    					}
					    				else
					    					{
						    				playlists[i].Playlistlevel=listtype;
						    				playlists[i].Scheduletype=ScheduleType;					    				
						    				if(playlists[i].Playlistname!=listname)
						    					{
						    					//$("#pl_"+playlistid).text(listname);
						    					$("#playlist option[value="+playlistid+"]").text(listname);
						    					playlists[i].Playlistname=listname;
						    					}
						    				
						    				playlists[i].Programlist=JSON.stringify(itemlist);
						    				playlists[i].Timequantum=strquantums;
						    				/*
						    				if(playlists[i].pubid!==0 && playlists[i].pubid!=="0")
					    					{
						    					var count = parseInt($('#badge_draft').text()) + 1;
												if(count <= 0){count = 0;}
												$('#badge_draft').text(count);
												
												$('#playlist_draft').append($("#div_" + playlistid));
												var count = parseInt($('#badge_publish').text()) - 1;
												$('#badge_publish').text(count);
					        					
												$('#modal_save').modal('hide');
												$('#modal_addplaylist').modal('hide');
												
												playlists[i].pubid = "0";
					    					}
						    				*/
						    				
			    							if(data.pubid!=null)
						    				{
			    								//保存后发布了
			    								playlists[i].pubid=data.pubid;			  										
						    				}
			    							else
		    								{playlists[i].pubid = "0";}
			    							
			    							
			    							playlists[i].isSave=0;
			    										
			    							if(isRefresh)
		    								{
				    							DrawBackground(JSON.parse(strquantums).listcycle);	
				    							clearlayeritem();
				    							if(selectinfoid==0)
				    							{GetitemList('','');}
				    							else
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
			    						        			GetitemList(lifeAct,lifeDie);
			    						        			break;
			    											}
			    									}
			    								}
		    								}
					    					}
					    				alertMessage(0, "成功", "修改成功");		    							
					    				break;
					    				}
								}
							}
						
							sessionStorage.setItem('playlists', JSON.stringify(playlists));
							$('#modal_addplaylist').modal('hide');
        				}
        			else
        				{alertMessage(1, "警告", data.resultMessage);}
        		}
        	else{alertMessage(1, "警告", "存储列表返回数据为null");}	        	   
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  UpdateInfoList 错误");        	            
          }  
    });
}
//ajax 修改列表名称
function ajaxUpdatePlaylistName(playlistid,listname)
{
	$.ajax({  
	    url:"/UpdatePlaylistName",          
	    type:"post",  
	    data:{
	    	playlistid:playlistid,
	    	listname:listname,
	    	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
	    	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
	    dataType:"json", 
	    success:function(data)  
	    {       	  
	    	if(data!=null)    		
	    		{
	    			if(data.result=='success')
	    				{	    						    						    												
							var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							if(playlists!=null && playlists.length>0)//存在直接取数据    		
							{
					    		for(var i=0;i<playlists.length;i++)
								{
					    			var id = playlists[i].id;
					    			if(id == playlistid)
					    				{
					    				$("#playlist option[value="+playlistid+"]").text(listname);
					    				playlists[i].Playlistname=listname;
					    				}
								}
							}
							sessionStorage.setItem('playlists', JSON.stringify(playlists));													
							$('#modal_addplaylist').modal('hide');
	    				}
	    			else
	    				{alertMessage(1, "警告", data.resultMessage);}
	    		}
	    	else{alertMessage(1, "警告", "存储列表返回数据为null");}	        	   
	    },  
	    error: function() {  
	    	alertMessage(2, "异常", "ajax 函数  UpdatePlaylistName 错误");	    	        
	      }  
	});
}
//串口回传
function SendCallback(SN,infocodelist,i)
{
	var timesRun = 0,reSend=0;
	var interval = setInterval(function(){
		if(infocodelist.length > i)
		{
		    timesRun += 1;  
		    if(timesRun >= 10){ 
		    	sendinfoDataCallback(newSN,infocodelist,i,SendCallback(newSN,infocodelist,i));
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
		}
		else{clearInterval(interval);}
	}, 500);
}
//串口发布广告通过id
function playlistSerialPublishbyid(playlistid)
{				
	$('#progress').css('height','20px');
					
	if(itemlist == null || itemlist.length<=0)
	{	
	alertMessage(1, "警告", "排期数据为空不能发布");
	return;
	}
		
	var listtype = "";				
	var strquantums="";
	var ScheduleType=1;
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==playlistid)
				{								
				listtype=playlists[i].Playlistlevel	
				ScheduleType=playlists[i].Scheduletype					
				strquantums=playlists[i].Timequantum				
				}
		}
	}
	
	$.ajax({  
        url:"/getPublishListbyTemp", 
        data:{
        	listtype:listtype,
        	quantums:strquantums,
        	ScheduleType:ScheduleType,
        	programlist:JSON.stringify(itemlist),
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
function SendSerialData(playlistid)
{
	var SerialPort = localStorage.getItem("SerialPort");
	if(SerialPort!=null && SerialPort!="")
		{
		intiWebSocket();
		        		        		
		ws.addEventListener('open', function () { 
		
			var jsonSerialPort;
			
			try
			{
				jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));
			}
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
	        			playlistSerialPublishbyid(playlistid);
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
//串口发布广告通过id
function infoSerialPublishbyid(infoid)
{				
	$('#progress').css('height','20px');
						
	$.ajax({  
        url:"/getinfoListbyinfoid", 
        data:{
        	infosn:infoid,
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

function infoSendSerialData(infoid)
{
	var SerialPort = localStorage.getItem("SerialPort");
	if(SerialPort!=null && SerialPort!="")
		{
		intiWebSocket();
		        		        		
		ws.addEventListener('open', function () { 
		
			var jsonSerialPort;
			
			try
			{
				jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));
			}
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
        		var ArrayTable=[];
    			if(data.itemlist!=null && data.itemlist.length>0)
    				{       
    					for(var i=0;i<data.itemlist.length;i++)
    						{
    							var jitem=data.itemlist[i];        							     							
    							var delindex =jitem.delindex;        							
    							if(delindex==0)
    								{    
    								var itemstyle="";
    							    if(jitem.itemstyle!=null && jitem.itemstyle!="")
    						    	{    		
    							    	var arrayPlaytype=['静止','左滚','上滚','左拉帘','右拉帘','左右拉帘','上拉帘','下拉帘','上下拉帘','渐亮','渐灭','左移进','右移进','左右移进','隔行左右对进','上移进','下移进','上下隔行移进','闪烁','百叶','放大 左上角->全屏','放大 中间->全屏'];
    							    					          								          								      
    							    	var itemstyleJson = JSON.parse(jitem.itemstyle);
    							    	var linkmove = "联动:否";
    							    	if(itemstyleJson.linkmove==1)
    							    		{linkmove = "联动:是"}    							    	
    							    	var playtype = "播放方式:" + arrayPlaytype[parseInt(itemstyleJson.playtype)];
    							    	var playspeed = "速度:" + itemstyleJson.playspeed;
    							    	var rollstop = "停留方式:最终";
    							    	if(itemstyleJson.rollstop==1)
    							    		{rollstop = "停留方式:每屏";}
    							    	var stoptime = "停留时间:" + itemstyleJson.stoptime;
    							    	var looptime = "次数:" + itemstyleJson.looptime;
    							    	var gamma = "对比度:" + itemstyleJson.gamma;
    							    	var playtime = "时长:" + itemstyleJson.playtime;
    						    	}    						    
    								
    							    itemstyle=playtype+";"+playspeed+";"+looptime+";"+stoptime+";"+rollstop+";"+gamma+";"+linkmove+";"+playtime;
    							    
    								item={
										itemContext:jitem.itemcontext,
        								itemLocationSize:jitem.itemleft+","+jitem.itemtop+","+jitem.itemwidth+","+jitem.itemheight,
        								itemStyle:itemstyle		
    								};  
    								ArrayTable.push(item);	
    								}
    						}
    				}
    						
				$("#info_details_table").bootstrapTable('load', ArrayTable);
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