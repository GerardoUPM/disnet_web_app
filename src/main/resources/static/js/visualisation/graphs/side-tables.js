/* MENU AND SIDE-TABLES */
const layoutClasses = $(".table-wrapper").attr('class').replace('table-wrapper','')

// when clicking the table, nodes are highlighted
$('.fader').on('click', e => {


    link.attr('opacity', 1)
    node.attr('opacity', 1)
    let selector = e.currentTarget.className.split(" ").find(d => /intersection_[0-9]*/.test(d))

    if (selector) {
        if (+e.currentTarget.dataset['toggled'] === 0) {
            d3.selectAll(node.nodes().filter(d => !d3.selectAll("g." + selector).nodes().includes(d))).attr('opacity', 0.1)
            let intersectionId = selector.replace("intersection_", "")
            let disNodes = d3.selectAll(link.filter(d => d.target.intersection.includes(intersectionId)).data().map(d => d.source))._groups[0]
            node.filter(d => disNodes.includes(d)).attr('opacity', 1)
            link.filter(d => !d.target.intersection.includes(intersectionId)).attr('opacity', 0.1)
            link.filter(d => d.target.intersection.includes(intersectionId))

            // turn off other rows before turning on this one
            $("tr[data-toggled=1]").each((d, e) => e.dataset['toggled'] = "0")
            // turn on current
            $("tr." + selector).each((d, e) => e.dataset['toggled'] = "1")
        } else {

            d3.selectAll(node.nodes().filter(d => !d3.selectAll("g." + selector).nodes().includes(d))).attr('opacity', 1)
            let intersectionId = selector.replace("intersection_", "")
            link.filter(d => !d.target.intersection.includes(intersectionId)).attr('opacity', 1)

            $("tr." + selector).each((d, e) => e.dataset['toggled'] = "0")
        }
    }
})


/* TABLE SCROLL */
let $th = $('.table-wrapper').find('thead th')
$('.table-wrapper').on('scroll', function () {
    let currentScroll = +this.scrollTop
    let n = currentScroll > 0 && $('.table-wrapper').find('caption').length === 1 ? currentScroll - 27 : currentScroll;
    $th.css('transform', 'translateY(' + n + 'px)');
});




$(document).click(function() {
    return menu.style("visibility")==="visible"&&menu.style("visibility","hidden")
});

$('body').on('click', '.get-disease-ajax', (event) => {
    event.stopPropagation();

    let feature = event.currentTarget.dataset['feature']
    let featureId = event.currentTarget.dataset['id'].replace(/^id/, '')
    let tabId = "tab_"+featureId
    let wrapperId = $('#new-wrapper').length ? ".active[id^='tab_']" : '#common-nodes'

    if (!$("#"+tabId).length){
        $.ajax({
            type:"get",
            data: {
                'type' : type,
                'id' : featureId
            },
            url:"/visualisation/ajax-get-diseases",
            dataType: "html",
            beforeSend: function () {
                $(wrapperId+' *').hide()
                $(wrapperId).children().first().before(
                    '<div id="loader" style="margin: auto">' +
                    '<div class="preloader-wrapper big active">' +
                    '<div class="spinner-layer spinner-grey-only">' +
                    '<div class="circle-clipper left">' +
                    '<div class="circle">' +
                    '</div>' +
                    '</div>' +
                    '<div class="gap-patch">' +
                    '<div class="circle">' +
                    '</div>' +
                    '</div>' +
                    '<div class="circle-clipper right">' +
                    '<div class="circle">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>')
                $(wrapperId).addClass('valign-wrapper')
                $(wrapperId).css('height', '90vh')

            },
            success: function(htmlTable){
                // TODO: can this be optimized???
                new Promise( (resolve) => {
                    if (!$('#new-wrapper').length){
                        $(".table-wrapper").removeClass(layoutClasses)
                        $(".table-wrapper").wrap(`<div class="row"><div id="new-wrapper" class="${layoutClasses}"></div></div>`)
                        $(".table-wrapper").before("    <ul class='tabs'>" +
                            "        <li class='tab col s4 two-lines'><a href='#common-nodes'><b class='pt-5'><span class='row mb-0'>Common</span><span class='row mb-0'>symptoms</span></b></a></li>" +
                            `       <li class='tab col s4'><a href='#${tabId}'>${feature}</a></li>` +
                            "    </ul>")
                        $(".table-wrapper").after(`<div id=${tabId}></div>`)
                    } else {
                        $('.tabs').append(`<li class='tab col s4'><a href='#${tabId}'>${feature}</a></li>`)
                        $('#new-wrapper').children().last().after(`<div id=${tabId}></div>`)
                    }
                    resolve()
                }).then(()=>{
                    $(document).ready(function () {
                        $('ul.tabs').tabs()
                    })
                }).then(()=>{

                    $(wrapperId).removeClass('valign-wrapper')
                    $(wrapperId).removeAttr('style')
                    $('#loader').remove()
                    $(wrapperId+' *:not(script)').show()
                    $('#'+tabId).html(htmlTable)
                })


            },
            error: (a,b,c) => {
                console.error('ajaxError')

            }
        });
    } else {
        $("a[href='#"+tabId+"']").click();
    }
})

$('body').on('click', '.tab', () => {$.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust()})


