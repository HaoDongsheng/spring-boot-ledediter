var selectplaylistid=0,rowIndex=-1,ptrowIndex=-1;

$(function(){
	
	$('#playlist_StartTime').timepicker({
		defaultTime:'9:00',
		minuteStep:1,
		showInputs:false,		
		explicitMode:true,
		showMeridian:false
		});
	
	$('#playlist_EndTime').timepicker({
		defaultTime:'9:00',
		minuteStep:1,
		showInputs:false,		
		explicitMode:true,
		showMeridian:false
		});
	
	getGroup();
	
	initInfolist();			
    
	$('#infolist_table').on('click-row.bs.table', function (e,row,$element) {
        
        if($($element).hasClass("changeColor"))
        	{
        	$($element).removeClass('changeColor');
        	rowIndex=-1;        	
        	}
        else {
        	$('#infolist_table .changeColor').removeClass('changeColor');
        	$($element).addClass('changeColor');
        	rowIndex = $element[0].rowIndex;
		}        
	});
	
	$('#infoitem_table').on('click-row.bs.table', function (e,row,$element) {        
        if($($element).hasClass("changeColor"))
        	{
        	$($element).removeClass('changeColor');
        	ptrowIndex=-1;        	
        	}
        else {
        	$('#infoitem_table .changeColor').removeClass('changeColor');
        	$($element).addClass('changeColor');
        	ptrowIndex = $element[0].rowIndex;
		} 
        
        infoSelectChange(row.infosn);
        
        refreshTableRowSelect();
	});
	
	$("#grouplist").change(function(){			
		getInfoList(parseInt($("#grouplist").val()));	    
	  });
	
	$("#list_create").click(function(){
		playlistCreate();
	});		
	
	$("#list_delete").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		playlistDeletebyid(playlistid);
		}
	});
	
	$("#list_publish").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		playlistPublishbyid(playlistid);
		}
	});		
	
	$("#list_attribute").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		playlistAttributebyid(playlistid);
		}
	});	
	
	//设置串口
	$("#btn_setSerialPort").click(function(){
		var SerialPort={
			"ComName":$("#ComName").val(),
			"BaudRate":parseInt($("#BaudRate").val())
		};
		localStorage.setItem("SerialPort",JSON.stringify(SerialPort));
		$("#modal_SerialPort").modal('hide');		
	});
	
	//串口发送
	$("#list_SerialPort").click(function(){
		if(selectplaylistid != 0)
		{
		var playlistid = selectplaylistid;
		SendSerialData(playlistid);
		}
	});
		
	$("#playlist").change(function(){
		playlistSelectChange(parseInt($("#playlist").val()));
	});		
	
	initBTabel();
});

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

function initBTabel()
{    
    $('#infolist_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        //toolbar: '#toolbar',                //工具按钮用哪个容器
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
                var pageSize = $('#infolist_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#infolist_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
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
            field: 'lifeAct',
            title: '开始日期'
         
        }, {
            field: 'lifeDie',
            title: '结束日期'
         
        }, {
            field: 'timelenght',
            title: '播放时长(秒)'
          
        }, {
        	field: 'operate',
        	title: '操作',
        	align: 'center',       
        	events: advoperateEvents,
        	formatter: advoperateFormatter
        }]
    });    
    
    $('#infoitem_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        //toolbar: '#toolbar',                //工具按钮用哪个容器
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
        height: 200,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
            field: 'infosn',
            title: '广告id',
            visible: false
            
        }, {
            field: 'infoname',
            title: '广告名称'
            
        }, {
            field: 'lifeAct',
            title: '开始日期'
         
        }, {
            field: 'lifeDie',
            title: '结束日期'
         
        }, {
            field: 'timelenght',
            title: '播放时长(秒)'
          
        },  {
            field: 'infopubid',
            title: '发布id',
            visible: false          
        }, {
        	field: 'operate',
        	title: '操作',
        	align: 'center',
        	events: operateEvents,
        	formatter: operateFormatter
        }]
    });
    
    $('#info_details_table').bootstrapTable({            
        method: 'get',                      //请求方式（*）
        toolbar: '#details_toolbar',                //工具按钮用哪个容器
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
                var pageSize = $('#info_details_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#info_details_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'itemContext',
            title: '内容'
            
        }, {
            field: 'itemLocationSize',
            title: '位置大小'
            
        }, {
            field: 'itemStyle',
            title: '参数'
         
        }
        ]
    });
}

function advoperateFormatter(value, row, index) {
    return [
    	'<a class="delete" href="javascript:void(0)" title="下架">',
        '<i class="fa fa-trash-o"></i>下架',
        '</a>'
    ].join('');
}

window.advoperateEvents = {
	'click .delete': function (e, value, row, index) {
		var item={		        					
				id:-1,
		};
		$("#infolist_table").bootstrapTable('updateRow', {index: index, row: item})
		
		$("#infolist_table").bootstrapTable("remove", {field: "id",values: [-1]}); 
    	itemChange();
    	
    	refreshTableRowSelect();
    }
};

function operateFormatter(value, row, index) {
    return [
    	'<a class="publish" href="javascript:void(0)" style="margin:0px 5px;" title="上架">',
        '<i class="fa fa-plus"></i>上架',
        '</a>'+
        '<a class="details" href="javascript:void(0)" style="margin:0px 5px;" title="详情">',
        '<i class="fa fa-exclamation-circle"></i>详情',
        '</a>'+
        '<a class="delete" href="javascript:void(0)" style="margin:0px 5px;" title="删除">',
        '<i class="fa fa-trash-o"></i>删除',
        '</a>'+
        '<a class="copy" href="javascript:void(0)" style="margin:0px 5px;" title="复制">',
        '<i class="fa fa-copy"></i>复制',
        '</a>'+
        '<a class="SerialPort" href="javascript:void(0)" style="margin:0px 5px;" title="串口显示">',
        '<i class="fa fa-magnet"></i>串口显示',
        '</a>'
    ].join('');
}

window.operateEvents = {
	'click .publish': function (e, value, row, index) {
		var infoid = row.infosn;
    	var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
    	
    	for(var i=0;i<infopubs.length;i++)
		{
			var infosn=infopubs[i].id;	
			var infoname=infopubs[i].Advname;
			var lifeAct=infopubs[i].lifeAct;
			var lifeDie=infopubs[i].lifeDie;
			var playTimelength=infopubs[i].playTimelength;
			var infopubid=infopubs[i].Pubidid;	
			if(infosn==infoid)
				{		        				
				var item={    
						infosn:infosn,
						infoname:infoname,
						lifeAct:lifeAct,
						lifeDie:lifeDie,
						timelenght:playTimelength
					};    				
				if(rowIndex==-1)
					{
					$('#infolist_table').bootstrapTable('append', item);
					}
				else {
					var params = {index:rowIndex, row:item};
		    		$('#infolist_table').bootstrapTable('insertRow', params);
				}
				
		    	itemlist = $("#infolist_table").bootstrapTable("getData");
		    	
		    	itemChange();    		    	    		    	    		    	
				break;
				}
		}
    	
    	refreshTableRowSelect();
    	window.event?window.event.cancelBubble=true:e.stopPropagation();
    },
    'click .details': function (e, value, row, index) {  
    	$("#modal_details").attr("data-type",row.infosn);
    	$("#modal_details").modal('show'); 
    	getitem(row.infosn);
    },
    'click .delete': function (e, value, row, index) {  
    	var infoid = row.infosn;
    	var infopubid = row.infopubid;
    	$.ajax({  
	        url:"/deleteinfobyid",          
	        type:"post",		        
	        data:{
	        	infosn:infoid,
	        	groupid:parseInt($("#grouplist").val()),
	        	infopubid:infopubid
				},  
	        dataType:"json", 
	        success:function(data)  
	        {       	        	
	        	if(data.result=='success')
				{
	        		$("#infoitem_table").bootstrapTable("remove", {field: "infosn",values: [parseInt(infoid)]});
				}
			else
				{alert(data.resultMessage);}
	        },  
	        error: function() {
	        	alert("ajax 函数  deleteinfobyid 错误");            
	          }  
	    });
    },
    'click .copy': function (e, value, row, index) {
    	var infoid = row.infosn;
    	$.ajax({  
	        url:"/CopyInfotodraft", 
	        data:{
	        	infosn:infoid,
	        	groupid:parseInt($("#grouplist").val())
				},  
	        type:"post",  
	        dataType:"json", 
	        success:function(data)  
	        {       	  
	        	if(data.result=="success")
	        		{
	        			alert("复制成功");
	        		}
	        	else
	        		{
	        			alert(data.resultMessage);
	        		}
	        },  
	        error: function() { 
	        	alert("ajax 函数  CopyInfo 错误");	            
	          }  
	    });
    },
    'click .SerialPort': function (e, value, row, index) { 
    	var infoid = row.infosn;
    	infoSendSerialData(infoid);
    }
};

//界面初始化
function initInfolist()
{
	$(".modal" ).draggable();
	
	$("#modal_addplaylist").on("shown.bs.modal",function(){		
		document.getElementById('creat_playlist_name').focus();
		});
	
	$('#btn_add_playlist').click(function(){	
		playlist_creat();		
	});
	
	$('#btn_delete_item').click(function(){
		
		var layerid = $("#modal_deleteitem").attr("data-layerid");
		var infoid=parseInt(layerid.substring(0,layerid.indexOf("offset")));
		var st=parseInt(layerid.substring(layerid.indexOf("offset") + 6));
		itemDeletebyst(infoid,st);
		
		$('#modal_deleteitem').modal('hide');
	});
	
	$('#btn_add_quantum').click(function(){
		var st = $('#playlist_StartTime').val();
		var et = $('#playlist_EndTime').val();
		if(st>et && et!='00:00')
			{alert('开始时间不能大于结束时间');return;}
				
		var optionCount = $('#creat_playlist_quantum option').length;
		var isJoin=false;
		if(optionCount>0)
			{		
			$("#creat_playlist_quantum option").each(function(){
				var lst = this.value.substring(0,this.value.indexOf("---"));
				var let = this.value.substring(this.value.indexOf("---") + 3);
				
				var lstval=parseInt(lst.substring(0,lst.indexOf(":")))*60 + parseInt(lst.substring(lst.indexOf(":") + 1));
				var letval=parseInt(let.substring(0,let.indexOf(":")))*60 + parseInt(let.substring(let.indexOf(":") + 1));
				if(letval==0){letval=24*60;}
				
				var stval=parseInt(st.substring(0,st.indexOf(":")))*60 + parseInt(st.substring(st.indexOf(":") + 1));
				var etval=parseInt(et.substring(0,et.indexOf(":")))*60 + parseInt(et.substring(et.indexOf(":") + 1));
				if(etval==0){etval=24*60;}
				
				if((stval>=lstval && stval<letval) || (etval>lstval && etval<letval) || (stval<=lstval && etval>=letval))
				{isJoin=true;return;}					
									
			  });					
			}
			 					
		if(isJoin)
			{alert('时间段不能和已存在的时间段存在交集');}
		else
			{			
			var oisJ = isJionCreattime(st,et,$("#creat_playlist_level").val());
			if(oisJ)
				{alert('时间段不能和已存在的时间段存在交集');}
			else
				{
				var txt = st+"---"+et;
				var option = "<option value='"+txt+"'>"+txt+"</option>";
				$('#creat_playlist_quantum').append(option);
				
				$('#modal_addquantum').modal('hide');
				}
			}					
	});	
	
	$('#btn_del_quantum').click(function(){
		
		var checkText=$("#creat_playlist_quantum").val();   //获取Select选择的Text
		$("#creat_playlist_quantum option[value='"+checkText+"']").remove(); 
	});
	
	$('#creat_playlist_type').change(function(){
		var playlist_type=parseInt($('#creat_playlist_type').val());
		switch(playlist_type)
		{
			case 2:{
				$('#creat_playlist_cycle').parents('.row').css('display','none');
			};break;
			
			case 1:{
				$('#creat_playlist_cycle').parents('.row').css('display','block');
			};break;
		}
	});	
	
	$('#creat_playlist_level').change(function(){
		var playlist_level = parseInt($('#creat_playlist_level').val());
		if(playlist_level==0)
			{
			$('#creat_playlist_type').parents('.row').css('display','none');
			$('#creat_playlist_quantum').parents('.row').css('display','none');			
			$('#creat_playlist_cycle').parents('.row').css('display','none');
			}
		else {			
			$('#creat_playlist_type').parents('.row').css('display','block');
			$('#creat_playlist_quantum').parents('.row').css('display','block');			
			$('#creat_playlist_cycle').parents('.row').css('display','block');
		}
	});
	
}

//获取分组信息
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
	        				if(selectGrpid==0 || selectGrpid==null)
        					{
	        				if(i==0)
							{
							$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
							getInfoList(grpid);
							}
	        				else
							{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
        					}
	        				else {
	        					if(selectGrpid == grpid)
								{
								$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
								getInfoList(grpid);
								}
		        				else
								{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
							}
	        			}
	        			
	        			if(grpsinfo.length>1)
	        			{
	        			$('#input_group_grouplist').css("display","inline");
	        			}
	        			else
	        			{$('#input_group_grouplist').css("display","none");}
	        			
	        		}
	        },  
	        error: function() { 
	        	alert("ajax 函数  getGroup 错误");	            
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
				getInfoList(grpid);
				}
				else
				{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}	
				}
				else {
					if(selectGrpid == grpid)
					{
					$('#grouplist').append("<option selected value='"+grpid+"'>"+grpname+"</option>");
					getInfoList(grpid);
					}
    				else
					{$('#grouplist').append("<option value='"+grpid+"'>"+grpname+"</option>");}
				}
			}
		}
}
//获取播放列表信息靠分组id
function getInfoList(groupid)
{
	sessionStorage.setItem('selectgrpid', groupid);
	$.ajax({  
        url:"/getInfoList",          
        type:"post", 
        data:{
        	groupid:groupid
			},  
        dataType:"json", 
        success:function(data)  
        {     
			$('#playlist').empty();
        	if(data!=null && data.length>0)    		
        		{        			
        			var fristid=0;
	        		for(var i=0;i<data.length;i++)
					{
	        			var id=data[i].id;
						var Playlistname=data[i].Playlistname;							
						var pubid=data[i].pubid;
												
						addinfolistnav(id,Playlistname,pubid);												
						
						if(i==0)
						{fristid=id;}						
					}
	        		
	        		sessionStorage.setItem('playlists', JSON.stringify(data));
	        		
	        		GetInfopubsbygrpid(groupid);
	        		
	        		playlistSelectChange(fristid);
        		}
        	else
        	{
        		$("#infolist_table").bootstrapTable('removeAll');
        		
        		sessionStorage.setItem('playlists', "[]");
        		
        		GetInfopubsbygrpid(groupid);
        	}
        },  
        error: function() {
        	alert("ajax 函数  getInfoList 错误");            
          }  
    });	
}
//获取分组下已发布广告集合
function GetInfopubsbygrpid(groupid)
{
	$.ajax({  
        url:"/getInfopubs",          
        type:"post", 
        async:false, 
        data:{
        	groupid:groupid
			},  
        dataType:"json", 
        success:function(data)  
        {       	        	
        	$('#infolist_draft').empty();			
			$('#badge_infolist').text(0);
			
        	if(data!=null && data.length>0)    		
        		{        
        			var ArrayTable = [];
        			
	        		for(var i=0;i<data.length;i++)
					{
	        			
	        			var infosn=data[i].id;	
	        			var infoname=data[i].Advname;
	        			var lifeAct=data[i].lifeAct;
	        			var lifeDie=data[i].lifeDie;
	        			var playTimelength=data[i].playTimelength;
	        			var infopubid=data[i].Pubidid;	
	        				    
	        			var item={
        					infosn:infosn,
        					infoname:infoname,
        					lifeAct:lifeAct,
        					lifeDie:lifeDie,
        					timelenght:playTimelength,
        					infopubid:infopubid
	        			};
	        			ArrayTable.push(item);	        			
					}
	        		
					$("#infoitem_table").bootstrapTable('load', ArrayTable);
	        		
	        		sessionStorage.setItem('infopubs', JSON.stringify(data));	        			        		
        		}
        },  
        error: function() {
        	alert("ajax 函数  getInfopubs 错误");            
          }  
    });
}
//判断新建广告添加时间段是否和已有广告有交集
function isJionCreattime(st,et,playlist_level)
{
	var isJoin=false;
	var stval=parseInt(st.substring(0,st.indexOf(":")))*60 + parseInt(st.substring(st.indexOf(":") + 1));
	var etval=parseInt(et.substring(0,et.indexOf(":")))*60 + parseInt(et.substring(et.indexOf(":") + 1));
	if(etval==0){etval=24*60;}
	
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var Playlistlevel=playlists[i].Playlistlevel;
			if(parseInt(playlist_level) == parseInt(Playlistlevel))
				{
				var Timequantum=playlists[i].Timequantum;					
				
				var jsontq = JSON.parse(Timequantum);			
				var timelist = jsontq.timelist
				if(timelist!=null && timelist.length>0)
				{
					for(var q=0;q<timelist.length;q++)
						{													
							var lstval=parseInt(timelist[q].lifeAct);
							var letval=parseInt(timelist[q].lifeDie);
							if(letval==0){letval=24*60;}
							
							if((stval>=lstval && stval<letval) || (etval>lstval && etval<letval) || (stval<=lstval && etval>=letval))
							{isJoin=true;break;}										
						}
				}
				}
		}
	}
	return isJoin;
}
//播放列表选择改变
function playlistSelectChange(playlistid)
{
	if(playlistid!=selectplaylistid)
	{
		$("#playlist option").removeAttr("selected","selected");
		$("#playlist option[value="+playlistid+"]").attr("selected","selected"); 		
		
		itemlist=[];
		
		var playlists= JSON.parse(sessionStorage.getItem('playlists'));
    	if(playlists!=null && playlists.length>0)//存在直接取数据    		
		{
    		for(var i=0;i<playlists.length;i++)
			{
    			var id=playlists[i].id;
    			if(id==playlistid)
    				{    				
					var Scheduletype=parseInt(playlists[i].Scheduletype);
					var Timequantum=playlists[i].Timequantum;
					var Programlist=playlists[i].Programlist;
										
					var jsontq = JSON.parse(Timequantum);					
					var timelist = jsontq.timelist
					
					if(Programlist=="")
					{itemlist=[];}
					else
					{itemlist=JSON.parse(Programlist);}	
					
					switch(Scheduletype)
					{
						case 2:{
							$("#pt_sort").css("display","inline");
							$("#pt_template").css("display","none");
							
							var ArrayTable = [];
							if(itemlist!=null && itemlist.length>0)
							{		
								ArrayTable=itemlist;
							}
							$("#infolist_table").bootstrapTable('load', ArrayTable);
						};break;
						case 1:{
							$("#pt_sort").css("display","none");
							$("#pt_template").css("display","inline");
							if(jsontq.listcycle!=null && jsontq.listcycle!="" && jsontq.listcycle!=0)
							{DrawBackground(jsontq.listcycle);}
							GetitemList('','');
						};break;
					}
					
					refreshTablebyDate(getNowFormatDate(),'');					
					
					break;
    				}
			}
		}
    	else//不存在数据库重新取
    		{alert("playlists=null");}
    	
    	selectplaylistid=playlistid;    	
	}
}

//刷新选中列表
function refreshTableRowSelect() {
	if(rowIndex != -1)
		{
		var trlist = $("#infolist_table tbody tr");
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

function refreshTablebyDate(dts,dte)
{
	var slt = $("#infolist_table").bootstrapTable("getData");    		    	
	if(slt.length>0)
		{    	
		if(dts==''){dts='1999-09-09';}
		if(dte==''){dte='2100-09-09';}
		for(var r=0;r<slt.length;r++)
			{
			var lifeAct = slt[r].lifeAct;
			var lifeDie = slt[r].lifeDie;
			if(lifeAct==''){lifeAct='1999-09-09';}
			if(lifeDie==''){lifeDie='2100-09-09';}
			var isjion = dtJion(dts,dte,lifeAct,lifeDie);//判断日期是否有交集
			if(isjion)
				{$("#infolist_table").bootstrapTable('showRow', {index:r});}
			else {
				$("#infolist_table").bootstrapTable('hideRow', {index:r});
			}
			}    			   		    		            
		}
	
	clearlayeritem();
	GetitemList(dts,dte);
}

//播放列表选择改变
function infoSelectChange(infoid)
{		
	if(selectinfoid != infoid)
		{
		selectinfoid=infoid;		
			
		var dts = '';
		var dte = '';
		
		var slt = $("#infoitem_table").bootstrapTable("getData");    		    	
		if(slt.length>0)
			{    				
			for(var r=0;r<slt.length;r++)
				{
				if(slt[r].infosn == infoid)
					{
					dts=slt[r].lifeAct;
					dte=slt[r].lifeDie;
					break;
					}				
				}    			   		    		            
			}	
	
		refreshTablebyDate(dts,dte);
		/*
		var slt = $("#infolist_table").bootstrapTable("getData");    		    	
    	if(slt.length>0)
    		{    	
    		if(dts==''){dts='1999-09-09';}
    		if(dte==''){dte='2100-09-09';}
    		for(var r=0;r<slt.length;r++)
    			{
    			var lifeAct = slt[r].lifeAct;
    			var lifeDie = slt[r].lifeDie;
    			if(lifeAct==''){lifeAct='1999-09-09';}
    			if(lifeDie==''){lifeDie='2100-09-09';}
    			var isjion = dtJion(dts,dte,lifeAct,lifeDie);//判断日期是否有交集
				if(isjion)
					{$("#infolist_table").bootstrapTable('showRow', {index:r});}
				else {
					$("#infolist_table").bootstrapTable('hideRow', {index:r});
				}
				}    			   		    		            
    		}
    		
		clearlayeritem();
		GetitemList(dts,dte);
    	*/
    	refreshTableRowSelect();    		
				        					        			
		if(itemlist!=null && itemlist.length>0)
		{
		for(var l=0;l<itemlist.length;l++)
			{
				var item = itemlist[l];
				
				var itemColor = "#eeeeeeaa";
				if(item.infoid==selectinfoid)
				{
					itemColor = "#b3d4fdaa";
				}
				
				var offsetlist = item.offsetlist;
				if(offsetlist!=null && offsetlist.length>0)
				{
					for(var j=0;j<offsetlist.length;j++)
						{
						var st = offsetlist[j];
						
	        			var layerid=item.infoid + "offset" + st;		        					        					        					        			
	        			$('#info_canvas').setLayerGroup(layerid, {
	        				  fillStyle: itemColor		        					        				  
	        				})
	        				.drawLayers();	        					        			
						}
				}		        						
			}
		}
	}
	else
	{			
	selectinfoid = 0;	
	
	refreshTablebyDate(getNowFormatDate(),'')	 
	}
}
//删除列表
function playlistDeletebyid(playlistid)
{
	$.ajax({  
        url:"/deleteplaylistbyid",          
        type:"post", 
        data:{
        	playlistid:playlistid
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null)    		
        		{
	        		if(data.result=='success')
					{
	        			/*
						if($("#div_"+playlistid).parent()[0].id=='playlist_draft')
        				{
        				var count_draft = parseInt($('#badge_draft').text()) - 1;
            			if(count_draft<0){count_draft=0;}
    					$('#badge_draft').text(count_draft);
    					}
        			else
        				{
        				var count_publish = parseInt($('#badge_publish').text()) - 1;
            			if(count_publish <0){count_publish =0;}
    					$('#badge_publish').text(count_publish );
    					}
						
						$("#div_"+playlistid).remove();	
						*/
	        			
	        			$("#playlist option[value="+playlistid+"]").remove(); 
	        			
						var playlists= JSON.parse(sessionStorage.getItem('playlists'));
												
						if(playlists!=null && playlists.length>0)//存在直接取数据    		
						{
				    		for(var i=0;i<playlists.length;i++)
							{
				    			var id=playlists[i].id;
				    			if(id==playlistid)
				    				{
				    				playlists.splice(i, 1);
				    				break;
				    				}
							}
						}
						
						sessionStorage.setItem('playlists', JSON.stringify(playlists));
						
						$('#info_canvas').removeLayers().drawLayers();
						itemlist=[];
					}
	        		else
					{alert(data.resultMessage);}
        		}
        },  
        error: function() {  
        	alert("ajax 函数  deleteplaylistbyid 错误");            
          }  
    });
}
//发布列表
function playlistPublishbyid(playlistid)
{
	if(itemlist == null || itemlist.length<=0)
		{
		alert("排期数据为空不能发布");
		return;
		}
	
	var mapData = getListData(playlistid);
	
	if(mapData.issave != 0)
	{ajaxSaveInfoList(playlistid,mapData.listname,mapData.listtype,mapData.strquantums,mapData.ScheduleType,itemlist,false,true);}	
}
//获取数据
function getListData(playlistid)
{
	var listname = "";
	var listtype = "";				
	var strquantums="";
	var ScheduleType=1;
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	var issave = 0;
	var pubid = "0";
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==selectplaylistid)
				{
				pubid=playlists[i].pubid;
				listname=playlists[i].Playlistname;
				ScheduleType=playlists[i].Scheduletype;
				listtype=playlists[i].Playlistlevel					
				strquantums=playlists[i].Timequantum
				if(playlists[i].isSave==null || playlists[i].isSave==0)
					{issave=0;}
				else
					{issave=1;}								
				break;
				}
		}
	}
	
	var mapData={
		listid:playlistid,
		listname:listname,
		listtype:listtype,
		strquantums:strquantums,
		ScheduleType:ScheduleType,		
		issave:issave,
		pubid:pubid
	};
	
	return mapData;
}
//广告详情
function playlistAttributebyid(playlistid)
{
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id = playlists[i].id;
			if(id == playlistid)
				{
				var Playlistname=playlists[i].Playlistname;							
				//var pubid=playlists[i].pubid;					
				var Playlistlevel=playlists[i].Playlistlevel;
				//var Grpid=playlists[i].Grpid;
				var Scheduletype=playlists[i].Scheduletype;
				var Timequantum=playlists[i].Timequantum;
				//var Programlist=playlists[i].Programlist;
				//var Delindex=playlists[i].Delindex;	
				
				$('#creat_playlist_name').val(Playlistname);
				$('#creat_playlist_type').val(Scheduletype);
				$('#creat_playlist_level').val(Playlistlevel);
				
				$('#playlist_StartTime').val('00:00');
				$('#playlist_EndTime').val('00:00');					
				
				$('#creat_playlist_quantum').empty();
				var jsontq = JSON.parse(Timequantum);
				$('#creat_playlist_cycle').val(jsontq.listcycle);
				
				var timelist = jsontq.timelist
				if(timelist!=null && timelist.length>0)
				{
					for(var q=0;q<timelist.length;q++)
						{							
							var st=(Array(2).join(0) + parseInt(timelist[q].lifeAct/60)).slice(-2)+":"+(Array(2).join(0) + timelist[q].lifeAct%60).slice(-2);
							var et=(Array(2).join(0) + parseInt(timelist[q].lifeDie/60)).slice(-2)+":"+(Array(2).join(0) + timelist[q].lifeDie%60).slice(-2);
							var option = "<option>"+st+"---"+et+"</option>";
							$('#creat_playlist_quantum').append(option);
						}
				}				
				break;
				}
		}
	}
	
	$("#modal_addplaylist").attr("data-type","update_playlist");
	$('#modal_addplaylist').modal('show');
}
//点击创建标示，打开模态窗口
function playlistCreate()
{
	$('#creat_playlist_name').val();
	$('#creat_playlist_type').val("2");
	$('#creat_playlist_level').val("1");
	$('#creat_playlist_quantum').empty();
	$('#creat_playlist_cycle').val(300);
	
	$("#modal_addplaylist").attr("data-type","create_playlist");
	$('#modal_addplaylist').modal('show');
}
//改变需要保存
function itemChange()
{
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==selectplaylistid)
				{
				playlists[i].isSave=1;				
				break;
				}
		}
	}
	sessionStorage.setItem('playlists', JSON.stringify(playlists));
}
//删除排期
function itemDeletebyst(infoid,st)
{	
	if(itemlist!=null && itemlist.length>0)
	{
		for(var l=0;l<itemlist.length;l++)
		{
			var item = itemlist[l];
						
			if(item.infoid==infoid)
			{
				var index = item.offsetlist.indexOf(st);
				if(index!=-1)
				{
					item.offsetlist.splice(index, 1);					
					if(item.offsetlist.length <= 0)
						{
						itemlist.splice(l, 1)
						}					
					itemChange();
				}				
				break;				
			}			
		}
	}
		
	// 删除所所有排期图层	
	$('#info_canvas').removeLayerGroup(infoid+"offset"+st).drawLayers();	
	
}
//创建播放列表
function playlist_creat()
{		
	var listname = $('#creat_playlist_name').val();
	var ScheduleType = $('#creat_playlist_type').val();
	var listlevel = $('#creat_playlist_level').val();
	var listcycle = $('#creat_playlist_cycle').val();
	
	var quantumcount = $('#creat_playlist_quantum option').length;
	var timelist=[];
	var quantums={};			
	
	if(listname==null || listname=='')
		{alert('列表名称不能为空');return;}
	
	if(listlevel==null || listlevel=='')
	{alert('列表类型不能为空');return;}
	
	if(listcycle==null || listcycle=='' || listcycle==0)
	{alert('列表周期不能为空');return;}
	
	if(listlevel==0)
	{
		ScheduleType=2;
		quantums.listcycle =300;
		var tms={};
		tms.lifeAct=0;
		tms.lifeDie=0;				
		timelist.push(tms);
		quantums.timelist = timelist;
	}
	else {
		if(quantumcount<=0)
		{alert('时间集合不能为空');return;}
		else
			{
			$("#creat_playlist_quantum option").each(function(){
				var lst = this.value.substring(0,this.value.indexOf("---"));
				var let = this.value.substring(this.value.indexOf("---") + 3);
				
				var lstval=parseInt(lst.substring(0,lst.indexOf(":")))*60 + parseInt(lst.substring(lst.indexOf(":") + 1));
				var letval=parseInt(let.substring(0,let.indexOf(":")))*60 + parseInt(let.substring(let.indexOf(":") + 1));
				
				var tms={};
				tms.lifeAct=lstval.toString();
				tms.lifeDie=letval.toString();				
				timelist.push(tms);
			  });
			}
		quantums.listcycle = listcycle;
		quantums.timelist = timelist;	
	}		
	
	var datatype = $("#modal_addplaylist").attr("data-type");
	if(datatype=="create_playlist")
	{
		ajaxCreateInfoList(listname,listlevel,quantums,ScheduleType);	
	}
	else
	{
		var playlists= JSON.parse(sessionStorage.getItem('playlists'));
		var issave=0;
		var pubid = "0";
		if(playlists!=null && playlists.length>0)//存在直接取数据    		
		{
			for(var i=0;i<playlists.length;i++)
			{
				var id=playlists[i].id;
				if(id==selectplaylistid)
					{					
					pubid=playlists[i].pubid;
					
					if(playlists[i].isSave==null || playlists[i].isSave==0)
						{issave=0;}
					else
						{issave=1;}	
					
					if(playlists[i].Playlistname!=listname)
					{issave=1;}
					
					if(playlists[i].Playlistlevel!=listlevel)
					{issave=3;}
					
					if(playlists[i].Timequantum!=JSON.stringify(quantums))
					{issave=3;}
					
					if(JSON.parse(playlists[i].Timequantum).listcycle!=quantums.listcycle)
					{issave=2;}
					
					break;
					}
			}
		}
		
		if(issave==0){$('#modal_addplaylist').modal('hide');return;}
		
		var isRefresh = false,isPublish = false;
		if(issave == 2)
			{isRefresh = true;isPublish = true;}
		if(issave==3)
		{isPublish = true;}
		if(pubid!=0 && pubid!="0")//已发布
			{
			if(isPublish)
				{
				ajaxSaveInfoList(selectplaylistid,listname,listlevel,JSON.stringify(quantums),ScheduleType,itemlist,isRefresh,isPublish);
				}
			else
				{
				//只修改了列表名称
				ajaxUpdatePlaylistName(selectplaylistid,listname);
				}
			}
		else
			{
			ajaxSaveInfoList(selectplaylistid,listname,listlevel,JSON.stringify(quantums),ScheduleType,itemlist,isRefresh,false);
			}	
	}
}
//添加列表按钮
function addinfolistnav(id,listname,pubid)
{
	$('#playlist').append("<option value='"+id+"'>"+listname+"</option>");
}
//ajax 创建列表
function ajaxCreateInfoList(listname,listtype,quantums,ScheduleType)
{
	$.ajax({  
	    url:"/CreatInfoList",          
	    type:"post",  
	    data:{
	    	listname:listname,
	    	groupid:parseInt($("#grouplist").val()),
	    	listtype:listtype,
	    	quantums:JSON.stringify(quantums),
	    	ScheduleType:ScheduleType,
	    	programlist:''
			},  
	    dataType:"json", 
	    success:function(data)  
	    {       	  
	    	if(data!=null)    		
	    		{
	    			if(data.result=='success')
	    				{
	    					itemlist=[];
	    					
	    					var infoListID=data.infoListID;
								
							addinfolistnav(infoListID,listname,0);
							
							var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							var jpl={};    							
							jpl.id=infoListID;
							
							jpl.Delindex=0;
							jpl.Grpid=infoListID;
							jpl.Playlistlevel=listtype;
							jpl.Scheduletype=ScheduleType;							
							jpl.Playlistname=listname;
							jpl.Programlist=JSON.stringify(itemlist);
							jpl.Timequantum=JSON.stringify(quantums);
							jpl.pubid="0";
							    							    							
							playlists.push(jpl);
							
							sessionStorage.setItem('playlists', JSON.stringify(playlists));
							
							playlistSelectChange(infoListID);
							
							$('#modal_addplaylist').modal('hide');
	    				}
	    			else
	    				{alert(data.resultMessage);}
	    		}
	    	else{alert("存储列表返回数据为null");}	        	   
	    },  
	    error: function() {  
	    	alert("ajax 函数  CreatInfoList 错误");	        
	      }  
	});
}
//ajax 保存列表
function ajaxSaveInfoList(playlistid,listname,listtype,strquantums,ScheduleType,itemlist,isRefresh,isPublish)
{
	$.ajax({  
        url:"/UpdateInfoList",          
        type:"post",  
        data:{
        	listid:playlistid,
        	listname:listname,
        	groupid:parseInt($("#grouplist").val()),
        	listtype:listtype,
        	quantums:strquantums,
        	ScheduleType:ScheduleType,
        	programlist:JSON.stringify(itemlist),
        	isPublish:isPublish
			},  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data!=null)    		
        		{
        			if(data.result=='success')
        				{        		
	        				var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							if(playlists!=null && playlists.length>0)//存在直接取数据    		
							{
					    		for(var i=0;i<playlists.length;i++)
								{
					    			var id = playlists[i].id;
					    			if(id == playlistid)
					    				{
					    				if(data.returnid != null)//生成新列表
					    					{
					    						addinfolistnav(data.returnid,listname,0);
					    						playlists.splice(i, 1);					    						
					    						
					    						var jpl={
					    								id:data.returnid,
					    								Playlistname:listname,
					    								pubid:"0",
					    								Playlistlifeact:"",
					    								Playlistlifedie:"",
					    								Playlistlevel:listtype,
					    								Grpid:parseInt($("#grouplist").val()),
					    								Scheduletype:ScheduleType,
					    								Timequantum:strquantums,
					    								Programlist:JSON.stringify(itemlist),
					    								Delindex:0
					    						};
					    	        			
					    						playlists.push(jpl);
												
												sessionStorage.setItem('playlists', JSON.stringify(playlists));
												
					    						playlistSelectChange(data.returnid);
					    						
					    						$("#playlist option[value="+playlistid+"]").remove(); 
					    						
					    						/*
					    						$("#div_"+playlistid).remove();
					    						
												var count = parseInt($('#badge_publish').text()) - 1;
												$('#badge_publish').text(count);
					        					
												$('#modal_save').modal('hide');
												*/
												$('#modal_addplaylist').modal('hide');					    						
					    					}
					    				else
					    					{
						    				playlists[i].Playlistlevel=listtype;
						    				playlists[i].Scheduletype=ScheduleType;					    				
						    				if(playlists[i].Playlistname!=listname)
						    					{
						    					//$("#pl_"+playlistid).text(listname);
						    					$("#playlist option[value="+playlistid+"]").text(listname);
						    					playlists[i].Playlistname=listname;
						    					}
						    				
						    				playlists[i].Programlist=JSON.stringify(itemlist);
						    				playlists[i].Timequantum=strquantums;
						    				/*
						    				if(playlists[i].pubid!==0 && playlists[i].pubid!=="0")
					    					{
						    					var count = parseInt($('#badge_draft').text()) + 1;
												if(count <= 0){count = 0;}
												$('#badge_draft').text(count);
												
												$('#playlist_draft').append($("#div_" + playlistid));
												var count = parseInt($('#badge_publish').text()) - 1;
												$('#badge_publish').text(count);
					        					
												$('#modal_save').modal('hide');
												$('#modal_addplaylist').modal('hide');
												
												playlists[i].pubid = "0";
					    					}
						    				*/
						    				
			    							if(data.pubid!=null)
						    				{
			    								//保存后发布了
			    								playlists[i].pubid=data.pubid;			  										
						    				}
			    							else
		    								{playlists[i].pubid = "0";}
			    							
			    							
			    							playlists[i].isSave=0;
			    										
			    							if(isRefresh)
		    								{
				    							DrawBackground(JSON.parse(strquantums).listcycle);	
				    							clearlayeritem();
				    							if(selectinfoid==0)
				    							{GetitemList('','');}
				    							else
			    								{
			    									var infopubs = JSON.parse(sessionStorage.getItem('infopubs'));
			    									
			    									for(var i=0;i<infopubs.length;i++)
			    									{
			    										var infosn=infopubs[i].id;	
			    										var infoname=infopubs[i].Advname;
			    										var lifeAct=infopubs[i].lifeAct;
			    										var lifeDie=infopubs[i].lifeDie;
			    										var playTimelength=infopubs[i].playTimelength;
			    										var infopubid=infopubs[i].Pubidid;	
			    										if(infosn==selectinfoid)
			    											{						
			    						        			GetitemList(lifeAct,lifeDie);
			    						        			break;
			    											}
			    									}
			    								}
		    								}
					    					}
		    							alert('修改成功');
					    				break;
					    				}
								}
							}
						
							sessionStorage.setItem('playlists', JSON.stringify(playlists));
							$('#modal_addplaylist').modal('hide');
        				}
        			else
        				{alert(data.resultMessage);}
        		}
        	else{alert("存储列表返回数据为null");}	        	   
        },  
        error: function() { 
        	alert("ajax 函数  UpdateInfoList 错误");	            
          }  
    });
}
//ajax 修改列表名称
function ajaxUpdatePlaylistName(playlistid,listname)
{
	$.ajax({  
	    url:"/UpdatePlaylistName",          
	    type:"post",  
	    data:{
	    	playlistid:playlistid,
	    	listname:listname
			},  
	    dataType:"json", 
	    success:function(data)  
	    {       	  
	    	if(data!=null)    		
	    		{
	    			if(data.result=='success')
	    				{	    						    						    												
							var playlists= JSON.parse(sessionStorage.getItem('playlists'));
							
							if(playlists!=null && playlists.length>0)//存在直接取数据    		
							{
					    		for(var i=0;i<playlists.length;i++)
								{
					    			var id = playlists[i].id;
					    			if(id == playlistid)
					    				{
					    				$("#playlist option[value="+playlistid+"]").text(listname);
					    				playlists[i].Playlistname=listname;
					    				}
								}
							}
							sessionStorage.setItem('playlists', JSON.stringify(playlists));													
							$('#modal_addplaylist').modal('hide');
	    				}
	    			else
	    				{alert(data.resultMessage);}
	    		}
	    	else{alert("存储列表返回数据为null");}	        	   
	    },  
	    error: function() {  
	    	alert("ajax 函数  UpdatePlaylistName 错误");	        
	      }  
	});
}
//串口回传
function SendCallback(SN,infocodelist,i)
{
	var timesRun = 0,reSend=0;
	var interval = setInterval(function(){
		if(infocodelist.length > i)
		{
		    timesRun += 1;  
		    if(timesRun >= 10){ 
		    	sendinfoDataCallback(newSN,infocodelist,i,SendCallback(newSN,infocodelist,i));
		    	reSend += 1;
		    }
		    if(reSend >= 3)
		    	{
		    	$('#progress').css('height','0px');
		    	var closeJsonObj={
		    			command:"closeSerialPort",
						commandSN:getSN()
		    		};	        
		        wssend(JSON.stringify(closeJsonObj));
		    	alert("通讯不畅");
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
//串口发布广告通过id
function playlistSerialPublishbyid(playlistid)
{				
	$('#progress').css('height','20px');
					
	if(itemlist == null || itemlist.length<=0)
	{
	alert("排期数据为空不能发布");
	return;
	}
		
	var listtype = "";				
	var strquantums="";
	var ScheduleType=1;
	var playlists= JSON.parse(sessionStorage.getItem('playlists'));
	
	if(playlists!=null && playlists.length>0)//存在直接取数据    		
	{
		for(var i=0;i<playlists.length;i++)
		{
			var id=playlists[i].id;
			if(id==playlistid)
				{								
				listtype=playlists[i].Playlistlevel	
				ScheduleType=playlists[i].Scheduletype					
				strquantums=playlists[i].Timequantum				
				}
		}
	}
	
	$.ajax({  
        url:"/getPublishListbyTemp", 
        data:{
        	listtype:listtype,
        	quantums:strquantums,
        	ScheduleType:ScheduleType,
        	programlist:JSON.stringify(itemlist)
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{        
        		var infocodelist = data.infocodelist;
        		var plcodelist = data.plcodelist;
        		var sendArray=[];
        		sendArray.push("7E 46 47 4A 00 36 01 01 00 01 00 00 41 61 00 00 00 00 00 00 00 00 00 00 64 65 6C 65 00 00 00 00 E8 5E 13 03 0C 0F 30 01 02 B4 00 00 00 00 00 00 00 00 00 00 00 00 45 4E 7E");
        		sendArray = sendArray.concat(infocodelist);
        		sendArray = sendArray.concat(plcodelist);
        		
        		var length=sendArray.length;
        		var SN = getSN();
        		sendinfoDataCallback(SN,sendArray,0,SendCallback(SN,sendArray,0));        		
        		}
        	else
        		{
	        		var SN = getSN();
			        var closeJsonObj={
			    			command:"closeSerialPort",
							commandSN:SN
			    		};	        
			        wssend(JSON.stringify(closeJsonObj));
			        $('#progress').css('height','0px');
	    			alert(data.resultMessage);        			
        		}        	
        },  
        error: function() {  
        	var SN = getSN();
	        var closeJsonObj={
	    			command:"closeSerialPort",
					commandSN:SN
	    		};	        
	        wssend(JSON.stringify(closeJsonObj));
	        $('#progress').css('height','0px');
        	alert("ajax 函数  getPublishInfobyid 错误");            
          }  
    });    	 
}
//发送串口数据
function SendSerialData(playlistid)
{
	var SerialPort = localStorage.getItem("SerialPort");
	if(SerialPort!=null && SerialPort!="")
		{
		intiWebSocket();
		        		        		
		ws.addEventListener('open', function () { 
		
			var jsonSerialPort;
			
			try
			{
				jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));
			}
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
	    		        alert("通讯不畅");
	    		        clearInterval(interval);    		        
	    		    }
	    		    
	    		    if(receiveMap[JsonObj.commandSN]!=null)
	        		{
	        		receiveData=receiveMap[JsonObj.commandSN]; 
	        		if(receiveData.SerialPort)
	        			{
	        			//串口打开发广告
	        			playlistSerialPublishbyid(playlistid);
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
//串口发布广告通过id
function infoSerialPublishbyid(infoid)
{				
	$('#progress').css('height','20px');
						
	$.ajax({  
        url:"/getinfoListbyinfoid", 
        data:{
        	infosn:infoid
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{        
        		var infocodelist = data.infocodelist;
        		var plcodelist = data.plcodelist;
        		var sendArray=[];
        		sendArray.push("7E 46 47 4A 00 36 01 01 00 01 00 00 41 61 00 00 00 00 00 00 00 00 00 00 64 65 6C 65 00 00 00 00 E8 5E 13 03 0C 0F 30 01 02 B4 00 00 00 00 00 00 00 00 00 00 00 00 45 4E 7E");
        		sendArray = sendArray.concat(infocodelist);
        		sendArray = sendArray.concat(plcodelist);
        		
        		var length=sendArray.length;
        		var SN = getSN();
        		sendinfoDataCallback(SN,sendArray,0,SendCallback(SN,sendArray,0));        		
        		}
        	else
        		{
	        		var SN = getSN();
			        var closeJsonObj={
			    			command:"closeSerialPort",
							commandSN:SN
			    		};	        
			        wssend(JSON.stringify(closeJsonObj));
			        $('#progress').css('height','0px');
	    			alert(data.resultMessage);        			
        		}        	
        },  
        error: function() {  
        	var SN = getSN();
	        var closeJsonObj={
	    			command:"closeSerialPort",
					commandSN:SN
	    		};	        
	        wssend(JSON.stringify(closeJsonObj));
	        $('#progress').css('height','0px');
        	alert("ajax 函数  getPublishInfobyid 错误");            
          }  
    });    	 
}

function infoSendSerialData(infoid)
{
	var SerialPort = localStorage.getItem("SerialPort");
	if(SerialPort!=null && SerialPort!="")
		{
		intiWebSocket();
		        		        		
		ws.addEventListener('open', function () { 
		
			var jsonSerialPort;
			
			try
			{
				jsonSerialPort = JSON.parse(localStorage.getItem("SerialPort"));
			}
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
	    		        alert("通讯不畅");
	    		        clearInterval(interval);    		        
	    		    }
	    		    
	    		    if(receiveMap[JsonObj.commandSN]!=null)
	        		{
	        		receiveData=receiveMap[JsonObj.commandSN]; 
	        		if(receiveData.SerialPort)
	        			{
	        			//串口打开发广告
	        			infoSerialPublishbyid(infoid);
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

//获取显示项
function getitem(infoid)
{	
	$.ajax({  
        url:"/GetItem",         
        data:{
        	infoid:infoid     	
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       	  
        	if(data.result=="success")
        		{         
        		var ArrayTable=[];
    			if(data.itemlist!=null && data.itemlist.length>0)
    				{       
    					for(var i=0;i<data.itemlist.length;i++)
    						{
    							var jitem=data.itemlist[i];        							     							
    							var delindex =jitem.delindex;        							
    							if(delindex==0)
    								{    
    								var itemstyle="";
    							    if(jitem.itemstyle!=null && jitem.itemstyle!="")
    						    	{    		
    							    	var arrayPlaytype=['静止','左滚','上滚','左拉帘','右拉帘','左右拉帘','上拉帘','下拉帘','上下拉帘','渐亮','渐灭','左移进','右移进','左右移进','隔行左右对进','上移进','下移进','上下隔行移进','闪烁','百叶','放大 左上角->全屏','放大 中间->全屏'];
    							    					          								          								      
    							    	var itemstyleJson = JSON.parse(jitem.itemstyle);
    							    	var linkmove = "联动:否";
    							    	if(itemstyleJson.linkmove==1)
    							    		{linkmove = "联动:是"}    							    	
    							    	var playtype = "播放方式:" + arrayPlaytype[parseInt(itemstyleJson.playtype)];
    							    	var playspeed = "速度:" + itemstyleJson.playspeed;
    							    	var rollstop = "停留方式:最终";
    							    	if(itemstyleJson.rollstop==1)
    							    		{rollstop = "停留方式:每屏";}
    							    	var stoptime = "停留时间:" + itemstyleJson.stoptime;
    							    	var looptime = "次数:" + itemstyleJson.looptime;
    							    	var gamma = "对比度:" + itemstyleJson.gamma;
    							    	var playtime = "时长:" + itemstyleJson.playtime;
    						    	}    						    
    								
    							    itemstyle=playtype+";"+playspeed+";"+looptime+";"+stoptime+";"+rollstop+";"+gamma+";"+linkmove+";"+playtime;
    							    
    								item={
										itemContext:jitem.itemcontext,
        								itemLocationSize:jitem.itemleft+","+jitem.itemtop+","+jitem.itemwidth+","+jitem.itemheight,
        								itemStyle:itemstyle		
    								};        								
    								}
    						}
    				}
    			ArrayTable.push(item);				
				$("#info_details_table").bootstrapTable('load', ArrayTable);
        		}
        	else
        		{
        			alert(data.resultMessage);
        		}
        },  
        error: function() { 
        	alert("ajax 函数  GetItem 错误");            
          }  
    });
}