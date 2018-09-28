(function($){
  $(function(){

      $(document).ready(function() {
          $('select').material_select();
          $('.scrollspy').scrollSpy();
      });

      $(".dropdown-button").dropdown();
      $(".dropdown-trigger").dropdown();

    $('.button-collapse').sideNav();

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


    /* ==========================================================================
   TABLE OF CONTENT ADD by hand GLG 2018-09-19
   ========================================================================== */
    // Floating-Fixed table of contents
    setTimeout(function() {
        var tocWrapperHeight = 260; // Max height of ads.
        var tocHeight = $('.toc-wrapper .table-of-contents').length
            ? $('.toc-wrapper .table-of-contents').height()
            : 0;
        var socialHeight = 95; // Height of unloaded social media in footer.
        var footerOffset = $('body > footer').first().length
            ? $('body > footer')
                .first()
                .offset().top
            : 0;
        var bottomOffset = footerOffset - socialHeight - tocHeight - tocWrapperHeight;

        if ($('nav').length) {
            console.log('Nav pushpin', $('nav').height());
            $('.toc-wrapper').pushpin({
                top: $('nav').height(),
                bottom: bottomOffset
            });
        } else if ($('#index-banner').length) {
            $('.toc-wrapper').pushpin({
                top: $('#index-banner').height(),
                bottom: bottomOffset
            });
        } else {
            $('.toc-wrapper').pushpin({
                top: 0,
                bottom: bottomOffset
            });
        }
    }, 100);
    /* ==========================================================================
   TABLE OF CONTENT ADD by hand GLG 2018-09-19
   ========================================================================== */



})(jQuery); // end of jQuery name space

//Se colocan afuera para que puedan ser utilizadas por otros archivos js
function isNumber(n) {
    return !isNaN(parseInt(n)) && isFinite(n);
}

function isEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}