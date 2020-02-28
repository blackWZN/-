
layui.use(['element', 'jquery', 'layer', 'laytpl'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var storage = window.localStorage;

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

    //刷新token
    setInterval(function () {
        $.ajax({
            url: 'http://localhost:8202/admin/refreshToken',
            type: 'get',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            headers: {
                Authorization: storage.getItem('token')
            },
            success: function (data) {
                if (data.status == '500') {
                    layer.open({
                        content: data.message,
                        yes: function (index, layero) {
                            layer.close(index);
                            location.href = 'login.html';
                        }
                    });
                }
            }
        })
    }, 1000 * 60 * 24)

    //退出登录
    $('#loginOut').click(function () {
        storage.removeItem('token');
        location.href = 'login.html'
    })
});
