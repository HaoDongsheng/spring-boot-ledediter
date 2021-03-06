var selectProjectid = "";
$(function(){			
	//$( '#taxi_command_cycle div').draggable({ cursor: "move",containment: "parent",axis: "y",connectToSortable: "#taxi_command_cycle",helper: "clone",revert: "invalid" });
	
	$( "#taxi_command_cycle" ).sortable({
	      revert: true,
	      cursor: "move",
	      containment: "parent",
	      axis: "y"
	    });
	
	getprojectList();
	
//	getGroup();
	
	//initBTabel();
		
	$('#select_project').change(function() {		
		selectProjectid = $('#select_project').val();
		getGroup($('#select_project').val());
		initBTabel();
	});
	
	$('#taxi_command_cycle input').change(function() {
		alert($(this).parent().text().trim());
	});	
	
	//var intervalpie = setInterval("refreshTable()",60000);
	var intervalpie = setInterval("updateTable()",60000);
	
	$('#btn_table_search').click(function() {
		initBTabel();		
	});
	
	$('#btn_taxi_edit').click(function() {
		taxi_info_edit();
	});
	
	$('#btn_taxi_delete').click(function() {
		taxi_info_delete();
	});
	
	$('#btn_group_edit').click(function() {
		taxi_group_edit();
	});			
	
	$('#btn_table_mutilUpdate').click(function() {	
		$('#modal_group_edit').modal('show');
	});
	
	$('#btn_table_parmset').click(function() {	
		$('#modal_parmset').modal('show');
	});
	
	
	$('#btn_table_edit').click(function() {	
		if(selectDtuKey=="")
			{
			alertMessage(1, "警告", "请选择要编辑的终端!");			
			}
		else {
			var tableData = $('#taxi_table').bootstrapTable('getData');
	    	for(var i=0;i<tableData.length;i++)
	    		{
	    			var DtuKey = tableData[i].DtuKey;
	    			if(DtuKey == selectDtuKey)
	    				{
	    				$('#taxi_edit_DTUKey').val(DtuKey);
	    				if(isVehicleNumber(tableData[i].name))
	    				{	    			
	    					var province = tableData[i].name.substring(0,1);
	    					var NO = tableData[i].name.substring(1,2);
	    					var name = tableData[i].name.substring(2).trim();
	    					$('#taxi_edit_province').val(province);
	    					$('#taxi_edit_NO').val(NO);
	    					$('#taxi_edit_name').val(name);
	    				}
	    				else {
	    					$('#taxi_edit_name').val("");
						}
	    					    				
	    				$('#taxi_edit_startLevel').val(tableData[i].StarLevel);
	    				
	    				var groupName = tableData[i].groupid;
	    				
	    				grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	        			
	        			for(var i=0;i<grpsinfo.length;i++)
	        			{
	        				var grpid=grpsinfo[i].grpid;
	        				
	        				if(groupName == grpsinfo[i].grpname)
	        					{
	        					$('#taxi_edit_group').val(grpid);
	        					}
	        			}	 
	        			
	    				break;
	    				}    			
	    		}        	
	    	
			$('#modal_taxi_edit').modal('show');	
		}					
	});
	
	$('#btn_table_delete').click(function() {	
		if(selectDtuKey=="")
			{
			alertMessage(1, "警告", "请选择要编辑的终端!");			
			}
		else {
			//selectDtuKey
			
			$('#modal_taxi_delete').modal('show');	
		}					
	});
	
	$('#taxi_table').on('click-row.bs.table', function (e,row,$element) {        
        if($($element).hasClass("changeColor"))
        	{
        	$($element).removeClass('changeColor'); 
        	selectDtuKey="";
        	}
        else {
        	$('#taxi_table .changeColor').removeClass('changeColor');
        	$($element).addClass('changeColor');
        	var tableData = $('#taxi_table').bootstrapTable('getData');
        	if($element[0].rowIndex - 1 <tableData.length)
        		{
        			var DtuKey = tableData[$element[0].rowIndex - 1].DtuKey;
        			selectDtuKey=DtuKey;
        		}        	
		}         
	});
	
	$("#taxi_table").on('load-success.bs.table',function(data){
       refreshTableRowSelect();
    });
});

var selectDtuKey="";

function isVehicleNumber(vehicleNumber) {
    var result = false;    
    var express = /(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1} [A-Z0-9]{5}$)|(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1} [A-Z0-9]{6}$)/;
    result = express.test(vehicleNumber);    
    return result;
}

//刷新选中列表
function refreshTableRowSelect() {
	if(selectDtuKey != "")
		{
		rowIndex = -1;
		var tableData = $('#taxi_table').bootstrapTable('getData');
    	for(var i=0;i<tableData.length;i++)
    		{
    			var DtuKey = tableData[i].DtuKey;
    			if(DtuKey == selectDtuKey)
    				{
    				rowIndex=i + 1;break;
    				}    			
    		}        	
    	if(rowIndex==-1)
    		{selectDtuKey="";}
    	else {
    		var trlist = $("#taxi_table tbody tr");
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
		
}

//刷新表格数据,点击你的按钮调用这个方法就可以刷新
function refreshTable() {
    $('#taxi_table').bootstrapTable('refresh', {url: '/getTerminalsbypageNum'});        
}

//刷新表格数据,点击你的按钮调用这个方法就可以刷新
function updateTable() {
    //$('#taxi_table').bootstrapTable('refresh', {url: '/getTerminalsbypageNum'});  
	var DtuKeyList=[];
	var tableData = $('#taxi_table').bootstrapTable('getData');
	if(tableData!=null && tableData.length>0)
		{
		for(var i=0;i<tableData.length;i++)
			{
				var DtuKey = tableData[i].DtuKey;
				if(DtuKey!=null)
				{DtuKeyList.push(DtuKey);}
			}  	
		}
	if(DtuKeyList.length>0)
		{
	$.ajax({  
        url:"/getTerminalsUpdate", 
        data:{
        	Dtukeys: JSON.stringify(DtuKeyList),        	
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{    
        		//$("#taxi_table").bootstrapTable("remove", {field: "DtuKey",values: [selectDtuKey]}); 
        		var items = data.rows;
        		if(items!=null && items.length>0)
        			{
        			for(var i=0;i<items.length;i++)
        				{        				
        				var tableData = $('#taxi_table').bootstrapTable('getData');
                    	for(var j=0;j<tableData.length;j++)
                    		{            			
                    			if(items[i].DtuKey == tableData[j].DtuKey)
                    				{                    				
                    				var map={
                        					index:j,
                        					row: items[i]
                        			};
                            		$("#taxi_table").bootstrapTable("updateRow",map);
                            		break;
                    				}    			
                    		}                            	                		                		
        				}
        			}
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getTerminalsUpdate 错误");         		                                    
          }  
    });	
		}
}

function initBTabel()
{
	$('#taxi_table').bootstrapTable('destroy');
    $('#taxi_table').bootstrapTable({   
    	url: '/getTerminalsbypageNum',//请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        contentType: "application/x-www-form-urlencoded",
        toolbar: '#taxi_toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式    
        sortName:"DtuKey",
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
            title: '序号',
            field: 'id',            
            formatter: function (value, row, index) {
                var pageSize = $('#taxi_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#taxi_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            },
            visible: false  
        }, {
            field: 'name',
            title: '终端名称',
            width:150,
            sortable:true
            
        }, {
            field: 'DtuKey',
            title: 'DTU编号',
            width:150,
            sortable:true          
        }, {
            field: 'DtuOnline',
            title: 'DTU状态',
            width:50,
            sortable:true,
            formatter:function (value, row, index) { 
            	if(row.DtuOnline==1)
            		{
	                return "<span class='fa fa fa-circle' style='font-size: 25px;color:#00ff00;'></span>";
            		}
            	else {
            		return "<span class='fa fa fa-circle' style='font-size: 25px;color:#ff0000;'></span>";
				}
            }          
        }, {
            field: 'LedOnline',
            title: 'LED状态',
            width:50,
            sortable:true,
            formatter:function (value, row, index) { 
            	if(row.LedOnline==1)
            		{
	                return "<span class='fa fa fa-circle' style='font-size: 25px;color:#00ff00;'></span>";
            		}
            	else {
            		return "<span class='fa fa fa-circle' style='font-size: 25px;color:#ff0000;'></span>";
				}
            }          
        }, {
            field: 'disconnect',
            title: '内置车牌',
            visible: false,
            sortable:true,
            formatter: function (value, row, index) {  
            	var txt="否"
            	if(value!=null && value!="" && value!=0)
            		{txt = "是";}
            	return txt;
            }        
        }, {
            field: 'projectid',
            title: '项目名称',
            visible: false,
            sortable:true         
        }, {
            field: 'groupid',
            title: '分组名称',
            visible: true,
            sortable:true              
        },/* {
            field: 'LedStateString',
            title: '屏幕状态',
            width:300,
            class:'colStyle',
            formatter:function (value, row, index) { 
            	if(row.LedStateString!=null)
            		{
	            	var values = row.LedStateString;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.LedStateString+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: true,
            sortable:true         
        },*/ {
            field: 'LED_ID',
            title: 'LED编号',
            visible: false,
            sortable:true
         
        }, {
            field: 'SIMNo',
            title: 'SIM卡号',
            visible: false,
            sortable:true
         
        }, {
            field: 'AdIdList',
            title: '广告列表',
            width:200,
            visible: true,
            formatter: function (value, row, index) {            	
            	var AdIdList = value;
            	
            	var option;            	
            	   
            	var advArr=null;
            	if(AdIdList != null)
            		{
            		advArr = JSON.parse(AdIdList);
            		}
            	var headOption = "";            	    
            	
            	if(advArr!=null)
            		{
	            	for(var i=0;i<advArr.length;i++)
	    			{
	            		var txt=advArr[i].advname;
	            		if(advArr[i].advstatus!="")
	            			{
	            			txt+="("+advArr[i].advstatus+")";
	            			}
		    			headOption = headOption + "<option value='"+advArr[i].pubid+"'>"+txt+"</option>";                    			
	    			}
            		}
            	option = '<select class="form-control">'+ headOption + '</select>';
            	            	
                return option;
            },
            sortable:true
        }, {
            field: 'AdIdListCount',
            title: '广告总数',
            width:50,
            visible: true,                   
        }, /*{
            field: 'PlayList',
            title: '播放列表',
            width:100,
            class:'colStyle',
            formatter:function (value, row, index) { 
            	if(row.PlayList!=null)
            		{
	            	var values = row.PlayList;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.PlayList+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        },*/ {
            field: 'UpdateRate',
            title: '更新率',
            visible: true,
            sortable:true         
        }, {
            field: 'LedVersion',
            title: 'LED版本',
            width:100,
            "class":'colStyle',
            formatter:function (value, row, index) { 
            	if(row.LedVersion!=null)
            		{
	            	var values = row.LedVersion;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.LedVersion+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        }, {
            field: 'StateLedVersion',
            title: '智能顶灯版本',
            width:100,
            "class":'colStyle',
            formatter:function (value, row, index) { 
            	if(row.StateLedVersion!=null)
            		{
	            	var values = row.StateLedVersion;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.StateLedVersion+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        }, {
            field: 'StarLevel',
            title: '星级状态',
            width:100,
            /*
            class:'colStyle',
            formatter:function (value, row, index) { 
            	if(row.StarLevel!=null)
            		{
	            	var values = row.StarLevel;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.StarLevel+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },    
            */
            visible: false,
            sortable:true         
        }, {
            field: 'StarLevelSet',
            title: '设置星级',
            width:100,            
            visible: false,
            sortable:true 
        }, {
            field: 'RegisterDate',
            title: '安装日期',
            width:100,
            "class":'colStyle',
            formatter:function (value, row, index) { 
            	if(row.RegisterDate!=null)
            		{
	            	var values = row.RegisterDate;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.RegisterDate+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        }, {
            field: 'DTU_ResponseTime',
            title: 'DTU最后应答',
            width:100,
            "class":'colStyle',
            formatter:function (value, row, index) { 
            	if(row.DTU_ResponseTime!=null)
            		{
	            	var values = row.DTU_ResponseTime;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.DTU_ResponseTime+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        }, {
            field: 'LED_ResponseTime',
            title: 'LED最后应答',
            width:100,
            "class":'colStyle',
            formatter:function (value, row, index) { 
            	if(row.LED_ResponseTime!=null)
            		{
	            	var values = row.LED_ResponseTime;//获取当前字段的值
	                //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
	                //&nbsp;代替
	                values = values.replace(/\s+/g,'&nbsp;')
	                values = values.replace(/\},/,'},\n');//换行
	                return "<span title="+values+">"+row.LED_ResponseTime+"</span>"
            		}
            	else {
            		return "<span title=''></span>"
				}
            },
            visible: false,
            sortable:true         
        }
        ]
    }); 
    
    $('#taxi_table').colResizable();
    
    // 得到查询的参数
    function queryParams(params) {		
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的            
            pageSize: params.limit,                        //页面大小
            pageNum: (params.offset / params.limit) + 1,   //页码
            searchString: $('#searchString_id').val().trim(),
            sort: params.sort,      //排序列名  
            sortOrder: params.order,//排位命令（desc，asc） 
            issuperuser:JSON.parse(localStorage.getItem("adminInfo")).issuperuser,
            projectid:selectProjectid,//JSON.parse(localStorage.getItem("adminInfo")).projectid,
            adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname,
            groupids:JSON.parse(localStorage.getItem("adminInfo")).groupids,
            adminlevel:JSON.parse(localStorage.getItem("adminInfo")).adminlevel
        };
        return temp;
    };
}

//获取项目列表
function getprojectList()
{
	if($('#select_project').length>0)
		{
		$.ajax({  
	        url:"/getProjectListbyuser",          
	        type:"post",  
	        dataType:"json", 
	        data:{
	        	adminInfo:localStorage.getItem("adminInfo")
	        	},
	        success:function(data)  
	        {       	  
	        	if(data!=null && data.length>0)    		
	        		{    
	        			$('#select_project').empty();
		        		for(var i=0;i<data.length;i++)
						{	
		        			var project = data[i];
		        			var projectId = project.projectid;
		        			var projectName = project.projectname;
		        			var option="";
	        				if(i==0)
							{
	        					option = "<option selected value='"+projectId+"'>"+projectName+"</option>";		
	        					
	        					getGroup(projectId);
	        					selectProjectid = projectId;
	        					initBTabel();
							}
	        				else
							{option = "<option value='"+projectId+"'>"+projectName+"</option>";}
	        				
		        			$('#select_project').append(option);
						}	        		
	        		}        	
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  getProjectList 错误");            
	          }  
	    });
		}
	else
		{
		$.ajax({  
	        url:"/getProjectListbyuser",          
	        type:"post",  
	        dataType:"json", 
	        data:{
	        	adminInfo:localStorage.getItem("adminInfo")
	        	},
	        success:function(data)  
	        {       	  
	        	if(data!=null && data.length>0)    		
	        		{    	        			
		        		for(var i=0;i<data.length;i++)
						{	
		        			var project = data[i];
		        			var projectId = project.projectid;
		        			var projectName = project.projectname;
		        			var option="";
	        				if(i==0)
							{		        					
	        					getGroup(projectId);
	        					selectProjectid = projectId;
	        					initBTabel();
	        					break;
							}
						}	        		
	        		}        	
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  getProjectList 错误");            
	          }  
	    });
		}
}
//获取分组信息
function getGroup(selectProjectid)
{	
	$('#taxi_edit_group').empty();
	$('#taxi_group_group').empty();
	$.ajax({  
        url:"/gettGroupbyProjectid",          
        type:"post",  
        dataType:"json", 
        data:{
        	projectid:selectProjectid,
        	adminInfo:localStorage.getItem("adminInfo")
        	},
        success:function(data)  
        {       	  
        	if(data!=null && data.length>0)    		
        		{        				        		
        			grpsinfo= data;
        				     
        			$('#taxi_group_group').append("<option value='0' selected>全部分组</option>");
        			for(var i=0;i<grpsinfo.length;i++)
        			{
        				var grpid=grpsinfo[i].grpid;
        				var grpname = grpsinfo[i].grpname;
        				
        				$('#taxi_edit_group').append("<option value='"+grpid+"'>"+grpname+"</option>");	
        				
        				$('#taxi_group_group').append("<option value='"+grpid+"'>"+grpname+"</option>");	
        			}	        			
        		}
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  gettGroupbyProjectid 错误");	        	           
          } 
	 });
}
//编辑终端信息
function taxi_info_edit()
{
	var DTUKey = $('#taxi_edit_DTUKey').val();
	var province = $('#taxi_edit_province').val();
	var NO = $('#taxi_edit_NO').val();
	var taxiName = $('#taxi_edit_name').val().trim().toUpperCase();	
	
	var StarLevelset = $('#taxi_edit_startLevel').val();
	if(taxiName==null || taxiName=="")
		{alertMessage(1, "警告", "终端名称不能为空!");return;}
	
	var carNum =province+NO+" "+taxiName;
		
	var groupid = parseInt($('#taxi_edit_group').val());
	$.ajax({  
        url:"/taxiInfoEdit", 
        data:{
        	dtukey:DTUKey,
        	taxiname:carNum,
        	groupid:groupid,
        	StarLevelset:StarLevelset,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{    
        		var item={		        					
        				name:carNum,
        				DtuKey:DTUKey,		        					
        				projectid:data.projectName,
        				groupid:data.groupName,
        				StarLevelSet:StarLevelset,
        				disconnect:data.disconnect
    			};
        		var rowIndex=-1;
        		
        		var tableData = $('#taxi_table').bootstrapTable('getData');
            	for(var i=0;i<tableData.length;i++)
            		{            			
            			if(DTUKey == tableData[i].DtuKey)
            				{
            				rowIndex=i;break;
            				}    			
            		}        
            	
        		var map={
    					index:rowIndex,
    					row: item
    			};
        		if(rowIndex!=-1)
    			{$("#taxi_table").bootstrapTable("updateRow",map);}        		 					 
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        	refreshTableRowSelect();
        	$('#modal_taxi_edit').modal('hide');
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  taxiInfoEdit 错误");         		                        
            $('#modal_taxi_edit').modal('hide');
          }  
    });
}

//删除终端信息
function taxi_info_delete()
{	
	$.ajax({  
        url:"/taxiInfoDelete", 
        data:{
        	dtukey: selectDtuKey,        	
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{    
        		$("#taxi_table").bootstrapTable("remove", {field: "DtuKey",values: [selectDtuKey]}); 		 
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        	refreshTableRowSelect();
        	$('#modal_taxi_delete').modal('hide');
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  taxiInfoDelete 错误");         		                        
            $('#modal_taxi_delete').modal('hide');
          }  
    });
}

function taxi_group_edit()
{	
	var StarLevelset = $('#taxi_group_startLevel').val();	
	var groupid = parseInt($('#taxi_group_group').val());
	$.ajax({  
        url:"/taxiInfoEditbyGroup", 
        data:{        	
        	groupid:groupid,
        	StarLevelset:StarLevelset,
        	issuperuser:0,
            projectid:selectProjectid,
            adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{    
        		
        		var item={
        				StarLevelSet:StarLevelset
    			};
        		var isall=false;
        		if(data.groupName=="全部分组")
        			{isall=true;}
        		var tableData = $('#taxi_table').bootstrapTable('getData');
            	for(var i=0;i<tableData.length;i++)
            		{            			
            			if(data.groupName == tableData[i].groupid || isall)
            				{
            				rowIndex=i;
            				var map={
                					index:rowIndex,
                					row: item
                			};
                    		if(rowIndex!=-1)
                			{$("#taxi_table").bootstrapTable("updateRow",map);}    
            				}    			
            		}
            	$('#modal_group_edit').modal('hide');
        		}
        	else
        		{
        			alertMessage(1, "警告", data.resultMessage);        			
        		}
        	refreshTableRowSelect();
        	$('#modal_taxi_edit').modal('hide');
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  taxiInfoEditbyGroup 错误");         		                        
            $('#modal_taxi_edit').modal('hide');
          }  
    });
}