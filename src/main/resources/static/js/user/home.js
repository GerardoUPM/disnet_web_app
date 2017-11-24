/**
 * Created by gerardo on 19/09/2017.
 */
$(function () {

    $(document).ready(function(){
        disabledTrueForm();
    });

    $('#edit_personal_info-btn').on('click', function() {
        var option  = $(this).val();
        if (option == "edit"){
            $(this).prop('value', 'cancel');
            $(this).html('cancel');
            /*swal("Hello world!" + option);*/
            disabledFalseForm();
            $("#save_personal_info-btn").removeClass('disabled');
        }else{
            //noinspection JSAnnotator
            $(this).prop('value', 'edit');
            $(this).html('edit');
            disabledTrueForm();
            $("#save_personal_info-btn").addClass('disabled');
        }

    });

    $('#save_personal_info-btn').on('click', function() {
        var userForm = getDataForm("#updateForm");
        if(registerValidation(userForm, "update")){

            swal({
                title: "Are you sure?",
                text: "Are you sure that you want to update your personal information?",
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: "Yes, update it!",
                confirmButtonColor: "#ec6c62"
            }, function() {
                doUpdate(convertUserForm(userForm));
            });

        }

    });


    function convertUserForm(userForm) {
        return {
            firstName:      userForm.firstName,
            lastName:       userForm.lastName,
            email:          userForm.email,
            password:       userForm.password,
            institution:    userForm.institution,
            country:        userForm.country,
            occupation:     userForm.occupation,
            interest:       userForm.interest
        };
    }

    function disabledTrueForm() {
        $('input[name="firstName"]').prop('disabled', true);
        $('input[name="lastName"]').prop('disabled', true);
        $('input[name="email"]').prop('disabled', true);
        $('input[name="password"]').prop('disabled', true);
        $('input[name="password_again"]').prop('disabled', true);
        $('input[name="institution"]').prop('disabled', true);
        $('#country').prop('disabled', true);/*forma diferente*/
        $('select').material_select();
        $('input[name="occupation"]').prop('disabled', true);
        $('textarea[name="interest"]').prop('disabled', true);
        $("#save_personal_info-btn").addClass('disabled');
    }

    function disabledFalseForm() {
        $('input[name="firstName"]').prop('disabled', false);
        $('input[name="lastName"]').prop('disabled', false);
        /*$('input[name="email"]').prop('disabled', false);*/
        $('input[name="password"]').prop('disabled', false);
        $('input[name="password_again"]').prop('disabled', false);
        $('input[name="institution"]').prop('disabled', false);
        $('select[name="country"]').prop('disabled', false);
        $('select').material_select();
        $('input[name="occupation"]').prop('disabled', false);
        $('textarea[name="interest"]').prop('disabled', false);
        $("#save_personal_info-btn").removeClass('disabled');
    }

    // REGISTER EVENT LISTENERS =============================================================
    function doUpdate(uploadData) {
        /*alert(JSON.stringify(uploadData));*/
        $.ajax({
            url: "/user/update",
            type: "POST",
            data: JSON.stringify(uploadData),
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
                /*alert(data);*/
                if(data.code === 200) {
                    swal("Updated!", "Your personal information was successfully updated!", "success", {icon: "success"});
                    location.reload(true);
                }else{
                    swal("Error", "Internal error occured: (" + data.code + ", " + data.status + ", " + data.action + ", " + data.message + ")", "error", {icon: "error"});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {
                    /*alert("<p>Spring exceptionGLG:<br>" + jqXHR.responseJSON.exception + "</p>")*/
                    swal("Error", "Internal error occured: " + jqXHR.responseJSON.exception, "error", {icon: "error"});
                    $('#loginErrorModal')
                    //.modal("show")
                    //.find(".modal-body")
                    //.empty()
                    //.html("<p>Spring exceptionGLG:<br>" + jqXHR.responseJSON.exception + "</p>");
                } else {
                    swal("Error", "An unexpected internal error occured: " + textStatus + ", " + jqXHR.responseJSON.exception, "error", {icon: "error"});
                }
            }
        });
    }




});