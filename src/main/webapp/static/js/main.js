$(function () {
    $('.wu-side-tree a').bind("click", function () {
        var title = $(this).text();
        var url = $(this).attr('data-link');
        var iconCls = $(this).attr('data-icon');
        var iframe = $(this).attr('iframe') == 1 ? true : false;
        addTab(title, url, iconCls, iframe);
    });
})

function openLogin() {
    $('#loginForm').form('clear');
    $("#login").dialog('open');
}

function logout() {
    $.messager.confirm('exit', '确定退出吗?', function (r) {
        if (r) {
            $.post('/api/login/logout', function (result) {
                if (result.returnValue === 'SUCCESS') {
                    location.replace('/');
                }
            }, "JSON").error(function (err) {
                if (err.status === 403) {
                    location.replace('/');
                } else {
                    $.messager.alert('系统提示', "操作失败", 'error');
                }
            });
        }
    });
}

function switchThemes() {
    alert("切换皮肤");

}

/**
 * Name 添加菜单选项
 * Param title 名称
 * Param href 链接
 * Param iconCls 图标样式
 * Param iframe 链接跳转方式（true为iframe，false为href）
 */
function addTab(title, href, iconCls, iframe) {
    var tabPanel = $('#wu-tabs');
    if (!tabPanel.tabs('exists', title)) { //如果tab已经存在,则选中并刷新该tab
        var content = '<iframe scrolling="auto" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
        if (iframe) {
            tabPanel.tabs('add', {
                title: title,
                content: content,
                iconCls: iconCls,
                fit: true,
                cls: 'pd3',
                closable: true
            });
        }
        else {
            tabPanel.tabs('add', {
                title: title,
                href: href,
                iconCls: iconCls,
                fit: true,
                cls: 'pd3',
                closable: true
            });
        }
    }
    else {
        tabPanel.tabs('select', title);
    }
}

/**
 * Name 移除菜单选项
 */
function removeTab() {
    var tabPanel = $('#wu-tabs');
    var tab = tabPanel.tabs('getSelected');
    if (tab) {
        var index = tabPanel.tabs('getTabIndex', tab);
        tabPanel.tabs('close', index1);
    }
}