sessionStorage.removeItem('fill-from-history')

if (!document.referrer || new URL(document.referrer).pathname !== "/visualization/form" ) {
    $('#go-back-wrapper').remove()
}

$('#go-back').on('click', ()=> {
    hideBackButton()
    sessionStorage.setItem('fill-from-history', true)
    history.back()
})