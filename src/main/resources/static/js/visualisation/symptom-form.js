//TODO: common function w/ form.js

// helper functions
function split( val ) {
    return val.split( /\s*\|\s*/ );
}
// jquery ui autocomplete
$("#dummy-list-input2").on("keydown", function (event) {
    if (event.keyCode === $.ui.keyCode.TAB &&
        $(this).autocomplete("instance").menu.active) {
        event.preventDefault();
    }
})
    .autocomplete(
        {
            minLength: 2, delay: 500,
            source: function (request, callback) {
                let lrequest = split(request.term);
                let query = lrequest[lrequest.length-1];
                $.ajax({
                    type: "GET",
                    url: `/visualisation/search-by-symptom-autocompleteEndPoint?sourceId=SO01&date=2020-01-15&query=${query.replace(/ /g,"%20")}`,
                    dataType: 'json',
                    // data:
                    success: function (data) {
                        callback(data.options);
                    },
                    error: function () {
                        console.error("Autocomplete error");
                    }
                })
            },
            messages: {
                noResults: '',
                results: function () {
                }
            },
            focus: function () {
                return false
            },
            select: function(event, ui){
                let terms = split(this.value);
                let allTerms = split($("#disease-list2").val())
                // remove current input
                terms.pop();
                if (!allTerms.includes(ui.item.value)){
                    // add the selected item
                    terms.push( ui.item.value );
                    // add empty slot to get " | " at the end
                    terms.push( "" );
                    if ($("#chips2").find("div").length > 0){
                        $("#chips2").find("div").last().after("<div class='chip' contenteditable='false'>"+
                            ui.item.value+
                            "<i class=\"material-icons close\">close</i>"+
                            "</div>")
                    } else {
                        // adding first chip
                        $("#chips2").children().first().before("<div class='chip' contenteditable='false'>"+
                            ui.item.value+
                            "<i class=\"material-icons close\">close</i>"+
                            "</div>")
                        /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
                            so when the first chip is written, that means the form is filled, so we remove the
                            required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
                         */
                        $("#dummy-list-input2").removeAttr('required');
                    }
                } else {
                    terms.push( "" );
                }
                this.value = "";
                $("#disease-list2").val($("#disease-list2").val()+terms.join( " | " ))
                return false;
            }
        });

$(".tooltipped").tooltip({delay: 50})
