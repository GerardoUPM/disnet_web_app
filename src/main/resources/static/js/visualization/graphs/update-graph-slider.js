function update(){
    graph.links = getRepeatedLinks(graph.links)

    let l = svg.selectAll(".link")
        .data(graph.links, d=>JSON.stringify(d))
    let n = svg.selectAll(".node")
        .data(graph.nodes, d=>d.id)

    enterLinks(l)
    exitLinks(l)
    enterNodes(n)
    exitNodes(n)
    /* TODO: fix bug:
    http://127.0.0.1:8000/visualization/common-nodes?sourceId=SO01&date=2020-01-15&diseaseList=Acneiform+eruption+%7C+Acne+keloidalis+nuchae+%7C+Acne+cosmetica+%7C+Acne+miliaris+necrotica+%7C+Acne+fulminans+%7C+Acne+%7C+Acne+conglobata+%7C+Acne+aestivalis+%7C+Acne+with+facial+edema+%7C+Acne+mechanica+%7C+Acne+medicamentosa+%7C+Dermatitis+herpetiformis+%7C+Dermatopathia+pigmentosa+reticularis+%7C+Dermatographic+urticaria+%7C+Dermal+cylindroma+%7C+Dermatofibrosarcoma+protuberans+%7C+Dermatopolymyositis+%7C+Dermatophytid+%7C+Dermatoses+of+pregnancy+%7C+Dermatochalasis+%7C+Dermanyssus+gallinae+%7C+Dermatosis+neglecta+%7C+Dermatitis+gangrenosa+%7C+Dermatopathic+lymphadenopathy+%7C+Dermatophagia+%7C+Dermatitis+repens+%7C+Dermatosis+papulosa+nigra+%7C+Dermabrasion+%7C+Dermatofibroma+%7C+Dermatomycosis+%7C+Dermatomyositis+%7C+Dermatitis+%7C+Dermatophytosis+%7C+Edema+%7C+Edentulism+%7C+Edema+blister+%7C+Carcinoid+%7C+Carcinoid+syndrome+%7C+Carcinosarcoma+%7C+Carcinoma+%7C+Carcinoma+in+situ+%7C+Carcinoma+ex+pleomorphic+adenoma+%7C+Carcinocythemia+%7C+Melanoma+%7C+Melanocytic+nevus+%7C+MELAS+syndrome+%7C+Melanotic+neuroectodermal+tumor+of+infancy+%7C+Melanosis+coli+%7C+Melanosis+%7C+Melanoma-associated+leukoderma+%7C+Melancholic+depression+%7C+Freckle+%7C+Nevus+spilus+%7C+Nevoid+basal-cell+carcinoma+syndrome+%7C+Nevus+%7C+Nevus+psiloliparus+%7C+Nevus+unius+lateris+%7C+Nevus+sebaceous+%7C+Nevoid+hypertrichosis+%7C+Nevo+syndrome+%7C+Nevus+comedonicus+syndrome+%7C+Nevus+depigmentosus+%7C+Nevus+flammeus+nuchae+%7C+Nevus+anemicus+%7C+Nevus+of+Ota+%7C+Nevus+of+Ito+%7C+&type=gene
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

let sliderDef = d3
    .sliderTop()
    .min(0)
    .max(1)
    .width(300)
    .tickFormat(d3.format('.1~f'))
    .displayFormat(d3.format(',.2~f'))
    .step(0.01)
    .ticks(10)
    .default(0)
    .handle(
        d3
            .symbol()
            .type(d3.symbolCircle)
            .size(200)()
    )
    .on('onchange', val => {
        let limit = d3.format(',.2~f')(val)
        let newLinks = graphConst.links.filter(n=>n.value>=limit)
        let newNodes = [...new Set(newLinks.flatMap(l=>[l.target,l.source]))]
        graph = {
            'nodes': newNodes,
            'links': newLinks
        }
        // TODO do not update unless there's something to update
        update()
    });

let slider = svgSelection
    .append("g")
    .attr('transform', `translate(${width*0.024},${height-40})`);

if(slider.call(sliderDef)) {
    d3.select(".parameter-value > text").attr("y", 27);
    d3.select(".slider").attr("cursor","auto")
}
