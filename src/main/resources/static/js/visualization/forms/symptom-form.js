//TODO: common function w/ disease-form.js

// helper functions
function split( val ) {
    return val.split( /\s*\|\s*/ );
}
// helper functions
function splitNewLine( val ) {
    return val.split( /\s*\r?\n\s*/ );
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
                    url: `/visualization/search-by-symptom-autocompleteEndPoint?sourceId=SO01&date=2020-01-15&query=${query.replace(/ /g,"%20")}`,
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


// Handle removal of static chips.
$(document).on('click', '.chip .close', function (e) {
    e.preventDefault()
    let $chips = $(this).closest('.chips');
    if ($chips.attr('data-initialized')) {
        return;
    }
    $(this).closest('.chip').remove();
    let terms = split($("#disease-list2").val());
    let removedChipText = $(this).closest('.chip').children().remove().end().text();
    let index = terms.indexOf(removedChipText);
    if (index !== -1 ) terms.splice(index,1);
    $("#disease-list2").val(terms.join( " | " ))
    if ($("#chips2").find("div").length === 0) {
        $("#dummy-list-input2")[0].required = true;
        // $("#step-3").addClass("md-inactive")
        $(".submit-text").addClass("disable-submit")
        $(".step-3-button").addClass("disabled")
    }
});


pasteToChips($("#dummy-list-input2"), $("#disease-list2"), $("#chips2"), $("#flash"));


$(".tooltipped").tooltip({delay: 50})
