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
                    let allTerms = split($actualInput.val())
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
        let terms = split($("#disease-list").val());
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
        let terms = split($("#disease-list2").val());
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