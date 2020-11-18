$("a.step-3-button").on('click',(event)=> {
    let currentButton = event.target
    let currentRow = $(currentButton).parent().parent().parent()[0]
    let currentButtonId = currentButton.id
    if (currentButtonId) {
        let rows = document.querySelectorAll(`.step-3-row:not(.${currentButtonId})`)
        let placement = Array.prototype.indexOf.call(document.querySelectorAll(`.step-3-row`), document.querySelector(`.${currentButtonId}`))
        let radiosToDisplay = document.querySelector('.source-radios');
        $('#dropdown').fadeIn()

        rows.forEach((r,i)=>{
            anime({
                targets:  r,
                opacity: 0,
                easing: 'easeInOutSine',
                duration: 200+(60*(i+1))
            })


            anime({
                targets:  r,
                translateY: -200*(i+1),
                easing: 'easeInOutSine',
                duration: 600
            })
        })


        anime({
            targets:  currentRow,
            translateY: `-${10*placement}vh`,
            translateX: '-27%',
            easing: 'easeInOutSine',
            duration: 250,
            complete: () => {
                $(currentRow).addClass("shown-row")
                $(currentButton).addClass("shown-button")
            }
        })
        anime({
            targets: currentButton,
            translateX:'290%',
            easing: 'easeInOutSine',
            duration: 300,
            begin: ()=>{
                !$('.source-radios [type="checkbox"]').is(":checked") && $(currentButton).addClass('disabled',10)
                // $(currentButton).parent().parent().siblings('div:not(.source-radios)').switchClass( "s8", "s12", 100, "easeInOutQuad" );
                // $(currentButton).parent().parent().siblings('div:not(.source-radios)').children('span').children('br').remove()
                $(currentButton).html(
                    "<i class='material-icons md-48 pt-10'>chevron_right</i>" +
                    "<button type='submit' class='waves-button-input'></button>"
                )
            }

        })
        anime.timeline({
            easing: 'linear',
            duration: 250
        }).
        add({
            targets: radiosToDisplay,
            translateY: '-29vh',
            translateX: '2vw',
            duration: 10,
        })
            .add({
                targets: radiosToDisplay,
                opacity: 1
            })
    }

    else {
        if (!$('.step-3-button').hasClass('disabled')){
            $("#dummy-list-input").val("") //empty textarea to trigger html control in case step-2 is not filled
            let currentType = $(event.target).parent().attr('id')
            $('input[name=type]').val(currentType)
            $('input[name=mapping]').val($('input.mapping-dummy').filter((e,d)=>d.checked).map((a,b)=>b.value).toArray())

        }
    }
    $(event.currentTarget).children().each((idx,ele)=> {
        ele.click()
    })
})

$('.source-radios [type="checkbox"]').on("change", () => {
    if ($('.source-radios [type="checkbox"]').is(":checked") &&  $('.step-3-button').hasClass('disabled')) {
        $('.step-3-button').removeClass('disabled')
    }
    else if(!$('.source-radios [type="checkbox"]').is(":checked") && !$('.step-3-button').hasClass('disabled')){
        $('.step-3-button').addClass('disabled')
    }
})

$("#dropdown").on('click', () => {
    let rows = document.querySelectorAll('.step-3-row:not(.shown-row)')
    let placement = Array.prototype.indexOf.call(document.querySelectorAll(`.step-3-row`), document.querySelector('.shown-button'))
    let currentButton = document.querySelector('.shown-button')
    let currentRow = document.querySelector('.shown-row')
    let radiosToDisplay = document.querySelector('.source-radios');
    $('#dropdown').fadeOut()

    rows.forEach((r,i)=>{
        anime({
            targets:  r,
            opacity: 1,
            easing: 'easeInOutSine',
            duration: 200+(60*(i+1))
        })


        anime({
            targets:  r,
            translateY: 0,
            easing: 'easeInOutSine',
            duration: 600
        })
    })


    anime({
        targets:  currentRow,
        translateY: 0,
        translateX: 0,
        easing: 'easeInOutSine',
        duration: 250,
        complete: () => {
            $(currentRow).removeClass("shown-row")
            $(currentButton).removeClass("shown-button")
        }
    })
    anime({
        targets: currentButton,
        translateX: 0,
        easing: 'easeInOutSine',
        duration: 300,
        begin: ()=>{
            $('.step-3-button').removeClass('disabled',10)
            $(currentButton).html("")
        }

    })
    anime.timeline({
        easing: 'linear',
        duration: 250
    }).
    add({
        targets: radiosToDisplay,
        translateY: 0,
        translateX: 0,
        duration: 10,
    })
        .add({
            targets: radiosToDisplay,
            opacity: 0
        })
})
