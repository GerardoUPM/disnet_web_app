(function($){
  $(function(){

    $('.button-collapse').sideNav();

  }); // end of document ready

    $( "#logoutClick" ).click(function() {
        $( "#logoutForm" ).submit();
    });

    $( "#signinClick" ).click(function() {
        $( "#signInForm" ).submit();
    });

})(jQuery); // end of jQuery name space