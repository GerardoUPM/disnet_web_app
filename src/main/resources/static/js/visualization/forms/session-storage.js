// When the form is rendered, check the session, and if there's the form data put it in the form
const currentSession = sessionStorage;
const sessionKeys = Object.keys(currentSession)
const diseaseFormKeys = $('#search-by-disease-form :input').map((index, element)=>$(element).attr('name')).get();
if (diseaseFormKeys.every(n => sessionKeys.includes(n))) {

    enableDateInput()

    enableStep2()
    enableStep3()

    let filteredCurrentSession = {...currentSession}
    Object.keys(filteredCurrentSession).filter(k=>diseaseFormKeys.includes(k)).reduce((res, key) => (res[key] = filteredCurrentSession[key], res), {})
    // fill out inputs
    for ([k,v] of Object.entries(filteredCurrentSession)){
        document.querySelector(`[name='${k}']`).value = v
    }
    $("#mapping-input").val('') // remove any value to the mapping input
    initializeDatePicker()


    // fill dummy input with chips
    let allTerms = splitAutocomplete($("#disease-list").val())
    allTerms.pop() // there's a trailing "|"
    allTerms.forEach((element,index)=>{
        if(index===0){
            addFirstChip($("#chips"),$("#dummy-list-input"), element);
        }
        else {
            addChipOtherThanFirst($("#chips"), element)
        }
    })
}

const symptomFormKey = $('#search-by-symptom-form :input').map((index, element)=>$(element).attr('name')).get()[0];
if (sessionKeys.includes(symptomFormKey)){
    document.querySelector(`[name='${symptomFormKey}']`).value = currentSession[symptomFormKey]

    // fill dummy input with chips
    let allTerms = splitAutocomplete($("#disease-list2").val())
    allTerms.pop() // there's a trailing "|"
    allTerms.forEach((element,index)=>{
        if(index===0){
            addFirstChip($("#chips2"),$("#dummy-list-input2"), element);
        }
        else {
            addChipOtherThanFirst($("#chips2"), element)
        }
    })

    // enable submit
    $("#gene2").removeClass('disabled');

}



// When the disease-form is submitted save data to the session
$('#search-by-disease-form').on('submit', () => {
    $('#search-by-disease-form').serializeArray().forEach(d => sessionStorage.setItem(d.name, d.value))
})

// When the symptom-form is submitted save data to the session
$('#search-by-symptom-form').on('submit', () => {
    $('#search-by-symptom-form').serializeArray().forEach(d => sessionStorage.setItem(d.name, d.value))
})