$(function(){
			
	//initBTabel();
	
	getGroup();
	//分组改变
	$("#grouplist").change(function(){	
//		Grpid:parseInt($("#grouplist").val()),
//		type:parseInt($("#recycle_type").val()),
//		infoName:$("#search_info_name").val().trim(),
//		lifeAct:$("#search_info_lifeAct").val().trim(),
//		lifeDie:$("#search_info_lifeDie").val().trim(),
		
		$("#search_info_name").val("");
		$("#search_info_lifeAct").val("");
		$("#search_info_lifeDie").val("");
		sessionStorage.setItem('selectgrpid', parseInt($("#grouplist").val()));
		getadvListbyGrpid(parseInt($("#grouplist").val()));	    
	  });
	
	$("#recycle_type").change(function(){	
		$("#search_info_name").val("");
		$("#search_info_lifeAct").val("");
		$("#search_info_lifeDie").val("");
		getadvListbyGrpid(parseInt($("#grouplist").val()));	    
	  });
	
	//串口发送
	$("#btn_setSerialPort").click(function(){
		var SerialPort={
			"ComName":$("#ComName").val(),
			"BaudRate":parseInt($("#BaudRate").val())
		};
		localStorage.setItem("SerialPort",JSON.stringify(SerialPort));
		$("#modal_SerialPort").modal('hide');		
	});	
	//条件检索	
	$("#btn_table_search").click(function(){
		$("#modal_search").modal('show');
	});
	//刷新
	$("#btn_table_refresh").click(function(){
		$("#search_info_name").val("");
		$("#search_info_lifeAct").val("");
		$("#search_info_lifeDie").val("");
		getadvListbyGrpid(parseInt($("#grouplist").val()));
	});
	//条件检索
	$("#btn_info_search").click(function(){
		getadvListbyGrpid(parseInt($("#grouplist").val()));
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
								getadvListbyGrpid(grpid);								
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								getadvListbyGrpid(grpid);
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
			$('#grouplist').css("display","inline");
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
				getadvListbyGrpid(grpid);				
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid == grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					getadvListbyGrpid(grpid);					
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

function initBTabel()
{
    $('#recycle_table').bootstrapTable({    
    	url: '/getDelinfolistbypageNum',//请求后台的URL（*）
    	method: 'post',                      //请求方式（*）
        contentType: "application/x-www-form-urlencoded",
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sortName:"lifeDie",
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParams:queryParams,			//请求服务器时所传的参数        
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
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
                var pageSize = $('#recycle_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#recycle_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'infosn',
            title: '广告id',
            visible: false
            
        }, {
            field: 'infoname',
            title: '广告名称',
            sortable:true
            
        }, {
            field: 'lifeAct',
            title: '开始日期',
            sortable:true
         
        }, {
            field: 'lifeDie',
            title: '结束日期',
            sortable:true
         
        }, {
            field: 'items',
            title: '显示内容',
            sortable:true,
            formatter: function (value, row, index) {            	
                return "<div style='max-height: 100px;max-width: 400px;overflow:auto;'>" + value + "</div>";
            }
        }, {
            field: 'timelenght',
            title: '播放时长(秒)',
            sortable:true
          
        } , {
        	field: 'operate',
        	title: '操作',
        	align: 'center',
        	events: operateEvents,
        	formatter: operateFormatter
        }
        ]
    });   
}

// 得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的          
		Grpid:parseInt($("#grouplist").val()),
		type:parseInt($("#recycle_type").val()),
		infoName:$("#search_info_name").val().trim(),
		lifeAct:$("#search_info_lifeAct").val().trim(),
		lifeDie:$("#search_info_lifeDie").val().trim(),
        pageSize: params.limit,                         //页面大小
        pageNum: (params.offset / params.limit) + 1,   //页码
        sort: params.sort,      //排序列名  
        sortOrder: params.order, //排位命令（desc，asc） 
        adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
    };
    return temp;
};

function operateFormatter(value, row, index) {
	var result = '<a class="copy" href="javascript:void(0)" style="margin:0px 5px;" title="复制"><i class="fa fa-copy"></i>复制</a>';
	 if($("#recycle_type").val()==0)
 	{
		 result += '<a class="delete" href="javascript:void(0)" style="margin:0px 5px;" title="删除"><i class="fa fa-trash-o"></i>删除</a><a class="SerialPort" href="javascript:void(0)" style="margin:0px 5px;" title="串口显示"><i class="fa fa-magnet"></i>串口显示</a>';
 	}
    return result;
}

window.operateEvents = {
        'click .copy': function (e, value, row, index) {        
        	var infoid = row.infosn;
        	$.ajax({  
    	        url:"/CopyInfotodraftbyInfoSN", 
    	        data:{
    	        	infosn:infoid,
    	        	groupid:parseInt($("#grouplist").val()),
    	        	type:parseInt($("#recycle_type").val()),
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
        },
        'click .delete': function (e, value, row, index) {                	        	        	
        	var infoid = row.infosn;
        	$.ajax({  
    	        url:"/DeleteInfo2old", 
    	        data:{
    	        	infosn:infoid,    	        	
    	        	adminid:JSON.parse(localStorage.getItem("adminInfo")).adminid,
    	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
    				},  
    	        type:"post",  
    	        dataType:"json", 
    	        success:function(data)  
    	        {       	  
    	        	if(data.result=="success")
    	        		{    	     
    	        			$("#recycle_table").bootstrapTable("remove", {field: "infosn",values: [infoid]});
    	        			alertMessage(0, "成功", "删除成功");
    	        		}
    	        	else
    	        		{
    	        			alertMessage(1, "警告", data.resultMessage);
    	        		}
    	        },  
    	        error: function() { 
    	        	alertMessage(2, "异常", "ajax 函数  DeleteInfo2old 错误");     	        	            
    	          }  
    	    });
        },
        'click .SerialPort': function (e, value, row, index) {   
        	
        	var SerialPort = localStorage.getItem("SerialPort");
        	if(SerialPort!=null && SerialPort!="")
        		{
        		intiWebSocket();
        		        		        		
        		ws.addEventListener('open', function () { 
        		
    			var jsonSerialPort;
    			try
    			{jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));}
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
    	        			infoSerialPublishbyid(row.infosn);
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
    };

function SendCallback(SN,infocodelist,i)
{
	var timesRun = 0;
	var interval = setInterval(function(){
		if(infocodelist.length > i)
		{
	    timesRun += 1;  
	    if(timesRun >= 10){    
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
//按组取广告列表
function getadvListbyGrpid(grpid)
{		
	$('#recycle_table').bootstrapTable('destroy')
	initBTabel();
}