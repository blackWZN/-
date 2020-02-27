
layui.use(['element', 'jquery', 'layer', 'laytpl'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var storage = window.localStorage;

    //系统首页跳转
    $('#index a').on('click', function () {
        layer.msg($(this).text(), { icon: -1 });
        $('#bread a').eq(0).text(
            $(this).text()
        );
        $('#bread a').eq(1).html('');
        $('#content iframe').attr('src', 'systemIndex.htm');
    })

    //下拉菜单面包屑导航
    $('.layui-nav-child a').on('click', function () {
        layer.msg($(this).text(), { icon: -1 });
        $('#bread a').eq(0).text(
            $(this).parents('.layui-nav-child').siblings('a').text()
        );
        $('#bread a').eq(1).html(
            $(this).text()
        );
    })

    //没有子菜单的菜单 面包屑导航
    $('#choice a').on('click', function () {
        layer.msg($(this).text(), { icon: -1 });
        $('#bread a').eq(0).text(
            $(this).text()
        );
        $('#bread a').eq(1).html('');
    })


    //显示用户名
    $.ajax({
        url: 'http://localhost:8202/admin/getUsername',
        type: 'get',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        headers: {
            Authorization: storage.getItem('token')
        },
        success: function (data) {
            $('#username').text(data.data)
        }
    })

    //退出登录
    $('#loginOut').click(function () {
        storage.removeItem('token');
        location.href = 'login.html'
    })
});
