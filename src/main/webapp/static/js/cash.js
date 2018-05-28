$(function () {
    $('#cashDatagrid').datagrid({
        url: "/api/account/recordList",
        title: "操作记录列表",
        pagination: false,// 是否分页
        pageSize: 12,//条数
        pageList: [3, 6, 12],//选择条数
        fit: true,//大自动适应
        selectOnCheck: true,//如果设置为true，单击一个复选框，将始终选择行。如果为false，不会选择行选中该复选框。
        loadMsg: "请稍等...",
        fitColumn: false, //列自适应宽度
        /* 			width : 'auto',
         height : 'auto', */

        /* idField : 'Id', */
        //			singleSelect : true,
        striped: true,
        rownumbers: true,
        columns: [[{
            title: '类型',
            field: 'type',
            align: "center",
            width : fixWidth(0.2),
            formatter: function(value,row,index){
                if('withdraw' == value){
                    return "取钱"
                }else if('deposited' == value){
                    return "存钱"
                }else if('cheque' == value){
                    return "票存"
                }else{
                    return "清理资金"
                }
            }

        }, {
            title: '操作账户',
            field: 'number',
            align: 'center',
            width: fixWidth(0.2)
        }, {
            title: '操作时间',
            field: 'createTime',
            align: "center",
            width: fixWidth(0.2),
            formatter: function(value,row,index){
                 return value;
                /*if(value != null){

                    return formatDateTime(value);
                }*/
            }
        }, {
            title: '操作金额',
            field: 'amount',
            align: "center",
            width: fixWidth(0.2)
        }]],

        toolbar: '#tb',
        onLoadSuccess: function () {

        },
        onLoadError: function (err) {
            if (err.status === 403) {
                location.replace('/');
            } else {
                $.messager.alert('系统提示', "系统错误", 'error');
            }
        }
    });

    // 列宽
    function fixWidth(percent) {
        return document.documentElement.clientWidth
            * percent; // 这里你可以自己做调整
    }

    //存款
    $('#depositBtn').click(function () {
        depositCommit();
    });

    $('#depositResetBtn').click(function () {
        $("#deposit").dialog('close');
    });
    //取款
    $('#drawBtn').click(function () {
        drawCommit();
    });

    $('#drawResetBtn').click(function () {
        $("#draw").dialog('close');
    });
    //清理资金
    $('#clearCashBtn').click(function () {
        clearCashCommit();
    })

    $('#clearCashResetBtn').click(function () {
        $("#clearCash").dialog('close');
    });

});

/* 存款,打开对话框 */
function depositOpen() {
    $('#depositForm').form('clear');
    $("#deposit").dialog('open');
}

/* 存款提交 */
function depositCommit() {
    var depositType = $("#depositType").val();
    var pidDeposit = $("#pidDeposit").val();
    var depositAmount = $("#depositAmount").val();
    if (!depositType) {
        $.messager.alert('系统提示', "类型不能为空", 'info');
        return;
    }
    if (!pidDeposit) {
        $.messager.alert('系统提示', "密码不能为空", 'info');
        return;
    }
    if (!depositAmount) {
        $.messager.alert('系统提示', "金额不能为空", 'info');
        return;
    }
    $.ajax({
        type: "POST",
        url: '/api/account/deposited',
        data: {
            "depositType": depositType,
            "pid": pidDeposit,
            "amount": depositAmount
        },
        success: function (result) {
            if (result.returnValue == 'SUCCESS') {
                reload();
                $.messager.alert('系统提示', "操作成功", 'info');
                $('#depositForm').form('clear');
                $("#deposit").dialog('close');
            } else {
                $.messager.alert('系统提示', result.reason, 'error');
            }

        },
        error: function (err) {
            if (err.status === 403) {
                location.replace('/');
            } else {
                $.messager.alert('系统提示', "操作失败", 'error');
            }
        }
    });
}

/* 取款 */
function drawOpen() {
    $('#drawForm').form('clear');
    $("#draw").dialog('open');
};

/* 取款提交 */
function drawCommit() {
    var drawPid = $("#drawPid").val();
    var drawAmount = $("#drawAmount").val();
    if (!drawAmount) {
        $.messager.alert('系统提示', "金额不能为空", 'info');
        return;
    }
    if (!drawPid) {
        $.messager.alert('系统提示', "密码不能为空", 'info');
        return;
    }

    $.ajax({
        type: "POST",
        url: '/api/account/withdraw',
        data: {
            "pid": drawPid,
            "amount": drawAmount
        },
        success : function(result) {
            if(result.returnValue == 'SUCCESS'){
                reload();
                $.messager.alert('系统提示',"操作成功", 'info');
                $('#drawForm').form('clear');
                $("#draw").dialog('close');
            }else{
                $.messager.alert('系统提示',result.reason, 'error');
            }
        },
        error: function (err) {
            if (err.status === 403) {
                location.replace('/');
            } else {
                $.messager.alert('系统提示', "操作失败", 'error');
            }
        }
    });
};

/* 清理资金 */
function clearCashOpen() {
    $.ajax({
        type : "POST",
        url : '/api/account/clearFoundsAmount',
        success : function(result) {
            if(result.returnValue == 'SUCCESS'){
                if(result.data && result.data > 0){
                    $('#clearCashForm').form('clear');
                    $("#clearCash").dialog('open');
                    $("#clearCashAmount").text(result.data + "元");
                }else{
                    $.messager.alert('系统提示',"当前暂无待清理资金", 'info');
                }
            }else{
                $.messager.alert('系统提示',result.reason, 'error');
            }

        },
        error: function (err) {
            if (err.status === 403) {
                location.replace('/');
            } else {
                $.messager.alert('系统提示', "系统错误", 'error');
            }
        }
    });
};

/* 清理资金提交 */
function clearCashCommit() {
    var clearCashPid = $("#clearCashPid").val();
    if (!clearCashPid) {
        $.messager.alert('系统提示', "密码不能为空", 'info');
        return;
    }
    $.ajax({
        type: "POST",
        url: '/api/account/clearFounds',
        data: {
            "pid": clearCashPid
        },
        success : function(result) {
            if(result.returnValue == 'SUCCESS'){
                $.messager.alert('系统提示',"操作成功", 'info');
                reload();
                $('#clearCashForm').form('clear');
                $("#clearCash").dialog('close');
            }else{
                $.messager.alert('系统提示',result.reason, 'error');
            }

        },
        error: function (err) {
            if (err.status === 403) {
                location.replace('/');
            } else {
                $.messager.alert('系统提示', "系统错误", 'error');
            }
        }
    });
};

function reload() {
    $("#cashDatagrid").datagrid('reload');
}

function formatDateTime(timeStamp) {
    var date = new Date();
    date.setTime(timeStamp * 1000);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};

