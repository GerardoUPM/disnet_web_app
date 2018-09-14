(function($){
  $(function(){

      $(document).ready(function() {
          $('select').material_select();
      });

      $(".dropdown-button").dropdown();
      $(".dropdown-trigger").dropdown();

    $('.button-collapse').sideNav();

      $(".sidenav").sidenav();

  }); // end of document ready

    $( "#logout_li" ).click(function() {
        $( "#logout_li_form" ).submit();
    });

    $( "#signin_li" ).click(function() {
        $( "#signin_li_form" ).submit();
    });

    $( "#logout_li_mobile" ).click(function() {
        $( "#logout_li_form_mobile" ).submit();
    });

    $( "#signin_li_mobile" ).click(function() {
        $( "#signin_li_form_mobile" ).submit();
    });



})(jQuery); // end of jQuery name space

//Se colocan afuera para que puedan ser utilizadas por otros archivos js
function isNumber(n) {
    return !isNaN(parseInt(n)) && isFinite(n);
}

function isEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}