$(function () {
    $('#userDatagrid').datagrid({
        url: "/api/account/accountList",
        title: "用户列表",
        pagination: false,// 是否分页
        fit: true,//大自动适应
        selectOnCheck: true,//如果设置为true，单击一个复选框，将始终选择行。如果为false，不会选择行选中该复选框。
        loadMsg: "正在加载用户数据，请稍等...",
        fitColumn: false, //列自适应宽度
        striped: true,
        rownumbers: true,
        columns: [[{
            title: '姓名',
            field: 'name',
            align: "center",
            width : fixWidth(0.08)

        }, {
            title: '地址',
            field: 'address',
            align: 'center',
            width : fixWidth(0.2)

        }, {
            title: '生日',
            field: 'birth',
            align: "center",
            width: fixWidth(0.1)
        }, {
            title: '账户类型',
            field: 'type',
            align: "center",
            width : fixWidth(0.07)
        }, {
            title: "余额(元)",
            field: 'balance',
            align: "center",
            width : fixWidth(0.1)
        },{
            title: "待清理资金(元)",
            field: 'un_cleared_balance',
            align: "center",
            width : fixWidth(0.1)
        },{
            title: "账户状态",
            field: 'status',
            align: "center",
            width : fixWidth(0.1),
            formatter: function(value,row,index){
                if('NORMAL' == value){
                    return "正常"
                }else if('SUSPENDED' == value){
                    return "冻结"
                }else{
                    return "关闭"
                }
            }
        },{
            title: '操作',
            field: 'sex',
            align: "center",
            width : fixWidth(0.1),
            formatter: function(value,row,index){
                return operationFormater(row)
            }
        }]],


        onLoadSuccess: function (result) {
            console.info(result);
            $("#frozeAccount").click(function(){
                $.messager.confirm('操作提示','您确定要冻结该账户',function(r){
                    if (r){
                        frozeAccount();
                    }
                });

            });
            $("#closeAccount").click(function(){
                $.messager.confirm('操作提示','您确定要关闭该账户',function(r){
                    if (r){
                        closeAccount();
                    }
                });

            });
        }
    });



    function operationFormater(row){
        var btnGroup = '<span  style = "color:red"; id = "frozeAccount">冻结账户</span>&nbsp;&nbsp;'
            + '<span style = "color:green" style="margin-left: 20px"; id = "closeAccount">关闭账户</span>';
        return btnGroup;
    }

    function frozeAccount(){
        $.ajax({
            type : "POST",
            url : '/api/account/unSuspended',
           /* data : {
                "number" : number
            },*/
            success : function(result) {
                if(result.returnValue == 'SUCCESS'){
                    reload();
                    $.messager.alert('系统提示',"操作成功", 'info');
                }else{
                    $.messager.alert('系统提示',result.reason, 'error');
                }

            },
            error : function(err) {
                $.messager.alert('系统提示',"操作失败", 'error');
            }
        });
    }
    function closeAccount(){
        $.ajax({
            type : "POST",
            url : '/api/account/close',
           /* data : {
                "number" : number
            },*/
            success : function(result) {
                if(result.returnValue == 'SUCCESS'){
                    reload();
                    $.messager.alert('系统提示',"操作成功", 'info');
                }else{
                    $.messager.alert('系统提示',result.reason, 'error');
                }
            },
            error : function(err) {
                $.messager.alert('系统提示',"操作失败", 'error');
            }
        });
    }

    // 列宽
    function fixWidth(percent) {
        return document.documentElement.clientWidth
            * percent; // 这里你可以自己做调整
    }
    function reload(){
        $("#userDatagrid").datagrid('reload');
    }

});