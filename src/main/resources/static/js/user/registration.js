/**
 * Created by gerardo on 19/09/2017.
 */
$(function () {

    $(document).ready(function() {
        $('select').material_select();
    });

    $('#register_now-btn').on('click', function() {
        $(this).removeClass("waves-effect waves-light submit").addClass('disabled');
    });

    // REGISTER EVENT LISTENERS =============================================================

    $("#registrationForms").submit(function (event) {
        event.preventDefault();
        var $form = $(this);
        var formData = {
            firstName:      $form.find('input[name="firstName"]').val(),
            lastName:       $form.find('input[name="lastName"]').val(),
            email:          $form.find('input[name="email"]').val(),
            password:       $form.find('input[name="password"]').val(),
            institution:    $form.find('input[name="institution"]').val(),
            country:        $form.find('input[name="country"]').val(),
            occupation:     $form.find('input[name="occupation"]').val(),
            interest:       $form.find('textarea[name="interest"]').val()
        };

        doRegistration(formData);
    });

    function doRegistration(registrationData) {
        alert(JSON.stringify(registrationData))
        $.ajax({
            url: "/user/registration",
            type: "POST",
            data: JSON.stringify(registrationData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
/*
                setJwtToken(data.token);
                $login.hide();
                $notLoggedIn.hide();
                showTokenInformation();
                showUserInformation();
*/
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