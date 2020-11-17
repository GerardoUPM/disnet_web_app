let reloadOnBack = false;
const changeToSearchByDisease = function(b=true) {
    if (document.documentElement.clientWidth >= 601) {
        anime({
            targets: document.getElementById("select-search-method"),
            translateX: "-100%",
            duration: b?250:0,
            opacity: 0,
            easing: 'easeInExpo',
            complete: () => {
                $("#search-by-disease").fadeIn(50)
                $("#search-by-disease").addClass("shown-form")
            }
        })
        if (document.documentElement.clientHeight <= 401) {
            anime({
                targets: document.getElementById("search-by-disease"),
                easing: "easeInExpo",
                translateX: "-85%",
                duration: b?350:0
            })
        }
    else {
            anime({
                targets: document.getElementById("search-by-disease"),
                easing: "easeInExpo",
                translateX: "-85%",
                duration: b?350:0
            })
        }

    }
    else {
        anime({
            targets: $('.row-flex>*:not(#search-by-disease)').get(),
            translateY: "-100%",
            duration: b?250:0,
            opacity: 0,
            easing: 'easeInExpo',
            complete: () => {
                $("#search-by-disease").fadeIn(50)
                $("#search-by-disease").addClass("shown-form")
            }
        })
        anime({
            targets: document.getElementById("search-by-disease"),
            easing: "easeInExpo",
            translateY: "-45%",
            duration: b?350:0
        })
    }

    showBackButton()
}


const changeToSearchBySymptom = function(b=true) {
    if (document.documentElement.clientWidth >= 601) {
        anime({
            targets: document.getElementById("select-search-method"),
            translateX: "-100%",
            duration: b?250:0,
            opacity: 0,
            easing: 'easeInExpo',
            complete: () => {
                $("#search-by-symptom").fadeIn(50)
                $("#search-by-symptom").addClass("shown-form")
            }
        })
        if (document.documentElement.clientHeight <= 401) {
            anime({
                targets: document.getElementById("search-by-symptom"),
                easing: "easeInExpo",
                translateX: "-80%",
                duration: b?350:0
            })
        }
    else {
            anime({
                targets: document.getElementById("search-by-symptom"),
                easing: "easeInExpo",
                translateX: "-85%",
                duration: b?350:0
            })
        }

    }
    else {
        anime({
            targets: $('.row-flex>*:not(#search-by-symptom)').get(),
            translateY: "-100%",
            duration: b?250:0,
            opacity: 0,
            easing: 'easeInExpo',
            complete: () => {
                $("#search-by-symptom").fadeIn(50)
                $("#search-by-symptom").addClass("shown-form")
            }
        })
        anime({
            targets: document.getElementById("search-by-symptom"),
            easing: "easeInExpo",
            translateY: "-75%",
            duration: b?350:0
        })
    }


    showBackButton()

}

const goBackToMainPage = function (b=true) {
    if (document.documentElement.clientWidth >= 601) {
        anime({
            targets: document.getElementsByClassName("shown-form"),
            easing: "easeInExpo",
            translateX: "50%",
            duration: b?350:0
        })

        anime({
            targets: document.getElementById("select-search-method"),
            translateX: "0%",
            duration: b?250:0,
            opacity: 1,
            easing: 'easeInExpo',
            complete: () => {
                $(".shown-form").fadeOut(50)
                $(".shown-form").removeClass("shown-form")
            }
        })
    }
    else {
        anime({
            targets: $('.row-flex>*').get(),
            translateY: "0",
            duration: b?250:0,
            opacity: 1,
            easing: 'easeInExpo',
            complete: () => {
                $(".shown-form").fadeOut(50)
                $(".shown-form").removeClass("shown-form")
            }
        })
        anime({
            targets: document.getElementById("select-search-method"),
            easing: "easeInExpo",
            translateY: "0",
            duration: b?350:0
        })

    }
}


window.onload = function () {
    const hash = window.location.hash
    switch (hash) {
        case '#search-by-disease':
            changeToSearchByDisease(0);
            break;
        case "#search-by-symptom":
            changeToSearchBySymptom(0);
            break;
    }
}

$("#search-by-disease-button").on('click', (e) => {
    window.location.hash = "#search-by-disease"
})

$("#search-by-symptom-button").on('click', (e) => {
    window.location.hash = "#search-by-symptom"
})

$('#go-back').on('click', ()=> {
    hideBackButton()
    window.location.hash = ""
})


$(window).on('hashchange', () => {

    const hash = window.location.hash
    switch (hash) {
        case '#search-by-disease':
                changeToSearchByDisease()
            break;
        case "#search-by-symptom":
            changeToSearchBySymptom()
            break;
        default:
            if (reloadOnBack){
                location.reload()
            }
            else {
                goBackToMainPage();
            }
    }
})

const fadeGoBackButtonOnMobile = () => {
    return $('main').on('touchmove wheel', () => {
        let scroll = window.scrollY
        if (scroll > 0) {
            $("#go-back").css('opacity', 1 - scrollY / 100)
        } else {
            $("#go-back").css('opacity', 1)
        }
    })
}

if (document.documentElement.clientWidth <= 601 || document.documentElement.clientHeight <= 401 ) {
    fadeGoBackButtonOnMobile();
}

window.addEventListener('orientationchange', () => {
    if (document.documentElement.clientWidth <= 601 || document.documentElement.clientHeight <= 401 ) {
    reloadOnBack = true
    let current = $('.shown-form').attr('id');
    switch(window.orientation) {
        case -90: case 90: //landscape
            // if (current==='search-by-disease'){
                anime({
                    targets: document.getElementsByClassName('shown-form'),
                    translateY: '0%',
                    translateX: '-85%',
                    duration: 0

                })
            // }
            break;
        default: //portrait
            // if (current==='search-by-disease') {
                anime({
                    targets: document.getElementsByClassName('shown-form'),
                    translateX: '0%',
                    translateY: current==='search-by-disease'?'-45%':'-75%',
                    duration: 0

                })
            // }
            break;
    }
    }
    // console.log('orientchange')
    // switch($(".shown-form").attr('id')) {
    //     case "search-by-disease":
    //         // goBackToMainPage(0)
    //         changeToSearchByDisease(0);
    //         break;
    //     case "search-by-symptom":
    //         // goBackToMainPage(0)
    //         changeToSearchBySymptom(0)
    //         break;
    // }
})
