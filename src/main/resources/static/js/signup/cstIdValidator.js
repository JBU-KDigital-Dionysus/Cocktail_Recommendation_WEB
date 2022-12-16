var idInput = $("#uid")

idInput.on("keyup", function(){
    var regExp = /^[a-z0-9]{4,20}$/g;
    if(regExp.test(idInput.val())){
        $.ajax({
            type:"POST",
            url: "/signup/checkId",
            data : {
                "cstId": idInput.val()
            },
            success: function(data) {
                if (data === 1) {
                    console.log("사용 불가능한 아이디")
                    $("#userIdInvalid").html("이미 존재하는 아이디")
                    $("input[name=userInsertForm]").addClass("is-invalid")
                } else {
                    console.log("사용 가능한 아이디", idInput.val());
                    $("input[name=userInsertForm]").removeClass("is-invalid")
                }
            }
        })
    }
});
