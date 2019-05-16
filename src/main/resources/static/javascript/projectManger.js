$(function(){
	initBTabel();
	
	getProjectlist();
});

function initBTabel()
{
    $('#projectinfo_table').bootstrapTable({            
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
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
            title: '序号',
            field: 'id',            
            formatter: function (value, row, index) {
                var pageSize = $('#projectinfo_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#projectinfo_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'projectId',
            title: '项目id'            
            
        }, {
            field: 'projectName',
            title: '项目名称'
            
        }, {
            field: 'ConnectParameters',
            title: '连接参数',
            visible: false         
        },{
            field: 'grouplist',
            title: '包含分组'
         
        }, {
            field: 'ourmodule',
            title: '我司模块'
         
        }, {
            field: 'userlist',
            title: '包含用户'         
        }, {
        	field: 'operate',
        	title: '操作',
        	align: 'center',
        	events: operateEvents,
        	formatter: operateFormatter
        }
        ]
    });
    
    $("#btn_table_add").click(function(){ 
    	$('#user_card').css('display','block');
		$('#group_card').css('display','block');
		
		$('#project_id').removeAttr("disabled");
		$('#project_id').val('');
    	$('#project_name').val('');
    	$('#select_our').val(1);
    	 	
    	$('#protocal_ip').val('');
    	$('#protocal_port').val('');
    	$('#protocal_name').val('');
    	$('#protocal_pwd').val('');
    	$('#protocal_type').val('博海科技XML1.0');
		
		$('#modal_CreateProject').attr("data-type",-1);
    	$('#modal_CreateProject').attr("data-index",-1);
    	
    	$("#modal_CreateProject").modal('show');
    });        
    
    $("#btn_createProject").click(function(){    			
    	var Protocal = {
    			IP : $('#protocal_ip').val(),
    			Port : $('#protocal_port').val(),
    			UserName : $('#protocal_name').val(),
    			Password : $('#protocal_pwd').val(),
    			Protocal : $('#protocal_type').val()
    	};
    	
    	var data_type = $('#modal_CreateProject').attr("data-type");
    	if(data_type==-1)
    		{
    		$.ajax({  
                url:"/createProject",
                data:{
                	projectid:$('#project_id').val(),
                	projectname:$('#project_name').val(),
                	isOurModule:$('#select_our').val(),
                	ConnectParameters:JSON.stringify(Protocal),
                	username:$('#user_name').val(),
                	userpwd:$('#user_pwd').val(),
                	groupname:$('#group_name').val(),
                	groupwidth:parseInt($('#group_width').val()),
                	groupheight:parseInt($('#group_height').val())
        			},  
                type:"post",  
                dataType:"json", 
                success:function(data)  
                {       	  
                	if(data.result=="success")
                		{ 
                		var isOurModule="是";
                		if($('#select_our').val()=="0")
                		{isOurModule="否";}            		
                		var item={
            					projectId:$('#project_id').val(),
            					projectName:$('#project_name').val(),
            					ConnectParameters:JSON.stringify(Protocal),
            					ourmodule:isOurModule,
            					grouplist:$('#group_name').val(),
            					userlist:$('#user_name').val()
            			};
                		
                		grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
            			var grpitem={};
            			grpitem.grpid=data.groupid;
            			grpitem.grpname=$('#group_name').val();
            			grpitem.screenwidth= data.groupwidth;
            			grpitem.screenheight=data.groupheight;
            			grpitem.pubid=100;
            			grpitem.plpubid=100;
            			grpitem.delindex=0;
            			
            			grpsinfo.push(grpitem);
            			
            			sessionStorage.setItem('grpsinfo', JSON.stringify(grpsinfo));
            			
                		$('#projectinfo_table').bootstrapTable('append', item);
                		$("#modal_CreateProject").modal('hide');
                		}
                	else
                		{
                			alert(data.resultMessage);        			
                		}        	
                },  
                error: function() {  
                	alert("ajax 函数  createProject 错误");            
                  }  
            });
    		}
    	else {
    		$.ajax({  
                url:"/updateProject",
                data:{
                	projectid:$('#project_id').val(),
                	projectname:$('#project_name').val(),
                	isOurModule:$('#select_our').val(),
                	ConnectParameters:JSON.stringify(Protocal)
        			},  
                type:"post",  
                dataType:"json", 
                success:function(data)  
                {       	  
                	if(data.result=="success")
                		{ 
                		var isOurModule="是";
                		if($('#select_our').val()=="0")
                		{isOurModule="否";}            		
                		var item={
            					projectId:$('#project_id').val(),
            					projectName:$('#project_name').val(),
            					ConnectParameters:JSON.stringify(Protocal),
            					ourmodule:isOurModule            					
            			};          
                		var index = $('#modal_CreateProject').attr("data-index");
                		$('#projectinfo_table').bootstrapTable('updateRow', {index: index, row: item});
                		$("#modal_CreateProject").modal('hide');
                		}
                	else
                		{
                			alert(data.resultMessage);        			
                		}        	
                },  
                error: function() {  
                	alert("ajax 函数  createProject 错误");            
                  }  
            });
		}    	    	    	    
    });    
}

function operateFormatter(value, row, index) {
    return [
    	'<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
        '<i class="fa fa-edit"></i>编辑',
        '</a>'+
        '<a class="remove" href="javascript:void(0)" style="margin:0px 5px;" title="删除">',
        '<i class="fa fa-remove"></i>删除',
        '</a>'
    ].join('');
}

window.operateEvents = {
		'click .edit': function (e, value, row, index) {  
			
			$('#user_card').css('display','none');
			$('#group_card').css('display','none');
			
			$('#project_id').attr("disabled", "disabled");
			$('#project_id').val(row.projectId);
        	$('#project_name').val(row.projectName);
        	if(row.ourmodule=='否')
        		{$('#select_our').val(0);}
        	else{$('#select_our').val(1);}
        	
        	
        	var Protocal = JSON.parse(row.ConnectParameters);
        	
        	$('#protocal_ip').val(Protocal.IP);
        	$('#protocal_port').val(Protocal.Port);
        	$('#protocal_name').val(Protocal.UserName);
        	$('#protocal_pwd').val(Protocal.Password);
        	$('#protocal_type').val(Protocal.Protocal);
        	
        	$('#modal_CreateProject').attr("data-type",row.projectId);
        	$('#modal_CreateProject').attr("data-index",index);
			$("#modal_CreateProject").modal('show');
        },
        'click .remove': function (e, value, row, index) {  
        	var projectId = row.projectId;
        	
        	$.ajax({  
                url:"/removeProject", 
                data:{
                	projectid:projectId
        			},  
                type:"post",  
                dataType:"json", 
                success:function(data)  
                {       	  
                	if(data.result=="success")
                		{  
                		$("#projectinfo_table").bootstrapTable("remove", {field: "projectId",values: [projectId]});
                		}
                	else
                		{
                			alert(data.resultMessage);        			
                		}        	
                },  
                error: function() {  
                	alert("ajax 函数  createProject 错误");            
                  }  
            });
        }
}

//按组取广告列表
function getProjectlist()
{			
	$.ajax({  
        url:"/getProjectlist",           
        type:"post",
        success:function(data)  
        {               				
        	if(data!=null && data.length>0)
        		{   
        			var ArrayTable = [];
        			
	        		for(var i=0;i<data.length;i++)
					{
	        			var projectid=data[i].projectid;
	        			var projectname=data[i].projectname;	        			
	        			var grouplist=data[i].grouplist;
	        			var userlist=data[i].userlist;
	        			var IsOurModule = data[i].IsOurModule;
	        			var ConnectParameters = data[i].ConnectParameters;
	        			
	        			var strOurModule="是";
	            		if(IsOurModule == "0")
	            		{strOurModule="否";} 
	            		
	        			var playTimelength=data[i].playTimelength;
	        			var item={
	        					projectId:projectid,
	        					projectName:projectname,
	        					ConnectParameters:ConnectParameters,
	        					ourmodule:strOurModule,
	        					grouplist:grouplist,
	        					userlist:userlist
	        			};
	        			
	        			ArrayTable.push(item);
					}
	        		
					
					$("#projectinfo_table").bootstrapTable('load', ArrayTable);
        			
        		}
        	else
        		{$("#projectinfo_table").bootstrapTable('removeAll');}
        },  
        error: function() {  
        	alert("ajax 函数  getProjectlist 错误");            
          }  
    });
}