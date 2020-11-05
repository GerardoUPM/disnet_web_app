// HELPER FUNCTIONS
function splitAutocomplete(val) {
    return val.split(/\s*\|\s*/);
}
function splitNewLine( val ) {
    return val.split( /\s*\r?\n\s*/ );
}
            // END //


// ENABLE STEPS IN DISEASE-FORM
const enableDateInput = ()=>{
    $("#calendar").removeClass("md-inactive")
    $("#date-input").prop('disabled', false)
}
const enableStep2 = ()=>{
    $("label[for='date-input']").addClass("active") //manually added class for materialize
    $("#dummy-list-input").prop("disabled", false) //enable step 2
    $("#step-2").removeClass("md-inactive")
    $("#tooltip").removeClass("md-inactive")
    $("#tooltip").addClass("tooltipped").tooltip({delay: 50})
}
const enableStep3 = function (){
    // when first chip is added, step 3 unblocks
    $("#step-3").removeClass("md-inactive")
    $(".submit-text").removeClass("disable-submit")
    $(".step-3-button").removeClass("disabled")
}
                // END //


// ADD CHIPS
function addFirstChip($chips, $dummyListInput, text) {
    // adding first chip
    $chips.children().first().before("<div class='chip' contenteditable='false'>"+
        text+
        "<i class=\"material-icons close\">close</i>"+
        "</div>")
    /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
        so when the first chip is written, that means the form is filled, so we remove the
        required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
     */
    $dummyListInput.removeAttr('required');
}
function addChipOtherThanFirst($chips, text) {
    $chips.find("div").last().after("<div class='chip' contenteditable='false'>"+
        text+
        "<i class=\"material-icons close\">close</i>"+
        "</div>")
}

const pasteToChips = function($dummyInput,$actualInput, $chips, $flash, unblockingFunction=false){
    $dummyInput.on("paste", () => {
        $flash.fadeIn('fast').delay(1500).queue((next)=>{
            $flash.fadeOut('slow', 'swing')
            next();
        })
        $dummyInput.keypress(function(event){
            let keycode = (event.keyCode ? event.keyCode : event.which);
            if(+keycode === 13) {
                let textareaValue = $dummyInput.val();
                if (textareaValue) {
                    let allTerms = splitAutocomplete($actualInput.val())
                    let diseases = [... new Set(splitNewLine(textareaValue).map(term=> term.trim()))] //get array of unique values from the pasted list + remove possible whitespace characters
                    diseases.forEach((disease, idx) => {
                        if (!allTerms.includes(disease)&&disease) {
                            $actualInput.val($actualInput.val()+disease+' | ')
                            if (idx === 0) {
                                // adding first chip
                                $chips.children().first().before("<div class='chip' contenteditable='false'>" +
                                    disease +
                                    "<i class=\"material-icons close\">close</i>" +
                                    "</div>")
                                /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
                                    so when the first chip is written, that means the form is filled, so we remove the
                                    required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
                                 */
                                $dummyInput.removeAttr('required');
                                if (unblockingFunction){
                                    unblockingFunction();
                                }

                            } else {
                                $chips.find("div").last().after("<div class='chip' contenteditable='false'>" +
                                    disease +
                                    "<i class=\"material-icons close\">close</i>" +
                                    "</div>")
                            }
                        }
                    })
                    $dummyInput.val("")
                }
            }
        });

    })
}
                // END //


// WIDGETS

function initializeDatePicker(){
    const datesJson = $('#date-input').data('dates');

    let sourceId = $('#source option:selected').val();

    let dates = datesJson[sourceId]
        .map(d=> new Date(d))
        .sort((a,b)=>a-b);
    let lastDay = new Date(dates[dates.length-1]);
    lastDay.setDate(lastDay.getDate() + 1) // for some reason default Date is set on the previous set date by default
    let datesAvailable = dates.map(d=> {
        d.setDate(d.getDate()-1) //for some reason beforeShowDay does the action on the following date. So here I'm just "counteracting" that
        return new Date(d).toISOString().substring(0,10)
    })

    return $('#date-input').datepicker({
        beforeShowDay: (date) => {
            let currentDate = new Date(date).toISOString().substring(0, 10);
            return [datesAvailable.includes(currentDate)]
        },
        defaultDate: lastDay,
        gotoCurrent: true,
        dateFormat: "yy-mm-dd",
        onSelect: ()=> enableStep2()
    }).attr('readonly', 'readonly')
}

// EVENTS

// Handle removal of static chips.
$(document).on('click', '.chip .close', function (e) {
    e.preventDefault()

    let current = $(".shown-form").attr('id');
    if (current==="search-by-disease"){
        let $chips = $(this).closest('.chips');
        if ($chips.attr('data-initialized')) {
            return;
        }
        $(this).closest('.chip').remove();
        let terms = splitAutocomplete($("#disease-list").val());
        let removedChipText = $(this).closest('.chip').children().remove().end().text();
        let index = terms.indexOf(removedChipText);
        if (index !== -1 ) terms.splice(index,1);
        $("#disease-list").val(terms.join( " | " ))
        if ($("#chips").find("div").length === 0) {
            $("#dummy-list-input")[0].required = true;
            $("#step-3").addClass("md-inactive")
            $(".submit-text").addClass("disable-submit")
            $(".step-3-button").addClass("disabled")
        }
    }
    else {
        let $chips = $(this).closest('.chips');
        if ($chips.attr('data-initialized')) {
            return;
        }
        $(this).closest('.chip').remove();
        let terms = splitAutocomplete($("#disease-list2").val());
        let removedChipText = $(this).closest('.chip').children().remove().end().text();
        let index = terms.indexOf(removedChipText);
        if (index !== -1 ) terms.splice(index,1);
        $("#disease-list2").val(terms.join( " | " ))
        if ($("#chips2").find("div").length === 0) {
            $("#dummy-list-input2")[0].required = true;
            $("#gene2").addClass("disabled")
        }
    }

});

                        // END EVENTS//

