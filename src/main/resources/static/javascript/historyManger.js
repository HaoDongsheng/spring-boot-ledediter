$(function(){	
	
//	var url = "http://192.168.1.136:8011/不良词汇表.txt";	
//	var htmlobj= $.ajax({url:url,async:false});
	 
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
    
	$("#history_month").datetimepicker({
		bootcssVer: 3,
        pickerPosition: "bottom-left",
		startView: 3,  //起始选择范围	    
	    minView:3, //最小选择范围
	    //todayHighlight : true,// 当前时间高亮显示
	    autoclose : true,// 选择时间后弹框自动消失
	    format : 'yyyy-mm',// 时间格式
	    language : 'zh-CN',// 汉化
	    //todayBtn:"linked",//显示今天 按钮
	    //clearBtn : true,// 清除按钮，和今天 按钮只能显示一个
        endDate : new Date()
	});
	
	$("#search_lifeAct").datetimepicker({
		bootcssVer: 3,
        pickerPosition: "bottom-left",
		//startView: 3,  //起始选择范围	    
	    minView:"month", //最小选择范围
	    todayHighlight : true,// 当前时间高亮显示
	    autoclose : true,// 选择时间后弹框自动消失
	    format : 'yyyy-mm-dd',// 时间格式
	    language : 'zh-CN',// 汉化
	    todayBtn:"linked",//显示今天 按钮
	});
	
	$("#search_lifeDie").datetimepicker({
		bootcssVer: 3,
        pickerPosition: "bottom-left",
		//startView: 3,  //起始选择范围	    
	    minView:"month", //最小选择范围
	    todayHighlight : true,// 当前时间高亮显示
	    autoclose : true,// 选择时间后弹框自动消失
	    format : 'yyyy-mm-dd',// 时间格式
	    language : 'zh-CN',// 汉化
	    todayBtn:"linked",//显示今天 按钮
	});
	
	$('#history_month').val(getNowFormatMonth());
	
	$('#search_lifeAct').val(getNowFormatDate());
    $('#search_lifeDie').val(getNowFormatDate());
	
	getGroup();
	
	initBar();
	
	initBTabel();
	
	$('#grouplist').change(function() {
	});
		
	$('#btn_table_search').click(function() {
		var date = $('#history_month').val();
		RefreshData($('#grouplist').val().trim(),date.trim() + "-01",date.trim() + "-31");
	});
	$('#btn_table_change').click(function() {
		if($('#charts_history').css('display') != 'none'){
			$('#charts_history').css('display','none');
			$('#history_info_table').parents('.table').css('display','block');
		}
		else {
			$('#charts_history').css('display','block');
			$('#history_info_table').parents('.table').css('display','none');
		}
	});
	
	$('#btn_table_highlevel').click(function() {
		$('#modal_search_date').modal('show');
	});
	
	$('#btn_search_date').click(function() {
		RefreshData($('#grouplist').val().trim(),$('#search_lifeAct').val().trim(),$('#search_lifeDie').val().trim());
		$('#modal_search_date').modal('hide');
	});
});

var spinner;

function initBar()
{
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('charts_history'));
    
    var labelOption = {
    	    show: true,
    	    position: 'insideBottom',
    	    distance: 15,
    	    align: 'left',
    	    verticalAlign: 'middle',
    	    rotate: 90,
    	    formatter: '{c}  {name|{a}}',
    	    fontSize: 16,
    	    rich: {
    	        name: {
    	            textBorderColor: '#fff'
    	        }
    	    }
    	};
    
    option = {
    	    color: ['#003366', '#006699', '#4cabce'],
    	    tooltip: {
    	        trigger: 'axis',
    	        axisPointer: {
    	            type: 'shadow'
    	        }
    	    },
    	    legend: {
    	        data: ['总时长', '播放次数', '单次时长']
    	    },
    	    toolbox: {
    	        show: true,
    	        orient: 'vertical',
    	        left: 'right',
    	        top: 'center',
    	        feature: {
    	            mark: {show: true},
    	            dataView: {show: true, readOnly: false},
    	            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
    	            restore: {show: true},
    	            saveAsImage: {show: true}
    	        }
    	    },
    	    xAxis: [
    	        {
    	            type: 'category',
    	            axisTick: {show: false},
    	            data: ['2012', '2013', '2014', '2015', '2016']
    	        }
    	    ],
    	    yAxis: [
    	        {
    	            type: 'value'
    	        }
    	    ],
    	    series: [
    	        {
    	            name: '总时长',
    	            type: 'bar',
    	            barGap: 0,
    	            label: labelOption,
    	            data: [320, 332, 301, 334, 390]
    	        },
    	        {
    	            name: '播放次数',
    	            type: 'bar',
    	            label: labelOption,
    	            data: [220, 182, 191, 234, 290]
    	        },
    	        {
    	            name: '单次时长',
    	            type: 'bar',
    	            label: labelOption,
    	            data: [150, 232, 201, 154, 190]
    	        }
    	    ]
    	};
    
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);    
}
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
								
								RefreshData(grpid,getNowFormatMonth() + "-01",getNowFormatMonth() + "-31");
								
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								RefreshData(grpid,getNowFormatMonth() + "-01",getNowFormatMonth() + "-31");
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
				RefreshData(grpid,getNowFormatMonth() + "-01",getNowFormatMonth() + "-31");
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid == grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					RefreshData(grpid,getNowFormatMonth() + "-01",getNowFormatMonth() + "-31");
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
	$('#history_info_table').bootstrapTable('destroy');		    
    $('#history_info_table').bootstrapTable({   
    	//url: '/gethistoryinfobypageNum',//请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: "application/x-www-form-urlencoded",
        //toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        //pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式    
        sortName:"infoid",
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        //queryParams:queryinfoParams,			//请求服务器时所传的参数        
        //pageNumber:1,                       //初始化加载第一页，默认第一页
        //pageSize: 10,                       //每页的记录行数（*）
        //pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 600,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        //导出功能设置（关键代码）
        exportDataType:'basic',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
        showExport: true,  //是否显示导出按钮
        buttonsAlign:"right",  //按钮位置
        exportTypes:['excel', 'txt'],  //导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
        exportOptions:{
            //ignoreColumn: [0],  //忽略某一列的索引
            fileName: '广告统计',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '广告统计',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
//            onCellHtmlData: function (cell, row, col, data) { 
//            	if(row>0 && col==4)
//            		{            		
//            		var spanObj = $(data)[0];
//            		var result = "";            		
//            		for(var i=0;i<spanObj.innerText.length;i++)
//            			{
//            			if(i!=0 && i%8==0)
//        				{result +=","}
//            			result +=spanObj.innerText[i];            			
//            			}
//            		return result;
//            		} 
//            	else
//            		{return data;}
//            }
        },
        columns: [{
            field: 'infoid',
            title: '广告id',
            visible: false
            
        },{
            field: 'infoName',
            title: '广告名称',
            sortable:true,
            width:150,
            "class":'colStyle',
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
        }, {
            field: 'items',
            title: '显示内容',
            sortable:true,
            formatter: function (value, row, index) {            	
                return "<div style='max-height: 100px;max-width: 400px;overflow:auto;'>" + value + "</div>";
            }
        },{
            field: 'offsetlists',
            title: '排期详情',
            sortable:true,
            visible: true,	
            width:200,
            height:60,
            formatter: function (value, row, index) {            		            	
            	var jArray = value;
            	
            	var Option = "";
            	
            	for(var i=0;i<jArray.length;i++)
    			{            		            		
            		var jObject = jArray[i];
            		            		            		
	            	var lifeAct = jObject.pllifeAct;
	            	var lifeDie = jObject.pllifeDie;
	            	var title = lifeAct + "~" + lifeDie;
	            	var context = getDisplayTimes(jObject);;
	            	if(context!=null && context!="")
	            		{
	            		Option +="<Strong style='color:#0000ff'>"+title+"</Strong></br><a title='"+context+"' style='color:#000000'>"+context+"</a></br>";
	            		}
    			}
//            	return "<span title="+Option+">"+Option+"</span>"
            	overflow:scroll;
    			return "<div style='max-height: 100px;max-width: 400px;overflow:auto;'>" + Option + "</div>";
//            	return "<div style='max-height: 100px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>" + Option + "</div>";
            }
        }]
    }); 	    
}

//排期数据转换成时分秒数据
function getDisplayTimes(jObject) {
	var lifeAct = jObject.pllifeAct;
	var lifeDie = jObject.pllifeDie;
	var offsetlist = jObject.offsetlist;
	var listcycle = parseInt(jObject.listcycle);
	var timelist = jObject.timelist;
	
	var returnlist = []; 
	for (var k = 0; k < timelist.length; k++) {
		
		var tact = parseInt(timelist[k].lifeAct);
		var tdie = parseInt(timelist[k].lifeDie);
		if (tdie == 0) {
			tdie = 24 * 60;
		}
		
		for (var i = 0; i < offsetlist.length; i++) {
			for (var j = tact * 60; j < tdie * 60; j += listcycle) {
				// Collections.sort(timeArray);
				var val = j + parseInt(offsetlist[i]);
				
				if(lifeAct.substring(0,lifeAct.indexOf(" ")) == lifeDie.substring(0,lifeDie.indexOf(" ")))
				{
					var startTime = lifeAct.substring(lifeAct.indexOf(" ") + 1).split(":");
					var endTime = lifeDie.substring(lifeDie.indexOf(" ") + 1).split(":");
					var startVal=parseInt(startTime[0])*3600 + parseInt(startTime[1])*60 + parseInt(startTime[2]);
					var endVal=parseInt(endTime[0])*3600 + parseInt(endTime[1])*60 + parseInt(endTime[2]);
					if(val>=startVal && val<=endVal)
						{returnlist.push(formatSeconds(val));}
				}
				else {
					returnlist.push(formatSeconds(val));
				}
				
			}
		}
	}
	return returnlist.sort().join(",");
}

//获取数据 
function RefreshData(groupid,startDate,endDate)
{
	//请求时spinner出现
	var target;
	
	if($('#charts_history').css('display') != 'none'){
		target = $('#charts_history').parents('.col-md-12').get(0);
	}
	else {
		target = $('#history_info_table').parents('.col-md-12').get(0);
	}
	
    spinner.spin(target);
    
	var myChart = echarts.init(document.getElementById('charts_history'));
	$.ajax({  
        url:"/RefreshHistoryData", 
        data:{
        	groupid:groupid,
        	startDate:startDate,
        	endDate:endDate,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname,
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.total>0)
        		{      		
        		var ArrayTable = [];

        		var infoNames = [];
        		var totaltimelengths = [];
        		var offsetlistCounts = [];
        		var timelengths = [];
        		for(var i=0;i<data.rows.length;i++)
        			{
        			var item=data.rows[i];
        			var infoid = item.infoid;
        			var infoName = item.infoName;
        			var LifeAct = item.LifeAct;
        			var LifeDie = item.LifeDie;
        			var offsetlistCount = item.offsetlistCount;
        			var offsetlist = item.offsetlist;
        			var offsetlists = item.offsetlists;
        			var timelength = item.timelength;
        			
        			infoNames.push(infoName);
        			totaltimelengths.push(offsetlistCount * timelength);
        			offsetlistCounts.push(offsetlistCount);
        			timelengths.push(timelength);
        			
        			ArrayTable.push(item);
        			
        			}        		        		
        		 
        		 myChart.setOption({
        			 	xAxis: [
        			        {
        			            data: infoNames
        			        }
        			    ],
        	            series: [{
        	            	data: totaltimelengths
        	            }, {
        	                data: offsetlistCounts
        	            }, {
        	            	data: timelengths
        	            }]
        	        });
        		 
        		 $("#history_info_table").bootstrapTable('load', ArrayTable);
        		}  
        	else
        		{
        		 myChart.setOption({
     			 	xAxis: [
     			        {
     			            data: []
     			        }
     			    ],
     	            series: [{
     	            	data: []
     	            }, {
     	                data: []
     	            }, {
     	            	data: []
     	            }]
     	        });
    		 initBTabel();    		
        		}        
        	//关闭spinner  
            spinner.spin();
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  RefreshHistoryData 错误"); 
        	//关闭spinner  
            spinner.spin();
          }  
    });	
}
