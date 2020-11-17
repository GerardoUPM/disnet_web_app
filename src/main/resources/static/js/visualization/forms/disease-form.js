                //    STEP 1

// Display corresponding dates to selected source
$('#source').on('change', ()=>{
    enableDateInput();

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

    initializeDatePicker()
})

                //   STEP 2
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
                let lrequest = splitAutocomplete(request.term);
                let query = lrequest[lrequest.length-1];
                $.ajax({
                    type: "GET",
                    url: `/visualization/autocompleteEndPoint?sourceId=${$('#source option:selected').val()}&date=${$('#date-input').val()}&query=${query.replace(/ /g,"%20")}`,
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
                let terms = splitAutocomplete(this.value);
                let allTerms = splitAutocomplete($("#disease-list").val())
                // remove current input
                terms.pop();
                if (!allTerms.includes(ui.item.value)){
                    // add the selected item
                    terms.push( ui.item.value );
                    // add empty slot to get " | " at the end
                    terms.push( "" );
                    if ($("#chips").find("div").length > 0){
                        addChipOtherThanFirst($("#chips"),ui.item.value)
                    } else {
                        addFirstChip($("#chips"),$("#dummy-list-input"), ui.item.value);
                        // when first chip is added, step 3 unblocks
                        enableStep3()
                    }
                } else {
                    terms.push( "" );
                }
                this.value = "";
                $("#disease-list").val($("#disease-list").val()+terms.join( " | " ))
                return false;
            }
        });


pasteToChips($("#dummy-list-input"), $("#disease-list"), $("#chips"), $("#flash"), enableStep3);

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









