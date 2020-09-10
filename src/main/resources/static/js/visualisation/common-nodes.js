const windowWidth = window.innerWidth;
//const svgLeftMargin = 50;
const offcenter = windowWidth < 601 ? 1 : 8 / 12; // col s8
const w = windowWidth * offcenter;
const margin = {'top': 20, 'right': 0, 'bottom': 0, 'left': 50}

const h = window.innerHeight - 64 - 40; // - header - margin(svg's margin-top + div.row's margin-bottom)
let svgSelection = d3.select("#d3")
let svg = svgSelection
    .attr("width", w)
    .attr("height", h)
    .style("border", "rgba(47, 79, 79, 0.3) 1px solid")
    .style("border-radius", "5px"); //TODO: style in css

// BASIC NODE SIZE
const nodeRadius = 5;

// GRAPH VARIABLES
const rcx = w / 2;
const rcy = h / 2;

// ----- MANAGE JSON DATA -----
const preNodes = JSON.parse(svg.attr('data-nodes'))['preNodes']
const preLinks = JSON.parse(svg.attr('data-links'))['preLinks']



// D3 node format
let nodes = [...new Set(preNodes.map(JSON.stringify))].map(JSON.parse)
nodes.forEach(n => {
    svg.append("text").text(n.name).attr("id", "dummy")
    let textLength = d3.select("#dummy").node().getComputedTextLength()
    if (textLength>w/4.5){
        let string = n.name
        let stringL = string.length
        let half = stringL/2;
        let first = string.lastIndexOf(" ", half)
        if (first===-1){first=stringL+1}
        let last = string.indexOf(" ", half)
        if (last===-1){last=stringL+1}
        let firstHalf, lastHalf;
        if (half-first  < last-half){
            firstHalf = string.slice(0,first)
            lastHalf = string.slice(first)
        } else {
            firstHalf = string.slice(0,last)
            lastHalf = string.slice(last)
        }

        n.splitName = [firstHalf, lastHalf]

        d3.select("#dummy").remove();
        svg.append("text").text(firstHalf.length>lastHalf.length ? firstHalf : lastHalf).attr("id", "dummy")
        textLength = d3.select("#dummy").node().getComputedTextLength();
    }
    d3.select("#dummy").remove();

    n.textLength = textLength
})


// calculate node degrees
let nodeList = nodes.map(node => node.id);
preLinks.forEach(link => nodes[nodeList.indexOf(link.target)].degree = (nodes[nodeList.indexOf(link.target)].degree + 1) || 1)

// name node intersections on nodes
let intersections = JSON.parse(svg.attr('data-intersections'))
nodes.forEach(node => {
    node.intersection = Object.keys(intersections).filter(idx => intersections[idx].includes(node.name))
})
const noIntersections = $.isEmptyObject(intersections)
// D3 link format
const index = new Map(nodes.map(d => [d.id, d]))
const links = preLinks.map(d => Object.assign(Object.create(d), {
    "source": index.get(d.source),
    "target": index.get(d.target),
    "value": +d.value
}));


const graphConst = {"nodes": nodes, "links": links}
let graph = graphConst
// *** D3 NETWORK *** //

const maxDegree = Math.max(...graph.nodes.reduce(function (result, node) {
    if (node.degree !== undefined) {
        result.push(node.degree);
    }
    return result
}, []));


const interpolateDegree = d3.scaleLinear().domain([1, maxDegree]).range([0.85, 0.15])
// FORCE SIMULATION

const featuresN = +graph.nodes.reduce((accumulator, node) => {
    return node.type === "feature" ? accumulator + 1 : accumulator + 0
}, 0)

const diseasesN = +graph.nodes.reduce((accumulator, node) => {
    return node.type === "disease" ? accumulator + 1 : accumulator + 0
}, 0)

let link, node, circle, labelGroup, label, splitLabel, plainLabel,
    simulation = d3.forceSimulation(graph.nodes);

function render(){

    simulation = simulation
        .force("link", d3.forceLink().id(d => d.id))
        .force("charge", d3.forceManyBody().strength(featuresN * nodeRadius > rcx ? -rcx / 2 : -featuresN * nodeRadius))
        .force("center", d3.forceCenter(rcx, rcy))
        .force("collide", d3.forceCollide().radius(nodeRadius * 2))
        .force("radial", d3.forceRadial().radius(d => d.type === "disease" ? (diseasesN === 1 ? 0 : (Math.log(diseasesN * nodeRadius) / Math.log(diseasesN + 1)) * rcx / 60) : (Math.log(featuresN * nodeRadius) / Math.log(featuresN + 1)) * rcx / 3).x(rcx).y(rcy).strength(1));

    let l = svg.selectAll(".link")
        .data(graph.links, d=>d.source+','+d.target)
    let n = svg.selectAll(".node")
        .data(graph.nodes, d=>d.id)

    enterLinks(l)
    enterNodes(n)

    link = svg.selectAll(".link")
    node = svg.selectAll(".node")
    circle = svg.selectAll(".circle")
    labelGroup = svg.selectAll(".label-group")
    label = svg.selectAll(".label")
    plainLabel = svg.selectAll(".no-split")
    splitLabel = svg.selectAll(".split-text")

    node.call(d3.drag()
        .on("start", dragStart)
        .on("drag", dragging)
        .on("end", dragEnd));

    // DEFINE SIMULATION
    simulation.nodes(graph.nodes)

    simulation.force("link")
        .links(graph.links);

}

function update(){

    let l = svg.selectAll(".link")
        .data(graph.links, d=>d.source+','+d.target)
    let n = svg.selectAll(".node")
        .data(graph.nodes, d=>d.id)

    enterLinks(l)
    exitLinks(l)
    enterNodes(n)
    exitNodes(n)
    /* TODO: fix bug:
    http://127.0.0.1:8000/visualisation/common-nodes?sourceId=SO01&date=2020-01-15&diseaseList=Acneiform+eruption+%7C+Acne+keloidalis+nuchae+%7C+Acne+cosmetica+%7C+Acne+miliaris+necrotica+%7C+Acne+fulminans+%7C+Acne+%7C+Acne+conglobata+%7C+Acne+aestivalis+%7C+Acne+with+facial+edema+%7C+Acne+mechanica+%7C+Acne+medicamentosa+%7C+Dermatitis+herpetiformis+%7C+Dermatopathia+pigmentosa+reticularis+%7C+Dermatographic+urticaria+%7C+Dermal+cylindroma+%7C+Dermatofibrosarcoma+protuberans+%7C+Dermatopolymyositis+%7C+Dermatophytid+%7C+Dermatoses+of+pregnancy+%7C+Dermatochalasis+%7C+Dermanyssus+gallinae+%7C+Dermatosis+neglecta+%7C+Dermatitis+gangrenosa+%7C+Dermatopathic+lymphadenopathy+%7C+Dermatophagia+%7C+Dermatitis+repens+%7C+Dermatosis+papulosa+nigra+%7C+Dermabrasion+%7C+Dermatofibroma+%7C+Dermatomycosis+%7C+Dermatomyositis+%7C+Dermatitis+%7C+Dermatophytosis+%7C+Edema+%7C+Edentulism+%7C+Edema+blister+%7C+Carcinoid+%7C+Carcinoid+syndrome+%7C+Carcinosarcoma+%7C+Carcinoma+%7C+Carcinoma+in+situ+%7C+Carcinoma+ex+pleomorphic+adenoma+%7C+Carcinocythemia+%7C+Melanoma+%7C+Melanocytic+nevus+%7C+MELAS+syndrome+%7C+Melanotic+neuroectodermal+tumor+of+infancy+%7C+Melanosis+coli+%7C+Melanosis+%7C+Melanoma-associated+leukoderma+%7C+Melancholic+depression+%7C+Freckle+%7C+Nevus+spilus+%7C+Nevoid+basal-cell+carcinoma+syndrome+%7C+Nevus+%7C+Nevus+psiloliparus+%7C+Nevus+unius+lateris+%7C+Nevus+sebaceous+%7C+Nevoid+hypertrichosis+%7C+Nevo+syndrome+%7C+Nevus+comedonicus+syndrome+%7C+Nevus+depigmentosus+%7C+Nevus+flammeus+nuchae+%7C+Nevus+anemicus+%7C+Nevus+of+Ota+%7C+Nevus+of+Ito+%7C+&type=gene
    SCORE -> 1 & VALUE: patched 1 - Nevoid basal-cell carcinoma */

    link = svg.selectAll(".link")
    node = svg.selectAll(".node")
    circle = svg.selectAll(".circle")
    labelGroup = svg.selectAll(".label-group")
    label = svg.selectAll(".label")
    plainLabel = svg.selectAll(".no-split")
    splitLabel = svg.selectAll(".split-text")

    node.call(d3.drag()
        .on("start", dragStart)
        .on("drag", dragging)
        .on("end", dragEnd));

    // DEFINE SIMULATION
    simulation.nodes(graph.nodes)
    simulation.force("link")
        .links(graph.links);

    simulation.alpha(1).restart()


}

function enterNodes(n){
    let g = n.enter().append("g")
        .attr("class", d => d.intersection.map(i => 'intersection_' + i).join(" ").concat(d.intersection.length?" menu node":" node"))

    let circleHelper = g.append("circle")
        .attr("class", "circle")
        .attr("r", d => nodeRadius)
        .attr("fill", d => d.degree !== undefined ? d3.interpolateInferno(interpolateDegree(d.degree)) : "#0d47a1") //TODO: fix bug : color is not working the same way as it used to --> probs bc/o redefined variables
        .attr("stroke-width", 1)
        .attr("stroke", d => d.type === "disease" ? "rgba(13,71,161,0.75)" : "GhostWhite")
        .attr("data-name", d => d.name)
        .attr("data-id", d => d.id)
        .attr("data-degree", d => d.degree);

    // INITIALIZE LABELS
    let labelGroupHelper = g.append("g")
        .attr("class", "label-group")
        .attr("id", d => d.id)
        .attr("data-text-length", d => d.textLength)

    let labelHelper = labelGroupHelper.append("text")
        .attr("font-weight", d => d.type === "disease" ? 500 : 400)
        .attr("class", d => d.splitName?"split-text label":"no-split label")

    labelHelper.filter(".no-split")
        .attr("y", d => d.type === "feature" ? nodeRadius : 2.1 * nodeRadius)
        .text(d => d.type === "disease" ? d.name : (d.degree > 1 || diseasesN === 1 || noIntersections ? d.name : "")) // No labels in nodes with degree 1 (too many nodes, looks messy)

    let splitLabelHelper = labelHelper.filter(".split-text")

    splitLabelHelper.append("tspan")
        .text(d => d.type === "disease" ? d.splitName[0] : (d.degree > 1 ? d.splitName[0] : "")) // No labels in nodes with degree 1 (too many nodes, looks messy)
    splitLabelHelper.append("tspan")
        .text(d => d.type === "disease" ? d.splitName[1] : (d.degree > 1 ? d.splitName[1] : "")) // No labels in nodes with degree 1 (too many nodes, looks messy)
        .attr("dy", "1.1em")

    // Display tooltips when hovering over nodes w/o label (= nodes with degree 1)
    circleHelper.on("mouseover", function () {
        if (+this.getAttribute("data-degree") === 1 && !noIntersections) {
            return tooltip.style("visibility", "visible")
                .text(this.getAttribute("data-name"));
        }
    })
        .on("mousemove", function () {
            return tooltip.style("top", (event.pageY - 10) + "px").style("left", (event.pageX + 10) + "px");
        })
        .on("mouseout", function () {
            return tooltip.style("visibility", "hidden");
        });

    g.on('mouseover', fade(0.3))
        .on('mouseout', fade(1))

    // Display menus
    d3.selectAll(".menu circle").on("click", function () {
        d3.event.stopPropagation()


        if (menu.style("visibility")==="visible" && this.getAttribute('data-id')===menu.attr("data-menu-id")) {
            return menu.style("visibility","hidden")
        }
        else {
            return menu.style("visibility", "visible")
                .attr("data-menu-id", this.getAttribute('data-id') )
                .html(
                    "<div class='row mb-10'>" +
                    "<div class='col s12 valign-wrapper'>" +
                    "<div class='col s9 menu-title'>" +
                    `<b>SEARCH BY ${type.toUpperCase()}</b>` +
                    "</div>" +
                    "<div class='col s3'>" +
                    "<a " +
                    "class='get-disease-ajax btn-floating btn-xs waves-effect waves-light grey' " +
                    "data-feature='"+this.getAttribute("data-name")+"'" +
                    "data-id='"+this.getAttribute("data-id")+"'>"+
                    "<i class= 'material-icons right '>chevron_right</i>" +
                    "</a>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row menu-description mb-5'>" +
                    "<div class='col s12 left-align'>" +
                    `Find diseases having <i>${this.getAttribute("data-name")}</i> as a ${type}` +
                    "</div>" +
                    "</div>"
                );

        }
    })

}
function exitNodes(n){
    // Avoid having a node menu when the node doesn't exist (bc it's been removed)
    if (menu.style("visibility")==="visible"){
        const preN = n
        let removedNodesId = Object.values(preN._exit[0].map(e=>d3.select(e).select("circle").attr("data-id")))
        let currentMenuId = d3.select(".menu-wrapper").attr("data-menu-id")
        removedNodesId.includes(currentMenuId) && menu.style("visibility", "hidden")
    }
    n.exit().remove()
}
function enterLinks(l){
    l.enter().insert("line", ".node")
        .attr("class", "link")
        .attr("stroke-width", d => {
            if(d.target.id==="id5727"){
                console.log(d)
            }
            return d.value*4
        })
        .attr("stroke", "#cccccc");
    l.on('mouseover', fadeLink(0.5))
        .on('mouseout', fadeLink(1))

}
function exitLinks(l){
    l.exit().remove()
}


// INITIALIZE TOOLTIPS
let tooltip = d3.select("body")
    .append("div")
    .style("position", "absolute")
    .style("z-index", "10")
    .style("visibility", "hidden")
    .style("background", "DarkSlateGray")
    .style("opacity", ".75")
    .style("padding", "6px")
    .style("border-radius", "10px")
    .style("font", "bold 12px sans-serif")
    .style("color", "white");

// INITIALIZE MENU
let menu = d3.select("body")
    .append("div")
    .attr("class", "row center-align m-0 menu-wrapper")
    .style("position", "absolute")
    .style("z-index", "10")
    .style("visibility", "hidden")
    .style("background", "Whitesmoke")
    .style("opacity", ".85")
    .style("padding", "6px")
    .style("border-radius", "3px")
    .style("border", "1px solid #bdbdbd")
    .style("color", "black");


// MAKE DRAGGABLE NODES
function dragStart(d) {
    if (!d3.event.active) simulation.alphaTarget(0.3).restart();
    d.fx = d.x;
    d.fy = d.y;
}

function dragging(d) {
    d.fx = d3.event.x;
    d.fy = d3.event.y;
}

function dragEnd(d) {
    if (!d3.event.active) simulation.alphaTarget(0);
    d.fx = null;
    d.fy = null;
}

// HIGHLIGHT LINKS AND NODES ON HOVER
const linkedByIndex = {};
graph.links.forEach(d => {
    linkedByIndex[`${d.source.index},${d.target.index}`] = 1;
});

function isConnected(a, b) {
    return linkedByIndex[`${a.index},${b.index}`] || linkedByIndex[`${b.index},${a.index}`] || a.index === b.index;
}

function fade(opacity) {
    //AVOID FADING FUNCTIONALITY WHEN TABLE IS ACTIVATED
    return d => {
        if ($("tr[data-toggled='1']").length === 0) {
            node.attr('opacity', o => isConnected(d, o) ? 1 : opacity);
            link.style('stroke-opacity', o => (o.source === d || o.target === d ? 1 : opacity));
        }
    };
}

function fadeLink(opacity) {
    return d => {
        if ($("tr[data-toggled='1']").length === 0) {
            node.attr('opacity', o => [d.source, d.target].includes(o) ? 1 : opacity);
            link.style('stroke-opacity', o => [o.source, o.target].includes(d.source) && [o.source, o.target].includes(d.target) ? 1 : opacity);
        }
    };
}

simulation.on("tick", function() {

    //display circles so that their label is not off range + menu position
    circle
        .attr("cx", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.x = Math.max(nodeRadius + 2 + textSize, Math.min(w - (nodeRadius + 2) - 15 - textSize, d.x));
            if(d.id===menu.attr('data-menu-id')){
                menu.style("left", (d.x + 55) + "px")
            }
            return d.x
        })
        .attr("cy", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.y = Math.max(nodeRadius + 2 + textSize, Math.min(h - (nodeRadius + 2) - textSize, d.y));
            if(d.id===menu.attr('data-menu-id')){
                menu.style("top", (d.y + 20) + "px")
            }
            return d.y
        });


    // move labels along and rotate the text to make it readable
    labelGroup
        .attr("transform", d => {
            let ux = d.x <= rcx ? d.x - rcx : rcx - d.x;
            let uy = d.x <= rcx ? d.y - rcy : rcy - d.y;
            let uLength = Math.sqrt(ux ** 2 + uy ** 2)
            let angle = Math.acos(ux / (uLength)) * (180 / Math.PI)

            return `rotate(${d.type === "feature" ? (uy > 0 ? angle + 180 : -angle - 180) : 0},${d.x},${d.y})`
        })

    label
        .attr("text-anchor", d => d.type === "disease" ? "middle" : (d.x <= rcx ? "end" : "start"))
        .attr("x", d => d.type === "feature" ? (d.x > rcx ? nodeRadius : -nodeRadius) : 0);

    plainLabel
        .attr("dx", d => d.x)
        .attr("dy", d => d.y)

    splitLabel.select("tspan:nth-child(1)")
        .attr("dx", d=>d.x)
        .attr("dy", d => d.y)
    splitLabel.select("tspan:nth-child(2)")
        .attr("x", d=>d.x)

    link
        .attr("x1", function (d) {
            return d.source.x;
        })
        .attr("y1", function (d) {
            return d.source.y;
        })
        .attr("x2", function (d) {
            return d.target.x;
        })
        .attr("y2", function (d) {
            return d.target.y;
        });


});


render()
// function to return link, circle and label position when the simulation is generated
/*function ticked() {

    //




}*/


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


/* MENU AND SIDE-TABLES */
const type = $("#d3").data("type")

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

const layoutClasses = $(".table-wrapper").attr('class').replace('table-wrapper','')

$('body').on('click', '.tab', () => {$.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust()})


