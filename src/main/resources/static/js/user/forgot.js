/**
 * Created by gerardo on 14/11/2017.
 */
$(function () {

    $('#reset_my_password-btn').on('click', function() {
        var $form = $('#resetPasswordForm');
        var formData = {
            email: $form.find('input[name="email"]').val()
        };
        if(isEmail(formData.email)){
            swal({
                title: "Are you sure?",
                text: "It's your email correct?",
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: "Yes, reset my password!",
                confirmButtonColor: "#ec6c62"
            }, function() {
                doReset(formData);
            });
        }else{
            swal("Oops!", "Email with incorrect formatting or It's not an email. Please verify! \n\n", "error", {icon: "error"});
            return false;
        }
    });


    function doReset(resetData) {
        /*alert(JSON.stringify(resetData));*/
        $.ajax({
            url: "/user/reset_password",
            type: "POST",
            data: JSON.stringify(resetData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                /*alert(data);*/
                if(data.code === 200) {
                    swal("Reset!", "Your password has been successfully reset!", "success", {icon: "success"});
                }else{
                    swal("Error", "Internal error occured: (" + data.code + ", " + data.status + ", " + data.action + ", " + data.message + ")", "error", {icon: "error"});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {
                    /*alert("<p>Spring exceptionGLG:<br>" + jqXHR.responseJSON.exception + "</p>")*/
                    swal("Error", "Internal error occured: " + jqXHR.responseJSON, "error", {icon: "error"});
                } else {
                    swal("Error", "An unexpected internal error occured: " + textStatus + ", " + jqXHR.responseJSON, "error", {icon: "error"});
                }
            }
        });
    }

});
