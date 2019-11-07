var addBtn = null;
var backBtn = null;
var devCode = null;
var devName = null;
var devPassword = null;
var devPassword1 = null;


devPassword = $("#devPassword");
devPassword1 = $("#devPassword1");
backBtn = $("#back");
addBtn = $("#add");

addBtn.bind("click", function () {

    if (devPassword.val() == devPassword1.val()) {
        if (confirm("是否确认提交数据")) {
            $("#userForm").submit();
        }
    } else {
        confirm("输入的密码不匹配请核对后重新输入");
    }


});

