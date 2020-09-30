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