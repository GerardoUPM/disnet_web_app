// Same as common-nodes.js but without the menues

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
    "target": index.get(d.target)
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
        .data(graph.links, d=>JSON.stringify(d))
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

function enterNodes(n){
    let g = n.enter().append("g")
        .attr("class", d => d.intersection.map(i => 'intersection_' + i).join(" ").concat(" node"))

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
        .text(d => d.type === "feature" ? (d.degree > 1 || diseasesN===1 ? d.splitName[0] : ""):d.splitName[0]) // No labels in nodes with degree 1 (too many nodes, looks messy)
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

    g.on('mouseover', fade(0.3))
        .on('mouseout', fade(1))


}
function exitNodes(n){
    n.exit().remove()
}
function enterLinks(l){
    let linkHelper = l.enter().insert("line", ".node")
        .attr("class", "link")
        .attr("stroke", "#cccccc")
        .attr("stroke-width", "1.5px");
    linkHelper.on('mouseover', fadeLink(0.5))
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

    //display circles so that their label is not off range
    circle
        .attr("cx", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.x = Math.max(nodeRadius + 2 + textSize, Math.min(w - (nodeRadius + 2) - 15 - textSize, d.x));
            return d.x
        })
        .attr("cy", d => {
            let textSize = (+d.degree === 1 && diseasesN !== 1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
            d.y = Math.max(nodeRadius + 2 + textSize, Math.min(h - (nodeRadius + 2) - textSize, d.y));
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

const type = $("#d3").data("type")