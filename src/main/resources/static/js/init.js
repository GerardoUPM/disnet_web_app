(function($){
  $(function(){

      $(document).ready(function() {
          $('select').material_select();
      });

    $('.button-collapse').sideNav();

  }); // end of document ready

    $( "#logoutClick" ).click(function() {
        $( "#logoutForm" ).submit();
    });

    $( "#signinClick" ).click(function() {
        $( "#signInForm" ).submit();
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