function showBackButton(b=true) {
    return anime({
        targets: document.getElementById('go-back'),
        easing: "easeInSine",
        rotate: 0,
        opacity: 1,
        duration: b?250:0
    })
}
function hideBackButton() {
    return anime({
        targets: document.getElementById('go-back'),
        easing: "easeInSine",
        rotate: 180,
        opacity: 0,
        duration: 250
    })
}