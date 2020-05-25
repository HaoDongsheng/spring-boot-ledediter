var arrpms=["广告编辑","广告审核","广告发布","分组管理","用户管理","车辆管理"];
var isDisplayProjectid=false;
var selectProjectid="";
$(function(){
	
	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));
	
	if(adminInfo.adminlevel>=2)
		{
		$('#btn_table_add').css("display","none");
		}
	else {
		$('#btn_table_add').css("display","block");
	}
	
	if(adminInfo.issuperuser==1)
		{isDisplayProjectid=true;}
	
	initBTabel();
	
	getprojectList();				
		
	$('#select_project').change(function() {
		
		selectProjectid = $('#select_project').val();
		getuserList($('#select_project').val());
	});
		
	//二级用户修改密码
	$('#btn_user_changPwd').click(function(){
		
		if($('#old_pwd').val().trim()==null || $('#old_pwd').val().trim()=='')
		{alertMessage(1, "警告", "旧密码不能为空!");return;}
		
		if($('#new_pwd').val().trim()==null || $('#new_pwd').val().trim()=='')
		{alertMessage(1, "警告", "新密码不能为空!");return;}
		
		if($('#new_pwd').val().trim() != $('#ok_pwd').val().trim())
		{alertMessage(1, "警告", "新密码和确认密码不一致!");return;}
		
		var user_sn = parseInt($('#modal_user_changPwd').attr("data-type"));
		
		$.ajax({  
	        url:"/ChangPassword",          
	        type:"post", 
	        data:{	        	
	        	adminid: user_sn,
	        	oldPwd: $('#old_pwd').val().trim(),
	        	newPwd: $('#new_pwd').val().trim(),
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data!=null)    		
	        		{
		        		if(data.result=='success')
						{		        			
		        			$('#modal_user_changPwd').modal('hide');	
						}
		        		else
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  ChangPassword 错误"); 	            
	          }  
	    });
	});	
	
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
	        	adminid: parseInt($("#modal_user_delete").attr("data-type")),
	        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
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
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  DeleteUser 错误"); 	            
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
            field: 'projectid',
            title: '项目id',
            visible: isDisplayProjectid            
        }, {
            field: 'user_pwd',
            title: '密码',
            visible: false            
        }, {
            field: 'user_level',
            title: '用户级别',
        	visible: false 
        }, {
            field: 'user_permission',
            title: '权限',
            formatter: function (value, row, index) {            	
            	var permission = value;
            	
            	var option;            	
            	   
            	var pmArr=null;
            	if(permission != null && permission!="")
            		{
            		pmArr = permission.split(",");
            		}
            	var headOption = "";            	    
            	
            	if(pmArr!=null)
            		{
	            	for(var i=0;i<pmArr.length;i++)
	    			{
	            		var txt=pmArr[i];
		    			headOption = headOption + "<option value='"+txt+"'>"+txt+"</option>";                    			
	    			}
            		}
            	option = '<select class="form-control">'+ headOption + '</select>';
            	            	
                return option;
            }         
        }, {
            field: 'user_group',
            title: '分组',
            formatter: function (value, row, index) {            	
            	var groups = value;
            	
            	var option;            	
            	   
            	var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
            	
            	var gpArr=null;
            	if(groups != null && groups != "")
            		{
            		gpArr = groups.split(",");
            		}
            	var headOption = "";            	    
            	
            	if(gpArr!=null)
        		{
	            	for(var j=0;j<gpArr.length;j++)
	    			{
	            		for(var i=0;i<grpsinfo.length;i++)
	        			{
	        				var grpid=grpsinfo[i].grpid;
	        				var grpname = grpsinfo[i].grpname;
	        				var screenwidth = grpsinfo[i].screenwidth;
	        				var screenheight = grpsinfo[i].screenheight;	
	        				
	        				if(gpArr[j]==grpid)
	        				{
	        					headOption = headOption + "<option value='"+grpid+"'>"+grpname+"</option>";
	        				}					    			
	        			}	                   			
	    			}
        		}
            	else
        		{
            		for(var i=0;i<grpsinfo.length;i++)
        			{
        				var grpid=grpsinfo[i].grpid;
        				var grpname = grpsinfo[i].grpname;
        				var screenwidth = grpsinfo[i].screenwidth;
        				var screenheight = grpsinfo[i].screenheight;	
        				
        				if(row.projectid==grpsinfo[i].projectid)
        				{
        					headOption = headOption + "<option value='"+grpid+"'>"+grpname+"</option>";
        				}					    			
        			}			
        		}
            	
            	option = '<select class="form-control">'+ headOption + '</select>';
            	            	
                return option;
            }         
        }, {
            field: 'user_status',
            title: '状态'
          
        } , {
            field: 'user_exp',
            title: '到期时间',
        	formatter: function (value, row, index) {
        		if(value==null || value=="")
        			{return "永久"}
        		else{return value;}
        	}
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
	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));
	
	if(adminInfo.adminlevel>=2)
		{
		return [
	         '<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
	            '<i class="fa fa-edit"></i>编辑',
	            '</a>'
	    ].join('');
		}
	else {
	    return [
	         '<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
	            '<i class="fa fa-edit"></i>编辑',
	            '</a>'+
	            '<a class="remove" href="javascript:void(0)" style="margin:0px 5px;" title="删除">',
	            '<i class="fa fa-remove	"></i>删除',
	            '</a>'
	    ].join('');
	}

}

window.operateEvents = {
        'click .edit': function (e, value, row, index) {  

        	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));
        	
        	if(adminInfo.adminlevel>=2)
        		{        		
        		$('#modal_user_changPwd').attr("data-type",row.user_sn);            	
        		$('#modal_user_changPwd').modal('show');
        		}
        	else {				
			var name = row.user_name;
			var pwd = row.user_pwd;
			var level = row.user_level;
			var pm = row.user_permission;			
			var status = row.user_status;
			var groups = row.user_group;
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
			
			getGroup(row.projectid,groups);			
        	$('#modal_user_edit').attr("data-type",row.user_sn);
        	$('#modal_user_edit').attr("data-index",index);
    		$('#modal_user_edit').modal('show');
        	}
        },
        'click .remove': function (e, value, row, index) {        
        	$("#modal_user_delete").attr("data-type",row.user_sn);
        	$("#modal_user_delete").modal('show');	        	
        }
    };

//获取分组
function getGroup(projectid,groups)
{
	$('#user_edit_group').empty();
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
	        				if(projectid==grpsinfo[i].projectid)
        					{
	        					var checked='';
	        					if(groups==null || groups=="")
	        						{checked='checked';}
	        					else {
	        						if(groups.split(",").indexOf(grpid.toString())>-1)
	        							{checked='checked';}
	        						else {
	        							checked='';
									}
								}
	        					var label ="<input type='checkbox' value='"+grpid+"' "+checked+" >"+grpname+"</input>";
	        					$('#user_edit_group').append(label);
        					}	        						        					        			
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
		if(grpsinfo.length>0)
			{
			var ArrayTable = [];
			for(var i=0;i<grpsinfo.length;i++)
			{
				var grpid=grpsinfo[i].grpid;
				var grpname = grpsinfo[i].grpname;
				var screenwidth = grpsinfo[i].screenwidth;
				var screenheight = grpsinfo[i].screenheight;	
				
				if(projectid==grpsinfo[i].projectid)
				{
					var checked='';
					if(groups==null || groups=="")
						{checked='checked';}
					else {
						if(groups.split(",").indexOf(grpid.toString())>-1)
							{checked='checked';}
						else {
							checked='';
						}
					}
					var label ="<input type='checkbox' value='"+grpid+"' "+checked+" >"+grpname+"</input>";
					$('#user_edit_group').append(label);
				}					    			
			}			
			}		
		}	
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
	        					
	        					getuserList(projectId);
	        					selectProjectid = projectId;
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
	        					getuserList(projectId);
	        					selectProjectid = projectId;
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
//获取用户列表信息
function getuserList(SelectProjectid)
{
	getGroup(SelectProjectid,null);
	
	$.ajax({  
        url:"/getUserList",          
        type:"post",  
        dataType:"json", 
        data:{    
        	projectid:SelectProjectid,
        	adminInfo:localStorage.getItem("adminInfo")
        	},
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
	        			var adminGrps=admin.groupids;
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
	        					grps=adminGrps;
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
	        					projectid:admin.projectid,
	        					user_pwd:pwd,
	        					user_level:level,
	        					user_permission:Permission,
	        					user_group:grps,
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
        	alertMessage(2, "异常", "ajax 函数  getUserList 错误"); 
          }  
    });
}
//权限选择转字符串
function groupinput2value()
{
	var adminGrps="";
	
	var grpinputs = $("#user_edit_group input[type=checkbox]:checked");	
	
	for(var g=0;g<grpinputs.length;g++)
		{
		var inputvalue = $(grpinputs[g]).val();
		adminGrps+=inputvalue+",";
		}
	if(adminGrps.length>0)
		{
		adminGrps=adminGrps.substring(0,adminGrps.length - 1);
		}
	return adminGrps;
}
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
	if(selectProjectid=="" || selectProjectid==null)
	{
	alertMessage(1, "警告", "项目id不能为空!");return;
	}
	var adminname = $('#user_edit_name').val().trim();
	var adminpwd = $('#user_edit_pwd').val().trim();
	var inherit=0;	
	var adminstatus=$('#user_edit_status').val().trim();			
	var expdate = $('#user_edit_exp').val().trim();	
	
	var admingrps=groupinput2value();
	
	var adminpermission=permissioninput2value();
	
	if(adminname==null || adminname=='')
		{alertMessage(1, "警告", "用户名不能为空!");return;}
	
	if(adminpwd==null || adminpwd=='')
	{alertMessage(1, "警告", "密码不能为空!");return;}	
	
	if(admingrps==null || admingrps=='')
	{alertMessage(1, "警告", "请选择分组!");return;}					
	
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
	        	admingrps:admingrps,
	        	projectid:selectProjectid,
	        	inherit:inherit,
	        	adminInfo:localStorage.getItem("adminInfo")
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
		        					projectid:selectProjectid,
		        					user_status:Status,
		        					user_group:admingrps,
		        					user_exp:expDate
		        			};
		        			
		        			$('#userManger_table').bootstrapTable('append', item);
		        			
		        			$('#modal_user_edit').modal('hide');
						}
		        		else
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() {  
	        	alertMessage(2, "异常", "ajax 函数  CreatUser 错误");  
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
	        	admingrps:admingrps,
	        	inherit:inherit,
	        	padminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
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
		        					projectid:selectProjectid,
		        					user_status:Status,
		        					user_group:admingrps,
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
						{alertMessage(1, "警告", data.resultMessage);}
	        		}
	        },  
	        error: function() { 
	        	alertMessage(2, "异常", "ajax 函数  EditUser 错误"); 	            
	          }  
	    });
		}
}