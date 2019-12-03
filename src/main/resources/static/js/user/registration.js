/**
 * Created by gerardo on 19/09/2017.
 */

$(function () {
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal({
            //opción para que el modal se cierra haciendo click en el botón, no en cualquier parte
            dismissible: false
        });

    });

    $('#register_now-btn').on('click', function() {
        var userForm = getDataForm("#registrationForm");
        if(registerValidation(userForm, "registration")){
            swal({
                title: "Are you sure?",
                text: "All data is correct?",
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: "Yes, register me!",
                confirmButtonColor: "#ec6c62"
            }, function() {
                $(this).removeClass("waves-effect waves-light submit").addClass('disabled');
                $('#registrationForm').submit();
            });
        }
    });

});


/*Fuera para ser utilizado por otros archivos js*/
function getDataForm(form) {
    var $form = $(form);
    var formData = {
        firstName:      $form.find('input[name="firstName"]').val(),
        lastName:       $form.find('input[name="lastName"]').val(),
        email:          $form.find('input[name="email"]').val(),
        password:       $form.find('input[name="password"]').val(),
        password_again: $form.find('input[name="password_again"]').val(),
        institution:    $form.find('input[name="institution"]').val(),
        country:        $form.find('select[name="country"]').val(),
        occupation:     $form.find('input[name="occupation"]').val(),
        interest:       $form.find('textarea[name="interest"]').val()
    };
    return formData;
}

function registerValidation(user, form) {
    var bError = false;
    var message = "";

    if(user.firstName.length <= 0 || user.firstName.length > 55){
        bError = true;
        message = "First name empty. \n";
    }
    if(user.lastName.length <= 0 || user.lastName.length > 55){
        bError = true;
        message += "Last name empty. \n";
    }
    if(!isEmail(user.email)){
        bError = true;
        message += "Email with incorrect formatting or It's not an email. \n";
    }
    if(form === "registration"){
        if (user.password.length < 8 && user.password_again.length < 8) {
            bError = true;
            message += "Password empty or less a 8 characters. \n";
        }
        if (user.password != user.password_again) {
            bError = true;
            message += "Passwords are not the same. \n";
        }
    }else {
        if (user.password.length > 0) {
            if (user.password.length < 8 && user.password_again.length < 8) {
                bError = true;
                message += "Password empty or less a 8 characters. \n";
            }
            if (user.password != user.password_again) {
                bError = true;
                message += "Passwords are not the same. \n";
            }
        }
    }
    if(user.institution.length <= 0 || user.institution.length > 200){
        bError = true;
        message += "Institution empty. \n";
    }
    if(!isNumber(user.country)){
        bError = true;
        message += "Country incorrect. \n";/*+ user.country*/
    }
    if(user.occupation.length <= 0 || user.occupation.length > 55){
        bError = true;
        message += "Occupation empty. \n";
    }
    if(user.interest.length <= 0 || user.occupation.interest > 5000){
        bError = true;
        message += "Interest empty. \n";
    }

    if(bError){
        swal("Oops!", "Some fields are incorrect. Please verify! \n\n" + message, "error", {icon: "error"});
        return false;
    }else{
        /*alert(JSON.stringify(user));*/
        return true;
    }
}


$(window).on('load', function() {

    var pageName = document.location.pathname.match(/[^\/]+$/)[0];

    // alert(document.location.pathname.match(/[^\/]+$/)[0] + " GLG");

    //Mensaje porque no cumplimos la protección de datos aun
    if (pageName == "registration"){
        swal({
            title: "DISNET Information",
            text: "Registration is temporarily disabled. If you have any question please contact with the administrator",
            type: "info"
        });
    }
    disabledTrueOrFalseForm(true, pageName);
});


/**
 *
 */
function disabledTrueOrFalseForm(_disabledOption_, form) {
    $('input[name="firstName"]').prop('disabled', _disabledOption_);
    $('input[name="lastName"]').prop('disabled', _disabledOption_);
    $('input[name="email"]').prop('disabled', _disabledOption_);
    $('input[name="password"]').prop('disabled', _disabledOption_);
    $('input[name="password_again"]').prop('disabled', _disabledOption_);
    $('input[name="institution"]').prop('disabled', _disabledOption_);
    $('#country').prop('disabled', _disabledOption_);/*forma diferente*/
    $('select').material_select();
    $('input[name="occupation"]').prop('disabled', _disabledOption_);
    $('textarea[name="interest"]').prop('disabled', _disabledOption_);
    if (form=="home"){
        if (_disabledOption_){
            $("#save_personal_info-btn").addClass('disabled');
        } else{
            $("#save_personal_info-btn").removeClass('disabled');
            $('input[name="email"]').prop('disabled', !_disabledOption_);
        }
    } else if (form=="registration") {
        if (_disabledOption_){
            $("#register_now-btn").addClass('disabled');
        } else{
            $("#register_now-btn").removeClass('disabled');
        }
    }

}