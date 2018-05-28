$(function () {
    $('#userDatagrid').datagrid({
        url: "/api/account/accountList",
        title: "用户列表",
        pagination: true,// 是否分页
        fit: true,//大自动适应
        selectOnCheck: true,//如果设置为true，单击一个复选框，将始终选择行。如果为false，不会选择行选中该复选框。
        loadMsg: "正在加载用户数据，请稍等...",
        fitColumn: false, //列自适应宽度
        striped: true,
        rownumbers: true,
        columns: [[{
            title: '姓名',
            field: 'name',
            width: fixWidth(0.15)
        }, {
            title: '地址',
            field: 'address',
            align: 'center',
            width: fixWidth(0.15)
        }, {
            title: '生日',
            field: 'birth',
            align: "center",
            width: fixWidth(0.1)
        }, {
            title: '账户类型',
            field: 'type',
            align: "center",
            width: fixWidth(0.1)
        }, {
            title: "余额",
            field: 'balance',
            align: "center",
            width: fixWidth(0.1)
        }, {
            title: "待清理资金",
            field: 'unClearedBalance',
            align: "center",
            width: fixWidth(0.1)
        }, {
            title: '操作',
            field: 'sex',
            width: fixWidth(0.2),
            formatter: function (value, row, index) {
                alert(row);
                return operationFormater(row)
            }
        }]],


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

    function operationFormater(row) {
        var btnGroup = '<span class="easyui-linkbutton" onclick="release(\''
            + row.id
            + '\')">发布</span>'
            + '<span class="easyui-linkbutton" style="margin-left: 20px;" onclick="edit(\''
            + row.id
            + '\')">编辑</span>'
            + '<span class="easyui-linkbutton" style="margin-left: 20px;" onclick="deletes(\''
            + row.id + '\')">删除</span>';
        return btnGroup;
    }

    // 列宽
    function fixWidth(percent) {
        return document.documentElement.clientWidth
            * percent; // 这里你可以自己做调整
    }

});