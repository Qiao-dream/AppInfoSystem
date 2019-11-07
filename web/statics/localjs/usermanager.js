$(function () {
    $(":button").on("click", function () {
        var obj = $(this);
        $.ajax({
            type: "GET",
            url: "delete.json",
            data: {method: "delete", devCode: obj.attr("devCode")},
            dataType: "json",
            success: function (data) {
                if (data.Result == "true") {//通过申请
                    alert("删除成功！！！");
                    window.location.reload()
                } else if (data.Result == "false") {
                    alert("对不起，操作失败");
                    window.location.reload()
                } else if (data.Result == "notexist") {
                    alert("对不起，该用户不存在");
                    window.location.reload()
                }
            },
            error: function () {
                alert("操作失败！！！");
                window.location.reload()
            }
        });

    });
});