var loginDialog;
$(function() {

    $('#registryAccount').dialog({
        modal:true
    });
    //开户提交
    $('#registryAccountBtn').click(function () {
        registryAccountAction();
    })
    //登录
    loginDialog = $('#loginDialog').show().dialog({
        id: 'loginBtn',
        modal : true,
        closable : false,
        draggable : false,
        buttons : [ {
            text : '登录   ',
            /* disabled : true, */
            handler : function() {
                loginFun();
            }
        },{
            text : '开户    ',
            /* disabled : true, */
            handler : function() {
                openAccountForm();
            }
        } ]
    });

    $('#loginDialog input').keyup(function(event) {
        if (event.keyCode == '13') {
            loginFun();
        }
    });


    function loginFun() {
        //location.replace('/main');
        if ($('#form-body').form('validate')) {//validate方式，未使用form方式提交表单，该语句必须写
            $.post('/api/login/login', $('#form-body').serialize(), function(result) {
                console.info(result);
                if (result.returnValue == "SUCCESS") {
                    $('#loginDialog').dialog('close');
                    location.replace('/main');
                    console.log(result);
                } else {
                    $.messager.alert('错误',result.reason, 'error');
                }
                parent.$.messager.progress('close');
            }, "JSON");
        }
    }
});

function openAccountForm(){
    $('#registryAccountForm').form('clear');
    $("#registryAccount").dialog('open');
}

/* 开户 */
function registryAccountAction() {
    var param = $('#registryAccountForm').serialize();
    $.post('/api/customer/signIn',param, function(result) {
        $("#registryAccount").dialog('close');
        $.messager.alert('系统提示', result.reason, 'Info');
    }, "JSON");
};