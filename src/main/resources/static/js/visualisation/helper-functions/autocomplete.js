// TODO: common functions for symptom.form & form
// TODO: refactor form.js inside document.ready-function ? bc o/w there's asynchronous bugs when trying yo ref common functions

// // jquery ui autocomplete
// function autocomplete(preQueryAjaxUrl){
//     $("#dummy-list-input").on("keydown", function (event) {
//         if (event.keyCode === $.ui.keyCode.TAB &&
//             $(this).autocomplete("instance").menu.active) {
//             event.preventDefault();
//         }
//     })
//         .autocomplete(
//             {
//                 minLength: 2, delay: 500,
//                 source: function (request, callback) {
//                     let lrequest = split(request.term);
//                     let query = lrequest[lrequest.length-1];
//                     $.ajax({
//                         type: "GET",
//                         url: `${preQueryAjaxUrl}&query=${query.replace(/ /g,"%20")}`,
//                         dataType: 'json',
//                         // data:
//                         success: function (data) {
//                             callback(data.options);
//                         },
//                         error: function () {
//                             console.error("Autocomplete error");
//                         }
//                     })
//                 },
//                 messages: {
//                     noResults: '',
//                     results: function () {
//                     }
//                 },
//                 focus: function () {
//                     return false
//                 },
//                 select: function(event, ui){
//                     let terms = split(this.value);
//                     let allTerms = split($("#disease-list").val())
//                     // remove current input
//                     terms.pop();
//                     if (!allTerms.includes(ui.item.value)){
//                         // add the selected item
//                         terms.push( ui.item.value );
//                         // add empty slot to get " | " at the end
//                         terms.push( "" );
//                         if ($("#chips").find("div").length > 0){
//                             $("#chips").find("div").last().after("<div class='chip' contenteditable='false'>"+
//                                 ui.item.value+
//                                 "<i class=\"material-icons close\">close</i>"+
//                                 "</div>")
//                         } else {
//                             // adding first chip
//                             $("#chips").children().first().before("<div class='chip' contenteditable='false'>"+
//                                 ui.item.value+
//                                 "<i class=\"material-icons close\">close</i>"+
//                                 "</div>")
//                             /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
//                                 so when the first chip is written, that means the form is filled, so we remove the
//                                 required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
//                              */
//                             $("#dummy-list-input").removeAttr('required');
//                             // when first chip is added, step 3 unblocks
//                             $("#step-3").removeClass("md-inactive")
//                             $(".submit-text").removeClass("disable-submit")
//                             $(".step-3-button").removeClass("disabled")
//                         }
//                     } else {
//                         terms.push( "" );
//                     }
//                     this.value = "";
//                     $("#disease-list").val($("#disease-list").val()+terms.join( " | " ))
//                     return false;
//                 }
//             });
// }