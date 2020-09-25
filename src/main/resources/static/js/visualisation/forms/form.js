                //    STEP 1

// Display corresponding dates to selected source
const datesJson = $('#date-input').data('dates');
$('#source').on('change', ()=>{
    $("#calendar").removeClass("md-inactive") //enable date input
    $("#date-input").prop('disabled', false)

    // In case the following steps have already been triggered
    $('#date-input').datepicker("destroy")
    $('#date-input').val("") //empty date input
    $("#chips>.chip").remove() // remove disease-list chips
    $("#dummy-list-input").val("") // empty disease-list dummy input
    $("#disease-list").val("") // empty disease-list input
    $("label[for='date-input']").removeClass("active") //manually removed class for materialize
    $("#dummy-list-input").prop("disabled",true) //disable step 2
    $("#step-2").addClass("md-inactive")
    $("#tooltip").addClass("md-inactive")
    $("#tooltip").tooltip("remove")

    let sourceId = $('#source option:selected').val();

    let dates = datesJson[sourceId]
        .map(d=> new Date(d))
        .sort((a,b)=>a-b);
    let datesAvailable = dates.map(d=> {
        d.setDate(d.getDate()-1) //for some reason beforeShowDay does the action on the following date. So here I'm just "counteracting" that
        return new Date(d).toISOString().substring(0,10)
    })

    $('#date-input').datepicker({
        beforeShowDay: (date) => {
            let currentDate = new Date(date).toISOString().substring(0, 10);
            return [datesAvailable.includes(currentDate)]
        },
        defaultDate: dates[dates.length-1],
        gotoCurrent: true,
        dateFormat: "yy-mm-dd",
        onSelect: ()=> {
            $("label[for='date-input']").addClass("active") //manually added class for materialize
            $("#dummy-list-input").prop("disabled", false) //enable step 2
            $("#step-2").removeClass("md-inactive")
            $("#tooltip").removeClass("md-inactive")
            $("#tooltip").addClass("tooltipped").tooltip({delay: 50})
        }
    })
})

                //   STEP 2

// helper functions
function split( val ) {
    return val.split( /\s*\|\s*/ );
}

/*const javaDateFormat = function(stringDate){
    let date = new Date(stringDate);
    let dateTimeFormat = new Intl.DateTimeFormat('en', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    })
    let [{value: month},,
        {value: day},,
        {value: year}] = dateTimeFormat.formatToParts(date)
    return `${year}-${month}-${day}`
}*/

// jquery ui autocomplete
$("#dummy-list-input").on("keydown", function (event) {
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
                    url: `/visualisation/autocompleteEndPoint?sourceId=${$('#source option:selected').val()}&date=${$('#date-input').val()}&query=${query.replace(/ /g,"%20")}`,
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
                let allTerms = split($("#disease-list").val())
                // remove current input
                terms.pop();
                if (!allTerms.includes(ui.item.value)){
                    // add the selected item
                    terms.push( ui.item.value );
                    // add empty slot to get " | " at the end
                    terms.push( "" );
                    if ($("#chips").find("div").length > 0){
                        $("#chips").find("div").last().after("<div class='chip' contenteditable='false'>"+
                            ui.item.value+
                            "<i class=\"material-icons close\">close</i>"+
                            "</div>")
                    } else {
                        // adding first chip
                        $("#chips").children().first().before("<div class='chip' contenteditable='false'>"+
                            ui.item.value+
                            "<i class=\"material-icons close\">close</i>"+
                            "</div>")
                        /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
                            so when the first chip is written, that means the form is filled, so we remove the
                            required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
                         */
                        $("#dummy-list-input").removeAttr('required');
                        // when first chip is added, step 3 unblocks
                        $("#step-3").removeClass("md-inactive")
                        $(".submit-text").removeClass("disable-submit")
                        $(".step-3-button").removeClass("disabled")
                    }
                } else {
                    terms.push( "" );
                }
                this.value = "";
                $("#disease-list").val($("#disease-list").val()+terms.join( " | " ))
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
    let terms = split($("#disease-list").val());
    let removedChipText = $(this).closest('.chip').children().remove().end().text();
    let index = terms.indexOf(removedChipText);
    if (index !== -1 ) terms.splice(index,1);
    $("#disease-list").val(terms.join( " | " ))
    if ($("#chips").find("div").length === 0) {
        $("#dummy-list-input").prop('required',true);
        $("#step-3").addClass("md-inactive")
        $(".submit-text").addClass("disable-submit")
        $(".step-3-button").addClass("disabled")
    }
});

pasteToChips($("#dummy-list-input"));

// $('#searchLocus').on('focus', function () {
//     if ($('#searchGenesBy_2').is(':checked')) {
//         $(this).autocomplete("enable")
//         // .load(location.href + "#searchLocus")
//         // location.reload()
//     } else {
//         $(this).autocomplete("disable")
//     }

$(document).ready(function(){
    // TODO: common-function w/ symptom-form
    //     $(".input>.waves-button-input").on('click', e => {
    $("input.waves-button-input").on('click', e => {
        $("#dummy-list-input").val("") //empty textarea to trigger html control in case step-2 is not filled
        $('input[name=type]').val($(e.currentTarget).attr('id'))
    })
    $("i.step-3-button").on('click', e => {

        $(e.currentTarget).children().each((idx,ele)=> {
            ele.click()
        })
    })
})









