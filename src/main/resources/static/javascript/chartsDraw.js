var myChart, myChartPie;
var minDate='',maxDate='';
var interval = null;
$(function(){	
	getGroup();
	
	initPie();
	initLine();
        
	getUpdateRate();	
    
    $('#btn_select_date').click(function(){
    	var startDate = $('#charts_lifeAct').val();
    	var endDate = $('#charts_lifeDie').val();
    	if(endDate!='')
    		{endDate = endDate + ' 23:59:59'}
    	var projectid = JSON.parse(localStorage.getItem("adminInfo")).projectid;
    	getStatisticsDatabyDate(projectid,startDate,endDate); 
    	
    	$('#modal_select_date').modal('hide');
    });
    
    $('#btn_select_group').click(function(){
    	getUpdateRate();
    	$('#modal_select_group').modal('hide');
    });        
    
    $('#charts_lifeAct').val(getNowFormatDate());
    $('#charts_lifeDie').val(getNowFormatDate());
    
	$("#charts_lifeAct").datetimepicker({
		format: 'yyyy-mm-dd',
		bootcssVer: 3,
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		autoclose: true,
        todayBtn: true,
        language: 'zh-CN',
        pickerPosition: "bottom-left"
	});
	
	
	$("#charts_lifeDie").datetimepicker({
		format: 'yyyy-mm-dd',
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		autoclose: true,
        todayBtn: true,
        language: 'zh-CN',
        pickerPosition: "bottom-left"
	});		
	
	myChart.on('restore',function(){
		$('#charts_lifeAct').val(getNowFormatDate());
	    $('#charts_lifeDie').val(getNowFormatDate());
	    
	    getStatisticsDatabyDate(projectid,$('#charts_lifeAct').val(),$('#charts_lifeDie').val() + ' 23:59:59');
	});
	
	myChartPie.on('restore',function(){
		$('#charts_select_group').val(0);
		getUpdateRate();
	});
	
	var intervalpie = setInterval("getUpdateRate()",60000);
    
	var projectid = JSON.parse(localStorage.getItem("adminInfo")).projectid;
	
    getStatisticsDatabyDate(projectid,$('#charts_lifeAct').val(),$('#charts_lifeDie').val() + ' 23:59:59');        
});

function initLine()
{
	// 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('charts_terminals'));
    
    option = {
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer: {
    	            type: 'cross'
    	        }
    	    },
    	    legend: {
    	        data:['总数','DTU在线','LED在线','已更新','待更新','更新率']
    	    },
    	    toolbox: {
    	        feature: {
    	        	restore:{},
    	        	myTool:{//自定义按钮 danielinbiti,这里增加，selfbuttons可以随便取名字  
    	                   show:true,//是否显示  
    	                   title:'选择日期', //鼠标移动上去显示的文字  
    	                   //icon:'image://http://echarts.baidu.com/images/favicon.png', //图标
    	                   icon:'path://M20,8 L20,5 L18,5 L18,6 L16,6 L16,5 L8,5 L8,6 L6,6 L6,5 L4,5 L4,8 L20,8 Z M20,10 L4,10 L4,20 L20,20 L20,10 Z M18,3 L20,3 C21.1045695,3 22,3.8954305 22,5 L22,20 C22,21.1045695 21.1045695,22 20,22 L4,22 C2.8954305,22 2,21.1045695 2,20 L2,5 C2,3.8954305 2.8954305,3 4,3 L6,3 L6,2 L8,2 L8,3 L16,3 L16,2 L18,2 L18,3 Z M9,14 L7,14 L7,12 L9,12 L9,14 Z M13,14 L11,14 L11,12 L13,12 L13,14 Z M17,14 L15,14 L15,12 L17,12 L17,14 Z M9,18 L7,18 L7,16 L9,16 L9,18 Z M13,18 L11,18 L11,16 L13,16 L13,18 Z', //图标
    	                   onclick:function() {      	                         
    	                         $('#modal_select_date').modal('show');
    	                         }  
    	                    },    	
    	            saveAsImage: {}
    	        }
    	    },
    	    grid: {
    	        left: '3%',
    	        right: '4%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
    	    xAxis : [
    	        {
    	            type : 'category',
    	            boundaryGap : false,
    	            data : []
    	        }
    	    ],
    	    yAxis : [
    	        {
    	        	name: '数值',
    	            type : 'value'
    	        },{
    	            name: '更新率(%)', 
    	            max: 100,
    	            type: 'value'    	            
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'DTU在线',
    	            type:'line',    	                	            
    	            data:[]
    	        },
    	        {
    	            name:'LED在线',
    	            type:'line',    	                	            
    	            data:[]
    	        },
    	        {
    	            name:'已更新',
    	            type:'line',    	                	            
    	            data:[]
    	        },
    	        {
    	            name:'待更新',
    	            type:'line',    	                	            
    	            data:[]
    	        },
    	        {
    	            name:'总数',
    	            type:'line',    	               	            
    	            data:[]
    	        },
    	        {
    	            name:'更新率',
    	            type:'line',    	               	            
    	            data:[]
    	        }
    	    ]
    	};
    
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);    
}

function initPie()
{
	myChartPie = echarts.init(document.getElementById('charts_pie'));
	var optionPie = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		            restore: {},
		            myTool:{//自定义按钮 danielinbiti,这里增加，selfbuttons可以随便取名字  
 	                   show:true,//是否显示  
 	                   title:'选择分组', //鼠标移动上去显示的文字  
 	                   //icon:'image://http://echarts.baidu.com/images/favicon.png', //图标
 	                   icon:'path://M373.4,133.65H46.1c-25.4,0-46.1,20.7-46.1,46.1v199.4c0,25.4,20.7,46.1,46.1,46.1h327.3c25.4,0,46.1-20.7,46.1-46.1v-199.4C419.5,154.35,398.8,133.65,373.4,133.65z M383.5,379.25c0,5.5-4.5,10.1-10.1,10.1H46.1c-5.6,0-10.1-4.5-10.1-10.1v-145.7h347.5V379.25z M383.5,197.55H36v-17.8c0-5.6,4.5-10.1,10.1-10.1h327.3c5.6,0,10.1,4.5,10.1,10.1V197.55z', //图标
 	                   onclick:function() {      	                         
 	                         $('#modal_select_group').modal('show');
 	                         }  
 	                    },    	
		            saveAsImage: {}
		        }
		    },
		    series: [
		        {
		        	 name: '完成率',
		             type: 'gauge',
		             detail: {formatter:'{value}%'},
		             data: [{value: 50, name: '完成率'}]
		        }
		    ]
		};
	
	// 使用刚指定的配置项和数据显示图表。
	myChartPie.setOption(optionPie);
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
	        				$('#charts_select_group').append("<option value='"+grpid+"'>"+grpname+"</option>");							
	        			}		
	        			
	        			if(grpsinfo.length>1)
	        				{
	        				$('#charts_select_group').css("display","inline");
	        				}
	        			else
	        				{$('#charts_select_group').css("display","none");}
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
				$('#charts_select_group').append("<option value='"+grpid+"'>"+grpname+"</option>");
			}
			
			if(grpsinfo.length>1)
			{
			$('#charts_select_group').css("display","inline");
			}
		else
			{$('#charts_select_group').css("display","none");}
		}
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

function getStatisticsDatabyDate(projectid,startDate,endDate)
{
	$.ajax({  
        url:"/GetStatisticsDatabyDate", 
        data:{
        	projectid:projectid,
        	startDate:startDate,
        	endDate:endDate
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{     
        		minDate = data.minDate;
        		maxDate = data.maxDate;
        		 myChart.setOption({        	    	    
        	    	    legend: {
        	    	        data:['总数','DTU在线','LED在线','已更新','待更新','更新率']
        	    	    }, dataZoom: [{
        	                startValue: startDate
        	            }, {
        	                type: 'inside'
        	            }],
        	    	    xAxis : [
        	    	        {
        	    	            type : 'category',
        	    	            boundaryGap : false,
        	    	            data : data.recordingtimeArr
        	    	        }
        	    	    ],
        	    	    series : [
        	    	        {
        	    	            name:'DTU在线',
        	    	            type:'line',        	    	                 	    	            
        	    	            data:data.DtuArr
        	    	        },
        	    	        {
        	    	            name:'LED在线',
        	    	            type:'line',
        	    	            data:data.LedArr
        	    	        },
        	    	        {
        	    	            name:'已更新',
        	    	            type:'line',        	    	                    	    	            
        	    	            data:data.UpdatedArr
        	    	        },
        	    	        {
        	    	            name:'待更新',
        	    	            type:'line',        	    	               	    	            
        	    	            data:data.WaitingArr
        	    	        },
        	    	        {
        	    	            name:'总数',
        	    	            type:'line',        	    	                	    	
        	    	            data:data.TotalArr
        	    	        },{
         	    	            name:'更新率',
         	    	            type:'line',   
         	    	            yAxisIndex: 1,
         	    	            data:data.UpdateRateArr
         	    	        }
        	    	    ]
        	    	});        		
        		 
        		 if(endDate=='' || endDate >= getNowFormatDate())
        			 {
        			 var projectid = JSON.parse(localStorage.getItem("adminInfo")).projectid;
        			 interval = setInterval("getAppendDatabyDate('"+projectid+"')",60000);
        			 }
        		 else {
        			 clearInterval(interval);//停止        		 
        		 	}
        		}
        	else
        		{  
        		var option = myChart.getOption();
        		
        		var dataDTU = option.series[0].data;
        	    var dataLED = option.series[1].data;
        	    var dataUpdated = option.series[2].data;
        	    var dataWaiting = option.series[3].data;
        	    var dataTotal = option.series[4].data;
        	    var UpdateRate = option.series[5].data;
        	    
        		dataDTU.shift();
         	    option.series[0].data = [];
         	    dataLED.shift();
         	    option.series[1].data = [];
         	    dataUpdated.shift();
         	    option.series[2].data = [];
         	    dataWaiting.shift();
         	    option.series[3].data = [];
         	    dataTotal.shift();
         	    option.series[4].data = [];
         	    UpdateRate.shift();
         	    option.series[5].data = [];
         	    
         	    option.xAxis[0].data.shift();
         	    option.xAxis[0].data = [];
         	    
         	    myChart.setOption(option);
         	    
         	    clearInterval(interval);//停止 
        		}        	
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  GetStatisticsDatabyDate 错误");         	                       
          }  
    });	
}

function getAppendDatabyDate(projectid)
{
	$.ajax({  
        url:"/GetStatisticsDatabyDate", 
        data:{
        	projectid:projectid,
        	startDate:maxDate,
        	endDate:''
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{     
        		minDate = data.minDate;
        		maxDate = data.maxDate;
        		var option = myChart.getOption();
        		
        		var dataDTU = option.series[0].data;
        	    var dataLED = option.series[1].data;
        	    var dataUpdated = option.series[2].data;
        	    var dataWaiting = option.series[3].data;
        	    var dataTotal = option.series[4].data;
        	    var UpdateRate = option.series[5].data;
        	    
        	    dataDTU.shift();
        	    option.series[0].data = dataDTU.concat(data.DtuArr);
        	    dataLED.shift();
        	    option.series[1].data = dataLED.concat(data.LedArr);
        	    dataUpdated.shift();
        	    option.series[2].data = dataUpdated.concat(data.UpdatedArr);
        	    dataWaiting.shift();
        	    option.series[3].data = dataWaiting.concat(data.WaitingArr);
        	    dataTotal.shift();
        	    option.series[4].data = dataTotal.concat(data.TotalArr);
        	    UpdateRate.shift();
        	    option.series[5].data = UpdateRate.concat(data.UpdateRateArr);
        	    
        	    option.xAxis[0].data.shift();
        	    option.xAxis[0].data = option.xAxis[0].data.concat(data.recordingtimeArr);
        	    
        	    myChart.setOption(option);
        		}        	
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  GetStatisticsDatabyDate 错误");         	                     
          }  
    });
}

function getUpdateRate()
{
	var groupid = parseInt($("#charts_select_group").val())
	var projectid = JSON.parse(localStorage.getItem("adminInfo")).projectid;
	$.ajax({  
        url:"/GetUpdateRate", 
        data:{
        	projectid:projectid,
        	groupid:groupid
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{   
        		var UpdateRate = data.UpdateRate;
        		var unUpdateRate = 100 - data.UpdateRate;
        		myChartPie.setOption({        	    	    
    			 series: [{
    				 name: '完成率',
		             type: 'gauge',
		             detail: {formatter:'{value}%'},
		             data: [{value: UpdateRate, name: '完成率'}]
			        }]
     	    	});
        		}
        	else
        		{}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  GetUpdateRate 错误");         	                       
          }  
    });
}