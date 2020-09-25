$("a.step-3-button").on('click',(event)=> {
    let currentButton = event.target
    console.log(currentButton)
    let currentRow = $(currentButton).parent().parent().parent()[0]
    let currentButtonId = currentButton.id
    if (currentButtonId) {
        let rows = document.querySelectorAll(`.step-3-row:not(.${currentButtonId})`)
        let placement = Array.prototype.indexOf.call(document.querySelectorAll(`.step-3-row`), document.querySelector(`.${currentButtonId}`))
        let radiosToDisplay = document.querySelector('.source-radios');
        anime({
            targets:  rows,
            translateY: -500,
            opacity: 0,
            easing: 'easeInOutSine',
            duration: 250
        })

        anime({
            targets:  currentRow,
            translateY: `-${10*placement}vh`,
            translateX: -$(currentButton).width(),
            easing: 'easeInOutSine',
            duration: 250
        })
        anime({
            targets: currentButton,
            translateX: $(currentButton).parent().parent().siblings('.col').outerWidth(),
            easing: 'easeInOutSine',
            duration: 300,
            begin: ()=>{
                $(currentButton).addClass('disabled',10)
                // $(currentButton).parent().parent().siblings('div:not(.source-radios)').switchClass( "s8", "s12", 100, "easeInOutQuad" );
                $(currentButton).parent().parent().siblings('div:not(.source-radios)').children('span').children('br').remove()
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
            translateY: '-30vh',
            translateX: '4vw',
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
            console.log('fill input type')
            console.log($(event.target))
            console.log($(event.target).parent().attr('id'))
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

