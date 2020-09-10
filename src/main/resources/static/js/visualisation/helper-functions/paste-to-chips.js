const pasteToChips = function(jQuerySelector){
    jQuerySelector.on("paste", () => {
        console.log('poipoi')
        $("#flash").fadeIn('fast').delay(1500).queue((next)=>{
            $("#flash").fadeOut('slow', 'swing')
            next();
            console.log('poi')
        })
        $('#dummy-list-input').keypress(function(event){
            let keycode = (event.keyCode ? event.keyCode : event.which);
            if(+keycode === 13) {
                let textareaValue = $("#dummy-list-input").val();
                if (textareaValue) {
                    let allTerms = split($("#disease-list").val())
                    let diseases = [... new Set(split(textareaValue).map(term=> term.trim()))] //get array of unique values from the pasted list + remove possible whitespace characters
                    diseases.forEach((disease, idx) => {
                        if (!allTerms.includes(disease)&&disease) {
                            $("#disease-list").val($("#disease-list").val()+disease+' | ')
                            if (idx === 0) {
                                // adding first chip
                                $("#chips").children().first().before("<div class='chip' contenteditable='false'>" +
                                    disease +
                                    "<i class=\"material-icons close\">close</i>" +
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
                            } else {
                                $("#chips").find("div").last().after("<div class='chip' contenteditable='false'>" +
                                    disease +
                                    "<i class=\"material-icons close\">close</i>" +
                                    "</div>")
                            }
                        }
                    })
                    $("#dummy-list-input").val("")
                }
            }
        });

    })
}