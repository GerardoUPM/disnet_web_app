/**
 * Created by gerardo on 19/09/2017.
 */
$(function () {

    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });

    // REGISTER EVENT LISTENERS =============================================================

    $("#loginForm").submit(function (event) {
        event.preventDefault();
        var $form = $(this);
        var formData = {
            username: $form.find('input[name="email"]').val(),
            password: $form.find('input[name="password"]').val()
        };

        doLogin(formData);
    });

    function doLogin(loginData) {
        alert(JSON.stringify(loginData))
        $.ajax({
            url: "/user/login",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                //setJwtToken(data.token);
                //$login.hide();
                //$notLoggedIn.hide();
                //showTokenInformation();
                //showUserInformation();
                alert(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {alert("<p>Spring exceptionGLG:<br>" + jqXHR.responseJSON.exception + "</p>")
                    $('#loginErrorModal')
                        //.modal("show")
                        //.find(".modal-body")
                        //.empty()
                        //.html("<p>Spring exceptionGLG:<br>" + jqXHR.responseJSON.exception + "</p>");
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    }




});

function showTermsAndConditions() {
    swal("DISNET Terms and Conditions!", "Estos son los terminos y condiciones de DISNET: ", "info", {icon: "info"});
}