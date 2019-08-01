$(function(){
	$( ".modal" ).draggable();
	
	initBTabel()
	
	getprojectList();
	
	getGroup();
	
	getProvinceList();
	//模态确定按钮
	$('#btn_group_edit').click(function(){
		 model_eidtgroup();
	});
	
	$('#select_ProvinceList').change(function(){
		getCityList($('#select_ProvinceList').val());
	});	
	
	//模态确定按钮
	$('#btn_group_delete').click(function(){
		$.ajax({  
	        url:"/DeleteGroup",          
	        type:"post", 
	        data:{	        	
	        	grpid:parseInt($("#modal_group_delete").attr("data-type")),
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{		        			
		        			$("#groupManger_table").bootstrapTable("remove", {field: "group_sn",values: [parseInt($("#modal_group_delete").attr("data-type"))]});		        			
		        			
		        			grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
		        			for(var i=0;i<grpsinfo.length;i++)
		        			{
		        				var grpid=grpsinfo[i].grpid;
		        				if(parseInt($("#modal_group_delete").attr("data-type"))==grpid)
		        					{
		        					grpsinfo.splice(i, 1);break;
		        					}
		        			}
		        			
		        			sessionStorage.setItem('grpsinfo', JSON.stringify(grpsinfo));	
		        			
		        			$('#modal_group_delete').modal('hide');	
						}
		        		else
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	        	alertMessage(2, "异常", "ajax 函数  DeleteGroup 错误"); 	            
	          }  
	    });
	});	
	
	$('#btn_table_add').click(function(){

		$('#group_eidt_name').val('');
		$('#group_eidt_width').val(128);
		$('#group_eidt_height').val(32);	
		
		if($('#group_edit_project option').length > 1)
		{$('#group_edit_project').parents('.col-md-6').css('display','inline');}
		else
		{$('#group_edit_project').parents('.col-md-6').css('display','none');}
		
		$('#modal_group_edit').attr("data-type",0);
		$('#modal_group_edit').modal('show');				
					
	});	
	
	$('#btn_basic_parameter').click(function(){
		var parameter={
				groupid:parseInt($("#modal_basic_parameter").attr("data-type")),
				setType:"basic",
				Showversion:$("#Showversion").val(),
				LinkTime:$("#LinkTime").val(),
				MaxReceiveLen:$("#MaxReceiveLen").val(),
				BootstrapperWaitingTime:$("#BootstrapperWaitingTime").val(),
				Test:$("#Test").val(),
				Energy:$("#Energy").val(),	
				DTULink:$("#DTULink").val()
			};
		set_parameter(parameter);
	});
	
	$('#btn_brightness_parameter').click(function(){
		var parameter={
				groupid:parseInt($("#modal_brightness_parameter").attr("data-type")),
				setType:"brightness",
				SetBrightnessMode:2,
				ProvinceName:$("#select_ProvinceList").val(),
				CityName:$("#select_CityList").val(),
				BrightnessValueArray:$("#brightness1").val()+"|"+$("#brightness2").val()+"|"+$("#brightness3").val()+"|"+$("#brightness4").val()+"|"+$("#brightness5").val()+"|"+$("#brightness6").val()+"|"+$("#brightness7").val()+"|"+$("#brightness8").val()				
			};
		set_parameter(parameter);
	});
	
	$('#btn_brightness2_parameter').click(function(){
		var parameter={
				groupid:parseInt($("#modal_brightness2_parameter").attr("data-type")),
				setType:"brightness",
				SetBrightnessMode:1,
				ProvinceName:"北京",
				CityName:"北京",
				BrightnessTimeArray:$("#time1").val()+"|"+$("#time2").val()+"|"+$("#time3").val()+"|"+$("#time4").val()+"|"+$("#time5").val()+"|"+$("#time6").val()+"|"+$("#time7").val()+"|"+$("#time8").val(),
				BrightnessValueArray:$("#brightness1").val()+"|"+$("#brightness2").val()+"|"+$("#brightness3").val()+"|"+$("#brightness4").val()+"|"+$("#brightness5").val()+"|"+$("#brightness6").val()+"|"+$("#brightness7").val()+"|"+$("#brightness8").val()				
			};
		set_parameter(parameter);
	});
	
	$('#btn_user_parameter').click(function(){
		var parameter={
				groupid:parseInt($("#modal_user_parameter").attr("data-type")),
				setType:"user",
				DefaulText:$("#DefaulText").val(),
				AlarmText:$("#AlarmText").val(),
				setZhaoMingMode:$("#setZhaoMingMode").val(),
				m_ZhaoMingTimeStart:$("#m_ZhaoMingTimeStart").val(),
				m_ZhaoMingTimeEnd:$("#m_ZhaoMingTimeEnd").val()
			};
		set_parameter(parameter);
	});
});

function initBTabel()
{
    $('#groupManger_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
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
        uniqueId: "group_sn",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
            title: '序号',
            field: 'group_sn'
        }, {
            field: 'group_name',
            title: '分组名称'                        	
        }, {
            field: 'maxPackLength',
            title: '最大包长'
            
        }, {
            field: 'group_screenwidth',
            title: '屏幕宽度'
            
        }, {
            field: 'group_screenheight',
            title: '屏幕高度'
         
        },{
        	field: 'operate',
        	title: '操作',
        	align: 'center',
        	events: operateEvents,
        	formatter: operateFormatter
        }
        ]
    });
}

function operateFormatter(value, row, index) {
	var adminInfo = localStorage.getItem("adminInfo");
	var issuper = JSON.parse(adminInfo).issuperuser;
	if(issuper==1)
		{
		return [
		         '<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
		         '<i class="fa fa-edit"></i>编辑',
		         '</a>'+
		         '<a class="remove" href="javascript:void(0)" style="margin:0px 5px;" title="删除">',
		         '<i class="fa fa-remove"></i>删除',
		         '</a>'+
		         '<a class="user_parameter" href="javascript:void(0)" style="margin:0px 5px;" title="用户常用可变参数">',
		         '<i class="fa fa-cog"></i>用户参数',
		         '</a>'+
		         '<a class="brightness_parameter" href="javascript:void(0)" style="margin:0px 5px;" title="亮度调节">',
		         '<i class="fa fa-lightbulb-o"></i>亮度调节',
		         '</a>'+
		         '<a class="basic_parameter" href="javascript:void(0)" style="margin:0px 5px;" title="基本共有参数">',
		         '<i class="fa fa-cogs"></i>基本参数',
		         '</a>'+
		         '<a class="brightness2_parameter" href="javascript:void(0)" style="margin:0px 5px;" title="时间亮度">',
		         '<i class="fa fa-clock-o"></i>时间亮度',
		         '</a>'         
		    ].join('');
		}
	else
		{
		return [
		         '<a class="edit" href="javascript:void(0)" title="编辑">',
		         '<i class="fa fa-edit"></i>编辑',
		         '</a>|'+
		         '<a class="remove" href="javascript:void(0)" title="删除">',
		         '<i class="fa fa-remove"></i>删除',
		         '</a>|'+
		         '<a class="user_parameter" href="javascript:void(0)" title="用户常用可变参数">',
		         '<i class="fa fa-cog"></i>用户参数',
		         '</a>|'+
		         '<a class="brightness_parameter" href="javascript:void(0)" title="亮度调节">',
		         '<i class="fa fa-lightbulb-o"></i>亮度调节',
		         '</a>'        
		    ].join('');
		}
}

window.operateEvents = {
        'click .edit': function (e, value, row, index) {  
			
			$('#group_edit_name').val(row.group_name);
			$('#group_edit_packLength').val(row.maxPackLength);
			$('#group_edit_width').val(row.group_screenwidth);
			$('#group_edit_height').val(row.group_screenheight);	
						
			$('#group_edit_project').parents('.col-md-6').css('display','none');
        	$('#modal_group_edit').attr("data-type",row.group_sn);
        	$('#modal_group_edit').attr("data-index",index);
    		$('#modal_group_edit').modal('show');
        },
        'click .remove': function (e, value, row, index) {        
        	$("#modal_group_delete").attr("data-type",row.group_sn);
        	$("#modal_group_delete").modal('show');	             	
        },
        'click .user_parameter': function (e, value, row, index) {  
        	//用户调节可变参数
        	var parameter={
    				groupid:parseInt(row.group_sn),
    				setType:"user",    				
    			};
    		get_parameter(parameter);
    		
        	$("#modal_user_parameter").attr("data-type",row.group_sn);
        	$("#modal_user_parameter").modal('show');	             	
        },
        'click .brightness_parameter': function (e, value, row, index) {
        	var parameter={
    				groupid:parseInt(row.group_sn),
    				setType:"brightness",  
    				SetBrightnessMode:2
    			};
    		get_parameter(parameter);
        	//时间亮度参数
        	$("#modal_brightness_parameter").attr("data-type",row.group_sn);
        	$("#modal_brightness_parameter").modal('show');	             	
        },
        'click .basic_parameter': function (e, value, row, index) {  
        	var parameter={
    				groupid:parseInt(row.group_sn),
    				setType:"basic",    				
    			};
        	get_parameter(parameter);
        	//基本参数
        	$("#modal_basic_parameter").attr("data-type",row.group_sn);
        	$("#modal_basic_parameter").modal('show');	             	
        },
        'click .brightness2_parameter': function (e, value, row, index) {  
        	var parameter={
    				groupid:parseInt(row.group_sn),
    				setType:"brightness",
    				SetBrightnessMode:1
    			};
        	get_parameter(parameter);
        	//时间亮度
        	$("#modal_brightness2_parameter").attr("data-type",row.group_sn);
        	$("#modal_brightness2_parameter").modal('show');	             	
        }
    };
//获取项目列表
function getprojectList()
{
	$.ajax({  
        url:"/getProjectList",          
        type:"post",  
        dataType:"json", 
        data:{
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
        	},
        success:function(data)  
        {       	  
        	if(data!=null && data.length>0)    		
        		{    
        			$('#group_edit_project').empty();
	        		for(var i=0;i<data.length;i++)
					{	
	        			var project = data[i];
	        			var projectId = project.projectid;
	        			var projectName = project.projectname;
	        			var option="";
        				if(i==0)
						{
        					option = "<option selected value='"+projectId+"'>"+projectName+"</option>";						
						}
        				else
						{option = "<option value='"+projectId+"'>"+projectName+"</option>";}
        				
	        			$('#group_edit_project').append(option);
					}	        		
        		}        	
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  getProjectList 错误");            
          }  
    });
}
//获取分组
function getGroup()
{
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
	        	var ArrayTable = [];
	        	if(data!=null && data.length>0)    		
	        		{
	        			sessionStorage.setItem('grpsinfo', JSON.stringify(data));	
	        			
	        			grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	        			for(var i=0;i<grpsinfo.length;i++)
	        			{
	        				var grpid=grpsinfo[i].grpid;
	        				var grpname = grpsinfo[i].grpname;
	        				var screenwidth = grpsinfo[i].screenwidth;
	        				var screenheight = grpsinfo[i].screenheight;
	        					        				
	        				var item={
	        						group_sn:grpid,
	        						group_name:grpname,
	        						maxPackLength:grpsinfo[i].maxPackLength,
	        						group_screenwidth:screenwidth,
	        						group_screenheight:screenheight
		        			};
		        			
		        			ArrayTable.push(item);
	        			}
	        			$("#groupManger_table").bootstrapTable('load', ArrayTable);        			
	        		}
	        	else
	        		{$("#groupManger_table").bootstrapTable('removeAll');}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  getGroup 错误");	            
	          }  
	    });
		}
	else
		{
		if(grpsinfo.length>0)
			{
			var ArrayTable = [];
			for(var i=0;i<grpsinfo.length;i++)
			{
				var grpid=grpsinfo[i].grpid;
				var grpname = grpsinfo[i].grpname;
				var screenwidth = grpsinfo[i].screenwidth;
				var screenheight = grpsinfo[i].screenheight;	
				
				var item={
						group_sn:grpid,
						group_name:grpname,
						maxPackLength:grpsinfo[i].maxPackLength,
						group_screenwidth:screenwidth,
						group_screenheight:screenheight
    			};
    			
    			ArrayTable.push(item);
			}
			
			$("#groupManger_table").bootstrapTable('load', ArrayTable);
			}
		else
		{$("#groupManger_table").bootstrapTable('removeAll');}
		}	
}
//获取省份列表
function getProvinceList()
{
	$.ajax({  
        url:"/GetProvinceList",          
        type:"post",  
        dataType:"json", 
        data:{	        	
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},
        success:function(data)  
        {       	  
        	if(data!=null && data.result=='success')    		
        		{    
        			$('#select_ProvinceList').empty();
        			var ProvinceList=data.ProvinceList.split("|")
	        		for(var i=0;i<ProvinceList.length;i++)
					{	
	        			var province = ProvinceList[i];	        			
	        			var option="";
        				if(i==0)
						{
        					option = "<option selected value='"+province+"'>"+province+"</option>";	
        					getCityList(province);
						}
        				else
						{option = "<option value='"+province+"'>"+province+"</option>";}
        				
	        			$('#select_ProvinceList').append(option);
					}	        		
        		}
        	else{ alertMessage(1, "警告", data.resultMessage); }
        },  
        error: function() { 
        	alertMessage(2, "异常", "ajax 函数  GetProvinceList 错误");            
          }  
    });
}
//获取城市列表
function getCityList(ProvinceName)
{
	$.ajax({  
        url:"/GetCityList",          
        type:"post",
        data:{	        	
        	ProvinceName:ProvinceName,//$('#select_ProvinceList').val()
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.result=='success')    		
        		{    
        			$('#select_CityList').empty();
        			var CityList=data.CityList.split("|")
	        		for(var i=0;i<CityList.length;i++)
					{	
	        			var City = CityList[i];	        			
	        			var option="";
        				if(i==0)
						{
        					option = "<option selected value='"+City+"'>"+City+"</option>";						
						}
        				else
						{option = "<option value='"+City+"'>"+City+"</option>";}
        				
	        			$('#select_CityList').append(option);
					}	        		
        		}
        	else{ alertMessage(1, "警告", data.resultMessage); }
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  GetCityList 错误");            
          }  
    });
}
//获取城市列表
function getSelectCityList(ProvinceName,CityName)
{
	$.ajax({  
        url:"/GetCityList",          
        type:"post",
        data:{	        	
        	ProvinceName:ProvinceName,//$('#select_ProvinceList').val()
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.result=='success')    		
        		{    
        			$('#select_CityList').empty();
        			var CityList=data.CityList.split("|")
	        		for(var i=0;i<CityList.length;i++)
					{	
	        			var City = CityList[i];	        			
	        			var option="";
        				if(CityName==City)
						{
        					option = "<option selected value='"+City+"'>"+City+"</option>";						
						}
        				else
						{option = "<option value='"+City+"'>"+City+"</option>";}
        				
	        			$('#select_CityList').append(option);
					}	        		
        		}
        	else{ alertMessage(1, "警告", data.resultMessage); }
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  GetCityList 错误");            
          }  
    });
}
//编辑模态确定按钮
function model_eidtgroup()
{
	var grpname = $('#group_edit_name').val();
	var grpwidth = $('#group_edit_width').val();
	var grpheight = $('#group_edit_height').val();
		
	if(grpname==null && grpname=='')
		{alertMessage(1, "警告", "分组名不能为空!");return;}
	
	if(grpwidth==null && grpwidth=='')
	{alertMessage(1, "警告", "宽度不能为空!");return;}
	
	if(grpheight==null && grpheight=='')
	{alertMessage(1, "警告", "高度不能为空!");return;}
				
	if(parseInt($("#modal_group_edit").attr("data-type"))==0)//创建
		{
		$.ajax({  
	        url:"/CreatGroup",          
	        type:"post", 
	        data:{	        	
	        	grpname:grpname,
	        	projectid:$('#group_edit_project').val(),
	        	packLength:parseInt($('#group_edit_packLength').val()),
	        	grpwidth:grpwidth,
	        	grpheight:grpheight,
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{				        			
	        				var item={
	        						group_sn:data.groupid,
	        						group_name:grpname,
	        						maxPackLength:parseInt($('#group_edit_packLength').val()),
	        						group_screenwidth:grpwidth,
	        						group_screenheight:grpheight
		        			};
		        			
		        			$('#groupManger_table').bootstrapTable('append', item);
		        			
		        			$('#modal_group_edit').modal('hide');
		        			
		        			grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
		        			var grpitem={};
		        			grpitem.grpid=data.groupid;
		        			grpitem.grpname=grpname;
		        			grpitem.screenwidth=grpwidth;
		        			grpitem.screenheight=grpheight;
		        			grpitem.maxPackLength=parseInt($('#group_edit_packLength').val());
		        			grpitem.pubid=100;
		        			grpitem.plpubid=100;
		        			grpitem.delindex=0;
		        			
		        			grpsinfo.push(grpitem);
		        			
		        			sessionStorage.setItem('grpsinfo', JSON.stringify(grpsinfo));	
						}
		        		else
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  CreatGroup 错误"); 	            
	          }  
	    });
		}
	else//编辑
		{
		$.ajax({  
	        url:"/EditGroup",          
	        type:"post", 
	        data:{
	        	grpid:parseInt($("#modal_group_edit").attr("data-type")),
	        	grpname:grpname,
	        	packLength:parseInt($('#group_edit_packLength').val()),
	        	grpwidth:grpwidth,
	        	grpheight:grpheight,
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
				}, 
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{		        					        			
		        			var item={		        					
		        					group_name:grpname,
		        					maxPackLength:parseInt($('#group_edit_packLength').val()),
		        					group_screenwidth:grpwidth,		        					
		        					group_screenheight:grpheight
		        			};		        			
		        			var rowIndex = parseInt($('#modal_group_edit').attr("data-index"));
		        			var map={
		        					index:rowIndex,
		        					row: item
		        			};
		        			$("#groupManger_table").bootstrapTable("updateRow",map);	
		        			
		        			grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
		        			for(var i=0;i<grpsinfo.length;i++)
		        			{
		        				var grpid=grpsinfo[i].grpid;
		        				if(parseInt($("#modal_group_edit").attr("data-type"))==grpid)
		        					{		        					
				        				grpsinfo[i].grpname=grpname;
				        				grpsinfo[i].maxPackLength=parseInt($('#group_edit_packLength').val());
				        				grpsinfo[i].screenwidth=grpwidth;
				        				grpsinfo[i].screenheight=grpheight;
				        				break;
		        					}
		        			}
		        			
		        			sessionStorage.setItem('grpsinfo', JSON.stringify(grpsinfo));	
		        			
		        			$('#modal_group_edit').modal('hide');
						}
		        		else
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	        	alertMessage(2, "异常", "ajax 函数  EditGroup 错误");  
	          }  
	    });
		}
}
//参数设置
function set_parameter(parameter)
{		
	$.ajax({  
        url:"/Set_parameter",          
        type:"post",
        data:{	        	
        	parameter:JSON.stringify(parameter),
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.result=='success')    		
        		{        
        			switch(parameter.setType)
        			{
        			case "user":{
        				$("#modal_user_parameter").modal('hide');
        			};break;
        			case "brightness":{
        				$("#modal_brightness_parameter").modal('hide');
        				$("#modal_brightness2_parameter").modal('hide');
        			};break;
        			case "basic":{
        				$("#modal_basic_parameter").modal('hide');
        			};break;
        			}
        		}
        	else{ alertMessage(1, "警告", data.resultMessage); }
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  Set_parameter 错误");  
          }  
    });
}
//参数获取
function get_parameter(parameter)
{		
	$.ajax({  
        url:"/Get_parameter",          
        type:"post",
        data:{	        	
        	parameter:JSON.stringify(parameter),
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.result=='success')    		
        		{        
        			switch(parameter.setType)
        			{
        			case "user":{
        				$("#DefaulText").val(data.DefaulText);
        				$("#AlarmText").val(data.AlarmText);
        				$("#setZhaoMingMode").val(data.setZhaoMingMode);
        				$("#m_ZhaoMingTimeStart").val(data.m_ZhaoMingTimeStart);
        				$("#m_ZhaoMingTimeEnd").val(data.m_ZhaoMingTimeEnd);
        				//$("#modal_user_parameter").modal('hide');
        			};break;
        			case "brightness":{
        				var SetBrightnessMode = data.SetBrightnessMode;
        				var BrightnessValueArray = data.BrightnessValueArray.split("|");
        				if(parameter.SetBrightnessMode==2)
        					{        				
	        				$("#select_ProvinceList").val(data.ProvinceName);
	        				getSelectCityList(data.ProvinceName,data.CityName);
        				
	        				$("#brightness1").val(BrightnessValueArray[0]);
	        				$("#brightness2").val(BrightnessValueArray[1]);
	        				$("#brightness3").val(BrightnessValueArray[2]);
	        				$("#brightness4").val(BrightnessValueArray[3]);
	        				$("#brightness5").val(BrightnessValueArray[4]);
	        				$("#brightness6").val(BrightnessValueArray[5]);
	        				$("#brightness7").val(BrightnessValueArray[6]);
	        				$("#brightness8").val(BrightnessValueArray[7]);
        					}
        				else
        					{
        					var BrightnessTimeArray= JSON.parse(data.BrightnessTimeArray);        					
        					if(SetBrightnessMode==1)
        						{
	        					$("#time1").val(BrightnessTimeArray[0]);
		        				$("#time2").val(BrightnessTimeArray[1]);
		        				$("#time3").val(BrightnessTimeArray[2]);
		        				$("#time4").val(BrightnessTimeArray[3]);
		        				$("#time5").val(BrightnessTimeArray[4]);
		        				$("#time6").val(BrightnessTimeArray[5]);
		        				$("#time7").val(BrightnessTimeArray[6]);
		        				$("#time8").val(BrightnessTimeArray[7]);
        						}
        					else
        						{
        						$("#time1").val("03:00");
		        				$("#time2").val("05:00");
		        				$("#time3").val("06:00");
		        				$("#time4").val("07:00");
		        				$("#time5").val("16:00");
		        				$("#time6").val("17:00");
		        				$("#time7").val("19:00");
		        				$("#time8").val("21:00");
        						}
        					$("#brightnessB1").val(BrightnessValueArray[0]);
	        				$("#brightnessB2").val(BrightnessValueArray[1]);
	        				$("#brightnessB3").val(BrightnessValueArray[2]);
	        				$("#brightnessB4").val(BrightnessValueArray[3]);
	        				$("#brightnessB5").val(BrightnessValueArray[4]);
	        				$("#brightnessB6").val(BrightnessValueArray[5]);
	        				$("#brightnessB7").val(BrightnessValueArray[6]);
	        				$("#brightnessB8").val(BrightnessValueArray[7]);
        					}
        				//$("#modal_brightness_parameter").modal('hide');
        			};break;
        			case "basic":{
        				$("#Showversion").val(data.Showversion);
        				$("#LinkTime").val(data.LinkTime);
        				$("#MaxReceiveLen").val(data.MaxReceiveLen);
        				$("#BootstrapperWaitingTime").val(data.BootstrapperWaitingTime);
        				$("#Test").val(data.Test);
        				$("#Energy").val(data.Energy);	
        				$("#DTULink").val(data.DTULink);
        				//$("#modal_basic_parameter").modal('hide');
        			};break;
        			}
        		}
        	else{ alertMessage(1, "警告", data.resultMessage); }
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  Get_parameter 错误");             
          }  
    });
}