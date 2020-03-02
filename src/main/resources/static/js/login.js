$("#form1").on("click",".btn",function(e){
    $.ajax({
        url:"/loginAjax",
        type:"post",
        data:{"userName":$("#userName").val(),"password":$("#password").val()},
        dataType:"json",
        success:function(result){
            if("true"== result.flag){
                window.location.reload()  //刷新页面
            }else{
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
})