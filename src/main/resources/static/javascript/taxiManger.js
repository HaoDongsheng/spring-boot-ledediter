$(function(){
	
	$(".modal" ).draggable();
		
	initBTabel();
});

function initBTabel()
{
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
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParams:queryParams,			//请求服务器时所传的参数        
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
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
        columns: [{
            title: '序号',
            field: 'id',            
            formatter: function (value, row, index) {
                var pageSize = $('#taxi_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#taxi_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        }, {
            field: 'name',
            title: '终端编号'
            
        }, {
            field: 'groupid',
            title: '分组名称'
            
        }, {
            field: 'LED_ID',
            title: 'LED编号'
         
        }, {
            field: 'SIMNo',
            title: 'SIM卡号'
         
        }, {
            field: 'DtuKey',
            title: 'DTU编号'
          
        }
        ]
    });  
    
    // 得到查询的参数
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的            
            pageSize: params.limit,                         //页面大小
            pageNum: (params.offset / params.limit) + 1,   //页码
            sort: params.sort,      //排序列名  
            sortOrder: params.order //排位命令（desc，asc） 
        };
        return temp;
    };
}