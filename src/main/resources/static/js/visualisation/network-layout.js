// // INITIALIZE MENU
// let menu = d3.select("body")
//     .append("div")
//     .attr("class", "row center-align m-0")
//     .style("position", "absolute")
//     .style("z-index", "10")
//     .style("visibility", "hidden")
//     .style("background", "Whitesmoke")
//     .style("opacity", ".85")
//     .style("padding", "6px")
//     .style("border-radius", "3px")
//     .style("border", "1px solid DarkSlateGray")
//     .style("font", "bold 12px sans-serif")
//     .style("color", "black");
// // Display
// d3.selectAll(".menu circle").on("click", function () {
//     console.log(menu)
//     console.log({'this':this})
//     if (menu.style("visibility")==="visible" && this.getAttribute('data-id')===menu.attr("data-menu-id")) {
//         return menu.style("visibility","hidden")
//     }
//     else {
//         return menu.style("visibility", "visible")
//             .attr("data-menu-id", this.getAttribute('data-id') )
//             .html("<div class='col s9 center-align'>" +
//                 "<h6><u>Search by symptom</u></h6>" +
//                 "<span>" +
//                 "Find diseases having "+"<br><i>"+this.getAttribute("data-name")+"</i>"+" as a symptom" +
//                 "</span>" +
//                 "</div>" +
//                 "<div class='col s3 center-align pt-10'>" +
//                 "<a " +
//                 "class='symptom-ajax btn-floating btn-small waves-effect waves-light blue darken-4' " +
//                 "data-symptom='"+this.getAttribute("data-name")+"'" +
//                 "data-id='"+this.getAttribute("data-id")+"'>"+
//                 "<i class= 'material-icons right '>chevron_right</i>" +
//                 "</a>" +
//                 "</div>"
//             );
//
//     }
//
// })
//
//
//
// function ticked() {
//     //display circles so that their label is not off range
//
//     // move labels along and rotate the text to make it readable
//     label.attr("dx", d => d.x)
//         .attr("dy", d => d.y)
//         .attr("transform", d => {
//             let ux = d.x <= rcx ? d.x - rcx : rcx - d.x;
//             let uy = d.x <= rcx ? d.y - rcy : rcy - d.y;
//             let uLength = Math.sqrt(ux ** 2 + uy ** 2)
//             let angle = Math.acos(ux / (uLength)) * (180 / Math.PI)
//
//             return `rotate(${d.type === "symptom" ? (uy > 0 ? angle + 180 : -angle - 180) : 0},${d.x},${d.y})`
//         })
//         .attr("text-anchor", d => d.type === "disease" ? "middle" : (d.x <= rcx ? "end" : "start"))
//         .attr("x", d => d.type === "symptom" ? (d.x > rcx ? nodeRadius : -nodeRadius) : 0);
//
//     link
//         .attr("x1", function (d) {
//             return d.source.x;
//         })
//         .attr("y1", function (d) {
//             return d.source.y;
//         })
//         .attr("x2", function (d) {
//             return d.target.x;
//         })
//         .attr("y2", function (d) {
//             return d.target.y;
//         });
// }
//
// // DEFINE SIMULATION
// simulation.nodes(graph.nodes)
//     .on("tick", ticked);
//
// let button = svg.append('g')
//     .attr("transform", `translate(${margin.left / 4},${h - margin.top/2 - 27})`)
//     .attr("class", "svgButton")
//     .attr("id", "triggerMenuLayout")
// let buttonRect = button.append('rect')
//     .attr("width", 110)
//     .attr("height", 27)
//     .attr("rx", "0.2%")
//     .attr("ry", "0.8%")
// let buttonText = button.append('text')
//     .attr("dy", 18)
//     .attr("dx", 7)
//     .text('Search by symptom')
//
// const moredeg = nodes.reduce((c,n)=>{
//     +n.degree>1 && c++
//     return c
// },0)
//
// const central = nodes.reduce((c,n)=>{!n.degree && c++
//     return c
// },0)
//
// const onedeg = nodes.reduce((c,n)=>{
//     +n.degree===1 && c++
//     return c
// },0)
// let maxLength = Math.max(...graph.nodes.filter(n=>+n.degree>1).map(n=>+d3.select("#" + n.id).attr('data-text-length')));
// $("#triggerMenuLayout").on('click', ()=> {
//
//     simulation.stop()
//         .force("radial",null)
//         .force("charge", d3.forceManyBody().strength(0))
//         .force("center", d3.forceCenter(w/2,h/2))
//         .force("collide", d3.forceCollide().radius(2.5*nodeRadius))
//         .force("x", d3.forceX().x(d => d.degree?(+d.degree===1?-2*onedeg:onedeg):-1.9*(onedeg)).strength(0.9))
//         .alpha(1).restart()
//         .nodes(graph.nodes).on("tick", ()=> {
//         circle
//             .attr("cx", d => {
//                 d.x = d.degree?(+d.degree===1?margin.left+50:w-50-maxLength):w/2
//                 menu.style("left", (d.x + 20) + "px")
//                 return d.x
//             })
//             .attr("cy", d => {
//                 menu.style("top", (d.y - 30) + "px")
//                 return d.y
//             });
//         // move labels along and rotate the text to make it readable
//         label.attr("dx", d=> d.x = d.degree?(+d.degree===1?margin.left+50:w-50-maxLength+nodeRadius):w/2)
//             .attr("dy", d=>d.y)
//             .attr("transform", null)
//             .attr("text-anchor", d => d.type === "disease" ? "middle" : (d.x <= rcx ? "end" : "start"))
//             .attr("x", d => d.type === "symptom" ? (d.x > rcx ? nodeRadius : -nodeRadius) : 0);
//
//         link
//             .attr("x1", function (d) {
//                 return d.source.x;
//             })
//             .attr("y1", function (d) {
//                 return d.source.y;
//             })
//             .attr("x2", function (d) {
//                 return d.target.x;
//             })
//             .attr("y2", function (d) {
//                 return d.target.y;
//             });
//
//         })
//
//
//         node.call(d3.drag()
//             .on("start", null)
//             .on("drag", null)
//             .on("end", null));
//
// })
//
//
//
// $('body').on('click', '.symptom-ajax', (event) => {
//     console.log('aaaaaaaaaaaaaaasdfclicked')
//
//     let symptom = event.currentTarget.dataset['symptom']
//     let symptomId = "tab_"+event.currentTarget.dataset['id']
//     if (!$("#"+symptomId).length){
//         $.ajax({
//             type:"get",
//             data: {'symptom' : symptom},
//             url:"/visualisation/ajax-search-by-symptom",
//             dataType: "html",
//             success: function(htmlTable){
//
//                 new Promise((resolve) => {
//                         new Promise( (resolve) => {
//                             if (!$('#new-wrapper').length){
//                                 $(".table-wrapper").removeClass(layoutClasses)
//                                 $(".table-wrapper").wrap(`<div class="row"><div id="new-wrapper" class="${layoutClasses}"></div></div>`)
//                                 $(".table-wrapper").before("    <ul class='tabs'>" +
//                                     "        <li class='tab col s4 two-lines'><a href='#common-nodes'><b class='pt-5'><span class='row mb-0'>Common</span><span class='row mb-0'>symptoms</span></b></a></li>" +
//                                     `       <li class='tab col s4'><a href='#${symptomId}'>${symptom}</a></li>` +
//                                     "    </ul>")
//                                 $(".table-wrapper").after(`<div id=${symptomId}></div>`)
//                             } else {
//                                 $('.tabs').append(`<li class='tab col s4'><a href='#${symptomId}'>${symptom}</a></li>`)
//                                 $('#new-wrapper').children().last().after(`<div id=${symptomId}></div>`)
//                             }
//                             resolve()
//                         }).then(()=>{
//                             $(document).ready(function () {
//                                 $('ul.tabs').tabs()
//                             })
//                         })
//                     resolve()
//                 }).then(()=>{
//                     console.log('second then')
//                     console.log(symptomId)
//                     $('#'+symptomId).html(htmlTable)
//                 })
//
//
//             },
//             error: (a,b,c) => {
//                 console.error('ajaxError')
//                 console.log({'a':a,'b':b,'c':c})
//             }
//         });
//     } else {
//         $("a[href='#"+symptomId+"']").click();
//     }
// })
//
// const layoutClasses = $(".table-wrapper").attr('class').replace('table-wrapper','')
//
// $('body').on('click', '.tab', () => {$.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust()})
//
//
