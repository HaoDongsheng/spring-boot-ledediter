var myChart,myChartPie;
var minDate='',maxDate='';
var interval = null;
$(function(){	
	getGroup();
	
	initPie();
	initLine();
        
	getUpdateRate();
	
    $('#charts_lifeAct').change(function(){		
    	var startDate = $('#charts_lifeAct').val();
    	var endDate = $('#charts_lifeDie').val();
    	if(endDate!='')
    		{endDate = endDate + ' 23:59:59'}
    	getStatisticsDatabyDate('zhaotong',startDate,endDate);	    
	  });
    
    $('#charts_lifeDie').change(function(){			
    	var startDate = $('#charts_lifeAct').val();
    	var endDate = $('#charts_lifeDie').val();
    	if(endDate!='')
    		{endDate = endDate + ' 23:59:59'}
    	getStatisticsDatabyDate('zhaotong',startDate,endDate);    
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
	
	$('#charts_select_group').change(function(){			
		getUpdateRate();	    
	  });
	
	
	var intervalpie = setInterval("getUpdateRate()",60000);
    
    getStatisticsDatabyDate('zhaotong',$('#charts_lifeAct').val(),$('#charts_lifeDie').val() + ' 23:59:59');        
});

function initLine()
{
	// 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('charts_terminals'));
    
    option = {
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['总数','DTU在线','LED在线','已更新','待更新']
    	    },
    	    toolbox: {
    	        feature: {
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
    	            type : 'value'
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
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        //orient: 'vertical',
		        //x: 'left',
		        data:['已更新','未更新']
		    },
		    series: [
		        {
		            name:'访问来源',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: false,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:85, name:'已更新'},
		                {value:15, name:'未更新'}
		            ]
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
	        	alert("ajax 函数  getGroup 错误");	            
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

function getStatisticsData(projectid)
{
	$.ajax({  
        url:"/GetStatisticsData", 
        data:{
        	projectid:projectid    	
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{     
        		 myChart.setOption({        	    	    
        	    	    legend: {
        	    	        data:['总数','DTU在线','LED在线','已更新','待更新']
        	    	    },   
        	    	    dataZoom: [{
        	                startValue: '2019/3/16 8:45:13'
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
        	    	    yAxis : [
        	    	        {
        	    	            type : 'value'
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
        	    	        }
        	    	    ]
        	    	});
        		}
        	else
        		{
        			alert(data.resultMessage);        			
        		}        	
        },  
        error: function() {  
        	alert("ajax 函数  GetStatisticsData 错误");                        
          }  
    });	
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
        	    	        data:['总数','DTU在线','LED在线','已更新','待更新']
        	    	    }, dataZoom: [{
        	                startValue: 'startDate'
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
        	    	    yAxis : [
        	    	        {
        	    	            type : 'value'
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
        	    	        }
        	    	    ]
        	    	});
        		 if(endDate=='' || endDate >= getNowFormatDate())
        			 {
        			 interval = setInterval("getAppendDatabyDate('zhaotong')",60000);
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
         	    
         	    option.xAxis[0].data.shift();
         	    option.xAxis[0].data = [];
         	    
         	    myChart.setOption(option);
         	    
         	   clearInterval(interval);//停止 
        		}        	
        },  
        error: function() {  
        	alert("ajax 函数  GetStatisticsDatabyDate 错误");                        
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
        	    
        	    option.xAxis[0].data.shift();
        	    option.xAxis[0].data = option.xAxis[0].data.concat(data.recordingtimeArr);
        	    
        	    myChart.setOption(option);
     
        		}        	
        },  
        error: function() {  
        	alert("ajax 函数  GetStatisticsDatabyDate 错误");                        
          }  
    });
}

function getUpdateRate()
{
	var groupid = parseInt($("#charts_select_group").val())
	$.ajax({  
        url:"/GetUpdateRate", 
        data:{
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
			            name:'访问来源',
			            type:'pie',
			            radius: ['50%', '70%'],
			            avoidLabelOverlap: false,
			            label: {
			                normal: {
			                    show: false,
			                    position: 'center'
			                },
			                emphasis: {
			                    show: true,
			                    textStyle: {
			                        fontSize: '30',
			                        fontWeight: 'bold'
			                    }
			                }
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:UpdateRate, name:'已更新'},
			                {value:unUpdateRate, name:'未更新'}
			            ]
			        }]
     	    	});
        		}
        	else
        		{}
        },  
        error: function() {  
        	alert("ajax 函数  GetStatisticsDatabyDate 错误");                        
          }  
    });
}