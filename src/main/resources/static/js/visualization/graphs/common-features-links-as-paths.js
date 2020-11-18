'use strict'

const windowWidth = window.innerWidth;
const type = $("#d3").data("type")
const offcenter = windowWidth < 601 ? 1 : 8 / 12; // col s8
const width = windowWidth * offcenter;
const margin = {'top': 20, 'right': 0, 'bottom': 0, 'left': 50}

const height = window.innerHeight - 64 - 40; // - header - margin(svg's margin-top + div.row's margin-bottom)
let svgSelection = d3.select("#d3")
let svg = svgSelection
    .attr("width", width)
    .attr("height", height)
    .style("border", "rgba(47, 79, 79, 0.3) 1px solid")
    .style("border-radius", "5px"); //TODO: style in css

// BASIC NODE SIZE
const nodeRadius = 5;
const linkWidthMultiplier = 5;
const absoluteArcHeight = height/(100*linkWidthMultiplier/3);

const halfIncrements = (int) => {
    let ceil = Math.ceil(int*10)
    let ceilHalf = Math.round(ceil/2)
    if (ceilHalf<int){
        return ceilHalf
    }
    else {
        return ceil/10
    }
}


// GRAPH VARIABLES
const rcx = width / 2;
const rcy = height / 2;

// ----- MANAGE JSON DATA -----
const preNodes = JSON.parse(svg.attr('data-nodes'))['preNodes']
const preLinks = JSON.parse(svg.attr('data-links'))['preLinks']



// D3 node format
let nodes = [...new Set(preNodes.map(JSON.stringify))].map(JSON.parse)
nodes.forEach(n => {
    svg.append("text").text(n.name).attr("id", "dummy")
    let textLength = d3.select("#dummy").node().getComputedTextLength()
    if (textLength>width/4.5){
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
let preLinksFiltered = preLinks.filter((v,i,a)=>a.findIndex(t=>(t.source === v.source && t.target ===v.target))===i) // we only need a one.way comparison bc "diseases" are always set as sources
preLinksFiltered.forEach(link => nodes[nodeList.indexOf(link.target)].degree = (nodes[nodeList.indexOf(link.target)].degree + 1) || 1)

// name node intersections on nodes
let intersections = JSON.parse(svg.attr('data-intersections'))
nodes.forEach(node => {
    node.intersection = Object.keys(intersections).filter(idx => intersections[idx].includes(node.name))
})
const noIntersections = $.isEmptyObject(intersections)
// D3 link format
const index = new Map(nodes.map(d => [d.id, d]))
let links = preLinks.map(d => Object.assign(Object.create(d), {
    "source": index.get(d.source),
    "target": index.get(d.target),
    "value": +d.value,
    "mappingSource": d.mappingSource,
    "mappingVocabulary": d.mappingVocabulary,
    "mappedDisease": d.mappedDisease
}));

// const mappingSources = [...new Set(links.map(l=>l.mappingSource))].sort()
// const mappingSourcesN = mappingSources.length
// const mapMappingSources = {}
// mappingSources.forEach((s,i)=>mapMappingSources[s]=d3.scaleLinear().domain([0, mappingSourcesN]).range([0.15, 0.95])(i))
// // const colorBySource = mappingSource => d3.hsl(d3.hsl(d3.interpolateTurbo(mapMappingSources[mappingSource])).h, 0.6,0.75);
// const colorBySource = mappingSource => d3.hsl(d3.interpolateCool(mapMappingSources[mappingSource])).darker(0.3)
// // const colorBySource = mappingSource => d3.hsl(d3.interpolateRainbow(mapMappingSources[mappingSource]))
const transformColorNoTransparency = color => {
    return d3.hsl(color.h,color.s,color.l*1.2)

};

const transformColor = color => {
    return d3.hsl(color.h,color.s,color.l*1.2,0.3)

};


const mappingSources = [...new Set(links.map(l=>l.mappingSource))].sort()
const mapMappingSources = {}
mappingSources.forEach((s,i)=>mapMappingSources[s]=i)
// const colorSchema = .slice()

const colorBySource = mappingSource => d3.hsl(d3.schemeAccent[mapMappingSources[mappingSource]]).darker(0.4);
// const reversedColorScheme = d3.schemeSet1.slice().reverse()
// const colorBySource = mappingSource => reversedColorScheme[mapMappingSources[mappingSource]];
// const colorBySource = mappingSource => d3.hsl(d3.hsl(reversedColorScheme[mapMappingSources[mappingSource]]).h, 0.6,0.75);

function getRepeatedLinks(links){
    links.forEach(link => {

        // find other links with same target+source or source+target
        let same = links.filter(l => l.source === link.source && l.target === link.target)

        let sameAlt = links.filter(l => l.source === link.target && l.target === link.source) //TODO: en teoría source siempre es disease por lo que el alt no sería necesario

        let sameAll = same.concat(sameAlt);

        sameAll.forEach((s, i) => {
            s.sameIndex = (i + 1);
            s.sameTotal = sameAll.length;
            s.sameTotalHalf = (s.sameTotal / 2);
            s.sameUneven = ((s.sameTotal % 2) !== 0);
            s.sameMiddleLink = ((s.sameUneven === true) && (Math.ceil(s.sameTotalHalf) === s.sameIndex));
            s.sameLowerHalf = (s.sameIndex <= s.sameTotalHalf);
            s.sameArcDirection = s.sameLowerHalf ? 0 : 1;
            s.sameIndexCorrected = s.sameLowerHalf ? s.sameIndex : (s.sameIndex - Math.ceil(s.sameTotalHalf));
        });
    });
    return links
}

let maxSame = Math.max(...links.map(l => l.sameIndex));

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

    graph.links = getRepeatedLinks(graph.links)

    simulation = simulation
        .force("link", d3.forceLink().id(d => d.id))
        .force("charge", d3.forceManyBody().strength(featuresN * nodeRadius > rcx ? -rcx / 2 : -featuresN * nodeRadius))
        .force("center", d3.forceCenter(rcx, rcy))
        .force("collide", d3.forceCollide().radius(nodeRadius * 2))
        .force("radial", d3.forceRadial().radius(d => d.type === "disease" ? (diseasesN === 1 ? 0 : (Math.log(diseasesN * nodeRadius) / Math.log(diseasesN + 1)) * rcx / 60) : (Math.log(featuresN * nodeRadius) / Math.log(featuresN + 1)) * rcx / 3).x(rcx).y(rcy).strength(1));

    let l = svg.selectAll(".link")
        .data(graph.links,d=>JSON.stringify(d))
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



    // link.on("mouseover", function(){return linkTooltip.style("visibility", "visible").text('hello');})
    //     .on("mousemove", function(){return linkTooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
    //     .on("mouseout", function(){return linkTooltip.style("visibility", "hidden");});


    // DEFINE SIMULATION
    simulation.nodes(graph.nodes)

    simulation.force("link")
        .links(graph.links);
    //
    // l.on('mouseover', (d) => {
    //         // fadeLink(d,0.5);
    //         return linkTooltip.style("visibility", "visible").text('hello');
    //     }
    //
    // )
    //     .on('mouseout', (d)=> {
    //         // fadeLink(d,1)
    //         return linkTooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");
    //     })
    //     .on("mouseout", function(){return linkTooltip.style("visibility", "hidden");});
}

function enterNodes(n){
    let g = n.enter().append("g")
        .attr("class", d => d.intersection.map(i => 'intersection_' + i).join(" ").concat(" menu node"))
        // .attr("class", d => d.intersection.map(i => 'intersection_' + i).join(" ").concat(d.intersection.length?" menu node":"node"))
        // .style("z-index", "5")

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
        .text((d) => d.type === "feature" ? (d.degree > 1 || diseasesN===1 ? d.splitName[0] : ""):d.splitName[0]) // No labels in nodes with degree 1 (too many nodes, looks messy)
    splitLabelHelper.append("tspan")
        .text(d => d.type === "feature" ? (d.degree > 1 || diseasesN===1 ? d.splitName[1] : ""):d.splitName[1]) // No labels in nodes with degree 1 (too many nodes, looks messy)
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
// HIGHLIGHT LINKS AND NODES ON HOVER
    function isConnected(a, b, linkedByIndex) {
        return linkedByIndex[`${a.index},${b.index}`] || linkedByIndex[`${b.index},${a.index}`] || a.index === b.index;
    }

    function fade(opacity) {
        //AVOID FADING FUNCTIONALITY WHEN TABLE IS ACTIVATED
        return d => {
            const lbi = Object.fromEntries(graph.links.map(l => [l.source.index+','+l.target.index,1]));
            if ($("tr[data-toggled='1']").length === 0) {
                node.attr('opacity', o => isConnected(d, o, lbi) ? 1 : opacity);
                link.style('stroke-opacity', o => (o.source === d || o.target === d ? 1 : opacity));
            }
        };
    }
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
    let linkHelper = l.enter().insert("path", ".node")
        .attr("class", "link")
        .attr("stroke-width", d => {
            let val = halfIncrements(d.value)
            // if (val*(linkWidthMultiplier-val)<0.5){
            //     return 0.8
            // }
            return val*(linkWidthMultiplier+(1/val))
        })
        // .attr("stroke-width", "1.5px")
        .style("stroke", d => transformColor(colorBySource(d.mappingSource)))
        .attr("data-value", d=>d.value)
        .attr("data-mapping-source", d=>d.mappingSource)
        .attr("data-mapping-vocabulary", d=>d.mappingVocabulary)
        .attr("data-disease", d=>d.source.name)
        .attr("data-mapped-disease", d=>d.mappedDisease)
        .attr("data-feature-name", d=>d.target.name);


if (type==="pathway") {
    linkHelper.on("mouseover", function (d) {
        fadeLink(d,0.5);
        let mappedDisease = this.getAttribute("data-mapped-disease");
        return linkTooltip.style("visibility", "visible")
            .html(`
                        <b>${this.getAttribute("data-disease")}</b> maps to <b>${mappedDisease}</b>
                        </br>
                        <u>Mapping source</u>: ${this.getAttribute("data-mapping-source")}
                        </br>
                        <u>Vocabulary</u>: ${this.getAttribute("data-mapping-vocabulary")}
                    `
            );
    })
}
else {
    linkHelper.on("mouseover", function (d) {
        fadeLink(d,0.5);
        let mappedDisease = this.getAttribute("data-mapped-disease");
        return linkTooltip.style("visibility", "visible")
            .html(`
                        <b>${this.getAttribute("data-disease")}</b> maps to <b>${mappedDisease}</b>
                        </br>
                        <u>Mapping source</u>: ${this.getAttribute("data-mapping-source")}
                        </br>
                        <u>Vocabulary</u>: ${this.getAttribute("data-mapping-vocabulary")}
                        </br>
                        <b><u>${mappedDisease+' - '+this.getAttribute("data-feature-name")}</u> score</b>: ${this.getAttribute("data-value")}
                    `
            );
    })
}
    linkHelper
        .on("mousemove", function () {
            return linkTooltip.style("top", (event.pageY - 10) + "px").style("left", (event.pageX + 10) + "px");
        })
        .on("mouseout", function (d) {
            fadeLink(d,1)
            return linkTooltip.style("visibility", "hidden");
        });



}
function exitLinks(l){
    l.exit().remove()
}


// INITIALIZE TOOLTIPS
const tooltip = d3.select("body")
    .append("div")
    .style("position", "absolute")
    .style("z-index", "10")
    .style("visibility", "hidden")
    .style("background", "DarkSlateGray")
    .style("opacity", ".75")
    .style("padding", "6px")
    .style("border-radius", "10px")
    .style("color", "white")
    .style("font", "bold 12px sans-serif")


let linkTooltip = tooltip.clone()
    linkTooltip.style("font", "normal 12px sans-serif")


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


function fadeLink(currentLink, opacity) {
        if ($("tr[data-toggled='1']").length === 0) {
            node.attr('opacity', o => [currentLink.source, currentLink.target].includes(o) ? 1 : opacity);
            link.style('stroke-opacity', o => [o.source, o.target].includes(currentLink.source) && [o.source, o.target].includes(currentLink.target) ? 1 : opacity);
        }
}
render()

simulation.on("tick", function() {

    //display circles so that their label is not off range + menu position
    circle
        .attr("cx", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.x = Math.max(nodeRadius + 2 + textSize, Math.min(width - (nodeRadius + 2) - 15 - textSize, d.x));
            if(d.id===menu.attr('data-menu-id')){
                menu.style("left", (d.x + 55) + "px")
            }
            return d.x
        })
        .attr("cy", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.y = Math.max(nodeRadius + 2 + textSize, Math.min(height - (nodeRadius + 2) - textSize, d.y));
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
        .attr("d", function (d) {
            let dr = distanceBetweenTwoPoints(d.source.x,d.source.y,d.target.x,d.target.y),
                arcHeight = absoluteArcHeight*(d.sameIndexCorrected - (d.sameUneven ? 0 : 0.5));
            let arc = d.sameMiddleLink ? 0 : (arcHeight/2)+(dr**2/(8*arcHeight))

            return "M" + d.source.x + "," + d.source.y +
                "A" + arc + "," + arc + " 0 0," + d.sameArcDirection + " " + d.target.x + "," + d.target.y;
        });


});

const  distanceBetweenTwoPoints = function( x1, y1, x2, y2 ) {

    return Math.sqrt( (x2-x1)**2 + (y2-y1)**2 );
};


// function to return link, circle and label position when the simulation is generated
/*function ticked() {

    //




}*/

let legendBuffer = 0;
let legendPaddings = 30;

const legend = svg.append("g")
    .attr("id", "legend")
    .attr("transform", `translate(${width-140},${height-legendPaddings*mappingSources.length})`)

// mappingSources.forEach(s=>console.log(`%c                                         ${d3.hsl(transformColor(colorBySource(s))).formatHsl()}                                      `, `background: ${transformColor(colorBySource(s))}`))

mappingSources.forEach(s=>{
    legend.append("line")
        .attr("x1", 0)
        .attr("y1", legendBuffer)
        .attr("x2", 30)
        .attr("y2", legendBuffer)
        .style("stroke", transformColorNoTransparency(colorBySource(s)))
        .style("stroke-width", "3px")
        .style("stroke-linecap", "round")

    legend.append("text").attr("x", 40).attr("y", legendBuffer).text(s).style("font-size", "12px").attr("alignment-baseline", "middle")

    legendBuffer+=legendPaddings

})



//
// legend
//     .append("line")
//     .attr("x1", 0)
//     .attr("y1", 30)
//     .attr("x2", 30)
//     .attr("y2", 30)
//     .style("stroke", "#69b3a2")
//     .style("stroke-width", "3px")
//     .style("stroke-linecap", "round")
//
// legend.append("text").attr("x", 40).attr("y", 30).text("variable B").style("font-size", "15px").attr("alignment-baseline", "middle")