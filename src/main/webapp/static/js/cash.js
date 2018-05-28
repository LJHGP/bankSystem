$(function () {
    alert();
    $('#yglb').datagrid({
        url: "/yglb",
        title: "操作记录列表",
        pagination: true,// 是否分页
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
            field: 'name',
            width : fixWidth(0.2)
        }, {
            title: '操作者',
            field: 'pwd',
            align: 'center',
            width : fixWidth(0.2)
        }, {
            title: '操作时间',
            field: 'age',
            align: "center",
            width : fixWidth(0.2)
        }, {
            title: '金额',
            field: 'sex',
            width : fixWidth(0.2)
        }]],

        toolbar: '#tb',
        onLoadSuccess: function () {

        }
    });

    // 列宽
    function fixWidth(percent) {
        return document.documentElement.clientWidth
            * percent; // 这里你可以自己做调整
    }

    //存款
    $('#depositBtn').click(function () {
        alert("depositCommit");
        depositCommit();
    });
    //取款
    $('#drawBtn').click(function () {
        drawCommit();
    });
    //清理资金
    $('#clearCashBtn').click(function () {
        clearCashCommit();
    })

});

/* 存款,打开对话框 */
function depositOpen() {
    $('#depositForm').form('clear');
    $("#deposit").dialog('open');
}
/* 存款提交 */
function depositCommit() {
    $.ajax({
        type : "POST",
        url : '/api/account/deposited',
        contentType:"application/json;charset=utf-8",
        headers: {
            token: "0"
        },
        data : {
            amount : "1111"
        },
        success : function(result) {
            alert("11222");
        },
        error : function(err) {
            alert("111333111");
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
    console.info("进入修改页面提交数据");
    $('#drawForm').form('submit', {
        url: "udpuser",
        onSubmit: function () { //表单提交前的回调函数
            var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字
            if (!isValid) {

            }
            console.info(isValid);
            return isValid; // 如果验证不通过，返回false终止表单提交
            /*   alert("进入回调函数"); */
        },

        success: function (data) { //表单提交成功后的回调函数，里面参数data是我们调用/BasicClass/ModifyClassInfo方法的返回值。
            console.info(data);

            if (data == 0) {
                console.info(data);
                $("#updUser").dialog('close'); //关闭窗口
                $.messager.show({
                    title: '提示消息',
                    msg: '提交成功',
                    showType: 'show',
                    timeout: 1000,
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
                $('#yglb').datagrid('reload'); // 重新载入当前页面数据

            } else {
                $.messager.alert('提示信息', '提交失败，请联系管理员！', 'warning');
            }
        }
    });
};

/* 清理资金 */
function clearCashOpen() {
    $('#clearCashForm').form('clear');
    $("#clearCash").dialog('open');
};

/* 清理资金提交 */
function clearCashCommit() {
    console.info("进入修改页面提交数据");
    $('#clearCashForm').form('submit', {
        url: "udpuser",
        onSubmit: function () { //表单提交前的回调函数
            var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字
            if (!isValid) {

            }
            console.info(isValid);
            return isValid; // 如果验证不通过，返回false终止表单提交
            /*   alert("进入回调函数"); */
        },

        success: function (data) { //表单提交成功后的回调函数，里面参数data是我们调用/BasicClass/ModifyClassInfo方法的返回值。
            console.info(data);

            if (data == 0) {
                console.info(data);
                $("#updUser").dialog('close'); //关闭窗口
                $.messager.show({
                    title: '提示消息',
                    msg: '提交成功',
                    showType: 'show',
                    timeout: 1000,
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
                $('#yglb').datagrid('reload'); // 重新载入当前页面数据

            } else {
                $.messager.alert('提示信息', '提交失败，请联系管理员！', 'warning');
            }
        }
    });
};
function retUser() {
    $('#xzUser').window('open');
}