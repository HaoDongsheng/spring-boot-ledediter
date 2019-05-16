var arrpms=["广告编辑","广告审核","广告发布","分组管理","用户管理","车辆管理"];

$(function(){
	$( ".modal" ).draggable();
	
	initBTabel();
	
	getprojectList();
	
	getuserList();
	
	//模态确定按钮
	$('#btn_user_edit').click(function(){
		 model_eidtuser();
	});	
	
	//模态确定按钮
	$('#btn_user_delete').click(function(){
		$.ajax({  
	        url:"/DeleteUser",          
	        type:"post", 
	        data:{	        	
	        	adminid: parseInt($("#modal_user_delete").attr("data-type"))
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{
		        			$("#userManger_table").bootstrapTable("remove", {field: "user_sn",values: [parseInt($("#modal_user_delete").attr("data-type"))]});
		        			$('#modal_user_delete').modal('hide');	
						}
		        		else
						{alert(data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	            alert("error");  
	          }  
	    });
	});	
	
	$('#btn_table_add').click(function(){				
		$('#user_edit_name').val('');
		$('#user_edit_pwd').val('');
		$('#user_edit_status').val(0);	
		$('#user_edit_exp').val('2022-01-01');
				
		$('#user_edit_grps input').attr("checked", false);
		$('#user_edit_pm input').attr("checked", false);
		
		if($('#user_edit_project option').length > 1)
		{$('#user_edit_project').parents('.row').css('display','inline');}
		else
		{$('#user_edit_project').parents('.row').css('display','none');}
		
		$('#modal_user_edit').attr("data-type",0);
		$('#modal_user_edit').modal('show');
					
	});	
	
});

function initBTabel()
{
    $('#userManger_table').bootstrapTable({            
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
        uniqueId: "user_sn",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
            title: '序号',
            field: 'user_sn'
        }, {
            field: 'user_name',
            title: '用户名'            
            
        }, {
            field: 'user_pwd',
            title: '密码'
            
        }, {
            field: 'user_level',
            title: '用户级别'
         
        }, {
            field: 'user_permission',
            title: '权限'
         
        }, {
            field: 'user_status',
            title: '状态'
          
        } , {
            field: 'user_exp',
            title: '到期时间'
          
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

function operateFormatter(value, row, index) {
    return [
         '<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
            '<i class="fa fa-edit"></i>编辑',
            '</a>'+
            '<a class="remove" href="javascript:void(0)" style="margin:0px 5px;" title="删除">',
            '<i class="fa fa-remove	"></i>删除',
            '</a>'
    ].join('');
}

window.operateEvents = {
        'click .edit': function (e, value, row, index) {  

			var name = row.user_name;
			var pwd = row.user_pwd;
			var level = row.user_level;
			var pm = row.user_permission;			
			var status = row.user_status;
			var exp = row.user_exp;        				        				
			
			$('#user_edit_name').val(name);
			$('#user_edit_pwd').val(pwd);
			if(status=='正常')
			{$('#user_edit_status').val(0);}
			else{$('#user_edit_status').val(1);}	
			$('#user_edit_exp').val(exp);
									
			if(pm=='全部权限')
				{
					$('#user_edit_pm input').attr("checked", true);
				}
			else
				{
					var arrpm = pm.split(",");
					if(arrpm.length>0)
						{
							$('#user_edit_pm input').attr("checked", false);
							for(var p=0;p<arrpm.length;p++)
								{
								 var stringpm=arrpm[p];
								 $("#user_edit_pm input[value='"+stringpm+"']").attr("checked", true);
								}
						}
				}
			$('#user_edit_project').parents('.row').css('display','none');
        	$('#modal_user_edit').attr("data-type",row.user_sn);
        	$('#modal_user_edit').attr("data-index",index);
    		$('#modal_user_edit').modal('show');
        },
        'click .remove': function (e, value, row, index) {        
        	$("#modal_user_delete").attr("data-type",row.user_sn);
        	$("#modal_user_delete").modal('show');	        	
        }
    };
//获取项目列表
function getprojectList()
{
	$.ajax({  
        url:"/getProjectList",          
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.length>0)    		
        		{    
        			$('#user_edit_project').empty();
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
        				
	        			$('#user_edit_project').append(option);
					}	        		
        		}        	
        },  
        error: function() {  
            alert("error");  
          }  
    });
}
//获取用户列表信息
function getuserList()
{
	$.ajax({  
        url:"/getUserList",          
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null && data.length>0)    		
        		{
        			var ArrayTable = [];
	        		for(var i=0;i<data.length;i++)
					{						
	        			var admin = data[i];
	        			var adminID=admin.adminid;
	        			var adminName=admin.adminname;
	        			var adminPwd=admin.adminpwd;
	        			var adminStatus=admin.adminstatus;
	        			var expDate=admin.expdate;
	        			var adminPermission=admin.adminpermission;
	        			var adminGrps=admin.admingrps;
	        			var adminLevel=admin.adminlevel;
	        			var parentId=admin.parentid;
	        			var inherit=admin.inherit;
	        			var GrpCount=admin.grpcount;
	        			var adminCount=admin.admincount;
	        			var isSuperuser=admin.issuperuser;

	        			var sn=adminID;
	        			var name=adminName;
	        			var pwd=adminPwd;
	        			var level="";
	        			var Permission="";
	        			var grps="";
	        			var Status="";
	        			var expDate="";
	        			
	        			if(isSuperuser==0)
	        				{
	        					switch(adminLevel)
	        					{
	        					case 1:{level="一级用户";};break;
	        					case 2:{level="二级用户";};break;
	        					}
	        						        					
	        					var pm=permissionvalue2String(adminPermission);
	        					Permission=pm;
	        					
	    	        			if(adminStatus==0)
	    	        				{Status="正常";}
	    	        			else
	    	        				{Status="停用";}
	    	        			
	    	        			expDate=expDate;
	        				}
	        			else
	        				{
	        					level="超级用户";
	        					Permission="全部权限";
	    	        			grps="所有分组";
	    	        			Status="正常";
	    	        			expDate="永久";
	        				}
	        			
	        			var item={
	        					user_sn:sn,
	        					user_name:name,
	        					user_pwd:pwd,
	        					user_level:level,
	        					user_permission:Permission,
	        					user_status:Status,
	        					user_exp:expDate
	        			};
	        			
	        			ArrayTable.push(item);
					}
	        		$("#userManger_table").bootstrapTable('load', ArrayTable);        			
        		}
        	else
        		{$("#userManger_table").bootstrapTable('removeAll');}
        },  
        error: function() {  
            alert("error");  
          }  
    });
}
//编辑

//权限选择转字符串
function permissioninput2value()
{
	var adminpermission="";
	
	var pminputs = $("#user_edit_pm input[type=checkbox]:checked");	
	
	for(var p=0;p<arrpms.length;p++)
		{
		var pm=0;
		for(var i = 0;i < pminputs.length;i++){
			var inputvalue = $(pminputs[i]).val();			
			if(arrpms[p]==inputvalue)
				{pm=1;break;}
		}
		adminpermission+=pm;
		}
	return adminpermission;
}
//分组值变成可读字符串
function permissionvalue2String(permissionvalue)
{
	var permissionString="";
	
	for(var a=0;a<arrpms.length;a++){	        					    
	    if(parseInt(permissionvalue.substring(a,a+1))==1)
	    	{permissionString += arrpms[a]+",";}
	}
	if(permissionString.length>1)
	{permissionString=permissionString.substring(0,permissionString.length - 1);}
	else{permissionString="";}
	
	return permissionString;
}
//模态确定按钮
function model_eidtuser()
{
	var adminname = $('#user_edit_name').val();
	var adminpwd = $('#user_edit_pwd').val();
	var inherit=0;
	if($('#inherit').prop('checked'))
		{inherit=1;}
	var adminstatus=$('#user_edit_status').val();			
	var expdate = $('#user_edit_exp').val();	
	var adminpermission=permissioninput2value();
	
	if(adminname==null && adminname=='')
		{alert("用户名不能为空！");return;}
	
	if(adminpwd==null && adminpwd=='')
	{alert("密码不能为空！");return;}		
			
	var projectid=$('#user_edit_project').val();
	
	var user_sn = parseInt($('#modal_user_edit').attr("data-type"));
	if(user_sn==0)//创建
		{
		$.ajax({  
	        url:"/CreatUser",          
	        type:"post", 
	        data:{	        	
	        	adminname:adminname,
	        	adminpwd:adminpwd,
	        	adminstatus:adminstatus,
	        	expdate:expdate,
	        	adminpermission:adminpermission,
	        	projectid:projectid,
	        	inherit:inherit
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{		        					        			
		        			var sn=data.adminid;
		        			var name=adminname;
		        			var pwd=adminpwd;
		        			var level="";
		        			var Permission="";		        			
		        			var Status="";
		        			var expDate="";
		        					        		
        					switch(data.adminlevel)
        					{
        					case 1:{level="一级用户";};break;
        					case 2:{level="二级用户";};break;
        					}
        						        					
        					var pm=permissionvalue2String(adminpermission);
        					Permission=pm;
        					
    	        			if(adminstatus==0)
    	        				{Status="正常";}
    	        			else
    	        				{Status="停用";}
    	        			
    	        			expDate=expDate;		        			
		        			var item={
		        					user_sn:sn,
		        					user_name:name,
		        					user_pwd:pwd,
		        					user_level:level,
		        					user_permission:Permission,
		        					user_status:Status,
		        					user_exp:expDate
		        			};
		        			
		        			$('#userManger_table').bootstrapTable('append', item);
		        			
		        			$('#modal_user_edit').modal('hide');
						}
		        		else
						{alert(data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	            alert("error");  
	          }  
	    });
		}
	else//编辑
		{
		$.ajax({  
	        url:"/EditUser",          
	        type:"post", 
	        data:{
	        	adminid:user_sn,
	        	adminname:adminname,
	        	adminpwd:adminpwd,
	        	adminstatus:adminstatus,
	        	expdate:expdate,
	        	adminpermission:adminpermission,	        	
	        	inherit:inherit
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{		        				
		        			var Status='停用';
		        			if(adminstatus==0)//状态
    						{Status='正常';}
		        			var item={		        					
		        					user_name:adminname,
		        					user_pwd:adminpwd,		        					
		        					user_permission:permissionvalue2String(adminpermission),
		        					user_status:Status,
		        					user_exp:expdate
		        			};
		        			var rowIndex = parseInt($('#modal_user_edit').attr("data-index"));
		        			var map={
		        					index:rowIndex,
		        					row: item
		        			};
		        			$("#userManger_table").bootstrapTable("updateRow",map);		        			
		        			$('#modal_user_edit').modal('hide');
						}
		        		else
						{alert(data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	            alert("error");  
	          }  
	    });
		}
}