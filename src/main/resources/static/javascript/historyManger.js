$(function(){	
	
//	var url = "http://192.168.1.136:8011/不良词汇表.txt";	
//	var htmlobj= $.ajax({url:url,async:false});
	 
	$("#history_lifeAct").datetimepicker({
		format: 'yyyy-mm-dd',
		bootcssVer: 3,
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		autoclose: true,
        todayBtn: true,
        language: 'zh-CN',
        pickerPosition: "bottom-left"
	});
	
	
	$("#history_lifeDie").datetimepicker({
		format: 'yyyy-mm-dd',
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		autoclose: true,
        todayBtn: true,
        language: 'zh-CN',
        pickerPosition: "bottom-left"
	});
	
	$('#history_lifeAct').val(getNowFormatDate());
    $('#history_lifeDie').val(getNowFormatDate());
	
	getGroup();
	
	initBTabel();
	
	$('#select_type').change(function() {
		initBTabel();	
	});
	
	$('#grouplist').change(function() {
		var select_type = $('#select_type').val()
		if(select_type==1)
		{$('#history_table').bootstrapTable('refresh', {url: '/gethistorybypageNum'});}
		else {
			$('#history_info_table').bootstrapTable('refresh', {url: '/gethistoryinfobypageNum'});
		}
	});
	
	$('#history_lifeAct').change(function() {
		var select_type = $('#select_type').val()
		if(select_type==1)
		{$('#history_table').bootstrapTable('refresh', {url: '/gethistorybypageNum'});}
		else {
			$('#history_info_table').bootstrapTable('refresh', {url: '/gethistoryinfobypageNum'});
		}
	});
	
	$('#history_lifeDie').change(function() {
		var select_type = $('#select_type').val()
		if(select_type==1)
		{$('#history_table').bootstrapTable('refresh', {url: '/gethistorybypageNum'});}
		else {
			$('#history_info_table').bootstrapTable('refresh', {url: '/gethistoryinfobypageNum'});
		}	
	});
	$('#select_date').change(function() {
		var select_date=$('#select_date').val();
		var itemlist = JSON.parse(sessionStorage.getItem('historyitemlist'));
		var ArrayTable=[];
		if(itemlist!=null && itemlist.length>0)
		{       
			for(var i=0;i<itemlist.length;i++)
				{
					var jitem=itemlist[i];    
					
					var act=jitem.lifeAct;
					var die=jitem.lifeDie;					
					if(act+"~"+die == select_date)
						{											
						var totalTimeLength=jitem.totalTimeLength;
						var advlist=jitem.advlist;    							
						for(var j=0;j<jitem.advlist.length;j++)
							{
							var advitem =jitem.advlist[j];
							var infoid=advitem.infoid;
							var timelength=advitem.timelenght;
							var offsetlist=advitem.offsetlist;
							for(var z=0;z<offsetlist.length;z++)
								{
									var offset=offsetlist[z];
									
									item={
										infosn:infoid,
										infoname:advitem.infoname,
										timelength:timelength,
										offsetval:offset
									};    
									ArrayTable.push(item);	
								}
							}
						}					  								
				}
		}
			
	$("#info_details_table").bootstrapTable('load', ArrayTable);
	});
});

//获取分组信息
function getGroup()
{
	var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	var selectGrpid = sessionStorage.getItem('selectgrpid');
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
								//getadvListbyGrpid(grpid);
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								//getadvListbyGrpid(grpid);
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
							}
	        			}
	        			
	        			if(grpsinfo.length<=1)
	        			{
	        				$('#grouplist').css("display","none");
	        			}	        			
	        			
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
			$('#grouplist').css("display","block");
			}
			else
			{$('#grouplist').css("display","none");}
		
			for(var i=0;i<grpsinfo.length;i++)
			{
				var grpid=grpsinfo[i].grpid;
				var grpname = grpsinfo[i].grpname;
				if(selectGrpid==0 || selectGrpid==null)
				{
				if(i==0)
				{
				$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
				//getadvListbyGrpid(grpid);
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid == grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					//getadvListbyGrpid(grpid);
					}
    				else
					{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
				}
			}
			
			if(grpsinfo.length<=1)
			{
				$('#grouplist').css("display","none");
			}
		}
}
//初始化表格
function initBTabel()
{
	var select_type = $('#select_type').val()
	if(select_type==1)
		{
//		$('#history_table').css('display','block');
//		$('#history_info_table').css('display','none');
		$('#history_info_table').bootstrapTable('destroy');
		$('#history_table').bootstrapTable('destroy');
	    $('#history_table').bootstrapTable({   
	    	url: '/gethistorybypageNum',//请求后台的URL（*）
	        method: 'post',                      //请求方式（*）
	        contentType: "application/x-www-form-urlencoded",
	        toolbar: '#toolbar',                //工具按钮用哪个容器
	        striped: true,                      //是否显示行间隔色
	        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	        pagination: true,                   //是否显示分页（*）
	        sortable: true,                     //是否启用排序
	        sortOrder: "asc",                   //排序方式    
	        sortName:"publishDate",
	        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	        queryParams:queryParams,			//请求服务器时所传的参数        
	        pageNumber:1,                       //初始化加载第一页，默认第一页
	        pageSize: 10,                       //每页的记录行数（*）
	        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	        strictSearch: true,
	        showColumns: true,                  //是否显示所有的列
	        showRefresh: true,                  //是否显示刷新按钮
	        minimumCountColumns: 2,             //最少允许的列数
	        clickToSelect: true,                //是否启用点击选中行
	        //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	        cardView: false,                    //是否显示详细视图
	        detailView: false,                   //是否显示父子表onEditableSave
	      //导出功能设置（关键代码）
	        exportDataType:'basic',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
	        showExport: true,  //是否显示导出按钮
	        buttonsAlign:"right",  //按钮位置
	        exportTypes:['excel', 'txt'],  //导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
	        columns: [{
	            field: 'playlistsn',
	            title: '列表id',
	            visible: false
	            
	        },{
	            field: 'publishDate',
	            title: '开始日期',
	            sortable:true
	            
	        }, {
	            field: 'deleteDate',
	            title: '结束日期',
	            sortable:true
	          
	        }, {
	            field: 'playlistlevel',
	            title: '列表类型',
	            formatter: function (value, row, index) {            	
	            	var playlistlevel = value;
	            	var playlistlevelString="普通列表";
	            	switch (playlistlevel) {
					case 0:
						playlistlevelString="转场列表";
						break;
					case 1:
						playlistlevelString="普通列表";
						break;
					case 2:
						playlistlevelString="通知列表";
						break;				
					}
	            	            	
	                return playlistlevelString;
	            }
	        }, {
	            field: 'timequantum',
	            title: '时间段', 
	            formatter: function (value, row, index) {            	
	            	var timeList = value;
	            	
	            	var option;            	
	            	   
	            	var timeArr=null;
	            	if(timeList != null)
	            		{
	            		timeArr = JSON.parse(timeList);
	            		}
	   
	            	var headOption = "";            	    
	            	
	            	if(timeArr!=null)
	            		{
		            	for(var i=0;i<timeArr.length;i++)
		    			{
		            		var lifeAct=timeArr[i].lifeAct;
		            		var lifeDie=timeArr[i].lifeDie;
		            		var txt =lifeAct+"~"+lifeDie;
			    			headOption = headOption + "<option value='"+txt+"'>"+txt+"</option>";                    			
		    			}
	            		}
	            	option = '<select class="form-control">'+ headOption + '</select>';
	            	            	
	                return option;
	            }
	//            sortable:true         
	        }, {
	            field: 'templateCycle',
	            title: '模板周期',            
	//            sortable:true              
	        },{
	            field: 'infosnlist',
	            title: '包含广告',             
	            formatter: function (value, row, index) {            	
	            	var AdIdList = value;
	            	
	            	var option;            	
	            	   
	            	var advArr=AdIdList.split(',');
	   
	            	var headOption = "";            	    
	            	
	            	if(advArr!=null)
	            		{
		            	for(var i=0;i<advArr.length;i++)
		    			{
		            		var txt=advArr[i].trim();	            		
			    			headOption = headOption + "<option value='"+txt+"'>"+txt+"</option>";                    			
		    			}
	            		}
	            	option = '<select class="form-control">'+ headOption + '</select>';
	            	            	
	                return option;
	            }
	//            class:'colStyle',
	//            formatter:function (value, row, index) { 
	//            	if(row.infosnlist!=null)
	//            		{
	//	            	var values = row.infosnlist;//获取当前字段的值
	//	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	//	                //&nbsp;代替
	//	                values = values.replace(/\s+/g,'&nbsp;')
	//	                values = values.replace(/\},/,'},\n');//换行
	//	                return "<span title="+values+">"+row.infosnlist+"</span>"
	//            		}
	//            	else {
	//            		return "<span title=''></span>"
	//				}
	//            }         
	        }, {
	        	field: 'operate',
	        	title: '操作',
	        	align: 'center',
	        	events: operateEvents,
	        	formatter: operateFormatter
	        }]
	    }); 
	    
	    $('#history_table').colResizable();
	    
	    // 得到查询的参数
	    function queryParams(params) {	    	
	        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的            
	            pageSize: params.limit,                         //页面大小
	            pageNum: (params.offset / params.limit) + 1,   //页码
	            lifeAct: $('#history_lifeAct').val().trim(),
	            lifeDie: $('#history_lifeDie').val().trim(),
	            sort: params.sort,      //排序列名  
	            sortOrder: params.order,//排位命令（desc，asc）             
	            adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname,
	            groupid: parseInt($('#grouplist').val().trim())
	        };
	        return temp;
	    };
	    
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
	        height: 300,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	        cardView: false,                    //是否显示详细视图
	        detailView: false,                   //是否显示父子表onEditableSave
	        //导出功能设置（关键代码）
	        exportDataType:'all',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
	        showExport: true,  //是否显示导出按钮
	        buttonsAlign:"right",  //按钮位置
	        exportTypes:['excel'],  //导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
	        columns: [{
	            field: 'infosn',
	            title: '广告序号',
	        	visible: false
	        }, {
	            field: 'infoname',
	            title: '广告名称'
	            
	        }, {
	            field: 'timelength',
	            title: '时长'         
	        }, {
	            field: 'offsetval',
	            title: '偏移量'         
	        }]
	    });
		}
	else {
		$('#history_info_table').bootstrapTable('destroy');		
	    $('#history_table').bootstrapTable('destroy');
	    $('#history_info_table').bootstrapTable({   
	    	url: '/gethistoryinfobypageNum',//请求后台的URL（*）
	        method: 'post',                      //请求方式（*）
	        contentType: "application/x-www-form-urlencoded",
	        toolbar: '#toolbar',                //工具按钮用哪个容器
	        striped: true,                      //是否显示行间隔色
	        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	        pagination: true,                   //是否显示分页（*）
	        sortable: true,                     //是否启用排序
	        sortOrder: "asc",                   //排序方式    
	        sortName:"infoid",
	        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
	        queryParams:queryinfoParams,			//请求服务器时所传的参数        
	        pageNumber:1,                       //初始化加载第一页，默认第一页
	        pageSize: 10,                       //每页的记录行数（*）
	        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	        strictSearch: true,
	        showColumns: true,                  //是否显示所有的列
	        showRefresh: true,                  //是否显示刷新按钮
	        minimumCountColumns: 2,             //最少允许的列数
	        clickToSelect: true,                //是否启用点击选中行
	        //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	        cardView: false,                    //是否显示详细视图
	        detailView: false,                   //是否显示父子表onEditableSave
	        //导出功能设置（关键代码）
	        exportDataType:'basic',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
	        showExport: true,  //是否显示导出按钮
	        buttonsAlign:"right",  //按钮位置
	        exportTypes:['excel', 'txt'],  //导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
	        columns: [{
	            field: 'infoid',
	            title: '广告id',
	            visible: false
	            
	        },{
	            field: 'infoName',
	            title: '广告名称',
	            sortable:true,
	            width:150,
	            class:'colStyle',
	            formatter:function (value, row, index) { 
	            	if(row.infoName!=null)
	            		{
		            	var values = row.infoName;//获取当前字段的值
		                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
		                //&nbsp;代替
		                values = values.replace(/\s+/g,'&nbsp;')
		                values = values.replace(/\},/,'},\n');//换行
		                return "<span title="+values+">"+row.infoName+"</span>"
	            		}
	            	else {
	            		return "<span title=''></span>"
					}
	            }       
	        }, {
	            field: 'timelength',
	            title: '播放时长(秒)',
	            sortable:true
	          
	        }, {
	            field: 'offsetlistCount',
	            title: '播放次数',
	            sortable:true   
	        }, {
	            field: 'totaltimelength',
	            title: '总播放时长',
	            sortable:true,
	            formatter: function (value, row, index) {            	
	            	var totalSecond = row.offsetlistCount * row.timelength;
	            	var hour = parseInt(totalSecond/3600);
	            	var minute = parseInt(totalSecond%3600/60);
	            	var second = parseInt(totalSecond%3600%60);
	            	var returnString = "";
	            	if(hour!=0){returnString += hour + "小时";}
	            	if(minute!=0){returnString += minute + "分";}
	            	if(second!=0){returnString += second + "秒";}
	                return returnString;
	            }
	        },{
	            field: 'offsetlist',
	            title: '排期详情',
	            sortable:true,
	            visible: true,
	            formatter: operateFormatter1,
	            events:operateEvents
	        },{
	            field: 'offsets',
	            title: '排期详情',
	            sortable:true,
	            visible: true,
	            formatter: function (value, row, index) {            		            	
	            	var advArr = value;
	            	
	            	var option;    	            		    	            
	         	   
	            	var headOption = "";            	    
	            	
	            	if(advArr!=null)
	            		{
		            	for(var i=0;i<advArr.length;i++)
		    			{
		            		var txt=advArr[i];	            		
			    			headOption = headOption + "<option value='"+txt+"'>"+txt+"</option>";                    			
		    			}
	            		}
	            	option = '<select class="form-control">'+ headOption + '</select>';
	            	            	
	                return option;
	            }	            	            
	        }]
	    }); 
	    
	    $('#history_table').colResizable();
	    
	 // 得到查询的参数
	    function queryinfoParams(params) {	    	
	        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的            
	            pageSize: 10,//params.limit,                         //页面大小
	            pageNum: 1,//(params.offset / params.limit) + 1,   //页码
	            lifeAct: $('#history_lifeAct').val().trim(),
	            lifeDie: $('#history_lifeDie').val().trim(),
	            sort: params.sort,      //排序列名  
	            sortOrder: params.order,//排位命令（desc，asc）             
	            adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname,
	            groupid: parseInt($('#grouplist').val().trim())
	        };
	        return temp;
	    };
	}    
}

function operateFormatter(value, row, index) {
    return [
         '<a class="details" href="javascript:void(0)" style="margin:0px 5px;" title="详情">',
            '<i class="fa fa-exclamation-circle"></i>详情',
         '</a>'
    ].join('');
}

function operateFormatter1(value, row, index) {
	var offsetlist = row.offsetlist;
	
	var option;            	
	var joffsetlist = JSON.parse(offsetlist);	            		   
	var headOption = "";            	    
	
	if(joffsetlist!=null)
		{
    	for(var i=0;i<joffsetlist.length;i++)
		{
    		var lifeAct=joffsetlist[i].pllifeAct.trim();
    		var lifeDie=joffsetlist[i].pllifeDie.trim();
    		var txt = lifeAct+"~"+lifeDie
			headOption = headOption + "<option value='"+txt+"'>"+txt+"</option>"; 
    		if(i==0)
    			{
    			row.offsets=joffsetlist[i].offsetlist;
    			}
		}
		}
	option = '<select class="form-control selectchange">'+ headOption + '</select>';
	            	
    return [option].join('');
}

window.operateEvents = {
    'click .details': function (e, value, row, index) {          	
    	$("#modal_details").attr("data-type",row.playlistsn);
    	$("#modal_details").modal('show'); 
    	getplaylistinfo(row.playlistsn);
    },
    'change .selectchange': function (e, value, row, index) {          	    	    	
    	var offsetlist = row.offsetlist;
    	         	
    	var joffsetlist = JSON.parse(offsetlist);	            		            	    
    	row.offsets=value;
    	if(joffsetlist!=null)
		{
	    	for(var i=0;i<joffsetlist.length;i++)
			{
	    		if(i==e.currentTarget.selectedIndex)
	    			{
	    			row.offsets=joffsetlist[i].offsetlist;
	    			break;
	    			}
			}
		}
    }  
};

//获取显示项
function getplaylistinfo(playlistsn)
{	
	$.ajax({  
        url:"/getplaylistinfo",         
        data:{
        	playlistsn:playlistsn,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{         
        		$('#select_date').empty();
        		var ArrayTable=[];
        		sessionStorage.setItem('historyitemlist', JSON.stringify(data.itemlist));
    			if(data.itemlist!=null && data.itemlist.length>0)
    				{       
    					for(var i=0;i<data.itemlist.length;i++)
    						{
    							var jitem=data.itemlist[i];    
    							
    							var act=jitem.lifeAct;
    							var die=jitem.lifeDie;
    							var sel="";
    							if(i==0)
    								{
    								sel="selected";
    							
	    							var totalTimeLength=jitem.totalTimeLength;
	    							var advlist=jitem.advlist;    							
	    							for(var j=0;j<jitem.advlist.length;j++)
	    								{
	    								var advitem =jitem.advlist[j];
	    								var infoid=advitem.infoid;
	    								var timelength=advitem.timelenght;
	    								var offsetlist=advitem.offsetlist;
	    								for(var z=0;z<offsetlist.length;z++)
		    								{
		    									var offset=offsetlist[z];
		    									
		    									item={
	    											infosn:infoid,
	    											infoname:advitem.infoname,
	    											timelength:timelength,
	    											offsetval:offset
	    										};    
	    										ArrayTable.push(item);	
		    								}
	    								}
    								}
    							$('#select_date').append("<option "+sel+" value='"+act+"~"+die+"'>"+act+"~"+die+"</option>");   								
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
        	alertMessage(2, "异常", "ajax 函数  getplaylistinfo 错误");        	         
          }  
    });
}