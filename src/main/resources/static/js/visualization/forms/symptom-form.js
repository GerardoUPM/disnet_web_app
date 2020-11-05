//TODO: common function w/ disease-form.js

// jquery ui autocomplete
$("#dummy-list-input2").on("keydown", function (event) {
    $("#autocomplete-load").remove()
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
                let query = lrequest[lrequest.length - 1];
                $.ajax({
                    type: "GET",
                    url: `/visualization/search-by-symptom-autocompleteEndPoint?sourceId=SO01&date=2020-01-15&query=${query.replace(/ /g, "%20")}`,
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
            select: function (event, ui) {
                let terms = splitAutocomplete(this.value);
                let allTerms = splitAutocomplete($("#disease-list2").val())
                // remove current input
                terms.pop();
                if (!allTerms.includes(ui.item.value)) {
                    // add the selected item
                    terms.push(ui.item.value);
                    // add empty slot to get " | " at the end
                    terms.push("");
                    if ($("#chips2").find("div").length > 0) {
                        $("#chips2").find("div").last().after("<div class='chip' contenteditable='false'>" +
                            ui.item.value +
                            "<i class=\"material-icons close\">close</i>" +
                            "</div>")
                    } else {
                        // adding first chip
                        $("#chips2").children().first().before("<div class='chip' contenteditable='false'>" +
                            ui.item.value +
                            "<i class=\"material-icons close\">close</i>" +
                            "</div>")
                        /* The required attribute for step-2 points to a disabled textarea (used as a dummy)
                            so when the first chip is written, that means the form is filled, so we remove the
                            required attr  from textarea (otherwise html will always see it as empty cause it's disabled)
                         */
                        $("#dummy-list-input2").removeAttr('required');
                        $("#gene2").removeClass('disabled');
                    }
                } else {
                    terms.push("");
                }
                this.value = "";
                $("#disease-list2").val($("#disease-list2").val() + terms.join(" | "))
                return false;
            }
        });



// ADD LOADING ICON WHILE WAITING FOR AUTOCOMPLETE RESPONSE
$("#dummy-list-input2").on("autocompletesearch", () => {
    if ($("#ui-id-2").css('display')!=='none'){
        $("#ui-id-2").children().first().before(`
                <li class="ui-menu-item">
            <div tabindex="-1" class="ui-menu-item-wrapper center-align">
              <div class="preloader-wrapper small active">
                <div class="spinner-layer spinner-grey-only">
                  <div class="circle-clipper left">
                    <div class="circle"></div>
                  </div><div class="gap-patch">
                    <div class="circle"></div>
                  </div><div class="circle-clipper right">
                    <div class="circle"></div>
                  </div>
                </div>
          </div>
        </div>
        </li>
        `)
    }
    else {
        const offset = $("#dummy-list-input2").offset()
        const pos = $("#dummy-list-input2").position()
        const chipsPadT = $("#chips2").innerHeight() - $("#chips2").height();
        const chipsMarT = $("#chips2").outerHeight(true) - $("#chips2").outerHeight();
        const extraHeight = $("#chips2").height()-chipsPadT-chipsMarT
        $(document.body).append(`
    <ul tabindex="0" id="autocomplete-load" class="ui-menu ui-widget ui-widget-content ui-autocomplete ui-front dont-scroll" style="top: ${pos.top+offset.top+7-extraHeight}px; left: ${offset.left}px; width: ${$("#dummy-list-input2").width()}px;">
        <li class="ui-menu-item">
            <div tabindex="-1" class="ui-menu-item-wrapper center-align">
              <div class="preloader-wrapper small active">
                <div class="spinner-layer spinner-grey-only">
                  <div class="circle-clipper left">
                    <div class="circle"></div>
                  </div><div class="gap-patch">
                    <div class="circle"></div>
                  </div><div class="circle-clipper right">
                    <div class="circle"></div>
                  </div>
                </div>
          </div>
        </div>
        </li>
    </ul>
    `)
        console.log('autocompletesearch')
    }
})
$("#dummy-list-input2").on("autocompleteresponse", () => $("#autocomplete-load").remove())
const unblockSubmit = function () {
    $("#gene2").removeClass("disabled")
}

pasteToChips($("#dummy-list-input2"), $("#disease-list2"), $("#chips2"), $("#flash"), unblockSubmit);


$(".tooltipped").tooltip({delay: 50})
