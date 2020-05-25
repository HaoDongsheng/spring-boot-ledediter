$(function(){
	//var codekey = getCodeKey("AA");
	
	var opts = {            
        lines: 13, // 花瓣数目
        length: 20, // 花瓣长度
        width: 10, // 花瓣宽度
        radius: 30, // 花瓣距中心半径
        corners: 1, // 花瓣圆滑度 (0-1)
        rotate: 0, // 花瓣旋转角度
        direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
        color: '#000', // 花瓣颜色
        speed: 1, // 花瓣旋转速度
        trail: 60, // 花瓣旋转时的拖影(百分比)
        shadow: false, // 花瓣是否显示阴影
        hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
        className: 'spinner', // spinner css 样式名称
        zIndex: 2e9, // spinner的z轴 (默认是2000000000)
        top: '25%', // spinner 相对父容器Top定位 单位 px
        left: '50%'// spinner 相对父容器Left定位 单位 px
    };

    spinner = new Spinner(opts);
	    
	initBTabel();
	
	getProjectlist();
});

var spinner;
var projectList=[];

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
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
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
            title: '项目id',
            sortable:true            
        }, {
            field: 'projectName',
            title: '项目名称',
            sortable:true
            
        },{
            field: 'projectPwd',
            title: '校验码',
            visible: false  
            
        },{
            field: 'autoGrp',
            title: '默认分组',
            sortable:true,
            formatter: function (value, row, index) {            	
            	var groupid = value;
            	var dgrpname="";         	
            	   
            	var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
            	           	               	
            	if(groupid!=null)
        		{	            	
            		for(var i=0;i<grpsinfo.length;i++)
        			{
        				var grpid=grpsinfo[i].grpid;
        				var grpname = grpsinfo[i].grpname;	
        				
        				if(groupid==grpid)
        				{	        					
        					dgrpname = grpname;break;
        				}					    			
        			}	                   				    			
        		}            	
            	            	
                return dgrpname;
            }
            
        },{
            field: 'startLevelControl',
            title: '可控星级',
            sortable:true,
            formatter: function (value, row, index) {            	
            	if(value==0)
            	{return "否";}
            	else {
            		return "是";	
				}
                
            }
            
        },{
            field: 'defaultStartLevel',
            title: '默认星级',
            sortable:true,
            formatter: function (value, row, index) {
            	var startlevel="☆☆☆☆☆";
            	switch (value) {
				case 1:
					startlevel="★☆☆☆☆";
					break;
				case 2:
					startlevel="★★☆☆☆";
					break;
				case 3:
					startlevel="★★★☆☆";
					break;
				case 4:
					startlevel="★★★★☆";
					break;
				case 5:
					startlevel="★★★★★";
					break;

				default:
					startlevel="☆☆☆☆☆";
					break;
				}
            	return startlevel;            	                
            }
            
        }, {
            field: 'ConnectParameters',
            title: '连接参数',
            sortable:true,
            visible: false         
        },{
            field: 'grouplist',
            title: '包含分组',
            sortable:true,
            formatter: function (value, row, index) {            	
            	var groups = value.toString();
            	
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
            field: 'ourmodule',
            sortable:true,
            title: '我司模块'
         
        }, {
            field: 'userlist',
            title: '包含用户',
            sortable:true,
            formatter: function (value, row, index) {            	
            	var names = value;
            	
            	var option;            	
            	               	            	
            	var nameArr=null;
            	if(names != null && names != "")
            		{
            		nameArr = names.split(",");
            		}
            	var headOption = "";            	    
            	
            	if(nameArr!=null)
        		{
	            	for(var j=0;j<nameArr.length;j++)
	    			{	            			        				
	            		headOption = headOption + "<option value='"+nameArr[j]+"'>"+nameArr[j]+"</option>";	        				                   			
	    			}
        		}            	
            	
            	option = '<select class="form-control">'+ headOption + '</select>';
            	            	
                return option;
            }
        }, {
        	field: 'operate',
        	title: '操作',
        	align: 'center',
        	events: operateEvents,
        	formatter: operateFormatter
        }
        ]
    });
    
    $("#group_packLength").change(function(){ 
    	var group_packLength = parseInt($("#group_packLength").val());
    	$("#group_batchcount").empty();
    	switch (group_packLength) {
		case 768:			
			for(var i=0;i<5;i++)
				{
				$("#group_batchcount").append("<option value='"+ (i+1)*8 +"'>"+ (i+1)*8 +"</option>")
				}
			break;
		case 1024:			
			for(var i=0;i<5;i++)
				{
				$("#group_batchcount").append("<option value='"+ (i+1)*6 +"'>"+ (i+1)*6 +"</option>")
				}
			break;
		}    							
    });
    
    $("#select_start").change(function(){ 
    	var select_start = parseInt($("#select_start").val());
    	if(select_start==0)
    		{
    		$('#project_startLevel').attr("disabled", "disabled");
    		}
    	else {
    		$('#project_startLevel').removeAttr("disabled");
		}						
    }); 
    
    $("#select_project").change(function(){ 
    	getGroupbyProjectid($("#select_project").val(),'select_group');						
    }); 
    
    $("#ore_project").change(function(){ 
    	getGroupbyProjectid($("#ore_project").val(),'ore_group');						
    }); 
    
    
    $("#btn_table_Move").click(function(){
    	$('#select_project').empty();
    	$('#ore_project').empty();
    	if(projectList!=null && projectList.length>0)
    		{
    		for(var i=0;i<projectList.length;i++)
    			{
    			var sel="";
    			if(i==0)
    				{
    				sel="selected";
    				getGroupbyProjectid(projectList[i].projectid,'select_group');
    				getGroupbyProjectid(projectList[i].projectid,'ore_group');
    				}
    			var item="<option "+sel+" value='"+projectList[i].projectid+"'>"+projectList[i].projectname+"</option>";
    			$('#select_project').append(item);
    			$('#ore_project').append(item);
    			}
    		}		
    	
    	$("#modal_moveGroup").modal('show');
    });
    
    $("#btn_table_decode").click(function(){ 
    	$('#decode_result').empty();
    	$("#modal_decode").modal('show');
    });
    
    $("#btn_project_remove").click(function(){   
    	//请求时spinner出现
    	var target = $("#modal_project_remove .modal-body").get(0);
        spinner.spin(target);
        
    	var projectId = $('#modal_project_remove').attr("data-projectid");		
    	
    	$.ajax({  
            url:"/removeProject", 
            data:{
            	projectid:projectId,
            	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
    			},  
            type:"post",  
            dataType:"json", 
            success:function(data)  
            {       	  
            	if(data.result=="success")
            		{  
            		for(var i=0;i<projectList.length;i++)
					{
		    			var id=projectList[i].projectid;
		    			if(id==projectId)
		    				{
		    				projectList.splice(i, 1);
		    				break;
		    				}
					}
            		
            		$("#projectinfo_table").bootstrapTable("remove", {field: "projectId",values: [projectId]});
            		alertMessage(0, "成功", "删除项目成功!");
            		}
            	else
            		{
            			alertMessage(1, "警告", data.resultMessage);        			
            		}   
            	$("#modal_project_remove").modal('hide');
            	//关闭spinner  
                spinner.spin();
            },  
            error: function() {  
            	alertMessage(2, "异常", "ajax 函数  removeProject 错误");    
            	$("#modal_project_remove").modal('hide');
            	//关闭spinner  
                spinner.spin();
              }  
        });
    });
    
    $("#btn_table_change").click(function(){ 
//    	$.ajax({  
//            url:"/programlistChange",           
//            type:"post",  
//            dataType:"json", 
//            success:function(data)  
//            {       	  
//            	if(data.result=="success")
//            		{  
//            		alertMessage(0, "提示", "转换成功");
//            		}
//            	else
//            		{
//            			alertMessage(1, "警告", data.resultMessage);              			
//            		}        	            	
//            },  
//            error: function() {  
//            	alertMessage(2, "异常", "ajax 函数  programlistChange 错误");             	
//              }  
//        });
    	
//    	$.ajax({  
//            url:"/passwordEnCode",           
//            type:"post",  
//            dataType:"json", 
//            success:function(data)  
//            {       	  
//            	if(data.result=="success")
//            		{  
//            		alertMessage(0, "提示", "转换成功");
//            		}
//            	else
//            		{
//            			alertMessage(1, "警告", data.resultMessage);              			
//            		}        	            	
//            },  
//            error: function() {  
//            	alertMessage(2, "异常", "ajax 函数  passwordEnCode 错误");             	
//              }  
//        });
    	
//    	$.ajax({  
//            url:"/txt2db",           
//            type:"post",  
//            dataType:"json", 
//            success:function(data)  
//            {       	  
//            	if(data.result=="success")
//            		{  
//            		alertMessage(0, "提示", "转换成功");
//            		}
//            	else
//            		{
//            			alertMessage(1, "警告", data.resultMessage);              			
//            		}        	            	
//            },  
//            error: function() {  
//            	alertMessage(2, "异常", "ajax 函数  passwordEnCode 错误");             	
//              }  
//        });
    	var target = $("#projectinfo_table").get(0);
        spinner.spin(target);
        
    	$.ajax({  
            url:"/codeChangeSingle",           
            type:"post",  
            dataType:"json", 
            success:function(data)  
            {       	  
            	if(data.result=="success")
            		{  
            		
            		if(data.delinfosns=="" && data.delplaylistsns=="")
            			{
            			alertMessage(0, "提示", "转换成功");
            			}
            		else {
						alert("广告编码转换失败:</br>"+ data.delinfosns+"</br>列表转换失败:</br>"+data.delplaylistsns);
					}
            		}
            	else
            		{
            			alertMessage(1, "警告", data.resultMessage);              			
            		}
            	//关闭spinner  
                spinner.spin();
            },  
            error: function() {  
            	alertMessage(2, "异常", "ajax 函数  codeChangeSingle 错误");   
            	//关闭spinner  
                spinner.spin();
              }  
        });
    });
    
    //解码
    $("#btn_decode").click(function(){  
    	var Content = tinymce.activeEditor.getContent({ 'format' : 'text' });
    	//alert(Content);
    	var data="";
    	var codeJson= JSON.parse(Content);
    	if(codeJson!=null && codeJson.length>0)
    		{
    		for(var i=0;i<codeJson.length;i++)
    			{
    			var codeitem = codeJson[i].replace(/\s*/g,"");
    			var newitem = GJ_FanZhuanYi(codeitem);
    			var isJM = parseInt(newitem.substring(14*2,14*2+2),16);
    			var dataitem="";
    			if(isJM==1)//解密
    				{
					var JMkey = newitem.substring(15*2,15*2+2);    			
					var codekey = getCodeKey("AA");
					var code=0;
					if(codekey[7]^codekey[6] == 0)
						{
						code = (codekey[5]*4 + codekey[4]*2 + codekey[3])*10 + (codekey[2]*4 + codekey[1]*2 + codekey[0]);
						}
					else {
						code = (codekey[5]*2 + codekey[4])*10 + (codekey[3]*4 + codekey[2]*4 + codekey[1]*2 + codekey[0]);
					}
					dataitem = newitem.substring(32 * 2,newitem.length - 23*2);
					var newDate="";
					for(var j=0;j<dataitem.length;j+=2)
						{
						var valitem = parseInt(dataitem.substring(j,2),16);
						newDate += (valitem^code).toString(16);
						}
					dataitem = newDate;
    				}
    			else {
    				dataitem = newitem.substring(32 * 2,newitem.length - 23*2);	
				}
    			
    			data += dataitem;
    			}
    		var decodeType = parseInt($('#select_decodeType').val());
    		if(decodeType==0)//广告解码
    			{
    			var n=0;
    			var pid=parseInt(data.substring(n,n+=4),16);
    			var tlength=parseInt(data.substring(n,n+=8),16);
    			var type=parseInt(data.substring(n,n+=2),16);
    			var dtsize=data.substring(n+=20,n+=8);
    			var bordertype=parseInt(data.substring(n,n+=2),16);
    			var borderspeed=parseInt(data.substring(n,n+=2),16);
    			var op=parseInt(data.substring(n,n+=2),16);
    			var dtbytesize=parseInt(data.substring(n+=20,n+=8),16);
    			var dtcount=parseInt(data.substring(n,n+=2),16);
    			var pm=parseInt(data.substring(n,n+=2),16);
    			var pl=parseInt(data.substring(n,n+=4),16);
    			var itemcount=parseInt(data.substring(n,n+=4),16);
    			
    			var itemlist=data.substring(n,data.length - 4);
    			var m=0;
    			for(var j=0;j<itemcount;j++)
    				{
    				var itembytelemgth=parseInt(itemlist.substring(m,m+=8),16);
    				var itemtype=parseInt(itemlist.substring(m,m+=2),16);
    				var itemcolor=itemlist.substring(m,m+=16);
    				var itemfomtmo=parseInt(itemlist.substring(m,m+=2),16);
    				var itemls=itemlist.substring(m,m+=16);
    				var itemlink=parseInt(itemlist.substring(m,m+=2),16);
    				var itemdisplaymode=parseInt(itemlist.substring(m,m+=2),16);
    				var itemspeed=parseInt(itemlist.substring(m,m+=2),16);
    				var itemstopmode=parseInt(itemlist.substring(m,m+=2),16);
    				var itemstoptime=parseInt(itemlist.substring(m,m+=8),16);
    				var itemloop=parseInt(itemlist.substring(m,m+=2),16);
    				var itemdb=parseInt(itemlist.substring(m,m+=2),16);
    				var ptcoumt=parseInt(itemlist.substring(m+=18,m+=4),16);
    				for(var z=0;z<ptcoumt;z++)
    					{
    					var pttype=parseInt(itemlist.substring(m,m+=2),16);
    					var ptlength=parseInt(itemlist.substring(m,m+=4),16);
    					var ptw = parseInt(itemlist.substring(m,m+=4),16);
    					var pth = parseInt(itemlist.substring(m,m+=4),16);
    					
    					switch(pttype)
    					{
    					case 0:/*图文*/{};break;
    					case 1:/*时间*/{};break;
    					case 2:/*日期*/{};break;
    					case 3:/*全彩图*/{};break;
    					case 4:/*gif*/{};break;
    					case 5:/*2位图*/{
    						var colorbyteTableLength = 2*3*2;
    						var colorbyteTable = itemlist.substring(m,m+=colorbyteTableLength);//颜色索引表
    						
    						var Index_compress_length= ptw * pth / 8 * 2;    						
    						var Index_compress = itemlist.substring(m,m+=Index_compress_length);//图片索引表
    					};break;
    					case 6:/*4位图*/{
    						var colorbyteTableLength = 4*3*2;
    						var colorbyteTable = itemlist.substring(m,m+=colorbyteTableLength);
    						
    						var Index_compress_length= ptw * pth / 4 * 2;    						
    						var Index_compress = itemlist.substring(m,m+=Index_compress_length);
    					};break;
    					case 7:/*16位图*/{
    						var colorbyteTableLength = 16*3*2;
    						var colorbyteTable = itemlist.substring(m,m+=colorbyteTableLength);
    						
    						var Index_compress_length= ptw * pth / 2 * 2;    						
    						var Index_compress = itemlist.substring(m,m+=Index_compress_length);
    					};break;
    					case 8:/*256位图*/{
    						var colorbyteTableLength = 256*3*2;
    						var colorbyteTable = itemlist.substring(m,m+=colorbyteTableLength);
    						
    						var Index_compress_length= ptw * pth * 2;    						
    						var Index_compress = itemlist.substring(m,m+=Index_compress_length);
    					};break;
    					}    					    					
    					}    				
    				}
    			}
    		else {//列表解码
    			var message="";
				var pid=parseInt(data.substring(0,4),16);
				message+="列表id:"+pid+"</br>";
				var tlength=parseInt(data.substring(4,8),16);				
				var yxj=parseInt(data.substring(8,10),16);
				message+="优先级:"+yxj+"</br>";
				var life=parseInt(data.substring(30,42),16);				
				message+="生命周期:"+hexlife2String(life)+"</br>";
				var tcount=parseInt(data.substring(42,44),16);
				message+="播放时段:【"+tcount+"】";
				var tlist = data.substring(44,44 + 8*tcount);
				message+= tlist+"</br>";
				var pmode = data.substring(44 + 8*tcount,44 + 8*tcount+2);
				message+="播放模式:"+life+"</br>";
				var dlist = data.substring(44 + 8*tcount+2,data.length -4);
				var crc = data.substring(data.length -4);
				message+="crc:"+crc+"</br>";
				
				var cycle = dlist.substring(20,28);
				message+="周期:"+life+"</br>";
				var infoCount = parseInt(dlist.substring(28,32),16);
				message+="广告数量:"+infoCount+"</br>";
				var n=32;
				for(var j=0;j<infoCount;j++)
					{
					var infoid = parseInt(dlist.substring(n,n+4),16);
					message+="广告id:"+infoid;
					var infolife = dlist.substring(n+12,n+24);
					message+="生命周期:"+hexlife2String(infolife);
					var plength = parseInt(dlist.substring(n+24,n+32),16);
					message+="时长:"+plength;
					var valCount = parseInt(dlist.substring(n+32,n+36),16);
					message+="偏移量数量:【"+valCount+"】";
					var plist =dlist.substring(n+36,n+36+8*valCount);
					for(var z=0;z<valCount;z++)
						{
						var pval=parseInt(plist.substring(z*8,z*8 +8),16)
						message+= pval+",";
						}
					message+="</br>";
					n=n+36+8*valCount;
					}
				$('#decode_result').empty();
				$('#decode_result').append(message);				
			}
    		}
    	//$("#modal_decode").modal('hide');
    });
    
    
    $("#btn_moveGroup").click(function(){ 
    	var sp = $('#select_project').val();
    	var op = $('#ore_project').val();
    	var sg = $('#select_group').val();
    	var og = $('#ore_group').val();
    	
    	$.ajax({  
            url:"/moveGroup", 
            data:{
            	sprojectid:sp,
            	oprojectid:op,
            	sgroupid:sg,
            	ogroupid:og,
            	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
    			},  
            type:"post",  
            dataType:"json", 
            success:function(data)  
            {       	  
            	if(data.result=="success")
            		{  
            		}
            	else
            		{
            			alertMessage(1, "警告", data.resultMessage);              			
            		}        	
            	$("#modal_moveGroup").modal('hide');
            },  
            error: function() {  
            	alertMessage(2, "异常", "ajax 函数  moveGroup 错误"); 
            	$("#modal_moveGroup").modal('hide');
              }  
        });
    	
    });
    
    $("#btn_table_add").click(function(){ 
    	$('#user_card').css('display','block');
		$('#group_card').css('display','block');
		
		$('#project_group').empty();
		$('#project_group').attr("disabled", "disabled");
		
		$("#select_start").val(0);
		$('#project_startLevel').attr("disabled", "disabled");
		
		$('#project_id').removeAttr("disabled");
		$('#project_id').val('');
    	$('#project_name').val('');
    	$('#select_our').val(1);
    	 	
    	$('#protocal_ip').val('');
    	$('#protocal_port').val('');
    	$('#protocal_name').val('');
    	$('#protocal_pwd').val('');
    	$('#protocal_type').val('BHKJ_XML1.0');
		
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
                	CheckCode:$('#project_pwd').val(),
                	startlevelControl:parseInt($("#select_start").val()),
                	DefaultStartlevel:parseInt($("#project_startLevel").val()),
                	isOurModule:$('#select_our').val(),                	
                	ConnectParameters:JSON.stringify(Protocal),
                	username:$('#user_name').val(),
                	userpwd:$('#user_pwd').val(),
                	groupname:$('#group_name').val(),
                	packLength:parseInt($('#group_packLength').val()),
                	batchCount:parseInt($('#group_batchcount').val()),
                	groupwidth:parseInt($('#group_width').val()),
                	groupheight:parseInt($('#group_height').val()),
                	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
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
            					projectPwd:$('#project_pwd').val(),
	        					autoGrp:data.Groupid,
	        					startLevelControl:parseInt($("#select_start").val()),
	        					defaultStartLevel:parseInt($("#project_startLevel").val()),
            					ConnectParameters:JSON.stringify(Protocal),
            					ourmodule:isOurModule,
            					grouplist:data.Groupid,
            					userlist:$('#user_name').val()
            			};
                		
                		var pitem={
	        					projectid:item.projectId,
	        					projectname:item.projectName	
	        			};
	        			projectList.push(pitem);
	        			
                		grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
                		if(grpsinfo==null)
                			{grpsinfo=[];}
            			var grpitem={};
            			grpitem.grpid=data.Groupid;
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
                			alertMessage(1, "警告", data.resultMessage);        			
                		}        	
                },  
                error: function() {  
                	alertMessage(2, "异常", "ajax 函数  createProject 错误");                	          
                  }  
            });
    		}
    	else {
    		$.ajax({  
                url:"/updateProject",
                data:{
                	projectid:$('#project_id').val(),
                	projectname:$('#project_name').val(),
                	AutoGroupTo:$('#project_group').val(),
                	CheckCode:$('#project_pwd').val(),
                	startlevelControl:parseInt($("#select_start").val()),
                	DefaultStartlevel:parseInt($("#project_startLevel").val()),
                	isOurModule:$('#select_our').val(),                	
                	ConnectParameters:JSON.stringify(Protocal),
                	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
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
            					projectPwd:$('#project_pwd').val(),
	        					autoGrp:$('#project_group').val(),
	        					startLevelControl:parseInt($("#select_start").val()),
	        					defaultStartLevel:parseInt($("#project_startLevel").val()),
            					ConnectParameters:JSON.stringify(Protocal),
            					ourmodule:isOurModule
            			};    
                		
                		for(var i=0;i<projectList.length;i++)
						{
			    			var id=projectList[i].projectid;
			    			if(id==item.projectId)
			    				{
			    				projectList[i].projectname=item.projectName
			    				break;
			    				}
						}
                		
                		var index = $('#modal_CreateProject').attr("data-index");
                		$('#projectinfo_table').bootstrapTable('updateRow', {index: index, row: item});
                		$("#modal_CreateProject").modal('hide');
                		}
                	else
                		{
                			alertMessage(1, "警告", data.resultMessage);        			
                		}        	
                },  
                error: function() {  
                	alertMessage(2, "异常", "ajax 函数  updateProject 错误");                	           
                  }  
            });
		}    	    	    	    
    });  
    
    $("#btn_project_limit").click(function(){
    	
    	var projectLimit = {
    			ExpTime : parseInt($('#project_infoSaveDay').val()),
    			ExpDisplay : parseInt($('#deleteinfo_display').val())
    	};
    	
//    	$('#modal_project_limit').attr("data-projectid",row.projectId);
//    	$('#modal_project_limit').attr("data-index",index);
    	
    	$.ajax({  
            url:"/updateProjectlimit",
            data:{
            	projectid:$('#modal_project_limit').attr("data-projectid"),             	
            	projectLimit:JSON.stringify(projectLimit),
            	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
    			},  
            type:"post",  
            dataType:"json", 
            success:function(data)  
            {       	  
            	if(data.result=="success")
            		{   		
            		var index = $('#modal_project_limit').attr("data-index");
            		var rows = $('#projectinfo_table').bootstrapTable('getData');//行的数据
        		    var item = "";
        		    for(var i=0;i<rows.length;i++){
	    		         if(rows[i].projectId == $('#modal_project_limit').attr("data-projectid")){
	    					item = rows[i];
	    		             break;
	    		         }
        		    }     
        			item.projectLimit = JSON.stringify(projectLimit);
            		$('#projectinfo_table').bootstrapTable('updateRow', {index: index, row: item});
            		$("#modal_project_limit").modal('hide');
            		}
            	else
            		{
            			alertMessage(1, "警告", data.resultMessage);        			
            		}        	
            },  
            error: function() {  
            	alertMessage(2, "异常", "ajax 函数  updateProjectlimit 错误");                	           
              }  
        });
    });
}

function operateFormatter(value, row, index) {
    return [
    	'<a class="edit" href="javascript:void(0)" style="margin:0px 5px;" title="编辑">',
        '<i class="fa fa-edit"></i>编辑',
        '</a>'+
        '<a class="limit" href="javascript:void(0)" style="margin:0px 5px;" title="权限">',
        '<i class="fa fa-cog"></i>权限',
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
			
			$('#project_group').removeAttr("disabled");
			
			getGroupbyProjectid(row.projectId,'project_group');
			
			$('#project_id').attr("disabled", "disabled");
			$('#project_id').val(row.projectId);
        	$('#project_name').val(row.projectName);
        	if(row.ourmodule=='否')
        		{$('#select_our').val(0);}
        	else{$('#select_our').val(1);}        	        	
        	
        	$('#project_pwd').val(row.projectPwd);
        	$('#select_start').val(row.startLevelControl);
        	
        	var select_start = parseInt($("#select_start").val());
        	if(select_start==0)
        		{
        		$('#project_startLevel').attr("disabled", "disabled");
        		}
        	else {
        		$('#project_startLevel').removeAttr("disabled");
    		}		
        	
        	$('#project_startLevel').val(row.defaultStartLevel);        	
        	
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
        'click .limit': function (e, value, row, index) {  
        	var projectId = row.projectId;
        	//projectLimit: "{"ExpTime":60,"ExpDisplay":1}"
        	var projectLimit = row.projectLimit;
        	if(projectLimit!=null)
        		{
        		var JprojectLimit = JSON.parse(projectLimit);
        		$('#project_infoSaveDay').val(JprojectLimit.ExpTime);
        		$('#deleteinfo_display').val(JprojectLimit.ExpDisplay);
        		}
        	$('#modal_project_limit').attr("data-projectid",row.projectId);
        	$('#modal_project_limit').attr("data-index",index);
        	
			$("#modal_project_limit").modal('show');			
        },
        'click .remove': function (e, value, row, index) {  
        	var projectId = row.projectId;
        	        	        	        	
        	$('#modal_project_remove').attr("data-projectid",projectId);
			$("#modal_project_remove").modal('show');			
        }
}
//获取分组按项目编号
function getGroupbyProjectid(projectid,SelectName) {
	$.ajax({  
        url:"/getGroupbyProjectid", 
        data:{
        	projectid:projectid,
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
			},  
        type:"post",  
        dataType:"json", 
        success:function(data)  
        {       
        	$('#'+SelectName).empty();
        	if(data!=null && data.length>0)
        		{
        		for(var i=0;i<data.length;i++)
        			{
        			var sel="";
        			if(data[i].isSelect==1)
        				{sel = "selected";}
        			var item="<option "+sel+" value='"+data[i].grpid+"'>"+data[i].grpname+"</option>";
        			$('#'+SelectName).append(item);
        			}
        		
        		}        	       	       	
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getGroupbyProjectid 错误");            
          }  
    });
}

//按组取广告列表
function getProjectlist()
{			
	$.ajax({  
        url:"/getProjectlist",           
        type:"post",
        data:{
        	adminname:JSON.parse(localStorage.getItem("adminInfo")).adminname
        },
        success:function(data)  
        {               				
        	if(data!=null && data.length>0)
        		{   
        			var ArrayTable = [];
        			        			
	        		for(var i=0;i<data.length;i++)
					{
	        			var projectid=data[i].projectid;
	        			var projectname=data[i].projectname;
	        			var item={
	        					projectid:projectid,
	        					projectname:projectname	
	        			};
	        			projectList.push(item);

	        			var grouplist=data[i].grouplist;
	        			var userlist=data[i].userlist;
	        			var IsOurModule = data[i].IsOurModule;	        			
	        			var ConnectParameters = data[i].ConnectParameters;
	        			var projectLimit =  data[i].projectLimit;
	        			var strOurModule="是";
	            		if(IsOurModule == "0")
	            		{strOurModule="否";} 
	            		
	        			var playTimelength=data[i].playTimelength;
	        			var item={
	        					projectId:projectid,
	        					projectName:projectname,
	        					projectPwd:data[i].projectPwd,
	        					autoGrp:data[i].autoGrp,
	        					startLevelControl:data[i].startLevelControl,
	        					defaultStartLevel:data[i].defaultStartLevel,
	        					ConnectParameters:ConnectParameters,
	        					ourmodule:strOurModule,	        					
	        					grouplist:grouplist,
	        					userlist:userlist,
	        					projectLimit:projectLimit
	        			};
	        			
	        			ArrayTable.push(item);
					}
	        		
					
					$("#projectinfo_table").bootstrapTable('load', ArrayTable);
        			
        		}
        	else
        		{$("#projectinfo_table").bootstrapTable('removeAll');}
        },  
        error: function() {  
        	alertMessage(2, "异常", "ajax 函数  getProjectlist 错误");         	           
          }  
    });
}