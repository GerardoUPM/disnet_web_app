

const source = "wikipedia";
const version = "2020-01-15";
//const diseaseArray = ["Precancerous condition", "Cancer", "Leukemia", "Progeria", "Male breast cancer"];
//const fetchPromises = [];
// diseaseArray.forEach(disease => {
//     fetchPromises.push(fetch(`http://disnet.ctb.upm.es/api/disnet/query/disnetConceptList?source=${source}&version=${version}&diseaseName=${disease}&matchExactName=true&token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJpbmEuZGlhei51enF1aWFub0BhbHVtbm9zLnVwbS5lcyIsImF1ZCI6IndlYiIsIm5hbWUiOiJNYXJpbmEgRMOtYXogVXpxdWlhbm8iLCJ1c2VyIjp0cnVlLCJpYXQiOjE1ODE1MDI3Mjh9.wMptlK7PwfXeVBqf7Em8Ahqmr0r60BqmEvMVOQVDtkbZgO_HTwkJfKG4UsKUN2m7ZFR9cO3qu8MRUvxJ3Bm1Cw`).then(response => response.json()))

let windowWidth = window.innerWidth;
//const svgLeftMargin = 50;
const offcenter = windowWidth<601 ? 1 : 8/12; // col s8
let w = windowWidth * offcenter ;


let h = window.innerHeight - 64 - 40; // - header - margin(svg's margin-top + div.row's margin-bottom)
let svg = d3.select("#d3")
    .attr("width", w)
    .attr("height", h)
    .attr("id", "svg")
    .style("border", "rgba(47, 79, 79, 0.3) 1px solid")
    .style("border-radius", "5px");


// });

// NODE COLORS
let color = d3.scaleOrdinal(d3.schemeCategory20);


// BASIC NODE SIZE
let nodeRadius = 5;

// GRAPH VARIABLES
let rcx = w / 2;
let rcy = h / 2;

// ----- MANAGE JSON DATA -----


let preNodes = JSON.parse(svg.attr('data-nodes'))['preNodes']
let preLinks = JSON.parse(svg.attr('data-links'))['preLinks']
    console.log(preNodes, preLinks)


    // D3 node format
    let nodes = [...new Set(preNodes.map(JSON.stringify))].map(JSON.parse)

    // calculate node degrees
    let nodeList = nodes.map(node => node.id);
    preLinks.forEach(link => nodes[nodeList.indexOf(link.target)].degree = (nodes[nodeList.indexOf(link.target)].degree + 1) || 1)

    // name node intersections on nodes
    let intersections = JSON.parse(svg.attr('data-intersections'))
    nodes.forEach(node=> {
        node.intersection = Object.keys(intersections).filter(idx => intersections[idx].includes(node.name))
    })
    const noIntersections = $.isEmptyObject(intersections)
    // D3 link format
    let index = new Map(nodes.map(d => [d.id, d]))
    let links = preLinks.map(d => Object.assign(Object.create(d), {
        "source": index.get(d.source),
        "target": index.get(d.target)
    }));

    //nodes.sort((a,b) => a.degree < b.degree ? 1 : (a.degree > b.degree ? -1 :0)); //sorting nodes by degree makes the nw look messier


const graph = {"nodes": nodes, "links": links}

// *** D3 NETWORK *** //

    console.log(graph);

    const maxDegree = Math.max(...graph.nodes.reduce(function (result, node) {
        if (node.degree !== undefined) {
            result.push(node.degree);
        }
        return result
    }, []));


    const interpolateDegree = d3.scaleLinear().domain([1, maxDegree]).range([0.85, 0.15])
    // FORCE SIMULATION

    let symptomsN = +graph.nodes.reduce((accumulator, node) => {
        return node.type === "symptom" ? accumulator + 1 : accumulator + 0
    }, 0)

    let diseasesN = +graph.nodes.reduce((accumulator, node) => {
        return node.type === "disease" ? accumulator + 1 : accumulator + 0
    }, 0)


    let simulation = d3.forceSimulation(graph.nodes)
        .force("link", d3.forceLink().id(d => d.id))
        .force("charge", d3.forceManyBody().strength(symptomsN*nodeRadius>rcx ? -rcx/2 : -symptomsN*nodeRadius))
        .force("center", d3.forceCenter(rcx, rcy))
        .force("collide", d3.forceCollide().radius(nodeRadius * 2))
        .force("radial", d3.forceRadial().radius(d => d.type === "disease" ?  (diseasesN===1 ? 0 : (Math.log(diseasesN * nodeRadius)/Math.log(diseasesN+1))*rcx/60 ) : (Math.log(symptomsN * nodeRadius)/Math.log(symptomsN+1))*rcx/3).x(rcx).y(rcy).strength(1));

    // INITIALIZE LINKS
    let link = svg.append("g")
        .attr("class", "links")
        .selectAll("line")
        .data(graph.links)
        .enter()
        .append("line");

    // INITIALIZE NODES
    let node = svg.append("g")
        .attr("class", "nodes")
        .selectAll("g")
        .data(graph.nodes)
        .enter()
        .append("g")
        .attr("class", d=>d.intersection.map(i=>'intersection_'+i).join(" "));
    let circle = node.append("circle")
        .attr("r", d => nodeRadius)
        .attr("fill", d => d.degree !== undefined ? d3.interpolateInferno(interpolateDegree(d.degree)) : "#0D47A1")
        .attr("stroke-width", 1)
        .attr("stroke", d => d.type === "disease" ? "rgba(13,71,161,0.75)" : "GhostWhite")
        .attr("data-name", d=>d.name)
        .attr("data-degree", d=>d.degree);


    // INITIALIZE LABELS
    let label = node.append("text")
        .text(d => d.type === "disease" ? d.name : (d.degree > 1 || diseasesN === 1 || noIntersections ? d.name : "")) // No labels in nodes with degree 1 (too many nodes, looks messy)
        .attr("id", d => d.id)
        .attr("font-weight", d => d.type === "disease" ? 500 : 400)
        .attr("y", d => d.type === "symptom" ? nodeRadius : 2.1 * nodeRadius)
        .attr("data-text-length", d => {
            svg.append("text").text(d.name).attr("id", "dummy")
            let textLength = d3.select("#dummy").node().getComputedTextLength()
            d3.select("#dummy").remove();
            return textLength
        })

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
    // Display tooltips when hovering over nodes w/o label (= nodes with degree 1)
    circle.on("mouseover", function(){ if (+this.getAttribute("data-degree")===1&&!noIntersections){
            return tooltip.style("visibility", "visible")
                .text(this.getAttribute("data-name"));
            }
        })
        .on("mousemove", function(){return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
        .on("mouseout", function(){return tooltip.style("visibility", "hidden");});



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
    node.call(d3.drag()
        .on("start", dragStart)
        .on("drag", dragging)
        .on("end", dragEnd));

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
                if($("tr[data-toggled='1']").length===0){
                    node.attr('opacity', o => isConnected(d, o) ? 1 : opacity);
                    link.style('stroke-opacity', o => (o.source === d || o.target === d ? 1 : opacity));
                }
            };
    }
    function fadeLink(opacity) {
            return d => {
                if($("tr[data-toggled='1']").length===0){
                    node.attr('opacity', o => [d.source, d.target].includes(o) ? 1 : opacity);
                    link.style('stroke-opacity', o => [o.source, o.target].includes(d.source) && [o.source, o.target].includes(d.target) ? 1 : opacity);
                }
            };
    }
    node.on('mouseover', fade(0.3))
        .on('mouseout', fade(1))
    link.on('mouseover', fadeLink(0.5))
        .on('mouseout', fadeLink(1))

    // DEFINE SIMULATION
    simulation.nodes(graph.nodes)
        .on("tick", ticked);

    simulation.force("link")
        .links(graph.links);
    // function to return link, circle and label position when the simulation is generated
    function ticked() {
        //display circles so that their label is not off range
        circle
            .attr("cx", d => {
                let textSize = (+d.degree===1 && diseasesN!==1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
                return d.x = Math.max(nodeRadius+2 + textSize, Math.min(w - (nodeRadius+2)-15 - textSize, d.x));
            })
            .attr("cy", d => {
                let textSize = (+d.degree===1 && diseasesN!==1 && !noIntersections) ? 0 : +d3.select("#" + d.id).attr('data-text-length')
                return d.y = Math.max(nodeRadius+2 + textSize, Math.min(h - (nodeRadius+2) - textSize, d.y));
            });
        // move labels along and rotate the text to make it readable
        label.attr("dx", d => d.x)
            .attr("dy", d => d.y)
            .attr("transform", d => {
                let ux = d.x <=  rcx ? d.x -  rcx :  rcx - d.x;
                let uy = d.x <=  rcx ? d.y - rcy : rcy - d.y;
                let uLength = Math.sqrt(ux ** 2 + uy ** 2)
                let angle = Math.acos(ux / (uLength)) * (180 / Math.PI)

                return `rotate(${d.type === "symptom" ? (uy > 0 ? angle + 180 : -angle - 180) : 0},${d.x},${d.y})`
            })
            .attr("text-anchor", d => d.type === "disease" ? "middle" : (d.x <=  rcx ? "end" : "start"))
            .attr("x", d => d.type === "symptom" ? (d.x > rcx ? nodeRadius : -nodeRadius) : 0);

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
    }


//// when clicking the table, nodes are highlighted

$('.fader').on('click', e=>{
    console.log('click')
    console.log('currentTarget:')
    console.log(e.currentTarget)

    link.attr('opacity', 1)
    node.attr('opacity', 1)
    let selector = e.currentTarget.className.split(" ").find(d => /intersection_[0-9]*/.test(d))
    console.log(selector)
    if (selector) {
        if (+e.currentTarget.dataset['toggled'] === 0) {
            d3.selectAll(node.nodes().filter(d => !d3.selectAll("g." + selector).nodes().includes(d))).attr('opacity', 0.1)
            let intersectionId = selector.replace("intersection_", "")
            let disNodes = d3.selectAll(link.filter(d => d.target.intersection.includes(intersectionId)).data().map(d => d.source))._groups[0]
            node.filter(d => disNodes.includes(d)).attr('opacity', 1)
            link.filter(d => !d.target.intersection.includes(intersectionId)).attr('opacity', 0.1)
            link.filter(d => d.target.intersection.includes(intersectionId))
            console.log("tr."+selector)
            // turn off other rows before turning on this one
            $("tr[data-toggled=1]").each((d,e)=>e.dataset['toggled']="0")
            // turn on current
            $("tr."+selector).each((d,e)=>e.dataset['toggled'] = "1")
        } else {
            console.log(+e.currentTarget.dataset['toggled'])
            d3.selectAll(node.nodes().filter(d => !d3.selectAll("g." + selector).nodes().includes(d))).attr('opacity', 1)
            let intersectionId = selector.replace("intersection_", "")
            link.filter(d => !d.target.intersection.includes(intersectionId)).attr('opacity', 1)
            console.log("tr."+selector)
            $("tr."+selector).each((d,e)=>e.dataset['toggled'] = "0")
        }
    }
})


/* TABLE SCROLL */
let $th = $('.table-wrapper').find('thead th')
$('.table-wrapper').on('scroll', function() {
    let currentScroll = +this.scrollTop
    let n = currentScroll > 0 && $('.table-wrapper').find('caption').length===1 ? currentScroll-27 : currentScroll;
    $th.css('transform', 'translateY('+ n +'px)');
});