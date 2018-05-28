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
                if (result.success) {
                    if(result.success=='b'){
                        $('#loginDialog').dialog('close');
                        location.replace('/main');
                        console.log(result);
                    }else{
                        $.messager.alert('错误', '用户名或密码出错', 'error');
                    }
                } else {
                    $.messager.alert('错误', '系统出错', 'error');
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
    console.info(param);
    alert(param);
    $.post('/api/customer/signIn',param, function(result) {
        alert(result);
    }, "JSON");
};