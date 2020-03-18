$("#loginuser").click(function () {
    //点击进入按钮时 重新设置所有属性
    document.getElementById("divseccid").className = 'form-group has-feedback';
    document.getElementById("spanseccid").className = 'glyphicon form-control-feedback';
    document.getElementById("divseccid1").className = 'form-group has-feedback';
    document.getElementById("spanseccid1").className = 'glyphicon form-control-feedback';
    var label=document.getElementById("labeluser");
    label.innerHTML="";
    $("#userName").val("");
    $("#password").val("");
});

//点击登陆
$("#form1").on("click",".btn",function(e){
    //判断用户名 密码是否为空
    if ($("#userName").val() != null && $("#userName").val() != ""){

        if ($("#password").val() != null && $("#password").val() != ""){
            //采用Ajax传输数据
            $.ajax({
                url:"/loginAjax",
                type:"post",
                data:{"userName":$("#userName").val(),"password":$("#password").val()},
                dataType:"json",
                success:function(result){
                    if("true"== result.flag){
                        //登陆成功 返回主页
                        window.location.replace("/");  //刷新页面
                    }else{
                        //登陆失败 更改样式为错误样式
                        document.getElementById("divseccid").className = 'form-group has-error has-feedback';
                        document.getElementById("spanseccid").className = 'glyphicon glyphicon-remove form-control-feedback';
                        document.getElementById("divseccid1").className = 'form-group has-error has-feedback';
                        document.getElementById("spanseccid1").className = 'glyphicon glyphicon-remove form-control-feedback';
                        var label=document.getElementById("labeluser");
                        label.innerHTML="账号密码错误";
                        $("#userName").val("");
                        $("#password").val("");
                    }
                }
            })


        }else {
            document.getElementById("divseccid1").className = 'form-group has-error has-feedback';
            document.getElementById("spanseccid1").className = 'glyphicon glyphicon-remove form-control-feedback';
            var label=document.getElementById("labeluser");
            label.innerHTML="密码不能为空";
        }
    }else {
        document.getElementById("divseccid1").className = 'form-group has-error has-feedback';
        document.getElementById("spanseccid1").className = 'glyphicon glyphicon-remove form-control-feedback';
        document.getElementById("divseccid").className = 'form-group has-error has-feedback';
        document.getElementById("spanseccid").className = 'glyphicon glyphicon-remove form-control-feedback';
        var label=document.getElementById("labeluser");
        label.innerHTML="账号密码不能为空";
    }

})

//点赞系统
$("#likesbtu").click(function () {
    $.ajax({
        url:"/likes/addlikes",
        type:"post",
        data:{"articleId":$("#questionId").val()},
        dataType:"json",
        success:function(result){
            window.location.replace("/question/"+$("#questionId").val());
        }
    })
});
