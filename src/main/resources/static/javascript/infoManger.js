$(function(){
		
	initBTabel();
	
	getGroup();
	
	$("#grouplist").change(function(){			
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
	        				$('#input_group_grouplist1').css("display","none");
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
			$('#input_group_grouplist').css("display","inline");
			}
			else
			{$('#input_group_grouplist').css("display","none");}
		
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
				$('#input_group_grouplist1').css("display","none");
			}
		}
}//table初始化
//table初始化
function initBTabel()
{
    $('#auditinfo_table').bootstrapTable({            
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
                var pageSize = $('#auditinfo_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#auditinfo_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'infosn',
            title: '广告id',
            visible: false
            
        }, {
            field: 'infoname',
            title: '广告名称'
            
        }, {
            field: 'infoState',
            title: '广告状态',
        	formatter:function (value, row, index) { 
        		var returnString="";
        		switch (value) {
					case 0:{
						returnString="<span style='color:blue' title='草稿'>草稿</span>";
					};break;
					case 1:{
						returnString="<span style='color:red' title='已拒绝'>已拒绝</span>";
					};break;
					case 2:{
						returnString="<span style='color:red' title='已拒绝'>已拒绝</span>";
					};break;
					case 3:{
						returnString="<span style='color:green' title='已审核'>已审核</span>";
					};break;				
				}
        		
        		return returnString;
            	
            }
        }, {
            field: 'lifeAct',
            title: '开始日期'
         
        }, {
            field: 'lifeDie',
            title: '结束日期'
         
        }, {
            field: 'timelenght',
            title: '播放时长(秒)'
          
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
            '<i class="fa fa-remove"></i>删除',
            '</a>'
    ].join('');
}

window.operateEvents = {
        'click .edit': function (e, value, row, index) {        
        	$("#modal_details").attr("data-type",row.infosn);
        	$("#modal_details").modal('show'); 
        	getitem(row.infosn);
        },
        'click .remove': function (e, value, row, index) {        
        	$("#modal_allow").attr("data-type",row.infosn);
        	$("#modal_allow").modal('show');	
        }
    };

//按组取广告列表
function getadvListbyGrpid(grpid)
{		
	sessionStorage.setItem('selectgrpid', grpid);
	$.ajax({  
        url:"/getinfoListbyGrpidState", 
        data:{
        	Grpid:grpid, 
        	State:-1,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",
        success:function(data)  
        {               				
        	if(data!=null && data.length>0)
        		{   
        			var ArrayTable = [];
        			
	        		for(var i=0;i<data.length;i++)
					{
	        			var id=data[i].id;
	        			var advname=data[i].advname;	        			
	        			var lifeAct=data[i].lifeAct;
	        			var lifeDie=data[i].lifeDie;
	        			
	        			var playTimelength=data[i].playTimelength;
	        			var item={
	        					infosn:id,
	        					infoname:advname,
	        					infoState:data[i].infoState,
	        					lifeAct:lifeAct,
	        					lifeDie:lifeDie,
	        					timelenght:playTimelength
	        			};
	        			
	        			ArrayTable.push(item);
					}
	        		
					
					$("#auditinfo_table").bootstrapTable('load', ArrayTable);
        			
        		}
        	else
        		{$("#auditinfo_table").bootstrapTable('removeAll');}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getadvListbyGrpidState 错误");        	          
          }  
    });
}
