/**
 * Created by gerardo on 19/09/2017.
 */
$(function () {

    $(document).ready(function(){
        $('.modal').modal();
        disabledTrueForm();
        //Carga los datos de las consultas hechas por el usuario
        var requestHistoryTable = $('#request_history_table').DataTable({
            "sAjaxSource": "/user/request_history",
            "sAjaxDataProp": "",
            "responsive": true,
            "processing": true,
            "sEcho": 0,
            "iTotalRecords": "0",
            "iTotalDisplayRecords": "0",
             /*"sScrollX": "100%",*/
            /*"scrollX": true,*/
             /*"scrollY": 200,
            "bScrollCollapse": true,*/
            "bLengthChange": true,
            "order": [[ 3, "desc" ]],
            "aoColumns": [
                {
                    "class":          "details-control",
                    "orderable":      false,
                    "data":           null,
                    "defaultContent": ""
                },
                { "mData": "transactionId"},
                { "mData": "runtime_milliseconds"},
                { "mData": "datetimeFormat"},
                { "mData": "request"}
            ]
        });
        //Oculta la columna de request que es muy larga
        requestHistoryTable.column(4).visible(false);
        //Añade la clase "browser-default" para que se muestra el select del número de registros a mostrar
        $('select').addClass("browser-default");

        //Cada que se hace clic en el boton + se muestra un modal con la info de la request
        //Originalmente muestra una nueva fila con la información de toda la fila con la función format( row.data() )
        // Array to track the ids of the details displayed rows
        var detailRows = [];
        $('#request_history_table tbody').on( 'click', 'tr td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = requestHistoryTable.row( tr );
            //var idx = $.inArray( tr.attr('id'), detailRows );
            //swal("Request", row.data().request, "info");
            $('#requestBody').text(row.data().request);
            $('#modalRequest').modal('open');
            /*if ( row.child.isShown() ) {
                tr.removeClass( 'details' );
                row.child.hide();

                // Remove from the 'open' array
                detailRows.splice( idx, 1 );
            }
            else {
                tr.addClass( 'details' );
                row.child( format( row.data() ) ).show();

                // Add to the 'open' array
                if ( idx === -1 ) {
                    detailRows.push( tr.attr('id') );
                }
            }*/
        });

    });
    //Ordena la información que se mostrará en la fila creada al presionar el boton + en cada fila
    function format ( d ) {
        return 'Full name: '+d.transactionId+' '+d.request+'<br>'+
            'Salary: '+d.datetimeFormat+'<br>'+
            'The child row can contain any data you wish, including links, images, inner tables etc.';
    }

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

jQuery('.dataTable').wrap('<div class="dataTables_scroll" />');