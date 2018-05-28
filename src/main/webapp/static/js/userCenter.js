$(function () {
    $('#userDatagrid').datagrid({
        url: "/yglb",
        title: "用户列表",
        pagination: true,// 是否分页
        pageSize: 12,//条数
        pageList: [3, 6, 12],//选择条数
        fit: true,//大自动适应
        selectOnCheck: true,//如果设置为true，单击一个复选框，将始终选择行。如果为false，不会选择行选中该复选框。
        loadMsg: "正在加载用户数据，请稍等...",
        fitColumn: false, //列自适应宽度
        striped: true,
        rownumbers: true,
        columns: [[{
            title: '姓名',
            field: 'name',
            width : fixWidth(0.2)
        }, {
            title: '性别',
            field: 'pwd',
            align: 'center',
            width : fixWidth(0.1)
        }, {
            title: '开户时间',
            field: 'age',
            align: "center",
            width : fixWidth(0.2)
        }, {
            title: '余额',
            field: 'age',
            align: "center",
            width : fixWidth(0.1)
        }, {
            title: '操作',
            field: 'sex',
            width : fixWidth(0.2),
            formatter : operationFormater
        }]],

        toolbar: '#tb',
        onLoadSuccess: function () {

        }
    });

    function operationFormater(){
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