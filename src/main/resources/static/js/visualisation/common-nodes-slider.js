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
    .attr('transform', `translate(${w*0.024},${h-40})`);

if(slider.call(sliderDef)) {
    d3.select(".parameter-value > text").attr("y", 27);
    d3.select(".slider").attr("cursor","auto")
}
